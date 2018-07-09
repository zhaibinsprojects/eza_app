package com.sanbang.app.controller;

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
import com.sanbang.utils.Page;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;

@Controller
@RequestMapping("/app/paymanager")
public class AppPayManageController {
	
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
			@RequestParam(value="pay_mode",required=false)Integer pay_mode,
			@RequestParam(name = "pageNow", defaultValue = "1") int pageNow,HttpServletRequest request){
		Result result = Result.failure();
		ezs_user upi=RedisUserSession.getUserInfoByKeyForApp(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		if(!Tools.isEmpty(starttime)&&!Tools.isEmpty(endtime)){
			if(Tools.compare_date(starttime, endtime)!=1){
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("请选择正确的时间");
				result.setSuccess(false);
				return result;
			}
		}
		Page page = new Page();
		page.setStartPos(pageNow);
		page.setPageNow(pageNow);
		
		Map<String,Object> map = new HashMap<>();
		map.put("paymentUser_id", upi.getId());
		map.put("receUser_id", upi.getId());
		map.put("deleteStatus", false);
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		map.put("order_type", order_type);
		map.put("pay_mode", pay_mode);
		map.put("startPos", page.getStartPos());
		map.put("pageSize", page.getPageSize());
		
		result = payManageService.selectPayInfoByParam(map,page);
		
		return result;
	}
	
	

}
