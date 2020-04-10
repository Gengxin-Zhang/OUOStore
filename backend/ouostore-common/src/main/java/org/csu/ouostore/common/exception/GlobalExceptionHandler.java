package org.csu.ouostore.common.exception;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import org.csu.ouostore.common.api.CommonResult;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 请求参数异常处理
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return CommonResult.validateFailed(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    /**
     * mybatis-plus异常处理
     */
    @ExceptionHandler(value = ApiException.class)
    public CommonResult handleApiException(ApiException e) {
        if (e.getErrorCode() != null) {
            return CommonResult.failed(e.getErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }

    /**
     * 业务异常处理
     */
    @ExceptionHandler(value = org.csu.ouostore.common.exception.ApiException.class)
    public CommonResult handleMyApiException(org.csu.ouostore.common.exception.ApiException e) {
        if (e.getErrorCode() != null) {
            return CommonResult.failed(e.getErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }

    /**
     * 拦截所有异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CommonResult handleMyApiException(HttpMessageNotReadableException e) {
        return CommonResult.validateFailed(e.getMessage());
    }
}
