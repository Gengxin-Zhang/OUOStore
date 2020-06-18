package org.csu.ouostore.portal.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.entity.PmsProduct;
import org.csu.ouostore.model.entity.PmsProductAttributeValue;
import org.csu.ouostore.model.entity.PmsSkuStock;
import org.csu.ouostore.model.query.PmsProductParam;
import org.csu.ouostore.model.query.PmsProductQueryParam;
import org.csu.ouostore.service.PmsProductAttributeValueService;
import org.csu.ouostore.service.PmsProductService;
import org.csu.ouostore.service.PmsSkuStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品列表
 */
@RestController
@Api(tags = "商品展示")
@RequestMapping("/api/v1/products")
public class PmsProductContrller {

    @Autowired
    private PmsProductService productService;
    @Autowired
    private PmsSkuStockService skuStockService;
    @Autowired
    private PmsProductAttributeValueService productAttributeValueService;

    @ApiOperation("分页返回商品列表")
    @GetMapping("")
    public CommonResult<Page<PmsProduct>> getProducts(PmsProductQueryParam queryParam){
        Page<PmsProduct> page = new Page<>();
        productService.productListIpage(page, queryParam);
        return CommonResult.OK(page);
    }

    @ApiOperation("分页模糊查找商品")
    @GetMapping("/{keyword}")
    public CommonResult<Page<PmsProduct>> search(@PathVariable String keyword,PmsProductQueryParam queryParam){
        Page<PmsProduct> page = new Page<>();
        queryParam.setProductKeyword(keyword);
        productService.select(page,queryParam);
        return CommonResult.OK(page);
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
}
