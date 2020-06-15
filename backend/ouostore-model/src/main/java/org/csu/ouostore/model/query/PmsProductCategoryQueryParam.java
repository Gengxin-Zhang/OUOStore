package org.csu.ouostore.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "商品种类查询对象")
public class PmsProductCategoryQueryParam {
//
//    @ApiModelProperty(value = "商品目录编号")
//    private String productCategoryId;

    @ApiModelProperty(value = "目录显示状态，0->不显示；1->显示", example = "1")
    private Integer status;

    @ApiModelProperty(value = "指定第几页,默认1,从1开始", example = "1")
    private Long page = 1L;

    @ApiModelProperty(value = "指定每页条数,默认20", example = "20")
    private Long perPage = 5L;


}
