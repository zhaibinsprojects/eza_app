package com.sanbang.setup.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.area.service.AreaService;
import com.sanbang.bean.ezs_user;
import com.sanbang.dict.service.DictService;
import com.sanbang.userpro.service.UserProService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCate;
import com.sanbang.vo.DictionaryCode;

@Controller
@RequestMapping("/setup/companyinfo/")
public class UserSetupCompanyInfoController {

	private Logger log=Logger.getLogger(UserSetupCompanyInfoController.class);
	
	
	@Autowired
	private UserProService userProService;
	
	@Autowired
	private AreaService areaService;
	
	//注册验证码标识
	@Value("${consparam.mobile.recode}")
	private String mobilerecode;
	
	//验证码有效时间
	@Value("${consparam.mobile.sendcodeexpir}")
	private String mobilesendcodeexpir;
	
	//验证码发送的间隔
	@Value("${consparam.mobile.interval}")
	private String mobileinterval;
	
	//#短信验证码发送的次数
	@Value("${consparam.mobile.sendtimes}")
	private String mobilesendtimes;
	
	@Autowired
	private  DictService dictService;
	
	
	
	/**
	 * 设置企业资料
	 * @param userName
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/init")
	@ResponseBody
	public Object upCompanyInit(HttpServletRequest request) throws Exception{
		Result result=Result.failure();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		
		if(null!=upi.getEzs_userinfo()){
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setSuccess(true);
			result.setMsg("请求成功");
			Map<String, Object> map=new HashMap<>();
			map.put("cominfo", upi.getEzs_store());
			//主营行业
			map.put("industry", dictService.getDictByParentId(DictionaryCate.EZS_INDUSTRY));
			//公司类型
			map.put("comtype", dictService.getDictByParentId(DictionaryCate.EZS_COMPANYTYPE));
			//地址
			map.put("area", areaService.getAreaParentList());
 			result.setObj(map);
		};
		
		return result;
	}
	
	/**
	 * 保存企业资料
	 * @param userName
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/upCompanyInfo")
	@ResponseBody
	public Object upCompanyOperat(HttpServletRequest request) throws Exception{
		Result result=Result.failure();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		
		if(null!=upi.getEzs_userinfo()){
			result=userProService.upStoreInfo(request, upi.getEzs_store(), upi);
		};
		
		return result;
	}
	
	
}
