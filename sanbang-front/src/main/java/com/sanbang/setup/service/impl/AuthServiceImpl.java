package com.sanbang.setup.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TooManyListenersException;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_bill;
import com.sanbang.bean.ezs_card_dict;
import com.sanbang.bean.ezs_paper;
import com.sanbang.bean.ezs_store;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_accessoryMapper;
import com.sanbang.dao.ezs_billMapper;
import com.sanbang.dao.ezs_card_dictMapper;
import com.sanbang.dao.ezs_industry_dictMapper;
import com.sanbang.dao.ezs_paperMapper;
import com.sanbang.dao.ezs_positionMapper;
import com.sanbang.dao.ezs_storeMapper;
import com.sanbang.dao.ezs_userMapper;
import com.sanbang.dict.service.DictService;
import com.sanbang.redis.RedisConstants;
import com.sanbang.redis.RedisResult;
import com.sanbang.setup.service.AuthService;
import com.sanbang.utils.FilePathUtil;
import com.sanbang.utils.RandomStr32;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.RedisUtils;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCate;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.userauth.AuthImageVo;

@Service("authService")
public class AuthServiceImpl implements AuthService {
	// 日志
	private static Logger log = Logger.getLogger(AuthServiceImpl.class.getName());

	@Value("${consparam.cookie.userauthcard}")
	private String userauthcard;

	// rediskey有效期
	@Value("${consparam.redis.redisuserkeyexpir}")
	private String redisuserkeyexpir;

	@Value("${consparam.cookie.cookieuserkeyexpir}")
	private String cookieuserkeyexpir;
	
	//cookie
	@Value("${consparam.cookie.userkey}")
	private String cookieuserkey;

	@Resource(name = "ezs_userinfoMapper")
	private com.sanbang.dao.ezs_userinfoMapper ezs_userinfoMapper;

	// 用户登陆信息
	@Resource(name = "ezs_userMapper")
	private ezs_userMapper ezs_userMapper;

	// 商铺
	@Resource(name = "ezs_storeMapper")
	private ezs_storeMapper ezs_storeMapper;

	// 关于用户公司类型存储
	@Resource(name = "ezs_companyType_dictMapper")
	private com.sanbang.dao.ezs_companyType_dictMapper ezs_companyType_dictMapper;

	// 关于用户公司主营行业存储
	@Resource(name = "ezs_industry_dictMapper")
	private ezs_industry_dictMapper ezs_industry_dictMapper;

	@Resource(name = "ezs_positionMapper")
	private ezs_positionMapper ezs_positionMapper;

	@Autowired
	private ezs_billMapper ezs_billMapper;
	
	@Autowired
	private ezs_accessoryMapper ezs_accessoryMapper;
	
	@Autowired
	private ezs_paperMapper ezs_paperMapper;
	
	@Autowired
	private ezs_card_dictMapper ezs_card_dictMapper;
	
	@Autowired
	private DictService dictService;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public Result saveComAuth(Result result, HttpServletRequest request, ezs_user upi, HttpServletResponse response) {
		result = savecomvali(request);
		if (result.getSuccess()) {
			String companyName = request.getParameter("companyName");// 企业名称
			String area_id = request.getParameter("area_id");// 经营地址区县
			String address = request.getParameter("address");// 经营地址
			String capitalPrice = request.getParameter("capitalPrice");// 注册资本
			String unifyCode = request.getParameter("unifyCode");// 社会信用代码
			String persion = request.getParameter("persion");// 法人
			String trueName = request.getParameter("trueName");// 联系人
			
			upi.getEzs_store().setCompanyName(companyName);
			upi.getEzs_store().setArea_id(Long.valueOf(area_id));
			upi.getEzs_store().setAddress(address);
			upi.getEzs_store().setCapitalPrice(Double.valueOf(capitalPrice));
			upi.getEzs_store().setUnifyCode(unifyCode);
			upi.getEzs_store().setPerson(persion);
			upi.setTrueName(trueName);
			upi.getEzs_store().setAccountType(1);
			//truename
			upi.setId(upi.getId());
			upi.setLastLoginDate(new Date());
			ezs_userMapper.updateByPrimaryKeySelective(upi);

			//企业个体信息
			upi.getEzs_store().setId(upi.getStore_id());
			ezs_storeMapper.updateByPrimaryKeySelective(upi.getEzs_store());

			boolean res = RedisUserSession.updateUserInfo(upi.getUserkey(), upi,
					Long.parseLong(redisuserkeyexpir));

			if (res) {
				result.setSuccess(true);
				result.setMsg("保存成功");
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			} else {
				result.setSuccess(false);
				result.setMsg("系统错误");
				result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			}
		}

		return result;
	}

