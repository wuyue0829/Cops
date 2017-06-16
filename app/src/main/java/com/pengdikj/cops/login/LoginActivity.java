package com.pengdikj.cops.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.pengdikj.cops.R;
import com.pengdikj.cops.model.MoLogin;
import com.pengdikj.cops.oneKey.OnkeyPoliceActivity;
import com.pengdikj.cops.ui.A1_HomeActivity;
import com.pengdikj.cops.ui.BaseActivity;
import com.pengdikj.cops.utils.BaseUtil;
import com.pengdikj.cops.utils.constant.NetConstant;
import com.pengdikj.cops.utils.volley.IRequest;
import com.pengdikj.cops.utils.volley.RequestJsonListener;
import com.pengdikj.cops.utils.volley.RequestParams;
import com.tencent.android.tpush.XGPushConfig;

/**
 * 作者：wuyue on 2017/6/5 0005 13:52
 * 邮箱：yy107829@sina.com
 */

public class LoginActivity extends BaseActivity{

    private EditText ed_name;
    private Context mContext;
    private EditText ed_password;
    private String name;
    private String password;
    private Button bt_login;
    private TextView tv_register;
    private TextView tv_forget_password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("登录");
        initView();
        initData();
    }

    private void initView(){
        mContext = this;
        ed_name = (EditText) findViewById(R.id.ed_name);
        ed_password = (EditText) findViewById(R.id.ed_password);
        bt_login = (Button) findViewById(R.id.bt_login);
        tv_register = (TextView) findViewById(R.id.tv_register);
        tv_forget_password = (TextView) findViewById(R.id.tv_forget_password);
    }


    private void initData(){
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "软件属于内测阶段，再不开放此功能！", Toast.LENGTH_SHORT).show();
            }
        });

        tv_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "软件属于内测阶段，再不开放此功能！", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void login(){
        name = ed_name.getText().toString().trim();
        password = ed_password.getText().toString().trim();
        if(BaseUtil.isSpace(name)||BaseUtil.isSpace(password)){
            Toast.makeText(LoginActivity.this, "电话号码和密码不能为空！", Toast.LENGTH_SHORT).show();
        }else{
            RequestParams requestParams = new RequestParams();
            requestParams.put("phone",name);
            requestParams.put("password",password);
            IRequest.post(mContext, NetConstant.USERLOGIN, MoLogin.class,requestParams,"登录中...",new RequestJsonListener<MoLogin>() {

                @Override
                public void requestSuccess(MoLogin result) {
                    // TODO Auto-generated method stub
                    sysConfig.setUserID(result.getResult().getUserInfo().getUserId());
                    sysConfig.setUserSex(result.getResult().getUserInfo().getType());
                    sysConfig.setUserPhoneNum(result.getResult().getUserInfo().getPhone());
                    sysConfig.setUserName(result.getResult().getUserInfo().getUserName());
                    sysConfig.setToken(result.getResult().getUserInfo().getToken());
                    //区分是警察还是用户
                    if(result.getResult().getUserInfo().getType().equals("1")){
                        sysConfig.setUserType("1");
                        startActivity(new Intent(mContext, A1_HomeActivity.class));
                    }else{
                        sysConfig.setUserType("0");
                        startActivity(new Intent(mContext, OnkeyPoliceActivity.class));
                    }

                    RequestParams requestParams = new RequestParams();
                    requestParams.put("equipmentToken",XGPushConfig.getToken(mContext));
                    requestParams.put("userId",sysConfig.getUserID());
                    IRequest.post(mContext, NetConstant.EQUIPMENT, MoLogin.class,requestParams,new RequestJsonListener<MoLogin>() {

                        @Override
                        public void requestSuccess(MoLogin result) {
                            // TODO Auto-generated method stub
                            finish();
                        }
                        @Override
                        public void requestError(VolleyError e) {
                            // TODO Auto-generated method stub
                        }
                    });
                }
                @Override
                public void requestError(VolleyError e) {
                    // TODO Auto-generated method stub
                }
            });
        }
    }
}
