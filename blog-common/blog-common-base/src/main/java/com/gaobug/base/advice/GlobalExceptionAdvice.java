package com.gaobug.base.advice;


import com.gaobug.base.enums.ResultEnum;
import com.gaobug.base.exception.BusinessException;
import com.gaobug.base.response.ResponseWrapped;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;


/**
 * @author gaobug
 * 定义统一异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * 处理自定义异常
     *
     * @param e
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResponseWrapped<Object> exceptionHandler(BusinessException e) {
        log.error("业务异常", e);
        return ResponseWrapped.error(e);
    }

    /**
     * 处理空指针异常
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public ResponseWrapped<Object> exceptionHandler(NullPointerException e) {
        log.error("空指针异常:", e);
        return ResponseWrapped.error(ResultEnum.ERROR);
    }

    /**
     * 处理404异常
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    public ResponseWrapped<Object> exceptionHandler(NoHandlerFoundException e) {
        log.error("404异常:", e);
        return new ResponseWrapped<>(ResultEnum.ERROR_NOT_FOUND);
    }

    /**
     * 处理请求方式错误异常
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseWrapped<Object> exceptionHandler(HttpRequestMethodNotSupportedException e) {
        log.error("错误的请求方法", e);
        return ResponseWrapped.error(ResultEnum.ERROR_METHOD);
    }

    /**
     * 处理非法参数异常
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    public ResponseWrapped<Object> exceptionHandler(IllegalArgumentException e) {
        log.error("非法参数错误", e);
        return ResponseWrapped.error(e.getMessage());
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseWrapped<Object> exceptionHandler(Exception e) {
        log.error("其他异常", e);
        return ResponseWrapped.error(ResultEnum.ERROR);
    }
}
