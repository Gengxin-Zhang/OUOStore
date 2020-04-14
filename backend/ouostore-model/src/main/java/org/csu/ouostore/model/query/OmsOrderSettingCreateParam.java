package org.csu.ouostore.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
* @Description: 订单设置创建参数
* @Author: Guanyu-Cai
* @Date: 4/14/20
*/
@Data
@ApiModel(description="订单设置创建参数")
public class OmsOrderSettingCreateParam {

    @ApiModelProperty(value = "未支付订单超时时间(分)", example = "30", required = true)
    @NotNull(message = "未支付订单超时时间不能为空")
    private Integer unpaidOvertime;

    @ApiModelProperty(value = "发货后自动确认收货时间（天）", example = "7", required = true)
    @NotNull(message = "发货后自动确认收货时间不能为空")
    private Integer confirmOvertime;

    @ApiModelProperty(value = "收货后自动完成交易时间，不能申请售后（天）", example = "7", required = true)
    @NotNull(message = "收货后不能申请售后时间不能为空")
    private Integer finishOvertime;


}
