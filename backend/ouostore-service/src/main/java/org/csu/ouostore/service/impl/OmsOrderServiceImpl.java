package org.csu.ouostore.service.impl;

import org.csu.ouostore.model.entity.OmsOrder;
import org.csu.ouostore.mapper.OmsOrderMapper;
import org.csu.ouostore.service.OmsOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Service
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder> implements OmsOrderService {

}