	Result savecomvali(HttpServletRequest request) {

		Result result = Result.success();
		String companyName = request.getParameter("companyName");// 企业名称
		String area_id = request.getParameter("area_id");// 经营地址区县
		String address = request.getParameter("address");// 经营地址
		String capitalPrice = request.getParameter("capitalPrice");// 注册资本
		String unifyCode = request.getParameter("unifyCode");// 社会信用代码
		String persion = request.getParameter("persion");// 法人
		String trueName = request.getParameter("trueName");// 联系人
		// TODO 有效期至
		// String unifyCode = request.getParameter("unifyCode ");//社会信用代码

		if (Tools.isEmpty(companyName)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入公司名称");
			return result;
		}

		if (Tools.isEmpty(trueName)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入联系人");
			return result;
		}

		if (Tools.isEmpty(address)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入详细地址");
			return result;
		}
		if (Tools.isEmpty(unifyCode)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入社会信用代码");
			return result;
		}

		if (Tools.isEmpty(capitalPrice)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入注册资本");
			return result;
		}
		
		if (!Tools.isNum(capitalPrice)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入有效注册资本金额");
			return result;
		}
		
		if (Tools.isEmpty(persion)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入法人");
			return result;
		}

		return result;
	}

	@Override
	public Result saveComEin(Result result, HttpServletRequest request, ezs_user upi, HttpServletResponse response) {
		result = savecomeinvali(request);
		if (result.getSuccess()) {
			String companyName = request.getParameter("companyName");// 企业名称
			String dutyNo = request.getParameter("dutyNo");// 税号
			String number = request.getParameter("number");// 开户账号
			String phone = request.getParameter("phone");// 电话号码
			String address = request.getParameter("address");// 单位地址
			String bank = request.getParameter("bank");// 开户行

			ezs_bill ezs_bill=new com.sanbang.bean.ezs_bill();
			if (null != upi.getEzs_bill()&&!Tools.isEmpty(upi.getEzs_bill().getBank())) {
				upi.getEzs_bill().setId(upi.getEzs_bill().getId());
				upi.getEzs_bill().setUser_id(upi.getId());
				upi.getEzs_bill().setCompanyName(companyName);
				upi.getEzs_bill().setDutyNo(dutyNo);
				upi.getEzs_bill().setNumber(number);
				upi.getEzs_bill().setPhone(phone);
				upi.getEzs_bill().setAddress(address);
				upi.getEzs_bill().setBank(bank);
				upi.getEzs_store().setAccountType(1);
				ezs_billMapper.updateByPrimaryKeySelective(upi.getEzs_bill());
			} else {
				ezs_bill.setUser_id(upi.getId());
				ezs_bill.setCompanyName(companyName);
				ezs_bill.setDutyNo(dutyNo);
				ezs_bill.setNumber(number);
				ezs_bill.setDeleteStatus(false);
				ezs_bill.setPhone(phone);
				ezs_bill.setAddress(address);
				ezs_bill.setBank(bank);
				ezs_bill.setUser_id(upi.getId());
				ezs_bill.setAddTime(new Date());
				ezs_billMapper.insertSelective(ezs_bill);
				upi.setEzs_bill(ezs_bill);
			}
			
			//保存类型
			upi.getEzs_store().setAccountType(1);
			//upi.setEzs_bill(ezs_bill);
			ezs_storeMapper.updateByPrimaryKeySelective(upi.getEzs_store());
			
				boolean res = RedisUserSession.updateUserInfo(upi.getUserkey(), upi,
						Long.parseLong(redisuserkeyexpir));

				if (res) {
					result.setSuccess(true);
					result.setMsg("保存成功");
					result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				} else {
					result.setSuccess(false);
					result.setMsg("系统错误");
					result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
				}
			}

		return result;
	}

