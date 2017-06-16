package com.pengdikj.cops.oneKey;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pengdikj.cops.R;
import com.pengdikj.cops.ui.BaseActivity;
import com.pengdikj.cops.utils.BaseUtil;

/**
 * 作者：wuyue on 2017/6/7 0007 10:17
 * 邮箱：yy107829@sina.com
 */

public class TabulationActivity extends BaseActivity{

    private TextView tv_content;
    private TextView tv_date;
    private TextView tv_name;
    private TextView tv_address;
    private TextView tv_result;
    private TextView tv_result_time;
    private RelativeLayout rl_retime;
    private View v_retime;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView2Base(R. layout.activity_tabulation);
        this.setTitle("报警详情");
        this.setToolBarLeftButtonByString(R.string.head_return);
        this.setBarColor(getResources().getColor(R.color.blue_cop));
        initView();
        initData();
    }

    private void initView(){
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_result = (TextView) findViewById(R.id.tv_result);
        tv_result_time = (TextView) findViewById(R.id.tv_result_time);
        rl_retime = (RelativeLayout) findViewById(R.id.rl_retime);
        v_retime = findViewById(R.id.v_retime);
    }

    private void initData(){
        if(null == getIntent().getExtras()){
            Toast.makeText(mContext, "数据刷新异常，请稍后重试！", Toast.LENGTH_SHORT).show();
        }else{
            if(null != getIntent().getExtras().getString("content")){
                tv_content.setText(getIntent().getExtras().getString("content"));
                tv_date.setText(getIntent().getExtras().getString("date"));
                tv_name.setText(getIntent().getExtras().getString("name"));
                tv_address.setText(getIntent().getExtras().getString("address"));
                tv_result.setText(getIntent().getExtras().getString("result"));
                if(BaseUtil.isSpace(getIntent().getExtras().getString("result_time"))){
                    rl_retime.setVisibility(View.GONE);
                    v_retime.setVisibility(View.GONE);
                }else{
                    tv_result_time.setText(getIntent().getExtras().getString("result_time"));
                }
            }else{
                Toast.makeText(mContext, "数据刷新异常，请稍后重试！", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
