package org.csu.ouostore.model.bo;

import lombok.Getter;
import lombok.Setter;
import org.csu.ouostore.model.entity.OmsOrder;
import org.csu.ouostore.model.entity.OmsOrderItem;

import java.util.List;

/**
 * 包含订单商品信息的订单详情
 * Created by macro on 2018/9/4.
 */
@Getter
@Setter
public class OrderDetails extends OmsOrder {

    private List<OmsOrderItem> orderItemList;

}
