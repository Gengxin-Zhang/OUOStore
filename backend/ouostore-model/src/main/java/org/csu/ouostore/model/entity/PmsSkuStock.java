package org.csu.ouostore.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * sku(库存保有单位)的库存
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="PmsSkuStock对象", description="sku(库存保有单位)的库存")
public class PmsSkuStock implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "商品id", example = "1")
    private Long productId;

    @ApiModelProperty(value = "sku编码", hidden = true)
    private String skuCode;

    @ApiModelProperty(value = "价格", example = "2000")
    private BigDecimal price;

    @ApiModelProperty(value = "库存", example = "500")
    private Integer stock;

    @ApiModelProperty(value = "展示图片")
    private String pic;

    @ApiModelProperty(value = "销量", hidden = true)
    private Integer sale;

    @ApiModelProperty(value = "被锁定的库存", hidden = true)
    private Integer lockStock;

    @ApiModelProperty(value = "商品销售属性例:[{\"key\":\"毛色\",\"value\":\"黑色\"},{\"key\":\"年龄\",\"value\":\"1岁\"}]")
    private String spData;


}
