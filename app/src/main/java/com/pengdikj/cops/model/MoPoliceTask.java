package com.pengdikj.cops.model;

/**
 * 作者：wuyue on 2017/6/12 0012 10:56
 * 邮箱：yy107829@sina.com
 */

public class MoPoliceTask {
    private String code;
    private String message;
    private MoTaskInfo result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MoTaskInfo getResult() {
        return result;
    }

    public void setResult(MoTaskInfo result) {
        this.result = result;
    }
}
