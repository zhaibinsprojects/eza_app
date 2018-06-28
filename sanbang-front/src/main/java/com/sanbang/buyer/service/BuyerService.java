package com.sanbang.buyer.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sanbang.bean.ezs_order_info;
import com.sanbang.bean.ezs_user;
import com.sanbang.utils.Result;
import com.sanbang.vo.PagerOrder;

public interface BuyerService {

	/**
	 * 
	 * @param meta
	 * @return
	 */
	List<ezs_order_info> getOrderListByValue(PagerOrder pager);
	
	/**
	 * 订单详情
	 * @param orderid
	 * @return
	 */
	Map<String, Object> getOrderInfoShow(String order_no);
	
	/**
	 * 支付确认
	 * @param orderid
	 * @return
	 */
	Map<String, Object>orderconfirm(String order_no);
	
	
	/**
	 * 合同查看
	 * @param request
	 * @param order_no
	 * @return
	 */
	Result showOrderContent(HttpServletRequest request,String order_no);
	
	/**
	 * 签订合同
	 * @param order_no
	 * @param request
	 * @param response
	 * @return
	 */
	 public Result seller_order_signature(String order_no,HttpServletRequest request,
	            HttpServletResponse response,ezs_user upi);
	
	 
	 
	 /**
	  * 合同查看
	  * @param member
	  * @param temid
	  * @param pageno
	  * @return
	  */
	 public Result  getContentList(String member,int temid,int pageno,HttpServletRequest request);
	 
	 /**
	  * 关闭订单
	  * @param request
	  * @param order_no
	  * @return
	  */
	 public Result   orderclose(HttpServletRequest request,String order_no,ezs_user upi);
	 
	 /**
	  * 上传订单支付凭证
	  * @param request
	  * @param order_no
	  * @return
	  */
	 public Result  orderpaysubmit(HttpServletRequest request,String order_no,String urlParam,ezs_user upi);
	 
	 
	 /**
	  * 发票信息查看
	  * @param request
	  * @param order_no
	  * @return
	  */
	 public  Result getezs_invoice(HttpServletRequest request,String order_no);
	 
	 
	 
	 /**
	  * 物流信息查看
	  * @param request
	  * @param order_no
	  * @return
	  */
	 public  Result getezs_logistics(HttpServletRequest request,String order_no);
	 
	 
	 /**
	  * 判断是否可支付
	  * @param request
	  * @param order_no
	  * @return
	  */
	 public Result payconfirm(HttpServletRequest request,String order_no);
	 
	 
}
