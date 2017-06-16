package com.pengdikj.cops.recorder.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class StreamData {
	private SharedPreferences sp;
	private final static String DEFULT = "StreamData_";
	private Editor edt;
	
	public StreamData(Context context) {
		sp = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	private String pushStream;

	public String getPushStream() {
		pushStream = sp.getString(DEFULT +"pushStream", null);
		return pushStream;
	}
	public void setPushStream(String pushStream) {
		edt = sp.edit();
		this.pushStream = pushStream;
		edt.putString(DEFULT +"pushStream", pushStream);
		edt.apply();
	}
}
