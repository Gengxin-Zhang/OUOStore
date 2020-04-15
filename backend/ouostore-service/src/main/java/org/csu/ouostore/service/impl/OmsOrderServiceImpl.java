package org.csu.ouostore.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.csu.ouostore.common.exception.ApiException;
import org.csu.ouostore.mapper.OmsOrderMapper;
import org.csu.ouostore.model.entity.OmsOrder;
import org.csu.ouostore.model.entity.OmsOrderItem;
import org.csu.ouostore.model.entity.OmsOrderOperateLog;
import org.csu.ouostore.model.query.OmsOrderPatchParam;
import org.csu.ouostore.model.query.OmsOrderQueryParam;
import org.csu.ouostore.model.vo.OmsOrderDetailVo;
import org.csu.ouostore.service.OmsOrderItemService;
import org.csu.ouostore.service.OmsOrderOperateLogService;
import org.csu.ouostore.service.OmsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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

    @Autowired
    OmsOrderItemService orderItemService;
    @Autowired
    OmsOrderOperateLogService orderOperateLogService;

    @Override
    public IPage<OmsOrder> selectPage(Page<OmsOrder> page, OmsOrderQueryParam queryParam) {
        QueryWrapper<OmsOrder> wrapper = new QueryWrapper<OmsOrder>()
                .eq(StrUtil.isNotBlank(queryParam.getOrderSn()), "order_sn", queryParam.getOrderSn())
                .like(StrUtil.isNotBlank(queryParam.getReceiverNameKeyword()), "receiver_name", queryParam.getReceiverNameKeyword())
                .like(StrUtil.isNotBlank(queryParam.getReceiverPhoneKeyword()), "receiver_phone", queryParam.getReceiverPhoneKeyword())
                .eq(ObjectUtil.isNotNull(queryParam.getStatus()), "status", queryParam.getStatus())
                .like(StrUtil.isNotBlank(queryParam.getCreateTime()), "create_time", queryParam.getCreateTime());
        page.setCurrent(queryParam.getPage());
        page.setSize(queryParam.getPerPage());
        return page(page, wrapper);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        OmsOrder order = new OmsOrder();
        order.setId(id);
        order.setStatus(1); //删除状态为1
        this.updateById(order);
        return true;
    }

    @Override
    public OmsOrderDetailVo detail(Long id) {
        OmsOrderDetailVo detailVo = new OmsOrderDetailVo();
        OmsOrder order = this.getOne(new QueryWrapper<OmsOrder>().eq("id", id).last("limit 1"));
        if (ObjectUtil.isNull(order)) {
            throw new ApiException("订单不存在");
        }
        List<OmsOrderItem> orderItems = orderItemService.list(new QueryWrapper<OmsOrderItem>().eq("order_id", id));
        List<OmsOrderOperateLog> operateLogs = orderOperateLogService.list(new QueryWrapper<OmsOrderOperateLog>()
                .eq("order_id", id)
                .orderByDesc("create_time"));
        BeanUtil.copyProperties(order, detailVo);
        detailVo.setOrderItemList(orderItems);
        detailVo.setHistoryList(operateLogs);
        return detailVo;
    }

    @Override
    @Transactional
    public boolean patch(OmsOrderPatchParam orderPatchParam) {
        OmsOrder order = BeanUtil.copyProperties(orderPatchParam, OmsOrder.class);
        LocalDateTime now = LocalDateTime.now();
        order.setModifyTime(now);
        this.updateById(order);
        //更新后的订单信息
        OmsOrder operatedOrder = this.getById(orderPatchParam.getId());
        //新增修改记录
        OmsOrderOperateLog operateLog = new OmsOrderOperateLog();
        operateLog.setCreateTime(now);
        operateLog.setOrderId(orderPatchParam.getId());
        operateLog.setOperateMan("后台管理员");
        operateLog.setOrderStatus(operatedOrder.getStatus());
        operateLog.setNote(orderPatchParam.getNote());
        orderOperateLogService.save(operateLog);
        return true;
    }
}
