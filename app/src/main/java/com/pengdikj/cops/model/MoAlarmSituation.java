package com.pengdikj.cops.model;

/**
 * Created by yy on 2017/6/13.
 */

public class MoAlarmSituation {
    private String code;
    private String message;
    private MoAlarmSituations result;

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

    public MoAlarmSituations getResult() {
        return result;
    }

    public void setResult(MoAlarmSituations result) {
        this.result = result;
    }
}
