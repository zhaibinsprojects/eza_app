package com.sanbang.seller.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sanbang.bean.ezs_bill;
import com.sanbang.bean.ezs_invoice;
import com.sanbang.bean.ezs_payinfo;
import com.sanbang.bean.ezs_user;
import com.sanbang.utils.Result;

public interface SellerReceiptService {

	ezs_bill getBillInfoById(Long userId);

	ezs_invoice getInvoiceInfoById(String orderno);

	int getCount();

	ezs_payinfo getPayInfoById(Long billId);

	ezs_user getUserInfoById(Long paymentUser_id);

	List<ezs_invoice> getInvoiceListInfoById(Long userId);

	Object queryInvoiceByIdOrDate(Result result, String orderno, String startTime, String endTime,
			HttpServletRequest request, HttpServletResponse response);
	
	int insertInvoice(ezs_invoice invoice);
}
