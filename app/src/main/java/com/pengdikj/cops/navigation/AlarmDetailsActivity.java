package com.pengdikj.cops.navigation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.android.volley.VolleyError;
import com.pengdikj.cops.R;
import com.pengdikj.cops.caserecord.CaseRecordActivity;
import com.pengdikj.cops.model.MoAlarmSituation;
import com.pengdikj.cops.recorder.ui.RecorderCortlActivity;
import com.pengdikj.cops.ui.BaseActivity;
import com.pengdikj.cops.utils.BaseUtil;
import com.pengdikj.cops.utils.DateUtil;
import com.pengdikj.cops.utils.DoNumberUtil;
import com.pengdikj.cops.utils.LogUtil;
import com.pengdikj.cops.utils.constant.NetConstant;
import com.pengdikj.cops.utils.volley.IRequest;
import com.pengdikj.cops.utils.volley.RequestJsonListener;
import com.pengdikj.cops.utils.volley.RequestParams;

/**
 * 作者：wuyue on 2017/6/12 0012 18:35
 * 邮箱：yy107829@sina.com
 */

public class AlarmDetailsActivity extends BaseActivity implements View.OnClickListener{

    private AMapLocation mLocation;
    private AMapLocationClient locationClientSingle = null;
    private MapView mMapView;
    private AMap mAMap;
    private RelativeLayout rl_go;
    private LinearLayout ly_liji;
    private Button bt_daoda;
    private Button bt_chuli;
    private TextView tv_jishi;
    private TextView tv_content;
    private TextView tv_address;
    private TextView tv_type;
    private TextView tv_name;
    private TextView tv_sex;
    private TextView tv_phone;
    private String longitude;
    private String latitude;
    private String titleTime;
    private TextView tv_toptime;
    private TextView tv_type_now;
    private TextView tv_topaddress;
    private String taskid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView2Base(R.layout.activity_alarm_details);
        this.setToolBarLeftButtonByString(R.string.head_return);
        this.setBarColor(getResources().getColor(R.color.blue_cop));
        mMapView = (MapView) findViewById(R.id.navi_view);
        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        startSingleLocation();
        init();
        initView();
        initData();
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (mAMap == null) {
            mAMap = mMapView.getMap();

        }
    }

    private void initView(){
        rl_go = (RelativeLayout) findViewById(R.id.rl_go);
        ly_liji = (LinearLayout) findViewById(R.id.ly_liji);
        bt_daoda = (Button) findViewById(R.id.bt_daoda);
        bt_chuli = (Button) findViewById(R.id.bt_chuli);
        tv_jishi = (TextView) findViewById(R.id.tv_jishi);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_type_now = (TextView) findViewById(R.id.tv_type_now);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_toptime = (TextView) findViewById(R.id.tv_toptime);
        tv_topaddress = (TextView) findViewById(R.id.tv_topaddress);

        if(null != getIntent().getExtras()){
            taskid = getIntent().getExtras().getString("taskid");
            LogUtil.e("taskId是：",taskid);
            this.setTitle(getIntent().getExtras().getString("neirong"));
            tv_content.setText(getIntent().getExtras().getString("neirong"));
            tv_address.setText(getIntent().getExtras().getString("address"));
            tv_type.setText(getIntent().getExtras().getString("Alarmtype"));
            tv_name.setText(getIntent().getExtras().getString("name"));
            if(getIntent().getExtras().getString("sex").equals("1")){
                tv_sex.setText("男");
            }else{
                tv_sex.setText("女");
            }
            tv_sex.setText(getIntent().getExtras().getString("sex"));
            tv_phone.setText(getIntent().getExtras().getString("phone"));
            longitude = getIntent().getExtras().getString("longitude");
            latitude = getIntent().getExtras().getString("latitude");
            titleTime = getIntent().getExtras().getString("jishi");
            tv_jishi.setText(DateUtil.getFormatDate("HH:mm:ss",DateUtil.getDate("yyyy-MM-dd HH:mm:ss",getIntent().getExtras().getString("ji"))));
            tv_toptime.setText(DateUtil.getFormatDate("HH:mm",DateUtil.getDate("yyyy-MM-dd HH:mm:ss",titleTime)));
            tv_topaddress.setText(getIntent().getExtras().getString("address"));
            double longitude = DoNumberUtil.dblNullDowith(getIntent().getExtras().getString("longitude"));
            double latitude = DoNumberUtil.dblNullDowith(getIntent().getExtras().getString("latitude"));
            LatLng latLng = new LatLng(latitude,longitude);

            mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 14));
            mAMap.addMarker(new MarkerOptions().position(latLng).title(getIntent().getExtras().getString("type")).snippet(getIntent().getExtras().getString("address")));
        }

        rl_go.setOnClickListener(this);
        bt_daoda.setOnClickListener(this);
        bt_chuli.setOnClickListener(this);
    }


    private void initData(){
        if(!BaseUtil.isSpace(getIntent().getExtras().getString("type"))){
            rl_go.setVisibility(View.GONE);
            ly_liji.setVisibility(View.GONE);
            bt_daoda.setText("已处理");
            bt_daoda.setClickable(false);
            tv_type_now.setText("已处理");
            tv_type_now.setTextColor(getResources().getColor(R.color.green));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_go:
                Intent intent = new Intent(mContext,CalculateRouteActivity.class);
                intent.putExtra("startlon",mLocation.getLongitude()+"");
                intent.putExtra("stoplon",getIntent().getExtras().getString("longitude"));
                intent.putExtra("startlat",mLocation.getLatitude()+"");
                intent.putExtra("stoplat",getIntent().getExtras().getString("latitude"));
                startActivity(intent);
                break;
            case R.id.bt_daoda:
                RequestParams requestParams = new RequestParams();
                requestParams.put("taskId",taskid);
                IRequest.post(mContext, NetConstant.ARRIVE, MoAlarmSituation.class,requestParams,new RequestJsonListener<MoAlarmSituation>() {

                    @Override
                    public void requestSuccess(MoAlarmSituation result) {
                        // TODO Auto-generated method stub
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                        builder1.setTitle("提示");
                        builder1.setMessage("是否开始上传警务？");
                        builder1.setNegativeButton("取消", new AlertDialog.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                }
                        });

                        builder1.setPositiveButton("确定", new AlertDialog.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent1 = new Intent(mContext, CaseRecordActivity.class);
                                intent1.putExtra("taskid",taskid);
                                startActivity(intent1);
                                finish();
                            }
                        });
                        builder1.show();
                    }
                    @Override
                    public void requestError(VolleyError e) {
                        // TODO Auto-generated method stub
                    }
                });
                break;
            case R.id.bt_chuli:
                RequestParams requestParams1 = new RequestParams();
                requestParams1.put("taskId",taskid);
                IRequest.post(mContext, NetConstant.ACCEPT, MoAlarmSituation.class,requestParams1,new RequestJsonListener<MoAlarmSituation>() {

                    @Override
                    public void requestSuccess(MoAlarmSituation result) {
                        // TODO Auto-generated method stub
                        ly_liji.setVisibility(View.GONE);
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("提示");
                        builder.setMessage("您的处理请求已经发生给指挥中心。是否开启导航进入行程？");
                        builder.setNegativeButton("取消", new AlertDialog.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(mContext,CalculateRouteActivity.class);
                                intent.putExtra("startlon",mLocation.getLongitude()+"");
                                intent.putExtra("stoplon",getIntent().getExtras().getString("longitude"));
                                intent.putExtra("startlat",mLocation.getLatitude()+"");
                                intent.putExtra("stoplat",getIntent().getExtras().getString("latitude"));
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.show();
                    }
                    @Override
                    public void requestError(VolleyError e) {
                        // TODO Auto-generated method stub
                    }
                });
                break;
        }
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

}
