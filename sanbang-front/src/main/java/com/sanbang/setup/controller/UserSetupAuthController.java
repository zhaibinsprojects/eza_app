package com.sanbang.setup.controller;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
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
import com.sanbang.upload.sevice.FileUploadService;
import com.sanbang.userpro.service.UserProService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.RedisUtils;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCate;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.LinkUserVo;
import com.sanbang.vo.userauth.AuthImageVo;

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
	
	//认证标识
	@Value("${consparam.cookie.userauthcard}")
	private String userauthcard;
	
	@Autowired
	private  DictService dictService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private AuthService authService;
	
	@Resource(name="fileUploadService")
	private FileUploadService fileUploadService;
	
	
	

	/**
	 * 企业认证初始化
	 * @param userName
	 * @return
	 * @throws Exception 
	 * 
	 * {typevalue} 
	 * companyshow:企业基本信息  
	 * zhizhishow：资质信息
	 * authfile：授权证书
	 */
	@RequestMapping(value="/cominit/{typevalue}")
	@ResponseBody
	public Object authCompanyInit(@PathVariable(value="typevalue")String typevalue,
			HttpServletRequest request) throws Exception{
		Result result=Result.failure();
		result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
		result.setMsg("参数错误");
		
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		
		Map<String, Object> map=new HashMap<>();
		map.put("status", upi.getEzs_store().getStatus());// 0:初始数据无业务 审核状态 1:需要审核 2.审核通过,3审核未通过
		//企业信息
		if("companyshow".equals(typevalue)){
			
			//初始化地址
			map.put("initarea", areaService.getAreaParentList());
			
			map.put("companyName", upi.getEzs_store().getCompanyName());// 企业名称
			map.put("trueName", upi.getTrueName());// 联系人
			map.put("area", areaService.getAreaListByParId(	upi.getEzs_store().getArea_id()));// 经营地址区县
			map.put("address", upi.getEzs_store().getAddress());// 经营地址
			
			if(0!=upi.getEzs_store().getStatus()){
				map.put("capitalPrice", upi.getEzs_store().getCapitalPrice());// 注册资本
				map.put("unifyCode", upi.getEzs_store().getUnifyCode());// 社会信用代码
				map.put("persion", upi.getEzs_store().getPerson());// 法人
				
			}
			
			result.setObj(map);
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setMsg("获取信息成功");
			result.setSuccess(true);
			return result;
		}
		
		//资质信息
		if("zhizhishow".equals(typevalue)){
			//初始化地址
			if(null!=upi.getAuthimg()){
				for (AuthImageVo authimg : upi.getAuthimg()) {
					map.put("hashimg", false);
					map.put(authimg.getImgcode(), authimg.getImgurl());
				}
			}else{
				map.put("hashimg", false);
			}
			
			
			
			result.setObj(map);
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setMsg("获取信息成功");
			result.setSuccess(true);
			return result;
		}
		
		
		//开票信息
		if("comEin".equals(typevalue)){
			if(0!=upi.getEzs_store().getStatus()){
				map.put("companyName",upi.getEzs_bill().getCompanyName());
				map.put("dutyNo",upi.getEzs_bill().getDutyNo());
				map.put("number",upi.getEzs_bill().getNumber());
				map.put("phone",upi.getEzs_bill().getPhone());
				map.put("address", upi.getEzs_bill().getAddress());
				map.put("bank",upi.getEzs_bill().getBank());
			}
			
			result.setObj(map);
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setMsg("获取信息成功");
			result.setSuccess(true);
			return result;
		}
		
		
		//授权证书
		if("authfile".equals(typevalue)){
			//初始化地址
			if(null!=upi.getAuthimg()){
				for (AuthImageVo authimg : upi.getAuthimg()) {
					map.put("hashimg", false);
					map.put(authimg.getImgcode(), authimg.getImgurl());
				}
			}else{
				map.put("hashimg", false);
			}
			map.put("temurl", "https://www.baidu.com");
			result.setObj(map);
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setMsg("获取信息成功");
			result.setSuccess(true);
			return result;
		}
		return result;
	}
	
	
	
	
	/**
	 * 个体认证初始化
	 * @param userName
	 * @return
	 * @throws Exception 
	 * {typevalue} 
	 * companyshow:企业基本信息  
	 * zhizhishow：资质信息
	 * authfile：授权证书
	 */
	@RequestMapping(value="/indivinit/{typevalue}")
	@ResponseBody
	public Object indivinit(@PathVariable(value="typevalue")String typevalue,
			HttpServletRequest request) throws Exception{
		Result result=Result.failure();
		result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
		result.setMsg("参数错误");
		
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		
		Map<String, Object> map=new HashMap<>();
		map.put("status", upi.getEzs_store().getStatus());// 0:初始数据无业务 审核状态 1:需要审核 2.审核通过,3审核未通过
		//公司信息
		if("companyshow".equals(typevalue)){
			//初始化地址
			map.put("initarea", areaService.getAreaParentList());
			
			map.put("companyName", upi.getEzs_store().getCompanyName());// 企业名称
			map.put("trueName", upi.getTrueName());// 联系人
			map.put("area", areaService.getAreaListByParId(	upi.getEzs_store().getArea_id()));// 经营地址区县
			map.put("address", upi.getEzs_store().getAddress());// 经营地址
			
			
			if(0!=upi.getEzs_store().getStatus()){
				map.put("idCardNum", upi.getEzs_store().getIdCardNum());// 经营者省份证号
				map.put("account", upi.getEzs_store().getAccount());// 注册号
				map.put("persion", upi.getEzs_store().getPerson());// 经营者
				
			}
			
			result.setObj(map);
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setMsg("获取信息成功");
			result.setSuccess(true);
			return result;
		}
		
		//资质信息
		if("zhizhishow".equals(typevalue)){
			//初始化地址
			if(null!=upi.getAuthimg()){
				for (AuthImageVo authimg : upi.getAuthimg()) {
					map.put("hashimg", false);
					map.put(authimg.getImgcode(), authimg.getImgurl());
				}
			}else{
				map.put("hashimg", false);
			}
			
			
			
			result.setObj(map);
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setMsg("获取信息成功");
			result.setSuccess(true);
			return result;
		}
		
		
		//开票信息
		if("comEin".equals(typevalue)){
			if(0!=upi.getEzs_store().getStatus()){
				map.put("companyName",upi.getEzs_bill().getCompanyName());
				map.put("dutyNo",upi.getEzs_bill().getDutyNo());
				map.put("number",upi.getEzs_bill().getNumber());
				map.put("phone",upi.getEzs_bill().getPhone());
				map.put("address", upi.getEzs_bill().getAddress());
				map.put("bank",upi.getEzs_bill().getBank());
			}
			
			result.setObj(map);
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setMsg("获取信息成功");
			result.setSuccess(true);
			return result;
		}
		
		
		//授权证书
		if("authfile".equals(typevalue)){
			//初始化地址
			if(null!=upi.getAuthimg()){
				for (AuthImageVo authimg : upi.getAuthimg()) {
					map.put("hashimg", false);
					map.put(authimg.getImgcode(), authimg.getImgurl());
				}
			}else{
				map.put("hashimg", false);
			}
			map.put("temurl", "https://www.baidu.com");
			result.setObj(map);
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setMsg("获取信息成功");
			result.setSuccess(true);
			return result;
		}
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
	@RequestMapping("/saveComEinAuth")
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
	
	
	/**
	 * 保存个体基本信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveDivAuth")
	public Result saveDivAuth(
			HttpServletRequest request,HttpServletResponse response){
		Result result=Result.failure();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
		try {
			result=authService.saveindivAuth(result, request, upi, response);
		} catch (Exception e) {
			log.info("h5保存认证个体基本信息"+upi.getName()+"错误"+e.toString());
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}
		return result;
	}
	
	/**
	 * 保存个体开票信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveDivEinAuth")
	public Result saveDivEinAuth(
			HttpServletRequest request,HttpServletResponse response){
		Result result=Result.failure();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
		try {
			result=authService.saveindivEin(result, request, upi, response);
		} catch (Exception e) {
			log.info("h5保存认证个体开票信息"+upi.getName()+"错误"+e.toString());
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}
		return result;
	}

	
	/**
	 * 上传认证图片
	 * @param request
	 * @param response
	 * @return
	 */
	public Result upAuthPic(
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="type",required=false) String type){
		Result result=Result.failure();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
		/*
		//检查上传类型
		result=checkuptype(type);*/
		
		/*if(!result.getSuccess()){
			return result;
		}*/
		
		try {
			Map<String , Object> map=fileUploadService.uploadFile(request,0,0,10*1024*1024l);
			if("000".equals(map.get("code"))){
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				result.setMsg("上传成功");
				result.setObj(new HashMap<>().put("picurl", map.get("url")));
				result.setSuccess(false);
				return result;
			}else{
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("上传失败");
				result.setObj("");
				result.setSuccess(false);
			}
		} catch (Exception e) {
			log.info("文件：上传接口调用失败"+e.toString());
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("上传失败");
			result.setObj("");
			result.setSuccess(false);
		} 
		
		
		return result;
	}
	
	
	
	/**
	 * 保存企业资质信息信息  保存个体资质信息信息
	 * authtype company indiv
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/savezhizhi/{authtype}")
	public Result savezhizhicom(@PathVariable(value="authtype") String authtype,
			HttpServletRequest request,
			HttpServletResponse response){
		Result result=Result.failure();  
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
		
		if("company".equals(authtype)){
			upi.getEzs_store().setAccountType(1);
		}else{
			upi.getEzs_store().setAccountType(2);
		}
		result=authService.savePicUrl(result, request, upi, response);
		return result;
	}
	
	
	/**
	 * 保存企业授权信息 保存个体授权信息 
	 * authtype company indiv
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveshouq/{authtype}")
	public Result saveshouq(@PathVariable(value="authtype") String authtype,
			HttpServletRequest request,
			HttpServletResponse response){
		Result result=Result.failure();  
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
		
		if("company".equals(authtype)){
			upi.getEzs_store().setAccountType(1);
		}else{
			upi.getEzs_store().setAccountType(2);
		}
		result=authService.savePicUrl(result, request, upi, response);
		return result;
	}
	
	
	
	
	/**
	 * 保存企业  保存个体  提交
	 * authtype company indiv
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveAuthInfo/{authtype}")
	public Result saveAuthInfo(@PathVariable(value="authtype") String authtype,
			HttpServletRequest request,
			HttpServletResponse response){
		Result result=Result.failure();  
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
		
		if("company".equals(authtype)){
			upi.getEzs_store().setAccountType(1);
		}else{
			upi.getEzs_store().setAccountType(2);
		}
		result=authService.saveAuthInfo(result, request, upi, response, authtype);
		return result;
	}
	
	
	
	
	/**
	 * 查看认证状态
	 * authtype company indiv
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/lookAuthstatus")
	public Result lookAuthstatus(@PathVariable(value="authtype") String authtype,
			HttpServletRequest request,
			HttpServletResponse response){
		Result result=Result.failure();  
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
		

		Map<String, Object> map=new HashMap<>();
		//初始化地址
		map.put("companyName", upi.getEzs_store().getCompanyName());// 企业名称
		map.put("status", upi.getEzs_store().getStatus());// 0:初始数据无业务 审核状态 1:需要审核 2.审核通过,3审核未通过
		
		result.setObj(map);
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("获取信息成功");
		result.setSuccess(true);
		return result;
	
	}
	
	
	
	/**
	 * 检查上传类型
	 * @param type
	 * @return
	 */
	private Result checkuptype(String type){
		Result result=Result.failure();
		result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
		result.setMsg("上传类型错误");
		
		switch (type) {
		//身份证，正面
		case "IDCARD_FONT":
			result.setSuccess(true);
			result.setMsg("上传成功");
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			break;
		// 身份证，反面
		case "IDCARD_BACK":
			result.setSuccess(true);
			result.setMsg("上传成功");
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			break;
			//开户许可证
		case "ACCOUNT_OPENING_LICENSE":
			result.setSuccess(true);
			result.setMsg("上传成功");
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			break;
			//企业授权证书
		case "LETTER_OF_AUTHORIZATION":
			result.setSuccess(true);
			result.setMsg("上传成功");
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			break;
			//其他资质
		case "OTHER_QUALIFICATIONS":
			result.setSuccess(true);
			result.setMsg("上传成功");
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			break;
			//被授权人身份证复印件
		case "LICENSEE_IDCARD":
			result.setSuccess(true);
			result.setMsg("上传成功");
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			break;
			//工商营业执照
		case "BUSINESS_LICENSE":
			result.setSuccess(true);
			result.setMsg("上传成功");
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			break;
		
		default:
			break;
		}
		
		return result;
	}
	
}
