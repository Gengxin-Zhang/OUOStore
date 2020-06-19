package org.csu.ouostore.portal.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.dto.JwtDto;
import org.csu.ouostore.model.entity.UmsMember;
import org.csu.ouostore.model.query.UmsMemberCompleteParam;
import org.csu.ouostore.model.query.UmsMemberSignParam;
import org.csu.ouostore.model.query.UmsMemberUpdateBaseParam;
import org.csu.ouostore.model.vo.UmsMemberVo;
import org.csu.ouostore.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "会员管理")
@RequestMapping("/api/v1/members")
public class UmsMemberContrller {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UmsMemberService umsMemberService;

    @ApiOperation(value = "注册或登入并获取token")
    @PostMapping("/oauth/access_token")
    public CommonResult<JwtDto> sign(@RequestBody @Validated UmsMemberSignParam signParam) {
        JwtDto dto = umsMemberService.sign(signParam.getPhone(), signParam.getCode());
        if (StrUtil.isEmpty(dto.getAccessToken())) {
            CommonResult.failed("注册失败,未知错误");
        }
        return CommonResult.OK(dto);
    }

    @ApiOperation(value = "第一次登入后完善用户信息")
    @PatchMapping("/complete")
    public CommonResult<String> patch(@RequestBody @Validated UmsMemberCompleteParam param) {
        boolean result = umsMemberService.patch(param);
        System.out.println(result);
        return result ? CommonResult.OK("更新成功") : CommonResult.failed("更新失败,未知错误");
    }

    @ApiOperation(value = "修改用户基本信息")
    @PatchMapping("")
    public CommonResult<String> updateBase(@RequestBody @Validated UmsMemberUpdateBaseParam param) {
        UmsMember member = umsMemberService.getCurrentMember();
        BeanUtil.copyProperties(param, member);
        boolean success = umsMemberService.save(member);
        return success ? CommonResult.OK("更新成功") : CommonResult.failed("更新失败,未知错误");
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("")
    public CommonResult<UmsMemberVo> query() {
        UmsMember currentMember = umsMemberService.getCurrentMember();
        UmsMemberVo vo = BeanUtil.copyProperties(currentMember, UmsMemberVo.class);
        return CommonResult.OK(vo);
    }

}
