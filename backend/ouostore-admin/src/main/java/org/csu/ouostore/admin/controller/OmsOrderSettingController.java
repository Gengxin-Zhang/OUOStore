package org.csu.ouostore.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.entity.OmsOrderSetting;
import org.csu.ouostore.model.query.OmsOrderSettingCreateParam;
import org.csu.ouostore.model.query.OmsOrderSettingPatchParam;
import org.csu.ouostore.service.OmsOrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 订单基础设置
 */
@RestController
@Api(tags = "订单基础设置管理")
@RequestMapping("/api/v1/order_settings")
public class OmsOrderSettingController {

    @Autowired
    private OmsOrderSettingService orderSettingService;

    @ApiOperation("创建订单基础设置")
    @PostMapping()
    public CommonResult create(@RequestBody @Validated OmsOrderSettingCreateParam param) {
        OmsOrderSetting omsOrderSetting = BeanUtil.copyProperties(param, OmsOrderSetting.class);
        boolean success = orderSettingService.save(omsOrderSetting);
        return success ? CommonResult.OK("创建成功") : CommonResult.failed("创建失败,未知错误");
    }

    @ApiOperation("获取订单基础设置")
    @GetMapping("/{id}")
    public CommonResult<OmsOrderSetting> getItem(@PathVariable Long id) {
        OmsOrderSetting orderSetting = orderSettingService.getById(id);
        return CommonResult.OK(orderSetting);
    }

    @ApiOperation("删除订单基础设置")
    @DeleteMapping("/{id}")
    public CommonResult delete(@PathVariable Long id) {
        boolean success = orderSettingService.removeById(id);
        return success ? CommonResult.OK("删除成功") : CommonResult.failed("删除失败,未知错误");
    }

    @ApiOperation("更新订单基础设置")
    @PutMapping("/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody OmsOrderSettingPatchParam param) {
        OmsOrderSetting orderSetting = BeanUtil.copyProperties(param, OmsOrderSetting.class);
        orderSetting.setId(id);
        boolean success = orderSettingService.updateById(orderSetting);
        return success ? CommonResult.OK("更新成功") : CommonResult.failed("id不存在");
    }

}
