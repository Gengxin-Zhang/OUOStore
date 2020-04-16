package org.csu.ouostore.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.csu.ouostore.model.entity.UmsMember;

@Data
@ApiModel(description = "更新会员参数")
public class UmsMemberPatchParam extends UmsMember {

    @ApiModelProperty(value = "验证码")
    private String code;
}
