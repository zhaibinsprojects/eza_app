package com.sanbang.buyer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_return_logistics;
import com.sanbang.buyer.service.BuyerReturnOrderService;
import com.sanbang.dao.ezs_return_logisticsMapper;
import com.sanbang.utils.Page;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.returnorder.ReturnOrderVO;

@Service("buyerReturnOrderService")
public class BuyerReturnOrderServiceImpl implements BuyerReturnOrderService{

	@Autowired
	private  com.sanbang.dao.ezs_set_return_orderMapper ezs_set_return_orderMapper;
	@Autowired
	private ezs_return_logisticsMapper   ezs_return_logisticsMapper;
	
	@Override
	public Result returnOrderList(int pageNo,long userid) {
		Result result=Result.failure();
		try {
			if(pageNo<=0){
				pageNo=1; 
			}
			int crtnum=ezs_set_return_orderMapper.returnOrderCountforBuyer(userid, (pageNo-1)*10);
			List<ReturnOrderVO> list=ezs_set_return_orderMapper.returnOrderListforBuyer(userid, (pageNo-1)*10);
			result.setMeta(new Page(crtnum, 10));
			result.setObj(list);
			result.setSuccess(true);
			result.setMsg("请求成功");
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return result;
	}

	@Override
	public Result returnOrderinfoforBuyer(long returnid) {
		Result result=Result.failure();
		try {
			ReturnOrderVO returninfo=ezs_set_return_orderMapper.returnOrderinfoforBuyer(returnid);
			result.setObj(returninfo);
			result.setSuccess(true);
			result.setMsg("请求成功");
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return result;
	}

	@Override
	public Result getreturnlogistics(long returnid) {
		Result result=Result.failure();
		try {
			ezs_return_logistics returnlogistics=ezs_return_logisticsMapper.selectReturnLogisticsForReturnNo(returnid);
			if(null!=returnlogistics){
				result.setSuccess(true);
				result.setMsg("请求成功");
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				result.setObj(returnlogistics);
			}else{
				result.setObj(new ezs_return_logistics());
				result.setSuccess(false);
				result.setMsg("暂无数据");
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
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
