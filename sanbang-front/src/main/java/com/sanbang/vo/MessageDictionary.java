package com.sanbang.vo;

public class MessageDictionary {

	//注册短信内容
	public static String  registCode(StringBuilder code){
		return "尊敬的用户您好，您的短信验证码为:"+code.toString()+",请勿告诉他人,有效时间为10分钟!";
	}
	
	//无密登录短信内容
	public static String  loginCode(String code){
		return "尊敬的用户您好，您本次的动态登录码为"+code.toString()+"。温馨提示：您的动态登录码30分钟内有效，请安全保管。";
	}
	
	//修改密码
	public static String upPswCode(String code){
		return "尊敬的用户您好，您本次修改密码短信验证码为:"+code.toString()+",请勿告诉他人,有效时间为10分钟!";
	}
	
	//设置密保手机
	public static String securatePhoneCode(StringBuilder code){
		return "尊敬的用户您好，您本次设置密保手机的短信验证码为:"+code.toString()+",请勿告诉他人,有效时间为10分钟!";
	}
}
