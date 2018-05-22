package com.sanbang.seller.service;

import java.util.List;
import java.util.Map;

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

	ezs_payinfo getPayInfoById(Long billId);
	
	ezs_user getUserInfoById(Long paymentUser_id);

	Map<String, Object> getInvoiceListById(Long userId, String currentPage);

<<<<<<< HEAD
	Object queryInvoiceByIdOrDate(Result result, String orderno, String startTime, String endTime,
			HttpServletRequest request, HttpServletResponse response);
	
	int insertInvoice(ezs_invoice invoice);
=======
	Map<String, Object> queryInvoiceByIdOrDate(Result result, String orderno, String startTime, String endTime,
			long userId, String currentPage);
>>>>>>> 57d2f15f122f7aeb33fc47a59b450ff93d950574
}
