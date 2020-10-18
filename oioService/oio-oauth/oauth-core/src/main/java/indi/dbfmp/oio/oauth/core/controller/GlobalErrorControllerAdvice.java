package indi.dbfmp.oio.oauth.core.controller;

import indi.dbfmp.oio.oauth.core.exception.CommonException;
import inid.dbfmp.common.dto.CommonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: GlobalErrorController
 * @since 2020/10/12 12:56 上午
 */
@RestControllerAdvice
public class GlobalErrorControllerAdvice {

    /**
     * 捕捉所有CommonException异常
     * @param e
     * @return
     */
    @ExceptionHandler(CommonException.class)
    public CommonResult<?> MessageExceptionHandler(CommonException e) {
        return CommonResult.failed(e.getMessage());

    }


}
