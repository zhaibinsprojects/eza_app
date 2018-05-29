package com.sanbang.paymanage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_user;
import com.sanbang.paymanage.service.PayManageService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;

@Controller
@RequestMapping("/paymanager")
public class PayManageController {
	
	@Autowired
	private PayManageService payManageService;
	
	/**
	 * 获取资金管理信息
	 * @param starttime 筛选开始时间
	 * @param endtime 筛选结束时间
	 * @param order_type 筛选资金流向 1.采购单，2.订单
	 * @param pay_mode 筛选支付方式 1.线下 2.银联
	 * @return
	 */
	@RequestMapping("/getPayList")
	@ResponseBody
	public Result getPayManage(@RequestParam(value="starttime",required=false)String starttime,
			@RequestParam(value="endtime",required=false)String endtime,
			@RequestParam(value="order_type",required=false)Integer order_type,
			@RequestParam(value="pay_mode",required=false)Integer pay_mode,HttpServletRequest request){
		Result result = new Result().failure();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		if(starttime!=null&&endtime!=null){
			if(Tools.compare_date(starttime, endtime)!=1){
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("请选择正确的时间");
				result.setSuccess(false);
				return result;
			}
		}
		Map<String,Object> map = new HashMap<>();
		map.put("deleteStatus", false);
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		map.put("order_type", order_type);
		map.put("pay_mode", pay_mode);
		
		result = payManageService.selectPayInfoByParam(map);
		
		return result;
	}
	
	

}
