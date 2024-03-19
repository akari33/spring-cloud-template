package com.temp.common.web.handler;

import com.temp.common.exception.ApiException;
import com.temp.common.result.CommonResult;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Iterator;
import java.util.Set;

/**
 * 全局异常处理类
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public <T> CommonResult<T> handle(ApiException e) {
        log.error("ApiException: {}", e.getMessage(), e);
        if (e.getErrorCode() != null) {
            return CommonResult.failed(e.getErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public <T> CommonResult<T> handleValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getDefaultMessage();
            }
        }
        return CommonResult.validateFailed(message);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public <T> CommonResult<T> handleValidException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
        String message = null;
        while (iterator.hasNext()) {
            ConstraintViolation<?> cvl = iterator.next();
            message = cvl.getMessageTemplate();
        }
        return CommonResult.validateFailed(message);
    }

    @ExceptionHandler(value = BindException.class)
    public <T> CommonResult<T> handleValidException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getDefaultMessage();
            }
        }
        return CommonResult.validateFailed(message);
    }

    /**
     * 服务器异常
     */
    @ExceptionHandler(Exception.class)
    public CommonResult<String> exception(Exception e) {
        log.error("unknown exception:{}", e.getMessage(), e);
        return CommonResult.failed("系统异常");
    }

}
