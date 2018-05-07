package com.sanbang.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class BasePathInterceptor implements HandlerInterceptor{
	
	@Value("${consparam.ezaisheng.base.url}")
	private String baseurl;
	
	@Value("${consparam.ser.baseurl}")
	private String serurl;

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		 
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse arg1,
			Object arg2) throws Exception {
		request.setAttribute("baseurl", baseurl);
		request.setAttribute("serurl", serurl);
		return true;
	}

}
