package com.imantou.common.exception;

import com.imantou.common.enums.ResultEnum;
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
