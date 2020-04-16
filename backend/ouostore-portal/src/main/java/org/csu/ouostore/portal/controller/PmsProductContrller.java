package org.csu.ouostore.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.entity.PmsProduct;
import org.csu.ouostore.model.entity.PmsProductCategory;
import org.csu.ouostore.model.query.PmsProductQueryParam;
import org.csu.ouostore.model.vo.OmsOrderDetailVo;
import org.csu.ouostore.model.vo.PmsProductDetailVo;
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

//    @ApiOperation("商品详情页面")
//    @GetMapping("/{productId}")
//    public CommonResult<PmsProductDetailVo> detail(@PathVariable Long productId){
//        PmsProductDetailVo detail = productService.detail(productId);
//        return CommonResult.OK(detail);
//     }
}
