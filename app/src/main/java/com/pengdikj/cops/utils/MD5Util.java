package com.pengdikj.cops.utils;

import java.security.MessageDigest;

public class MD5Util {
    /**
	 * md5加密
	 * @param sourceString
	 * @return
	 */
    public static String encode(String sourceString)
    {
        String resultString = null;
        try
        {
            resultString = new String(sourceString);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byte2hexString(md.digest(resultString.getBytes()));
        }
        catch(Exception ex)
        {
        }
        return resultString;
    }
    
    public static String encodeMima(String userID)
    {
        return encode(userID+"20091111");
        
    }
    /**
	 * md5加密
	 * @return
	 */
    public static final String byte2hexString(byte bytes[])
    {
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        for(int i = 0; i < bytes.length; i++)
        {
            if((bytes[i] & 0xff) < 16)
                buf.append("0");
            buf.append(Long.toString(bytes[i] & 0xff, 16));
        }

        return buf.toString();
    }
    /**
     * 表白密码 用于YYYY-MM-DD 日期类型 数字类型
     * @return
     */
    public static String encodeUnburden(String str)
    {
    	String encodeStr = "";
    	String key = "ilovesunqx!";
    	int length = str.length();
    	for(int i=0;i<length;i++){
    		String c = str.charAt(i)+"";
    		if(c.equals("-")){
    			encodeStr+="!";
    		}else{
    			encodeStr+=key.charAt(DoNumberUtil.intNullDowith(c));
    		}
    	}
    	return encodeStr;
    }
    public static String decodeUnburden(String str)
    {
    	String encodeStr = "";
    	String key = "ilovesunqx!";
    	int length = str.length();
    	for(int i=0;i<length;i++){
    		char c = str.charAt(i);
    		if(key.indexOf(c)==10){
    			encodeStr+="-";
    		}else{
    			encodeStr+=key.indexOf(c);
    		}
    	}
    	return encodeStr;
    }
}
