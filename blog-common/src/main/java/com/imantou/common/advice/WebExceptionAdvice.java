package com.imantou.common.advice;


import com.imantou.common.enums.ResultEnum;
import com.imantou.common.exception.*;
import com.imantou.common.response.ResponseWrapped;
import com.imantou.common.verify.exception.ParamsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;


/**
 * @author yhht
 * 定义统一异常处理
 */
@Slf4j
@RestControllerAdvice
public class WebExceptionAdvice {
//    /**
//     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
//     *
//     * @param binder
//     */
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//    }
//
//    /**
//     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
//     *
//     * @param model
//     */
//    @ModelAttribute
//    public void addAttributes(Model model) {
//        model.addAttribute("author", "imantou");
//    }

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
     * 处理自定义异常
     *
     * @param e
     */
    @ExceptionHandler(value = CaptchaException.class)
    @ResponseBody
    public ResponseWrapped<Object> exceptionHandler(CaptchaException e) {
        log.error("登录校验异常", e);
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

//    /**
//     * 处理403异常
//     */
//    @ExceptionHandler(value = UnauthorizedException.class)
//    @ResponseBody
//    public ResponseWrapped<Object> exceptionHandler(UnauthorizedException e) {
//        log.error("403无访问权限:", e);
//        return ResponseWrapped.error(ResultEnum.ERROR_UNAUTHORIZED);
//    }

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
     * 参数校验异常
     */
    @ExceptionHandler(ParamsException.class)
    @ResponseBody
    public ResponseWrapped<Object> exceptionHandler(ParamsException exception) {
        return ResponseWrapped.error(exception.getMessage());
    }

    /**
     * redis服务错误异常
     */
    @ExceptionHandler(value = QueryTimeoutException.class)
    @ResponseBody
    public ResponseWrapped<Object> exceptionHandler(QueryTimeoutException e) {
        log.error("redis连接超时", e);
        return ResponseWrapped.error(ResultEnum.ERROR_REDIS_OUT_TIME);
    }

//    /**
//     * 限流异常
//     */
//    @ExceptionHandler(RateLimitException.class)
//    @ResponseBody
//    public ResponseWrapped<Object> exceptionHandler(RateLimitException e) {
//        log.error("限流异常", e);
//        return ResponseWrapped.error(e.getMessage());
//    }
//
//    /**
//     * 分布式锁异常
//     */
//    @ExceptionHandler(DistributedLockException.class)
//    @ResponseBody
//    public ResponseWrapped<Object> exceptionHandler(DistributedLockException e) {
//        log.error("限流异常", e);
//        return ResponseWrapped.error(e.getMessage());
//    }

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
