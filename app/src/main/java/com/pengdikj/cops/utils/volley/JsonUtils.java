package com.pengdikj.cops.utils.volley;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pengdikj.cops.utils.LogUtil;


public class JsonUtils {
	private static Gson gson =  new GsonBuilder().serializeNulls().create();

	public static <T> T object(String json, Class<T> classOfT) {
		LogUtil.e("result==",json);
		return gson.fromJson(json, classOfT);
	}
	public static <T> String toJson(Class<T> param) {
		return gson.toJson(param);
	}
}