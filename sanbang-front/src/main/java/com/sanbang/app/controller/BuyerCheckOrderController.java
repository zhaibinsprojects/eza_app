package com.sanbang.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanbang.bean.ezs_user;
import com.sanbang.buyer.service.CheckOrderService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;

@Controller
@RequestMapping("/app/checkOrder")
public class BuyerCheckOrderController {

	
	@Autowired
	private CheckOrderService checkOrderService;
	
	/**
	 * 查看对账单
	 * @param request
	 * @param orderno
	 * @return
	 */
	@RequestMapping("/checkOrderH")
	public Result  getCheckOrderH(HttpServletRequest request,String orderno) {
		Result result=Result.failure();
		
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if (upi == null) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			result=checkOrderService.getCheckOrderH(request, result, orderno);
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
	 * @param request
	 * @param orderno
	 * @return
	 */
	@RequestMapping("/addOrUpdateCheckOrder")
	public Result  addOrUpdateCheckOrder(HttpServletRequest request,String orderno) {
		Result result=Result.failure();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if (upi == null) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			result=checkOrderService.getCheckOrderH(request, result, orderno);
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
	 *合同预览
	 * @param request
	 * @param orderno
	 * @return
	 */
	@RequestMapping("/contentPreview")
	public Result  signContentPreview(HttpServletRequest request,String orderno) {
		Result result=Result.failure();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if (upi == null) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			//TODO
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return result;
	}
	
}
