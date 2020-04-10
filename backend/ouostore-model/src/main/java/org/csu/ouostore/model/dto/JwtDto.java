package org.csu.ouostore.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Guanyu-Cai
 * @Date: 04/10/2020
 */
@Data
@ApiModel
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class JwtDto {

    @ApiModelProperty(value = "令牌", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6YWNrIiwiY3JlYXRlZCI6MTU" +
            "4NjUyOTY2MjAyNywiZXhwIjoxNTg3MTM0NDYyfQ.j63gV6JAuAvzUjtE5zg6Pts7HQTT7SIG4JwaMXIa7Wc")
    private String accessToken;

    @ApiModelProperty(value = "令牌类型", example = "Bearer")
    private String tokenType;

    @ApiModelProperty(value = "有效时间(秒)", example = "3600")
    private long expiresIn;

}
