package com.pengdikj.cops.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：Macyy on 2017/3/22 0022 10:47
 * 邮箱：yy107829@sian.com
 */
public class CheckUtil {
    //正则表达式验证是否为手机号码
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((17[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
    /**
     * 获取手机IMEI号
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        return imei;
    }

    /**
     * 获取手机型号
     */
    public static String getPhoneMode(){
        return  android.os.Build.MODEL;
    }

    public static List removeDuplicateObj(List list) {
        // ................
        Set someSet = new HashSet(list);

        // 将Set中的集合，放到一个临时的链表中(tempList)
        Iterator iterator = someSet.iterator();
        List tempList = new ArrayList();
        int i = 0;
        while (iterator.hasNext()) {

            tempList.add(iterator.next().toString());
            i++;
        }
        return tempList;
    }

    public static  List<String> removeDuplicate(List<String> list) {
        Set<String> set = new HashSet<String>();
        List<String> newList = new ArrayList<String>();
        for (Iterator<String> iter = list.iterator(); iter.hasNext();) {
            String element = (String) iter.next();
            if (set.add(element))
                newList.add(element);
        }
        return newList;
    }
}
