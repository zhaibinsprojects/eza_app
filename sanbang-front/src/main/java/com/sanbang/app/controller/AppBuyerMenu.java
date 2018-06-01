package com.sanbang.app.controller;

import java.util.HashMap;
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

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_invoice;
import com.sanbang.bean.ezs_order_info;
import com.sanbang.bean.ezs_store;
import com.sanbang.bean.ezs_user;
import com.sanbang.buyer.service.BuyerService;
import com.sanbang.dict.service.DictService;
import com.sanbang.seller.service.SellerReceiptService;
import com.sanbang.utils.Page;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCate;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.PagerOrder;

@Controller
@RequestMapping("/app/buyer")
public class AppBuyerMenu {

	@Autowired
	private BuyerService buyerService;
	
	@Autowired
	private SellerReceiptService sellerReceiptService;
	
	@Autowired
	private DictService dictService;
	
	
	private Logger log=Logger.getLogger(AppBuyerMenu.class);  

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

		ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
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
			@RequestParam(name = "pageNo", defaultValue = "1") int pageNow, HttpServletRequest request) {

		Result result = Result.success();
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("请求成功");
		try {
		ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
		if (upi == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
	
			if (pageNow < 1) {
				pageNow = 1;
			}
			PagerOrder pager = new PagerOrder();
			pager.setUserid(upi.getId());
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
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
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
		try {
		ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
		if (upi == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}

		
			Map<String, Object> map = buyerService.getOrderInfoShow(order_no);
			result.setObj(map);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return result;
	}

	
	
	/**
	 * 关闭订单
	 *  private String msg;
                    取消原因
       private String name;
	 * @param order_no
	 * @param request
	 * @return
	 */
	@RequestMapping("/orderclose")
	@ResponseBody
	public Result orderclose(@RequestParam(name = "order_no", defaultValue = "-1") String order_no,
			HttpServletRequest request) {

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
			result= buyerService.orderclose(request,order_no);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return result;
	}

	
	
	/**
	 * 支付之前确认是否为待支付状态
	 * 
	 * @param order_no
	 * @param request
	 * @return
	 */
	@RequestMapping("/orderconfirmBefore")
	@ResponseBody
	public Result orderconfirmBefore(@RequestParam(name = "order_no", defaultValue = "-1") String order_no,
			HttpServletRequest request) {

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
			result = buyerService.payconfirm(request,order_no);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
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

		ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
		if (upi == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}

		try {
			result = buyerService.payconfirm(request,order_no);
			if(!result.getSuccess()){
				return result;
			}
			
			Map<String, Object> map = buyerService.getOrderInfoShow(order_no);
			result.setObj(map);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return result;
	}
	
	
	/**
	 * 上传支付凭证
	 * //PAYIMG
	 * @param order_no
	 * @param request
	 * @return
	 */
	@RequestMapping("/orderpaysubmit")
	@ResponseBody
	public Result orderpaysubmit(@RequestParam(name = "order_no", defaultValue = "-1") String order_no,
			@RequestParam(name = "urlparam", defaultValue = "-1") String urlParam,
			HttpServletRequest request) {

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
			result = buyerService.orderpaysubmit(request, order_no, urlParam);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("上传支付凭证失败");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
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
		try {
			result = buyerService.showOrderContent(request, order_no);
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
	@RequestMapping("/signordercontent")
	@ResponseBody
	public Result seller_order_signature(@RequestParam(name = "order_no", defaultValue = "") String order_no,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = Result.success();
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("请求成功");
		try {
			result = buyerService.seller_order_signature(order_no, request, response);
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
	 * 获取合同列表
	 * @param order_no
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getContentList" , produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Result getContentList(@RequestParam(name = "pageno", defaultValue = "1") int pageno,
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
			//销售合同 6
			result = buyerService.getContentList(upi.getEzs_store().getNumber(), 6, pageno, request);
		} catch (Exception e) {
			result.setMsg("未获取到数据");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 发票信息
	 * @param order_no
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getezs_invoice")
	public Result getezs_invoice(@RequestParam(name = "order_no", defaultValue = "") String order_no,
			HttpServletRequest request, HttpServletResponse response){
		Result result = Result.success();
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("请求成功");
		try {
			result=buyerService.getezs_invoice(request, order_no);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return result;
	}
	
	
	
	/**
	 * 物流信息查看
	 * @param order_no
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getezs_logistics")
	public Result getezs_logistics(@RequestParam(name = "order_no", defaultValue = "") String order_no,
			HttpServletRequest request, HttpServletResponse response){
		Result result = Result.success();
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("请求成功");
		try {
			result=buyerService.getezs_logistics(request, order_no);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return result;
	}
	
	
	/**
	 * 票据管理页面,展示列表
	 * @param userId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getReceiptList")
	@ResponseBody
	public Object getReceiptList(HttpServletRequest request, HttpServletResponse response, String currentPage){
		Map<String, Object> map = new HashMap<>();
		List<ezs_invoice> list = null;
		Result result=Result.failure();
		ezs_user upi=RedisUserSession.getUserInfoByKeyForApp(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
		Long userId = upi.getId();
		
		Page page = null;
		if(currentPage==null){
			currentPage = "1";
		}
		try {
			map = sellerReceiptService.getInvoiceListById(userId,currentPage);
			Integer ErrorCode = (Integer)map.get("ErrorCode");
			if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
				list = (List<ezs_invoice>) map.get("Obj");
				page = (Page) map.get("Page");
				result = Result.success(); 
				result.setObj(list);
				result.setMeta(page);
			}else{
				result = Result.failure();
				result.setErrorcode(Integer.valueOf(map.get("ErrorCode").toString()));
				result.setMsg(map.get("Msg").toString());
				result.setObj(list);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return result;
	}
	
	
	/**
	 * 发票查看
	 * @param orderNo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryInvoiceInfoById")
	@ResponseBody
	public Object queryInvoiceInfoById(String orderNo, HttpServletRequest request, HttpServletResponse response){
		Result result = Result.failure();
		Map<String, Object> map = new HashMap<>();
		ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		Long userId = upi.getId();
		
		ezs_invoice invoice = null; 
		ezs_accessory accessory = null;		
		try {
			invoice = sellerReceiptService.queryInvoiceByNo(orderNo);
			if (invoice != null ) {
				
				Long receipt_id = invoice.getReceipt_id();
				accessory = sellerReceiptService.queryAccessoryById(receipt_id);
				String path = accessory.getPath();
				map.put("invoice", invoice);
				map.put("path", path);
				result.setSuccess(true);
				result.setMsg("查询成功");
				result.setObj(map);
			}else{
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("查询失败");
			}
		} catch (Exception e) {
			log.info("查询发票信息出错" + e.toString());
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		return result;
	}
	
	
}