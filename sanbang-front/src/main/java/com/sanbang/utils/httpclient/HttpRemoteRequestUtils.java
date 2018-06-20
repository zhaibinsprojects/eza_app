/**
 * 
 */
package com.sanbang.utils.httpclient;

import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.sanbang.utils.SendMobileMessage;
import com.sanbang.vo.Dictionary;

/**
 * 
 * @author zhangxiantao
 * 
 */
public class HttpRemoteRequestUtils {

	//日志
	private static Logger log = Logger.getLogger(HttpRemoteRequestUtils.class.getName());
	/**
	 * 提交post请求
	 * @param URL
	 * @param httpParams
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static JSONObject doPost(String URL, HttpRequestParam httpParams)
			throws ClientProtocolException, IOException {

		DefaultHttpClient defaulthttpclient = new DefaultHttpClient();
		HttpClient httpclient = WebCilentWrapper.wrapClient(defaulthttpclient);	//处理网络通信证书
		try {
			HttpPost httpost = new HttpPost(URL);
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
					httpParams.getLstUrlParams(), "UTF-8");
			httpost.setEntity(entity);
			HttpResponse response = httpclient.execute(httpost);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity result = response.getEntity();
				String string = EntityUtils.toString(result, "UTF-8");
				JSONObject jsonResult = JSONObject.fromObject(string);
				EntityUtils.consume(result);
				return jsonResult;	
			}else{
            	log.error("调用接口时出现错误，网页请求的状态码为："+response.getStatusLine().getStatusCode());
            	return null;
            }
		} catch(Exception e){
			log.error("----------------调用接口出错doPost方法！---");
			log.error(e.toString());
			//SendMobileMessage.sendMsg(Dictionary.getProperties("systemErrorAdvice"), "Post请求失败,原因:"+e.toString());
			return null;
		}finally {
			httpclient.getConnectionManager().shutdown();
		}
	}
	
	/**
	 * 提交get请求
	 * @param URL
	 * @param httpParams
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static JSONObject doGet(String URL) throws ClientProtocolException, IOException {
		DefaultHttpClient defaulthttpclient = new DefaultHttpClient();
		HttpClient httpclient = WebCilentWrapper.wrapClient(defaulthttpclient);	//处理网络通信证书
		try {
			HttpGet httpGet = new HttpGet(URL);
			httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
			HttpResponse response = httpclient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity result = response.getEntity();
				String string = EntityUtils.toString(result, "UTF-8");
				JSONObject jsonResult = JSONObject.fromObject(string);
				EntityUtils.consume(result);
				return jsonResult;	
			}else{
            	log.error("调用接口时出现错误，网页请求的状态码为："+response.getStatusLine().getStatusCode());
            	return null;
            }
		} catch(Exception e){
			 log.error("----------------调用接口出错doGet方法！---");
			 log.error(e.toString(),e);
			 return null;
		}
		finally {
			httpclient.getConnectionManager().shutdown();
		}
	}
	/**
	 * 提交post请求,返回结果为Json数组
	 * @param URL
	 * @param httpParams
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static JSONArray doPostToArray(String URL, HttpRequestParam httpParams)
			throws ClientProtocolException, IOException {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			HttpPost httpost = new HttpPost(URL);
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
					httpParams.getLstUrlParams(), "UTF-8");
			// 设置表单提交编码为UTF-8
			httpost.setEntity(entity);
			HttpResponse response = httpclient.execute(httpost);
			HttpEntity result = response.getEntity();
			JSONArray jsonResult = JSONArray.fromObject(EntityUtils.toString(
					result, "UTF-8"));
			EntityUtils.consume(result);
			return jsonResult;
		} catch(Exception e){
			log.error("------------------------调用接口出错了dopostToArray方法");
			return null;
		}
		finally {
			httpclient.getConnectionManager().shutdown();
		}
	}
	/**
	 * 提交post请求,返回结果为String
	 * @param URL
	 * @param httpParams
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String doPostToString(String URL, HttpRequestParam httpParams)
			throws ClientProtocolException, IOException {

		DefaultHttpClient defaulthttpclient = new DefaultHttpClient();
		HttpClient httpclient = WebCilentWrapper.wrapClient(defaulthttpclient);	//处理网络通信证书
		try {
			HttpPost httpost = new HttpPost(URL);
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
					httpParams.getLstUrlParams(), "UTF-8");
			httpost.setEntity(entity);
			HttpResponse response = httpclient.execute(httpost);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity result = response.getEntity();
				String string = EntityUtils.toString(result, "UTF-8");
				return string;	
			}else{
            	log.error("调用接口时出现错误，网页请求的状态码为："+response.getStatusLine().getStatusCode());
            	return null;
            }
		} catch(Exception e){
			e.printStackTrace();
			log.error("----------------调用接口出错doPost方法！---");
			 return null;
		}finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	/**
	 * 提交post请求,返回结果为String			***请求头中加入cookie***
	 * @param URL
	 * @param httpParams
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String doPostCookieToString(String URL, HttpRequestParam httpParams, String cookie)
			throws ClientProtocolException, IOException {
		
		DefaultHttpClient defaulthttpclient = new DefaultHttpClient();
		HttpClient httpclient = WebCilentWrapper.wrapClient(defaulthttpclient);	//处理网络通信证书
		try {
			HttpPost httpost = new HttpPost(URL);
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
					httpParams.getLstUrlParams(), "UTF-8");
			httpost.setEntity(entity);
			httpost.setHeader("Cookie", cookie);
			HttpResponse response = httpclient.execute(httpost);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity result = response.getEntity();
				String string = EntityUtils.toString(result, "UTF-8");
				return string;	
			}else{
				log.error("调用接口时出现错误，网页请求的状态码为："+response.getStatusLine().getStatusCode());
				return null;
			}
		} catch(Exception e){
			log.error("----------------调用接口出错doPost方法！---");
			return null;
		}finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	/**
	 * 提交post请求			***请求头中加入cookie***
	 * @param URL
	 * @param httpParams
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static JSONObject doPostCookie(String URL, HttpRequestParam httpParams, String cookie)
			throws ClientProtocolException, IOException {

		DefaultHttpClient defaulthttpclient = new DefaultHttpClient();
		HttpClient httpclient = WebCilentWrapper.wrapClient(defaulthttpclient);	//处理网络通信证书
		try {
			HttpPost httpost = new HttpPost(URL);
			log.info("调用doPostCookie,httpParams:"+httpParams.getLstUrlParams());
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
					httpParams.getLstUrlParams(), "UTF-8");
			httpost.setEntity(entity);
			log.info("调用doPostCookie,cookie:"+cookie);
			httpost.setHeader("Cookie", cookie);
			HttpResponse response = httpclient.execute(httpost);
			log.info("调用doPostCookie,响应状态:"+response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity result = response.getEntity();
				String string = EntityUtils.toString(result, "UTF-8");
				JSONObject jsonResult = JSONObject.fromObject(string);
				EntityUtils.consume(result);
				return jsonResult;	
			}else{
            	log.error("调用接口时出现错误，网页请求的状态码为："+response.getStatusLine().getStatusCode());
            	return null;
            }
		} catch(Exception e){
			log.error("----------------调用接口出错doPost方法！---");
			log.error(e.toString());
			//SendMobileMessage.sendMsg(Dictionary.getProperties("systemErrorAdvice"), "Post请求失败,原因:"+e.toString());
			return null;
		}finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

/**
 * 提交post请求,发送json 数据，返回结果为String
 * @param URL
 * @param Obj
 * @return
 * @throws ClientProtocolException
 * @throws IOException
 */
public static JSONObject doPostJsonToString(String URL,Object json,Header header)
		throws ClientProtocolException, IOException {
	
	DefaultHttpClient defaulthttpclient = new DefaultHttpClient();
	HttpClient httpclient = WebCilentWrapper.wrapClient(defaulthttpclient);	//处理网络通信证书
	try {
		HttpPost httpost = new HttpPost(URL);
		StringEntity entity = new StringEntity(json.toString(),"UTF-8");//解决中文乱码问题  
		 entity.setContentEncoding("UTF-8");  
		 entity.setContentType("application/json");
		httpost.setEntity(entity);
		if(header!=null)
		httpost.setHeader(header);
		HttpResponse response = httpclient.execute(httpost);
		log.info("调用doPostCookie,响应状态:"+response.getStatusLine().getStatusCode());
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity result = response.getEntity();
			String string = EntityUtils.toString(result, "UTF-8");
			JSONObject jsonResult = JSONObject.fromObject(string);
			jsonResult.put("httpstatus", response.getStatusLine().getStatusCode());
			EntityUtils.consume(result);
			return jsonResult;	
		}else{
			HttpEntity result = response.getEntity();
			String string = EntityUtils.toString(result, "UTF-8");
			JSONObject jsonResult = JSONObject.fromObject(string);
			jsonResult.put("httpstatus", response.getStatusLine().getStatusCode());
			log.error("调用接口时出现错误，网页请求的状态码为："+response.getStatusLine().getStatusCode());
			return jsonResult;
		}
	} catch(Exception e){
		log.error("----------------调用接口出错doPost方法！---");
		log.error(e.toString());
//		SendMobileMessage.sendMsg(Dictionary.getProperties("systemErrorAdvice"), "Post请求失败,原因:"+e.toString());
		return null;
	}finally {
		httpclient.getConnectionManager().shutdown();
	}
}
/**
 * 提交get请求,发送json 数据，返回结果为String
 * @param URL
 * @param Obj
 * @return
 * @throws ClientProtocolException
 * @throws IOException
 */
