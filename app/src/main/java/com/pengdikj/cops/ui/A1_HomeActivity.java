package com.pengdikj.cops.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;
import com.android.volley.VolleyError;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.pengdikj.cops.BigActivity.BigActivity;
import com.pengdikj.cops.R;
import com.pengdikj.cops.caserecord.CaseRecordActivity;
import com.pengdikj.cops.contacts.ContactsActivity;
import com.pengdikj.cops.history.HistoryActivity;
import com.pengdikj.cops.location.LocationService;
import com.pengdikj.cops.location.Utils;
import com.pengdikj.cops.me.MeActivity;
import com.pengdikj.cops.model.MoLogin;
import com.pengdikj.cops.navigation.AlarmSituationActivity;
import com.pengdikj.cops.notice.NoticeActivity;
import com.pengdikj.cops.recorder.ui.RecorderCortlActivity;
import com.pengdikj.cops.utils.constant.ConfigConstant;
import com.pengdikj.cops.utils.constant.NetConstant;
import com.pengdikj.cops.utils.volley.IRequest;
import com.pengdikj.cops.utils.volley.RequestJsonListener;
import com.pengdikj.cops.utils.volley.RequestParams;

/**
 * 作者：wuyue on 2017/6/5 0005 17:30
 * 邮箱：yy107829@sina.com
 */

public class A1_HomeActivity extends BaseActivity{

    private long firstTime = 0;
    private ImageButton im_start;
    private ImageView im_notice;
    private ImageView im_cortl;
    private TextView tv_start;
    private RelativeLayout rl_contacts;
    private RelativeLayout rl_big;
    private RelativeLayout rl_dangan;
    private RelativeLayout rl_zhuyi;
    private RelativeLayout rl_anqing;
    private RelativeLayout rl_geren;
    private ImageView imageView10;

    public static final String RECEIVER_ACTION = "location_in_background";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView(){
        im_start = (ImageButton) findViewById(R.id.im_start);
        tv_start = (TextView) findViewById(R.id.tv_start);
        im_cortl = (ImageView) findViewById(R.id.im_cortl);
        im_notice = (ImageView) findViewById(R.id.im_notice);
        rl_contacts = (RelativeLayout) findViewById(R.id.rl_contacts);
        rl_big = (RelativeLayout) findViewById(R.id.rl_big);
        rl_zhuyi = (RelativeLayout) findViewById(R.id.rl_zhuyi);
        rl_anqing = (RelativeLayout) findViewById(R.id.rl_anqing);
        rl_dangan = (RelativeLayout) findViewById(R.id.rl_dangan);
        rl_geren = (RelativeLayout) findViewById(R.id.rl_geren);
        imageView10 = (ImageView) findViewById(R.id.imageView10);

        im_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        im_cortl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, RecorderCortlActivity.class));
            }
        });

        im_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, AlarmSituationActivity.class));
            }
        });

        rl_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, ContactsActivity.class));
            }
        });
        rl_big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,BigActivity.class);
                intent.putExtra("type","1");
                startActivity(intent);
            }
        });

        imageView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, NoticeActivity.class));
            }
        });

        rl_anqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,HistoryActivity.class);
                intent.putExtra("type","2");
                startActivity(intent);
            }
        });

        rl_geren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, MeActivity.class));
            }
        });


        rl_dangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,HistoryActivity.class);
                intent.putExtra("type","1");
                startActivity(intent);
            }
        });

        rl_zhuyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,BigActivity.class);
                intent.putExtra("type","2");
                startActivity(intent);
            }
        });
        if("0".equals(sysConfig.getCustomConfig(ConfigConstant.IS_START_LOCATION,"0"))){
            tv_start.setText(R.string.startLocation);
        }else{
            tv_start.setText(R.string.stopLocation);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
       开始定位服务
     */
    private void startLocationService(){
        getApplicationContext().startService(new Intent(this, LocationService.class));
        sysConfig.setCustomConfig(ConfigConstant.IS_START_LOCATION,"1");
    }

    /**
            * 关闭服务
     * 先关闭守护进程，再关闭定位服务
     */
    private void stopLocationService(){
        sendBroadcast(Utils.getCloseBrodecastIntent());
        sysConfig.setCustomConfig(ConfigConstant.IS_START_LOCATION,"0");
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(mContext, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else {
                this.finish();
            }
        }
        return false;
    }

    /**
     * 提示开始上班或者下班
     */
    private void showDialog(){
        if("0".equals(sysConfig.getCustomConfig(ConfigConstant.IS_START_LOCATION,"0"))){
            StyledDialog.buildIosAlert("提示", "您确定要打卡上班吗？这样你的位置信息将上传到总控制台，以供指挥端处理警情任务。", new MyDialogListener() {
                @Override
                public void onFirst() {
                    startLocationService();
                    tv_start.setText(R.string.stopLocation);
                    RequestParams requestParams = new RequestParams();
                    requestParams.put("userId",sysConfig.getUserID());
                    requestParams.put("status","1");
                    IRequest.post(mContext, NetConstant.SIGN, MoLogin.class,requestParams,new RequestJsonListener<MoLogin>() {

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

                @Override
                public void onSecond() {

                }
            }).show();
        }else{
            StyledDialog.buildIosAlert("提示", "您确定要打卡下班吗？这样你的位置信息将停止上传到总控制台。", new MyDialogListener() {
                @Override
                public void onFirst() {
                    stopLocationService();
                    tv_start.setText(R.string.startLocation);
                    RequestParams requestParams = new RequestParams();
                    requestParams.put("userId",sysConfig.getUserID());
                    requestParams.put("status","0");
                    IRequest.post(mContext, NetConstant.SIGN, MoLogin.class,requestParams,new RequestJsonListener<MoLogin>() {

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

                @Override
                public void onSecond() {

                }
            }).show();
        }
    }
}
