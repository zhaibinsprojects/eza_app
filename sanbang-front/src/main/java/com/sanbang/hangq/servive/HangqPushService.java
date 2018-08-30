package com.sanbang.hangq.servive;

import javax.servlet.http.HttpServletRequest;

import com.sanbang.utils.Result;

public interface HangqPushService {
	/**
	 * 推送记录展示
	 * @param request
	 * @param pushcode  (push+(dzid)+"时间yyyyMMdd)  base64"  
	 * @param result
	 * @return
	 */
	public  Result getPushDate(HttpServletRequest request,String pushcode,Result result);
	
}
