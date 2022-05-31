
package com.imantou.security.exception;

/**
 * 请求参数解密签名异常
 */
public class DecryptRequestException extends RuntimeException {

    public DecryptRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
