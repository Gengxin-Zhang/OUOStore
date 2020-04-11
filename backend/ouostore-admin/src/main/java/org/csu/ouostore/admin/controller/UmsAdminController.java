package org.csu.ouostore.admin.controller;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.dto.JwtDto;
import org.csu.ouostore.model.query.UmsAdminSignParam;
import org.csu.ouostore.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

    @ApiOperation(value = "登入并获取token")
    @PostMapping("/oauth/access_token")
    public CommonResult<JwtDto> signIn(@RequestBody @Validated UmsAdminSignParam umsAdminSignParam) {
        JwtDto dto = adminService.signIn(umsAdminSignParam.getUsername(), umsAdminSignParam.getPassword());
        if (StrUtil.isEmpty(dto.getAccessToken())) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        return CommonResult.OK(dto);
    }

    @ApiOperation(value = "注册并获取token")
    @PostMapping(value = "/users")
    public CommonResult<JwtDto> signUp(@RequestBody @Validated UmsAdminSignParam umsAdminParam) {
        JwtDto dto = adminService.signUp(umsAdminParam.getUsername(), umsAdminParam.getPassword());
        if (StrUtil.isEmpty(dto.getAccessToken())) {
            CommonResult.failed("注册失败,未知错误");
        }
        return CommonResult.OK(dto);
    }

}

