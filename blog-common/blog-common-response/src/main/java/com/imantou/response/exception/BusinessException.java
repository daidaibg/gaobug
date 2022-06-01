package com.imantou.response.exception;

import com.imantou.response.enums.ResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author hzq
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessException extends RuntimeException implements Serializable {

    private Integer code = ResultEnum.ERROR.getCode();
    private final Boolean result = ResultEnum.ERROR.isSuccess();

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
}
