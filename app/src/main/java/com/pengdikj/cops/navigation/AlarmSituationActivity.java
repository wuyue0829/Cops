package com.pengdikj.cops.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.pengdikj.cops.R;
import com.pengdikj.cops.entity.AlarmInfo;
import com.pengdikj.cops.model.MoAlarmSituation;
import com.pengdikj.cops.ui.BaseActivity;
import com.pengdikj.cops.utils.BaseUtil;
import com.pengdikj.cops.utils.constant.NetConstant;
import com.pengdikj.cops.utils.volley.IRequest;
import com.pengdikj.cops.utils.volley.RequestJsonListener;
import com.pengdikj.cops.utils.volley.RequestParams;

/**
 * 作者：wuyue on 2017/6/12 0012 18:04
 * 邮箱：yy107829@sina.com
 */

public class AlarmSituationActivity extends BaseActivity{

    private LinearLayout ly_alarm;
    private TextView tv_show;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView2Base(R.layout.activity_alarm_situation);
        this.setTitle("实时警情");
        this.setToolBarLeftButtonByString(R.string.head_return);
        this.setBarColor(getResources().getColor(R.color.blue_cop));
        initView();
    }

    private void initView(){
        ly_alarm = (LinearLayout) findViewById(R.id.ly_alarm);
        tv_show = (TextView) findViewById(R.id.tv_show);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RequestParams requestParams = new RequestParams();
        IRequest.post(mContext, NetConstant.UNTREATED+"/"+sysConfig.getUserID()+"/0", MoAlarmSituation.class,requestParams,"获取中...",new RequestJsonListener<MoAlarmSituation>() {

            @Override
            public void requestSuccess(MoAlarmSituation result) {
                // TODO Auto-generated method stub
                if(!BaseUtil.isSpace(result.getResult().getAlarmInfos())){
                    ly_alarm.removeAllViews();
                    tv_show.setVisibility(View.GONE);
                    for(AlarmInfo alarmInfo:result.getResult().getAlarmInfos()){
                        ly_alarm.addView(new Item_alarm(mContext,alarmInfo));
                    }
                }else{
                    ly_alarm.removeAllViews();
                    tv_show.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void requestError(VolleyError e) {
                // TODO Auto-generated method stub
                ly_alarm.removeAllViews();
                tv_show.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);// 必须要调用这句
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            leftButtonClick();
        }
        return false;
    }

}
