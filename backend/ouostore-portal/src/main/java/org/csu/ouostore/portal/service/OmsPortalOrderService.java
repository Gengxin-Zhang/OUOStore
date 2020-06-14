package org.csu.ouostore.portal.service;

import org.csu.ouostore.model.query.OmsGenerateOrderParam;
import org.csu.ouostore.model.vo.OrderVo;
import org.csu.ouostore.service.OmsOrderService;
import org.springframework.transaction.annotation.Transactional;

public interface OmsPortalOrderService extends OmsOrderService {

    /**
     * 生成订单
     */
    @Transactional
    OrderVo generateOrder(OmsGenerateOrderParam param);

}
