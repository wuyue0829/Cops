package com.pengdikj.cops.utils;

import android.util.Log;

public class LogUtil {
    public static final boolean DEBUG = true;//开启debug
	
	public static void e(String tag,String msg) {
		if(DEBUG){
			Log.e(tag, msg);
		}
	}
	public static void d(String tag,String msg) {
		if(DEBUG){
			Log.d(tag, msg);
		}
	}
	public static void i(String tag,String msg) {
		if(DEBUG){
			Log.i(tag, msg);
		}
	}
	public static void w(String tag,String msg) {
		if(DEBUG){
			Log.w(tag, msg);
		}
	}
	public static void v(String tag,String msg) {
		if(DEBUG){
			Log.v(tag, msg);
		}
	}
	public static void e(String msg) {
		if(DEBUG){
			Log.e("debug", msg);
		}
	}
	/**
	 * 没有必要请勿用
	 * @param tag
	 * @param msg
	 */
	public static void destroy(String tag,String msg) {
		if(DEBUG){
			Log.wtf(tag, msg);
		}
	}
	
}
