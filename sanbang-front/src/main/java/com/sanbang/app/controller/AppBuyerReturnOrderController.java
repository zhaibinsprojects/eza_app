package com.sanbang.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_user;
import com.sanbang.buyer.service.BuyerReturnOrderService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;

/**
 * 买家中心退货
 * 
 * @author LENOVO
 *
 */
@Controller
@RequestMapping("/app/buyer/returnOrder")
public class AppBuyerReturnOrderController {

	@Autowired
	private BuyerReturnOrderService buyerReturnOrderService;

	/**
	 * 申请退货订单列表
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("orderlist")
	public Result returnOrderList(HttpServletRequest request,
			@RequestParam(name = "pageNo", defaultValue = "1") int pageNo) {
		Result result = Result.failure();
		ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
		if (upi == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		result = buyerReturnOrderService.returnOrderList(pageNo, upi.getId());
		return result;
	}

	/**
	 * 申请退货订单详情
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("returnOrderinfo")
	public Result returnOrderinfoforBuyer(HttpServletRequest request,
			@RequestParam(name = "returnid", defaultValue = "1") int returnid) {
		Result result = Result.failure();
		ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
		if (upi == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		result = buyerReturnOrderService.returnOrderinfoforBuyer(returnid);
		return result;
	}

	/**
	 * 物流信息查看
	 * 
	 * @param order_no
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getreturnlogistics")
	public Result getreturnlogistics(@RequestParam(name = "returnid", defaultValue = "") long returnid,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = Result.success();
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("请求成功");
		ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
		if (upi == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		try {
			result = buyerReturnOrderService.getreturnlogistics(returnid);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return result;
	}

}