	Result savecomeinvali(HttpServletRequest request) {

		Result result = Result.success();

		String companyName = request.getParameter("companyName");// 企业名称
		String dutyNo = request.getParameter("dutyNo");// 税号
		String number = request.getParameter("number");// 开户账号
		String phone = request.getParameter("phone");// 电话号码
		String address = request.getParameter("address");// 单位地址
		String bank = request.getParameter("bank");// 开户行

		if (Tools.isEmpty(companyName)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入公司名称");
			return result;
		}
		if (Tools.isEmpty(dutyNo)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入税号");
			return result;
		}
		if (Tools.isEmpty(address)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入单位地址");
			return result;
		}
		if (Tools.isEmpty(phone)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入电话号码");
			return result;
		}
		if (Tools.isEmpty(bank)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入开户行");
			return result;
		}
		if (Tools.isEmpty(bank)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入开户账号");
			return result;
		}

		return result;
	}

	@Override
	public Result saveindivAuth(Result result, HttpServletRequest request, ezs_user upi, HttpServletResponse response) {
		result = saveindivvali(request);
		if (result.getSuccess()) {
			String companyName = request.getParameter("companyName");// 企业名称
			String trueName = request.getParameter("trueName");// 联系人
			String area_id = request.getParameter("area_id");// 经营地址区县
			String address = request.getParameter("address");// 经营地址
			String idCardNum = request.getParameter("idCardNum");// 经营者省份证号
			String account = request.getParameter("account");// 注册号
			String persion = request.getParameter("persion");// 经营者

			upi.getEzs_store().setCompanyName(companyName);
			upi.getEzs_store().setArea_id(Long.valueOf(area_id));
			upi.getEzs_store().setAddress(address);
			upi.getEzs_store().setPerson(persion);
			upi.setTrueName(trueName);
			upi.getEzs_store().setIdCardNum(idCardNum);
			upi.getEzs_store().setAccount(account);
			upi.getEzs_store().setAccountType(2);

			//truename
			upi.setId(upi.getId());
			upi.setLastLoginDate(new Date());
			ezs_userMapper.updateByPrimaryKeySelective(upi);

			//企业个体信息
			upi.getEzs_store().setId(upi.getStore_id());
			ezs_storeMapper.updateByPrimaryKeySelective(upi.getEzs_store());

			boolean res = RedisUserSession.updateUserInfo(upi.getUserkey(), upi,
					Long.parseLong(redisuserkeyexpir));

			if (res) {
				result.setSuccess(true);
				result.setMsg("保存成功");
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			} else {
				result.setSuccess(false);
				result.setMsg("系统错误");
				result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			}
		}
		return result;
	}

