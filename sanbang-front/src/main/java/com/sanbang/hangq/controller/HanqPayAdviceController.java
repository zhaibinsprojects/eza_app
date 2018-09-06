package com.sanbang.hangq.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_user;
import com.sanbang.hangq.servive.HangqPushService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;

@Controller
@RequestMapping("/app/hangq")
public class HanqPayAdviceController {

	private static Logger log = Logger.getLogger(HomeHangqIndexController.class);
	@Autowired
	private HangqPushService hangqPushService;

	private static String view = "/hangqv2/";

	/**
	 * 订阅列表
	 * 
	 * @param id
	 * @param catid
	 * @return
	 */
	@RequestMapping("/dataShow/{pushcode}")
	public String hangqgShow(@PathVariable String pushcode, Model model,HttpServletRequest request) {
		Result	result=Result.failure();
			result = hangqPushService.checkPushStatus(request, pushcode, result);
		model.addAttribute("pushcode", pushcode);
		//model.addAttribute("result", result);
		return view + "pushData";
	}

	/**
	 * 定制查看
	 * 
	 * @param id
	 * @param catid
	 * @return
	 */
	@RequestMapping("/pushData/{pushcode}")
	@ResponseBody
	public Result hangqgShow(@PathVariable String pushcode, HttpServletRequest request) {
		Result result = Result.failure();
		result = hangqPushService.getPushDate(request, pushcode, result);
		return result;
	}

	/**
	 * 定制查看
	 * @param id
	 * @param catid
	 * @return
	 */
	@RequestMapping("/hangqMessage")
	@ResponseBody
	public Result HangqMessagePage(HttpServletRequest request,
			@RequestParam(defaultValue="1",name="pageNo")int pageNo) {
		Result result=Result.failure();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
		result=hangqPushService.HangqMessagePage(pageNo, upi, result);
	}catch (Exception e) {
		e.printStackTrace();
		result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		result.setSuccess(false);
		result.setMsg("系统错误");
	}
		return result;
	}
	
	/**
	 * 定制查看
	 * 
	 * @param id
	 * @param catid
	 * @return
	 */
	@RequestMapping("/checkPushStatus/{pushcode}")
	@ResponseBody
	public Result checkPushStatus(@PathVariable String pushcode, HttpServletRequest request) {
		Result result = Result.failure();
		result = hangqPushService.checkPushStatus(request, pushcode, result);
		return result;
	}
	
}
