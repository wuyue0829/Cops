package com.pengdikj.cops.db.bll;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.pengdikj.cops.db.DataHelper;
import com.pengdikj.cops.db.dao.UserInfoDao;
import com.pengdikj.cops.entity.UserInfo;

/**
 * 作者：wuyue on 2017/6/5 0005 14:20
 * 邮箱：yy107829@sina.com
 */

public class BllUserInfo {
    private Context context;
    private UserInfoDao userInfoDao;

    public BllUserInfo(Context context) {
        this.context = context;
        Dao<UserInfo, Integer> dao = DataHelper.getDataHelper(this.context).getUserInfoDao();
        this.userInfoDao = new UserInfoDao(dao);
    }

    /**
     * 保存用户信息
     * @param userInfo
     */
    public void save(UserInfo userInfo){
        this.userInfoDao.save(userInfo);
    }

    /**
     *更新用户信息
     */
    public void update(UserInfo userInfo){
        this.userInfoDao.update(userInfo);
    }

    /**
     * 删除用户信息
     * @param userInfo
     */
    public void delete(UserInfo userInfo){
        this.userInfoDao.delete(userInfo);
    }

    /**
     * 删除所有用户信息
     *
     */
    public void deleteAll(){
        this.userInfoDao.deleteAll();
    }

}
