package com.pengdikj.cops.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.pengdikj.cops.entity.UserInfo;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class DataHelper extends OrmLiteSqliteOpenHelper {

	public static final String DATABASE_NAME = "shangluo.db";
    private static final int DATABASE_VERSION = 1;

	private static DataHelper staticDB;
	private Context context;

	@SuppressWarnings("rawtypes")
	Map<String, Dao> daoMaps = null; // 所有DAO的集合

	public DataHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context=context;
		initDaoMaps();		
	}

	public static DataHelper getDataHelper(Context context) {
		if (staticDB == null) {
			staticDB = new DataHelper(context);
		}
		return staticDB;
	}

	public static SQLiteDatabase getDB()
	{
		return staticDB.getWritableDatabase();
	}

	@SuppressWarnings("rawtypes")
	private void initDaoMaps() {
		daoMaps = new HashMap<String, Dao>();

		daoMaps.put("userInfo", null);  //用户缓存表


	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {

			TableUtils.createTable(connectionSource, UserInfo.class);

		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {

	}

	/**
	 * 插入初始数据
	 * 
	 */
	private void initData(SQLiteDatabase db) {
		initLibExercise(db);

	}

	//插入运动类型
	private void initLibExercise(SQLiteDatabase db) {	
//      executeSQLFile(db,R.raw.lib_exercise);
	}


	@Override
	public void close() {
		super.close();
		daoMaps.clear();
	}

	public Dao<UserInfo, Integer> getUserInfoDao(){
		Dao<UserInfo, Integer> accountDao = daoMaps.get("userInfo");
		if(accountDao == null){
			try {
				accountDao = getDao(UserInfo.class);
			} catch (SQLException e) {
			}
		}
		return accountDao;
	}
}
