package org.csu.ouostore.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.entity.PmsSkuStock;
import org.csu.ouostore.service.PmsSkuStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * sku库存Controller
 * @author zack
 */
@RestController
@Api(tags = "sku商品库存管理")
@RequestMapping("/api/v1/sku")
public class PmsSkuStockController {

    @Autowired
    private PmsSkuStockService skuStockService;

    @ApiOperation("根据product_id和sku_code模糊搜索sku库存")
    @GetMapping("/{productId}")
    public CommonResult<List<PmsSkuStock>> getList(
            @PathVariable Long productId,
            @RequestParam(value = "skuCode",required = false) String keyword) {
        QueryWrapper<PmsSkuStock> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id", productId);
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like("sku_code", keyword);
        }
        List<PmsSkuStock> stocks = skuStockService.list(wrapper);
        return CommonResult.OK(stocks);
    }

    @ApiOperation("批量更新库存信息")
    @PutMapping("")
    public CommonResult<String> update(
            @RequestBody List<PmsSkuStock> skuStockList){
        boolean success = skuStockService.updateBatchById(skuStockList);
        return success ? CommonResult.OK("更新成功") : CommonResult.failed("更新失败");
    }

    @ApiOperation("删除库存")
    @DeleteMapping("/{id}")
    public CommonResult<String> update(@PathVariable Long id){
        boolean success = skuStockService.delete(id);
        return success ? CommonResult.OK("更新成功") : CommonResult.failed("更新失败");
    }
}
