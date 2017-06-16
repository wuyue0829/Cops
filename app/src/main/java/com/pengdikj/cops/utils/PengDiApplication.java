package com.pengdikj.cops.utils;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

import com.hss01248.dialog.StyledDialog;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.zhy.autolayout.config.AutoLayoutConifg;

import me.xiaopan.sketch.Sketch;

/**
 * 作者：Macyy on 2017/3/20 0020 11:51
 * 邮箱：yy107829@sian.com
 */
public class PengDiApplication extends Application{

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        //初始化AutoLayout完成自适应
        AutoLayoutConifg.getInstance().useDeviceSize();
        //初始化dialog
        StyledDialog.init(this);

        XGPushConfig.enableDebug(getApplicationContext(), true);
        XGPushManager.registerPush(getApplicationContext());
    }

    public static Context getApp() {
        return mContext;
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        ImageLoader.getInstance().init(config);
    }


    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            Sketch.with(getBaseContext()).onTrimMemory(level);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            Sketch.with(getBaseContext()).onLowMemory();
        }
    }
}
