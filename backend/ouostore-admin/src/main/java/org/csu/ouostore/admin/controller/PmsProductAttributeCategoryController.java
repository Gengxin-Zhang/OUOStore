package org.csu.ouostore.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.entity.PmsProductAttribute;
import org.csu.ouostore.model.entity.PmsProductAttributeCategory;
import org.csu.ouostore.model.query.PmsProductAttributeCategoryParam;
import org.csu.ouostore.model.vo.PmsProductAttributeCategoryItem;
import org.csu.ouostore.service.PmsProductAttributeCategoryService;
import org.csu.ouostore.service.PmsProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品属性分类
 * @author zack
 */
@RestController
@Api(tags = "商品属性分类管理")
@RequestMapping("/api/v1/product_attr_categories")
public class PmsProductAttributeCategoryController {

    @Autowired
    private PmsProductAttributeCategoryService productAttributeCategoryService;
    @Autowired
    private PmsProductAttributeService productAttributeService;

    @ApiOperation("添加商品属性分类")
    @PostMapping(value = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", name = "name", value = "分类名"),
    })
    public CommonResult<String> create(@RequestBody @Validated PmsProductAttributeCategoryParam param) {
        PmsProductAttributeCategory category = new PmsProductAttributeCategory();
        category.setName(param.getName());
        boolean success = productAttributeCategoryService.save(category);
        return success ? CommonResult.OK("创建成功") : CommonResult.failed("创建失败,未知错误");
    }

    @ApiOperation("修改商品属性分类")
    @PutMapping(value = "/{id}")
    public CommonResult<String> update(@PathVariable Long id, @RequestBody @Validated PmsProductAttributeCategoryParam param) {
        PmsProductAttributeCategory category = new PmsProductAttributeCategory();
        category.setName(param.getName());
        category.setId(id);
        boolean success = productAttributeCategoryService.updateById(category);
        return success ? CommonResult.OK("修改成功") : CommonResult.failed("修改失败,未知错误");
    }

    @ApiOperation("删除单个商品属性分类")
    @DeleteMapping(value = "/{id}")
    public CommonResult<String> delete(@PathVariable Long id) {
        boolean success = productAttributeCategoryService.removeById(id);
        return success ? CommonResult.OK("删除成功") : CommonResult.failed("删除失败,未知错误");
    }

    @ApiOperation("获取单个商品属性分类及其下属性和规格信息")
    @GetMapping(value = "/{id}")
    public CommonResult<PmsProductAttributeCategoryItem> getItem(@PathVariable Long id) {
        PmsProductAttributeCategory category = productAttributeCategoryService.getById(id);
        List<PmsProductAttribute> attributes = productAttributeService.list(
                new QueryWrapper<PmsProductAttribute>().eq("product_attribute_category_id", id)
        );
        PmsProductAttributeCategoryItem result = BeanUtil.copyProperties(category, PmsProductAttributeCategoryItem.class);
        result.setProductAttributeList(attributes);
        return CommonResult.OK(result);
    }

    @ApiOperation("分页获取商品属性分类")
    @GetMapping(value = "")
    public CommonResult<IPage<PmsProductAttributeCategory>> getList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "per_page", defaultValue = "20") Integer perPage) {
        Page<PmsProductAttributeCategory> categoryPage = new Page<PmsProductAttributeCategory>().setCurrent(page).setSize(perPage);
        productAttributeCategoryService.page(categoryPage);
        return CommonResult.OK(categoryPage);
    }
}
