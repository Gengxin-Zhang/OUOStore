package org.csu.ouostore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.csu.ouostore.model.bo.OrderDetails;
import org.csu.ouostore.model.entity.OmsOrder;
import org.csu.ouostore.model.entity.OmsOrderItem;

import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
public interface OmsOrderMapper extends BaseMapper<OmsOrder> {

    /**
     * 获取超时订单
     * @param minute 超时时间（分）
     */
    List<OrderDetails> getTimeOutOrders(@Param("minute") Integer minute);

    /**
     * 解除取消订单的库存锁定
     */
    int releaseSkuStockLock(@Param("itemList") List<OmsOrderItem> orderItemList);

    /**
     * 获取订单及下单商品详情
     */
    OrderDetails getDetail(@Param("orderId") Long orderId);

    /**
     * 扣减库存,更新stock和lockStock
     */
    int updateSkuStock(@Param("itemList") List<OmsOrderItem> orderItemList);

}
