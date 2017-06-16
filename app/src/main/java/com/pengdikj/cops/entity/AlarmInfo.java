package com.pengdikj.cops.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by yy on 2017/6/13.
 */

@DatabaseTable(tableName="alarm_info")
public class AlarmInfo {

    private static final long serialVersionUID = 1L;

    public AlarmInfo() {
    }


    public AlarmInfo(String alarmType,String address, String acceptDate, String isCritical, String isAccept,
                     String remark,String id,String alarmDate, String userPhone,String userName,String gender,String longitude,String latitude) {
        super();
        this.id = id;
        this.alarmType = alarmType;
        this.address = address;
        this.acceptDate = acceptDate;
        this.alarmDate = alarmDate;
        this.isCritical = isCritical;
        this.isAccept = isAccept;
        this.remark = remark;
        this.userPhone = userPhone;
        this.userName = userName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.gender = gender;
    }

    @DatabaseField(id=true)
    private String id;
    @DatabaseField
    private String alarmType;
    @DatabaseField
    private String address;
    @DatabaseField
    private String acceptDate;
    @DatabaseField
    private String isCritical;
    @DatabaseField
    private String isAccept;
    @DatabaseField
    private String remark;
    @DatabaseField
    private String alarmDate;
    @DatabaseField
    private String userPhone;
    @DatabaseField
    private String userName;
    @DatabaseField
    private String gender;
    @DatabaseField
    private String longitude;
    @DatabaseField
    private String latitude;


    public String getTaskId() {
        return id;
    }

    public void setTaskId(String id) {
        this.id = id;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(String acceptDate) {
        this.acceptDate = acceptDate;
    }

    public String getIsCritical() {
        return isCritical;
    }

    public void setIsCritical(String isCritical) {
        this.isCritical = isCritical;
    }

    public String getIsAccept() {
        return isAccept;
    }

    public void setIsAccept(String isAccept) {
        this.isAccept = isAccept;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(String alarmDate) {
        this.alarmDate = alarmDate;
    }
}
