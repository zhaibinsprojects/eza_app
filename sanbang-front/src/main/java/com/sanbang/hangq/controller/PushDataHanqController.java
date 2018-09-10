package com.sanbang.hangq.controller;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.hangq.servive.HangqPayAdviceService;
import com.sanbang.hangq.servive.HangqPushService;
import com.sanbang.utils.Result;

/**
 * 
 * @author langjf  
 *
 */
@Controller
@RequestMapping("/app/payAdvice")
public class PushDataHanqController {

	private static Logger log = Logger.getLogger(PushDataHanqController.class);
	@Autowired
	private HangqPushService hangqPushService;
	@Autowired
	private HangqPayAdviceService hangqPayAdviceService;

	/**
	 * alipayAdvice
	 * @param param
	 * @param request
	 * @return
	 */
	@RequestMapping("/aliPayAdvice")
	@ResponseBody
	public Result AliPayAdvice(@RequestParam Map<String, String> param,HttpServletRequest request) {
		Result	result=Result.failure();
		String aa="https://api.xx.com/receive_notify.htm?"
				+ "total_amount=2.00"
				+ "&buyer_id=2088102116773037"
				+ "&body=大乐透2.1"
				+ "&trade_no=2016071921001003030200089909"
				+ "&refund_fee=0.00"
				+ "&notify_time=2016-07-19 14:10:49"
				+ "&subject=大乐透2.1"
				+ "&sign_type=RSA2"
				+ "&charset=utf-8"
				+ "&notify_type=trade_status_sync"
				+ "&out_trade_no=0719141034-6418"
				+ "&gmt_close=2016-07-19 14:10:46"
				+ "&gmt_payment=2016-07-19 14:10:47"
				+ "&trade_status=TRADE_SUCCESS"
				+ "&version=1.0"
				+ "&sign=kPbQIjX+xQc8F0/A6/AocELIjhhZnGbcBN6G4MM/HmfWL4ZiHM6fWl5NQhzXJusaklZ1LFuMo+lHQUELAYeugH8LYFvxnNajOvZhuxNFbN2LhF0l/KL8ANtj8oyPM4NN7Qft2kWJTDJUpQOzCzNnV9hDxh5AaT9FPqRS6ZKxnzM="
				+ "&gmt_create=2016-07-19 14:10:44"
				+ "&app_id=2015102700040153"
				+ "&seller_id=2088102119685838"
				+ "&notify_id=4a91b7a78a503640467525113fb7d8bg8e";
		
		for (Entry<String, String> map : param.entrySet()) {
			log.info(map.getKey()+ map.getValue());
		}
		result=hangqPayAdviceService.aliPayAdvice(request, param, result);
		return result;
	}

	
	
}
