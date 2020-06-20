package org.csu.ouostore.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.common.exception.ApiException;
import org.csu.ouostore.model.dto.JwtDto;
import org.csu.ouostore.model.entity.UmsAdmin;
import org.csu.ouostore.model.entity.UmsAdminLoginLog;
import org.csu.ouostore.model.entity.UmsRole;
import org.csu.ouostore.model.query.UmsAdminLogsQueryParam;
import org.csu.ouostore.model.query.UmsAdminSearchParam;
import org.csu.ouostore.model.query.UmsAdminSignInParam;
import org.csu.ouostore.model.query.UmsAdminSignUpParam;
import org.csu.ouostore.model.vo.UmsAdminDetailVo;
import org.csu.ouostore.model.vo.UmsAdminVo;
import org.csu.ouostore.service.UmsAdminLoginLogService;
import org.csu.ouostore.service.UmsAdminRoleRelationService;
import org.csu.ouostore.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 后台用户管理
 */
@Api(tags = "后台用户管理")
@RestController
@RequestMapping("/api/v1/admin")
public class UmsAdminController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UmsAdminService adminService;
    @Autowired
    private UmsAdminRoleRelationService adminRoleRelationService;
    @Autowired
    private UmsAdminLoginLogService adminLoginLogService;

    @ApiOperation(value = "登入并获取token")
    @PostMapping("/oauth/access_token")
    public CommonResult<JwtDto> signIn(@RequestBody @Validated UmsAdminSignInParam umsAdminSignInParam) {
        JwtDto dto = adminService.signIn(umsAdminSignInParam.getUsername(), umsAdminSignInParam.getPassword());
        if (StrUtil.isEmpty(dto.getAccessToken())) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        return CommonResult.OK(dto);
    }

    @ApiOperation(value = "注册并获取token")
    @PostMapping(value = "/users")
    public CommonResult<JwtDto> signUp(@RequestBody @Validated UmsAdminSignUpParam adminSignUpParam) {
        JwtDto dto = adminService.signUp(adminSignUpParam);
        if (StrUtil.isEmpty(dto.getAccessToken())) {
            CommonResult.failed("注册失败,未知错误");
        }
        return CommonResult.OK(dto);
    }

    @ApiOperation("删除指定后台用户及其相关信息")
    @DeleteMapping("/users/{id}")
    public CommonResult<String> delete(@PathVariable Long id) {
        boolean success = adminService.delete(id);
        return success ? CommonResult.OK("删除成功") : CommonResult.failed("删除失败,未知原因");
    }

    @ApiOperation("更新指定后台用户")
    @PutMapping("/users/{id}")
    public CommonResult<String> update(@PathVariable Long id, @RequestBody UmsAdminSignUpParam adminSignUpParam) {
        UmsAdmin admin = new UmsAdmin();
        admin.setId(id);
        BeanUtil.copyProperties(adminSignUpParam, admin);
        boolean success = adminService.updateById(admin);
        return success ? CommonResult.OK("更新成功") : CommonResult.failed("id不存在");
    }

    @ApiOperation("查询指定后台用户详细信息")
    @GetMapping("/users/{id}")
    public CommonResult<UmsAdminVo> query(@PathVariable Long id) {
        UmsAdmin one = adminService.getOne(new QueryWrapper<UmsAdmin>().eq("id", id).last("limit 1"));
        if (ObjectUtil.isNull(one)) {
            throw new ApiException("id不存在");
        }
        UmsAdminVo vo = new UmsAdminVo();
        BeanUtil.copyProperties(one, vo);
        return CommonResult.OK(vo);
    }

    @ApiOperation("查询登入的管理员详细信息")
    @GetMapping("/detail")
    public CommonResult<UmsAdminDetailVo> detail() {
        UmsAdminDetailVo vo = adminService.detail();
        return CommonResult.OK(vo);
    }

    @ApiOperation("模糊搜索分页获取用户列表")
    @GetMapping("/users/search")
    public CommonResult<Page<UmsAdminDetailVo>> search(UmsAdminSearchParam adminSearchParam) {
        Page<UmsAdminDetailVo> page = new Page<>();
        adminService.selectResourcePage(page, adminSearchParam);
        return CommonResult.OK(page);
    }

    @ApiOperation("给用户分配角色")
    @PostMapping("/users/{userId}/roles/{roleId}")
    public CommonResult addRole(@PathVariable("userId") Long userId, @PathVariable("roleId") Long roleId) {
        boolean success = adminRoleRelationService.create(userId, roleId);
        return success ? CommonResult.OK("分配成功") : CommonResult.failed("分配失败,未知原因");
    }

    @ApiOperation("删除用户角色")
    @DeleteMapping("/users/{userId}/roles/{roleId}")
    public CommonResult delRole(@PathVariable("userId") Long userId, @PathVariable("roleId") Long roleId) {
        boolean success = adminRoleRelationService.delete(userId, roleId);
        return success ? CommonResult.OK("删除成功") : CommonResult.failed("删除失败,未知原因");
    }

    @ApiOperation("查询指定用户的角色")
    @GetMapping("/users/{id}/roles")
    public CommonResult<List<UmsRole>> getRoleList(@PathVariable Long id) {
        List<UmsRole> roleList = adminService.getRoleList(id);
        return CommonResult.OK(roleList);
    }

//    @ApiOperation("查询指定用户的日志")
//    @GetMapping("/{adminId}/login_logs")
//    public CommonResult<List<UmsAdminLoginLog>> getOneLoginLogs(@PathVariable Long adminId) {
//        List<UmsAdminLoginLog> logs = adminLoginLogService.list(
//                new QueryWrapper<UmsAdminLoginLog>().eq("admin_id", adminId));
//        return CommonResult.OK(logs);
//    }

    @ApiOperation("分页查询日志")
    @PostMapping("/login_logs")
    public CommonResult<Page<UmsAdminLoginLog>> getLoginLogs(@RequestBody UmsAdminLogsQueryParam param) {
        Page<UmsAdminLoginLog> page = new Page<>();
        page.setCurrent(param.getPage());
        page.setSize(param.getPerPage());
        if (param.getDescByTime()) {
            page.setDesc("create_time");
        }
        QueryWrapper<UmsAdminLoginLog> wrapper = new QueryWrapper<>();
        if (param.getAdminId() != null) {
            wrapper.eq("admin_id", param.getAdminId());
        }
        adminLoginLogService.page(page, wrapper);
        return CommonResult.OK(page);
    }
}

