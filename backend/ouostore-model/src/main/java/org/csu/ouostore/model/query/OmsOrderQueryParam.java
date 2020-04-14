package org.csu.ouostore.model.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 订单查询参数
 * Created by macro on 2018/10/11.
 */
@Getter
@Setter
public class OmsOrderQueryParam {

    @ApiModelProperty(value = "订单编号")
    private String orderSn;

    @ApiModelProperty(value = "收货人姓名关键词", example = "zack")
    private String receiverNameKeyword;

    @ApiModelProperty(value = "收货人电话关键词", example = "zack")
    private String receiverPhoneKeyword;

    @ApiModelProperty(value = "订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单", example = "0")
    private Integer status;

    @ApiModelProperty(value = "订单提交时间", example = "2020-04-13")
    private String createTime;

    @ApiModelProperty(value = "指定第几页,默认1", example = "1")
    private Long page = 0L;

    @ApiModelProperty(value = "指定每页条数,默认20", example = "20")
    private Long perPage = 20L;
}
