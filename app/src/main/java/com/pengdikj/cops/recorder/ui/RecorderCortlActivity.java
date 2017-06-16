package com.pengdikj.cops.recorder.ui;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.letv.recorder.util.MD5Utls;
import com.pengdikj.cops.R;
import com.pengdikj.cops.model.MoLogin;
import com.pengdikj.cops.recorder.data.CreateStreamData;
import com.pengdikj.cops.recorder.data.LetvStreamData;
import com.pengdikj.cops.recorder.data.StreamData;
import com.pengdikj.cops.ui.BaseActivity;
import com.pengdikj.cops.utils.LogUtil;
import com.pengdikj.cops.utils.constant.NetConstant;
import com.pengdikj.cops.utils.volley.IRequest;
import com.pengdikj.cops.utils.volley.RequestJsonListener;
import com.pengdikj.cops.utils.volley.RequestParams;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 作者：wuyue on 2017/6/3 0003 11:37
 * 邮箱：yy107829@sina.com
 */

public class RecorderCortlActivity extends BaseActivity {
    // 移动直播推流域名，在官网移动直播创建应用后可拿到
    private static final String DEFAULT_DOMAINNAME = "20706.mpush.live.lecloud.com";
    // 移动直播推流签名密钥，在官网移动直播创建应用后可拿到
    private static final String DEFAULT_APPKEY = "ESSMUKTEQG2LMK9E5N5Z";
    // 移动直播推流地址， 当用户知道自己需要推流的地址后可以使用
    private static final String DEFAULT_PUSHSTREAM = "rtmp://20706.mpush.live.lecloud.com/live/demo";
    private static final String DEFAULT_PULLSTREAM = "rtmp://20706.mpull.live.lecloud.com/live/demo";


    private Button btnIsScreen;

    private CreateStreamData createStreamData;
    private LetvStreamData letvStreamData;
    private StreamData streamData;

    private EditText etDomainName;
    private TextView tvDomainName;
    private EditText etAppKey;
    private LinearLayout llAppKey;
    private TextView tvAppKey;
    private EditText etStreamId;
    private LinearLayout llStreamId;
    private TextView tvStreamId;

    private Button btnNoCreateStream;
    private RelativeLayout rlPlayerUrl;
    private TextView tvPlayerUrl;

