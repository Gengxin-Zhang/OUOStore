package org.csu.ouostore.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.entity.PmsProductCategory;
import org.csu.ouostore.model.query.PmsProductCategoryParam;
import org.csu.ouostore.service.PmsProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 后台商品分类管理
 * @author zack
 */
@RestController
@Api(tags = "商品分类管理")
@RequestMapping("/api/v1/product_categories")
public class PmsProductCategoryController {

    @Autowired
    private PmsProductCategoryService productCategoryService;

    @ApiOperation("添加产品分类")
    @PostMapping("")
    public CommonResult<String> create(@Validated @RequestBody PmsProductCategoryParam productCategoryParam) {
        PmsProductCategory category = productCategoryService.getOne(
                new QueryWrapper<PmsProductCategory>()
                        .eq("name", productCategoryParam.getName()).last("LIMIT 1"));
        if (ObjectUtil.isNotNull(category)) {
            return CommonResult.failed("分类名重复,创建失败");
        }
        PmsProductCategory category1 = BeanUtil.copyProperties(productCategoryParam, PmsProductCategory.class);
        category1.setProductCount(0);
        boolean success = productCategoryService.save(category1);
        return success ? CommonResult.OK("创建成功") : CommonResult.failed("创建失败,未知错误");
    }

    @ApiOperation("修改商品分类")
    @PutMapping("/{id}")
    public CommonResult<String> update(@PathVariable Long id,
                         @Validated @RequestBody PmsProductCategoryParam productCategoryParam) {
        PmsProductCategory category = BeanUtil.copyProperties(productCategoryParam, PmsProductCategory.class);
        category.setId(id);
        boolean success = productCategoryService.updateById(category);
        return success ? CommonResult.OK("修改成功") : CommonResult.failed("修改失败,分类不存在");
    }

    @ApiOperation("分页查询商品分类")
    @GetMapping(value = "")
    public CommonResult<IPage<PmsProductCategory>> getList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "per_page", defaultValue = "20") Integer perPage) {
        Page<PmsProductCategory> productCategoryPage = new Page<PmsProductCategory>().setCurrent(page).setSize(perPage);
        productCategoryService.page(productCategoryPage);
        return CommonResult.OK(productCategoryPage);
    }

    @ApiOperation("根据id获取商品分类")
    @GetMapping("/{id}")
    public CommonResult<PmsProductCategory> getItem(@PathVariable Long id) {
        PmsProductCategory productCategory = productCategoryService.getById(id);
        return CommonResult.OK(productCategory);
    }

    @ApiOperation("删除商品分类")
    @DeleteMapping(value = "/{id}")
    public CommonResult<String> delete(@PathVariable Long id) {
        boolean success = productCategoryService.removeById(id);
        return success ? CommonResult.OK("删除成功") : CommonResult.failed("删除失败,未知错误");
    }

}
