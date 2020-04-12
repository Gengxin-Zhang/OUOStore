package org.csu.ouostore.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* @Description: 角色分页模糊查询参数
* @Author: Guanyu-Cai
* @Date: 4/11/20
*/
@Data
@ApiModel(description ="角色分页模糊查询参数")
public class UmsRoleQueryParam {

    @ApiModelProperty(value = "指定第几页,默认1", example = "1")
    private Long page = 0L;

    @ApiModelProperty(value = "指定每页条数,默认20", example = "20")
    private Long perPage = 20L;

    @ApiModelProperty(value = "资源分类ID", example = "超级")
    private String nameKeyword;

}
