package com.sanbang.seller.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.advice.service.CommonOrderAdvice;
import com.sanbang.bean.ezs_store;
import com.sanbang.bean.ezs_user;
import com.sanbang.buyer.service.BuyerService;
import com.sanbang.dict.service.DictService;
import com.sanbang.seller.service.SellerReceiptService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCate;
import com.sanbang.vo.DictionaryCode;

@Controller
@RequestMapping("/seller")
public class SellerReceiptController {
	
	//日志
	private Logger log=Logger.getLogger(SellerReceiptController.class);
	
	@Autowired
	SellerReceiptService sellerReceiptService;
	
	@Autowired
	DictService dictService;
	
	@Autowired
	private BuyerService buyerService;
	
	@Autowired
	private CommonOrderAdvice commonOrderAdvice;
	
	/**
	 * 根据订单编号和时间查询发票
	 * @param orderno
	 * @param startTime
	 * @param endTime
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getReceiptList")
	@ResponseBody
	public Object queryInvoiceByIdOrDate(@RequestParam(name="orderno",defaultValue="")String orderno, 
			@RequestParam(name="startTime",defaultValue="")String startTime,
			@RequestParam(name="endTime",defaultValue="")String endTime,
			HttpServletRequest request, 
			HttpServletResponse response,
			@RequestParam(name="currentPage",defaultValue="1")int currentPage){
		Result result=Result.failure();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
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
		
		if(currentPage<=1){
			currentPage = 1;
		}
		result = sellerReceiptService.queryInvoiceByIdOrDate(result, orderno, startTime, endTime, upi.getId(), currentPage,2);
		return result;
  }
	/**
	 * 票据详情页面(发票查看)
	 * @param orderNo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryInvoiceInfoById")
	@ResponseBody
	public Object queryInvoiceInfoById(String orderNo, HttpServletRequest request, HttpServletResponse response){
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
		try {
			result=buyerService.getezs_invoice(request, orderNo,upi);
		} catch (Exception e) {
			log.info("查询发票信息出错" + e.toString());
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
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
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		try {
			result = buyerService.showOrderContent(request, order_no,upi);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("查看合同失败");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
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
			//采购合同 5，销售合同 6
			result = buyerService.getContentList(StringUtils.isEmpty(
					upi.getEzs_store().getSnumber())?
							(new StringBuffer().append("'").append(upi.getEzs_store().getNumber()).append("'").toString())
							:(new StringBuffer().append("'").append(upi.getEzs_store().getNumber()).append("'").append(",").append("'").append(upi.getEzs_store().getSnumber()).append("'").toString()),
							"PBUY", pageno, request);
		} catch (Exception e) {
			result.setMsg("未获取到数据");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	/**
	 * 上传票据
	 * //INVIMG
	 * @param order_no
	 * @param request
	 * @return
	 */
	@RequestMapping("/orderinvosubmit")
	@ResponseBody
	public Result orderpaysubmit(@RequestParam(name = "order_no", defaultValue = "") String order_no,
			@RequestParam(name = "urlparam", defaultValue = "") String urlParam,
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
		try {
			result = sellerReceiptService.orderivosubmit(request, order_no, urlParam,upi);
			//wemall回调
			if(result.getSuccess()){
				commonOrderAdvice.returnOrderAdvice(order_no, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("上传支付凭证失败");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return result;
	}
}

