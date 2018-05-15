package com.sanbang.setup.controller.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.sanbang.dao.ezs_industry_dictMapper;
import com.sanbang.dao.ezs_positionMapper;
import com.sanbang.dao.ezs_storeMapper;
import com.sanbang.dao.ezs_userMapper;
import com.sanbang.setup.controller.service.AuthService;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;

public class  AuthServiceImpl implements AuthService {
	//日志
		private static Logger log = Logger.getLogger(AuthServiceImpl.class.getName());
		
		@Resource(name="ezs_userinfoMapper")
		private com.sanbang.dao.ezs_userinfoMapper ezs_userinfoMapper;

		//用户登陆信息
		@Resource(name="ezs_userMapper")
		private ezs_userMapper ezs_userMapper;
		
		//商铺
		@Resource(name="ezs_storeMapper")
		private  ezs_storeMapper ezs_storeMapper;
		
		//关于用户公司类型存储
		@Resource(name="ezs_companyType_dictMapper")
		private com.sanbang.dao.ezs_companyType_dictMapper  ezs_companyType_dictMapper;

		//关于用户公司主营行业存储
		@Resource(name="ezs_industry_dictMapper")
		private ezs_industry_dictMapper  ezs_industry_dictMapper;
		
		@Resource(name="ezs_positionMapper")
		private ezs_positionMapper ezs_positionMapper;

		@Override
		public Result saveComAuth(Result result, HttpServletRequest request) {
			result=savecomvali(request);
			if(result.getSuccess()){
				String companyName = request.getParameter("companyName");// 企业名称
				String area_id = request.getParameter("area_id");// 经营地址区县
				String address = request.getParameter("address");// 经营地址
				String capitalPrice = request.getParameter("capitalPrice ");// 注册资本
				String unifyCode = request.getParameter("unifyCode ");// 社会信用代码
				String persion = request.getParameter("persion ");// 法人
				
				
			}
			return result;
		}
		
	Result savecomvali(HttpServletRequest request) {
		
		Result result = Result.success();
		String companyName = request.getParameter("companyName");// 企业名称
		String area_id = request.getParameter("area_id");// 经营地址区县
		String address = request.getParameter("address");// 经营地址
		String capitalPrice = request.getParameter("capitalPrice ");// 注册资本
		String unifyCode = request.getParameter("unifyCode ");// 社会信用代码
		String persion = request.getParameter("persion ");// 法人
		// TODO 有效期至
		// String unifyCode = request.getParameter("unifyCode ");//社会信用代码

		if (Tools.isEmpty(companyName)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入公司名称");
		}
		if (Tools.isEmpty(address)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入详细地址");
		}
		if (Tools.isEmpty(unifyCode)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入社会信用代码");
		}

		if (Tools.isEmpty(capitalPrice)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入注册资本");
			if (!Tools.isNum(capitalPrice)) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("请输入有效注册资本金额");
			}
		}
		if (Tools.isEmpty(persion)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入法人");
		}
			
			return result;
		}
}
