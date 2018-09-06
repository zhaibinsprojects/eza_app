package com.sanbang.hangq.servive;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sanbang.utils.Result;

public interface HangqPayAdviceService {
	/**
	 * AliPay回调
	 * @param request
	 * @param param
	 * @param result
	 * @return
	 */
	public  Result aliPayAdvice(HttpServletRequest request,Map<String, Object> param,Result result);
	
}
