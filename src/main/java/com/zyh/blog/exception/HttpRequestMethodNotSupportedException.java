package com.zyh.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author ZYH
 * @Date 2020/9/10 18:36
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class HttpRequestMethodNotSupportedException extends RuntimeException {
    public HttpRequestMethodNotSupportedException() {
        super();
    }

    public HttpRequestMethodNotSupportedException(String message) {
        super(message);
    }

    public HttpRequestMethodNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }
}
