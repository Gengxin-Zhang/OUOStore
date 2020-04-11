package org.csu.ouostore.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
* @Description: 创建角色参数
* @Author: Guanyu-Cai
* @Date: 4/11/20
*/
@Data
@ApiModel(description="创建角色参数")
public class UmsRoleCreateParam {

    @ApiModelProperty(value = "名称", example = "超级管理员", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(value = "描述", example = "拥有所有权限")
    private String description;

    @ApiModelProperty(value = "启用状态：0->禁用；1->启用", example = "1")
    private Integer status;

    @ApiModelProperty(value = "排序", example = "0")
    private Integer sort;


}
