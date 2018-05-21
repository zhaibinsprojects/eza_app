package com.sanbang.index.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_user;
import com.sanbang.index.service.CustomerService;
import com.sanbang.redis.RedisResult;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.RedisUtils;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.HomeDictionaryCode;
import com.sanbang.vo.UserInfoMess;

@Controller
@RequestMapping("/home")
public class HomeCustomerController {
	@Autowired
	private CustomerService customerService;
	/**
	 * 获取用户信息（并判断是否已登录）（定制采购接口调用前，先调用此接口）
	 * @param request
	 * @param response
	 * @return （获取）
	 */
	@RequestMapping("/getUserMess")
	@ResponseBody
	public Object getUserMess(HttpServletRequest request,HttpServletResponse response){
		//获取缓存中已登录用户信息
		ezs_user upi =RedisUserSession.getLoginUserInfo(request);
		Result rs = null;
		if(upi!=null){
			//用户已登录
			rs = Result.success();
			//返回用户信息
			rs.setObj(upi);
			rs.setMsg("登录成功！");
			//根据用户查询用户电话以及地址信息
		}else{
			rs = Result.failure();
			rs.setMsg("未登录用户");
			rs.setErrorcode(HomeDictionaryCode.ERROR_HOME_UN_LOGIN);
			//用户未登录
		}
		return rs;
	}
	/**
	 * 根据已登录用户信息获取用户相关信息
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	@RequestMapping("/getUserAddressInfo")
	@ResponseBody
	public Object getUserMess(HttpServletRequest request,HttpServletResponse response,ezs_user user){
		Map<String, Object> mmp = null;
		Result rs = null;
		mmp = this.customerService.getUserMessByUser(user);
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			UserInfoMess uim = (UserInfoMess) mmp.get("Obj");
			rs = Result.success();
			rs.setObj(uim);
		}else{
			rs = Result.failure();
			rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
			rs.setMsg(mmp.get("Msg").toString());
		}
		ezs_user user01 = new ezs_user();
		user01.setId(Long.valueOf(13));
		mmp = this.customerService.getUserMessByUser(user01);
		UserInfoMess uim = (UserInfoMess) mmp.get("Obj");
		Result rs = Result.success();
		rs.setObj(uim);
		return rs;
	}
	@RequestMapping("/getRedisMess")
	public Object getRedisMess(){
		ezs_user user = new ezs_user();
		user.setName("123123");
		RedisUtils.set("user", user,Long.valueOf(2000));
		RedisResult<ezs_user> tempCached=(RedisResult<ezs_user>) RedisUtils.get("user", ezs_user.class);
		System.out.println("UserName:"+tempCached.getResult().getName());
		return "";
	}
	
}
