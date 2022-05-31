package com.imantou.advice.exception;

import org.springframework.http.HttpStatus;

/**
 * 参数校验异常
 *
 * @author hzq
 * @date 2021/11/22
 */
public class RemoteCallException extends BusinessException {

    public RemoteCallException(String message) {
        super(message);
    }

    public RemoteCallException(HttpStatus httpStatus) {
        super(httpStatus.getReasonPhrase());
        super.setCode(httpStatus.value());
    }
}
