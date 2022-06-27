package com.gaobug.base.exception;

import com.gaobug.base.enums.ResultEnum;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author gaobug
 */
@EqualsAndHashCode(callSuper = false)
public class CaptchaException extends BusinessException implements Serializable {

    public CaptchaException(ResultEnum resultEnum) {
        super(resultEnum);
    }

}
