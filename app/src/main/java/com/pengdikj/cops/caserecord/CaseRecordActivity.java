package com.pengdikj.cops.caserecord;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.pengdikj.cops.R;
import com.pengdikj.cops.recorder.ToastUtils;
import com.pengdikj.cops.ui.BaseActivity;

/**
 * Created by yy on 2017/6/15.
 */

public class CaseRecordActivity extends BaseActivity implements View.OnClickListener{
    private Button bt_fabu;
    private ImageView im_photo;
    private ImageView im_record;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView2Base(R.layout.activity_case_record);
        this.setTitle("案情记录");
        this.setToolBarLeftButtonByString(R.string.head_return);
        this.setBarColor(getResources().getColor(R.color.blue_cop));
        initView();
        initData();
    }


    private void initView(){
        bt_fabu = (Button) findViewById(R.id.bt_fabu);
        im_photo = (ImageView) findViewById(R.id.im_photo);
        im_record = (ImageView) findViewById(R.id.im_record);
    }

    private void initData(){
        bt_fabu.setOnClickListener(this);
        im_photo.setOnClickListener(this);
        im_record.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_fabu:
                ToastUtils.showShort(mContext,"功能开发中...");
                break;
            case R.id.im_photo:
                ToastUtils.showShort(mContext,"功能开发中...");
                break;
            case R.id.im_record:
                ToastUtils.showShort(mContext,"功能开发中...");
                break;
        }
    }
}
