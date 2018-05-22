package com.sanbang.seller.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_invoice;
import com.sanbang.bean.ezs_logistics;
import com.sanbang.bean.ezs_purchase_orderform;
import com.sanbang.dao.ezs_invoiceMapper;
import com.sanbang.dao.ezs_logisticsMapper;
import com.sanbang.dao.ezs_purchase_orderformMapper;
import com.sanbang.seller.service.SellerOrderService;
import com.sanbang.utils.Page;
import com.sanbang.vo.DictionaryCode;
@Service
public class SellerOrderServiceImpl implements SellerOrderService {

	@Autowired
	ezs_purchase_orderformMapper purchaseOrderformMapper;
	
	@Autowired
	ezs_invoiceMapper invoiceMapper;
	@Autowired
	ezs_logisticsMapper logisticsMapper;
	
	@Override
	public Map<String, Object> queryOrders(String currentPage, Long userId, String orderType, Integer orderStatus) {
		
		Map<String, Object> mmp = new HashMap<>();
		//获取总页数
		int totalCount = purchaseOrderformMapper.selectCount(userId);
		Page page = new Page(totalCount, Integer.valueOf(currentPage));
		int startPos = 0;
		page.setStartPos(startPos);
		page.setPageSize(10);
		if(Integer.valueOf(currentPage)>=1||Integer.valueOf(currentPage)<=page.getTotalPageCount()){
			List<ezs_purchase_orderform> list = this.purchaseOrderformMapper.queryOrders(page, userId,orderType, orderStatus);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Page", page);
			mmp.put("Obj", list);
		}else{
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Page", page);
		}
		return mmp;
	}

	@Override
	public ezs_purchase_orderform queryOrderInfoById(long orderId) {
		return purchaseOrderformMapper.selectByPrimaryKey(orderId);
	}

	@Override
	public ezs_invoice queryInvoiceByNo(String orderNo) {
		
		return invoiceMapper.selectInvoiceByOrderNo(orderNo);
	}

	@Override
	public ezs_logistics queryLogisticsByNo(String orderNo) {
		return logisticsMapper.selectByOrderNo(orderNo);
	}
}
