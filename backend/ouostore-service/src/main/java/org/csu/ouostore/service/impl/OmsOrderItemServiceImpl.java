package org.csu.ouostore.service.impl;

import org.csu.ouostore.mapper.OmsCartItemMapper;
import org.csu.ouostore.model.bo.CartItemDetail;
import org.csu.ouostore.model.entity.OmsOrderItem;
import org.csu.ouostore.mapper.OmsOrderItemMapper;
import org.csu.ouostore.service.OmsOrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单中所包含的商品 服务实现类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Service
public class OmsOrderItemServiceImpl extends ServiceImpl<OmsOrderItemMapper, OmsOrderItem> implements OmsOrderItemService {

    @Autowired
    OmsCartItemMapper cartItemMapper;

    public List<CartItemDetail> list(List<String> ids, Long memberId) {
        return cartItemMapper.list(ids, memberId);
    }
}
