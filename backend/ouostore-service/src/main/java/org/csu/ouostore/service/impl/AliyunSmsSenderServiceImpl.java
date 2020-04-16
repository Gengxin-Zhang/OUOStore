package org.csu.ouostore.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;
import org.csu.ouostore.model.query.CaptchaQueryParam;
import org.csu.ouostore.service.AliyunSmsSenderService;
import org.csu.ouostore.service.RedisService;
import org.csu.ouostore.service.UmsMemberService;
import org.csu.ouostore.service.config.AliyunSMSConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class AliyunSmsSenderServiceImpl implements AliyunSmsSenderService {
    @Autowired
    private AliyunSMSConfig smsConfig;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UmsMemberService memberService;

    private static final Long AUTH_CODE_EXPIRE_SECONDS = 120L;

    /**
     * @Description: 对接阿里云短信服务实现短信发送
     */
    @Override
    public SendSmsResponse sendSms(CaptchaQueryParam queryParam) throws ClientException {
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", smsConfig.getAccessKeyId(),
                smsConfig.getAccessKeySecret());
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", smsConfig.getProduct(), smsConfig.getDomain());
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();

        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
        request.setPhoneNumbers(queryParam.getPhone());
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("OUOStore");
        //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        //注册模板
        if (queryParam.getType().equals("0")) {
            request.setTemplateCode("SMS_187951460");
        }
        //重置密码模板
        else {
            request.setTemplateCode("SMS_187941353");
        }

        //生成6位的动态验证码
        String captcha = memberService.generateAuthCode();
        request.setTemplateParam("{\"code\":\"" + captcha + "\"}");


        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            //请求成功
            redisService.set(queryParam.getPhone(), captcha);
            redisService.expire(captcha, AUTH_CODE_EXPIRE_SECONDS);
//            System.out.println(redisService.get(queryParam.getPhone())+ " 缓存成功 ");
        } else {
            System.out.println("失败状态" + sendSmsResponse.getCode());
            System.out.println("失败原因" + sendSmsResponse.getMessage());
        }
        return sendSmsResponse;
    }
}
