package org.csu.ouostore.service;

import org.csu.ouostore.model.entity.OmsCartItem;
import com.baomidou.mybatisplus.extension.service.IService;
import org.csu.ouostore.model.vo.OmsCartDetailVo;

import java.util.List;

/**
 * <p>
 * 购物车表 服务类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
public interface OmsCartItemService extends IService<OmsCartItem> {

    /**
     * 获取购物车详情
     */
    OmsCartDetailVo detail(Long memberId);
}
