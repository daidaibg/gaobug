package com.gaobug.base.exception;


import com.gaobug.response.enums.ResultEnum;
import com.gaobug.response.exception.BusinessException;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author hzq
 */
@EqualsAndHashCode(callSuper = false)
public class CaptchaException extends BusinessException implements Serializable {

    public CaptchaException(ResultEnum resultEnum) {
        super(resultEnum);
    }

}
