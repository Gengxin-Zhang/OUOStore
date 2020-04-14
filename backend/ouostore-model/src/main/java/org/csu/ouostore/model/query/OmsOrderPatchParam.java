package org.csu.ouostore.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @Description: 订单更新参数
 * @Author: Guanyu-Cai
 * @Date: 4/14/20
 */
@Data
@ApiModel(description = "订单更新参数")
public class OmsOrderPatchParam {

    @ApiModelProperty(value = "订单id", hidden = true)
    private Long id;

    @ApiModelProperty(value = "订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单")
    private Integer status;

    @ApiModelProperty(value = "物流公司(顺丰快递/申通快递/...)")
    private String deliveryCompany;

    @ApiModelProperty(value = "物流单号")
    private String deliverySn;

    @ApiModelProperty(value = "自动确认收货时间（天）")
    private Integer autoConfirmDay;

    @ApiModelProperty(value = "收货人姓名")
    private String receiverName;

    @ApiModelProperty(value = "收货人电话")
    private String receiverPhone;

    @ApiModelProperty(value = "收货人邮编")
    private String receiverPostCode;

    @ApiModelProperty(value = "省份/直辖市")
    private String receiverProvince;

    @ApiModelProperty(value = "城市")
    private String receiverCity;

    @ApiModelProperty(value = "区")
    private String receiverRegion;

    @ApiModelProperty(value = "详细地址")
    private String receiverDetailAddress;

    @ApiModelProperty(value = "删除状态：0->未删除；1->已删除")
    private Integer deleteStatus;

    @ApiModelProperty(value = "订单修改备注", example = "会员要求修改收货人信息")
    private String note;
}
