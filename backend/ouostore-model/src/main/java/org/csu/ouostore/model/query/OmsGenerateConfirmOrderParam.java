package org.csu.ouostore.model.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 订单查询参数
 */
@Getter
@Setter
public class OmsGenerateConfirmOrderParam {

    @ApiModelProperty(value = "购物车内item的id,默认全部")
    private String[] ids;

}
