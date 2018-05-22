package com.sanbang.buyer.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_order_info;
import com.sanbang.bean.ezs_user;
import com.sanbang.buyer.service.BuyerService;
import com.sanbang.utils.Page;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.PagerOrder;

@Controller
@RequestMapping("/buyer")
public class BuyerMenu {

	@Autowired
	private BuyerService buyerService;


	/**
	 * 买家会员中心
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/menuinit")
	@ResponseBody
	public Result cataInit(HttpServletRequest request) {
		Result result = Result.success();
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("请求成功");

		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if (upi == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		return result;
	}

	/**
	 * 订单中心
	 * 
	 * @param request
	 * @return
	 * 
	 * 		* @param pageNow 当前页数
	 * @param pageSize
	 *            每页显示记录的条数
	 * @param totalCount
	 *            总的记录条数
	 * @param totalPageCount
	 *            总的页数
	 * @param startPos
	 *            开始位置，从0开始
	 * @param hasFirst
	 *            是否有首页
	 * @param hasPre
	 *            是否有前一页
	 * @param hasNext
	 *            是否有下一页
	 * @param hasLast
	 *            是否有最后一页
	 */
	@RequestMapping("/orderinit")
	@ResponseBody
	public Result orderinit(@RequestParam(name = "order_status", defaultValue = "-1") int order_status,
			@RequestParam(name = "order_type", defaultValue = "") String order_type,
			@RequestParam(name = "pageNow", defaultValue = "1") int pageNow, HttpServletRequest request) {

		Result result = Result.success();
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("请求成功");
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if (upi == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}

		if (pageNow < 1) {
			pageNow = 1;
		}
		PagerOrder pager = new PagerOrder();
		pager.setOrder_status(order_status);
		pager.setOrder_type(order_type);
		pager.setPageNow(pageNow);
		List<ezs_order_info> list = buyerService.getOrderListByValue(pager);
		result.setMeta(new Page(pageNow, pager.getPageSize(), pager.getTotalCount(), pager.getTotalPageCount(), 0, true,
				true, true, true));
		if (list.size() == 0) {
			result.getMeta().setHasFirst(false);
		}
		if (pageNow == 1) {
			result.getMeta().setHasPre(false);
		}

		result.setObj(list);
		return result;
	}

	/**
	 * 订单详情
	 * 
	 * @param order_no
	 * @param request
	 * @return
	 */
	@RequestMapping("/orderinfoshow")
	@ResponseBody
	public Result getOrderInfoShow(@RequestParam(name = "order_no", defaultValue = "-1") String order_no,
			HttpServletRequest request) {

		Result result = Result.success();
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("请求成功");

		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if (upi == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}

		Map<String, Object> map = buyerService.getOrderInfoShow(order_no);
		result.setObj(map);
		return result;
	}

	/**
	 * 支付确认
	 * 
	 * @param order_no
	 * @param request
	 * @return
	 */
	@RequestMapping("/orderconfirm")
	@ResponseBody
	public Result orderconfirm(@RequestParam(name = "order_no", defaultValue = "-1") String order_no,
			HttpServletRequest request) {

		Result result = Result.success();
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("请求成功");

		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if (upi == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}

		Map<String, Object> map = buyerService.getOrderInfoShow(order_no);
		result.setObj(map);
		return result;
	}
	
	
	/**
	 * 查看合同
	 * 
	 * @param order_no
	 * @param request
	 * @return
	 */
	@RequestMapping("/showordercontent")
	@ResponseBody
	public Result showOrderContent(@RequestParam(name = "order_no", defaultValue = "") String order_no,
			HttpServletRequest request) {

		Result result = Result.success();
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("请求成功");
		result = buyerService.showOrderContent(request, order_no);
		return result;
	}

	/**
	 * 签订合同
	 * 
	 * @param order_no
	 * @param request
	 * @return
	 */
	@RequestMapping("/signordercontent")
	@ResponseBody
	public Result seller_order_signature(@RequestParam(name = "order_no", defaultValue = "") String order_no,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = Result.success();
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("请求成功");
		result = buyerService.seller_order_signature(order_no, request, response);
		return result;
	}
	
	
	/**
	 * 获取合同列表
	 * @param order_no
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getContentList")
	@ResponseBody
	public Result getContentList(@RequestParam(name = "pageno", defaultValue = "1") int pageno,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = Result.success();
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("请求成功");
		
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if (upi == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		
		try {
			result = buyerService.getContentList(upi.getEzs_store().getNumber(), 5, pageno, request);
		} catch (Exception e) {
			result.setMsg("未获取到数据");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			e.printStackTrace();
		}
		return result;
	}
	
}
