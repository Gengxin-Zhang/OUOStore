package org.csu.ouostore.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.query.UmsRoleCreateParam;
import org.csu.ouostore.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台用户角色管理
 */
@RestController
@Api(tags = "后台用户角色管理")
@RequestMapping("/api/v1/roles")
public class UmsRoleController {

    @Autowired
    private UmsRoleService roleService;

    @ApiOperation("新增一个角色")
    @PostMapping("")
    public CommonResult create(@RequestBody UmsRoleCreateParam roleCreateParam) {
        boolean success = roleService.create(roleCreateParam);
        return success ? CommonResult.success(null, "创建成功") : CommonResult.failed("创建失败,未知错误");
    }

}
