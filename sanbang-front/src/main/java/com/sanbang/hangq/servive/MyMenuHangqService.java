package com.sanbang.hangq.servive;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

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
	
	
	/**
	 * 我的订阅详情
	 * @param upi
	 * @param request
	 * @param result
	 * @return
	 */
	public  Result myDingZhi(ezs_user upi,HttpServletRequest request,Result result);
	
	/**
	 * 我的定制是否推送
	 * @param upi
	 * @param request
	 * @param result
	 * @return
	 */
	public  Result myDingZhiIsPush(ezs_user upi,HttpServletRequest request,Result result,boolean isPush,long id);
	
	
	/**
	 * 我的定制提交
	 * @param upi
	 * @param request
	 * @param result
	 * @return
	 */
	public  Result myDingZhiSubmit(ezs_user upi,HttpServletRequest request,Result result, String areaids,String category,String pushMethod);
	
	/**
	 * 定制推送 app req
	 * @param dzid
	 * @return
	 */
	public Result HangqPushData(long dzid,Result result);
	
	/**
	 * 查看试用状态
	 * @param upi
	 * @param request
	 * @param result
	 * @return
	 */
	public Result getDingYueStatusByUserid(ezs_user upi,HttpServletRequest request,Result result);
	
	/**
	 * 订阅支付凭证
	 * @param request
	 * @param upi
	 * @param id
	 * @param urlParam
	 * @param result
	 * @return
	 */
	public  Result getDingyuePayPic(HttpServletRequest request,ezs_user upi,long id,Result result);
	
	
	/**
	 * 试用开通
	 * @param request
	 * @param upi
	 * @param result
	 * @return
	 */
	public Result myDingYueTryAdd(HttpServletRequest request, ezs_user upi, Result result);
	
	/**
	 * 提交订单
	 * @param request
	 * @param upi
	 * @param id
	 * @param result
	 * @return
	 */
	public Result  submitOrder(HttpServletRequest request,ezs_user upi,long id,int paymode,Result result);
	
	/**
	 * 行情  查看
	 * @param id
	 * @param model
	 */
	void hangqgShow(long id,Model model);
	
	/**
	 * 查看文章状态  和权限
	 * @param request
	 * @param upi
	 * @param id
	 * @param result
	 * @return
	 */
	public Result  getDocStatusForUser(HttpServletRequest request,ezs_user upi,long id,Result result);
	
	/**
	 * 查看文章状态  和权限
	 * @param request
	 * @param upi
	 * @param id
	 * @param result
	 * @return
	 */
	public Result  doGiveOrHouse(HttpServletRequest request,ezs_user upi,long id,Result result,String reqtype);
	
}
