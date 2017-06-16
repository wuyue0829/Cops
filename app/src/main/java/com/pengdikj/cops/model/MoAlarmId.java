package com.pengdikj.cops.model;

/**
 * 作者：wuyue on 2017/6/12 0012 17:19
 * 邮箱：yy107829@sina.com
 */

public class MoAlarmId {
    private String code;
    private String message;
    private MoAlarm result;

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

    public MoAlarm getResult() {
        return result;
    }

    public void setResult(MoAlarm result) {
        this.result = result;
    }
}
