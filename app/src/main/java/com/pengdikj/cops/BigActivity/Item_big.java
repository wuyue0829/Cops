package com.pengdikj.cops.BigActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pengdikj.cops.R;
import com.pengdikj.cops.ui.MyWebView;

/**
 * Created by yy on 2017/6/14.
 */

public class Item_big extends LinearLayout{


    private TextView tv_content;
    private String content;
    private String type;
    private String url;
    private ImageView im_icon;
    private RelativeLayout rl_content;
    private Context mContext;
    public Item_big(Context context) {
        super(context);
    }

    public Item_big(Context context,String content,String type,String url) {
        super(context);
        this.mContext = context;
        this.content = content;
        this.type = type;
        this.url = url;
        init();
    }



    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.item_big, this,true);
        tv_content = (TextView) findViewById(R.id.tv_content);
        rl_content = (RelativeLayout) findViewById(R.id.rl_content);
        im_icon = (ImageView) findViewById(R.id.im_icon);
        tv_content.setText(content);
        if(type.equals("2")){
            im_icon.setBackground(getResources().getDrawable(R.mipmap.icon_wj));
        }else if(type.equals("1")){
            im_icon.setBackground(getResources().getDrawable(R.mipmap.icon_sji));
        }
        rl_content.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,MyWebView.class);
                intent.putExtra("link",url);
                intent.putExtra("title",content);
                mContext.startActivity(intent);
            }
        });
    }
}
