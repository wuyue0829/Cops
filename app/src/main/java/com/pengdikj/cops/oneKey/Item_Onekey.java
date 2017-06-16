package com.pengdikj.cops.oneKey;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pengdikj.cops.R;
import com.pengdikj.cops.entity.TaskInfo;
import com.pengdikj.cops.utils.SysConfig;

/**
 * 作者：wuyue on 2017/6/7 0007 09:07
 * 邮箱：yy107829@sina.com
 */

public class Item_Onekey extends LinearLayout{

    private TaskInfo taskInfo;
    private Context context;
    private TextView tv_title;
    private TextView tv_data;
    private TextView tv_type;
    private RelativeLayout rl_content;
    public Item_Onekey(Context context) {
        super(context);
    }


    public Item_Onekey(Context context, TaskInfo taskInfo){
        super(context);
        this.taskInfo = taskInfo;
        this.context = context;
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.item_onkey, this,true);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_data = (TextView) findViewById(R.id.tv_data);
        tv_type = (TextView) findViewById(R.id.tv_type);
        rl_content = (RelativeLayout) findViewById(R.id.rl_content);

        tv_data.setText(taskInfo.getAlarmDate());
        if(taskInfo.getIsAccept().equals("0")){
            tv_type.setText("未处理");
        }else{
            tv_type.setText("已处理");
            tv_type.setTextColor(getResources().getColor(R.color.liji_material_red_500));
        }

        rl_content.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,TabulationActivity.class);
                intent.putExtra("content",taskInfo.getRemark());
                intent.putExtra("date",taskInfo.getAlarmDate());
                intent.putExtra("name", SysConfig.getConfig(context).getUserName());
                intent.putExtra("address",taskInfo.getProvince()+taskInfo.getCity()+taskInfo.getDistrict()+taskInfo.getAddress());
                if(taskInfo.getIsAccept().equals("0")){
                    intent.putExtra("result","未处理，请等待");
                }else{
                    intent.putExtra("result","感谢举报，已进行处理。");
                }
                intent.putExtra("result_time",taskInfo.getAcceptDate());
                context.startActivity(intent);
            }
        });

    }
}
