package org.csu.ouostore.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.csu.ouostore.model.entity.OmsCartItem;
import org.csu.ouostore.model.entity.OmsOrderItem;

import java.util.List;

/**
 * 购物车详情
 */
@Data
@ApiModel
public class OmsCartDetailVo {
    @ApiModelProperty("购物车内商品列表")
    private List<OmsCartItem> cartItemList;

}
