package com.pengdikj.cops.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;

import com.pengdikj.cops.R;


public class ShortcutUtil {

    public static void createShortCut(Activity act, int iconResId, int appnameResId) {
    	
    	Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        //快捷方式的名称   
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, act.getString(R.string.app_name));
        shortcut.putExtra("duplicate", false); //不允许重复创建   
  
        //指定当前的Activity为快捷方式启动的对象: 如 com.everest.video.VideoPlayer   
        //注意: ComponentName的第二个参数必须加上点号(.)，否则快捷方式无法启动相应程序   
        ComponentName comp = new ComponentName(act.getPackageName(), act.getPackageName() + "." + act.getLocalClassName());
        Intent it = new Intent(Intent.ACTION_MAIN).setComponent(comp);
        it.addCategory(Intent.CATEGORY_LAUNCHER);  
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, it);   
        
        //快捷方式的图标   
        Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(act, R.mipmap.ic_launcher);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);   
        act.sendBroadcast(shortcut); 
    }  
}
