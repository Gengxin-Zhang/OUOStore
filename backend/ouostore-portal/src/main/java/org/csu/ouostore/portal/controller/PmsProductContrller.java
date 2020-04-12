package org.csu.ouostore.portal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.entity.PmsProduct;
import org.csu.ouostore.service.PmsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品列表
 */
@RestController
@Api(tags = "商品展示")
@RequestMapping("/api/v1/products")
public class PmsProductContrller {

    @Autowired
    PmsProductService productService;

    @ApiOperation("返回商品目录列表")
    @GetMapping("")
    public CommonResult<List<PmsProduct>> getProducts(){
        List<PmsProduct> list = productService.productList();
        return CommonResult.OK(list);
    }

    @ApiOperation("根据商品名查找商品")
    @GetMapping("/{keyword}")
    public CommonResult<List<PmsProduct>> search(@PathVariable String keyword){

    }

    @ApiOperation("根据商品id查找商品")
    @GetMapping("/{id}")
    public CommonResult<List<PmsProduct>> search(@PathVariable Long id){

    }


}
