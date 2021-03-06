package org.csu.ouostore.portal.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.query.CaptchaQueryParam;
import org.csu.ouostore.portal.service.AliyunSmsSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 测试发送短信controller
 */
@Api(tags = "短信发送")
@RestController
@RequestMapping("/api/v1")
public class SmsController {

    @Autowired
    AliyunSmsSenderService smsSenderService;

    @ApiOperation("向手机号发送验证码")
    @PostMapping("/sms")
    public CommonResult<SendSmsResponse> sms(@RequestBody CaptchaQueryParam queryParam) throws ClientException {
        SendSmsResponse sendSmsResponse = smsSenderService.sendSms(queryParam);
        return CommonResult.OK(sendSmsResponse);
    }

}
