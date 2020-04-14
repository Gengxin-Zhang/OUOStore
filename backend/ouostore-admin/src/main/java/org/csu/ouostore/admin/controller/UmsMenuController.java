package org.csu.ouostore.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.entity.UmsMenu;
import org.csu.ouostore.model.query.UmsMenuCreateParam;
import org.csu.ouostore.model.query.UmsMenuPatchParam;
import org.csu.ouostore.model.query.UmsMenuQueryParam;
import org.csu.ouostore.model.vo.UmsMenuNodeVo;
import org.csu.ouostore.service.UmsMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台菜单管理
 */
@RestController
@Api(tags = "后台菜单管理")
@RequestMapping("/api/v1/menus")
public class UmsMenuController {

    @Autowired
    private UmsMenuService menuService;

    @ApiOperation("添加后台菜单")
    @PostMapping("")
    public CommonResult create(@RequestBody @Validated UmsMenuCreateParam menuCreateParam) {
        boolean success = menuService.create(menuCreateParam);
        return success ? CommonResult.OK("创建成功") : CommonResult.failed("创建失败,未知错误");
    }

    @ApiOperation("更新后台菜单")
    @PutMapping("/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody @Validated UmsMenuCreateParam menuCreateParam) {
        UmsMenu menu = new UmsMenu();
        BeanUtil.copyProperties(menuCreateParam, menu);
        menu.setId(id);
        boolean success = menuService.updateById(menu);
        return success ? CommonResult.OK("更新成功") : CommonResult.failed("更新失败,未知错误");
    }

    @ApiOperation("部分更新后台菜单")
    @PatchMapping("/{id}")
    public CommonResult patch(@PathVariable Long id, @RequestBody UmsMenuPatchParam menuPatchParam) {
        UmsMenu menu = new UmsMenu();
        BeanUtil.copyProperties(menuPatchParam, menu);
        menu.setId(id);
        boolean success = menuService.updateById(menu);
        return success ? CommonResult.OK("更新成功") : CommonResult.failed("id不存在");
    }

    @ApiOperation("根据ID获取菜单详情")
    @GetMapping("/{id}")
    public CommonResult<UmsMenu> getItem(@PathVariable Long id) {
        UmsMenu menu = menuService.getById(id);
        return ObjectUtil.isNotNull(menu) ? CommonResult.OK(menu) : CommonResult.failed("id不存在");
    }

    @ApiOperation("根据ID删除后台菜单")
    @DeleteMapping("/{id}")
    public CommonResult delete(@PathVariable Long id) {
        boolean success = menuService.delete(id);
        return success ? CommonResult.OK("删除成功") : CommonResult.failed("删除失败,id不存在");
    }

    @ApiOperation("树形结构返回所有菜单列表")
    @GetMapping("")
    public CommonResult<List<UmsMenuNodeVo>> queryAll() {
        List<UmsMenuNodeVo> list = menuService.treeList();
        return CommonResult.OK(list);
    }

    @ApiOperation("分页查询指定parent菜单列表")
    @GetMapping("/parents/{id}")
    public CommonResult<Page<UmsMenu>> query(@PathVariable String id ,UmsMenuQueryParam menuQueryParam) {
        menuQueryParam.setParentId(id);
        Page<UmsMenu> menuPage = new Page<>();
        menuService. selectMenuPage(menuPage, menuQueryParam);
        return CommonResult.OK(menuPage);
    }

}
