package com.pengdikj.cops.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;
import java.util.Map;

/**
 * 存放各种系统参数
 * @author Macyy
 *
 */
public class SysConfig {
    private final static String SYS_PARMS = "sys_parms_pengdi";
	private SharedPreferences config ;

	private static SysConfig sysConfig;

	public SysConfig(Context context){
		config = context.getSharedPreferences(SYS_PARMS, 0);
	}

	//单例模式
	public static SysConfig getConfig(Context context){
		if(sysConfig == null){
			sysConfig = new SysConfig(context);
		}
		return sysConfig;
	}

	/*---------------------分割线----------------------------------------*/

	//获取原先配置中的版本号
	public int getAppVesion(){ 
		return config.getInt("version", 0); 	
	}

	//写入最新的版本号-
	public void setAppVesion(int version){
		config.edit().putInt("version", version).commit();
	}

	//获取服务器中最新的版本号
	public int getSerAppVesion(){ 
		return config.getInt("serversion", 1); 	
	}

	//写入服务器中最新的版本号-
	public void setSerAppVesion(int version){
		config.edit().putInt("serversion", version).commit();
	}

	//获取用户token
	public String getXgToken(){
		return config.getString("Xgtoken", "");
	}
	//写入用户token
	public void setXgToken(String token){
		config.edit().putString("Xgtoken", token).commit();
	}


	//获取用户token
	public String getToken(){
		return config.getString("token", "");
	}
	//写入用户token
	public void setToken(String token){
		config.edit().putString("token", token).commit();
	}
	//获取用户token
	public String getVerfiCode(){
		return config.getString("VerfiCode", "");
	}
	//写入用户token
	public void setVerfiCode(String VerfiCode){
		config.edit().putString("VerfiCode", VerfiCode).commit();
	}
	//获取用户头像
	public String getHeadImage(){
		return config.getString("headImage", "");
	}
	//写入用户头像
	public void setHeadImage(String headImage){
		config.edit().putString("headImage", headImage).commit();
	}
	//获取用户ID
	public String getUserID(){
		return config.getString("userID", "0");
	}
	/**
	 * 获取int 的用户ID
	 * @return
	 */
	public int getUserID_(){
		return DoNumberUtil.intNullDowith(getUserID());
	}
	//写入用户ID
	public void setUserID(String userID){
		config.edit().putString("userID", userID).commit();
	}
	//获取用户性别
	public String getUserSex(){
		return config.getString("sex", "1");
	}
	//写入用户性别
	public void setUserSex(String sex){
		config.edit().putString("sex", sex).commit();
	}


	//获取用户性别
	public String getUserType(){
		return config.getString("Type", "1");
	}
	//写入用户性别
	public void setUserType(String sex){
		config.edit().putString("Type", sex).commit();
	}
	
	//获取用户电话
	public String getUserPhoneNum(){
		return config.getString("phoneNum", "");
	}

	//写入用户电话
	public void setUserPhoneNum(String phoneNum){
		config.edit().putString("phoneNum", phoneNum).commit();
	}

	//获取用户电话
	public String getUserName(){
		return config.getString("userName", "");
	}

	//写入用户电话
	public void setUserName(String userName){
		config.edit().putString("userName", userName).commit();
	}

	//获取屏幕宽度
	public int getScreenWidth(){
		return config.getInt("screenWidth", 0);
	}
	//写入屏幕宽度
	public void setScreenWidth(int windth){
		config.edit().putInt("screenWidth", windth).commit();
	}

	//写入用户过期时间
	public void setExpirationDate(String token){
		config.edit().putString("expirationDate", MD5Util.encodeUnburden(token)).commit();
	}
	//获取用户过期日
	public String getExpiredDay(){
		return MD5Util.decodeUnburden(config.getString("expiredDay", "0"));
	}
	//写入用户过期日
	public void setExpiredDay(String token){
		config.edit().putString("expiredDay", MD5Util.encodeUnburden(token)).commit();
	}

