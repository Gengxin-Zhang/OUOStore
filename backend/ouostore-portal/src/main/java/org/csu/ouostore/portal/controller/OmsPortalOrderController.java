package org.csu.ouostore.portal.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.entity.OmsOrder;
import org.csu.ouostore.model.query.AlipayNotifyParam;
import org.csu.ouostore.model.query.OmsGenerateConfirmOrderParam;
import org.csu.ouostore.model.query.OmsGenerateOrderParam;
import org.csu.ouostore.model.vo.AlipayFormVo;
import org.csu.ouostore.model.vo.ConfirmOrderVo;
import org.csu.ouostore.model.vo.OrderVo;
import org.csu.ouostore.service.AlipayService;
import org.csu.ouostore.service.OmsOrderService;
import org.csu.ouostore.service.config.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 订单管理
 */
@RestController
@Api(tags = "订单管理")
@RequestMapping("/api/v1/orders")
public class OmsPortalOrderController {

    @Autowired
    private OmsOrderService orderService;
    @Autowired
    private AlipayService alipayService;

    @ApiOperation("根据购物车选中商品信息生成确认单信息")
    @GetMapping("/confirm_info")
    public CommonResult<ConfirmOrderVo> generateConfirmOrder(OmsGenerateConfirmOrderParam param) {
        ConfirmOrderVo confirmOrderVo = orderService.generateConfirmOrder(param);
        return CommonResult.OK(confirmOrderVo);
    }

    @ApiOperation("根据确认信息生成订单")
    @PostMapping("")
    public CommonResult generateOrder(@RequestBody OmsGenerateOrderParam param) {
        OrderVo orderVo = orderService.generateOrder(param);
        return CommonResult.OK(orderVo, "下单成功");
    }

    @ApiOperation("支付宝支付")
    @GetMapping("/{orderId}/alipay")
    public CommonResult<AlipayFormVo> alipay(@PathVariable Long orderId) {
        AlipayFormVo result = alipayService.alipay(orderId);
        return CommonResult.OK(result);
    }

    /**
     * 支付成功后支付宝会以post请求设置的notify_url
     */
    @PostMapping("/alipay/callback")
    public CommonResult alipayCallback(AlipayNotifyParam param) {
        alipayService.alipayCallback(param);
        return CommonResult.OK(null);
    }

    /**
     * 支付宝同步回调接口
     */
    @GetMapping("/alipay/callback")
    public CommonResult alipayCallback(HttpServletRequest request) {
        alipayService.alipayCallback(request);
        return CommonResult.OK(null);
    }

    @ApiOperation("支付宝支付(测试使用)")
    @GetMapping("/{orderId}/alipay_test")
    public void alipayTest(@PathVariable Long orderId, HttpServletResponse response, HttpServletRequest request) throws IOException {
        OmsOrder order = orderService.getById(orderId);
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
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
        String total_amount = new String("100");
        //订单名称，必填
        String subject = new String("OUOStore订单支付");
        aliPayRequest.setBizContent("{\"out_trade_no\":\"" + order_number + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求
        String result = null;
        try {
            result = alipayClient.pageExecute(aliPayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //输出
        out.println(result);
    }

}
