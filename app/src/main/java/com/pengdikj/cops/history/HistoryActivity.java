package com.pengdikj.cops.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.pengdikj.cops.R;
import com.pengdikj.cops.entity.AlarmInfo;
import com.pengdikj.cops.model.MoAlarmSituation;
import com.pengdikj.cops.ui.BaseActivity;
import com.pengdikj.cops.utils.BaseUtil;
import com.pengdikj.cops.utils.constant.NetConstant;
import com.pengdikj.cops.utils.volley.IRequest;
import com.pengdikj.cops.utils.volley.RequestJsonListener;
import com.pengdikj.cops.utils.volley.RequestParams;

/**
 * Created by yy on 2017/6/14.
 */

public class HistoryActivity extends BaseActivity{


    private LinearLayout ly_content;
    private String type;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView2Base(R.layout.activity_history);
        this.setToolBarLeftButtonByString(R.string.head_return);
        this.setBarColor(getResources().getColor(R.color.blue_cop));
        initView();

    }


    private void initView(){
        if(null != getIntent().getExtras()){
            if(!BaseUtil.isSpace(getIntent().getExtras().getString("type"))){
                type = getIntent().getExtras().getString("type");
            }
        }

        if(type.equals("1")){
            this.setTitle("历史档案");
        }else{
            this.setTitle("选择案件");
        }

        ly_content = (LinearLayout) findViewById(R.id.ly_content);
        RequestParams requestParams = new RequestParams();
        IRequest.post(mContext, NetConstant.UNTREATED+"/"+sysConfig.getUserID()+"/1", MoAlarmSituation.class,requestParams,"获取中...",new RequestJsonListener<MoAlarmSituation>() {

            @Override
            public void requestSuccess(MoAlarmSituation result) {
                // TODO Auto-generated method stub
                if(!BaseUtil.isSpace(result.getResult().getAlarmInfos())){
                    ly_content.removeAllViews();
                    for(AlarmInfo alarmInfo:result.getResult().getAlarmInfos()){
                        ly_content.addView(new Item_History(mContext,alarmInfo,type));
                    }
                }else{

                }
            }
            @Override
            public void requestError(VolleyError e) {
                // TODO Auto-generated method stub
            }
        });
    }
}
