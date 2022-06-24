package com.gaobug.base.exception;

import com.gaobug.base.enums.ResultEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author gaobug
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
