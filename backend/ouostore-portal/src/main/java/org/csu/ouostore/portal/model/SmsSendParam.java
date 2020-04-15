package org.csu.ouostore.portal.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@ApiModel(value = "短信传输参数")
public class SmsSendParam {

    @ApiModelProperty(value = "手机号")
    private String phoneNumbers;

    @ApiModelProperty(value = "模板参数",example = "{\"code\":\"123456\"}")
    private String templateParam;

    @ApiModelProperty(value = "阿里云模板code")
    private String templateCode;

    @ApiModelProperty(value = "用户bizid")
    private String bizId;

    @ApiModelProperty(value = "发送日期")
    private Date sendDate;

    @ApiModelProperty
    private Long pageSize;

    @ApiModelProperty
    private Long currentPage;



}
