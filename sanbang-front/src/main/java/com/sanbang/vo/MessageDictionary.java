package com.sanbang.vo;

public class MessageDictionary {

	//注册短信内容
	public static String  registCode(StringBuilder code){
		return "【易再生网】您的短信验证码:"+code.toString()+",请勿告诉他人,有效时间为10分钟!";
	}
}
