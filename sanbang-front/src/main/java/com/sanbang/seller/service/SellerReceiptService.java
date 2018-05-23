package com.sanbang.seller.service;

import java.util.Map;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_bill;
import com.sanbang.bean.ezs_invoice;
import com.sanbang.bean.ezs_payinfo;
import com.sanbang.bean.ezs_user;
import com.sanbang.utils.Result;

public interface SellerReceiptService {

	ezs_bill getBillInfoById(Long userId);

	ezs_payinfo getPayInfoById(Long billId);
	
	ezs_user getUserInfoById(Long paymentUser_id);

	Map<String, Object> getInvoiceListById(Long userId, String currentPage);

	Map<String, Object> queryInvoiceByIdOrDate(Result result, String orderno, String startTime, String endTime,
			long userId, String currentPage);

	ezs_invoice queryInvoiceByNo(String orderNo);

	ezs_accessory queryAccessoryById(Long receipt_id);
}
