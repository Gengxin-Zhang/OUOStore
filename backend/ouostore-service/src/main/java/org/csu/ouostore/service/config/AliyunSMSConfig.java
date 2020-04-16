package org.csu.ouostore.service.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
public class AliyunSMSConfig {
    /**
     * 阿里云 accessKeyId（安全信息管理下面）
     */
    private String accessKeyId = "LTAI4GBqhY4th4bTs1aQ5yXL";

    /**
     * 阿里云 accessKeySecret（安全信息管理下面）
     */
    private String accessKeySecret = "MQP5L6toLfvENY4ridnUd2OtQ38cHd";

    /**
     * 产品名称:云通信短信API产品,开发者无需替换
     */
    private String product = "Dysmsapi";

    /**
     * 产品域名,开发者无需替换
     */
    private String domain = "dysmsapi.aliyuncs.com";
    private String regionId = "cn-hangzhou";

    /**
     * 短信签名名称（国内/国际/港澳台消息-签名管理下面）
     */
    private String signName = "OUOStore";

    /**
     * 发送日期 支持30天内记录查询，格式yyyyMMdd
     */
    private String dateFormat = "yyyyMMdd";

    /**
     * 服务结点
     */
    private String endpointName = "cn-hangzhou";

}