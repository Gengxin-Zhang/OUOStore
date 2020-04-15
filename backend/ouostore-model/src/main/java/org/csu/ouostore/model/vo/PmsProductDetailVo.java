package org.csu.ouostore.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.csu.ouostore.model.entity.*;

import java.util.List;

/**
 * 商品详情信息
 */
@Data
@ApiModel
public class PmsProductDetailVo extends PmsProduct {

    @ApiModelProperty(value = "商品规格数量，可选的属性")
    private Integer attribute_count;

    @ApiModelProperty(value = "参数数量，固定的属性")
    private Integer param_count;

    @ApiModelProperty(value = "商品属性列表")
    private List<PmsProductAttribute> productAttribute;

    @ApiModelProperty(value = "商品属性值")
    private List<PmsProductAttributeValue> productAttributeValue;

    @ApiModelProperty(value = "商品库存")
    private List<PmsSkuStock> skuStock;

}
