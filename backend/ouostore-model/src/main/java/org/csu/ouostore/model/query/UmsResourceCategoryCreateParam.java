package org.csu.ouostore.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
* @Description: 资源分类
* @Author: Guanyu-Cai
* @Date: 4/11/20
*/
@Data
@ApiModel(description="资源分类创建请求参数")
public class UmsResourceCategoryCreateParam {

    @NotBlank(message = "分类名称不能为空")
    @ApiModelProperty(value = "分类名称", example = "商品模块", required = true)
    private String name;

    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;


}
