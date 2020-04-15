package org.csu.ouostore.portal.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import io.swagger.annotations.Api;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.portal.model.Sms;
import org.csu.ouostore.portal.model.SmsQuery;
import org.csu.ouostore.portal.model.SmsSendParam;
import org.csu.ouostore.portal.service.AliyunSmsSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 测试发送短信controller
 */
@Api("短信查询、发送")
@RestController
public class SmsController {

    @Autowired
    AliyunSmsSenderService smsSenderService;

    /**
     * @Description: 短信发送
     */
    @GetMapping("/sms")
    @ResponseBody
    public CommonResult sms(@RequestBody SmsSendParam param) {
        Sms sms = BeanUtil.copyProperties(param,Sms.class);
//        Map<String, String> map = new HashMap<>();
//        map.put("sellerName", "平台自营");
//        map.put("orderSn", "P2019041895451");
////        SendSmsResponse sendSmsResponse = smsSenderService.sendSms("此处填写手机号",
////                JSON.toJSONString(map),
////                "此处填写短信模板code");
        SendSmsResponse sendSmsResponse = smsSenderService.sendSms(sms);
//        return JSON.toJSONString(sendSmsResponse);
        return CommonResult.OK(sendSmsResponse);
    }

    /**
     * @Description: 短信查询
     */
    @GetMapping("/query")
    @ResponseBody
    public CommonResult query(@RequestBody SmsSendParam param) {
        SmsQuery query = BeanUtil.copyProperties(param,SmsQuery.class);
        QuerySendDetailsResponse querySendDetailsResponse = smsSenderService.querySendDetails(query);
//        QuerySendDetailsResponse querySendDetailsResponse = smsSenderService.querySendDetails("此处填写发送短信返回的bizId",
//                "此处填写手机号", 10L, 1L);
//        return JSON.toJSONString(querySendDetailsResponse);
        return CommonResult.OK(querySendDetailsResponse);
    }
}
