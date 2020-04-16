package org.csu.ouostore.portal.controller;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.dto.JwtDto;
import org.csu.ouostore.model.query.UmsMemberPatchParam;
import org.csu.ouostore.model.query.UmsMemberSignParam;
import org.csu.ouostore.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

//    @ApiOperation(value = "登入并获取token")
//    @PostMapping("/oauth/access_token")
//    public CommonResult<JwtDto> signIn(@RequestBody @Validated UmsMemberSignInParam signInParam){
//        System.out.println(signInParam.getUsername());
//        System.out.println(signInParam.getPassword());
//        JwtDto dto = umsMemberService.signIn(signInParam.getUsername(), signInParam.getPassword());
//        if (StrUtil.isEmpty(dto.getAccessToken())) {
//            return CommonResult.validateFailed("用户名或密码错误");
//        }
//        return CommonResult.OK(dto);
//    }

    @ApiOperation(value = "注册或登录并获取token")
    @PostMapping("/oauth/access_token")
    @ResponseBody
    public CommonResult<JwtDto> sign(@RequestBody @Validated UmsMemberSignParam signParam) {
        JwtDto dto = umsMemberService.SignUp(signParam.getPhone(), signParam.getCode());
        System.out.println(signParam.getCode());
        System.out.println(signParam.getPhone());
        if (StrUtil.isEmpty(dto.getAccessToken())) {
            CommonResult.failed("注册失败,未知错误");
        }
        return CommonResult.OK(dto);
    }

    @ApiOperation(value = "注册或登录并获取token")
    @PatchMapping("/{memberid}")
    public CommonResult update(@PathVariable Long memberid, @RequestBody @Validated UmsMemberPatchParam patchParam) {
        patchParam.setId(memberid);
        boolean result = umsMemberService.patch(patchParam);
        System.out.println(result);
        return result ? CommonResult.OK("更新成功") : CommonResult.failed("更新失败,未知错误");
    }

}
