package com.sanbang.app.controller;


import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.area.service.AreaService;
import com.sanbang.bean.ezs_area;
import com.sanbang.bean.ezs_user;
import com.sanbang.dict.service.DictService;
import com.sanbang.setup.controller.UserSetupAuthController;
import com.sanbang.setup.service.AuthService;
import com.sanbang.upload.sevice.FileUploadService;
import com.sanbang.utils.ImageUrlUtil;
import com.sanbang.utils.JiGuanPushUtils;
import com.sanbang.utils.MD5Util;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCate;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.userauth.AuthImageVo;

@Controller
@RequestMapping("/app/setup/auth/")
public class AppUserSetupAuthController {

	private Logger log=Logger.getLogger(UserSetupAuthController.class);
	
	@Autowired
	private com.sanbang.dao.ezs_areaMapper ezs_areaMapper;
	
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
	
	//授权url
	@Value("${config.shouquan.url}")
	private String shouquanurl;
	//demourl
	@Value("${config.shouquandemo.url}")
	private String shouquandemourl;
	
	@Autowired
	private  DictService dictService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private AuthService authService;
	
	@Resource(name="fileUploadService")
	private FileUploadService fileUploadService;
	
	@Resource(name="ezs_userMapper")
	private com.sanbang.dao.ezs_userMapper ezs_userMapper;
	
	// rediskey有效期
	@Value("${consparam.redis.redisuserkeyexpir}")
	private String redisuserkeyexpir;
	
	private static SimpleDateFormat smfh = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 买家会员中心
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/menuinit")
	@ResponseBody
	public Result cataInit(HttpServletRequest request) {
		Result result = Result.success();
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("请求成功");

		ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
		Map<String, Object> map=new HashMap<>();
		if (upi == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			result.setSuccess(false);
			map.put("userimg", "");
			map.put("name", "");
			map.put("status", 0);
			map.put("auditingusertype_id",0);
			map.put("accountType", 0);
			result.setObj(map);
			return result;
		}else{
			ezs_user upi1=ezs_userMapper.getUserInfoByUserNameFromBack(upi.getName()).get(0);
			if(null!=upi1){
				RedisUserSession.updateUserInfo(upi.getUserkey(), upi1, Long.parseLong(redisuserkeyexpir));
				upi=upi1;
			}
			map.put("userimg", ImageUrlUtil.geturl(DictionaryCate.USER_ICON, upi.getAuthimg()));
			map.put("name", upi.getName());
			map.put("status", upi.getEzs_store().getStatus());
			map.put("auditingusertype_id",dictService.getDictByThisId(upi.getEzs_store().getAuditingusertype_id())==null?"":
				dictService.getDictByThisId(upi.getEzs_store().getAuditingusertype_id()).getSequence());
			map.put("accountType", upi.getEzs_store().getAccountType());
			result.setObj(map);
		}
		//JiGuanPushUtils.JiGangPushData("aabbcc", MD5Util.md5Encode(upi.getEzs_userinfo().getPhone()+upi.getId()));
		return result;
	}
	

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
		
