package org.csu.ouostore.portal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.ouostore.common.exception.ApiException;
import org.csu.ouostore.model.bo.CartItemDetail;
import org.csu.ouostore.model.entity.*;
import org.csu.ouostore.model.query.OmsGenerateOrderParam;
import org.csu.ouostore.model.vo.OrderVo;
import org.csu.ouostore.portal.service.OmsPortalOrderService;
import org.csu.ouostore.portal.service.component.CancelOrderSender;
import org.csu.ouostore.service.impl.OmsOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OmsPortalOrderServiceImpl extends OmsOrderServiceImpl implements OmsPortalOrderService {

    @Autowired
    private CancelOrderSender cancelOrderSender;

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

    private void sendDelayMessageCancelOrder(Long orderId) {
        //获取订单超时时间
        OmsOrderSetting orderSetting = orderSettingService.list(new QueryWrapper<OmsOrderSetting>().orderByDesc("id")).get(0);
        long delayTimes = orderSetting.getUnpaidOvertime() * 60 * 1000;
        //发送延迟消息
        cancelOrderSender.sendMessage(orderId, delayTimes);
    }
}
