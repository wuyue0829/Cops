package com.pengdikj.cops.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pengdikj.cops.R;
import com.pengdikj.cops.oneKey.OnkeyPoliceActivity;
import com.pengdikj.cops.utils.ActivityCollecor;
import com.pengdikj.cops.utils.LogUtil;
import com.pengdikj.cops.utils.SysConfig;
import com.pengdikj.cops.utils.constant.BaseConstant;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;

/**
 * 作者：wuyue on 2017/6/5 0005 15:27
 * 邮箱：yy107829@sina.com
 */

public class BaseActivity extends AutoLayoutActivity{
    public Context mContext;
    protected LayoutInflater mInflater ;
    protected FrameLayout mainLayout ;  //整个窗体的布局
    protected LinearLayout head_50;
    protected LinearLayout l_left;			//左边布局
    protected Button b_left ;     	   		//左边按钮
    protected TextView tv_left ;     	   		//左边文字
    protected ImageButton b_left2;
    protected TextView b_left3;//左边图片按钮
    protected LinearLayout l_right;			//右边布局
    protected Button b_right ;     	   		//右边按钮
    protected Button b_right3 ;     	   		//右边按钮
    protected TextView tv_right ;     	   		//右边文字
    protected ImageButton b_right2;			//右边图片按钮
    protected TextView tv_head_logo ;
    protected TextView titleText ;	   //标题
    protected SysConfig sysConfig;
    protected LinearLayout head_head;
    protected RelativeLayout bar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ActivityCollecor.addActivity(this);
        LogUtil.e("the Activity is:", getClass().getSimpleName());
        sysConfig = SysConfig.getConfig(this);
        mContext = this;
        mInflater = LayoutInflater.from(mContext);
        mainLayout = (FrameLayout) findViewById(R.id.main_layout);
        l_left = (LinearLayout) findViewById(R.id.l_left);
        b_left = (Button) findViewById(R.id.b_left);
        tv_left = (TextView) findViewById(R.id.tv_left);
        b_left2 = (ImageButton) findViewById(R.id.b_left2);
        titleText = (TextView) findViewById(R.id.tv_head_title);
        l_right = (LinearLayout) findViewById(R.id.l_right);
        b_right = (Button) findViewById(R.id.b_right);
        b_right3 = (Button) findViewById(R.id.b_right3);
        tv_right = (TextView) findViewById(R.id.tv_right);
        b_right2 = (ImageButton) findViewById(R.id.b_right2);
        b_left3  = (TextView) findViewById(R.id.b_left3);
        tv_head_logo  = (TextView) findViewById(R.id.tv_head_logo);
        head_head =  (LinearLayout) findViewById(R.id.head_head);
        head_50 = (LinearLayout) findViewById(R.id.head_50);

        bar  =  (RelativeLayout) findViewById(R.id.head_bar);

        tv_head_logo.setVisibility(View.GONE);
        l_left.setVisibility(View.GONE);
        b_left.setVisibility(View.GONE);
        tv_left.setVisibility(View.GONE);
        b_left2.setVisibility(View.GONE);
        l_right.setVisibility(View.GONE);
        b_right.setVisibility(View.GONE);
        b_right3.setVisibility(View.GONE);
        tv_right.setVisibility(View.GONE);
        b_right2.setVisibility(View.GONE);

        l_left.setOnClickListener(btnClickLeft);
        b_left.setOnClickListener(btnClickLeft);
        tv_left.setOnClickListener(btnClickLeft);
        b_left2.setOnClickListener(btnClickLeft);

        l_right.setOnClickListener(btnClickRight);
        b_right.setOnClickListener(btnClickRight);
        b_right3.setOnClickListener(btnClickRight);
        tv_right.setOnClickListener(btnClickRight);
        b_right2.setOnClickListener(btnClickRight);

