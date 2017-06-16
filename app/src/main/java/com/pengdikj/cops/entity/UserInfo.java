package com.pengdikj.cops.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 作者：wuyue on 2017/6/5 0005 14:09
 * 邮箱：yy107829@sina.com
 */

@DatabaseTable(tableName="user_info")
public class UserInfo {

    private static final long serialVersionUID = 1L;

    public UserInfo() {

    }

    public UserInfo(String userId, String userName, String password, String phone, String identificationNumber,
                    String gender, String realName, String type,
                    String token) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.identificationNumber = identificationNumber;
        this.gender = gender;
        this.realName = realName;
        this.type = type;
        this.token = token;
    }

    @DatabaseField
    private String userId;

    @DatabaseField
    private String userName;

    @DatabaseField
    private String password;

    @DatabaseField
    private String phone;

    @DatabaseField
    private String identificationNumber;

    @DatabaseField
    private String gender;

    @DatabaseField
    private String realName;

    @DatabaseField
    private String type;//(1.警用2.民用)

    @DatabaseField
    private String token;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}