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
 * 商品信息
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="PmsProduct对象", description="商品信息")
public class PmsProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(hidden = true)
    private Long id;

    @ApiModelProperty(value = "商品类别id", example = "1")
    private Long productCategoryId;

    @ApiModelProperty(value = "商品参数类别id", example = "1")
    private Long productAttributeCategoryId;

    @ApiModelProperty(value = "商品名称", example = "萨摩耶")
    private String name;

    @ApiModelProperty(value = "展示图", example = "")
    private String pic;

    @ApiModelProperty(value = "货号", example = "No86580")
    private String productSn;

    @ApiModelProperty(value = "删除状态：0->未删除；1->已删除")
    private Integer deleteStatus;

    @ApiModelProperty(value = "上架状态：0->下架；1->上架")
    private Integer publishStatus;

    @ApiModelProperty(value = "商品价格", example = "100.00")
    private BigDecimal price;

    @ApiModelProperty(value = "商品描述", example = "可爱的萨摩耶")
    private String description;

    @ApiModelProperty(value = "市场价", required = false)
    private BigDecimal originalPrice;

    @ApiModelProperty(value = "product下所有sku库存之和,添加sku后自动增加", hidden = true)
    private Integer stock;

    @ApiModelProperty(value = "商品重量，默认为克", example = "5000")
    private BigDecimal weight;

    @ApiModelProperty(value = "可爱的宠物狗萨摩耶")
    private String keywords;

    @ApiModelProperty(value = "画册图片，连产品图片限制为5张，以逗号分割")
    private String albumPics;

    @ApiModelProperty(value = "商品分类名称", required = false)
    private String productCategoryName;


}
