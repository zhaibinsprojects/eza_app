package com.sanbang.salesReturn.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.advice.service.CommonOrderAdvice;
import com.sanbang.app.controller.AppUserSetupCompanyInfoController;
import com.sanbang.bean.ezs_order_info;
import com.sanbang.bean.ezs_orderform;
import com.sanbang.bean.ezs_set_return_order;
import com.sanbang.dao.ezs_orderformMapper;
import com.sanbang.dao.ezs_set_return_orderMapper;
import com.sanbang.salesReturn.service.SalesReturnService;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.returnorder.ReturnOrderVO;

@Service("salesReturnService")
public class SalesReturnServiceImpl implements SalesReturnService {

	@Autowired
	private ezs_orderformMapper ezs_orderformMapper;
	
	@Autowired
	private ezs_set_return_orderMapper ezs_set_return_orderMapper;
	
	@Autowired
	private CommonOrderAdvice commonOrderAdvice;
	
	private Logger log=Logger.getLogger(AppUserSetupCompanyInfoController.class);
	@Override
	public ezs_order_info getOrderListByOrderno(String order_no) {
		
		return ezs_orderformMapper.getOrderListByOrderno(order_no);
	}

	@Override
	public Result insertSetReturnOrder(HttpServletRequest request,ezs_set_return_order returnOrder) {
		Result result = Result.success();
		log.info("请求退货"+returnOrder.toString());
		try {
			if(Tools.isEmpty(returnOrder.getOrder_no())){
				result.setSuccess(false);
				result.setMsg("订单号不能为空");
				result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
				return  result;
			}
			
			ezs_order_info orde = ezs_orderformMapper.getOrderListByOrderno(returnOrder.getOrder_no());
			if(null==orde){
				result.setSuccess(false);
				result.setMsg("订单不存在");
				result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
				return result;
			}
			
			ReturnOrderVO returnvo =ezs_set_return_orderMapper.returnOrderinfoByOrderno(returnOrder.getOrder_no());
			
			if(null!=returnvo){
				result.setSuccess(false);
				result.setMsg("您已申请退货");
				result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
				return result;
			}
			// set_return_no; 退货编号
			//remark:补充说明  
			//returnReason: 退货原因  
			//returnTotal;申请的退货金额  
			//num: 数量  
			//returnType:退货类型 0全部退货 1部分退货
			returnOrder.setOrderForm_id(orde.getOrderid());
			returnOrder.setOrderType(0);
			returnOrder.setStatus(0);
			returnOrder.setAddTime(new Date());
			returnOrder.setGood_id(orde.getGoodsid());
			returnOrder.setNowStatus(0);
			returnOrder.setDeleteStatus(false);
			  //撮合订单
			 if("ORDER_MATCH_GOOD".equals(orde.getOrder_type())){
				 returnOrder.setUserseller_id(orde.getSellerid());
		        }
			ezs_set_return_orderMapper.insertSelective(returnOrder);
			
			ezs_orderform  order=new ezs_orderform();
			order.setOrder_no(orde.getOrder_no());
			order.setOrder_status(100);
			order.setId(orde.getOrderid());
			int status=ezs_orderformMapper.updateByPrimaryKeySelective(order);
			result.setSuccess(true);
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setMsg("保存成功");
			//wemall回调
			if(result.getSuccess()){
				commonOrderAdvice.returnOrderAdvice(orde.getOrder_no(), "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		
		
		return result;
	}

}
