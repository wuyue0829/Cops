package com.pengdikj.cops.contacts;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gjiazhe.wavesidebar.WaveSideBar;
import com.pengdikj.cops.R;
import com.pengdikj.cops.ui.BaseActivity;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：wuyue on 2017/6/13 0013 10:28
 * 邮箱：yy107829@sina.com
 */

public class ContactsActivity extends AutoLayoutActivity{

    private ViewPager viewPager;
    private ViewPagerIndicator indicator;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mList;
    private List<String> mDatas;
    private LinearLayout l_left;
    private int itemCount = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        //设置沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        initView();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.vp);
        indicator = (ViewPagerIndicator) findViewById(R.id.indicator);
        l_left = (LinearLayout) findViewById(R.id.l_left);
        mList = new ArrayList<Fragment>();
        Fragment fragment = new MeFragment();
        mList.add(fragment);
        Fragment fragment1 = new ContactFragment();
        mList.add(fragment1);

        mDatas = new ArrayList<>();
        mDatas.add("部门");
        mDatas.add("群众");
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mList.get(position);
            }

            @Override
            public int getCount() {
                return mList.size();
            }
        };

        l_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewPager.setAdapter(mAdapter);
        //将viewpager与indicator绑定
        indicator.setDatas(mDatas);
        indicator.setViewPager(viewPager);

    }
}
