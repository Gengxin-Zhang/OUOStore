package org.csu.ouostore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.csu.ouostore.model.entity.OmsCartItem;
import org.csu.ouostore.model.query.OmsCartAddParam;
import org.csu.ouostore.model.vo.OmsCartDetailVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 购物车表 服务类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Transactional
public interface OmsCartItemService extends IService<OmsCartItem> {

    /**
     * 获取购物车详情
     */
    OmsCartDetailVo detail(Long memberId);

    /**
     * 查询购物车中是否包含该商品，有增加数量，无添加到购物车
     */
    boolean add(OmsCartAddParam param);

    /**
     * 修改购物车中商品数量
     */
    boolean updateQuantity(Long cartItemId, Long memberId, Integer quantity);

    /**
     * 修改规格
     */
    boolean updateAttr(Long cartItemId, Long skuId);
}
