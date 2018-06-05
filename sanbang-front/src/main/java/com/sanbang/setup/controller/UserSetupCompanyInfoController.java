package com.sanbang.setup.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.area.service.AreaService;
import com.sanbang.bean.ezs_area;
import com.sanbang.bean.ezs_companyType_dict;
import com.sanbang.bean.ezs_industry_dict;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_areaMapper;
import com.sanbang.dao.ezs_companyType_dictMapper;
import com.sanbang.dao.ezs_industry_dictMapper;
import com.sanbang.dict.service.DictService;
import com.sanbang.userpro.service.UserProService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
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
	
	@Autowired
	private ezs_areaMapper ezs_areaMapper;
	
	@Autowired
	private ezs_industry_dictMapper ezs_industry_dictMapper;
	
	@Autowired
	private ezs_companyType_dictMapper ezs_companyType_dictMapper;
	
	
	
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
			Map<String,Object> map1=new HashMap<>();
			map1.put("companyName", upi.getEzs_store().getCompanyName());// 企业名称
			if(null!=upi.getEzs_store().getArea_id()){
				map.put("area_id",getaddressinfo(upi.getEzs_store().getArea_id()));// 经营地址区县
			}else{
				map.put("area_id",0);// 经营地址区县
			}
			if (null != upi.getEzs_store().getCompanyType_id() && upi.getEzs_store().getCompanyType_id() > 0) {
				map1.put("companyType_id",getezs_companyType_dict( ezs_companyType_dictMapper.getCompanyTypeByThisId(upi.getEzs_store().getId())));// 公司类型
			}else{
				map1.put("companyType_id",0);
			}
			
			if (null != upi.getEzs_store().getMianIndustry_id() && upi.getEzs_store().getMianIndustry_id() > 0) {
				map1.put("mianIndustry_id", getezs_industry_dict(ezs_industry_dictMapper.getIndustryByThisId(upi.getEzs_store().getId())));// 公司类型
			}else{
				map1.put("mianIndustry_id",0);
			}
			map1.put("address", upi.getEzs_store().getAddress());// 经营地址
			map1.put("yTurnover", upi.getEzs_store().getyTurnover());//// 年营业额
			map1.put("covered", upi.getEzs_store().getCovered());// 占地面积
			map1.put("rent", upi.getEzs_store().getRent());// 租用
			map1.put("device_num", upi.getEzs_store().getDevice_num());// 设备数量
			map1.put("employee_num", upi.getEzs_store().getEmployee_num());// 员工数量
			map1.put("fixed_assets", upi.getEzs_store().getFixed_assets());// 固定资产
			map1.put("obtainYear", upi.getEzs_store().getObtainYear());// 实际控制人从业年限
			
			map.put("cominfo", map1);
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
				twoinfo =  ezs_threeinfo.getAreaName();
				ezs_area ezs_oneinfo = ezs_areaMapper.selectByPrimaryKey(ezs_twoinfo.getParent_id());
				if (ezs_oneinfo != null) {
					oneinfo =  ezs_threeinfo.getAreaName();
				}
			}
		}
		
		if(!Tools.isEmpty(threeinfo)){
			sb = new StringBuilder().append(threeinfo);	
		}
		if(!Tools.isEmpty(twoinfo)){
			sb = new StringBuilder().append(twoinfo).append(threeinfo);
		}
		if(!Tools.isEmpty(oneinfo)){
			sb = new StringBuilder().append(oneinfo).append(twoinfo).append(threeinfo);
		}
		
		return sb.toString();
	}
	
	private String getezs_companyType_dict(List<ezs_companyType_dict> list){
		if(list.size()==0){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if(i!=0||i!=list.size()-1){
				sb.append("@");
			}
			sb.append(list.get(i).getDict_id());
		}
		return sb.toString();
		
	}
	
	private String getezs_industry_dict(List<ezs_industry_dict> list){
		if(list.size()==0){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if(i!=0||i!=list.size()-1){
				sb.append("@");
			}
			sb.append(list.get(i).getDict_id());
		}
		return sb.toString();
		
	}

}
