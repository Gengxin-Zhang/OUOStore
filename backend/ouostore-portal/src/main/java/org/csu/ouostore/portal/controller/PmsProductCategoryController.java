package org.csu.ouostore.portal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.entity.PmsProductCategory;
import org.csu.ouostore.service.PmsProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品目录
 */
@RestController
@Api(tags = "前台商品目录展示")
@RequestMapping("/api/v1/product_categories")
public class PmsProductCategoryController {

    @Autowired
    private PmsProductCategoryService productCategoryService;

    @ApiOperation("返回商品目录列表")
    @GetMapping("")
    public CommonResult<List<PmsProductCategory>> getCategory(){
        List<PmsProductCategory> list = productCategoryService.categoryList();
        return CommonResult.OK(list);
    }
}
