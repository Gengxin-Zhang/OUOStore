package org.csu.ouostore.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.csu.ouostore.model.entity.OmsOrder;
import org.csu.ouostore.model.entity.OmsOrderItem;

import java.util.List;

/**
 * 订单信息
 */
@ApiModel
@Data
public class OrderVo {

    @ApiModelProperty("订单信息")
    private OmsOrder order;

    @ApiModelProperty("订单内商品信息")
    private List<OmsOrderItem> omsOrderItems;

}
