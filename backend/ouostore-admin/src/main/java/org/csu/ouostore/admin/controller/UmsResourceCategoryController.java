package org.csu.ouostore.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.entity.UmsResource;
import org.csu.ouostore.model.entity.UmsResourceCategory;
import org.csu.ouostore.model.query.UmsResourceCategoryCreateParam;
import org.csu.ouostore.service.UmsResourceCategoryService;
import org.csu.ouostore.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台资源分类管理
 */
@RestController
@Api(tags = "后台资源分类管理")
@RequestMapping("/api/v1/resource_categories")
public class UmsResourceCategoryController {

    @Autowired
    private UmsResourceCategoryService resourceCategoryService;
    @Autowired
    private UmsResourceService resourceService;

    @ApiOperation("新增一个资源分类")
    @PostMapping("")
    public CommonResult create(@RequestBody @Validated UmsResourceCategoryCreateParam categoryCreateParam) {
        boolean success =  resourceCategoryService.create(categoryCreateParam);
        return success ? CommonResult.OK("创建成功") : CommonResult.failed("创建失败,未知错误");
    }

    @ApiOperation("查询所有后台资源分类")
    @GetMapping("")
    public CommonResult<List<UmsResourceCategory>> queryAll() {
        return CommonResult.OK(resourceCategoryService.list());
    }

    @ApiOperation("更新后台资源分类")
    @PutMapping("/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody @Validated UmsResourceCategoryCreateParam resourceCategoryCreateParam) {
        UmsResourceCategory resourceCategory = new UmsResourceCategory();
        BeanUtil.copyProperties(resourceCategoryCreateParam, resourceCategory);
        resourceCategory.setId(id);
        boolean success = resourceCategoryService.updateById(resourceCategory);
        return success ? CommonResult.OK("更新成功") : CommonResult.failed("id不存在");
    }

    @ApiOperation("根据ID删除后台资源分类")
    @DeleteMapping("/{id}")
    public CommonResult delete(@PathVariable Long id) {
        resourceCategoryService.removeById(id);
        resourceService.update(new UpdateWrapper<UmsResource>().eq("category_id", id).set("category_id", null));
        return CommonResult.OK("删除成功");
    }

}
