package org.csu.ouostore.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.csu.ouostore.common.exception.ApiException;
import org.csu.ouostore.mapper.OmsOrderMapper;
import org.csu.ouostore.model.bo.CartItemDetail;
import org.csu.ouostore.model.bo.OrderDetails;
import org.csu.ouostore.model.entity.*;
import org.csu.ouostore.model.query.OmsGenerateConfirmOrderParam;
import org.csu.ouostore.model.query.OmsOrderPatchParam;
import org.csu.ouostore.model.query.OmsOrderQueryParam;
import org.csu.ouostore.model.vo.ConfirmOrderVo;
import org.csu.ouostore.model.vo.OmsOrderDetailVo;
import org.csu.ouostore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Service
@Primary
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder> implements OmsOrderService {

    @Autowired
    protected OmsOrderItemService orderItemService;
    @Autowired
    protected OmsOrderOperateLogService orderOperateLogService;
    @Autowired
    protected OmsCartItemService cartItemService;
    @Autowired
    protected UmsMemberService memberService;
    @Autowired
    protected UmsMemberReceiveAddressService memberReceiveAddressService;
    @Autowired
    protected PmsSkuStockService skuStockService;
    @Autowired
    protected RedisService redisService;
    @Autowired
    protected OmsOrderService orderService;
    @Autowired
    protected OmsOrderSettingService orderSettingService;
    @Autowired
    protected OmsOrderMapper orderMapper;

    @Value("${redis.key.orderId}")
    private String REDIS_KEY_ORDER_ID;
    @Value("${redis.database}")
    private String REDIS_DATABASE;

    @Override
    public IPage<OmsOrder> selectPage(Page<OmsOrder> page, OmsOrderQueryParam queryParam) {
        QueryWrapper<OmsOrder> wrapper = new QueryWrapper<OmsOrder>()
                .eq(StrUtil.isNotBlank(queryParam.getOrderSn()), "order_sn", queryParam.getOrderSn())
                .like(StrUtil.isNotBlank(queryParam.getReceiverNameKeyword()), "receiver_name", queryParam.getReceiverNameKeyword())
                .like(StrUtil.isNotBlank(queryParam.getReceiverPhoneKeyword()), "receiver_phone", queryParam.getReceiverPhoneKeyword())
                .eq(ObjectUtil.isNotNull(queryParam.getStatus()), "status", queryParam.getStatus())
                .like(StrUtil.isNotBlank(queryParam.getCreateTime()), "create_time", queryParam.getCreateTime());
        page.setCurrent(queryParam.getPage());
        page.setSize(queryParam.getPerPage());
        return page(page, wrapper);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        OmsOrder order = new OmsOrder();
        order.setId(id);
        order.setStatus(4); //删除状态为1
        this.updateById(order);
        return true;
    }

    @Override
    public OmsOrderDetailVo detail(Long id) {
        OmsOrderDetailVo detailVo = new OmsOrderDetailVo();
        OmsOrder order = this.getOne(new QueryWrapper<OmsOrder>().eq("id", id).last("limit 1"));
        if (ObjectUtil.isNull(order)) {
            throw new ApiException("订单不存在");
        }
        List<OmsOrderItem> orderItems = orderItemService.list(new QueryWrapper<OmsOrderItem>().eq("order_id", id));
        List<OmsOrderOperateLog> operateLogs = orderOperateLogService.list(new QueryWrapper<OmsOrderOperateLog>()
                .eq("order_id", id)
                .orderByDesc("create_time"));
        BeanUtil.copyProperties(order, detailVo);
        detailVo.setOrderItemList(orderItems);
        detailVo.setHistoryList(operateLogs);
        return detailVo;
    }

    @Override
    @Transactional
    public boolean patch(OmsOrderPatchParam orderPatchParam) {
        OmsOrder order = BeanUtil.copyProperties(orderPatchParam, OmsOrder.class);
        LocalDateTime now = LocalDateTime.now();
        order.setModifyTime(now);
        this.updateById(order);
        //更新后的订单信息
        OmsOrder operatedOrder = this.getById(orderPatchParam.getId());
        //新增修改记录
        OmsOrderOperateLog operateLog = new OmsOrderOperateLog();
        operateLog.setCreateTime(now);
        operateLog.setOrderId(orderPatchParam.getId());
        operateLog.setOperateMan("后台管理员");
        operateLog.setOrderStatus(operatedOrder.getStatus());
        operateLog.setNote(orderPatchParam.getNote());
        orderOperateLogService.save(operateLog);
        return true;
    }

    @Override
    public ConfirmOrderVo generateConfirmOrder(OmsGenerateConfirmOrderParam param) {
        ConfirmOrderVo result = new ConfirmOrderVo();
        UmsMember member = memberService.getCurrentMember();
        if (member == null) {
            throw new ApiException("需要登入");
        }
        //查询用户所有收货地址
        List<UmsMemberReceiveAddress> addresses = memberReceiveAddressService.list(
                new QueryWrapper<UmsMemberReceiveAddress>()
                        .eq("member_id", member.getId()));
        result.setReceiveAddressList(addresses);
        //查询符合条件的cartItem
        List<CartItemDetail> cartItemDetails = orderItemService.list(Arrays.asList(param.getIds()), member.getId());
        result.setCartItemDetailList(cartItemDetails);
        //计算金额
        BigDecimal totalPrice = new BigDecimal(0);
        for (CartItemDetail itemDetails : cartItemDetails) {
            totalPrice = totalPrice.add(itemDetails.getPrice().multiply(new BigDecimal(itemDetails.getQuantity())));
        }
        result.setTotalPrice(totalPrice);
        return result;
    }

    @Override
    public Integer cancelTimeOutOrder() {
        Integer count= 0;
        //获取最后一条setting
        OmsOrderSetting orderSetting = orderSettingService.list(new QueryWrapper<OmsOrderSetting>().orderByDesc("id")).get(0);
        //查询超时、未支付的订单及订单详情
        List<OrderDetails> timeOutOrders = orderMapper.getTimeOutOrders(orderSetting.getUnpaidOvertime());
        if (CollectionUtils.isEmpty(timeOutOrders)) {
            return count;
        }
        //修改订单状态为交易取消
        List<Long> ids = new ArrayList<>();
        for (OrderDetails timeOutOrder : timeOutOrders) {
            ids.add(timeOutOrder.getId());
        }
        orderService.update(new UpdateWrapper<OmsOrder>().in("id", ids).set("status", 4));
        for (OrderDetails timeOutOrder : timeOutOrders) {
            //解除订单商品库存锁定
            orderMapper.releaseSkuStockLock(timeOutOrder.getOrderItemList());
        }
        return timeOutOrders.size();
    }

    @Override
    public void cancelOrder(Long orderId) {
        //指定id且待支付且未删除
        OmsOrder cancelOrder = this.getOne(
                new QueryWrapper<OmsOrder>()
                        .eq("id", orderId)
                        .eq("status", 0)
                        .eq("delete_status", 0));
        if (ObjectUtil.isNull(cancelOrder)) {
            return;
        }
        //修改订单状态为取消
        cancelOrder.setStatus(4);
        this.updateById(cancelOrder);
        List<OmsOrderItem> orderItems = orderItemService.list(new QueryWrapper<OmsOrderItem>().eq("order_id", cancelOrder.getId()));
        //解除订单商品库存锁定
        if (!CollectionUtils.isEmpty(orderItems)) {
            orderMapper.releaseSkuStockLock(orderItems);
        }
    }

    /**
     * 判断下单商品是否都有库存
     */
    protected boolean hasStock(List<CartItemDetail> cartPromotionItemList) {
        for (CartItemDetail cartItemDetail : cartPromotionItemList) {
            if (cartItemDetail.getRealStock() == null || cartItemDetail.getRealStock() <= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 锁定下单商品的所有库存
     */
    protected void lockStock(List<CartItemDetail> cartPromotionItemList) {
        for (CartItemDetail cartItemDetail : cartPromotionItemList) {
            PmsSkuStock skuStock = skuStockService.getById(cartItemDetail.getProductSkuId());
            skuStock.setLockStock(skuStock.getLockStock() + cartItemDetail.getQuantity());
            skuStockService.updateById(skuStock);
        }
    }

    /**
     * 计算订单
     */
    protected BigDecimal calcTotalAmount(List<OmsOrderItem> orderItemList) {
        BigDecimal totalAmount = new BigDecimal("0");
        for (OmsOrderItem item : orderItemList) {
            totalAmount = totalAmount.add(item.getProductPrice().multiply(new BigDecimal(item.getProductQuantity())));
        }
        return totalAmount;
    }

    /**
     * 生成16位订单编号:8位日期+2位支付方式+6位以上自增id
     */
    protected String generateOrderSn(OmsOrder order) {
        StringBuilder sb = new StringBuilder();
        String date = new SimpleDateFormat("yyyyMMdd").format(LocalDateTime.now());
        String key = REDIS_DATABASE+":"+ REDIS_KEY_ORDER_ID + date;
        Long increment = redisService.incr(key, 1);
        sb.append(date);
        sb.append(String.format("%02d", order.getPayType()));
        String incrementStr = increment.toString();
        if (incrementStr.length() <= 6) {
            sb.append(String.format("%06d", increment));
        } else {
            sb.append(incrementStr);
        }
        return sb.toString();
    }

}
