package com.sanbang.salesReturn.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_order_info;
import com.sanbang.bean.ezs_set_return_order;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_orderformMapper;
import com.sanbang.dao.ezs_set_return_orderMapper;
import com.sanbang.salesReturn.service.SalesReturnService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;

@Service("salesReturnService")
public class SalesReturnServiceImpl implements SalesReturnService {

	@Autowired
	private ezs_orderformMapper ezs_orderformMapper;
	
	@Autowired
	private ezs_set_return_orderMapper ezs_set_return_orderMapper;
	
	@Override
	public ezs_order_info getOrderListByOrderno(String order_no) {
		
		return ezs_orderformMapper.getOrderListByOrderno(order_no);
	}

	@Override
	public Result insertSetReturnOrder(HttpServletRequest request,ezs_set_return_order returnOrder) {
		Result result = Result.success();
		
		try {
			//校验用户是否登录
			ezs_user upi = RedisUserSession.getLoginUserInfo(request);
			if (upi == null) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				result.setSuccess(false);
				return result;
			}
			
			ezs_set_return_orderMapper.insertSelective(returnOrder);
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		
		
		return result;
	}

}
