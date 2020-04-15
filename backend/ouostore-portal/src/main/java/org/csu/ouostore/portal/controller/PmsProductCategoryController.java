package org.csu.ouostore.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.entity.PmsProductCategory;
import org.csu.ouostore.model.query.PmsProductCategoryQueryParam;
import org.csu.ouostore.model.query.PmsProductQueryParam;
import org.csu.ouostore.service.PmsProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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

    @ApiOperation("分页返回商品目录列表")
    @GetMapping("")
    public CommonResult<Page<PmsProductCategory>> getCategory(PmsProductCategoryQueryParam queryParam){
        Page<PmsProductCategory> page = new Page<>();
       productCategoryService.categoryListIPage(page,queryParam);
        return CommonResult.OK(page);
    }
}
