package com.pengdikj.cops.model;

/**
 * 作者：wuyue on 2017/6/5 0005 14:25
 * 邮箱：yy107829@sina.com
 */

public class MoLogin {
    private String code;
    private String message;
    private MoUserInfo result;

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

    public MoUserInfo getResult() {
        return result;
    }

    public void setResult(MoUserInfo result) {
        this.result = result;
    }
}