	//获取广告获取时间
	public String getGuangGaoDate(){
		return config.getString("guangaoDate", "");
	}

	//写入广告获取时间
	public void setGuangGaoDate(String date){
		config.edit().putString("guangaoDate", date).commit();
	}

	//获取顶部广告链接
	public String getGuangGaoLink(){
		return config.getString("guangaoLink", "");
	}
	//写入顶部广告链接
	public void setGuangGaoLink(String link){
		config.edit().putString("guangaoLink", link).commit();
	}
	//获取顶部广告图片地址
	public String getGuangGaoPath(){
		return config.getString("guangaoPath", "");
	}
	//写入顶部广告图片地址
	public void setGuangGaoPath(String path){
		config.edit().putString("guangaoPath", path).commit();
	}	
	//获得启动次数
	public int getStartCount(){
		return DoNumberUtil.intNullDowith(config.getString("startcount", ""));
	}
	//写入启动次数
	public void setStartCount(int count){
		config.edit().putString("startcount", DoNumberUtil.IntToStr(count)).commit();
	}
	//获得是否发送了微博
	public String getWeiboSendFlag(){
		return config.getString("issendweibo", "");
	}
	//写入是否发送了微博
	public void setWeiboSendFlag(String flag){
		config.edit().putString("issendweibo", flag).commit();
	}





	/**
	 * 今天是否显示过的标记
	 * @return
	 */
	public boolean isTodayShow(String action){
		boolean flag = false;
		String showDate = config.getString(action, "");
		String today = DateUtil.getToday();
		if(today.equals(showDate)){
			flag = true;
		}else {
			config.edit().putString(action, today).commit();
		}
		return flag;
	}

	/**
	 * 只显示一次的项目
	 * @param onceTpe
	 * @return
	 */
	public boolean isOnceConfig(String onceTpe){
		boolean flag = config.getBoolean(onceTpe, false);
		if(!flag){
			config.edit().putBoolean(onceTpe, true).commit();
		}
		return flag;
	}
	/***
	 * 设置自定义配置信息
	 * @param custom_action
	 * @param info  配置内容
	 * @return
	 */
	public void setCustomConfig(String custom_action,String info){
		config.edit().putString(custom_action, info).commit();
	}
	/***
	 * 获取自定义配置信息
	 * @return
	 */
	public String getCustomConfig(String custom_action,String defaultStr){
		return config.getString(custom_action, defaultStr);
	}

	/***
	 * 获取自定义配置信息
	 * @return
	 */
	public String getCustomConfig(String custom_action){
		return config.getString(custom_action, "");
	}
	/***
	 * 获取自定义配置信息
	 * @return
	 */
	public Date getCustomConfigDate(String custom_action){
		return DateUtil.getDate("yyyy-MM-dd HH:mm:ss", config.getString(custom_action, ""));
	}
	/**
	 * 清楚数据方法，只能在退出程序时用
	 */
	public void clear(){
		Map<String, ?> m = config.getAll();
		for (String key : m.keySet()) { 
			//LogUtil.e("key="+key);
			if(key.equals("screenWidth")
					||key.equals("version")
					){
				setCustomConfigInt(key,0);
			}else{
				setCustomConfig(key,null);
			}
		}  
	}
	/***
	 * 设置自定义配置信息
	 * @param custom_action
	 * @param info  配置内容
	 * @return
	 */
	public void setCustomConfigInt(String custom_action,int info){
		config.edit().putInt(custom_action, info).commit();
	}
	
	/***
	 * 设置自定义配置信息
	 * @param custom_action
	 * @param info  配置内容
	 * @return
	 */
	public void setCustomConfigLong(String custom_action,long info){
		config.edit().putLong(custom_action, info).commit();
	}
	
	public long getCustomConfigLong(String custom_action){
		return config.getLong(custom_action,0);
	}
}
