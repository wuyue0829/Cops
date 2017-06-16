package com.pengdikj.cops.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class DoNumberUtil
{

    public static String booleanToStr(boolean bl)
  {
	String str="";
    if (bl){
    	str="true";
    }else{
    	str="false";
    }
    return str;
  }
  
  public static boolean booleanToStrNum(String str)
  {
	boolean bl;
    if ("1".equals(str)){
    	bl=true;
    }else{
    	bl=false;
    }
    return bl;
  }
  
  
  public static String StrToStr(String str)
  {
	String StrDol;
    if ("false".equals(str)){
    	StrDol="0";
    }else{
    	StrDol="1";
    }
    return StrDol;
  }
  
	public static boolean StrToboolean(String str)
	{

	  if ("true".equals(str)){
	  	return true;
	  }else{
		return false;
	  }
	}
  public static int intNullDowith(String str)
  {
    if (str == null)
      return 0;
    if (str.trim().equals(""))
      return 0;
    return Integer.parseInt(str);
  }
  public static int intNullDowith(Long l)
  {
    if (l == null)
      return 0;

    return l.intValue();
  }
  public static int intNullDowith(Integer i)
  {
    if (i == null)
      return 0;

    return i.intValue();
  }
  public static double dblNullDowith(String str)
  {
    if (str == null)
      return 0.0D;
    if (str.trim().equals(""))
      return 0.0D;

    return Double.parseDouble(str);
  }
  public static long lngNullDowith(String str)
  {
    if (str == null)
      return 0L;
    if (str.trim().equals(""))
      return 0L;

    return Long.parseLong(str);
  }
  public static long lngNullDowith(Long l)
  {
    if (l == null)
      return 0L;

    return l.longValue();
  }
  public static long lngNullDowith(Integer i)
  {
    if (i == null)
      return 0L;

    return i.longValue();
  }
  public static Long lngNullDowith(int i)
  {
    if (i == 0)
      return new Long(0L);

    return new Long(i);
  }
  public static short srtNullDowith(String str)
  {
    if (str == null)
      return 0;
    if (str.trim().equals(""))
      return 0;

    return Short.parseShort(str);
  }
  public static Integer int2Int(int i)
  {
    if (i == 0)
    {
      return null;
    }

    return new Integer(i);
  }
  public static String LonToStr(Long l, double d)
  {
    if (l == null)
    {
      return "";
    }
    Double str1 = l.longValue() * d;
    String str = str1.toString();
    if (str.length() <= 3)
    {
      str = str + "0";
    }
    return str;
  }
  public static String LonToStr(Long l)
  {
    if (l == null)
    {
      return "";
    }
    String str = l.toString();
    return str;
  }
  public static String IntToStr(Integer l)
  {
    if (l == null)
    {
      return "";
    }
    String str = l.toString();
    return str;
  }
  public static String ShortToStr(Short l)
  {
  if (l == null)
  {
    return "";
  }
  String str = l.toString();
  return str;
  }
  public static String FloatToStr(Float l)
  {
	  if (l == null)
	  {
		  return "";
	  }
	  String str = l.toString();
	  return str;
  }
	public static float floatNullDowith(String str)
	{
	  if (str == null)
	    return 0.0f;
	  if (str.trim().equals(""))
	    return 0.0f;
	  return Float.parseFloat(str);
	}
	public static float floatDiff(float a,float b)
	{
         BigDecimal aB = new BigDecimal(Float.toString(a));
         BigDecimal bB = new BigDecimal(Float.toString(b));
	     return aB.subtract(bB).floatValue();
	}
  public static String formatCurrent(Long num)
  {
    if (num == null) return "";
    return formatCurrent(num.longValue());
  }

  public static String formatCurrent(long num) {
    NumberFormat numberformat = NumberFormat.getCurrencyInstance(Locale.JAPAN);
    DecimalFormat decimalformat = (DecimalFormat)numberformat;
    decimalformat.setDecimalSeparatorAlwaysShown(true);
    String s = "###,###";
    decimalformat.applyPattern(s);
    String result = decimalformat.format(num);
    return result;
  }
  public static String formatInteger(Integer i)
  {
    if (i == null)
      return "";

    return i.toString();
  }
  public static String formatCurrent(String strInt)
  {
    if ((strInt == null) || ("".equals(strInt))) {
      return "";
    }

    int strLen = strInt.length();
    String rtnStr = "";
    int rightSite = 0;
    for (int i = strLen; i > 1; --i)
    {
      rightSite = strLen - i;
      if (rightSite + 1 - (rightSite + 1) / 3 * 3 == 0)
      {
        rtnStr = strInt.substring(i - 1, i) + rtnStr;
        rtnStr = "," + rtnStr;
      }
      else {
        rtnStr = strInt.substring(i - 1, i) + rtnStr;
      }
    }

    rtnStr = strInt.substring(0, 1) + rtnStr;

    return rtnStr;
  }
  public static String BigDecimalToStr(BigDecimal bd)
  {
	  String rtnStr ="";
    if (( null == bd)) {
      return rtnStr;
    }
    rtnStr=FloatToStr(bd.floatValue());
    return rtnStr;
  }
  
public static float BigDecimalToFloat(BigDecimal bd)
{
	 if(null==bd){
		 return 0.0f;
	 }else{
		 return bd.floatValue();
	 }
}
	public static float FloatFormat(float f,int i){
		  BigDecimal   b   =   new   BigDecimal(f);   
		  return b.setScale(i, BigDecimal.ROUND_HALF_DOWN).floatValue();   
	}
}