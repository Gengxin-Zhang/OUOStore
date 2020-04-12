package org.csu.ouostore.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 后台资源表
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UmsResource对象", description="后台资源表")
public class UmsResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "创建时间", example = "2020-04-11 17:07:14")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "资源名称", example = "商品管理")
    private String name;

    @ApiModelProperty(value = "资源URL", example = "/api/v1/admin/**")
    private String url;

    @ApiModelProperty(value = "描述", example = "下架商品等操作")
    private String description;

    @ApiModelProperty(value = "资源分类ID", example = "1")
    private Long categoryId;


}