	@Override
	public Result saveindivEin(Result result, HttpServletRequest request, ezs_user upi, HttpServletResponse response) {
		result = saveindivEinvali(request);
		if (result.getSuccess()) {
			String companyName = request.getParameter("companyName");// 企业名称
			String dutyNo = request.getParameter("dutyNo");// 税号
			String number = request.getParameter("number");// 开户账号
			String phone = request.getParameter("phone");// 电话号码
			String address = request.getParameter("address");// 单位地址
			String bank = request.getParameter("bank");// 开户行

			ezs_bill ezs_bill=new com.sanbang.bean.ezs_bill();
			if (null != upi.getEzs_bill()&&!Tools.isEmpty(upi.getEzs_bill().getBank())) {
				upi.getEzs_bill().setId(upi.getEzs_bill().getId());
				upi.getEzs_bill().setUser_id(upi.getId());
				upi.getEzs_bill().setCompanyName(companyName);
				upi.getEzs_bill().setDutyNo(dutyNo);
				upi.getEzs_bill().setNumber(number);
				upi.getEzs_bill().setPhone(phone);
				upi.getEzs_bill().setAddress(address);
				upi.getEzs_bill().setBank(bank);
				upi.getEzs_store().setAccountType(1);
				ezs_billMapper.updateByPrimaryKeySelective(upi.getEzs_bill());
			} else {
				ezs_bill.setUser_id(upi.getId());
				ezs_bill.setCompanyName(companyName);
				ezs_bill.setDutyNo(dutyNo);
				ezs_bill.setNumber(number);
				ezs_bill.setDeleteStatus(false);
				ezs_bill.setPhone(phone);
				ezs_bill.setAddress(address);
				ezs_bill.setBank(bank);
				ezs_bill.setUser_id(upi.getId());
				ezs_bill.setAddTime(new Date());
				ezs_billMapper.insertSelective(ezs_bill);
				upi.setEzs_bill(ezs_bill);
			}
			
			//保存类型
			upi.getEzs_store().setAccountType(2);
			//upi.setEzs_bill(ezs_bill);
			ezs_storeMapper.updateByPrimaryKeySelective(upi.getEzs_store());
			
				boolean res = RedisUserSession.updateUserInfo(upi.getUserkey(), upi,
						Long.parseLong(redisuserkeyexpir));

				if (res) {
					result.setSuccess(true);
					result.setMsg("保存成功");
					result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				} else {
					result.setSuccess(false);
					result.setMsg("系统错误");
					result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
				}
			}
		return result;
	}

	Result saveindivEinvali(HttpServletRequest request) {

		Result result = Result.success();

		String companyName = request.getParameter("companyName");// 企业名称
		String dutyNo = request.getParameter("dutyNo");// 税号
		String number = request.getParameter("number");// 开户账号
		String phone = request.getParameter("phone");// 电话号码
		String address = request.getParameter("address");// 单位地址
		String bank = request.getParameter("bank");// 开户行

		if (Tools.isEmpty(companyName)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入公司名称");
			return result;
		}
		if (Tools.isEmpty(dutyNo)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入税号");
			return result;
		}
		if (Tools.isEmpty(address)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入单位地址");
			return result;
		}
		if (Tools.isEmpty(phone)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入电话号码");
			return result;
		}
		if (Tools.isEmpty(bank)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入开户行");
			return result;
		}
		if (Tools.isEmpty(number)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入开户账号");
			return result;
		}

		return result;
	}

	Result saveindivvali(HttpServletRequest request) {

		Result result = Result.success();
		String companyName = request.getParameter("companyName");// 企业名称
		String trueName = request.getParameter("trueName");// 联系人
		String area_id = request.getParameter("area_id");// 经营地址区县
		String address = request.getParameter("address");// 经营地址
		String idCardNum = request.getParameter("idCardNum");// 经营者省份证号
		String account = request.getParameter("account");// 注册号
		String persion = request.getParameter("persion");// 经营者
		// TODO 有效期至
		// String unifyCode = request.getParameter("unifyCode ");//社会信用代码

		if (Tools.isEmpty(companyName)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入公司名称");
			return result;
		}
		if (Tools.isEmpty(trueName)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入联系人");
			return result;
		}
		if (Tools.isEmpty(address)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入详细地址");
			return result;
		}
		if (Tools.isEmpty(account)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入注册号");
			return result;
		}

		if (Tools.isEmpty(idCardNum)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入经营者省份证号");
			return result;
		}
		if (Tools.isEmpty(persion)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入经营者");
			return result;
		}

		return result;
	}

