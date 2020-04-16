package org.csu.ouostore.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.csu.ouostore.model.bo.CartItemDetail;
import org.csu.ouostore.model.entity.UmsMemberReceiveAddress;

import java.math.BigDecimal;
import java.util.List;

/**
 * 确认单信息
 */
@ApiModel
@Data
public class ConfirmOrderVo {

    @ApiModelProperty("购物车商品详细信息")
    private List<CartItemDetail> cartItemDetailList;

    @ApiModelProperty("用户所有收货地址")
    private List<UmsMemberReceiveAddress> receiveAddressList;

    @ApiModelProperty("订单总金额")
    private BigDecimal totalPrice;

}
