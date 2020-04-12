package org.csu.ouostore.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.entity.UmsResource;
import org.csu.ouostore.model.entity.UmsRole;
import org.csu.ouostore.model.entity.UmsRoleResourceRelation;
import org.csu.ouostore.model.query.UmsRoleCreateParam;
import org.csu.ouostore.model.query.UmsRolePatchParam;
import org.csu.ouostore.model.query.UmsRoleQueryParam;
import org.csu.ouostore.service.UmsRoleResourceRelationService;
import org.csu.ouostore.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台用户角色管理
 */
@RestController
@Api(tags = "后台用户角色管理")
@RequestMapping("/api/v1/roles")
public class UmsRoleController {

    @Autowired
    private UmsRoleService roleService;
    @Autowired
    private UmsRoleResourceRelationService roleResourceRelationService;

    @ApiOperation("新增一个角色")
    @PostMapping("")
    public CommonResult create(@RequestBody @Validated UmsRoleCreateParam roleCreateParam) {
        boolean success = roleService.create(roleCreateParam);
        return success ? CommonResult.OK(null, "创建成功") : CommonResult.failed("创建失败,未知错误");
    }

    @ApiOperation("更新角色")
    @PutMapping("/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody @Validated UmsRoleCreateParam roleCreateParam) {
        UmsRole role = new UmsRole();
        BeanUtil.copyProperties(roleCreateParam, role);
        role.setId(id);
        boolean success = roleService.updateById(role);
        return success ? CommonResult.OK(null, "更新成功") : CommonResult.failed("更新失败,未知错误");
    }

    @ApiOperation("部分更新角色")
    @PatchMapping("/{id}")
    public CommonResult patch(@PathVariable Long id, @RequestBody UmsRolePatchParam rolePatchParam) {
        //mybatis-plus默认不更新null字段
        UmsRole role = new UmsRole();
        BeanUtil.copyProperties(rolePatchParam, role);
        role.setId(id);
        boolean success = roleService.updateById(role);
        return success ? CommonResult.OK(null, "更新成功") : CommonResult.failed("更新失败,未知错误");
    }

    @ApiOperation("根据id删除角色")
    @DeleteMapping("/{id}")
    public CommonResult delete(@PathVariable Long id) {
        boolean success = roleService.removeById(id);
        return success ? CommonResult.OK(null, "删除成功") : CommonResult.failed("删除失败,未知错误");
    }

    @ApiOperation("分页模糊查询角色")
    @GetMapping("")
    public CommonResult<Page<UmsRole>> query(@Validated UmsRoleQueryParam roleQueryParam) {
        Page<UmsRole> rolePage = new Page<>();
        roleService.selectRolePage(rolePage, roleQueryParam);
        return CommonResult.OK(rolePage);
    }


    @ApiOperation("获取角色相关资源")
    @GetMapping("/{id}/resources")
    public CommonResult<List<UmsResource>> queryResourcesByRoleId(@PathVariable Long id) {

        List<UmsResource> resourceList = roleService.listResource(id);
        return CommonResult.OK(resourceList);
    }

    @ApiOperation("给角色分配资源")
    @PostMapping("/{id}/resources}")
    public CommonResult allocResource(@PathVariable Long id, @RequestBody Long resourceId) {
        UmsRoleResourceRelation relation = new UmsRoleResourceRelation();
        relation.setResourceId(resourceId);
        relation.setRoleId(id);
        List<UmsRoleResourceRelation> list = roleResourceRelationService.list(new QueryWrapper<>(relation));
        if (list.size() < 1) {
            roleResourceRelationService.save(relation);
            return CommonResult.OK("分配成功");
        }
        return CommonResult.failed("该角色已拥有此资源");
    }

}
