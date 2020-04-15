package org.csu.ouostore.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* @Description: admin用户分页模糊查询参数
* @Author: Guanyu-Cai
* @Date: 4/11/20
*/
@Data
@ApiModel(description ="admin用户分页模糊查询参数")
public class UmsAdminSearchParam {

    @ApiModelProperty(value = "指定第几页,默认1", example = "1")
    private Long page = 0L;

    @ApiModelProperty(value = "指定每页条数,默认20", example = "20")
    private Long perPage = 20L;

    @ApiModelProperty(value = "username关键词", example = "za")
    private String userNameKeyword;

    @ApiModelProperty(value = "nickname关键词", example = "ck")
    private String nickNameKeyword;

    @ApiModelProperty(value = "email关键词", example = "183410@qq.com")
    private String emailKeyword;

}
