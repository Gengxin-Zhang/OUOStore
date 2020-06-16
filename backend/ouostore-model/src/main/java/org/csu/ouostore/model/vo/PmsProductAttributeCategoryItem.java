package org.csu.ouostore.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.csu.ouostore.model.entity.PmsProductAttribute;
import org.csu.ouostore.model.entity.PmsProductAttributeCategory;

import java.util.List;

/**
 * 包含有分类下属性的vo
 * @author zack
 */
public class PmsProductAttributeCategoryItem extends PmsProductAttributeCategory {

    @Getter
    @Setter
    @ApiModelProperty(value = "商品属性和规格列表")
    private List<PmsProductAttribute> productAttributeList;
}
