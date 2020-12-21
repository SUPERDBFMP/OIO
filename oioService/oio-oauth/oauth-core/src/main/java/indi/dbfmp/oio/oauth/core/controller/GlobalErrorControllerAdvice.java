package indi.dbfmp.oio.oauth.core.controller;

import inid.dbfmp.oauth.api.exception.CommonException;
import indi.dbfmp.oio.oauth.core.exception.ResetPasswordException;
import indi.dbfmp.web.common.dto.CommonResult;
import indi.dbfmp.web.common.dto.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: GlobalErrorController
 * @since 2020/10/12 12:56 上午
 */
@Slf4j
@RestControllerAdvice
public class GlobalErrorControllerAdvice {

    /**
     * 捕捉所有CommonException异常
     * @param e
     * @return
     */
    @ExceptionHandler(CommonException.class)
    public CommonResult<?> MessageExceptionHandler(CommonException e) {
        if (null != e.getResultCode()) {
            return CommonResult.failed(e.getResultCode(), e.getMessage());
        } else {
            return CommonResult.failed(e.getMessage());
        }
    }

    @ExceptionHandler(ValidationException.class)
    public CommonResult<?> MessageExceptionHandler(ValidationException e) {
        return CommonResult.failed(e.getMessage());

    }

    @ExceptionHandler(Exception.class)
    public CommonResult<?> MessageExceptionHandler(Exception e) {
        log.info("系统异常",e);
        return CommonResult.failed("系统异常，请稍后再试");

    }

    @ExceptionHandler(ResetPasswordException.class)
    public CommonResult<?> MessageExceptionHandler(ResetPasswordException e) {
        return CommonResult.failed(ResultCode.SET_NEW_PASSWORD);
    }


}
