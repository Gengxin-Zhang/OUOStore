package org.csu.ouostore.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录日志分页查询参数
 */
@Data
@ApiModel
public class UmsAdminLogsQueryParam {

    @ApiModelProperty(value = "用户id", example = "1", required = false)
    private Long adminId;

    @ApiModelProperty(value = "是否按时间倒序,默认是", required = false)
    private Boolean descByTime = true;

    @ApiModelProperty(value = "指定第几页,默认1", example = "1")
    private Long page = 0L;

    @ApiModelProperty(value = "指定每页条数,默认20", example = "20")
    private Long perPage = 20L;
}
