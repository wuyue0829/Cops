package com.pengdikj.cops.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

public class AppInfoUtil {

	/**
     * 获取版本名
	 */
	public static String getAppVersionName(Context context) {
		String versionName = "";    
		try {    
			// ---get the package info---    
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;    
			if (versionName == null || versionName.length() <= 0) {    
				return "";
			}    
		} catch (Exception e) {    
		}    
		return versionName;    
	}
	/**
	 * 获取版本号
	 */
	public static int getAppVersionCode(Context context) {
		int versionCode = 1;
		try {    
			// ---get the package info--- 
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionCode = pi.versionCode;
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		return versionCode; 
	}
	
	/**
	 * 应用是否在运行
	 * @param context
	 * @return
	 */
	public static Boolean isAppRunning(Context context) 
	{
		boolean isAppRunning = false;
		ActivityManager am = (ActivityManager)
				context.getSystemService(Context.ACTIVITY_SERVICE);
	    List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
	    for (ActivityManager.RunningTaskInfo info : list) {
	        if (info.topActivity.getPackageName().equals(context.getPackageName()) 
	        		&& info.baseActivity.getPackageName().equals(context.getPackageName())
	        		&& (!info.baseActivity.getClassName().equals(info.topActivity.getClassName()))
	        		) {
	            isAppRunning = true;
	            break;
	        }
	    }
	    return isAppRunning;
	}
	
	/**
	 * 获取启动APp的意图, 如果该APP 已经启动
	 * @return
	 */
	public static Intent getOnlyIntent(){
		Intent appIntent = new Intent(Intent.ACTION_MAIN);  
		appIntent.addCategory(Intent.CATEGORY_LAUNCHER);  
		appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED );//
		appIntent.setComponent(new ComponentName("com.pdkeji.shane", "com.pdkeji.shane.MainActivity"));
		return appIntent;
	}
}