package org.csu.ouostore.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

/**
 * 会员注册参数
 */
@Data
@ApiModel(description = "会员注册参数")
public class UmsMemberSignUpParam {

    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号",required = true)
    private String phone;

//    private DateTimeLiteralExpression.DateTime create_time;
//    private Date birthday;

    @ApiModelProperty(value = "帐号启用状态：0->禁用；1->启用,默认1", example = "1")
    private int status = 1;

    @ApiModelProperty(value = "性别：0->未知；1->男；2->女")
    private int gender;

    @ApiModelProperty(value = "所在城市")
    private String cith;

    @ApiModelProperty(value = "职业")
    private String job;

}
