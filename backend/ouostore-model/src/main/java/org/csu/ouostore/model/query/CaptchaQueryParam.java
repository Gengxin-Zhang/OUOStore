package org.csu.ouostore.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(description = "手机验证码参数")
public class CaptchaQueryParam {

    @ApiModelProperty(value = "发送手机号")
    private String phone;

    @ApiModelProperty(value = "发送验证码类型：0->注册，1->重置密码")
    private String type;
}
