package com.pengdikj.cops.oneKey;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.pengdikj.cops.R;
import com.pengdikj.cops.entity.TaskInfo;
import com.pengdikj.cops.model.MoPoliceTask;
import com.pengdikj.cops.ui.BaseActivity;
import com.pengdikj.cops.utils.BaseUtil;
import com.pengdikj.cops.utils.constant.NetConstant;
import com.pengdikj.cops.utils.volley.IRequest;
import com.pengdikj.cops.utils.volley.RequestJsonListener;
import com.pengdikj.cops.utils.volley.RequestParams;

/**
 * 作者：wuyue on 2017/6/3 0003 10:43
 * 邮箱：yy107829@sina.com
 */

public class OnkeyPoliceActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_none;
    private RelativeLayout rl_onekey;
    private LinearLayout ly_content;
    private long firstTime = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView2Base(R.layout.activity_onekey);
        this.setTitle("一键报警");
        this.setBarColor(getResources().getColor(R.color.blue_cop));
        initView();
        initData();
    }


    private void initView(){
        tv_none = (TextView) findViewById(R.id.tv_none);
        rl_onekey = (RelativeLayout) findViewById(R.id.rl_onekey);
        ly_content = (LinearLayout) findViewById(R.id.ly_content);
    }

    private void initData(){
        rl_onekey.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RequestParams requestParams = new RequestParams();
        requestParams.put("userId",sysConfig.getUserID());
        requestParams.put("token",sysConfig.getToken());
        IRequest.get(mContext, NetConstant.TASK+"/"+sysConfig.getUserID()+"/"+sysConfig.getToken(), MoPoliceTask.class,new RequestJsonListener<MoPoliceTask>() {
            @Override
            public void requestSuccess(MoPoliceTask result) {
                if(!BaseUtil.isSpace(result.getResult().getTaskInfo())){
                    ly_content.removeAllViews();
                    for(TaskInfo taskInfo:result.getResult().getTaskInfo()){
                        tv_none.setVisibility(View.GONE);
                        ly_content.addView(new Item_Onekey(mContext,taskInfo));
                    }
                }

                // TODO Auto-generated method stub
            }
            @Override
            public void requestError(VolleyError e) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_onekey:
                startActivity(new Intent(mContext,AlarmDetailsActivity.class));
                break;
        }
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

}
