package org.csu.ouostore.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 宠物一级分类,猫/狗/兔子...
 */
@Data
@ApiModel(description="宠物一级分类,猫/狗/兔子...")
public class PmsProductCategoryParam {

    @ApiModelProperty(example = "猫")
    @NotBlank(message = "name不能为空")
    private String name;

    @ApiModelProperty(value = "显示状态：0->不显示；1->显示")
    private Integer showStatus = 1;

    @ApiModelProperty(value = "图标", example = "http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180522/web.png")
    private String icon;


}
