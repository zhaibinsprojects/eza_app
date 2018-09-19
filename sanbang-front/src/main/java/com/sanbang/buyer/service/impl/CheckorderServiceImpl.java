package com.sanbang.buyer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_check_order_items;
import com.sanbang.bean.ezs_check_order_main;
import com.sanbang.buyer.service.CheckOrderService;
import com.sanbang.dao.ezs_check_order_mainMapper;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;

/**
 * 对账单操作
 * @author langf
 * @data 2018-9-17
 *
 */
@Service
public class CheckorderServiceImpl implements CheckOrderService {

	private Logger log=Logger.getLogger(CheckorderServiceImpl.class);
	
	@Autowired
	private ezs_check_order_mainMapper  ezs_check_order_mainMapper;
	
	@Override
	public Result getCheckOrderH(HttpServletRequest request, Result result, String orderno) {
		
		Map<String, Object> map=new HashMap<>();
		try {
			ezs_check_order_main checkorder=new ezs_check_order_main();
			if(Tools.isEmpty(orderno)) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("订单号不能为空");
				result.setSuccess(false);
			}else {
				checkorder=ezs_check_order_mainMapper.getCheckOrderForOrderNO(orderno);
				if(null==checkorder){
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setMsg("暂无对账单记录");
					result.setSuccess(false);
				}else {
					result.setSuccess(true);
					result.setMsg("请求成功");
					result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
					map.put("checkorder", checkorder);
				}
				
			}
			result.setObj(map);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return result;
	}

	@Override
	public Result addOrUpdateCheckOrder(HttpServletRequest request, Result result, String orderno,
			List<ezs_check_order_items> items,ezs_check_order_main main) {
		log.info(items.toArray().toString() +main.toString());
		return result;
	}

}
