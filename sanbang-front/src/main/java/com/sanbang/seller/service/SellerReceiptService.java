package com.sanbang.seller.service;

import java.util.Map;


import com.sanbang.bean.ezs_bill;
import com.sanbang.bean.ezs_invoice;
import com.sanbang.bean.ezs_payinfo;
import com.sanbang.bean.ezs_user;
import com.sanbang.utils.Result;

public interface SellerReceiptService {

	ezs_bill getBillInfoById(Long userId);

	ezs_invoice getInvoiceInfoById(String orderno);

	ezs_payinfo getPayInfoById(Long billId);
	
	ezs_user getUserInfoById(Long paymentUser_id);

	Map<String, Object> getInvoiceListById(Long userId, String currentPage);
	
	int insertInvoice(ezs_invoice invoice);
	
	Map<String, Object> queryInvoiceByIdOrDate(Result result, String orderno, String startTime, String endTime,
			long userId, String currentPage);
}
