package org.csu.ouostore.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * <p>
 * 会员表
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Data
@ApiModel("会员信息完善参数")
public class UmsMemberCompleteParam {

    @ApiModelProperty(value = "用户名", example = "zack")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码", example = "123456")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "昵称", example = "菜鸟")
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @ApiModelProperty(value = "头像", required = false)
    private String icon;

    @ApiModelProperty(value = "性别：0->未知；1->男；2->女", required = false)
    private Integer gender;

    @ApiModelProperty(value = "生日", required = false)
    private LocalDate birthday;

    @ApiModelProperty(value = "所在城市", required = false)
    private String city;

    @ApiModelProperty(value = "职业", required = false)
    private String job;


}
