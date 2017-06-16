package com.pengdikj.cops.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;

import com.pengdikj.cops.R;
import com.pengdikj.cops.navigation.Bean.StrategyStateBean;
import com.pengdikj.cops.navigation.adapter.StrategyChooseAdapter;
import com.pengdikj.cops.navigation.util.Utils;
import com.pengdikj.cops.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 驾车偏好设置
 */
public class StrategyChooseActivity extends BaseActivity{
    private boolean congestion, cost, hightspeed, avoidhightspeed;
    List<StrategyStateBean> mStrategys = new ArrayList<StrategyStateBean>();
    private ListView mStrategyChooseListView;
    private StrategyChooseAdapter mStrategyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityIntent();
        mStrategys.add(new StrategyStateBean(Utils.AVOID_CONGESTION, congestion));
        mStrategys.add(new StrategyStateBean(Utils.AVOID_COST, cost));
        mStrategys.add(new StrategyStateBean(Utils.AVOID_HIGHSPEED, avoidhightspeed));
        mStrategys.add(new StrategyStateBean(Utils.PRIORITY_HIGHSPEED, hightspeed));
        setContentView2Base(R.layout.activity_strategy_choose);
        this.setTitle("路线优选");
        this.setToolBarLeftButtonByString(R.string.head_return);
        this.setBarColor(getResources().getColor(R.color.blue_cop));
        mStrategyAdapter = new StrategyChooseAdapter(this.getApplicationContext(), mStrategys);
        mStrategyChooseListView = (ListView) findViewById(R.id.strategy_choose_list);
        mStrategyChooseListView.setAdapter(mStrategyAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResultIntent();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setResultIntent(){
        Intent intent = new Intent();
        for (StrategyStateBean bean : mStrategys) {
            if (bean.getStrategyCode() == Utils.AVOID_CONGESTION) {
                intent.putExtra(Utils.INTENT_NAME_AVOID_CONGESTION, bean.isOpen());
            }

            if (bean.getStrategyCode() == Utils.AVOID_COST) {
                intent.putExtra(Utils.INTENT_NAME_AVOID_COST, bean.isOpen());
            }

            if (bean.getStrategyCode() == Utils.AVOID_HIGHSPEED) {
                intent.putExtra(Utils.INTENT_NAME_AVOID_HIGHSPEED, bean.isOpen());
            }

            if (bean.getStrategyCode() == Utils.PRIORITY_HIGHSPEED) {
                intent.putExtra(Utils.INTENT_NAME_PRIORITY_HIGHSPEED, bean.isOpen());
            }
        }
        this.setResult(Utils.ACTIVITY_RESULT_CODE, intent);
    }

    private void getActivityIntent() {
        Intent intent = this.getIntent();
        if (intent == null) {
            return;
        }
        congestion = intent.getBooleanExtra(Utils.INTENT_NAME_AVOID_CONGESTION, false);
        cost = intent.getBooleanExtra(Utils.INTENT_NAME_AVOID_COST, false);
        avoidhightspeed = intent.getBooleanExtra(Utils.INTENT_NAME_AVOID_HIGHSPEED, false);
        hightspeed = intent.getBooleanExtra(Utils.INTENT_NAME_PRIORITY_HIGHSPEED, false);
    }
}
