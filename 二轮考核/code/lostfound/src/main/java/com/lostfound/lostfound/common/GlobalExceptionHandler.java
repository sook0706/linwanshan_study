package com.lostfound.lostfound.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 * 作用：后端报错不暴露给用户，符合PDF安全要求
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public R handle(Exception e) {
        e.printStackTrace();
        return R.error("系统繁忙，请稍后再试");
    }
}