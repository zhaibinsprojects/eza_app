package com.sanbang.setup.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateUserStatement.UserSpecification;
import com.sanbang.area.service.AreaService;
import com.sanbang.bean.ezs_contact;
import com.sanbang.bean.ezs_user;
import com.sanbang.dict.service.DictService;
import com.sanbang.setup.service.AuthService;
import com.sanbang.userpro.service.UserProService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCate;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.LinkUserVo;

@Controller
@RequestMapping("/setup/auth/")
public class UserSetupAuthController {

	private Logger log=Logger.getLogger(UserSetupAuthController.class);
	
	private  static final String view="/memberuser/regist/";
	
	@Autowired
	private UserProService userProService;
	
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
	
	@Value("${consparam.cookie.userauthcard}")
	private String userauthcard;
	
	@Autowired
	private  DictService dictService;
	
	@Autowired
	private AreaService areaService;
	
	private AuthService authService;
	
	
	

	/**
	 * 企业认证初始化
	 * @param userName
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/cominit")
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
			Map<String, Object> map=new HashMap<>();
			map.put("cominfo", upi.getEzs_store());
			//主营行业
			map.put("industry", dictService.getDictByParentId(DictionaryCate.EZS_INDUSTRY));
			//公司类型
			map.put("comtype", dictService.getDictByParentId(DictionaryCate.EZS_COMPANYTYPE));
			
			//已有地址
			map.put("area", areaService.getAreaParentList());
			//已有com
			map.put("hasccom", dictService.getCompanyTypeByThisId(upi.getStore_id()));
			//已有indus
			map.put("hascindus", dictService.getIndustryByThisId(upi.getStore_id()));
			
			//审核状态
			//0:初始数据无业务 审核状态  1:需要审核 2.审核通过,3审核未通过
			map.put("authstatus", upi.getEzs_store().getStatus());
			
 			result.setObj(map);
		};
		
		return result;
	}
	
	
	/**
	 * 个体认证初始化
	 * @param userName
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/indivinit")
	@ResponseBody
	public Object indivinit(HttpServletRequest request) throws Exception{
		Result result=Result.failure();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		
		if(null!=upi.getEzs_userinfo()){
			Map<String, Object> map=new HashMap<>();
			map.put("cominfo", upi.getEzs_store());
			//主营行业
			map.put("industry", dictService.getDictByParentId(DictionaryCate.EZS_INDUSTRY));
			//公司类型
			map.put("comtype", dictService.getDictByParentId(DictionaryCate.EZS_COMPANYTYPE));
			
			//已有地址
			map.put("area", areaService.getAreaParentList());
			//已有com
			map.put("hasccom", dictService.getCompanyTypeByThisId(upi.getStore_id()));
			//已有indus
			map.put("hascindus", dictService.getIndustryByThisId(upi.getStore_id()));
			
			//审核状态
			//0:初始数据无业务 审核状态  1:需要审核 2.审核通过,3审核未通过
			map.put("authstatus", upi.getEzs_store().getStatus());
			
 			result.setObj(map);
		};
		
		return result;
	}
	
	/**
	 * 保存企业基本信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveComAuth")
	public Result saveComAuth(
			HttpServletRequest request,HttpServletResponse response){
		Result result=Result.failure();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
		try {
			result=authService.saveComAuth(result, request, upi, response);
		} catch (Exception e) {
			log.info("h5保存认证企业基本信息"+upi.getName()+"错误"+e.toString());
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}
		return result;
	}
	
	/**
	 * 保存企业开票信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveComneinAuth")
	public Result saveComneinAuth(
			HttpServletRequest request,HttpServletResponse response){
		Result result=Result.failure();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
		try {
			result=authService.saveComEin(result, request, upi, response);
		} catch (Exception e) {
			log.info("h5保存认证企业开票信息"+upi.getName()+"错误"+e.toString());
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}
		return result;
	}
	
	
	

	
	
	
	
}