public static JSONObject doGetJsonToString(String URL,Object json,Header header)
		throws ClientProtocolException, IOException {
	
	DefaultHttpClient defaulthttpclient = new DefaultHttpClient();
	HttpClient httpclient = WebCilentWrapper.wrapClient(defaulthttpclient);	//处理网络通信证书
	try {
		HttpGet httpGet = new HttpGet(URL);
		if(header!=null)
			httpGet.setHeader(header);
		HttpResponse response = httpclient.execute(httpGet);
		log.info("调用doPostCookie,响应状态:"+response.getStatusLine().getStatusCode());
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity result = response.getEntity();
			String string = EntityUtils.toString(result, "UTF-8");
			JSONObject jsonResult = JSONObject.fromObject(string);
			jsonResult.put("httpstatus", response.getStatusLine().getStatusCode());
			EntityUtils.consume(result);
			return jsonResult;	
		}else{
			HttpEntity result = response.getEntity();
			String string = EntityUtils.toString(result, "UTF-8");
			JSONObject jsonResult = JSONObject.fromObject(string);
			jsonResult.put("httpstatus", response.getStatusLine().getStatusCode());
			log.error("调用接口时出现错误，网页请求的状态码为："+response.getStatusLine().getStatusCode());
			return jsonResult;
		}
	} catch(Exception e){
		log.error("----------------调用接口出错doPost方法！---");
		log.error(e.toString());
//		SendMobileMessage.sendMsg(Dictionary.getProperties("systemErrorAdvice"), "Post请求失败,原因:"+e.toString());
		return null;
	}finally {
		httpclient.getConnectionManager().shutdown(); 
	}
}
}
