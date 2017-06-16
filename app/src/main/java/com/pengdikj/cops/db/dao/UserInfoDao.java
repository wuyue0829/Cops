package com.pengdikj.cops.db.dao;

import com.j256.ormlite.dao.Dao;
import com.pengdikj.cops.entity.UserInfo;

import java.sql.SQLException;

public class UserInfoDao {

    private Dao<UserInfo, Integer> dao;

	public UserInfoDao(Dao<UserInfo, Integer> dao) {
		this.dao = dao;
	}
	
	/** 新增用户缓存信息
	 * @param ucPo
	 */
	public Boolean save(UserInfo ucPo)
	{
		try {
			dao.create(ucPo);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	/** 编辑用户缓存信息
	 * @param ucPo
	 */
	public Boolean update(UserInfo ucPo)
	{
		try {
			dao.update(ucPo);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	/** 删除用户缓存信息
	 * @param ucPo
	 */
	public Boolean delete(UserInfo ucPo)
	{
		try {
			dao.delete(ucPo);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	/** 删除用户信息
	 */
	public Boolean deleteAll()
	{
		try {
			dao.delete(dao.deleteBuilder().prepare());
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
}
