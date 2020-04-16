package org.csu.ouostore.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.csu.ouostore.model.entity.OmsCartItem;

/**
 * 购物车商品详细信息
 */
@Data
@ApiModel
public class CartItemDetail extends OmsCartItem {

    @ApiModelProperty("总库存-锁定库存")
    private Integer realStock;

}
