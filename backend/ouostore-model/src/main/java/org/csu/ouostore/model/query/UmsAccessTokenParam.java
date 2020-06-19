package org.csu.ouostore.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zack
 */
@Data
@ApiModel(description = "登入或注册参数")
public class UmsAccessTokenParam {

    @ApiModelProperty(value = "验证码")
    private String code;
}
