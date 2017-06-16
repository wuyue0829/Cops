package com.pengdikj.cops.utils.constant;

/**
 * 作者：Macyy on 2017/3/20 0020 14:28
 * 邮箱：yy107829@sian.com
 */
public class NetConstant {

    public static final String URL = "http://192.168.1.254:8080/";

//    public static final String URL = "http://222.73.135.133:8088/";

    //登录
    public static final String USERLOGIN = "police_affairs/api/user/userLogin";
    //上传Gps
    public static final String UPLOADGPS = "police_affairs/api/user/upload/gps";
    //上传图片
    public static final String UPLOADPIC = "police_affairs/api/file/upload";
    //一键报警
    public static final String ONEKEY = "police_affairs/api/alarm";
    //获取历史报警记录
    public static final String TASK = "police_affairs/api/user/task";
    //上班签到
    public static final String SIGN = "police_affairs/api/user/sign";
    //绑定照片
    public static final String BIND = "police_affairs/api/alarm/bind/photos";
    //获取实时警情
    public static final String UNTREATED = "police_affairs/api/alarm/untreated";
    //接受警情
    public static final String ACCEPT = "police_affairs/api/alarm/accept";
    //接受警情
    public static final String ARRIVE = "police_affairs/api/alarm/arrive";
    //绑定信鸽
    public static final String EQUIPMENT = "police_affairs/api/user/equipment";
    //绑定直播地址
    public static final String BINDADDRESS = "police_affairs/api/user/live";
}