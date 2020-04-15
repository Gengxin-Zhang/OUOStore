package org.csu.ouostore.model.query;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PmsProductQueryParam {

    @ApiModelProperty(value = "商品货号")
    private String productSn;

    @ApiModelProperty(value = "商品关键词")
    private String productKeyword;

    @ApiModelProperty(value = "商品目录id")
    private Integer productCategoryId;

    @ApiModelProperty(value = "上架状态：0->下架；1->上架")
    private Integer publishStatus;

    @ApiModelProperty(value = "商品描述")
    private String description;

    @ApiModelProperty(value = "商品库存")
    private Integer stock;

    @ApiModelProperty(value = "指定第几页,默认1", example = "1")
    private Long page = 0L;

    @ApiModelProperty(value = "指定每页条数,默认20", example = "20")
    private Long perPage = 20L;
}
