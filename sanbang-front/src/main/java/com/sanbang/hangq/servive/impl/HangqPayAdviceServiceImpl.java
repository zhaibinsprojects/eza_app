package com.sanbang.hangq.servive.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sanbang.hangq.servive.HangqPayAdviceService;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;

@Service("hangqPayAdviceService")
public class HangqPayAdviceServiceImpl  implements HangqPayAdviceService{

	private Logger log = Logger.getLogger(HangqPayAdviceServiceImpl.class);
	
	@Override
	public Result aliPayAdvice(HttpServletRequest request, Map<String, Object> param, Result result) {
		
		//请求参数缓存
		Map<String, Object> map=new HashMap<String, Object>();
		map.putAll(param);
		
		if(!(param.containsKey("sign_type")&&param.containsKey("sign"))) {
			result.setMsg("参数错误sign_type或sign为空");
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
		}	
		
		String signstr=String.valueOf(param.get("sign"));
		param.remove("sign");
		param.remove("sign_type");
		
		
		
		return null;
	}
	}
