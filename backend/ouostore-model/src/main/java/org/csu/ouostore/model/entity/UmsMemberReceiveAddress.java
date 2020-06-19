package org.csu.ouostore.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 会员收货地址表
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UmsMemberReceiveAddress对象", description="会员收货地址表")
public class UmsMemberReceiveAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(hidden = true)
    private Long id;

    @ApiModelProperty(hidden = true)
    private Long memberId;

    @ApiModelProperty(value = "收货人名称", example = "tutu")
    private String name;

    @ApiModelProperty(value = "手机号", example = "17777777777")
    private String phoneNumber;

    @ApiModelProperty(value = "是否为默认收货地址", example = "0")
    private Integer defaultStatus;

    @ApiModelProperty(value = "邮政编码", example = "350000")
    private String postCode;

    @ApiModelProperty(value = "省份/直辖市", example = "福建省")
    private String province;

    @ApiModelProperty(value = "城市", example = "厦门市")
    private String city;

    @ApiModelProperty(value = "区", example = "思明区")
    private String region;

    @ApiModelProperty(value = "详细地址(街道)", example = "xxx街道300号")
    private String detailAddress;


}
