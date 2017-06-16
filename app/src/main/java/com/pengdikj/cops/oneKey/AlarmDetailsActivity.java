package com.pengdikj.cops.oneKey;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.android.volley.VolleyError;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.bumptech.glide.Glide;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.pengdikj.cops.R;
import com.pengdikj.cops.model.MoAlarmId;
import com.pengdikj.cops.model.MoLogin;
import com.pengdikj.cops.model.MoUpload;
import com.pengdikj.cops.ui.BaseActivity;
import com.pengdikj.cops.utils.BaseUtil;
import com.pengdikj.cops.utils.DateUtil;
import com.pengdikj.cops.utils.DoNumberUtil;
import com.pengdikj.cops.utils.FileUtils;
import com.pengdikj.cops.utils.LogUtil;
import com.pengdikj.cops.utils.constant.ConfigConstant;
import com.pengdikj.cops.utils.constant.NetConstant;
import com.pengdikj.cops.utils.volley.IRequest;
import com.pengdikj.cops.utils.volley.RequestJsonListener;
import com.pengdikj.cops.utils.volley.RequestParams;

import java.io.File;
import java.util.List;

/**
 * 作者：wuyue on 2017/6/7 0007 09:35
 * 邮箱：yy107829@sina.com
 */

public class AlarmDetailsActivity extends BaseActivity implements View.OnClickListener{

