package com.sanbang.app.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.advice.service.CommonOrderAdvice;
import com.sanbang.bean.ezs_order_info;
import com.sanbang.bean.ezs_set_return_order;
import com.sanbang.bean.ezs_user;
import com.sanbang.salesReturn.controller.SalesReturnController;
import com.sanbang.salesReturn.service.SalesReturnService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;

@Controller
@RequestMapping("/app/salesReturn")
public class AppSalesReturnController {
	
	private Logger log = Logger.getLogger(SalesReturnController.class);

	@Autowired
	private SalesReturnService salesReturnService;
	
	@Autowired
	private CommonOrderAdvice commonOrderAdvice;
	
	
	@RequestMapping(value="/getOrderformById")
	@ResponseBody
	public Result getOrderformById(HttpServletRequest request,@RequestParam(required=true,value="order_no")String order_no){
		Result result = Result.success();
		Map<String,Object> map = new HashMap<String,Object>();
		
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			ezs_order_info orderinfo = salesReturnService.getOrderListByOrderno(order_no,upi);
			String returnNo = "";
			//手动生成退货编号
			if(null != orderinfo){
				returnNo = Tools.getReturnNo("TH");
				
			}
			
			//从订单表中封装前台所需的信息到map中返回给前台
			map.put("order_no", orderinfo.getOrder_no()); //订单编号
			map.put("name", orderinfo.getName()); //商品名称
			map.put("price", orderinfo.getPrice()); //单价
			map.put("count", orderinfo.getCount()); //数量
			map.put("total_price", orderinfo.getTotal_price()); //总价
			map.put("returnNo", returnNo); //手动生成的退货订单
			
			result.setObj(map);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("对象查询失败");
		}
		
		return result;
		
	}
	
	
	@RequestMapping(value="/insertEzsSetReturnOrder")
	@ResponseBody
	public Result insertEzsSetReturnOrder(HttpServletRequest request,ezs_set_return_order returnOrder){
		Result result = Result.success();
		ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		//未对象中一些前台未传入的字段赋值
		if(null == returnOrder){
			
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("传入对象为空");
			
		}else if(null == returnOrder.getAddTime()){
			
			returnOrder.setAddTime(new Date());    //设置退货单添加时间
			returnOrder.setDeleteStatus(true);     //是否启用--- 启用
			
		}
		result = salesReturnService.insertSetReturnOrder(request,returnOrder,upi);
		//wemall回调
		if(result.getSuccess()){
			commonOrderAdvice.returnOrderAdvice(returnOrder.getOrder_no(), "");
		}
		return result;
		
	}
	
	
	
}
