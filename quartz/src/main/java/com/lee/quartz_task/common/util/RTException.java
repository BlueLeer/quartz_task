package com.lee.quartz_task.common.util;

/**
 * @author WangLe
 * @date 2018/12/11 14:18
 * @description 异常类
 */
public class RTException extends RuntimeException {
    static final long serialVersionUID = 1L;
    private int code;
    private String msg;

    public RTException() {
        this("后台异常,请联系管理员");
    }

    public RTException(String msg) {
        this(500, msg);
    }

    public RTException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
