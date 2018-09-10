package com.sanbang.hangq.servive.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.sanbang.alipay.api.MainDoAlipayUtil;
import com.sanbang.hangq.servive.HangqPayAdviceService;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;

@Service("hangqPayAdviceService")
public class HangqPayAdviceServiceImpl implements HangqPayAdviceService {

	private Logger log = Logger.getLogger(HangqPayAdviceServiceImpl.class);

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Result aliPayAdvice(HttpServletRequest request, Map<String, String> param, Result result) {

		try {// 获取支付宝POST过来反馈信息
			Map<String, String> params = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用。
				 valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}
			
			//秘钥校验
			boolean flag = AlipaySignature.rsaCheckV1(params, MainDoAlipayUtil.ALIPAY_PUBLIC_KEY,
					MainDoAlipayUtil.CHARSET, "RSA2");
			
			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
			
			
			//是否成功
			if(flag) {
				//业务逻辑操作
				if (!"TRADE_FINISHED".equals(trade_status) && !"TRADE_SUCCESS".equals(trade_status)) {
					result.setMsg("交易状态码不正确");
					result.setSuccess(false);
					result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				}else {
					String out_trade_no=request.getParameter("out_trade_no");
					BigDecimal total_amount=new BigDecimal(request.getParameter("total_amount"));
					
				}
				result.setMsg("校验成功");
				result.setSuccess(false);
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			}else {
				//校验失败
				result.setMsg("秘钥校验失败");
				result.setSuccess(false);
				result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("系统错误");
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}

		return result;
	}
	
	
	
}
