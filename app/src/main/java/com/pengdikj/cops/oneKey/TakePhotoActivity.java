package com.pengdikj.cops.oneKey;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.pengdikj.cops.R;
import com.pengdikj.cops.ui.BaseActivity;

/**
 * 上传照片
 * 作者：wuyue on 2017/6/12 0012 14:35
 * 邮箱：yy107829@sina.com
 */

public class TakePhotoActivity extends BaseActivity{

    private ImageView im_one;
    private ImageView im_two;
    private ImageView im_three;
    private ImageView im_four;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView2Base(R.layout.activity_takephoto);
        this.setTitle("上传照片");
        this.setToolBarLeftButtonByString(R.string.head_return);
        this.setBarColor(getResources().getColor(R.color.blue_cop));
        initView();
        initData();
    }


    private void initView(){
        im_one = (ImageView) findViewById(R.id.im_one);
        im_two = (ImageView) findViewById(R.id.im_two);
        im_three = (ImageView) findViewById(R.id.im_three);
        im_four = (ImageView) findViewById(R.id.im_four);
    }


    private void initData(){

    }

}
