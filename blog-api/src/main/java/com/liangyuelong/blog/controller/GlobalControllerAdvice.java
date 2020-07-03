package com.liangyuelong.blog.controller;

import com.liangyuelong.blog.common.BizException;
import com.liangyuelong.blog.common.NoLogException;
import com.liangyuelong.blog.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 *
 * @author yuelong.liang
 */
@ControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    /**
     * exception 异常，返回服务器错误
     *
     * @param e 异常对象
     * @return ResponseEntity
     */
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public Result exception(Throwable e) {
        log.error(e.getMessage(), e);
        return Result.failed("服务器错误");
    }

    /**
     * 业务异常，打印警告信息, 并向前端返回 message
     *
     * @param e 异常对象
     * @return ResponseEntity
     */
    @ExceptionHandler(BizException.class)
    @ResponseBody
    public Result bizException(BizException e) {
        log.warn(e.getMessage());
        return Result.failed(e.getMessage());
    }

    /**
     * 不打印日志异常
     *
     * @param e e
     * @return ResponseEntity
     */
    @ExceptionHandler(NoLogException.class)
    @ResponseBody
    public Result noLogException(NoLogException e) {
        return Result.failed(e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<?> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    /**
     * 数据绑定异常
     * 一般为表单验证
     *
     * @param e e
     * @return ResponseEntity
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result bindException(BindException e) {
        return Result.failed(e.getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public Result authenticationException(AuthenticationException e) {
        return Result.failed(e);
    }

}
