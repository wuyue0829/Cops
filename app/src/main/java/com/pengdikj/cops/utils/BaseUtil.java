package com.pengdikj.cops.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.net.ssl.HttpsURLConnection;

public class BaseUtil
{
    public static final float SPEED = 60f;  //一分钟60米
	public static final float STRIDE = 75f;  //一跨距离
  public static boolean isSpace(Integer i)
  {
	if(null==i){
		return true;
	}
	if(i==0){
		return true;
	}
    return false;
  }
  
  public static boolean isSpace(Float i)
  {
	if(null==i){
		return true;
	}
	if(i==0){
		return true;
	}
    return false;
  }
  
  public static boolean isSpace(Long i)
  {
	if(null==i){
		return true;
	}
	if(i==0){
		return true;
	}
    return false;
  }
  public static boolean isSpace(String str)
  {
	if(null==str){
		return true;
	}
	if(str.trim().equals("")){
		return true;
	}
    return false;
  }
  public static boolean isSpace(List list)
  {
	if(null==list){
		return true;
	}
	if(list.size()<1){
		return true;
	}
    return false;
  }
  public static boolean isSpace(String[] strArray)
  {
	if(null==strArray){
		return true;
	}
	if(strArray.length<1){
		return true;
	}
    return false;
  }
  public static boolean isAbcNumber(String checkStr){
		if("".equals(checkStr)){
			return false;
		}
		//定义一个包含26个英文字母和阿拉伯数字的字符串
		String strAbcNumber  ="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		for(int i=0;i<checkStr.length();i++){
			//返回指定索引处的 char 值
			char c=checkStr.charAt(i);
			//如果在该字符串找到该字符值
			if(strAbcNumber.indexOf(c, 0)<0){
				return false;
			}
		}
		return true;
	}
  	public static boolean isNumber(String checkStr){
		if("".equals(checkStr)){
			return false;
		}
		//定义一个包含26个英文字母和阿拉伯数字的字符串
		String strAbcNumber  ="0123456789";
		for(int i=0;i<checkStr.length();i++){
			//返回指定索引处的 char 值
			char c=checkStr.charAt(i);
			//如果在该字符串找到该字符值
			if(strAbcNumber.indexOf(c, 0)<0){
				return false;
			}
		}
		return true;
	}
  	public static int getNumber(String checkStr){
		if(BaseUtil.isSpace(checkStr)){
			return 0;
		}
		String value = "";
		//定义一个包含26个英文字母和阿拉伯数字的字符串
		String strAbcNumber  ="0123456789";
		for(int i=0;i<checkStr.length();i++){
			//返回指定索引处的 char 值
			char c=checkStr.charAt(i);
			//如果在该字符串找到该字符值
			if(strAbcNumber.indexOf(c, 0)>=0){
				value+=c+"";
			}
		}
		return DoNumberUtil.intNullDowith(value);
	}
  	public static boolean isFloatNumber(String checkStr){
		if("".equals(checkStr)){
			return false;
		}
		int point = 0;
		//定义一个包含26个英文字母和阿拉伯数字的字符串
		String strAbcNumber  ="0123456789.";
		for(int i=0;i<checkStr.length();i++){
			//返回指定索引处的 char 值
			char c=checkStr.charAt(i);
			//如果在该字符串找到该字符值
			if(strAbcNumber.indexOf(c, 0)<0){
				return false;
			}
			if(c=='.'){
				point++;
			}
		}
		if(checkStr.startsWith(".")){
			return false;
		}
		if(checkStr.endsWith(".")){
			return false;
		}
		if(point>1){
			return false;
		}
		return true;
	}
  public static boolean isSpecial(String checkStr){
		//定义一个特殊字符字符串
		String strSpecial  =";<>*&!#(){}[]:‘“/^";
		for(int i=0;i<checkStr.length();i++){
			//返回指定索引处的 char 值
			char c=checkStr.charAt(i);
			//如果在该特殊字符串找到该字符值
			if(strSpecial.indexOf(c,0)>0){
				return true;
			}
		}
		return false;
	}
  
  
  /**
   * 统计给定字符串的字数（英文字符或字母算半个字）
   * @param detail
   * @return int 字符串字数
   * @author lydia
   * @date 2012-07-25
   */
  public static int getLength(String detail){
	  int len = detail.length();
	  int myLen =0;
	  for(int i=0;i<len && myLen<=280;i++){
		if(detail.charAt(i)>0 && detail.charAt(i)<128){
			myLen++;
		}else{
			myLen+=2;
		}  
	  }
	  BigDecimal b = new BigDecimal(myLen);
	  return b.divide(new BigDecimal(2),BigDecimal.ROUND_HALF_UP).intValue();
  }
  
  public static int fuHaoCount(String checkStr){
	    int count = 0;
		//定义一个包含26个英文字母和阿拉伯数字的字符串
		String strAbcNumber  ="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz，。！.、：；;!,.";
		for(int i=0;i<checkStr.length();i++){
			//返回指定索引处的 char 值
			char c=checkStr.charAt(i);
			//如果在该字符串找到该字符值
			if(strAbcNumber.indexOf(c, 0)<0){
				count++;
			}
		}
		return count;
	}
  public static String getparam(String param){
	   if(null==param){
		   return "";
	   }
	   if(param.indexOf(',')>-1){
		   String[] paramArr = param.split(",");
		   for(int i=0;i<paramArr.length;i++){
			   if(!paramArr[i].equals("")){
				   return paramArr[i];
			   }
		   }
	   }else{
		   return param;
	   }
	   return ""; 
   }

   public static List  getparamList(String param1,String param2,List list){
	   String[] strArr = new String[2];
	   strArr[0]=param1;
	   strArr[1]=param2;
	   list.add(strArr);
	   return list;
   }
   
  
   
   //共同方法
   public static String cutFloat(String str){
		 if(null==str||""==str){
           return "0";
		 }
         if(str.indexOf('.')<0){
            return str;
         }
         str=str.substring(0,str.indexOf('.'));
         return str;
   }
   //共同方法
   public static float cutFloat1(Float f){
		 if(null==f||0==f){
           return 0f;
		 }
        return DoNumberUtil.floatNullDowith(String.format("%.1f", f));
   }
   //共同方法
   public static float cutFloat2(Float f){
		 if(null==f||0==f){
           return 0f;
		 }
        return (float)(Math.round(f*100))/100;
   }
   public static String getRandomID(){
		  return DoNumberUtil.LonToStr(new Date().getTime());
   }
   /**
	 * 半角转换为全角
	 * 
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();  
		   for (int i = 0; i< c.length; i++) {  
		       if (c[i] == 12288) {  
		         c[i] = (char) 32;  
		         continue;  
		       }if (c[i]> 65280&& c[i]< 65375)  
		          c[i] = (char) (c[i] - 65248);  
		       }  
		   return new String(c);  
	}


   
   public static String getrandom(long size,int count)
   {
	   String returnStr = "";
	   if(size<=0){
		   return returnStr;
	   }
	   for(int i=0;i<count;i++){
		   returnStr+=((int)(Math.random()*1000))%size;
	   }
	   return returnStr;
   }
   public static String getrandomUserID()
   {
	   return  DoNumberUtil.IntToStr((319441+(int)(Math.random()*100)));
   }
   
   public static float[] getSortDesc(float[] array)
   {
	   for(int i=0;i<array.length;i++){
		   for(int j=array.length-1;j>i;j--){
			   if(array[j-1]<array[j]){
				   float tmp=array[j];
				   array[j]=array[j-1];
				   array[j-1]=tmp; 
			   }
		   }
	   }
	   return array;
   }
   
   
   /**
    * 洗牌算法，取随机数
    * @param count
    * @return
    */
   public static String getRandom(int count){
	    String str = "";
	    String[] strArr = new  String[count];
	    for(int i = 0;i<count;i++){
	    	strArr[i]=(i+1)+"";
	    }
	    for(int k=0;k<count;k++){
	    	for(int i = 0;i<count;i++){
		    	int j = ((int)(Math.random()*10000))%count;
		    	String tmp = strArr[i];
		    	strArr[i] = strArr[j];
		    	strArr[j] = tmp;
		    }
	    }
	    for(int i = 0;i<count;i++){
	    	str+=strArr[i]+",";
	    }
		return str;
   }
   /**
    * 洗牌算法，取随机数
    * @param count
    * @return
    */
   public static String getRandomNumber(int count){
	    String str = "";
    	for(int i = 0;i<count;i++){
	    	str+=((int)(Math.random()*10000))%10;
	    }
		return str;
   }
   public static String getHTML(String url) throws IOException {
		String   sTotalString   =   "";
		
		LogUtil.e("sTotalStringurl"+url);
		if(!BaseUtil.isSpace(url)){
			URL l_url = null;
			l_url = new   URL(url);
	         java.net.HttpURLConnection   l_connection   =   (java.net.HttpURLConnection)   l_url.openConnection();
	         BufferedReader   l_reader   =   new   BufferedReader(new   InputStreamReader(l_connection.getInputStream()));
	         String   sCurrentLine   =   " ";
			 while   ((sCurrentLine   =   l_reader.readLine())   !=   null)
			 {
			         sTotalString+=sCurrentLine;
			 }
		}
		LogUtil.e("sTotalString"+sTotalString);
		return sTotalString;
	}
   public static String simpleTest(String httpsURL){  
	   String   sTotalString   =   "";
	   try{
		   URL myurl = new URL(httpsURL);
	       HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection();
	       InputStream ins = con.getInputStream();
	       InputStreamReader isr = new InputStreamReader(ins);
	       BufferedReader in = new BufferedReader(isr);
	       String inputLine;  
	       while ((inputLine = in.readLine()) != null) {  
	    	   LogUtil.e("sTotalString====="+inputLine);
	    	   sTotalString+=inputLine;  
	       }  
	       in.close();
	   }catch(Exception e){
		   e.printStackTrace();
	   }
       
       LogUtil.e("sTotalString"+sTotalString);
	   return sTotalString;
   }  
   public static boolean isMobileNO(String mobile){     
       if(mobile.length()==11){
    	   if(BaseUtil.isNumber(mobile)){
    		   return true;
    		   /*String[] mobileNo = {"130","131","132","145","155","156","185","186","134",
    				   "135","136","137","138","139","147","150","151","152","157","158","159","182","183","187","188",
    				   "133","153","189","180"};
    		   if(DoArrayUtil.IsInArray(mobile.substring(0, 3), mobileNo)){
    			   return true;
    		   }*/
    	   }
       }
       return false;     
   } 
   public static boolean isEmail(String email){     
	   int pin = email.indexOf('@');
	   if(pin>0){
		   String tmp = email.substring(pin+1);
		   pin = tmp.indexOf('@');
		   if(pin<0){
			   pin = tmp.indexOf('.');
			   if(pin>0&&(pin+1)<tmp.length()){
				   return true;
			   }
		   }
	   }
       return false;      
   } 
   public static String getFormatStringFromInt(int input,int len){
		String strInput=Integer.toString(input);
		strInput=PadLeft(strInput,len,'0');
		return strInput;		
	}
   public static String getSex(String sex){
		if(BaseUtil.isSpace(sex)){
			return "1";
		}
		if(isNumber(sex)){
			return sex;
		}
		if(sex.equals("f")){
			return "2";
		}
		if(sex.equals("女")){
			return "2";
		}
		return "1";		
	}
	public static String PadLeft(String strInput,int len,char padChar){
		if (strInput.length()<len) {
			for (int i = 0; i < len-strInput.length(); i++) {
				strInput=padChar+strInput;
			}
			return strInput;
		}
		else
		{
			return strInput;
		}
		
	}
	/**
	   * 保存出错信息
	   */
  public static String getExceptionMessage(Exception e)
  {
	  String errorStr = e.getMessage()+"\tat " +e.getLocalizedMessage();
	  StackTraceElement[] trace = e.getStackTrace();
	  if(null!=trace){
		  for (int i=0; i < trace.length; i++){
			  errorStr+="\tat " + trace[i];
		  }
	  }
	  return errorStr;
  }

   public static void main(String[] args) {

   }

   /**
    * 用户管理师头像
    * @param bitmap
    * @return
    */
   public static Bitmap getRoundedCornerBitmap(Bitmap bitmap){
       Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
       Canvas canvas = new Canvas(outBitmap);
       final int color =0xff424242;
       final Paint paint = new Paint();
       final Rect rect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
       final RectF rectF = new RectF(rect);
       final float roundPX = bitmap.getWidth()/10;
       paint.setAntiAlias(true);
       canvas.drawARGB(0,0,0,0);
       paint.setColor(color);
       canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
       paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
       canvas.drawBitmap(bitmap, rect, rect, paint);
       return outBitmap;
   }
   /**
    * 将图片截取为圆角图片
    * @param bitmap 原图片
    * @param ratio 截取比例，如果是8，则圆角半径是宽高的1/8，如果是2，则是圆形图片
    * @return 圆角矩形图片
    */
   public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float ratio) {
          
           Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                           bitmap.getHeight(), Bitmap.Config.ARGB_8888);
           Canvas canvas = new Canvas(output);

           final Paint paint = new Paint();
           final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
           final RectF rectF = new RectF(rect);

           paint.setAntiAlias(true);
           canvas.drawARGB(0, 0, 0, 0);
           canvas.drawRoundRect(rectF, bitmap.getWidth()/ratio,
                           bitmap.getHeight()/ratio, paint);

           paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
           canvas.drawBitmap(bitmap, rect, rect, paint);
           return output;
   }
   public long getBitmapsize(Bitmap bitmap){

       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
		return bitmap.getByteCount();
		}
		//Pre HC-MR1
		return bitmap.getRowBytes() * bitmap.getHeight();
		
	}
   public static String getTimeRound(){
		  String timeRound;
		  Calendar nowdate = Calendar.getInstance();
		  timeRound=String.valueOf(nowdate.get(Calendar.HOUR))
		           	+String.valueOf(nowdate.get(Calendar.SECOND))
		            +String.valueOf(nowdate.get(Calendar.MINUTE))
		            +String.valueOf(nowdate.get(Calendar.MILLISECOND))
		            +String.valueOf((int)(Math.random()*10000));
	      return timeRound;
	}
// 替换、过滤特殊字符  
   public static String StringFilter(String str) throws PatternSyntaxException {
       str=str.replaceAll("【","[").replaceAll("】","]").replaceAll("！","!");//替换中文标号  
       String regEx="[『』]"; // 清除掉特殊字符  
       Pattern p = Pattern.compile(regEx);
       Matcher m = p.matcher(str);
    return m.replaceAll("").trim();  
   } 
   //计算距离 公里
   public static float _calculateDistance(int stepNum){
		return STRIDE * 0.01f * stepNum/1000f;
   }
   //计算距离 米
   public static int _calculateDistanceMi(int stepNum){
		return (int)(STRIDE * 0.01f * stepNum);
   }
   //计算时间
   public static int _calculateTime(int stepNum){
 		return (int) (_calculateDistanceMi(stepNum) / SPEED);
 	}

}