package com.sanbang.seller.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_logistics;
import com.sanbang.bean.ezs_order_info;
import com.sanbang.bean.ezs_store;
import com.sanbang.bean.ezs_user;
import com.sanbang.buyer.service.BuyerService;
import com.sanbang.dict.service.DictService;
import com.sanbang.seller.service.SellerOrderService;
import com.sanbang.utils.Page;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCate;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.PagerOrder;

@Controller
@RequestMapping("/seller")
public class SellerOrderContorller {
	
	//日志
	private static Logger log = Logger.getLogger(SellerOrderContorller.class.getName());
	
	@Autowired
	SellerOrderService sellerOrderService;
	
	@Autowired
	DictService dictService;

	@Autowired
	private BuyerService buyerService;
	/**
	 * 分页查询订单列表
	 * @param orderType
	 * @param orderStatus
	 * @param currentPage
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryOrderList")
	@ResponseBody
	public Object queryOrderList(@RequestParam(name = "order_status", defaultValue = "-1") int order_status,
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
		//验证用户是否激活，拥有卖家权限
		ezs_store store = upi.getEzs_store();
		Integer storeStatus = store.getStatus();
		Long auditingusertype_id = store.getAuditingusertype_id();
		String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
		if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setMsg("用户未激活，没有卖家权限。");
			return result;
		}
		
		if (pageNow < 1) {
			pageNow = 1;
		}
		PagerOrder pager = new PagerOrder();
		pager.setOrder_status(order_status);
		pager.setOrder_type(order_type);
		pager.setPageNow(pageNow);
		List<ezs_order_info> list = sellerOrderService.getOrderListByValue(pager);
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
	 * 查看订单详情
	 * @param order_no
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryOrderInfoById")
	@ResponseBody
	public Object queryOrderInfoById(@RequestParam(name = "order_no", defaultValue = "-1") String order_no,
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
		
		//验证用户是否激活，拥有卖家权限
		ezs_store store = upi.getEzs_store();
		Integer storeStatus = store.getStatus();
		Long auditingusertype_id = store.getAuditingusertype_id();
		String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
		if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setMsg("用户未激活，没有卖家权限。");
			return result;
		}
		
			
		Map<String, Object> map = sellerOrderService.queryOrderInfoById(order_no);
		result.setObj(map);
		return result;
	}
	

	/**
	 *	物流信息查看 
	 * @param orderNo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryLogisticsInfoById")
	@ResponseBody
	public Object queryLogisticsInfoById(String orderNo, HttpServletRequest request, HttpServletResponse response){
		Result result = Result.failure();
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		
		//验证用户是否激活，拥有卖家权限
		ezs_store store = upi.getEzs_store();
		Integer storeStatus = store.getStatus();
		Long auditingusertype_id = store.getAuditingusertype_id();
		String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
		if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setMsg("用户未激活，没有卖家权限。");
			return result;
		}
		
		ezs_logistics logistics = null; 
		try {
			logistics = sellerOrderService.queryLogisticsByNo(orderNo);
			if (logistics != null ) {
				result.setSuccess(true);
				result.setMsg("查询成功");
				result.setObj(logistics);
			}else{
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("查询失败");
			}
		} catch (Exception e) {
			log.info("查询物流信息出错" + e.toString());
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
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
	@RequestMapping("/signordercontent")
	@ResponseBody
	public Result seller_order_signature(@RequestParam(name = "order_no", defaultValue = "") String order_no,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = Result.success();
		
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		
		//验证用户是否激活，拥有卖家权限
		ezs_store store = upi.getEzs_store();
		Integer storeStatus = store.getStatus();
		Long auditingusertype_id = store.getAuditingusertype_id();
		String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
		if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setMsg("用户未激活，没有卖家权限。");
			return result;
		}	
		
		try {
			result = buyerService.seller_order_signature(order_no, request, response);
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setMsg("请求成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("签订合同失败");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 样品订单发货
	 * @param order_no
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/sampleDelivery")
	@ResponseBody
	public Object sampleDelivery(@RequestParam(name = "order_no", defaultValue = "") String order_no,
			HttpServletRequest request, HttpServletResponse response){
		Result result = Result.failure();
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		
		//验证用户是否激活，拥有卖家权限
		ezs_store store = upi.getEzs_store();
		Integer storeStatus = store.getStatus();
		Long auditingusertype_id = store.getAuditingusertype_id();
		String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
		if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setMsg("用户未激活，没有卖家权限。");
			return result;
		}	
		
		result = sellerOrderService.sampleDelivery(result, order_no, request, response);
		
		return result;
	}
	
	/**
	 * 货品订单发货
	 */
	@RequestMapping("/goodsDelivery")
	@ResponseBody
	public Object goodsDelivery(@RequestParam(name = "order_no", defaultValue = "") String order_no,
			HttpServletRequest request, HttpServletResponse response){
		Result result = Result.failure();
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		
		//验证用户是否激活，拥有卖家权限
		ezs_store store = upi.getEzs_store();
		Integer storeStatus = store.getStatus();
		Long auditingusertype_id = store.getAuditingusertype_id();
		String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
		if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setMsg("用户未激活，没有卖家权限。");
			return result;
		}	
		
		result = sellerOrderService.goodsDelivery(result, order_no, request, response);
		
		return result;
	}
	
}
