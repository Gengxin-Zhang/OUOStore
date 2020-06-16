package org.csu.ouostore.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * 属性分类
 * @author zack
 */
@Data
@ApiModel(description = "属性分类")
public class PmsProductAttributeCategoryParam {

    @ApiModelProperty(example = "宠物")
    @NotBlank(message = "name不能为空")
    private String name;

}
