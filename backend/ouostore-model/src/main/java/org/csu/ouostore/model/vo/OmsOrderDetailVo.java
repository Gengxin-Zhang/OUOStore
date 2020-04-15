package org.csu.ouostore.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.csu.ouostore.model.entity.OmsOrder;
import org.csu.ouostore.model.entity.OmsOrderItem;
import org.csu.ouostore.model.entity.OmsOrderOperateLog;

import java.util.List;

/**
 * 订单详情信息
 * Created by macro on 2018/10/11.
 */
@Data
@ApiModel
public class OmsOrderDetailVo extends OmsOrder {

    @ApiModelProperty("订单内商品列表")
    private List<OmsOrderItem> orderItemList;

    @ApiModelProperty("订单操作记录")
    private List<OmsOrderOperateLog> historyList;
}
