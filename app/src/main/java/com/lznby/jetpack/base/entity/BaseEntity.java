package com.lznby.jetpack.base.entity;

/**
 * @author Lznby
 */
public class BaseEntity<T> extends Object{
    /**
     * 错误消息
     */
    private String message;
    /**
     * 错误代码
     */
    private int code;
    /**
     * 数据
     */
    private T data;

    public BaseEntity() {
    }

    public BaseEntity(String message, int code, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}