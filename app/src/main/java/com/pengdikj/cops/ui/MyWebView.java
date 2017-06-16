package com.pengdikj.cops.ui;

import android.content.Intent;
import android.os.Bundle;

import com.pengdikj.cops.R;
import com.pengdikj.cops.utils.BaseUtil;


/**
 * 带进度条 WebView
 * 作者：Macyy on 2017/3/27 0027 09:25
 * 邮箱：yy107829@sian.com
 */
public class MyWebView extends BaseActivity{

    private ProgressWebView webview_main;
    private String link;
    private String title;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView2Base(R.layout.activity_my_webview);
        this.setBarColor(getResources().getColor(R.color.blue_cop));
        this.setToolBarLeftButtonByString(R.string.head_return);
        initView();
        initData();
    }

    private void initView(){
        if(null != getIntent().getExtras()){
            if(!BaseUtil.isSpace(getIntent().getExtras().getString("title"))){
                this.setTitle("公告");
            }
            link = getIntent().getExtras().getString("link");
        }
        webview_main = (ProgressWebView)findViewById(R.id.webview_main);
    }

    private void initData(){
        if(!BaseUtil.isSpace(link)){
            webview_main.loadUrl(link);
        }else{
        }
    }
}
