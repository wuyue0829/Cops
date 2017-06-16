package com.pengdikj.cops;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pengdikj.cops.login.LoginActivity;
import com.pengdikj.cops.oneKey.OnkeyPoliceActivity;
import com.pengdikj.cops.ui.A1_HomeActivity;
import com.pengdikj.cops.utils.ShortcutUtil;
import com.pengdikj.cops.utils.SysConfig;

public class MainActivity extends Activity implements View.OnClickListener{


    public static final String IS_SHORTCUT_CREATE =  "is_shortcut_create";  //快捷方式是否创建

    private final int DONE = 1001; //完成
    private final int WELCOME = 2001; //跳到欢迎界面
    private final int GOHOME = 3001;
    private final int UPDATE_TEAY_TIME = 103;
    private static final int SLEEP = 2000 ; //启动页面显示时间
    private int delayTime = 4;// 广告4秒倒计时
    private boolean adIsFinish = false;
    private boolean isChickAd = false;

    private Context mContext ;
    private SysConfig sysConfig;

    private View poster;  //封面
    private View welcomePanel;
    private ViewPager welcomePager;
    private RelativeLayout mainPoster;
    private TextView dTime;
    private static final int BAIDU_ACCESS_FINE_LOCATION = 10001;

    /**
     * 根据判断是否开始欢迎页面
     */
    private Handler myHandler =  new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case DONE:
                    goHome();
                    break;
                case WELCOME: //开始欢迎界面的出现
                    goWelcome();
                    break;
                case GOHOME:
                    goHome();
                    break;
            }
            super.handleMessage(msg);
        };
    };

    //欢迎界面图片
    private static int[] picResIDs = {
            /*pic_splash00,
            pic_splash01,
            pic_enter,*/
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
    }

    private void initView(){
        mContext = this;
        sysConfig = SysConfig.getConfig(mContext);
        poster = findViewById(R.id.welcomePoster);  //封面
        welcomePanel = findViewById(R.id.welcomePanel); //欢迎系列图的容器
        mainPoster = (RelativeLayout) findViewById(R.id.mainPoster);
        welcomePager = (ViewPager) findViewById(R.id.welcomePager);
        dTime = (TextView) findViewById(R.id.tv_time);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.WRITE_SETTINGS},
                        BAIDU_ACCESS_FINE_LOCATION);
                return;
            }else{
                new Thread(myRunnable).start();
            }
        }else{
            new Thread(myRunnable).start();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case BAIDU_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new Thread(myRunnable).start();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.camera_permission_location, Toast.LENGTH_LONG)
                            .show();
                    new Thread(myRunnable).start();
                }
                break;
            }
            default:
                break;
        }
    }

    /**
     * 用户是否第一次进入
     *
     */
    private Runnable myRunnable = new Runnable() {

        @Override
        public void run() {
            try {
                long now1 = SystemClock.elapsedRealtime();  //当前时间1
                /*************************************
                 **写入屏幕分辨率**
                 *************************************/
                DisplayMetrics metric = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metric);
                sysConfig.setScreenWidth(metric.widthPixels);
                /*************************************
                 **判断版本有无更新,有则跳出欢迎界面,无则推入完成消息**
                 *
                 *************************************/
                Message msg = myHandler.obtainMessage();
                msg.what = DONE;

                long now2 = SystemClock.elapsedRealtime();  //当前时间2
                if(now2 - now1 < SLEEP){   //沉睡1秒钟
                    Thread.sleep(SLEEP - (now2 - now1));
                }
                myHandler.sendMessage(msg);
            } catch (InterruptedException e) {
            }
        }
    };

    /**
     * 进入首页
     */
    private void goHome(){
        /**********************************
         * 创建桌面快捷方式,不提示,不友好
         **********************************/
        if(!sysConfig.isOnceConfig(IS_SHORTCUT_CREATE)){
            ShortcutUtil.createShortCut(MainActivity.this, R.mipmap.ic_launcher, R.string.app_name);
        }

        /*if(!ActivityCollecor.isExsitMianActivity(mContext,A1_HomeActivity.class)){
            startActivity(new Intent(mContext, A1_HomeActivity.class));
            this.finish();
        }*/
        if(sysConfig.getUserID_()>0){
            if(sysConfig.getUserSex().equals("1")){
                startActivity(new Intent(mContext, A1_HomeActivity.class));
            }else{
                startActivity(new Intent(mContext, OnkeyPoliceActivity.class));
            }

        }else{
            startActivity(new Intent(mContext, LoginActivity.class));
        }

        finish();
    }

    /**
     * 开始欢迎界面
     */

    private void goWelcome(){
        //隐藏封面---这里可以加动画效果
        welcomePanel.setVisibility(View.VISIBLE);
        mainPoster.setVisibility(View.GONE);
        welcomePager.setVisibility(View.VISIBLE);
        poster.setVisibility(View.GONE);
        welcomePager.setAdapter(new WelcomePagerAdaptar());
        welcomePager.setOnPageChangeListener(pageChange);
    }

    //页面改变监听
    private ViewPager.OnPageChangeListener pageChange = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int index) {

        }
        @Override
        public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels) {}
        @Override
        public void onPageScrollStateChanged(int arg0) {}
    };

    @Override
    public void onClick(View v) {

    }


    /** ViewPager 适配器	 */
    public class WelcomePagerAdaptar extends PagerAdapter {

        @Override
        public int getCount() {
            return picResIDs.length;
        }
        @Override
        public boolean isViewFromObject(View v1, Object o2) {
            return v1 == o2;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = productView(position);
            container.addView(view);
            return view;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        //生成图片文字VIEW
        private View productView(int position){
            int imgId = picResIDs[position];
            ImageView picImageView = new ImageView(mContext);
            picImageView.setImageResource(imgId);
            if(position == picResIDs.length - 1){
                picImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //最后一个了跳转了
                        Message msg = myHandler.obtainMessage();
                        msg.what = GOHOME;
                        myHandler.sendMessage(msg);
                    }
                });
            }
            return picImageView;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);// 必须要调用这句
    }
}
