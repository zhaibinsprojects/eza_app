package com.sanbang.interceptors;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sanbang.utils.CheckUtil;



public class RemoteUrlCheckInterceptor implements HandlerInterceptor{

	private static Logger log=Logger.getLogger(RemoteUrlCheckInterceptor.class);
	
	//加载需要过滤的方法
	@PostConstruct 
	public void initMethod(){
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
		
		log.info("请求远程调用当前地址："+saveLinkInfoUrl);
		
		return check(request,response);
		
	}
	
	@SuppressWarnings("unchecked")
	public boolean check(HttpServletRequest request,HttpServletResponse response) throws Exception{
		// TODO 暂时处理短信验证码接口
		String username = request.getParameter("username");
		String signature = request.getParameter("signature");
		String mobile = request.getParameter("mobile");
		log.info("username:"+username+",signature:"+signature+",mobile:"+mobile);
		
		boolean checkSignature = CheckUtil.checkSignature(signature, username, mobile);
		if(!checkSignature){
			response.setHeader("Content-type", "text/html;charset=UTF-8");  
			response.setCharacterEncoding("UTF-8");
			response.getWriter().println("非法请求！");
			return false;
		}
		return checkSignature;
	}
	
}
