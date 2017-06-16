package com.pengdikj.cops.notice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pengdikj.cops.R;
import com.pengdikj.cops.ui.MyWebView;

/**
 * Created by yy on 2017/6/15.
 */

public class Item_Notice extends LinearLayout{

    private Context mContext;
    private String title;
    private String url;
    private RelativeLayout rl_content;
    private TextView tv_title;

    public Item_Notice(Context context) {
        super(context);
    }

    public Item_Notice(Context context,String title,String url) {
        super(context);
        this.mContext = context;
        this.url = url;
        this.title = title;
        init();
    }



    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.item_notice, this,true);
        rl_content = (RelativeLayout) findViewById(R.id.rl_content);
        tv_title = (TextView) findViewById(R.id.tv_title);

        tv_title.setText(title);
        rl_content.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,MyWebView.class);
                intent.putExtra("link",url);
                intent.putExtra("title",title);
                mContext.startActivity(intent);
            }
        });
    }
}
