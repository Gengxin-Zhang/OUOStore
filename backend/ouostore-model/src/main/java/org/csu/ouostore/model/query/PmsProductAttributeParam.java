package org.csu.ouostore.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 商品属性(规格)参数表,一条记录对应一个规格/参数
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Data
@ApiModel(description="商品属性(规格)")
public class PmsProductAttributeParam {

    @ApiModelProperty(value = "商品规格/参数分类", example = "1")
    @NotNull
    private Long productAttributeCategoryId;

    @ApiModelProperty(value = "规格/参数名", example = "重量")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "属性选择类型：0->唯一；1->单选；2->多选", example = "0")
    private Integer selectType = 0;

    @ApiModelProperty(value = "属性录入方式：0->手工录入；1->从列表中选取", example = "0")
    private Integer inputType = 0;

    @ApiModelProperty(value = "可选值列表，以逗号隔开", example = "1kg,2kg,3kg")
    private String inputList;

    @ApiModelProperty(value = "排序字段：最高的可以单独上传图片")
    private Integer sort = 0;

    @ApiModelProperty(value = "是否支持手动新增；0->不支持；1->支持", example = "1")
    private Integer handAddStatus;

    @ApiModelProperty(value = "属性的类型；0->属性(规格)；1->参数", example = "1")
    private Integer type;


}
