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
@ApiModel("会员基本信息修改参数")
public class UmsMemberUpdateBaseParam {

    @ApiModelProperty(value = "昵称", example = "菜鸟", required = false)
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @ApiModelProperty(value = "头像", required = false)
    private String icon;

    @ApiModelProperty(value = "性别：0->未知；1->男；2->女", required = false, example = "1")
    private Integer gender;

    @ApiModelProperty(value = "生日", required = false, example = "2000-11-01")
    private LocalDate birthday;

    @ApiModelProperty(value = "所在城市", required = false, example = "北京")
    private String city;

    @ApiModelProperty(value = "职业", required = false, example = "程序猿")
    private String job;


}
