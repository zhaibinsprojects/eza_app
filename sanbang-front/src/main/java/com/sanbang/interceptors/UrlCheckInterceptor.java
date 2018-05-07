package com.sanbang.interceptors;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sanbang.redis.RedisConstants;
import com.sanbang.redis.RedisHelper;
import com.sanbang.redis.RedisResult;
import com.sanbang.utils.CheckUtil;



public class UrlCheckInterceptor implements HandlerInterceptor{

	private static Logger log=Logger.getLogger(UrlCheckInterceptor.class);
	
	@Value("${app.filter.urls}")
	private String filterurls;
	
	private Set<String> filterUrls=null;
	
	//加载需要过滤的方法
	@PostConstruct 
	public void initMethod(){
		String [] tempFilterUrls = StringUtils.split(filterurls, ",");
		filterUrls = new HashSet<>();
		for(int i=0;i<tempFilterUrls.length;i++){
			filterUrls.add(tempFilterUrls[i].trim().replaceAll("\\s*|\t|\r|\n", ""));
			
		}
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView arg3) throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		
		String saveLinkInfoUrl=request.getServletPath();
		
		log.info("App过滤当前地址："+saveLinkInfoUrl);
		
		if(filterUrls.contains(saveLinkInfoUrl)){
			return check(request,response);
		}else{
			log.info("App直接放行："+saveLinkInfoUrl);
			return true;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public boolean check(HttpServletRequest request,HttpServletResponse response) throws Exception{
		// TODO 暂时处理短信验证码接口
		String timestamp = request.getParameter("timestamp");
		String signature = request.getParameter("signature");
		String mobile = request.getParameter("mobile");
		log.info("timestamp:"+timestamp+",signature:"+signature+",mobile:"+mobile);

		if(StringUtils.isBlank(signature) || StringUtils.isBlank(timestamp) || StringUtils.isBlank(mobile)){
			request.getRequestDispatcher("/activity/appurlcheck.do").forward(request, response);
		}
		
		RedisResult<String> resu = RedisHelper.hget("APPURLCHECK", timestamp);
		if(resu.getCode() == RedisConstants.SUCCESS){
			if(StringUtils.isNotBlank(resu.getResult())){
				request.getRequestDispatcher("/activity/appurlcheck.do").forward(request, response);
				return false;
			}
		}else{
			request.getRequestDispatcher("/activity/appurlcheck.do").forward(request, response);
			return false;
		}
		RedisResult<String> result = RedisHelper.hset("APPURLCHECK", timestamp, timestamp, 60*60*2);
		if(result.getCode() != RedisConstants.SUCCESS){
			request.getRequestDispatcher("/activity/appurlcheck.do").forward(request, response);
			return false;
		}
		boolean checkSignature = CheckUtil.checkSignature(signature, timestamp, mobile);
		if(!checkSignature){
			request.getRequestDispatcher("/activity/appurlcheck.do").forward(request, response);
			return false;
		}
		return checkSignature;
	}
	
}
