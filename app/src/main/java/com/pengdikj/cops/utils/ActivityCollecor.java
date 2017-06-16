package com.pengdikj.cops.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 *	栈管理
 */

public class ActivityCollecor {
    public static List<Activity> acticities = new ArrayList<Activity>();
	
	public static void addActivity(Activity activity){
		acticities.add(activity);
	}
	
	public static void removeActivity(Activity activity){
		acticities.remove(activity);
	}
	
	public static void finishAll(){
		for(Activity activity2:acticities){
			if(! activity2.isFinishing()){
				activity2.finish();
			}
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public static void finishActivityclass(Class<?> cls) {
		if (acticities != null) {
			for (Activity activity : acticities) {
				if (activity.getClass().equals(cls)) {
					acticities.remove(activity);
					finishActivity(activity);
					break;
				}
			}
		}

	}

	/**
	 * 结束指定的Activity
	 *
	 * @param activity
	 */

	public  static void finishActivity(Activity activity) {

		if (activity != null) {
			acticities.remove(activity);
			activity.finish();
		}
	}

	public static boolean isExsitMianActivity(Context context,Class<?> cls){
		Intent intent = new Intent(context, cls);
		ComponentName cmpName = intent.resolveActivity(context.getPackageManager());
		boolean flag = false;
		if (cmpName != null) { // 说明系统中存在这个activity
			ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
			List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(10);
			for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
				if (taskInfo.baseActivity.equals(cmpName)) { // 说明它已经启动了
					flag = true;
					break;  //跳出循环，优化效率
				}
			}
		}
		return flag;
	}
}