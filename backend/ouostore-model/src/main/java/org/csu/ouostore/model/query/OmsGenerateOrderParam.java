package org.csu.ouostore.model.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 订单查询参数
 */
@Getter
@Setter
public class OmsGenerateOrderParam {

    @ApiModelProperty(value = "购物车内item的id,默认全部")
    private String[] itemIds;

    @ApiModelProperty(value = "会员使用的收货地址id", required = true)
    @NotNull(message = "地址Id不能为空")
    private Long addressId ;

    @ApiModelProperty(value = "支付方式,1->支付宝；2->微信", required = true)
    @NotNull(message = "支付方式不能为空")
    private Integer payType;

}
