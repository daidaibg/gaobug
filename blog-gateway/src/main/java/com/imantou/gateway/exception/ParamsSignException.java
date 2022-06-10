
package com.gaobug.gateway.exception;

/**
 * 请求参数签名异常
 * @author gaobug
 */
public class ParamsSignException extends RuntimeException {

    public ParamsSignException(String message) {
        super(message);
    }
}