    private static final int REQUEST_HAND_CAMERA = 105;
    private String phoneNum;
    private RelativeLayout rl_take_photo;
    private RelativeLayout rl_uply;
    private RelativeLayout rl_upsp;
    private EditText ed_content;
    private Button bt_upload;
    private Button bt_jijin;
    private ImageView im_pic;
    private AMapLocation mLocation;
    private AMapLocationClient locationClientSingle = null;
    private String address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView2Base(R.layout.activity_details);
        this.setTitle("一键报警");
        this.setToolBarLeftButtonByString(R.string.head_return);
        this.setBarColor(getResources().getColor(R.color.blue_cop));
        initView();
        initData();
    }

    private void initView(){
        rl_take_photo = (RelativeLayout) findViewById(R.id.rl_take_photo);
        rl_uply = (RelativeLayout) findViewById(R.id.rl_uply);
        rl_upsp = (RelativeLayout) findViewById(R.id.rl_upsp);
        bt_jijin = (Button) findViewById(R.id.bt_jijin);
        ed_content = (EditText) findViewById(R.id.ed_content);
        bt_upload = (Button) findViewById(R.id.bt_upload);
        im_pic = (ImageView) findViewById(R.id.im_pic);
        rl_take_photo.setOnClickListener(this);
        rl_uply.setOnClickListener(this);
        rl_upsp.setOnClickListener(this);
        bt_upload.setOnClickListener(this);
        bt_jijin.setOnClickListener(this);
        startSingleLocation();
    }

    private void initData(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_take_photo:
                takephoto();
                break;
            case R.id.rl_uply:
                uploadLY();
                break;
            case R.id.rl_upsp:
                uploadSP();
                break;
            case R.id.bt_upload:
                oneKeyUpload();
                break;
            case R.id.bt_jijin:
                jinjiBaojing();
                break;
        }
    }

    /**
     * 上传照片
     */
    private void takephoto(){
        Intent intent = new Intent(mContext, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtils.getSaveFileHand(getApplication()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_GENERAL);
        startActivityForResult(intent, REQUEST_HAND_CAMERA);
    }

    /**
     * 上传录音
     */
    private void uploadLY(){
        Toast.makeText(mContext, "功能开发中...", Toast.LENGTH_SHORT).show();
    }

    /**
     * 上传视频
     */
    private void uploadSP(){
        Toast.makeText(mContext, "功能开发中...", Toast.LENGTH_SHORT).show();
    }

    /**
     * 上传报警信息
     */
    private void oneKeyUpload(){
        getPhoneNum();
    }


    /**
     * 回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_HAND_CAMERA && resultCode == Activity.RESULT_OK){
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                String filePath = FileUtils.getSaveFileHand(getApplicationContext()).getAbsolutePath();
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_GENERAL.equals(contentType)) {
                        recIDHnadCard(filePath);
                    } else if (CameraActivity.CONTENT_TYPE_GENERAL.equals(contentType)) {
                        recIDHnadCard(filePath);
                    }
                }
            }
        }
    }

    /**
     * 设置照片
     * @param filePath
     */
    private void recIDHnadCard(final String filePath) {
        final File cover = FileUtils.getSmallBitmap(mContext, filePath);
        //加载本地图片
        Uri uri = Uri.fromFile(cover);
        Glide.with(mContext).load(uri).into(im_pic);

        RequestParams requestParams = new RequestParams();
        requestParams.put("userId",sysConfig.getUserID());
        requestParams.put("file",cover);
        IRequest.post(mContext, NetConstant.UPLOADPIC, MoUpload.class,requestParams,new RequestJsonListener<MoUpload>() {

            @Override
            public void requestSuccess(MoUpload result) {
                // TODO Auto-generated method stub
                sysConfig.setCustomConfig(ConfigConstant.PHONTO_ID,result.getResult().getUpLoad());
            }
            @Override
            public void requestError(VolleyError e) {
                // TODO Auto-generated method stub
            }
        });
    }


    private void getPhoneNum(){
        Acp.getInstance(this).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE
                                , Manifest.permission.READ_PHONE_STATE
                                , Manifest.permission.SEND_SMS
                                , Manifest.permission.CALL_PHONE)
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        TelephonyManager tm = (TelephonyManager)getApplication().getSystemService(Context.TELEPHONY_SERVICE);
                        String te1 = tm.getLine1Number();//获取本机号码
                        phoneNum = te1;
                        if(BaseUtil.isSpace(phoneNum)){
                            phoneNum = sysConfig.getUserPhoneNum();
                        }
                        if(BaseUtil.isSpace(ed_content.getText().toString().trim())){
                            Toast.makeText(mContext, "请填写您报警的内容！", Toast.LENGTH_SHORT).show();
                        }else{
                            if(BaseUtil.isSpace(sysConfig.getCustomConfig(ConfigConstant.PHONTO_ID))){
                                Toast.makeText(mContext, "请上传报警照片！", Toast.LENGTH_SHORT).show();
                            }else{
                                //开始上传
                                upload(phoneNum);
                            }
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        Toast.makeText(mContext, permissions.toString() + "为警情的真实性，请打开权限！", Toast.LENGTH_SHORT).show();
                        if(BaseUtil.isSpace(phoneNum)){
                            phoneNum = sysConfig.getUserPhoneNum();
                        }
                        if(BaseUtil.isSpace(ed_content.getText().toString().trim())){
                            Toast.makeText(mContext, "请填写您报警的内容！", Toast.LENGTH_SHORT).show();
                        }else{
                            if(BaseUtil.isSpace(sysConfig.getCustomConfig(ConfigConstant.PHONTO_ID))){
                                Toast.makeText(mContext, "请上传报警照片！", Toast.LENGTH_SHORT).show();
                            }else{
                                //开始上传
                                upload(phoneNum);
                            }
                        }
                    }
                });

    }

    private void callPhone(String number) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+number));
        startActivity(intent);
    }

    /**
     * 启动单次客户端定位
     */
    void startSingleLocation(){
        if(null == locationClientSingle){
            locationClientSingle = new AMapLocationClient(this.getApplicationContext());
        }

        AMapLocationClientOption locationClientOption = new AMapLocationClientOption();
        //使用单次定位
        locationClientOption.setOnceLocation(true);
        // 地址信息
        locationClientOption.setNeedAddress(true);
        locationClientOption.setLocationCacheEnable(false);
        locationClientSingle.setLocationOption(locationClientOption);
        locationClientSingle.setLocationListener(locationSingleListener);
        locationClientSingle.startLocation();
    }

    /**
     * 单次客户端的定位监听
     */
    AMapLocationListener locationSingleListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if(null != location){
                address = location.getAddress();
                mLocation = location;
                LogUtil.e(location.getAddress());
            }
            stopSingleLocation();
        }
    };

    /**
     * 停止单次客户端
     */
    void stopSingleLocation(){
        if(null != locationClientSingle){
            locationClientSingle.stopLocation();
        }
    }


    private void upload(String phoneNum){
        RequestParams requestParams = new RequestParams();
        requestParams.put("userId",sysConfig.getUserID());
        requestParams.put("locationType", DoNumberUtil.formatInteger(mLocation.getLocationType()));
        requestParams.put("accuracy", mLocation.getAccuracy()+"");
        requestParams.put("provider", mLocation.getProvider());
        requestParams.put("speed", mLocation.getSpeed()+"");
        requestParams.put("bearing", mLocation.getBearing()+"");
        requestParams.put("country", mLocation.getCountry());
        requestParams.put("province", mLocation.getProvince());
        requestParams.put("city", mLocation.getCity());
        requestParams.put("cityCode", mLocation.getCityCode());
        requestParams.put("district", mLocation.getDistrict());
        requestParams.put("address", mLocation.getAddress());
        requestParams.put("alarmDate", DateUtil.getNow());
        requestParams.put("userPhone", phoneNum);
        requestParams.put("remark", ed_content.getText().toString().trim());
        requestParams.put("longitude", mLocation.getLongitude()+"");
        requestParams.put("latitude", mLocation.getLatitude()+"");
        requestParams.put("alarmType", "居民报警");
        requestParams.put("isCritical", "0");

        IRequest.post(mContext, NetConstant.ONEKEY, MoAlarmId.class,requestParams,new RequestJsonListener<MoAlarmId>() {

            @Override
            public void requestSuccess(MoAlarmId result) {
                // TODO Auto-generated method stub
                if(!BaseUtil.isSpace(result.getResult().getAlarmId())){
                    RequestParams requestParams = new RequestParams();
                    requestParams.put("alarmId",result.getResult().getAlarmId());
                    requestParams.put("photoIds",sysConfig.getCustomConfig(ConfigConstant.PHONTO_ID));
                    IRequest.post(mContext, NetConstant.BIND, MoAlarmId.class,requestParams,"上传中...",new RequestJsonListener<MoAlarmId>() {

                        @Override
                        public void requestSuccess(MoAlarmId result) {
                            // TODO Auto-generated method stub
                            Toast.makeText(AlarmDetailsActivity.this, "你的报警我们已经收到，我们将尽快处理！", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void requestError(VolleyError e) {
                            // TODO Auto-generated method stub
                            Toast.makeText(AlarmDetailsActivity.this, "报警失败，请直接拨打110报警电话！！", Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                    sysConfig.setCustomConfig(ConfigConstant.PHONTO_ID,"");
                }
            }
            @Override
            public void requestError(VolleyError e) {
                // TODO Auto-generated method stub
            }
        });
    }


    private void jinjiBaojing(){
        StyledDialog.buildIosAlert("提示", "这种报警方式，将急速相应！请您务必保证报警的真实性，如果虚假报警，将承担法律责任！", new MyDialogListener() {
            @Override
            public void onFirst() {
                RequestParams requestParams = new RequestParams();
                requestParams.put("userId",sysConfig.getUserID());
                requestParams.put("locationType", DoNumberUtil.formatInteger(mLocation.getLocationType()));
                requestParams.put("accuracy", mLocation.getAccuracy()+"");
                requestParams.put("provider", mLocation.getProvider());
                requestParams.put("speed", mLocation.getSpeed()+"");
                requestParams.put("bearing", mLocation.getBearing()+"");
                requestParams.put("country", mLocation.getCountry());
                requestParams.put("province", mLocation.getProvince());
                requestParams.put("city", mLocation.getCity());
                requestParams.put("cityCode", mLocation.getCityCode());
                requestParams.put("district", mLocation.getDistrict());
                requestParams.put("address", mLocation.getAddress());
                requestParams.put("alarmDate", DateUtil.getNow());
                requestParams.put("userPhone", sysConfig.getUserPhoneNum());
                requestParams.put("longitude", mLocation.getLongitude()+"");
                requestParams.put("latitude", mLocation.getLatitude()+"");
                requestParams.put("alarmType", "居民报警");
                requestParams.put("isCritical", "1");

                IRequest.post(mContext, NetConstant.ONEKEY, MoAlarmId.class,requestParams,new RequestJsonListener<MoAlarmId>() {

                    @Override
                    public void requestSuccess(MoAlarmId result) {
                        // TODO Auto-generated method stub
                        if(!BaseUtil.isSpace(result.getResult().getAlarmId())){
                            finish();
                            sysConfig.setCustomConfig(ConfigConstant.PHONTO_ID,"");
                        }
                    }
                    @Override
                    public void requestError(VolleyError e) {
                        // TODO Auto-generated method stub
                    }
                });
            }

            @Override
            public void onSecond() {

            }
        }).show();
    }
}
