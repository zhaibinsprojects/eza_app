package com.sanbang.seller.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_bill;
import com.sanbang.bean.ezs_invoice;
import com.sanbang.bean.ezs_pact;
import com.sanbang.bean.ezs_payinfo;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_billMapper;
import com.sanbang.dao.ezs_invoiceMapper;
import com.sanbang.dao.ezs_payinfoMapper;
import com.sanbang.dao.ezs_userMapper;
import com.sanbang.seller.service.SellerReceiptService;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;
@Service
public class SellerReceiptServiceImpl implements SellerReceiptService {
	
	//日志
	private static Logger log = Logger.getLogger(SellerReceiptServiceImpl.class.getName());
	
	@Autowired
	ezs_billMapper billMapper;
	
	@Autowired
	ezs_invoiceMapper invoiceMapper;
	
	@Autowired
	ezs_payinfoMapper payinfoMapper;
	
	@Autowired
	ezs_userMapper userMapper;
	
	@Override
	public ezs_bill getBillInfoById(Long userId) {
	
		return billMapper.selectByPrimaryKey(userId);
	}

	@Override
	public List<ezs_invoice> getInvoiceListInfoById(Long userId) {
		
		return invoiceMapper.selectInvoiceListInfoById(userId);
	}

	

	@Override
	public int getCount() {
		
		return invoiceMapper.selectCount();
	}

	@Override
	public ezs_payinfo getPayInfoById(Long billId) {
		return payinfoMapper.selectByBillId(billId);
	}

	@Override
	public ezs_user getUserInfoById(Long paymentUser_id) {
		
		return userMapper.selectByPrimaryKey(paymentUser_id);
	}

	@Override
	public Object queryInvoiceByIdOrDate(Result result, String orderno, String startTime, String endTime,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("条件查询合同**************************");
		
		List<ezs_invoice> list = new ArrayList<>();
		ezs_invoice invoice = new  ezs_invoice();
		if(Tools.notEmpty(orderno) && Tools.isNum(orderno)){
			invoice = invoiceMapper.selectInvoiceByOrderNo(orderno);
			result.setObj(invoice);
		}
		if (Tools.notEmpty(startTime) && Tools.notEmpty(endTime)){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date dt1;
			Date dt2;
			try {
				dt1 = df.parse(startTime);
				dt2 = df.parse(endTime);
				if (dt1.getTime() > dt2.getTime()) {
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setMsg("日期格式错误");
					return result;
				}
				list = invoiceMapper.selectInvoiceByDate(dt1, dt2);
				result.setObj(list);
				
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public ezs_invoice getInvoiceInfoById(String orderno) {
		
		return invoiceMapper.selectInvoiceByOrderNo(orderno);
	}
	
	public int insertInvoice(ezs_invoice invoice){
		return invoiceMapper.insert(invoice);
	}
	
	
}
