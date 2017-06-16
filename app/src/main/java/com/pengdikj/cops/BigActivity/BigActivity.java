package com.pengdikj.cops.BigActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.pengdikj.cops.R;
import com.pengdikj.cops.ui.BaseActivity;

/**
 * Created by yy on 2017/6/14.
 */

public class BigActivity extends BaseActivity{

    private LinearLayout ly_content;
    private String type;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView2Base(R.layout.activity_big);
        this.setToolBarLeftButtonByString(R.string.head_return);
        this.setBarColor(getResources().getColor(R.color.blue_cop));
        initView();
        initData();
    }



    private void initView(){
        ly_content = (LinearLayout) findViewById(R.id.ly_content);
    }


    private void initData(){

        type = getIntent().getExtras().getString("type");
        ly_content.removeAllViews();

        if(type.equals("1")){
            this.setTitle("大型活动");
            ly_content.addView(new Item_big(mContext,"长安大学党委副书记白华来我县调研","1","http://www.shangnan.gov.cn/info/1032/29613.htm"));
            ly_content.addView(new Item_big(mContext,"商南突出四个重点营造良好考试环境","1","http://www.shangnan.gov.cn/info/1032/29391.htm"));
            ly_content.addView(new Item_big(mContext,"商南县2017丝博会暨第二十一届 西洽会各项筹备工作进入冲刺阶段","1","http://www.shangnan.gov.cn/info/1032/29143.htm"));
            ly_content.addView(new Item_big(mContext,"强化担当，主动作为，做实产业扶贫","1","http://www.shangnan.gov.cn/info/1032/28993.htm"));
            ly_content.addView(new Item_big(mContext,"县长崔华锋检查高考备考工作","1","http://www.shangnan.gov.cn/info/1032/28978.htm"));
            ly_content.addView(new Item_big(mContext,"商南县第5期创业+电商扶贫培训班顺利开班","1","http://www.shangnan.gov.cn/info/1032/28994.htm"));
        }else if(type.equals("2")){
            this.setTitle("注意事项");
            ly_content.addView(new Item_big(mContext,"中华人民共和国税收征收管理法","2","http://www.shangnan.gov.cn/info/1037/22409.htm"));
            ly_content.addView(new Item_big(mContext,"陕西省人民政府关于机关事业单位工作人员养老保险制度改革的实施意见","2","http://www.shangnan.gov.cn/info/1037/17280.htm"));
            ly_content.addView(new Item_big(mContext,"关于切实做好工商登记前置审批事项改为后置审批事项监管工作的通知","2","http://www.shangnan.gov.cn/info/1037/17276.htm"));
            ly_content.addView(new Item_big(mContext,"商南县人民政府重大行政决策程序办法","2","http://www.shangnan.gov.cn/info/1037/17307.htm"));
            ly_content.addView(new Item_big(mContext,"中共中央印发《中国共产党纪律处分条例》","2","http://www.shangnan.gov.cn/info/1037/17282.htm"));
            ly_content.addView(new Item_big(mContext,"关于切实做好工商登记前置审批事项改为后置审批事项监管工作的通知","2","http://www.shangnan.gov.cn/info/1037/17276.htm"));
        }
    }
}