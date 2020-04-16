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
import org.csu.ouostore.model.query.OmsGenerateOrderParam;
import org.csu.ouostore.model.query.OmsOrderPatchParam;
import org.csu.ouostore.model.query.OmsOrderQueryParam;
import org.csu.ouostore.model.vo.ConfirmOrderVo;
import org.csu.ouostore.model.vo.OmsOrderDetailVo;
import org.csu.ouostore.model.vo.OrderVo;
import org.csu.ouostore.service.*;
import org.csu.ouostore.service.component.CancelOrderSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Service
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder> implements OmsOrderService {

    @Autowired
    OmsOrderItemService orderItemService;
    @Autowired
    OmsOrderOperateLogService orderOperateLogService;
    @Autowired
    OmsCartItemService cartItemService;
    @Autowired
    UmsMemberService memberService;
    @Autowired
    UmsMemberReceiveAddressService memberReceiveAddressService;
    @Autowired
    PmsSkuStockService skuStockService;
    @Autowired
    RedisService redisService;
    @Autowired
    OmsOrderService orderService;
    @Autowired
    OmsOrderSettingService orderSettingService;
    @Autowired
    OmsOrderMapper orderMapper;
    @Autowired
    CancelOrderSender cancelOrderSender;

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
        order.setStatus(1); //删除状态为1
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
    public OrderVo generateOrder(OmsGenerateOrderParam param) {
        List<OmsOrderItem> orderItemList = new ArrayList<>();
        //获取当前用户
        UmsMember member = memberService.getCurrentMember();
        //查询符合条件的cartItem
        List<CartItemDetail> cartItemDetails = orderItemService.list(Arrays.asList(param.getItemIds()), member.getId());
        if (!hasStock(cartItemDetails)) {
            throw new ApiException("库存不足,无法下单");
        }
        for (CartItemDetail cartItemDetail : cartItemDetails) {
            OmsOrderItem orderItem = BeanUtil.copyProperties(cartItemDetail, OmsOrderItem.class);
            orderItem.setProductId(cartItemDetail.getProductId());
            orderItem.setProductName(cartItemDetail.getProductName());
            orderItem.setProductPic(cartItemDetail.getProductPic());
            orderItem.setProductAttr(cartItemDetail.getProductAttr());
            orderItem.setProductPrice(cartItemDetail.getPrice());
            orderItem.setProductQuantity(cartItemDetail.getQuantity());
            orderItem.setProductSkuId(cartItemDetail.getProductSkuId());
            orderItem.setProductSkuCode(cartItemDetail.getProductSkuCode());
            orderItem.setProductCategoryId(cartItemDetail.getProductCategoryId());
            orderItem.setRealAmount(cartItemDetail.getPrice().multiply(new BigDecimal(cartItemDetail.getQuantity())));
            orderItemList.add(orderItem);
        }
        //进行库存锁定
        lockStock(cartItemDetails);
        //生成订单信息
        OmsOrder order = new OmsOrder();
        order.setTotalPrice(calcTotalAmount(orderItemList));
        order.setMemberId(member.getId());
        order.setCreateTime(LocalDateTime.now());
        order.setMemberUsername(member.getUsername());
        //支付方式：0->未支付；1->支付宝；2->微信
        order.setPayType(param.getPayType());
        //订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
        order.setStatus(0);
        //收货人信息：姓名、电话、邮编、地址
        UmsMemberReceiveAddress address = memberReceiveAddressService.getById(param.getAddressId());
        if (ObjectUtil.isNull(address)) {
            throw new ApiException("地址不存在");
        }
        order.setReceiverName(address.getName());
        order.setReceiverPhone(address.getPhoneNumber());
        order.setReceiverPostCode(address.getPostCode());
        order.setReceiverProvince(address.getProvince());
        order.setReceiverCity(address.getCity());
        order.setReceiverRegion(address.getRegion());
        order.setReceiverDetailAddress(address.getDetailAddress());
        //0->未确认；1->已确认
        order.setConfirmStatus(0);
        order.setDeleteStatus(0);
        //生成订单号
        order.setOrderSn(generateOrderSn(order));
        //插入order表和order_item表
        orderService.save(order);
        for (OmsOrderItem orderItem : orderItemList) {
            orderItem.setOrderId(order.getId());
            orderItem.setOrderSn(order.getOrderSn());
        }
        orderItemService.saveBatch(orderItemList);
        //删除购物车中的下单商品
        cartItemService.removeByIds(cartItemDetails.stream().map(CartItemDetail::getId).collect(Collectors.toSet()));
        //发送延迟消息取消订单

        sendDelayMessageCancelOrder(order.getId());
        OrderVo orderVo = new OrderVo();
        orderVo.setOrder(order);
        orderVo.setOmsOrderItems(orderItemList);
        return orderVo;
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
    private boolean hasStock(List<CartItemDetail> cartPromotionItemList) {
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
    private void lockStock(List<CartItemDetail> cartPromotionItemList) {
        for (CartItemDetail cartItemDetail : cartPromotionItemList) {
            PmsSkuStock skuStock = skuStockService.getById(cartItemDetail.getProductSkuId());
            skuStock.setLockStock(skuStock.getLockStock() + cartItemDetail.getQuantity());
            skuStockService.updateById(skuStock);
        }
    }

    /**
     * 计算订单
     */
    private BigDecimal calcTotalAmount(List<OmsOrderItem> orderItemList) {
        BigDecimal totalAmount = new BigDecimal("0");
        for (OmsOrderItem item : orderItemList) {
            totalAmount = totalAmount.add(item.getProductPrice().multiply(new BigDecimal(item.getProductQuantity())));
        }
        return totalAmount;
    }

    /**
     * 生成16位订单编号:8位日期+2位支付方式+6位以上自增id
     */
    private String generateOrderSn(OmsOrder order) {
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

    private void sendDelayMessageCancelOrder(Long orderId) {
        //获取订单超时时间
        OmsOrderSetting orderSetting = orderSettingService.list(new QueryWrapper<OmsOrderSetting>().orderByDesc("id")).get(0);
        long delayTimes = orderSetting.getUnpaidOvertime() * 60 * 1000;
        //发送延迟消息
        cancelOrderSender.sendMessage(orderId, delayTimes);
    }

}
