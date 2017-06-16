package com.pengdikj.cops.recorder.ui;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.letv.recorder.util.LeLog;
import com.pengdikj.cops.BuildConfig;
import com.pengdikj.cops.R;
import com.pengdikj.cops.recorder.PublisherSkinView;
import com.pengdikj.cops.recorder.SettingActivity;
import com.pengdikj.cops.recorder.SkinParams;
import com.pengdikj.cops.recorder.ToastUtils;

/**
 * 移动直播推流界面
 * 在移动直播中，推流器只认识推流地址。
 * 这个Activity 需要用户传入三个值 isVertical 是否竖屏录制、streamUrl 推流地址、pushName 推流名称
 */
public class RecorderActivity extends Activity {
    private boolean isVertical;

    private String pushSteamUrl;
    private String pushName;
    private PublisherSkinView skinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        isVertical = getIntent().getBooleanExtra("isVertical", false);
        pushSteamUrl = getIntent().getStringExtra("streamUrl");
        pushName = getIntent().getStringExtra("pushName");
        if (TextUtils.isEmpty(pushSteamUrl)) {
            ToastUtils.showShort(this,"不能传入空的推流地址");
        }
        LeLog.w("推流地址是:" + pushSteamUrl);
        setContentView(R.layout.activity_stream_recorder);
        skinView = (PublisherSkinView) findViewById(R.id.psv_stream_recorder);
        SkinParams params = skinView.getSkinParams();
        params.setLanscape(!isVertical);
        test(params);
        skinView.initPublish(pushSteamUrl);
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

            findViewById(R.id.btn_updata).setVisibility(View.GONE);
            findViewById(R.id.btn_updata).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    skinView.updataFile();
                }
            });
        }
    }
}
