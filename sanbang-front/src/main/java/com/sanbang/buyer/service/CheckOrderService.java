package com.sanbang.buyer.service;

import javax.servlet.http.HttpServletRequest;

import com.sanbang.bean.ezs_user;
import com.sanbang.utils.Result;

public interface CheckOrderService {

	/**
	 * 对账单查看
	 * @param request
	 * @param result
	 * @param orderNo
	 * @return
	 */
	public Result getCheckOrderH(HttpServletRequest request, Result result,ezs_user upi, String orderNo) throws Exception;
	
	/***
	 * 
	 * @param request
	 * @param result
	 * @param orderno
	 * @param items
	 * @return
	 */
	public Result addOrUpdateCheckOrder(HttpServletRequest request,ezs_user upi,Result result) throws Exception;
	
	
	/***
	 * 
	 * @param request
	 * @param result
	 * @param orderno
	 * @param items
	 * @return
	 */
	public Result getCheckOrderInit(HttpServletRequest request,ezs_user upi,Result result) throws Exception;
	
	
	
	 /**
	  * 上传订单支付凭证
	  * @param request
	  * @param order_no
	  * @return
	  */
	 public Result  orderpaysubmitForNow(HttpServletRequest request,String order_no,String urlParam,ezs_user upi)  throws Exception;
	
	/**
	 * 生成合同  快速双向签订合同
	 * @param result
	 * @param orderno
	 * @return
	 */
	 public Result signContentProcess(Result result,String orderno);
	 
	 /**
	 * 签订合同  快速双向签订合同
	 * @param result
	 * @param orderno
	 * @return
	 */
	 public Result signContentForAdd(Result result,String orderno);
	
}
