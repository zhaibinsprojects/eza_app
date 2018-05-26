package com.sanbang.seller.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_bill;
import com.sanbang.bean.ezs_invoice;
import com.sanbang.bean.ezs_pact;
import com.sanbang.bean.ezs_payinfo;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_accessoryMapper;
import com.sanbang.dao.ezs_billMapper;
import com.sanbang.dao.ezs_invoiceMapper;
import com.sanbang.dao.ezs_payinfoMapper;
import com.sanbang.dao.ezs_userMapper;
import com.sanbang.seller.service.SellerReceiptService;
import com.sanbang.utils.Page;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.GoodsInfo;
import com.sanbang.vo.HomeDictionaryCode;

@Service
public class SellerReceiptServiceImpl implements SellerReceiptService {

	// 日志
	private static Logger log = Logger.getLogger(SellerReceiptServiceImpl.class.getName());

	@Autowired
	ezs_billMapper billMapper;

	@Autowired
	ezs_invoiceMapper invoiceMapper;

	@Autowired
	ezs_payinfoMapper payinfoMapper;

	@Autowired
	ezs_userMapper userMapper;
	
	@Autowired
	ezs_accessoryMapper accessoryMapper;
	
	@Override
	public ezs_bill getBillInfoById(Long userId) {

		return billMapper.selectByPrimaryKey(userId);
	}

	@Override
	public Map<String, Object> getInvoiceListById(Long userId, String currentPage) {
		Map<String, Object> mmp = new HashMap<>();
		// 获取总页数
		int totalCount = this.invoiceMapper.getInvoiceCountByUserId(userId);
		Page page = new Page(totalCount, Integer.valueOf(currentPage));
		page.setPageSize(10);
		int startPos = 0;
		page.setStartPos(startPos);
		if (Integer.valueOf(currentPage) >= 1 && Integer.valueOf(currentPage) <= page.getTotalPageCount()) {
			List<ezs_invoice> glist = invoiceMapper.goodsInvoiceCountPage(page, userId);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Page", page);
			mmp.put("Obj", glist);
		} else {
			mmp.put("ErrorCode", HomeDictionaryCode.ERROR_HOME_PAGE_FAIL);
			mmp.put("Msg", "页码越界");
			mmp.put("Page", page);
		}
		return mmp;
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
	public Map<String, Object> queryInvoiceByIdOrDate(Result result, String orderno, String startTime, String endTime,
			long userId, String currentPage) {
		log.info("条件查询合同**************************");
		Map<String, Object> mmp = new HashMap<>();
		List<ezs_invoice> list = new ArrayList<>();
		// 获取总页数
		int totalCount = invoiceMapper.getInvoiceCountByUserId(userId);

		Page page = new Page(totalCount, Integer.valueOf(currentPage));
		page.setPageSize(10);
		if ((Integer.valueOf(currentPage)>=1&&Integer.valueOf(currentPage)<=page.getTotalPageCount())||(page.getTotalPageCount()==0)) {
			if (Tools.notEmpty(startTime) && Tools.notEmpty(endTime)) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date dt1;
				Date dt2;
				try {
					dt1 = df.parse(startTime);
					dt2 = df.parse(endTime);
					if (dt1.getTime() > dt2.getTime()) {
						result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
						result.setMsg("日期格式错误");
					}
					list = invoiceMapper.selectInvoiceByDate(dt1, dt2, userId, page);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				mmp.put("Page", page);
				mmp.put("Obj", list);
			} else {
				ezs_invoice invoice = invoiceMapper.selectInvoiceByOrderNo(orderno);
				mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				mmp.put("Page", page);
				mmp.put("Obj", invoice);
			}
		} else {
			mmp.put("ErrorCode", HomeDictionaryCode.ERROR_HOME_PAGE_FAIL);
			mmp.put("Msg", "页码越界");
			mmp.put("Page", page);
		}
		return mmp;
	}	

	@Override
	public ezs_invoice queryInvoiceByNo(String orderNo) {
		
		return invoiceMapper.selectInvoiceByOrderNo(orderNo);
	}

	@Override
	public ezs_accessory queryAccessoryById(Long receipt_id) {
		
		return accessoryMapper.selectByPrimaryKey(receipt_id);
	}


	@Override
	public ezs_invoice getInvoiceInfoById(String orderno) {
		return invoiceMapper.selectInvoiceByOrderNo(orderno);
	}
	
	public int insertInvoice(ezs_invoice invoice){
		return invoiceMapper.insert(invoice);
	}

	@Override
	public Object queryInvoiceByIdOrDate(Result result, String orderno, String startTime, String endTime,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
