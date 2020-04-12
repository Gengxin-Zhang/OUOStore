package org.csu.ouostore.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
* @Description: 新建菜单参数
* @Author: Guanyu-Cai
* @Date: 4/11/20
*/
@Data
@ApiModel(description="新建菜单参数")
public class UmsMenuCreateParam {

    @ApiModelProperty(value = "父级ID", example = "权限")
    private Long parentId = 0L;

    @ApiModelProperty(value = "菜单名称", example = "用户列表")
    @NotBlank(message = "menu名称不能为空")
    private String title;

    @ApiModelProperty(value = "菜单排序", example = "0")
    private Integer sort = 0;

    @ApiModelProperty(value = "前端url名称", example = "ums")
    @NotBlank(message = "前端url名称不能为空")
    private String name;

    @ApiModelProperty(value = "前端图标")
    private String icon;

    @ApiModelProperty(value = "前端隐藏", example = "0")
    private Integer hidden = 0;


}
