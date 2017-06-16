package com.pengdikj.cops.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.pengdikj.cops.R;
import com.pengdikj.cops.navigation.AlarmSituationActivity;
import com.pengdikj.cops.ui.BaseActivity;

/**
 * Created by yy on 2017/6/15.
 */
public class MeActivity extends BaseActivity{

    private RelativeLayout rl_tixing;
    private RelativeLayout rl_clear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView2Base(R.layout.activity_me);
        this.setTitle("个人中心");
        this.setToolBarLeftButtonByString(R.string.head_return);
        this.setBarColor(getResources().getColor(R.color.blue_cop));
        initView();
        initData();
    }

    private void initView(){
        rl_tixing = (RelativeLayout) findViewById(R.id.rl_tixing);
        rl_clear = (RelativeLayout) findViewById(R.id.rl_clear);
    }

    private void initData(){
        rl_tixing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, AlarmSituationActivity.class));
            }
        });


        rl_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StyledDialog.buildIosAlert("提示", "您确定要清除缓存吗?", new MyDialogListener() {
                    @Override
                    public void onFirst() {
                        Toast.makeText(mContext, "清除成功！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSecond() {

                    }
                }).show();
            }
        });
    }




}
