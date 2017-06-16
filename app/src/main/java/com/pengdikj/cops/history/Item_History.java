package com.pengdikj.cops.history;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pengdikj.cops.R;
import com.pengdikj.cops.caserecord.CaseRecordActivity;
import com.pengdikj.cops.entity.AlarmInfo;
import com.pengdikj.cops.navigation.AlarmDetailsActivity;

/**
 * Created by yy on 2017/6/14.
 */

public class Item_History extends LinearLayout{


    private AlarmInfo alarmInfo;
    private TextView tv_content;
    private RelativeLayout rl_content;
    private Button bt_finish;
    private String type;
    private Context mContext;
    public Item_History(Context context) {
        super(context);
    }

    public Item_History(Context context, AlarmInfo alarmInfo,String type) {
        super(context);
        this.alarmInfo = alarmInfo;
        this.mContext = context;
        this.type = type;
        init();
    }



    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.item_history, this,true);
        tv_content = (TextView) findViewById(R.id.tv_content);
        bt_finish = (Button) findViewById(R.id.bt_finish);
        rl_content = (RelativeLayout) findViewById(R.id.rl_content);
        tv_content.setText(alarmInfo.getAlarmType());
        if(type.equals("1")){
            bt_finish.setVisibility(VISIBLE);
        }else{
            bt_finish.setVisibility(GONE);
        }
        rl_content.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(type.equals("1")){
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
                    intent.putExtra("type",type);
                    mContext.startActivity(intent);
                }else{
                    Intent intent = new Intent(mContext,CaseRecordActivity.class);
                    intent.putExtra("taskid",alarmInfo.getTaskId());
                    mContext.startActivity(intent);
                }

            }
        });
    }
}