	@Override
	public Result saveAuthInfo(Result result, HttpServletRequest request, ezs_user upi, HttpServletResponse response,
			String flag) {
		if ("company".equals(flag)) {
			result = saveCompanyAuth(result, request, upi);
		} else {
			result = saveDivAuth(result, request, upi);
		}
		ezs_store store=new ezs_store();
		store.setStatus(1);
		store.setAuditingusertype_id(dictService.getDictById(DictionaryCate.CRM_USR_TYPE_AUTHENTICATION).getId());
		store.setId(upi.getEzs_store().getId());
		store.setAccountType(upi.getEzs_store().getAccountType());
		store.setRegisterDate(new Date());
		ezs_storeMapper.updateByPrimaryKeySelective(store);
		if(result.getSuccess()){
			ezs_user upi1=ezs_userMapper.getUserInfoByUserNameFromBack(upi.getName()).get(0);
			RedisUserSession.updateUserInfo(upi.getUserkey(), upi1, Long.parseLong(redisuserkeyexpir));
		}
		return result;
	}
	
	
	private Result saveCompanyAuth (Result result,HttpServletRequest request, ezs_user upi){
		result.setSuccess(true);
		result.setMsg("提交成功");
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		//企业信息
		if(null==upi.getEzs_store().getCapitalPrice()||upi.getEzs_store().getCapitalPrice()==0){
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请完善企业基本信息");
			return result;
		}
		//企业信息
		if(null==upi.getEzs_bill()){
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请完善开票信息");
			return result;
		}
		//检查资质信息
		authPicCash(upi.getAuthimg(), upi);
		
		if(!upi.isAuthimgstate()){
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请完善资质信息");
			return result;
		}
		
		if(!upi.isAuthorfilestate()){
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请完善授权信息");
			return result;
		}
		
		return result;
	}
	
	
	private Result saveDivAuth(Result result,HttpServletRequest request, ezs_user upi){
		result.setSuccess(true);
		result.setMsg("提交成功");
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		//个体信息
		if(Tools.isEmpty(upi.getEzs_store().getAccount())){
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请完善个体基本信息");
		}
		//个体执照信息
		if(null==upi.getEzs_bill()){
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请完善开票信息");
		}
		//检查资质信息
		authPicCash(upi.getAuthimg(), upi);
		
		if(!upi.isAuthimgstate()){
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请完善资质信息");
		}
		
		if(!upi.isAuthorfilestate()){
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请完善授权信息");
		}
		
		return result;
	}
	
	/**
	 * 认证授权图片处理
	 * @param list
	 */
	private void authPicCash(List<AuthImageVo> list,ezs_user authupi){
		
		if(null==list||list.size()==0){
			authupi.setAuthorfilestate(false);
			authupi.setAuthimgstate(false);
			return ;
		}
		
		//企业授权
		if(!authupi.isAuthorfilestate()){
			int i=0;
			for (AuthImageVo authImageVo : list) {
				if(authImageVo.getImgcode().equals("LETTER_OF_AUTHORIZATION")
						||authImageVo.getImgcode().equals("LICENSEE_IDCARD")
						||authImageVo.getImgcode().equals("SHENGMING")){
				i++;	
				}
			}
			if(i==3){
				authupi.setAuthorfilestate(true);
			}
		}
		
		//1.企业账号，2.个体户
		int account=authupi.getEzs_store().getAccountType();
		if(1==account){
			authupi.setAuthimgstate(false);
			//企业授权
			if(!authupi.isAuthimgstate()){
				int i=0;
				for (AuthImageVo authImageVo : list) {
					if(authImageVo.getImgcode().equals("BUSLIC")
							||authImageVo.getImgcode().equals("ACCOUNT_OPENING_LICENSE")
							||authImageVo.getImgcode().equals("IDCARD_FONT")
							||authImageVo.getImgcode().equals("IDCARD_BACK")){
					i++;	
					}
				}
				if(i==4){
					authupi.setAuthimgstate(true);
				}
			}
		}else{
			authupi.setAuthimgstate(false);
			//企业授权
			if(!authupi.isAuthimgstate()){
				int i=0;
				for (AuthImageVo authImageVo : list) {
					if(authImageVo.getImgcode().equals("BUSLIC")
							||authImageVo.getImgcode().equals("ACCOUNT_OPENING_LICENSE")
							||authImageVo.getImgcode().equals("IDCARD_FONT")
							||authImageVo.getImgcode().equals("IDCARD_BACK")){
					i++;	
					}
				}
				if(i==4){
					authupi.setAuthimgstate(true);
				}
			}
		}
		
		
	}

