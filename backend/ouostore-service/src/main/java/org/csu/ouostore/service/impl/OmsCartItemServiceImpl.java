package org.csu.ouostore.service.impl;

import org.csu.ouostore.model.entity.OmsCartItem;
import org.csu.ouostore.mapper.OmsCartItemMapper;
import org.csu.ouostore.service.OmsCartItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 购物车表 服务实现类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Service
public class OmsCartItemServiceImpl extends ServiceImpl<OmsCartItemMapper, OmsCartItem> implements OmsCartItemService {

}
