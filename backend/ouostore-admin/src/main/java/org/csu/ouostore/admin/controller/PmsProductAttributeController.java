package org.csu.ouostore.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.entity.PmsProductAttribute;
import org.csu.ouostore.model.query.PmsProductAttributeParam;
import org.csu.ouostore.model.vo.ProductAttrInfo;
import org.csu.ouostore.service.PmsProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api(tags = "商品属性管理")
@RequestMapping("/api/v1/product_attrs")
public class PmsProductAttributeController {

    @Autowired
    private PmsProductAttributeService productAttributeService;

    @ApiOperation("根据attr分类查询分类下属性列表或参数列表")
    @GetMapping(value = "/category/{productAttrCategoryId}")
    public CommonResult<IPage<PmsProductAttribute>> getList(
            @PathVariable Long productAttrCategoryId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "per_page", defaultValue = "20") Integer perPage) {
        Page<PmsProductAttribute> productAttributePage = new Page<PmsProductAttribute>().setCurrent(page).setSize(perPage);
        productAttributeService.page(productAttributePage,
                new QueryWrapper<PmsProductAttribute>().eq("product_attribute_category_id", productAttrCategoryId));
        return CommonResult.OK(productAttributePage);
    }

    @ApiOperation("添加商品规格/参数信息")
    @PostMapping(value = "")
    public CommonResult<String> create(@RequestBody @Validated PmsProductAttributeParam productAttributeParam) {
        boolean success = productAttributeService.create(productAttributeParam);
        return success ? CommonResult.OK("创建成功") : CommonResult.failed("创建失败,未知错误");
    }

    @ApiOperation("修改商品规格/参数信息")
    @PutMapping(value = "/{id}")
    public CommonResult<String> update(@PathVariable Long id, @RequestBody @Validated PmsProductAttributeParam productAttributeParam) {
        PmsProductAttribute attribute = BeanUtil.copyProperties(productAttributeParam, PmsProductAttribute.class);
        attribute.setId(id);
        boolean success = productAttributeService.updateById(attribute);
        return success ? CommonResult.OK("修改成功") : CommonResult.failed("修改失败,未知错误");
    }

    @ApiOperation("查询单个商品属性")
    @GetMapping(value = "/{id}")
    public CommonResult<PmsProductAttribute> getItem(@PathVariable Long id) {
        PmsProductAttribute productAttribute = productAttributeService.getById(id);
        return CommonResult.OK(productAttribute);
    }

    @ApiOperation("删除商品属性")
    @DeleteMapping(value = "/{id}")
    public CommonResult<String> delete(@PathVariable Long id) {
        boolean success = productAttributeService.delete(id);
        return success ? CommonResult.OK("删除成功") : CommonResult.failed("删除失败,未知错误");
    }

    @ApiOperation("查询某个product_category下的商品属性分类及商品属性")
    @GetMapping(value = "/attr_info/{productCategoryId}")
    public CommonResult<List<ProductAttrInfo>> getAttrInfo(@PathVariable Long productCategoryId) {
        List<ProductAttrInfo> productAttrInfoList = productAttributeService.getProductAttrInfo(productCategoryId);
        return CommonResult.OK(productAttrInfoList);
    }
}
