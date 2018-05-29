package com.sanbang.utils;

import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import com.sanbang.utils.httpclient.HttpRemoteRequestUtils;
import com.sanbang.utils.httpclient.HttpRequestParam;
import com.sanbang.vo.Dictionary;

/**
 * 发送短信
 * 
 * @author zhangxiantao
 *  
 * 2016年8月9日
 */
public class SendMobileMessage {
	
	//日志
	private static Logger log = Logger.getLogger(SendMobileMessage.class.getName());

	/**
	 * 短信发送
	 * @param mobile	手机号,多个手机号发送用逗号隔开
	 * @param content	内容
	 * @return
	 */
	public static String sendMsg(String mobile, String content){
	
		log.info("发短信时间接口开始调用,时间:"+System.currentTimeMillis());
		
		String url = Dictionary.getProperties("common.sendMessage.zhizhen");
		
		HttpRequestParam httpParam = new HttpRequestParam();
		httpParam.addUrlParams(new BasicNameValuePair("UserName", "xinyi"));
		httpParam.addUrlParams(new BasicNameValuePair("UserPass", "ezaisheng2016"));
		httpParam.addUrlParams(new BasicNameValuePair("Mobile", mobile));
		httpParam.addUrlParams(new BasicNameValuePair("Content", content));
		
		log.info("请求参数"+httpParam.getLstUrlParams().toString());
		
		String result = null;
		try {
			result = Result.success().toString();
			HttpRemoteRequestUtils.doPostToString(url, httpParam);
		} catch (Exception e) {
			log.error("短信发送失败");
			log.error(e.toString());
		}
		log.info("短信发送结果"+result.toString());
		
		return result;
	}
	
	private SendMobileMessage() {
	}

	public static void main(String[] args) throws Exception {
		
		//sendMsg("18910821841,15602113833", "JavaSendMessageTest");
	}
}
