package org.csu.ouostore.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* @Description: 资源参数
* @Author: Guanyu-Cai
* @Date: 4/11/20
*/
@Data
@ApiModel(description ="资源创建请求参数")
public class UmsResourceCreateParam {

    @NotBlank(message = "资源名称不能为空")
    @ApiModelProperty(value = "资源名称", example = "商品管理", required = true)
    private String name;

    @NotBlank(message = "资源URL不能为空")
    @ApiModelProperty(value = "资源URL", example = "/admin/**", required = true)
    private String url;

    @ApiModelProperty(value = "描述", example = "新增,下架商品等操作")
    private String description;

    @NotNull(message = "资源分类ID不能为空")
    @ApiModelProperty(value = "资源分类ID", example = "1", required = true)
    private Long categoryId;

}
