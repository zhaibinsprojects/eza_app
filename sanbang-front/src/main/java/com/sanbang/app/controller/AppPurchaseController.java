package com.sanbang.app.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_customized;
import com.sanbang.bean.ezs_user;
import com.sanbang.buyer.service.PurchaseService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;
/**
 * 采购需求管理
 * @author LENOVO
 *
 */
@Controller
@RequestMapping("/app/buy")
public class AppPurchaseController {
	
	@Autowired
	private PurchaseService purchaseService;  
	/**
	 * 根据已登录用户Id查询定制信息
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getPurchaseByUser")
	@ResponseBody
	public Object getPurchaseByUser(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> mmp = null;
		List<ezs_customized> elist = null;
		Result rs = null;
		ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
		if (upi == null) {
			rs = Result.failure();
			rs.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			rs.setMsg("用户未登录");
			return rs;
		}
		mmp = this.purchaseService.getPurchaseGoodsByUser(request,upi.getId());
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			elist = (List<ezs_customized>) mmp.get("Obj");
			rs = Result.success();
			rs.setObj(elist);
		}else{
			rs = Result.failure();
			rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
			rs.setMsg(mmp.get("Msg").toString());
		}
		return rs;
	}
	/**
	 * 单条查询定制信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getPurchaseById")
	@ResponseBody
	public Object getPurchaseById(HttpServletRequest request,HttpServletResponse response,Long id){
		Map<String, Object> mmp = null;
		ezs_customized customized = null;
		Result rs = null;
		mmp = this.purchaseService.getPurchaseById(id);
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			customized = (ezs_customized) mmp.get("Obj");
			rs = Result.success();
			rs.setObj(customized);
		}else{
			rs = Result.failure();
			rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
			rs.setMsg(mmp.get("Msg").toString());
		}
		return rs;
	}
}
