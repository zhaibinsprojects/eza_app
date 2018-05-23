package com.sanbang.advice.service.impl;

import java.io.IOException;
import java.util.Map.Entry;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.sanbang.advice.service.CommonOrderAdvice;
import com.sanbang.utils.Result;
import com.sanbang.utils.httpclient.HttpRemoteRequestUtils;
import com.sanbang.utils.httpclient.HttpRequestParam;
import com.sanbang.vo.DictionaryCode;

import net.sf.json.JSONObject;

/**
 * 统一回调接口
 * @author LENOVO
 *
 */
public class CommonOrderAdviceImpl implements CommonOrderAdvice{

	// 上下文地址
	@Value("${config.orderform.advice}")
	public String orderformadviceurl;
		
	private Logger log=Logger.getLogger(CommonOrderAdviceImpl.class);
	@Override
	public Result orderFormAdvice(String order_no,String order_status) {
		log.info("智慧云行通知===<开始>order_no"+order_no+"order_status"+order_status);
		Result result=Result.failure();
		try {
			JSONObject callBackRet = null;
			HttpRequestParam httpParam = new HttpRequestParam();
			httpParam.addUrlParams(new BasicNameValuePair("order_no", order_no));
			httpParam.addUrlParams(new BasicNameValuePair("order_status", order_status));
			callBackRet = HttpRemoteRequestUtils.doPost(orderformadviceurl, httpParam);
			result.setSuccess(true);
			result.setMsg("调用成功");
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			log.info("智慧云行通知===<成功>order_no"+order_no+"order_status"+order_status);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			log.info("智慧云行通知===<错误>order_no"+order_no+"order_status"+order_status+"errorcode:"+e.toString());
			e.printStackTrace();
		}
		
		return null;
	}

}
