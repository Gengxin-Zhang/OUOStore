package org.csu.ouostore.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.csu.ouostore.model.query.CaptchaQueryParam;

public interface AliyunSmsSenderService {
    /**
     * @Description: 对接阿里云短信服务实现短信发送
     * 发送验证码类的短信时，每个号码每分钟最多发送一次，每个小时最多发送5次。其它类短信频控请参考阿里云
     */
    SendSmsResponse sendSms(CaptchaQueryParam queryParam) throws ClientException;

}
