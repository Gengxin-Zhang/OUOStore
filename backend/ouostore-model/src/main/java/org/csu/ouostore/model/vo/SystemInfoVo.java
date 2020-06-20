package org.csu.ouostore.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel
@Data
public class SystemInfoVo {

    @ApiModelProperty("操作系统")
    private String os;

    @ApiModelProperty(value = "程序启动时间")
    private Date startTime;

    @ApiModelProperty(value = "cpu核数")
    private Integer cpuCoreCount;

//    @ApiModelProperty(value = "cpu系统使用率")
//    private Double cpuSystemUsedRate;
//
//    @ApiModelProperty(value = "cpu用户使用率")
//    private Double cpuUserUsedRate;
//
//    @ApiModelProperty(value = "cpu空闲率")
//    private Double cpuUnwantedRate;

    @ApiModelProperty(value = "jvm最大可用内存(M)")
    private Double jvmMaxMemory;

    @ApiModelProperty(value = "jvm已使用内存(M)")
    private Double jvmLeftMemory;

    @ApiModelProperty(value = "系统最大可用内存(G)")
    private Double systemMaxMemory;

    @ApiModelProperty(value = "系统剩余内存(G)")
    private Double systemLeftMemory;

    @ApiModelProperty(value = "系统最大可用存储(G)")
    private Double systemMaxStore;

    @ApiModelProperty(value = "系统剩余用存储(G)")
    private Double systemLeftStore;
}
