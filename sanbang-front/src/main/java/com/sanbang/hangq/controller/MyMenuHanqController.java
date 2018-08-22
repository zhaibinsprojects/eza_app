package com.sanbang.hangq.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_user;
import com.sanbang.hangq.servive.MyMenuHangqService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;

@Controller
@RequestMapping("/menuhq")
public class MyMenuHanqController {

	@Autowired
	private MyMenuHangqService myMenuHangqService;
	
	/**
	 * 我的订阅
	 * @param request
	 * @return
	 */
	@RequestMapping("/myDingYue")
	@ResponseBody
	public Result myDingyueShow(HttpServletRequest request,@RequestParam(defaultValue="1",name="pageNo")int pageNo) {
		Result result=Result.failure();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			result=myMenuHangqService.myDingyueListShow(upi, request, result,pageNo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		
		return result;
	}
	
	/**
	 * 订阅详情查看
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDingyueInfo")
	public  Result getDingyueInfo(@RequestParam("id") long id,
			HttpServletRequest request) {
		Result result=Result.failure();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			result=myMenuHangqService.myDingyueInfoShow(upi, request, result,id);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		
		return result;
	}
	
	/**
	 * 我的收藏
	 * @param request
	 * @return
	 */
	@RequestMapping("/myCollected")
	@ResponseBody
	public Result myCollected(HttpServletRequest request,@RequestParam(defaultValue="1",name="pageNo")int pageNo) {
		Result result=Result.failure();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			result=myMenuHangqService.myShoucListShow(upi, request, result,pageNo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		
		return result;
	}
	
	
	
	/**
	  * 订阅支付
	 * @param request   HQPAY
	 * @return
	 */
	@RequestMapping("/DingyuePay")
	@ResponseBody
	public Result saveDingyueOrder(HttpServletRequest request,
			@RequestParam(defaultValue="",name="id")int id,
			@RequestParam(defaultValue="",name="urlParam")String urlParam) {
		Result result=Result.failure();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			result=myMenuHangqService.saveDingyuePic(request, upi, id, urlParam, result);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		
		return result;
	}
	
	
	/**
	 * 我的定制
	 * @param request
	 * @return
	 */
	@RequestMapping("/myDingZhi")
	@ResponseBody
	public Result myDingzhiListShow(HttpServletRequest request,
			@RequestParam(defaultValue="1",name="pageNo")int pageNo,
			@RequestParam(defaultValue="true",name="ispush")boolean ispush) {
		Result result=Result.failure();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			result=myMenuHangqService.myDingzhiListShow(upi, request, result, ispush, pageNo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		
		return result;
	}
	
	
	
	/**
	 * 我的定制
	 * @param request
	 * @return
	 */
	@RequestMapping("/myDingZhiInit")
	@ResponseBody
	public Result myDingZhiInit(HttpServletRequest request,
			@RequestParam(defaultValue="1",name="pageNo")int pageNo,
			@RequestParam(defaultValue="true",name="ispush")boolean ispush) {
		Result result=Result.failure();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			result=myMenuHangqService.myDingzhiListShow(upi, request, result, ispush, pageNo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		
		return result;
	}
}
