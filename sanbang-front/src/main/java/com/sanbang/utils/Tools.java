package com.sanbang.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;




public class Tools {
	// 日志
	private static Logger log = Logger.getLogger(Tools.class.getName());
	private static int num = 100;
	private static int anum = 110000;
	
	
	public static final String FOLDER = "uploads";
	/**
	 * 检测字符串是否不为空(null,"","null")
	 * 
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s) {
		return s != null && !"".equals(s.trim()) && !"null".equals(s.trim());
	}

	/**
	 * 检测字符串是否为空(null,"","null")
	 * 
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s) {
		return s == null || "".equals(s.trim()) || "null".equals(s.trim());
	}

	/**
	 * 字符串转换为字符串数组
	 * 
	 * @param str
	 *            字符串
	 * @param splitRegex
	 *            分隔符
	 * @return
	 */
	public static String[] str2StrArray(String str, String splitRegex) {
		if (isEmpty(str)) {
			return null;
		}
		return str.split(splitRegex);
	}

	/**
	 * 用默认的分隔符(,)将字符串转换为字符串数组
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String[] str2StrArray(String str) {
		return str2StrArray(str, ",\\s*");
	}

	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
	 * 
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String date2Str(Date date) {
		return date2Str(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date str2Date(String date) {
		if (notEmpty(date)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return new Date();
		} else {
			return null;
		}
	}

	/**
	 * 将"20160812043959"格式的时间转化为Unix的系统时间戳
	 * 
	 * @param date
	 * @return
	 */
	public static Long dateStr2UnixDare(String date) {

		if (notEmpty(date)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			try {
				Date sysDate = sdf.parse(date);
				Long time = sysDate.getTime() / 1000;
				return time;
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Date tempDate = new Date();
			long tempTime = tempDate.getTime();
			return tempTime;
		} else {
			return null;
		}
	}

	/**
	 * 按照参数format的格式，日期转字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date, String format) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} else {
			return "";
		}
	}

	/**
	 * 在批量支付时,产生批量转账的批次号 类型:String,长度:不超过32位
	 * 
	 * @return
	 */
	public static synchronized String getBatchNo() {
		long time = System.currentTimeMillis();
		String result = "BN" + time + num;
		num++;
		if (num == 1000) {
			num = 100;
		}
		return result;
	}
	
	/**
	 * 在批量支付时,产生批量转账的批次号 类型:String,长度:不超过32位
	 * 
	 * @return
	 */
	public static synchronized String getMarginNo() {
		long time = System.currentTimeMillis();
		String result = "TM" + time + num;
		num++;
		if (num == 1000) {
			num = 100;
		}
		return result;
	}
	
	/**
	 * 在批量支付时,产生批量转账的批次号 类型:String,长度:不超过32位
	 * 
	 * @return
	 */
	public static synchronized String getZhaoBiaoOrderNo() {
		long time = System.currentTimeMillis();
		String result = "ZB" + time + num;
		num++;
		if (num == 1000) {
			num = 100;
		}
		return result;
	}
	
	/**
	 * 在批量支付时,产生批量转账的批次号 类型:String,长度:不超过32位
	 * 
	 * @return
	 */
	public static synchronized String getCaiGouZhaoBiaoOrderNo() {
		long time = System.currentTimeMillis();
		String result = "CG" + time + num;
		num++;
		if (num == 1000) {
			num = 100;
		}
		return result;
	}
	
	
	/**
	 * 在批量支付时,产生批量转账的批次号 类型:String,长度:不超过32位
	 * 
	 * @return
	 */
	public static synchronized String getAnnexOrderNo() {
		long time = System.currentTimeMillis();
		String result = "AA" + time + num;
		num++;
		if (num == 1000) {
			num = 100;
		}
		return result;
	}
	
	/**
	 * 在批量支付时,产生批量转账的批次号 类型:String,长度:不超过32位
	 * 
	 * @return
	 */
	public static synchronized String getZhaoBiaoFenQiOrderNo() {
		long time = System.currentTimeMillis();
		String result = "FQ" + time + num;
		num++;
		if (num == 1000) {
			num = 100;
		}
		return result;
	}

	/**
	 * 在批量支付时,产生批量转账的批次号 类型:String,长度:不超过32位
	 * 
	 * @return
	 */
	public static synchronized String getSerialNo() {
		long time = System.currentTimeMillis();
		String result = "SN" + time + num;
		num++;
		if (num == 1000) {
			num = 100;
		}
		return result;
	}

	/**
	 * 产生订单序列号
	 * 
	 * @return
	 */
	public static synchronized String getOrderNo(String sign) {
		String temp = "SD";
		if (sign != null) {
			if (!StringUtils.isEmpty(sign)) {
				temp = sign;
			}
		}
		long time = System.currentTimeMillis();
		String result = temp + time + num;
		num++;
		if (num == 1000) {
			num = 100;
		}
		return result;
	}
	
	/**
	 * 产生退货编号
	 * 
	 * @return
	 */
	public static synchronized String getReturnNo(String sign) {
		String temp = "SD";
		if (sign != null) {
			if (!StringUtils.isEmpty(sign)) {
				temp = sign;
			}
		}
		long time = System.currentTimeMillis();
		String result = temp + time + num;
		num++;
		if (num == 1000) {
			num = 100;
		}
		return result;
	}
	
	/**
	 * 交易保支付 批次号 类型:String,长度:不超过32位  交易保专用 保证金
	 * 
	 * @return
	 */
	public static synchronized String getRouteorderNoBond() {
		long time = System.currentTimeMillis();
		String result = "TB" + time + num;
		num++;
		if (num == 1000) {
			num = 100;
		}
		return result;
	}
	/**
	 * 交易保支付 批次号 类型:String,长度:不超过32位  交易保专用 支付货款
	 * 
	 * @return
	 */
	public static synchronized String getRouteorderNoGood() {
		long time = System.currentTimeMillis();
		String result = "TG" + time + num;
		num++;
		if (num == 1000) {
			num = 100;
		}
		return result;
	}
	/**
	 * 交易保 随机批次号 类型:String,长度:不超过32位  交易保专用 支付货款
	 * 
	 * @return
	 */
	public static synchronized String getBsSBatchNo() {
		StringBuilder sb=new StringBuilder();
		long time = System.currentTimeMillis();
		sb.append(time);
		sb.append(RandomStr32.getStr7());
		return sb.toString();
	}

/*	*//**
	 * 将Json转化为Map
	 * 
	 * @param json
	 * @return
	 *//*
	@SuppressWarnings("unchecked")
	public static Map<String, Object> changeJsonToMap(JSONObject json) {
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator<String> it = json.keys();
		while (it.hasNext()) {
			String key = (String) it.next();
			String value = json.getString(key);
			map.put(key, value);
		}
		return map;
	}*/

	/**
	 * JsonArray转化为List
	 * 
	 * @param jsonArray
	 * @return
	 *//*
	public static List<Map<String, Object>> changJsonArrayToList(
			JSONArray jsonArray) {
		List<Map<String, Object>> arrayList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			Map<String, Object> jsonMap = changeJsonToMap(jsonObject);
			arrayList.add(jsonMap);
		}
		return arrayList;
	}*/

	// 加密
	public static String getBase64(String str) {
		byte[] b = null;
		String s = null;
		try {
			b = str.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (b != null) {
			s = Base64.encodeBase64String(b);
		}
		return s;
	}

	// 解密
	public static String getFromBase64(String s) {
		byte[] b = null;
		String result = null;
		if (s != null) {
			try {
				b = Base64.decodeBase64(s);
				result = new String(b, "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String MD5(String s) {
		try {
			byte[] btInput = s.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < md.length; i++) {
				int val = ((int) md[i]) & 0xff;
				if (val < 16)
					sb.append("0");
				sb.append(Integer.toHexString(val));
			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 发送短信验证码
	 * 
	 * @param phone
	 * @param request
	 * @throws Exception
	 *//*
	public static Object sendCode(String phone, String fcontent,String lcontent,HttpServletRequest request)
			throws Exception {
		Map<String, Object> result = new HashMap<String,Object>();
		StringBuilder code = new StringBuilder();
		Random random = new Random();
		// 6位验证码
		for (int i = 0; i < 6; i++) {
			code.append(String.valueOf(random.nextInt(10)));
		}
		HttpSession session = request.getSession();
		session.setAttribute("PHONE", phone);
		session.setAttribute("CODE", code.toString());
		session.setAttribute("SENDTIME", new Date().getTime());
		String smsText = "验证码：" + code;
		System.out.println("手机号：" + phone + ", " + smsText);
		// 短信内容
		 String content = fcontent+  code.toString()
				+ lcontent;

		log.info("短信验证码,发送内容:" + content);

		try {
			SendMobileMessage.sendMsg(phone, content);
			result.put("success", "true");
		} catch (Exception e) { 
			log.error("短信验证码功能失败");
			log.error(e.toString());
			result.put("success", "false"); 
		}
		return result;
	}*/
	/**
	 * 验证短信验证码
	 * @param inputCode
	 * @param inputPhone
	 * @param minute 为0时永不过时
	 * @param request
	 * @return
	 */
	public static Object validatecode(String inputCode, String inputPhone,
			int minute, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String phone = (String) session.getAttribute("PHONE");
		String  code= (String) session.getAttribute("CODE");
		long sendtime = (long) session.getAttribute("SENDTIME");
		long nowtime = new Date().getTime();
		Map<String, Object> result = new HashMap<String,Object>();
	
		if(minute!=0){
			if (nowtime - sendtime > minute * 60 * 1000) {
				result.put("success", "overtime");
			}
		}
		if (phone.equals(inputPhone) && code.equalsIgnoreCase(inputCode)) {
			result.put("success", "true");
		} else {
			result.put("success", "false"); 
		}
		return result;
	}
	/** 
     * 手机号验证 
     *  
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isMobile(String str) {   
        Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号  
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    }  
	
	/** 
     * 判断字符串是否数值  
     * @param str 
     * @return true:是数值 ；false：不是数值  
     * @author:WD_SUHUAFU 
     */  
    public static boolean isNumber(String str)  
    {  
//        Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]*)?$");  
        Pattern pattern = Pattern.compile("^[0-9]+$");  
        Matcher match=pattern.matcher(str);  
        return match.matches();  
    }
    /**
     * 判断字符串是否为数值  包含两位小数
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]{1,2})?$"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
    /**
     * 判断字符串是否为数值  包含三位小数
     * @param str
     * @return
     */
    public static boolean isNumertr(String str){ 
    	Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]{1,3})?$"); 
    	Matcher isNum = pattern.matcher(str);
    	if( !isNum.matches() ){
    		return false; 
    	} 
    	return true; 
    }
   
    /**
     * 判断字符串是否为大于0的整数数值 
     * @param str
     * @return
     */
    public static boolean isNum(String str){ 
		   Pattern pattern = Pattern.compile("^[0-9]*[1-9][0-9]*$"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
	
	/**
	 * 判断字符串是否为非负数值  包含小数
	 * @param str
	 * @return
	 */
	public static boolean isZNumeric(String str){ 
		Pattern pattern = Pattern.compile("^(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$"); 
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false; 
		} 
		return true; 
	}
	/**
	 * 匹配 英文2-16个字符
	 * @param str
	 * @return
	 */
	public static boolean isEng(String str){ 
		Pattern pattern = Pattern.compile("^[a-zA-Z]{3,16}$"); 
		Matcher isR = pattern.matcher(str);
		if( !isR.matches() ){
			return false; 
		} 
		return true; 
	}
    /**
     * 匹配汉字 英文 和数字 和下划线
     * @param str
     * @return
     */
    public static boolean isHys(String str){ 
    	Pattern pattern = Pattern.compile("^([\u4E00-\u9FFF]|\\w)+$"); 
    	Matcher isR = pattern.matcher(str);
    	if( !isR.matches() ){
    		//检查是否是汉字和空格的结合体
    		pattern = Pattern.compile("^([\u4E00-\u9FFF]|\\w)+(\\s|\u3000)*([\u4E00-\u9FFF]|\\w)*$");
    		isR = pattern.matcher(str);
    		if(isR.matches()){
    			return true;
    		}
    		return false; 
    	} 
    	return true; 
    }
    
    /**
     * 匹配汉字 英文 和数字 和下划线 横线
     * @param str
     * @return
     */
    public static boolean isHysh(String str){ 
    	Pattern pattern = Pattern.compile("^[a-zA-Z0-9_\\-\u4e00-\u9fa5]+$"); 
    	Matcher isR = pattern.matcher(str);
    	if( !isR.matches() ){
    		return false; 
    	} 
    	return true; 
    }
    /**
     * 匹配 英文和数字 和下划线  且  以 英文开头的字符串
     * @param str
     * @return
     */
    public static boolean isYs(String str){ 
    	Pattern pattern = Pattern.compile("^[a-zA-Z]+(\\w)*$"); 
    	Matcher isR = pattern.matcher(str);
    	if( !isR.matches() ){
    		return false; 
    	} 
    	return true; 
    }
    /**
     * 匹配汉字和英文
     * @param str
     * @return
     */
    public static boolean isHY(String str){ 
    	Pattern pattern = Pattern.compile("^([\u4E00-\u9FFF]|[a-zA-Z])+$"); 
    	Matcher isR = pattern.matcher(str);
    	if( !isR.matches() ){
    		return false; 
    	} 
    	return true; 
    }
    /**
     * 匹配汉字和英文和数字
     * @param str
     * @return
     */
    public static boolean isCompay(String str){ 
    	Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5_a-zA-Z0-9]+$"); 
    	Matcher isR = pattern.matcher(str);
    	if( !isR.matches() ){
    		return false; 
    	} 
    	return true; 
    }
    /**
     * 比较两个数字字符串大小
     * @param fnum
     * @param lnum
     * @return
     */
    public static boolean compareNum(String fnum,String lnum) {
		if(Tools.isNumeric(fnum)&&Tools.isNumeric(lnum)){
			if(Double.valueOf(lnum)-Double.valueOf(fnum)>0){
				return true;
			}
		}
		return false;
		
	}
    /**
	 * 验证参数
	 * @param param
	 * @param i
	 * @return
	 */
    public static boolean paramValidate(String param,int i){
		Pattern pattern = null;
		String zzstr=null;
		Matcher mt=null;
		boolean b=false;
		switch(i){
		case 0://注册用用户名
			    zzstr ="^\\w{6,20}$";
//			    zzstr ="(^\\w{1,20}$)|(^[1][3,4,5,7,8][0-9]{9}$)";
				break;
		case 1://手机号
			zzstr="^[1][3,4,5,7,8][0-9]{9}$";
			break;
		case 2://邮箱
			zzstr="^\\w+[@]{1}[a-zA-Z0-9]+[.]+[a-z]+";
			break;
		case 3://真实姓名
			zzstr="^(([\u4E00-\u9FA5]{2,7})|([a-zA-Z]{3,10}))$";
			break;
		case 4://固话
			zzstr ="(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|" +  
            "(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)";
			break;
		case 5://匹配图片地址  http://localhost:8080/website/userImgs/tempath/201610/19/09-55-02-80-34053.jpg
			zzstr="^(http://)?.*.(jpg|jpeg|gif|png|JPG|JPEG|GIF|PNG)$";
			break;
		case 6://匹配qq号码
			zzstr="^[1-9][0-9]{4,}$";
			break;
		case 7://匹配登陆用户名和手机号码
			zzstr ="(^\\w{1,20}$)|(^[1][3,4,5,7,8][0-9]{9}$)";
			break;
		case 8://单匹配用户名
			zzstr ="^\\w{1,20}$";
			break;
		case 9://匹配身份证号码
			zzstr ="^\\d{6,}\\w*";
			break;
		}
		pattern = Pattern.compile(zzstr);
		mt = pattern.matcher(param.trim());
		b = mt.matches();
		return b;
	}

	/**
	 * 在系统上传目录中创建文件夹
	 * 
	 * @param dir
	 * @return
	 */
	public static File createDirectory(String dir) {
		if (dir == null || dir.trim().length() == 0)
			dir = "unknow";
		//String base = getUploadRoot();
		String path =  dir + File.separator;

		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
			log.info("dir '" + dir + "' created");
		}
		return f;
	}
	
	/** 
     * 手机号和电话验证验证 
     *  
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isMobileAndPhone(String str) {   
        Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$|(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|" +  
            "(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)"); // 验证手机号和电话  
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    }  
	/**
	 * 获取文件上传的根目录，文件上传根目录是配置在config.properties文件中的upload.dir中，
	 * 如果没配置或者值为空，则会返回系统目录中的上传目录
	 * 
	 * @return
	 */
	/*public static String getUploadRoot() {

		//String base = Config.getString("upload.dir");
		if (base == null || base.trim().length() == 0)
			base = ServletRequestContext.getServletContext().getRealPath(
					File.separator);
		if (!base.endsWith("/") && !base.endsWith("\\"))
			base = base + File.separator;
		return base + FOLDER + File.separator;
	}*/
    /**
     * 比较时间大小
     * @param DATE1 较小时间
     * @param DATE2  较大时间
     * @return 1  大于   -1 小于  0 等于   2 错误
     */
    public static int compare_date(String DATE1, String DATE2) {
    	DATE1.compareTo(DATE2);
    	 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
             
                return -1;
            } else if (dt1.getTime() < dt2.getTime()) {
              
                return 1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return 2;
        }
        
    }
    /**
     * 校验日期格式   yyyy-MM-dd
     * @param str
     * @return
     */
    public static boolean isValidDate(String str) {  
        //String str = "2007-01-02";  
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");  
        try{  
            Date date = (Date)formatter.parse(str);  
            
            return str.equals(formatter.format(date));  
        }catch(Exception e){  
            return false;  
        }  
    }  
    /**
     *  校验日期格式 yyyy-MM-dd hh:mm:ss
     * @param str
     * @return
     */
    public static boolean isValidTime(String str) {  
        //String str = "2007-01-02";  
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        try{  
            Date date = (Date)formatter.parse(str); 
            
            return str.equals(formatter.format(date));  
        }catch(Exception e){  
            return false;  
        }  
    }  
	public static void mark( MultipartFile file,String outImgPath, String type,Color markContentColor, String waterMarkContent) {  
        try {  
            // 读取原图片信息  
        	
        	//file.getInputStream();
           /* File srcImgFile = new File(srcImgPath); */ 
       /* 	BufferedImage srcImg = ImageIO.read((File) file);*/  
        	 Image srcImg = ImageIO.read(file.getInputStream());
            int srcImgWidth = srcImg.getWidth(null);  
            int srcImgHeight = srcImg.getHeight(null);  
            // 加水印  
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);  
            Graphics2D g = bufImg.createGraphics();  
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);  
            //Font font = new Font("Courier New", Font.PLAIN, 12);  
            Font font = new Font("宋体", Font.PLAIN, 30);    
            g.setColor(markContentColor); //根据图片的背景设置水印颜色  
              
            g.setFont(font);  
            int x = srcImgWidth - getWatermarkLength(waterMarkContent, g) - 3;  
            int y = srcImgHeight - 3;  
            //int x = (srcImgWidth - getWatermarkLength(watermarkStr, g)) / 2;  
            //int y = srcImgHeight / 2;  
            g.drawString(waterMarkContent, x, y);  
            g.dispose();  
            // 输出图片  
            FileOutputStream outImgStream = new FileOutputStream(outImgPath);  
            ImageIO.write(bufImg, type, outImgStream);  
            outImgStream.flush();  
            outImgStream.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * 获取水印文字总长度 
     * @param waterMarkContent 水印的文字 
     * @param g 
     * @return 水印文字总长度 
     */  
    public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {  
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());  
    }  
	private Tools() {
	}
	
	/**
	 * 将字符串转化BigDecimal
	 * @param str
	 * @param nu 小数位数
	 * @return
	 */
	 public static BigDecimal strTodec(String str,int nu) {  
		 BigDecimal bd=new BigDecimal(str);   
		 return bd.setScale(nu, BigDecimal.ROUND_HALF_UP); 
	 }
	 /**
	  * 将double装换为%字符串
	  * @param dou 需要装换的数值
	  * @param nu 保留的小数位
	  * @return
	  */
	 public static String douToper(Double dou,int nu) {  
		   
		 return String.format("%."+nu+"f", dou*100)+"%"; 
	 }
	 /**
	  * 将字符串的小数点后面的0取出
	  * @param str
	  * @return
	  */
	 public static String removezero(String str){
		 if(str.indexOf(".") > 0){  
			 str = str.replaceAll("0+?$", "");//去掉多余的0  
			 str = str.replaceAll("[.]$", "");//如最后一位是.则去掉  
			 
		 }  
		 return str;
	 }
	 /**
		 * 在批量支付时,产生批量转账的批次号 类型:String,长度:不超过32位
		 * 
		 * @return
		 */
		public static synchronized String getH5PayNO() {
			long time = System.currentTimeMillis();
			String result = "HP" + time + num;
			num++;
			if (num == 1000) {
				num = 100;
			}
			return result;
		}
		
		public static void main(String[] args) {
			System.out.println(Tools.isNum(""));
			
		}
		
		/**
		 * 产生订单序列号
		 * 
		 * @return
		 */
		public static synchronized String getApplyNo() {
			String temp = "INV-"+CommUtil.dateToString(new Date(), "YYMMdd");
			
			String result = temp + anum;
			anum++;
			if (anum == 999999) {
				anum = 100000;
			}
			return result;
		}
		
		/**
		 * 得到当前登录名称
		 * @return
		 */
		public static synchronized String getRegistUserName(){
			String pushN=new StringBuffer().append(String.valueOf(System.currentTimeMillis())).append(String.valueOf((int)((Math.random()*9+1)*1000))).toString();
			String name=new StringBuffer().append("ezs").append(MD5Util.md5Encode(pushN).substring(15)).toString();
			System.out.println(name);
			return name;
		}
}
