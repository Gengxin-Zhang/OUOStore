package org.csu.ouostore.model.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品分类对应属性信息
 * @author zack
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductAttrInfo {

    @ApiModelProperty(value = "商品属性ID", example = "1")
    private Long attributeId;
    @ApiModelProperty(value = "商品属性分类ID", example = "1")
    private Long attributeCategoryId;
}
