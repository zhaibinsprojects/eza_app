package com.sanbang.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;


public class DateUtils {

	/**
	 * 获取当前时间到 今晚23:59:59秒的时间差 单位是秒
	 * 
	 * @return
	 */
	public static long getTimeValue() {
		Calendar c = Calendar.getInstance();
		// 现在的时间(单位：毫秒)
		long nowMills = c.getTimeInMillis();
		System.out.println(nowMills);
		// 设置需要的时间
		c.set(Calendar.YEAR, c.get(Calendar.YEAR));
		// 第二个参数是设置月的，月是基于0的
		// arg list:year,month,day,hour,minute,second
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 23, 59, 59);
		// c.set(year, month, day, hour, minute, seconds);
		long setMills = c.getTimeInMillis();
		System.out.println(setMills - nowMills);
		return (setMills - nowMills) / 1000;
	}

	/**
	 * 将指定日期对象转换成格式化字符串
	 * 
	 * @return String
	 */
	public static String getFormattedString(Date date, String datePattern) {
		SimpleDateFormat sd = new SimpleDateFormat(datePattern);

		return sd.format(date);
	}

	/**
	 * 将指定字符串转换成日期
	 * 
	 * @return Date
	 */
	public static Date getFormatDate(String date, String datePattern) {
		SimpleDateFormat sd = new SimpleDateFormat(datePattern);
		return sd.parse(date, new ParsePosition(0));
	}
	
	/**
	 * 获取当前指定格式  日期
	 * @return
	 * @throws ParseException
	 */
	public static Date getCurrentDate(Date date,String formtStr) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat(formtStr);
		Date time=sdf.parse(sdf.format(date));
		return time;
	}
	/**
	 * 将字符串日期转换成指定格式字符串日期
	 * @param time
	 * @return
	 */
	public static String convertSToSTime(String time,String format,String format2){
		try {  
			if (StringUtils.isNotBlank(time)) {  
				SimpleDateFormat sf = new SimpleDateFormat(format);  
				SimpleDateFormat sf2 = new SimpleDateFormat(format2);  
				return sf2.format(sf.parse(time));  
			}  
		} catch (ParseException e) {  
			e.printStackTrace();  
		}  
		return "0:0:0";
	}
	
	/**
	 * 获取前后日期 i为正数 向后推迟i天，负数时向前提前i天
	 */
	public static Date getNeedate(int i,String formtStr) 
	 {
		 Date dat = null;
		 Calendar cd = Calendar.getInstance();
		 cd.add(Calendar.DATE, i);
		 dat = cd.getTime();
		 SimpleDateFormat dformat = new SimpleDateFormat(formtStr);
		 Date date = null;
			try {
				date = dformat.parse(dformat.format(dat));
			} catch (ParseException e) {
				e.printStackTrace();
			}
	 return date;
	 }
	/**
	 * 返回当前时间的前或者后几天的时间
	 * @param i 为正表示向前推i天，为负表示向后推i天 例如：2015-01-12 ，i=1,结果是2015-01-13
	 * @param formtStr 时间转换格式
	 * @param date 需要转换的时间字符串
	 * @return
	 * @throws ParseException
	 */
	public static Date getTNeedate(int i,String formtStr,String date) throws ParseException {
		
	SimpleDateFormat sdf = new SimpleDateFormat(formtStr);
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(sdf.parse(date));
	calendar.add(Calendar.DAY_OF_MONTH,i); 
		return calendar.getTime();
	}
	/**
	 * 获取前后日期 i为正数 向后推迟i天，负数时向前提前i天
	 * 返回String类型时间
	 */
	public static String getNeedate1(int i,String formtStr) 
	 {
		 Date dat = null;
		 Calendar cd = Calendar.getInstance();
		 cd.add(Calendar.DATE, i);
		 dat = cd.getTime();
		 SimpleDateFormat dformat = new SimpleDateFormat(formtStr);
		 return dformat.format(dat);
	 }
	/**
	 * 获取前后日期 i为正数 向后推迟i月，负数时向前提前i月
	 * 返回String类型时间
	 */
	public static String getNeedate2(int i,String formtStr) 
	 {
		 Date dat = null;
		 Calendar cd = Calendar.getInstance();
		 cd.add(Calendar.MONTH, i);
		 dat = cd.getTime();
		 SimpleDateFormat dformat = new SimpleDateFormat(formtStr);
		 return dformat.format(dat);
	 }
	
	
	
	
	public static Boolean compareDate(String paytime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date1 = sdf.parse(paytime);
			Date date2 = new Date();
			long diff = date2.getTime()- date1.getTime(); 
			long days =diff/(24 * 60 * 60 * 1000);
			System.out.println(days);
			if(days >= 1){
				return true;
			}else {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	//是否在延迟时间内
	public static Boolean delay(short delaynum,String endtime){
		short min= 0;
		if(delaynum==1){
			min =3;
		}else if(delaynum==2){
			min =5;
		}
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			
			Date date1 = sdf.parse(endtime);
			Date date2 = new Date();
			long diff = date2.getTime()- (date1.getTime()-min*60*1000); 
			if(diff > 0){
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	} 
	
	public static Long convertLongTime(String time){
		 try {  
			   if (StringUtils.isNotBlank(time)) {  
			    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			    return sf.parse(time).getTime();  
			   }  
			  } catch (ParseException e) {  
			   e.printStackTrace();  
			  }  
			  return 0l;
	}
	
	
	
	
	
	public static void main(String[] args) throws ParseException {
		String endtime = "2017-06-08 15:05:00";
		System.out.println(delay((short)2, endtime));
	}
	
	
}
