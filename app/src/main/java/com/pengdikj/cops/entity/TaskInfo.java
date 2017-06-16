package com.pengdikj.cops.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 作者：wuyue on 2017/6/12 0012 10:57
 * 邮箱：yy107829@sina.com
 */

@DatabaseTable(tableName="task_info")
public class TaskInfo {

    private static final long serialVersionUID = 1L;

    public TaskInfo() {

    }

    public TaskInfo(String id,String province, String city, String district, String address, String alarmDate,
                    String isAccept,String acceptDate,String remark,String alarmType) {
        super();
        this.province = province;
        this.city = city;
        this.district = district;
        this.address = address;
        this.alarmDate = alarmDate;
        this.isAccept = isAccept;
        this.acceptDate = acceptDate;
        this.remark = remark;
        this.alarmType = alarmType;
        this.id = id;
    }

    @DatabaseField(id=true)
    private String id;
    @DatabaseField
    private String province;

    @DatabaseField
    private String city;

    @DatabaseField
    private String district;

    @DatabaseField
    private String address;

    @DatabaseField
    private String alarmDate;

    @DatabaseField
    private String isAccept;

    @DatabaseField
    private String acceptDate;

    @DatabaseField
    private String remark;

    @DatabaseField
    private String alarmType;


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(String alarmDate) {
        this.alarmDate = alarmDate;
    }

    public String getIsAccept() {
        return isAccept;
    }

    public void setIsAccept(String isAccept) {
        this.isAccept = isAccept;
    }

    public String getId() {
        return id;
    }


    public String getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(String acceptDate) {
        this.acceptDate = acceptDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }
}
