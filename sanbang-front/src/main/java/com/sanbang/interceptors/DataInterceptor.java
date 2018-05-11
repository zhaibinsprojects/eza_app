package com.sanbang.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.sanbang.bean.User_Proinfo;
import com.sanbang.bean.ezs_user;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.RedisUtils;


public class DataInterceptor implements HandlerInterceptor{

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
		
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if(upi != null){
			String saveLinkInfoUrl=request.getRequestURI();
			JSONObject param = new JSONObject();
			param.put("logintoken", upi.getUserkey());
			param.put("accresource", saveLinkInfoUrl);
			RedisUtils.setListSagment("ACTIVERECORDDATA", param.toJSONString());
		}

		return true;
		
	}
	
}
