package com.imantou.common.response;

import com.imantou.common.enums.ResultEnum;
import com.imantou.common.exception.BusinessException;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yhht
 */
@Data
public class ResponseWrapped<T> implements Serializable {
    private static final long serialVersionUID = -138487439556390908L;
    private final Integer code;
    private final String msg;
    private final boolean success;
    private final T data;

    public ResponseWrapped() {
        this.code = ResultEnum.SUCCESS.getCode();
        this.msg = ResultEnum.SUCCESS.getMsg();
        this.success = ResultEnum.SUCCESS.isSuccess();
        this.data = (T) "";
    }

    public ResponseWrapped(String msg) {
        this.code = ResultEnum.SUCCESS.getCode();
        this.msg = msg;
        this.success = ResultEnum.SUCCESS.isSuccess();
        this.data = (T) "";
    }

    public ResponseWrapped(T data) {
        this.code = ResultEnum.SUCCESS.getCode();
        this.msg = ResultEnum.SUCCESS.getMsg();
        this.success = ResultEnum.SUCCESS.isSuccess();
        this.data = data;
    }

    public ResponseWrapped(String msg, T data) {
        this.code = ResultEnum.SUCCESS.getCode();
        this.msg = msg;
        this.success = ResultEnum.SUCCESS.isSuccess();
        this.data = data;
    }

    public ResponseWrapped(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
        this.success = resultEnum.isSuccess();
        this.data = (T) "";
    }

    public ResponseWrapped(ResultEnum resultEnum, String msg) {
        this.code = resultEnum.getCode();
        this.msg = msg;
        this.success = resultEnum.isSuccess();
        this.data = (T) "";
    }

    public ResponseWrapped(BusinessException e) {
        this.code = e.getCode();
        this.msg = e.getMessage();
        this.success = e.getResult();
        this.data = (T) "";
    }

    public ResponseWrapped(Integer code, String msg, boolean success, T data) {
        this.code = code;
        this.msg = msg;
        this.success = success;
        this.data = data;
    }

    /**
     * 成功
     */
    public static ResponseWrapped<Object> success() {
        return new ResponseWrapped<>();
    }

    /**
     * 成功
     */
    public static ResponseWrapped<Object> success(String msg) {
        return new ResponseWrapped<>(msg);
    }

    /**
     * 成功
     */
    public static ResponseWrapped<Object> success(Object data) {
        return new ResponseWrapped<>(data);
    }

    /**
     * 保存成功
     */
    public static ResponseWrapped<Object> successSave() {
        return new ResponseWrapped<>(ResultEnum.SUCCESS_SAVE);
    }

    /**
     * 更新成功
     */
    public static ResponseWrapped<Object> successUpdate() {
        return new ResponseWrapped<>(ResultEnum.SUCCESS_UPDATE);
    }

    /**
     * 更新成功
     */
    public static ResponseWrapped<Object> successDelete() {
        return new ResponseWrapped<>(ResultEnum.SUCCESS_DELETE);
    }

    /**
     * 失败
     */
    public static ResponseWrapped<Object> error() {
        return new ResponseWrapped<>(ResultEnum.ERROR);
    }

    /**
     * 失败
     */
    public static ResponseWrapped<Object> error(String msg) {
        return new ResponseWrapped<>(ResultEnum.ERROR, msg);
    }

    /**
     * 失败
     */
    public static ResponseWrapped<Object> error(ResultEnum resultEnum) {
        return new ResponseWrapped<>(resultEnum);
    }

    /**
     * 失败
     */
    public static ResponseWrapped<Object> error(BusinessException e) {
        return new ResponseWrapped<>(e);
    }

    /**
     * 失败
     */
    public static ResponseWrapped<Object> error(ResultEnum resultEnum, String msg) {
        return new ResponseWrapped<>(resultEnum, msg);
    }

}
