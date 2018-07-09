package com.sanbang.buyer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_invoice;
import com.sanbang.buyer.service.GoodsInvoiceService;
import com.sanbang.dao.ezs_invoiceMapper;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.InvoiceInfo;

@Service
public class GoodsInvoiceServiceImpl implements GoodsInvoiceService {
	@Autowired
	private ezs_invoiceMapper invoiceMapper; 


	@Override
	public Map<String, Object> changeInvoiceStateById(Long id) {
		Map<String, Object> mmp = new HashMap<>();
		ezs_invoice invoice = null;
		if(id<=(long)0){
			mmp.put("Msg", "参数传递异常,传入为"+id);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
		}
		try {
			invoice = this.invoiceMapper.selectByPrimaryKey(id);
			invoice.setInvoice_status(3);
			this.invoiceMapper.updateByPrimaryKey(invoice);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递异常");
		}
		return mmp;
	}

}
