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
 * 公司发货/收货(接收退货)地址表
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="OmsCompanyAddress对象", description="公司发货/收货(接收退货)地址表")
public class OmsCompanyAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(hidden = true)
    private Long id;

    @ApiModelProperty(value = "地址名称", example = "深圳发货点")
    private String addressName;

    @ApiModelProperty(value = "默认发货地址：0->否；1->是", example = "1")
    private Integer sendStatus;

    @ApiModelProperty(value = "是否默认收货地址：0->否；1->是", example = "1")
    private Integer receiveStatus;

    @ApiModelProperty(value = "收发货人姓名", example = "zack")
    private String name;

    @ApiModelProperty(value = "收发货人电话", example = "17755555555")
    private String phone;

    @ApiModelProperty(value = "省/直辖市", example = "广东省")
    private String province;

    @ApiModelProperty(value = "市", example = "深圳市")
    private String city;

    @ApiModelProperty(value = "区", example = "福田区")
    private String region;

    @ApiModelProperty(value = "详细地址", example = "梅林路173号")
    private String detailAddress;


}
