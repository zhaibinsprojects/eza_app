package com.sanbang.utils.httpclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;


/**
 * 封装http请求的参数
 * @author zhangxiantao
 * @version 1.0
 * @date 2016年4月23日13:11:44
 */
public class HttpRequestParam {
	
	private Map<String,String> mapHeaderParams=new HashMap<String,String>(); //头部信息 	
	private List<BasicNameValuePair> lstUrlParams = new ArrayList <BasicNameValuePair>();  //请求参数
	
	//构造函数
	public HttpRequestParam() {
		
	}	
	//构造函数
	public HttpRequestParam(List<BasicNameValuePair> urlParams,Map<String,String> headerParams) {
		this.lstUrlParams = urlParams;
		this.mapHeaderParams = headerParams;
	} 
	//添加url参数
	public void addUrlParams(BasicNameValuePair param){
		lstUrlParams.add(param);
	}

	public Map<String, String> getMapHeaderParams() {
		return mapHeaderParams;
	}

	public void setMapHeaderParams(Map<String, String> mapHeaderParams) {
		this.mapHeaderParams = mapHeaderParams;
	}

	public List<BasicNameValuePair> getLstUrlParams() {
		return lstUrlParams;
	}

	public void setLstUrlParams(List<BasicNameValuePair> lstUrlParams) {
		this.lstUrlParams = lstUrlParams;
	}


	
}
