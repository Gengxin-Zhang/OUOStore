package org.csu.ouostore.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
* @Description: 订单设置更新参数
* @Author: Guanyu-Cai
* @Date: 4/14/20
*/
@Data
@ApiModel(description="订单设置参数")
public class OmsOrderSettingPatchParam {

    @ApiModelProperty(hidden = true)
    private Long id;

    @ApiModelProperty(value = "未支付订单超时时间(分)", example = "30")
    private Integer unpaidOvertime;

    @ApiModelProperty(value = "发货后自动确认收货时间（天）", example = "7")
    private Integer confirmOvertime;

    @ApiModelProperty(value = "收货后自动完成交易时间，不能申请售后（天）", example = "7")
    private Integer finishOvertime;


}
