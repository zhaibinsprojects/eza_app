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
	public Map<String, Object> getInvoiceByUser(Long userId) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		List<InvoiceInfo> iList = null;
		try {
			iList = this.invoiceMapper.selectInvoiceByUser(userId);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Obj", iList);
			mmp.put("Msg", "查询成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递异常");
		}
		return mmp;
	}

	@Override
	public Map<String, Object> getInvoiceByKey(Long id) {
		// TODO Auto-generated method stub
		InvoiceInfo invoice = null;
		Map<String, Object> mmp = new HashMap<>();
		try {
			invoice = this.invoiceMapper.selectByPrimaryKeyTwo(id);
			mmp.put("Obj", invoice);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Msg", "查询成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递异常");
		}
		return mmp;
	}

	@Override
	public Map<String, Object> changeInvoiceStateById(Long id) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		ezs_invoice invoice = null;
		try {
			invoice = this.invoiceMapper.selectByPrimaryKey(id);
			invoice.setInvoice_status(1);
			this.invoiceMapper.updateByPrimaryKey(invoice);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递异常");
		}
		return mmp;
	}

}
