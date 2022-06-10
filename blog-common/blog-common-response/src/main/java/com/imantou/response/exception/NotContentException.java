package com.imantou.response.exception;

import com.imantou.response.enums.ResultEnum;
import lombok.*;

import java.io.Serializable;

/**
 * @author hzq
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class NotContentException extends BusinessException implements Serializable {

    public NotContentException() {
        super(ResultEnum.SUCCESS_NOT_CONTENT);
    }

    public NotContentException(String msg) {
        super(msg);
    }

    public NotContentException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        super.setCode(resultEnum.getCode());
    }
}
