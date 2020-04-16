package org.csu.ouostore.mapper;

import org.apache.ibatis.annotations.Param;
import org.csu.ouostore.model.bo.CartItemDetail;
import org.csu.ouostore.model.entity.OmsCartItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 购物车表 Mapper 接口
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
public interface OmsCartItemMapper extends BaseMapper<OmsCartItem> {

    /**
     * 根据cartItem的id获取详细信息
     */
    List<CartItemDetail> list(@Param("ids") List<String> ids, @Param("member_id")Long memberId);
}