        //设置沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    //按钮点击
    private View.OnClickListener btnClickLeft = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            leftButtonClick();
        }
    };
    //按钮点击
    private View.OnClickListener btnClickRight = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            rightButtonClick();
        }
    };

    /**
     * 添加自己的布局文件
     * @return 该布局VIEW
     */
    public View setContentView2Base(int layoutID){
        View contentView = mInflater.inflate(layoutID, null);
        mainLayout.addView(contentView);
        return contentView;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentManager fm = getSupportFragmentManager();
        int index = requestCode >> 16;
        if (index != 0) {
            index--;
            if (fm.getFragments() == null || index < 0
                    || index >= fm.getFragments().size()) {
                Log.w(BaseConstant.TAG, "Activity result fragment index out of range: 0x"
                        + Integer.toHexString(requestCode));
                return;
            }
            Fragment frag = fm.getFragments().get(index);
            if (frag == null) {
                Log.w(BaseConstant.TAG, "Activity result no fragment exists for index: 0x"
                        + Integer.toHexString(requestCode));
            } else {
                handleResult(frag, requestCode, resultCode, data);
            }
            return;
        }
    }

    /**
     * 递归调用，对所有子Fragement生效
     *
     * @param frag
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handleResult(Fragment frag, int requestCode, int resultCode,
                              Intent data) {
        frag.onActivityResult(requestCode & 0xffff, resultCode, data);
        List<Fragment> frags = frag.getChildFragmentManager().getFragments();
        if (frags != null) {
            for (Fragment f : frags) {
                if (f != null)
                    handleResult(f, requestCode, resultCode, data);
            }
        }
    }


    /**
     * 添加自己的布局文件
     * @param contentView  自定义VIEW
     */
    public void setContentView2Base(View contentView){
        mainLayout.addView(contentView);
    }

    /**
     * 设置背景遮罩
     * @param color 颜色
     */
    public void setBackGroundColor(int color){
        mainLayout.setBackgroundColor(color);
    }

    /**
     * 设置背景遮罩
     */
    public void setBackGroundDrawable(int drawableID){
        mainLayout.setBackgroundResource(drawableID);
    }
    /**
     * 设置背景遮罩
     */
    public void setBackGroundDrawable(Drawable d){
        mainLayout.setBackgroundDrawable(d);
    }
    /*** 设置标题
     * @param title
     */
    public void setTitle(String title){
        titleText.setText(title);
    }

    /***  设置标题
     * @param stringID 资源id
     * */
    @Override
    public void setTitle(int stringID){
        titleText.setText(stringID);
    }

    /**
     * 设置背景色
     * @param drawableID
     */
    public void setBarColor(int drawableID){
        head_50.setBackgroundColor(drawableID);
    }
    /**
     * 设置左右侧按钮
     * @param strID
     */
    //左图标 右文字
    public void setToolBarLeftButtonByString(int strID) {
        setToolBarButtonString(strID, 0);
    }
    public void setToolBarRightButtonByString(int strID) {
        setToolBarButtonString(strID, 1);
    }
    //只有文字
    public void setToolBarLeftButtonByString0(int strID) {
        setToolBarButtonString(strID, 5);
    }
    public void setToolBarRightButtonByString0(int strID) {
        setToolBarButtonString(strID, 6);
    }
    //左文字 右图标
    public void setToolBarLeftButtonByString3(int strID) {
        setToolBarButtonString(strID, 3);
    }
    public void setToolBarRightButtonByString3(int strID) {
        setToolBarButtonString(strID, 4);
    }
    public void setToolBarLeftButton(int strID) {
        setToolBarButton(strID, 0);
    }
    public void setToolBarRightButton(int strID) {
        setToolBarButton(strID, 1);
    }
    //左图标
    public void setToolBarLeftButton2() {
        b_left2.setVisibility(View.VISIBLE);
    }

    public void setToolBarRightButton2(int drawableID) {
        b_right2.setVisibility(View.VISIBLE);
        b_right2.setImageResource(drawableID);
    }
    public void setToolBarLeftButton3() {
        b_left3.setVisibility(View.VISIBLE);
    }
    public void setToolBarLogo() {
        tv_head_logo.setVisibility(View.VISIBLE);
    }
    public void setToolBarLeftButton2(int drawableID) {
        b_left2.setVisibility(View.VISIBLE);
        b_left2.setImageResource(drawableID);
    }
    public void setToolBarRightButton1(int drawableID) {
        b_right.setBackgroundResource(drawableID);
    }
    public void setToolBarLeftButton1(int drawableID) {
        b_left.setBackgroundResource(drawableID);
    }
    /**
     * 设置第一个右边按钮的背景
     */
    public void setToolBarRightButtonBgColor(int drawableID) {
        b_right2.setVisibility(View.GONE);
        l_right.setVisibility(View.VISIBLE);
        l_right.setBackgroundResource(drawableID);
    }


    private void setToolBarButtonString(int strID, int index) {
        switch (index) {
            case 0:
                // 左侧第一个按钮
                l_left.setVisibility(View.VISIBLE);
                b_left.setVisibility(View.VISIBLE);
                tv_left.setVisibility(View.INVISIBLE);
                tv_left.setText(strID);
                break;
            case 1:
                // 右侧第一个按钮
                l_right.setVisibility(View.VISIBLE);
                b_right.setVisibility(View.VISIBLE);
                tv_right.setVisibility(View.VISIBLE);
                tv_right.setText(strID);
                break;
            case 3:
                // 左侧第一个按钮
                l_left.setVisibility(View.VISIBLE);
                b_left.setVisibility(View.VISIBLE);
                tv_left.setVisibility(View.VISIBLE);
                tv_left.setText(strID);
                break;
            case 4:
                // 右侧第一个按钮
                l_right.setVisibility(View.VISIBLE);
                b_right3.setVisibility(View.VISIBLE);
                tv_right.setVisibility(View.VISIBLE);
                tv_right.setText(strID);
                break;
            case 5:
                // 左侧第一个按钮
                l_left.setVisibility(View.VISIBLE);
                b_left.setVisibility(View.GONE);
                tv_left.setVisibility(View.VISIBLE);
                tv_left.setText(strID);
                break;
            case 6:
                // 右侧第一个按钮
                l_right.setVisibility(View.VISIBLE);
                b_right.setVisibility(View.GONE);
                tv_right.setVisibility(View.VISIBLE);
                tv_right.setText(strID);
                break;
            default:
                break;
        }
    }
    private void setToolBarButton(int strID, int index) {
        switch (index) {
            case 0:
                // 左侧第一个按钮
                l_left.setVisibility(View.VISIBLE);
                b_left.setVisibility(View.VISIBLE);
                b_left.setBackgroundResource(strID);
                break;
            case 1:
                // 右侧第一个按钮
                l_right.setVisibility(View.VISIBLE);
                b_right.setVisibility(View.VISIBLE);
                b_right.setBackgroundResource(strID);
                break;
            default:
                break;
        }
    }
    public void setToolBarLeftButtonGone() {
        l_left.setVisibility(View.GONE);

    }
    public void setToolBarRightButtonGone() {
        l_right.setVisibility(View.GONE);
    }
    /**
     * 去掉右边箭头
     */
    public void setToolBarRightArrowGone(){
        b_right.setVisibility(View.GONE);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);// 必须要调用这句
    }

    /**确定按钮点击**/
    public void rightButtonClick(){
        //do nothing......
    }

    //隐藏标题栏
    protected void hideHeadBar(){
        bar.setVisibility(View.GONE);
    }
    //显示标题栏
    protected void showHeadBar(){
        bar.setVisibility(View.VISIBLE);
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }
    /**
     * 页面跳转
     */
    protected void close(){
        this.finish();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        ActivityCollecor.removeActivity(this);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void leftButtonClick() {
        if(sysConfig.getUserType().equals("1")){
            if(ActivityCollecor.isExsitMianActivity(mContext, A1_HomeActivity.class)){
                this.finish();
            }else{
                this.finish();
                startActivity(new Intent(mContext,A1_HomeActivity.class));
            }
        }else{
            if(ActivityCollecor.isExsitMianActivity(mContext, OnkeyPoliceActivity.class)){
                this.finish();
            }else{
                this.finish();
                startActivity(new Intent(mContext,OnkeyPoliceActivity.class));
            }
        }
    }
}
