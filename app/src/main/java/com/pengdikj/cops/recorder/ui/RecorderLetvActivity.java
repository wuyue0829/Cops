package com.pengdikj.cops.recorder.ui;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.pengdikj.cops.BuildConfig;
import com.pengdikj.cops.R;
import com.pengdikj.cops.recorder.LePublisherSkinView;
import com.pengdikj.cops.recorder.SettingActivity;
import com.pengdikj.cops.recorder.SkinParams;

/**
 *	乐视云直播
 * 	在乐视云直播推流中，推流器只认识乐视云直播提供的三个参数
 * 	1、用户userId :在官网中可以获取
 *  2、用户私钥secretKey: 在官网中可以获取
 *  3、活动ID activityId: 在官网中创建活动后可以获取
 *  注意，在使用乐视云直播时 LetvPublisher.init(activityId, userId, secretKey); 必须首先调用
 *  
 */
public class RecorderLetvActivity extends Activity {

	protected static final String TAG = "RecorderActivity";

	private boolean isVertical = false;
	private String activityId;
	private LePublisherSkinView skinView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		super.onCreate(savedInstanceState);
		Window win = getWindow();
		win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		win.requestFeature(Window.FEATURE_NO_TITLE);
		activityId = getIntent().getStringExtra("letvStreamID");
		String userId = getIntent().getStringExtra("letvUserId");
		String secretKey = getIntent().getStringExtra("letvAppKey");

		isVertical = getIntent().getBooleanExtra("isVertical", false);

		setContentView(R.layout.activity_letv_recorder);
		skinView = (LePublisherSkinView) findViewById(R.id.lpsv_stream_recorder);
		SkinParams params = skinView.getSkinParams();
		params.setLanscape(!isVertical);
		test(params);
		skinView.initPublish(userId, secretKey, activityId);
	}

	@Override
	protected void onResume() {
		super.onResume();
		skinView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		skinView.onPause();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		skinView.onDestroy();
	}
	private void test(SkinParams params) {
		if (BuildConfig.DEBUG) {
			SettingActivity.jsonToObj(params, this);

			findViewById(R.id.btn_updata).setVisibility(View.VISIBLE);
			findViewById(R.id.btn_updata).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					skinView.updataFile();
				}
			});
		}
	}
}
