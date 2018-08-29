package com.sanbang.hangq.servive;

import javax.servlet.http.HttpServletRequest;

import org.apache.maven.model.Model;

public interface HangqPushService {

	public  void getPushDate(HttpServletRequest request,String pushcode,Model model) throws Exception;
	
}
