package org.csu.ouostore.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.query.UmsResourceCategoryCreateParam;
import org.csu.ouostore.service.UmsResourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台资源分类管理
 */
@RestController
@Api(tags = "后台资源分类管理")
@RequestMapping("/api/v1/resource_categories")
public class UmsResourceCategoryController {

    @Autowired
    private UmsResourceCategoryService resourceCategoryService;

    @ApiOperation("新增一个资源分类")
    @PostMapping("")
    public CommonResult create(@RequestBody UmsResourceCategoryCreateParam categoryCreateParam) {
        boolean success =  resourceCategoryService.create(categoryCreateParam);
        return success ? CommonResult.OK("创建成功") : CommonResult.failed("创建失败,未知错误");
    }

}
