package org.csu.ouostore.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.csu.ouostore.model.entity.PmsProduct;
import org.csu.ouostore.model.entity.PmsProductAttributeValue;
import org.csu.ouostore.model.entity.PmsSkuStock;

import java.util.List;

/**
 * 创建和修改商品时使用的参数
 * @author zack
 */
@Data
@ApiModel(description = "商品参数")
public class PmsProductParam extends PmsProduct {

    @ApiModelProperty("商品的sku库存信息")
    private List<PmsSkuStock> skuStockList;

    @ApiModelProperty("商品参数及自定义规格属性")
    private List<PmsProductAttributeValue> productAttributeValueList;

}
