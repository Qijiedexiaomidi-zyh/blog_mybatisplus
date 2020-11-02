package com.zyh.blog.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author ZYH
 * @Date 2020/9/10 16:26
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
@ControllerAdvice
public class ControllerException {

    private static final Logger logger = LoggerFactory.getLogger(ControllerException.class);

    //设置要拦截的错误
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e) throws Exception {
        logger.error("Request URL: {}, Exception: {}",request.getRequestURI(),e.getMessage());
        //如果有标注特定状态码的异常错误，就会被springboot本身处理，而不是返回到error界面
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw e;
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        //指定要返回到哪个界面，会被thymeleaf模板所修饰
        mv.setViewName("error/error");
        return mv;
    }
}
