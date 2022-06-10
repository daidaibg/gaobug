package com.gaobug.response.exception;

import com.gaobug.response.enums.ResultEnum;
import lombok.*;

import java.io.Serializable;

/**
 * @author hzq
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class BusinessException extends RuntimeException implements Serializable {

    private Integer code = ResultEnum.ERROR.getCode();

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
}
