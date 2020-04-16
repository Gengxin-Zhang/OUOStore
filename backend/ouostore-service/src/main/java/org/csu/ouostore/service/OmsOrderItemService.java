package org.csu.ouostore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.csu.ouostore.model.bo.CartItemDetail;
import org.csu.ouostore.model.entity.OmsOrderItem;

import java.util.List;

/**
 * <p>
 * 订单中所包含的商品 服务类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
public interface OmsOrderItemService extends IService<OmsOrderItem> {

    /**
     * 查询cartItem详细信息
     */
    List<CartItemDetail> list(List<String> ids, Long memberId);
}
