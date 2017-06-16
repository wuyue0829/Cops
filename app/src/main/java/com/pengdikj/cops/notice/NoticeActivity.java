package com.pengdikj.cops.notice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.pengdikj.cops.R;
import com.pengdikj.cops.ui.BaseActivity;

/**
 * Created by yy on 2017/6/15.
 */

public class NoticeActivity extends BaseActivity{

    private LinearLayout ly_content;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView2Base(R.layout.activity_notice);
        this.setTitle("通知公告");
        this.setToolBarLeftButtonByString(R.string.head_return);
        this.setBarColor(getResources().getColor(R.color.blue_cop));
        initView();
        initData();

    }



    private void initView(){
        ly_content = (LinearLayout) findViewById(R.id.ly_content);
    }

    private void initData(){
        ly_content.removeAllViews();
        ly_content.addView(new Item_Notice(mContext,"2017年陕西省从大学生村官中公开招聘乡镇（街办）扶贫工作人员公告","http://www.shangnan.gov.cn/info/1035/29150.htm"));
        ly_content.addView(new Item_Notice(mContext,"停电公告","http://www.shangnan.gov.cn/info/1035/28672.htm"));
        ly_content.addView(new Item_Notice(mContext,"关于商南县土地利用总体规划（2006-2020年）2014年调整完善方案》的公示","http://www.shangnan.gov.cn/info/1035/28324.htm"));
        ly_content.addView(new Item_Notice(mContext,"国务院办公厅关于对2016年落实有关重大政策措施真抓实干成效明显地方予以表扬激励的通报","http://www.shangnan.gov.cn/info/1035/28022.htm"));
        ly_content.addView(new Item_Notice(mContext,"商南县十八届县委第一轮巡察公告","http://www.shangnan.gov.cn/info/1035/27592.htm"));
        ly_content.addView(new Item_Notice(mContext,"中共商洛市委关于巡察整改情况的通报","http://www.shangnan.gov.cn/info/1035/26918.htm"));
        ly_content.addView(new Item_Notice(mContext,"商南县公安局交警大队关于在县塘坝广场举办“醉美鹿城.璀璨西街”群星演唱会期间实施道路交通管制的通告","http://www.shangnan.gov.cn/info/1035/23576.htm"));
        ly_content.addView(new Item_Notice(mContext,"商南县金丝峡太吉嘉园移民小区防护挡墙工程开标公示","http://www.shangnan.gov.cn/info/1035/20427.htm"));
    }
}
