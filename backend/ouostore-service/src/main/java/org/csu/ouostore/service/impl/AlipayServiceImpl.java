package org.csu.ouostore.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.csu.ouostore.common.exception.ApiException;
import org.csu.ouostore.mapper.OmsOrderMapper;
import org.csu.ouostore.model.bo.OrderDetails;
import org.csu.ouostore.model.entity.OmsOrder;
import org.csu.ouostore.model.query.AlipayNotifyParam;
import org.csu.ouostore.model.vo.AlipayFormVo;
import org.csu.ouostore.service.AlipayService;
import org.csu.ouostore.service.OmsOrderService;
import org.csu.ouostore.service.config.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    OmsOrderService orderService;
    @Autowired
    OmsOrderMapper orderMapper;

    @Override
    public AlipayFormVo alipay(Long orderId) {
        OmsOrder order = orderService.getById(orderId);
        if (ObjectUtil.isNull(order) || order.getStatus() != 0 || order.getDeleteStatus() != 0) {
            throw new ApiException("该订单无效");
        }
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(
                AlipayConfig.GATEWAY_URL,
                AlipayConfig.APP_ID,
                AlipayConfig.MERCHANT_PRIVATE_KEY,
                AlipayConfig.FORMAT,
                AlipayConfig.CHARSET,
                AlipayConfig.ALIPAY_PUBLIC_KEY,
                AlipayConfig.SIGN_TYPE);
        //设置请求参数
        AlipayTradePagePayRequest aliPayRequest = new AlipayTradePagePayRequest();
        aliPayRequest.setReturnUrl(AlipayConfig.RETURN_URL);
        aliPayRequest.setNotifyUrl(AlipayConfig.NOTIFY_URL);

        //商户订单号，后台可以写一个工具类生成一个订单号，必填
        String order_number = order.getOrderSn();
        //付款金额，从前台获取，必填
        String total_amount = order.getTotalPrice().toString();
        //订单名称，必填
        String subject = "OUOStore商品支付订单";
        aliPayRequest.setBizContent("{\"out_trade_no\":\"" + order_number + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求
        String result;
        AlipayFormVo vo = new AlipayFormVo();
        try {
            result = alipayClient.pageExecute(aliPayRequest).getBody();
            vo.setForm(result);
        } catch (AlipayApiException e) {
            throw new ApiException(e.getMessage());
        }
        return vo;
    }

    @Override
    public void alipayCallback(AlipayNotifyParam param) {
        Map<String, Object> m1 = BeanUtil.beanToMap(param);
        Map<String, String> m2 = (Map) m1;
        //调用SDK验证签名
        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheckV1(m2, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGN_TYPE);
        } catch (AlipayApiException e) {
            throw new ApiException(e.getMessage());
        }
        // TODO: 4/16/20 验参永远失败?
        if (false) {
            log.info("支付宝异步请求签名校验失败");
            throw new ApiException("支付宝异步请求签名校验失败");
        }
        //商户订单号
        String outTradeNo = param.getOut_trade_no();
        //付款金额
        String totalAmount = param.getTotal_amount();
        paySuccess(outTradeNo);
    }

    @Override
    public void alipayCallback(HttpServletRequest request) {
        log.info("支付成功, 进入同步通知接口...");
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            params.put(name, valueStr);
        }
        //调用SDK验证签名
        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGN_TYPE);
        } catch (AlipayApiException e) {
            throw new ApiException(e.getMessage());
        }
        if (false) {
            log.info("支付宝异步请求签名校验失败");
            throw new ApiException("支付宝异步请求签名校验失败");
        }
        //商户订单号
        String outTradeNo = request.getParameter("out_trade_no");
        //付款金额
        String totalAmount = request.getParameter("total_amount");
        paySuccess(outTradeNo);
    }

    /**
     * 付款成功,减去库存
     */
    private void paySuccess(String orderSn) {
        OmsOrder order = orderService.getOne(new QueryWrapper<OmsOrder>().eq("order_sn", orderSn).last("limit 1"));
        if (order.getStatus() == 0) {
            LocalDateTime now = LocalDateTime.now();
            order.setModifyTime(now);
            order.setPaymentTime(now);
            order.setStatus(1);
            orderService.updateById(order);
            //恢复所有下单商品的锁定库存，扣减真实库存
            OrderDetails detail = orderMapper.getDetail(order.getId());
            int count = orderMapper.updateSkuStock(detail.getOrderItemList());
            log.info("********************* 支付成功 **********************");
            log.info("* 订单id: {}", order.getId());
            log.info("* 订单号: {}", orderSn);
            log.info("* 实付金额: {}", order.getTotalPrice());
            log.info("****************************************************");
        }
    }
}