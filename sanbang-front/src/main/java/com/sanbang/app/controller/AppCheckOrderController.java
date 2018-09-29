package com.sanbang.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_user;
import com.sanbang.buyer.service.CheckOrderService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;


@Controller
@RequestMapping("/app/checkOrder")
public class AppCheckOrderController {

	@Autowired
	private CheckOrderService checkOrderService;

	/**
	 * 查看对账单
	 * 
	 * @param request
	 * @param orderno
	 * @return
	 */
	@RequestMapping("/checkOrderH")
	@ResponseBody
	public Result getCheckOrderH(HttpServletRequest request, String orderno) {
		Result result = Result.failure();

		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if (upi == null) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			result = checkOrderService.getCheckOrderH(request, result, upi, orderno);
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return result;
	}

	/**
	 * 添加修改对账单
	 * 
	 * @param request
	 * @param orderno
	 * @return
	 */
	@RequestMapping("/addOrUpdateCheckOrder")
	@ResponseBody
	public Result addOrUpdateCheckOrder(
			 HttpServletRequest request) {
		Result result = Result.failure();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if (upi == null) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			result = checkOrderService.addOrUpdateCheckOrder(request, upi, result);
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return result;
	}

	
	
	/**
	 * 初始化
	 * 
	 * @param request
	 * @param orderno
	 * @return
	 */
	@RequestMapping("/getCheckOrderInit")
	@ResponseBody
	public Result getCheckOrderInit(
			 HttpServletRequest request,String orderno) {
		Result result = Result.failure();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if (upi == null) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;  
			}
			result = checkOrderService.getCheckOrderInit(request, upi, result, orderno);
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return result;
	}
	
	
	/**
	 * 合同预览
	 * 
	 * @param order_no
	 * @param request
	 * @return
	 */
	@RequestMapping("/contentPreview")
	@ResponseBody
	public Result showOrderContent(@RequestParam(name = "orderno", defaultValue = "") String orderno,
			HttpServletRequest request) {

		Result result = Result.success();
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("请求成功");
		try {
			
			result = checkOrderService.signContentProcess(result, orderno);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("查看合同失败");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return result;
	}

	/**
	 * 签订合同
	 * 
	 * @param order_no
	 * @param request
	 * @return
	 */
	@RequestMapping("/signContent")
	@ResponseBody
	public Result seller_order_signature(@RequestParam(name = "orderno", defaultValue = "") String orderno,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = Result.success();
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("请求成功");
		try {
			
			result = checkOrderService.signContentProcess(result, orderno);
			if(result.getSuccess()) {
				result = checkOrderService.signContentForAdd(result, orderno);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("签订合同失败");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			e.printStackTrace();
		}
		return result;
	}
	
	
}
