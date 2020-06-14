package org.csu.ouostore.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.csu.ouostore.model.entity.OmsOrder;
import org.csu.ouostore.model.query.OmsGenerateConfirmOrderParam;
import org.csu.ouostore.model.query.OmsOrderPatchParam;
import org.csu.ouostore.model.query.OmsOrderQueryParam;
import org.csu.ouostore.model.vo.ConfirmOrderVo;
import org.csu.ouostore.model.vo.OmsOrderDetailVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
public interface OmsOrderService extends IService<OmsOrder> {

    /**
     * 分页模糊查询
     */
    IPage<OmsOrder> selectPage(Page<OmsOrder> page, OmsOrderQueryParam queryParam);

    /**
     * 删除订单及订单内的orderItem
     */
    boolean delete(Long id);

    /**
     * 获取订单详情，包括订单信息，订单内商品和订单操作记录
     */
    OmsOrderDetailVo detail(Long id);

    /**
     * 更新订单
     */
    boolean patch(OmsOrderPatchParam orderPatchParam);

    /**
     * 生成确认单
     */
    @Transactional
    ConfirmOrderVo generateConfirmOrder(OmsGenerateConfirmOrderParam param);

    /**
     * 取消超时订单
     * @return 取消的订单数
     */
    Integer cancelTimeOutOrder();

    /**
     * 取消单个超时订单
     */
    @Transactional
    void cancelOrder(Long orderId);

}
