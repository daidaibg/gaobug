
package com.imantou.security.exception;

/**
 * 请求参数签名异常
 */
public class ParamsSignException extends RuntimeException {

    public ParamsSignException(String message) {
        super(message);
    }
}
