package org.csu.ouostore.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 */
@Data
@ApiModel(description = "添加cartItem参数")
public class OmsCartAddParam {

    @ApiModelProperty(value = "数量", example = "5")
    private Integer quantity;

    @ApiModelProperty(value = "商品sku id", example = "1")
    private Long productSkuId;
}