		ezs_user upi=RedisUserSession.getUserInfoByKeyForApp(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		
		Map<String, Object> map=new HashMap<>();
		map.put("status", upi.getEzs_store().getStatus());// 0:初始数据无业务 审核状态 1:需要审核 2.审核通过,3审核未通过
		//企业信息
		if("companyshow".equals(typevalue)){
			map.put("business", upi.getEzs_store().getBusiness()==null?0:upi.getEzs_store().getBusiness()?1:0);
			map.put("businessCardTime", upi.getEzs_store().getBusinessCardTime()==null?"":smfh.format(upi.getEzs_store().getBusinessCardTime()));
			//初始化地址
			map.put("initarea", areaService.getAreaParentList());
			
			map.put("companyName", upi.getEzs_store().getCompanyName());// 企业名称
			map.put("trueName", upi.getTrueName());// 联系人
			if(null!=upi.getEzs_store().getArea_id()){
				map.put("area",getaddressinfo(upi.getEzs_store().getArea_id()));// 经营地址
			}else{
				map.put("area","");// 经营地址
			}
			
			if(null!=upi.getEzs_store().getArea_id()){
				map.put("area_id",upi.getEzs_store().getArea_id());// 经营地址区县
			}else{
				map.put("area_id",0);// 经营地址区县
			}
			map.put("address", upi.getEzs_store().getAddress());// 详细地址
			
			if(null!=upi.getEzs_store()){
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
			Map<String, Object> imgmap=new HashMap<>();
			//初始化地址
			if(null!=upi.getAuthimg()&&upi.getAuthimg().size()>0){
				for (AuthImageVo authimg : upi.getAuthimg()) {
					if(DictionaryCate.IDCARD_FONT.equals(authimg.getImgcode())||
							DictionaryCate.IDCARD_BACK.equals(authimg.getImgcode())||
							DictionaryCate.ACCOUNT_OPENING_LICENSE.equals(authimg.getImgcode())||
							DictionaryCate.OTHER_QUALIFICATIONS.equals(authimg.getImgcode())||
							DictionaryCate.BUSINESS_LICENSE.equals(authimg.getImgcode())){
						imgmap.put(authimg.getImgcode(), authimg.getImgurl());
					}
				}
				
				map.put("imgmap", imgmap);
				map.put("hashimg", true);
			}else{
				map.put("hashimg", false);
				map.put("imgmap", imgmap);
			}
			
			result.setObj(map);
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setMsg("获取信息成功");
			result.setSuccess(true);
			return result;
		}
		
		
		//开票信息
		Map<String, Object> bill=new HashMap<>();
		if("comEin".equals(typevalue)){
			if(null!=upi.getEzs_store().getStatus()){
				if(null!=upi.getEzs_bill()){
					map.put("companyName",upi.getEzs_bill().getCompanyName());
					map.put("dutyNo",upi.getEzs_bill().getDutyNo());
					map.put("number",upi.getEzs_bill().getNumber());
					map.put("phone",upi.getEzs_bill().getPhone());
					map.put("address", upi.getEzs_bill().getAddress());
					map.put("bank",upi.getEzs_bill().getBank());
					bill.put("hashbill", true);
				}
				
			}else{
				bill.put("hashbill", false);
			}
			
			bill.put("bill", map);
			result.setObj(bill);
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setMsg("获取信息成功");
			result.setSuccess(true);
			return result;
		}
		
		
		//授权证书
		Map<String, Object> auth=new HashMap<>();
		if("authfile".equals(typevalue)){
			boolean hashauth=false;
			//初始化地址
			if(null!=upi.getAuthimg()){
				for (AuthImageVo authimg : upi.getAuthimg()) {
					if(DictionaryCate.LETTER_OF_AUTHORIZATION.equals(authimg.getImgcode())||
							DictionaryCate.LICENSEE_IDCARD.equals(authimg.getImgcode())||
							DictionaryCate.SHENGMING.equals(authimg.getImgcode())){
						auth.put(authimg.getImgcode(), authimg.getImgurl());
						hashauth=true;
					}
				}
				
			}
		
			map.put("authfile", auth);
			map.put("hashauth", hashauth);
			map.put("temurl", shouquanurl);
			map.put("demotemurl", shouquandemourl);
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
		
		ezs_user upi=RedisUserSession.getUserInfoByKeyForApp(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		
		Map<String, Object> map=new HashMap<>();
		map.put("status", upi.getEzs_store().getStatus());// 0:初始数据无业务 审核状态 1:需要审核 2.审核通过,3审核未通过
		//公司信息
		if("companyshow".equals(typevalue)){
			map.put("business", upi.getEzs_store().getBusiness()==null?0:upi.getEzs_store().getBusiness()?1:0);
			map.put("businessCardTime", upi.getEzs_store().getBusinessCardTime()==null?"":smfh.format(upi.getEzs_store().getBusinessCardTime()));
			//初始化地址
			map.put("initarea", areaService.getAreaParentList());
			
			map.put("companyName", upi.getEzs_store().getCompanyName());// 企业名称
			map.put("trueName", upi.getTrueName());// 联系人
			if(null!=upi.getEzs_store().getArea_id()){
				map.put("area",getaddressinfo(upi.getEzs_store().getArea_id()));// 经营地址
			}else{
				map.put("area","");// 经营地址
			}
			
			if(null!=upi.getEzs_store().getArea_id()){
				map.put("area_id",upi.getEzs_store().getArea_id());// 经营地址区县
			}else{
				map.put("area_id",0);// 经营地址区县
			}
			map.put("address", upi.getEzs_store().getAddress());// 详细地址
			
			
			if(null!=upi.getEzs_store()){
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
			Map<String, Object> imgmap=new HashMap<>();
			//初始化地址
			if(null!=upi.getAuthimg()){
				for (AuthImageVo authimg : upi.getAuthimg()) {
					if(DictionaryCate.IDCARD_FONT.equals(authimg.getImgcode())||
							DictionaryCate.IDCARD_BACK.equals(authimg.getImgcode())||
							DictionaryCate.ACCOUNT_OPENING_LICENSE.equals(authimg.getImgcode())||
							DictionaryCate.OTHER_QUALIFICATIONS.equals(authimg.getImgcode())||
							DictionaryCate.BUSINESS_LICENSE.equals(authimg.getImgcode())){
						imgmap.put(authimg.getImgcode(), authimg.getImgurl());
					}
				}
			}
			map.put("imgmap", imgmap);
			result.setObj(map);
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setMsg("获取信息成功");
			result.setSuccess(true);
			return result;
		}
		
		
		//开票信息
		Map<String, Object> bill=new HashMap<>();
		if("comEin".equals(typevalue)){
			if(null!=upi.getEzs_store().getStatus()){
				if(null!=upi.getEzs_bill()){
					map.put("companyName",upi.getEzs_bill().getCompanyName());
					map.put("dutyNo",upi.getEzs_bill().getDutyNo());
					map.put("number",upi.getEzs_bill().getNumber());
					map.put("phone",upi.getEzs_bill().getPhone());
					map.put("address", upi.getEzs_bill().getAddress());
					map.put("bank",upi.getEzs_bill().getBank());
					bill.put("hashbill", true);
				}
				
			}else{
				bill.put("hashbill", false);
			}
			
			bill.put("bill", map);
			result.setObj(bill);
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setMsg("获取信息成功");
			result.setSuccess(true);
			return result;
		}
		
		
		//授权证书
		Map<String, Object> auth=new HashMap<>();
		if("authfile".equals(typevalue)){
			boolean hashauth=false;
			//初始化地址
			if(null!=upi.getAuthimg()){
				for (AuthImageVo authimg : upi.getAuthimg()) {
					if(DictionaryCate.LETTER_OF_AUTHORIZATION.equals(authimg.getImgcode())||
							DictionaryCate.LICENSEE_IDCARD.equals(authimg.getImgcode())||
							DictionaryCate.SHENGMING.equals(authimg.getImgcode())){
						auth.put(authimg.getImgcode(), authimg.getImgurl());
						hashauth=true;
					}
				}
				
			}
			
			map.put("authfile", auth);
			map.put("hashauth", hashauth);
			map.put("temurl", shouquanurl);
			map.put("demotemurl", shouquandemourl);
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
		ezs_user upi=RedisUserSession.getUserInfoByKeyForApp(request);
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
		ezs_user upi=RedisUserSession.getUserInfoByKeyForApp(request);
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
		ezs_user upi=RedisUserSession.getUserInfoByKeyForApp(request);
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
		ezs_user upi=RedisUserSession.getUserInfoByKeyForApp(request);
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
	@ResponseBody
	@RequestMapping("/upAuthPic")
	public Result upAuthPic(
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="type",required=false) String type){
		Result result=Result.failure();

		Map<String, Object> map1=new HashMap<>();
		try {
			Map<String , Object> map=fileUploadService.uploadFile(request,0,0,10*1024*1024l);
			if("000".equals(map.get("code"))){
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				result.setMsg("上传成功");
				map1.put("picurl",  map.get("url"));
				result.setObj(map1);
				result.setSuccess(true);
				return result;
			}else{
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setObj(map1);
				result.setMsg("上传失败");
				result.setSuccess(false);
			}
		} catch (Exception e) {
			log.info("文件：上传接口调用失败"+e.toString());
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("上传失败");
			result.setObj(map1);
			result.setSuccess(false);
		} 
		result.setObj(new HashMap<>());
		
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
		ezs_user upi=RedisUserSession.getUserInfoByKeyForApp(request);
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
		ezs_user upi=RedisUserSession.getUserInfoByKeyForApp(request);
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
		result=authService.saveAuthPicUrl(result, request, upi, response);
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
		ezs_user upi=RedisUserSession.getUserInfoByKeyForApp(request);
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
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/lookAuthstatus")
	public Result lookAuthstatus(
			HttpServletRequest request,
			HttpServletResponse response){
		Result result=Result.failure();  
		ezs_user upi=RedisUserSession.getUserInfoByKeyForApp(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
		

		Map<String, Object> map=new HashMap<>();
		Map<String, Object> map1=new HashMap<>();
		map1.put("status", upi.getEzs_store().getStatus());
		//初始化地址
		
		map.put("companyName", upi.getEzs_store().getCompanyName());// 企业名称
		map.put("accountType", upi.getEzs_store().getAccountType());
		map1.put("authinfo", map);
		result.setObj(map1);
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
	
	/**
	 * 地址
	 * @param areaid
	 * @return
	 */
	private String getaddressinfo(long areaid) {
		StringBuilder sb = new StringBuilder();
		String threeinfo = "";
		String twoinfo = "";
		String oneinfo = "";
		ezs_area ezs_threeinfo = ezs_areaMapper.selectByPrimaryKey(areaid);
		if (ezs_threeinfo != null) {
			threeinfo = ezs_threeinfo.getAreaName();
			ezs_area ezs_twoinfo = ezs_areaMapper.selectByPrimaryKey(ezs_threeinfo.getParent_id());
			if (ezs_twoinfo != null) {
				twoinfo =  ezs_twoinfo.getAreaName();
				ezs_area ezs_oneinfo = ezs_areaMapper.selectByPrimaryKey(ezs_twoinfo.getParent_id());
				if (ezs_oneinfo != null) {
					oneinfo =  ezs_oneinfo.getAreaName();
				}
			}
		}
		
		if(!Tools.isEmpty(threeinfo)){
			sb = new StringBuilder().append(threeinfo);	
		}
		if(!Tools.isEmpty(twoinfo)){
			sb = new StringBuilder().append(twoinfo).append("-").append(threeinfo);
		}
		if(!Tools.isEmpty(oneinfo)){
			sb = new StringBuilder().append(oneinfo).append("-").append(twoinfo).append("-").append(threeinfo);
		}
		
		return sb.toString();
	}
	
}
