package org.csu.ouostore.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.entity.PmsProduct;
import org.csu.ouostore.model.entity.PmsProductAttributeValue;
import org.csu.ouostore.model.entity.PmsSkuStock;
import org.csu.ouostore.model.query.PmsProductParam;
import org.csu.ouostore.service.PmsProductAttributeValueService;
import org.csu.ouostore.service.PmsProductService;
import org.csu.ouostore.service.PmsSkuStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品管理
 * @author zack
 */
@RestController
@Api(tags = "商品管理")
@RequestMapping("/api/v1/products")
public class PmsProductController {

    @Autowired
    private PmsProductService productService;
    @Autowired
    private PmsSkuStockService skuStockService;
    @Autowired
    private PmsProductAttributeValueService productAttributeValueService;

    @ApiOperation("创建商品")
    @PostMapping(value = "")
    public CommonResult<String> create(@RequestBody PmsProductParam productParam) {
        boolean success = productService.create(productParam);
        return success ? CommonResult.OK("创建成功") : CommonResult.failed("创建失败,未知错误");
    }

    @ApiOperation("根据商品id获取商品所有详细信息")
    @GetMapping(value = "/{id}")
    @ResponseBody
    public CommonResult<PmsProductParam> getUpdateInfo(@PathVariable Long id) {
        PmsProduct product = productService.getById(id);
        List<PmsSkuStock> skuStocks = skuStockService.list(new QueryWrapper<PmsSkuStock>().eq("product_id", id));
        List<PmsProductAttributeValue> productAttributeValues = productAttributeValueService.list(
                new QueryWrapper<PmsProductAttributeValue>().eq("product_id", id));
        PmsProductParam productParam = BeanUtil.copyProperties(product, PmsProductParam.class);
        productParam.setSkuStockList(skuStocks);
        productParam.setProductAttributeValueList(productAttributeValues);
        return CommonResult.OK(productParam);
    }

    @ApiOperation("更新商品")
    @PutMapping(value = "/{id}")
    public CommonResult<String> update(@PathVariable Long id, @RequestBody PmsProductParam productParam) {
        boolean success = productService.update1(id, productParam);
        return success ? CommonResult.OK("更新成功") : CommonResult.failed("更新失败,未知错误");
    }

    @ApiOperation("根据商品名称或product_sn模糊分页查询商品")
    @GetMapping(value = "")
    public CommonResult<IPage<PmsProduct>> getList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "per_page", defaultValue = "20") Integer perPage,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "product_sn", required = false) String productSn) {
        Page<PmsProduct> page1 = new Page<PmsProduct>().setCurrent(page).setSize(perPage);
        QueryWrapper<PmsProduct> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            wrapper.like("name", name);
        }
        if (StrUtil.isNotBlank(productSn)) {
            wrapper.like("product_sn", productSn);
        }
        productService.page(page1, wrapper);
        return CommonResult.OK(page1);
    }

    @ApiOperation("批量上下架")
    @PutMapping(value = "/publish_status")
    public CommonResult<String> updatePublishStatus(
            @RequestParam("ids") List<Long> ids,
            @RequestParam("publishStatus") Integer publishStatus) {
        List<PmsProduct> products = productService.list(new QueryWrapper<PmsProduct>().in("id", ids));
        for (PmsProduct product : products) {
            product.setPublishStatus(publishStatus);
        }
        boolean success = productService.updateBatchById(products);
        return success ? CommonResult.OK("修改成功") : CommonResult.failed("修改失败,未知错误");
    }

    @ApiOperation("批量修改删除状态")
    @PutMapping(value = "/delete_status")
    public CommonResult<String> updateDeleteStatus(
            @RequestParam("ids") List<Long> ids,
            @RequestParam("deleteStatus") Integer deleteStatus) {
        List<PmsProduct> products = productService.list(new QueryWrapper<PmsProduct>().in("id", ids));
        for (PmsProduct product : products) {
            product.setDeleteStatus(deleteStatus);
        }
        boolean success = productService.updateBatchById(products);
        return success ? CommonResult.OK("修改成功") : CommonResult.failed("修改失败,未知错误");
    }
}
