package com.pengdikj.cops.navigation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pengdikj.cops.R;
import com.pengdikj.cops.entity.AlarmInfo;
import com.pengdikj.cops.utils.DateUtil;

/**
 * 作者：wuyue on 2017/6/12 0012 18:08
 * 邮箱：yy107829@sina.com
 */

public class Item_alarm extends LinearLayout{

    private CardView cv_item;
    private TextView tv_title;
    private TextView tv_type;
    private TextView tv_address;
    private Context mContext;
    private TextView tv_time;
    private AlarmInfo alarmInfo;
    public Item_alarm(Context context) {
        super(context);
    }

    public Item_alarm(Context context, AlarmInfo alarmInfo){
        super(context);
        this.mContext = context;
        this.alarmInfo = alarmInfo;
        init();
    }


    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.item_alarm, this,true);
        initView();
    }

    private void initView(){
        cv_item = (CardView) findViewById(R.id.cv_item);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_time = (TextView) findViewById(R.id.tv_time);
        cv_item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,AlarmDetailsActivity.class);
                intent.putExtra("taskid",alarmInfo.getTaskId());
                intent.putExtra("jishi",alarmInfo.getAcceptDate());
                intent.putExtra("ji",alarmInfo.getAlarmDate());
                intent.putExtra("neirong",alarmInfo.getRemark());
                intent.putExtra("address",alarmInfo.getAddress());
                intent.putExtra("Alarmtype",alarmInfo.getAlarmType());
                intent.putExtra("name",alarmInfo.getUserName());
                intent.putExtra("sex",alarmInfo.getGender());
                intent.putExtra("phone",alarmInfo.getUserPhone());
                intent.putExtra("longitude",alarmInfo.getLongitude());
                intent.putExtra("latitude",alarmInfo.getLatitude());
                mContext.startActivity(intent);
            }
        });
        tv_title.setText(alarmInfo.getAlarmType());
        tv_address.setText(alarmInfo.getAddress());
        if(alarmInfo.getIsCritical().equals("1")){
            tv_type.setText("紧急");
        }else{
            tv_type.setText("非紧急");
        }
        tv_time.setText(DateUtil.getDateFormat(DateUtil.getDate("yyyy-MM-dd HH:mm:ss",alarmInfo.getAcceptDate())));
    }
}