	@Override
	public Result savePicUrl(Result result, HttpServletRequest request, ezs_user upi, HttpServletResponse response) {
		String zhizhao=request.getParameter("zhizhao");//,
		String shengfz=request.getParameter("shengfz");//,
		String qitazz=request.getParameter("qitazz");//; @ ,  type1,url1@name1@data1;type2,url2@name2@data2;  
		if(Tools.isEmpty(zhizhao)){
			result.setSuccess(false);
			result.setMsg("请完善执照信息");
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			return result;
		}
		if(upi.getEzs_store().getAccountType()==1){
			if(Tools.isEmpty(shengfz)){
				result.setSuccess(false);
				result.setMsg("请完善法人信息");
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);	
				return result;
			}
		}else{
			if(Tools.isEmpty(shengfz)){
				result.setSuccess(false);
				result.setMsg("请完善经营者信息");
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				return result;
			}
		}
		
		List<AuthImageVo> List=new ArrayList<>();
		try {
			List=savepic(zhizhao, List);
			List=savepic(shengfz, List);
			List=savepic(qitazz, List);
			if(null!=List&&List.size()>0){
			//资质信息
			for (AuthImageVo img : List) {
				ezs_accessory ezs_accessory=new com.sanbang.bean.ezs_accessory();
				if(null!=upi.getAuthimg()&&upi.getAuthimg().size()>0
						&&checkisExt(img.getImgcode(), upi.getAuthimg())){
					ezs_accessory.setName(FilePathUtil.getimageName(img.getImgurl()));
					ezs_accessory.setPath(FilePathUtil.getmiddelPath(img.getImgurl()));
					ezs_accessory.setId(checkisExtId(img.getImgcode(), upi.getAuthimg()));
					ezs_accessoryMapper.updateByPrimaryKeySelective(ezs_accessory);
				}else{
					ezs_accessory.setAddTime(new Date());
					ezs_accessory.setDeleteStatus(false);
					ezs_accessory.setExt("");
					ezs_accessory.setHeight(0);
					ezs_accessory.setInfo(null);
					ezs_accessory.setName(FilePathUtil.getimageName(img.getImgurl()));
					ezs_accessory.setPath(FilePathUtil.getmiddelPath(img.getImgurl()));
					ezs_accessory.setSize((float) 100);
					ezs_accessory.setWidth(100);
					ezs_accessory.setUser_id(upi.getId());
					ezs_accessoryMapper.insertSelective(ezs_accessory);
					
					//upi记录
					img.setAccid(ezs_accessory.getId());
					
					ezs_paper paper=new ezs_paper();
					paper.setAddTime(new Date());
					paper.setCertificate_id(ezs_accessory.getId());
					paper.setDeleteStatus(false);
					paper.setValidDate(img.getUsetime());
					paper.setPaperType(img.getImgcode());
					ezs_paperMapper.insertSelective(paper);
					
					ezs_card_dict card=new ezs_card_dict();
					card.setPaper_id(paper.getId());
					card.setStore_id(upi.getStore_id());
					ezs_card_dictMapper.insertSelective(card);
				}
				}
			}
			//保存类型
			ezs_storeMapper.updateByPrimaryKeySelective(upi.getEzs_store());
			
			List<AuthImageVo> imglist=ezs_accessoryMapper.getAuthImgListByStoreid(upi.getStore_id());
			upi.setAuthimg(imglist);
			boolean res = RedisUserSession.updateUserInfo(upi.getUserkey(), upi,
					Long.parseLong(redisuserkeyexpir));
			if (res) {
				result.setSuccess(true);
				result.setMsg("保存成功");
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			} else {
				result.setSuccess(false);
				result.setMsg("系统错误");
				result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		
		List<AuthImageVo> list=ezs_accessoryMapper.getAuthImgListByStoreid(upi.getStore_id());
		upi.setAuthimg(list);
		RedisUserSession.updateUserInfo(upi.getUserkey(), upi,
				Long.parseLong(redisuserkeyexpir));
		return result;
	}

	private  boolean checkisExt(String type,List<AuthImageVo> list){
		boolean status=false;
		for (AuthImageVo authImageVo : list) {
			if(authImageVo.getImgcode().equals(type)){
				status=true;
				break;
			}
		}
		return status;
	}
	
	private  long checkisExtId(String type,List<AuthImageVo> list){
		long id=0;
		for (AuthImageVo authImageVo : list) {
			if(authImageVo.getImgcode().equals(type)){
				id=authImageVo.getAccid();
				break;
			}
		}
		return id;
	}
	
	private static List<AuthImageVo> savepic(String param,List<AuthImageVo> list) throws ParseException{
		if(!Tools.isEmpty(param)){
		String[] aa=param.split(";");
		if(null==aa||aa.length==0){
			return list;
		}
		for (String bb : aa) {
			String[] cc=bb.split(",");
			if(null==cc||cc.length<2){
				return list;
			}
			AuthImageVo ImageVo=new AuthImageVo();
			ImageVo.setImgcode(cc[0]);
			
			if(cc[1].indexOf("@")>0&&cc[1].split("@").length==3){
				ImageVo.setImgurl(cc[1].split("@")[0]);
				ImageVo.setName(cc[1].split("@")[1]);
				ImageVo.setUsetime(sdf.parse(cc[1].split("@")[2]));
				list.add(ImageVo);
				System.out.println(ImageVo.toString());
			}else{
				ImageVo.setImgurl(cc[1].split("@")[0]);
				list.add(ImageVo);
				System.out.println(ImageVo.toString());
			}
		}
		}
		return list;
	}
	
	public static void main(String[] args) {
		List<AuthImageVo> list=new ArrayList<>();
		try {
			savepic("pic,url1;pic2,url2,",list );
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Result saveAuthPicUrl(Result result, HttpServletRequest request, ezs_user upi,
			HttpServletResponse response) {
		String shouquan=request.getParameter("shouquan");//,
		String sfzfyj=request.getParameter("sfzfyj");//,
		String shengming=request.getParameter("shengming");
		if(Tools.isEmpty(shouquan)){
			result.setSuccess(false);
			result.setMsg("请上传授权文件");
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			return result;
		}
		if(Tools.isEmpty(sfzfyj)){
			result.setSuccess(false);
			result.setMsg("请上传被授权人身份证复印件");
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			return result;
		}
		if(Tools.isEmpty(shengming)){
			result.setSuccess(false);
			result.setMsg("请上传声明文件");
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			return result;
		}
		List<AuthImageVo> List=new ArrayList<>();
		try {
			List=savepic(shouquan, List);
			List=savepic(sfzfyj, List);
			List=savepic(shengming, List);
			if(null!=List&&List.size()>0){
			//资质信息
			for (AuthImageVo img : List) {
				ezs_accessory ezs_accessory=new com.sanbang.bean.ezs_accessory();
				if(null!=upi.getAuthimg()&&upi.getAuthimg().size()>0
						&&checkisExt(img.getImgcode(), upi.getAuthimg())){
					ezs_accessory.setName(FilePathUtil.getimageName(img.getImgurl()));
					ezs_accessory.setPath(FilePathUtil.getmiddelPath(img.getImgurl()));
					ezs_accessory.setId(checkisExtId(img.getImgcode(), upi.getAuthimg()));
					ezs_accessoryMapper.updateByPrimaryKeySelective(ezs_accessory);
				}else{
					ezs_accessory.setAddTime(new Date());
					ezs_accessory.setDeleteStatus(false);
					ezs_accessory.setExt("");
					ezs_accessory.setHeight(0);
					ezs_accessory.setInfo(null);
					ezs_accessory.setName(FilePathUtil.getimageName(img.getImgurl()));
					ezs_accessory.setPath(FilePathUtil.getmiddelPath(img.getImgurl()));
					ezs_accessory.setSize((float) 100);
					ezs_accessory.setWidth(100);
					ezs_accessory.setUser_id(upi.getId());
					ezs_accessoryMapper.insertSelective(ezs_accessory);
					
					//upi记录
					img.setAccid(ezs_accessory.getId());
					
					ezs_paper paper=new ezs_paper();
					paper.setAddTime(new Date());
					paper.setCertificate_id(ezs_accessory.getId());
					paper.setDeleteStatus(false);
					paper.setValidDate(img.getUsetime());
					paper.setPaperType(img.getImgcode());
					ezs_paperMapper.insertSelective(paper);
					
					ezs_card_dict card=new ezs_card_dict();
					card.setPaper_id(paper.getId());
					card.setStore_id(upi.getStore_id());
					ezs_card_dictMapper.insertSelective(card);
				}
				}
			}
			//保存类型
			ezs_storeMapper.updateByPrimaryKeySelective(upi.getEzs_store());
			List<AuthImageVo> imglist=ezs_accessoryMapper.getAuthImgListByStoreid(upi.getStore_id());
			upi.setAuthimg(imglist);
			boolean res = RedisUserSession.updateUserInfo(upi.getUserkey(), upi,
					Long.parseLong(redisuserkeyexpir));
			if (res) {
				result.setSuccess(true);
				result.setMsg("保存成功");
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			} else {
				result.setSuccess(false);
				result.setMsg("系统错误");
				result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		
		return result;
	}

	@Override
	public Result saveHeadPicUrl(Result result, HttpServletRequest request, ezs_user upi,
			HttpServletResponse response) {
		String shouquan=request.getParameter("headimg");//,
		if(Tools.isEmpty(shouquan)){
			result.setSuccess(false);
			result.setMsg("请上传头像图片");
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			return result;
		}
		
		
		List<AuthImageVo> List=new ArrayList<>();
		try {
			List=savepic(shouquan, List);
			if(null!=List&&List.size()>0){
			//资质信息
			for (AuthImageVo img : List) {
				ezs_accessory ezs_accessory=new com.sanbang.bean.ezs_accessory();
				if(null!=upi.getAuthimg()&&upi.getAuthimg().size()>0
						&&checkisExt(img.getImgcode(), upi.getAuthimg())){
					ezs_accessory.setName(FilePathUtil.getimageName(img.getImgurl()));
					ezs_accessory.setPath(FilePathUtil.getmiddelPath(img.getImgurl()));
					ezs_accessory.setId(checkisExtId(img.getImgcode(), upi.getAuthimg()));
					ezs_accessoryMapper.updateByPrimaryKeySelective(ezs_accessory);
				}else{
					ezs_accessory.setAddTime(new Date());
					ezs_accessory.setDeleteStatus(false);
					ezs_accessory.setExt("");
					ezs_accessory.setHeight(0);
					ezs_accessory.setInfo(null);
					ezs_accessory.setName(FilePathUtil.getimageName(img.getImgurl()));
					ezs_accessory.setPath(FilePathUtil.getmiddelPath(img.getImgurl()));
					ezs_accessory.setSize((float) 100);
					ezs_accessory.setWidth(100);
					ezs_accessory.setUser_id(upi.getId());
					ezs_accessoryMapper.insertSelective(ezs_accessory);
					
					//upi记录
					img.setAccid(ezs_accessory.getId());
					
					ezs_paper paper=new ezs_paper();
					paper.setAddTime(new Date());
					paper.setCertificate_id(ezs_accessory.getId());
					paper.setDeleteStatus(false);
					paper.setValidDate(img.getUsetime());
					paper.setPaperType(img.getImgcode());
					ezs_paperMapper.insertSelective(paper);
					
					ezs_card_dict card=new ezs_card_dict();
					card.setPaper_id(paper.getId());
					card.setStore_id(upi.getStore_id());
					ezs_card_dictMapper.insertSelective(card);
				}
				}
			}
			//保存类型
			ezs_storeMapper.updateByPrimaryKeySelective(upi.getEzs_store());
			List<AuthImageVo> imglist=ezs_accessoryMapper.getAuthImgListByStoreid(upi.getStore_id());
			upi.setAuthimg(imglist);
			boolean res = RedisUserSession.updateUserInfo(upi.getUserkey(), upi,
					Long.parseLong(redisuserkeyexpir));
			if (res) {
				result.setSuccess(true);
				result.setMsg("保存成功");
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			} else {
				result.setSuccess(false);
				result.setMsg("系统错误");
				result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		
		return result;
	}
	
}