    private int type = 0;
    private SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
    private boolean isLanscape = true;
    private String playUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView2Base(R.layout.activity_recordercortl);
        this.setTitle("警务上传");
        this.setToolBarLeftButtonByString(R.string.head_return);
        this.setBarColor(getResources().getColor(R.color.blue_cop));
        createStreamData = new CreateStreamData(this);
        streamData = new StreamData(this);
        letvStreamData = new LetvStreamData(this);
        //初始化视图
        initView();
        //动态申请权限
        checkSelfPermission();
        createPlayerUrl();
    }
    /**
     * 检查权限,获取所有需要的权限
     * 	当targetSdkVersion大于23并且打算在6.0手机上运行时,请动态申请SDK所需要的权限
     */
    public void checkSelfPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO)!=PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.MODIFY_AUDIO_SETTINGS,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE
            }, 0);
        }
    }
    /**
     * 初始化视图
     */
    private void initView(){
        findViewById(R.id.btn_submit_push).setOnClickListener(onClick);
        etDomainName = (EditText) findViewById(R.id.et_domain_name);
        tvDomainName = (TextView) findViewById(R.id.tv_domain_name);
        etAppKey = (EditText) findViewById(R.id.et_app_key);
        llAppKey = (LinearLayout) findViewById(R.id.ll_app_key);
        tvAppKey = (TextView) findViewById(R.id.tv_app_key);
        etStreamId = (EditText) findViewById(R.id.et_stream_id);
        llStreamId = (LinearLayout) findViewById(R.id.ll_stream_id);
        tvStreamId = (TextView) findViewById(R.id.tv_stream_id);
        btnNoCreateStream = (Button) findViewById(R.id.btn_create_stream);
        btnNoCreateStream.setOnClickListener(onClick);
        rlPlayerUrl = (RelativeLayout) findViewById(R.id.rl_player_url);
        tvPlayerUrl = (TextView)findViewById(R.id.tv_line);
        TextView tvCopy = (TextView)findViewById(R.id.tv_copy);
        tvCopy.setText(Html.fromHtml("<u>"+"复制"+"</u>"));
        tvCopy.setOnClickListener(onClick);
        btnIsScreen = (Button) findViewById(R.id.btn_turn);
        if(isLanscape){
            btnIsScreen.setBackgroundResource(R.mipmap.turn_on);
        }else{
            btnIsScreen.setBackgroundResource(R.mipmap.turn_off);
        }
        btnIsScreen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isLanscape = !isLanscape;
                if(isLanscape){
                    btnIsScreen.setBackgroundResource(R.mipmap.turn_on);
                }else{
                    btnIsScreen.setBackgroundResource(R.mipmap.turn_off);
                }
            }
        });
    }
    // 界面中所有的按钮点击是事件
    private View.OnClickListener onClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                // 开始直播 点击事件
                case R.id.btn_submit_push:
                    pushStream();
                    break;
                // 无推流地址中 生成播放地址 按钮点击事件
                case R.id.btn_create_stream: // 生成播放地址
                    createPlayerUrl();
                    break;
                default:
                    break;
            }
        }
    };
    /**
     * 生成播放地址，点击生成播放地址按钮后调用这个方法
     */
    private void createPlayerUrl(){
        // 按照拼接规则，拼接出一个播放地址
        playUrl = createStreamUrl(false);
        btnNoCreateStream.setBackgroundColor(0xffb2b2b2);
        btnNoCreateStream.setClickable(false);
        rlPlayerUrl.setVisibility(View.GONE);
        tvPlayerUrl.setText(playUrl);
        LogUtil.e(playUrl);
    }
    /**
     * 点击开始推流按钮后调用该方法
     */
    private void pushStream(){
        Intent intent = new Intent();
        ComponentName componentName = null;
        String pushUrl = "";
        switch (type) {
            case 0: // 无推流地址
                // 无推流地址界面中，获取 推流域名 签名密钥 流名称 三个参数
                componentName = new ComponentName(this,RecorderActivity.class);
                if(etDomainName.getText().toString() != null && !"".equals(etDomainName.getText().toString())){
                    createStreamData.setDomainName(etDomainName.getText().toString());
                }
                if(etAppKey.getText().toString() != null && !"".equals(etAppKey.getText().toString())){
                    createStreamData.setAppKey(etAppKey.getText().toString());
                }
                if(etStreamId.getText().toString() != null && !"".equals(etStreamId.getText().toString())){
                    createStreamData.setStreamId(etStreamId.getText().toString());
                    intent.putExtra("pushName", createStreamData.getStreamId());
                }else{
                    intent.putExtra("pushName", sysConfig.getUserID());
                }
                // 生成一个推流地址，并且把推流地址 传递到 RecorderActivity 中去
                pushUrl = createStreamUrl(true);
                intent.putExtra("streamUrl",pushUrl );
                break;
        }
        // 获取当前 横屏还是竖屏推流。并且把参数传递给推流界面
        intent.putExtra("isVertical", !isLanscape);
        intent.setComponent(componentName);
        startActivity(intent);

        RequestParams requestParams = new RequestParams();
        requestParams.put("userId",sysConfig.getUserID());
        requestParams.put("status","1");
        requestParams.put("address",playUrl);
        IRequest.post(mContext, NetConstant.BINDADDRESS, MoLogin.class,requestParams,new RequestJsonListener<MoLogin>() {

            @Override
            public void requestSuccess(MoLogin result) {
                // TODO Auto-generated method stub
            }
            @Override
            public void requestError(VolleyError e) {
                // TODO Auto-generated method stub
            }
        });
    }
    /**
     * **移动直播 **    生成一个 推流地址/播放地址 。这两个地址生成规则特别像
     * @param isPush 当前需要生成的是推流地址还是播放地址，true 推流地址 ，false 播放地址
     * @return 返回生成的地址
     */
    private String createStreamUrl(boolean isPush) {
        try {
            // 格式化，获取时间参数 。注意，当你在创建移动直播应用时，如果开启推流防盗链或播放防盗链 。那么你必须保证这个时间和中国网络时间一致
            String tm = format.format(new Date());
            // 获取无推流地址时 流名称，推流域名，签名密钥 三个参数
            String streamName = etStreamId.getText().toString().trim();
            String domainName = etDomainName.getText().toString().trim();
            String appkey = etAppKey.getText().toString().trim();
            etStreamId.setText(streamName);
            etDomainName.setText(domainName);
            etAppKey.setText(appkey);

            if (domainName == null || domainName.equals("")) {
                domainName = DEFAULT_DOMAINNAME;
            }
            if (streamName == null || streamName.equals("")) {
                streamName = sysConfig.getUserID();
            }
            if (appkey == null || appkey.equals("")) {
                appkey = DEFAULT_APPKEY;
            }
            // 生成 sign值,在播放和推流时生成的sign值不一样
            String sign;
            if (isPush) {
                // 生成推流的 sign 值 。把流名称 ，时间，签名密钥 通过MD5 算法加密
                sign = MD5Utls.stringToMD5(streamName + tm + appkey);
            } else {
                // 生成播放 的sign 值，把流名称，时间，签名密钥，和"lecloud" 通过MD5 算法加密
                sign = MD5Utls.stringToMD5(streamName + tm + appkey + "lecloud");
                // 获取到播放域名。现在播放域名的获取规则是 把推流域名中的 push 替换为pull
                domainName = domainName.replaceAll("push", "pull");
            }
            // 拼接出一个rtmp 的地址
            return "rtmp://" + domainName + "/live/" + streamName + "?tm=" + tm + "&sign=" + sign;
        }catch (Exception e){
            e.printStackTrace();
            if(isPush){
                return DEFAULT_PUSHSTREAM;
            }else{
                return DEFAULT_PULLSTREAM;
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestParams requestParams = new RequestParams();
        requestParams.put("userId",sysConfig.getUserID());
        requestParams.put("status","0");
        IRequest.post(mContext, NetConstant.BINDADDRESS, MoLogin.class,requestParams,new RequestJsonListener<MoLogin>() {

            @Override
            public void requestSuccess(MoLogin result) {
                // TODO Auto-generated method stub
            }
            @Override
            public void requestError(VolleyError e) {
                // TODO Auto-generated method stub
            }
        });
    }
}
