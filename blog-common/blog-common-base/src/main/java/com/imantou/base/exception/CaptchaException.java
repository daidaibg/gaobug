package com.imantou.base.exception;


import com.imantou.response.enums.ResultEnum;
import com.imantou.response.exception.BusinessException;
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
