package com.sanbang.hangq.servive;

import javax.servlet.http.HttpServletRequest;

import com.sanbang.bean.ezs_user;
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
	
	/**
	 * 推送接口
	 */
	public void handleHangqPush();
	
	/**
	 * hangq 消息查看
	 * @param pageNo
	 * @param upi
	 * @param result
	 * @return
	 */
	public  Result  HangqMessagePage(int pageNo,ezs_user upi,Result result);
	
	/**
	 * 检查推送状态
	 * @param request
	 * @param pushcode
	 * @param result
	 * @return
	 */
	public Result checkPushStatus(HttpServletRequest request, String pushcode, Result result);
}
