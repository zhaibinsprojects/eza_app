package com.sanbang.hangq.servive;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import com.sanbang.bean.ezs_user;
import com.sanbang.utils.Result;

public interface MyMenuHangqService {

	/**
	 * 我的订阅列表
	 * @param upi
	 * @param request
	 * @param result
	 * @return
	 */
	public  Result myDingyueListShow(ezs_user upi,HttpServletRequest request,Result result,int pageNo);
	
	/**
	 * 我的收藏
	 * @param upi
	 * @param request
	 * @param result
	 * @return
	 */
	public  Result myShoucListShow(ezs_user upi,HttpServletRequest request,Result result,int pageNo);
	
	/**
	 * 我的定制
	 * @param upi
	 * @param request
	 * @param result
	 * @return
	 */
	public  Result myDingzhiListShow(ezs_user upi,HttpServletRequest request,Result result,boolean ispush,int pageNo);
	
	
	/**
	 * 我的订阅详情
	 * @param upi
	 * @param request
	 * @param result
	 * @return
	 */
	public  Result myDingyueInfoShow(ezs_user upi,HttpServletRequest request,Result result,long id);
	
	
	/**
	 * 上传汇款凭证
	 * @param request
	 * @param upi
	 * @param id
	 * @param result
	 * @return
	 */
	public Result  saveDingyuePic(HttpServletRequest request,ezs_user upi,long id,String urlParam,Result result);
	
	
	/**
	 * 上传汇款凭证
	 * @param request
	 * @param upi
	 * @param id
	 * @param result
	 * @return
	 */
	public Result  myDingYueAdd(HttpServletRequest request,ezs_user upi,String cycle,BigDecimal payment,String subtotal,int isall,Result result);
	
	
	
}