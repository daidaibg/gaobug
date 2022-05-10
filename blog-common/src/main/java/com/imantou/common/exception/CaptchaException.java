package com.imantou.common.exception;

import com.imantou.common.enums.ResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author yhht
 */
@EqualsAndHashCode(callSuper = false)
public class CaptchaException extends BusinessException implements Serializable {

    public CaptchaException(ResultEnum resultEnum) {
        super(resultEnum);
    }

}
