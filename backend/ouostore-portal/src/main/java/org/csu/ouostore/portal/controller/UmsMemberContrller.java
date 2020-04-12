package org.csu.ouostore.portal.controller;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.dto.JwtDto;
import org.csu.ouostore.model.query.UmsMemberSignParam;
import org.csu.ouostore.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "会员管理")
@RequestMapping("/api/v1/member")
public class UmsMemberContrller {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UmsMemberService umsMemberService;

    @ApiOperation(value = "登入并获取token")
    @PostMapping("/oauth/access_token")
    public CommonResult<JwtDto> signIn(@RequestBody @Validated UmsMemberSignParam umsMemberSignParam){
        JwtDto dto = umsMemberService.signIn(umsMemberSignParam.getUsername(),umsMemberSignParam.getPassword());
        if (StrUtil.isEmpty(dto.getAccessToken())) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        return CommonResult.OK(dto);
    }

    @ApiOperation(value = "注册并获取token")
    @PostMapping("/users")
    public CommonResult<JwtDto> signUp(@RequestBody @Validated UmsMemberSignParam umsMemberSignParam){
        JwtDto dto = umsMemberService.signUp(umsMemberSignParam.getUsername(), umsMemberSignParam.getPassword());
        if (StrUtil.isEmpty(dto.getAccessToken())) {
            CommonResult.failed("注册失败,未知错误");
        }
        return CommonResult.OK(dto);
    }

}
