package org.csu.ouostore.common.api;

import com.baomidou.mybatisplus.extension.api.IErrorCode;

/**
* @Description: 封装http状态码和业务状态码及描述
* @Author: Guanyu-Cai
* @Date: 4/10/20
*/
public enum ResultCode implements IErrorCode {

    OK(200, "操作成功"),
    INTERNAL_SERVER_ERROR(500, "操作失败"),
    BAD_REQUEST(400, "参数错误"),
    UNAUTHORIZED(401, "暂未登入或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    NOT_FOUND(404, "未找到"),
    ;

    private long code;
    private String msg;

    private ResultCode(long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

}
