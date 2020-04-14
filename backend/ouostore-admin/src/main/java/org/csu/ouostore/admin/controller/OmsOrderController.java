package org.csu.ouostore.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.entity.OmsOrder;
import org.csu.ouostore.model.query.OmsOrderPatchParam;
import org.csu.ouostore.model.query.OmsOrderQueryParam;
import org.csu.ouostore.model.vo.OmsOrderDetailVo;
import org.csu.ouostore.service.OmsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单管理
 */
@RestController
@Api(tags = "订单管理")
@RequestMapping("/api/v1/orders")
public class OmsOrderController {
    
    @Autowired
    private OmsOrderService orderService;

    @ApiOperation("分页模糊查询订单")
    @GetMapping("")
    public CommonResult<Page<OmsOrder>> query(OmsOrderQueryParam queryParam) {
        Page<OmsOrder> page = new Page<>();
        orderService.selectPage(page, queryParam);
        return CommonResult.OK(page);
    }

    @ApiOperation("删除订单")
    @DeleteMapping("/{orderId}")
    public CommonResult delete(@PathVariable Long orderId) {
        boolean success = orderService.delete(orderId);
        return success ? CommonResult.OK("删除成功") : CommonResult.failed("删除失败");
    }

    @ApiOperation("获取订单详情:订单信息、商品信息、操作记录")
    @GetMapping("/{orderId}")
    public CommonResult<OmsOrderDetailVo> detail(@PathVariable Long orderId) {
        OmsOrderDetailVo detail = orderService.detail(orderId);
        return CommonResult.OK(detail);
    }

    @ApiOperation("修改订单部分信息")
    @PatchMapping("/{orderId}")
    public CommonResult updateReceiverInfo(@PathVariable Long orderId, @RequestBody OmsOrderPatchParam orderPatchParam) {
        orderPatchParam.setId(orderId);
        boolean success = orderService.patch(orderPatchParam);
        return success ? CommonResult.OK("更新成功") : CommonResult.failed("更新失败,未知错误");
    }

}
