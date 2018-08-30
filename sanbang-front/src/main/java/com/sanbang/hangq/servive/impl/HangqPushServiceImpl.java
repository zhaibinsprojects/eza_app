package com.sanbang.hangq.servive.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.maven.model.Model;
import org.springframework.beans.factory.annotation.Autowired;

import com.sanbang.bean.ezs_customized;
import com.sanbang.bean.ezs_customizedhq;
import com.sanbang.bean.ezs_ezssubstance;
import com.sanbang.dao.ezs_customizedhqMapper;
import com.sanbang.hangq.servive.HangqPushService;
import com.sanbang.index.service.IndustryInfoService;
import com.sanbang.index.service.PriceConditionService;
import com.sanbang.redis.RedisConstants;
import com.sanbang.redis.RedisResult;
import com.sanbang.utils.RedisUtils;
import com.sanbang.vo.Advices;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.GoodClassType;
import com.sanbang.vo.HangqHomeMess;
import com.sanbang.vo.PriceInTimesVo;
import com.sanbang.vo.PriceTrendIfo;
import com.sanbang.vo.ReportType;

public class HangqPushServiceImpl implements HangqPushService {

	private static SimpleDateFormat smf=new SimpleDateFormat("yyyy-MM-dd");
	
	private ezs_customizedhqMapper ezs_customizedhqMapper;
	@Autowired
	private IndustryInfoService industryInfoService;
	@Autowired
	private PriceConditionService priceConditionService;
	
	@Override
	public void getPushDate(HttpServletRequest request, String pushcode,Model model) throws Exception {
		@SuppressWarnings("unchecked")
		RedisResult<Map<String, Object>> tempCached=(RedisResult<Map<String, Object>>) RedisUtils.get(pushcode,Map.class);
		if(tempCached!=null&&tempCached.getCode()==RedisConstants.SUCCESS){
			//缓存中已经存在了  说明该用户已经登陆了
			Map<String, Object> pushrecode = tempCached.getResult();
			long id =Long.valueOf(String.valueOf(pushrecode.get("id")));
			Date pushdate=smf.parse(String.valueOf(pushrecode.get("pushtime")));
			ezs_customizedhq	dzrecode=ezs_customizedhqMapper.selectByPrimaryKey(id);
			long cataid=Long.valueOf(dzrecode.getCategory().split(",")[0]);
			long areaid=Long.valueOf(dzrecode.getAreaids().split(",")[0]);
			
		}else{
			throw new Exception("缓存记录不存在");
		}
	}

	//价格评析
		@SuppressWarnings("unchecked")
		public List<ezs_ezssubstance> getPriceAnalyse(int pageno, String kindId){
			List<ezs_ezssubstance> glist = null;
			List<ezs_ezssubstance> glistTemp = new ArrayList<>();
			Map<String, Object> mmp = this.industryInfoService.getAllIndustryInfoByParentKinds(Long.valueOf(12), pageno,3);
			Integer ErrorCode = (Integer) mmp.get("ErrorCode");
			if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
				glist = (List<ezs_ezssubstance>)mmp.get("Obj");
				for (ezs_ezssubstance ezss : glist) {
					ezs_ezssubstance ezssTemp = new ezs_ezssubstance();
					ezssTemp.setMeta(ezss.getMeta());
					ezssTemp.setName(ezss.getName());
					ezssTemp.setId(ezss.getId());
					glistTemp.add(ezssTemp);
				}
			}
			return glistTemp;
		}
		//研究报告
		@SuppressWarnings("unchecked")
		public List<ezs_ezssubstance> getPriceReport(int pageno, String kindId){
			List<ezs_ezssubstance> glist = null;
			List<ezs_ezssubstance> glistTemp = new ArrayList<>();
			Map<String, Object> mmp = this.industryInfoService.getAllIndustryInfoByParentKinds(Long.valueOf(17), pageno,3);
			Integer ErrorCode = (Integer) mmp.get("ErrorCode");
			if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
				glist = (List<ezs_ezssubstance>)mmp.get("Obj");
				for (ezs_ezssubstance ezss : glist) {
					ezs_ezssubstance ezssTemp = new ezs_ezssubstance();
					ezssTemp.setMeta(ezss.getMeta());
					ezssTemp.setName(ezss.getName());
					ezssTemp.setId(ezss.getId());
					glistTemp.add(ezssTemp);
				}
			}
			return glistTemp;
		}
		//头条
		@SuppressWarnings("unchecked")
		public List<ezs_ezssubstance> getTouTiao(){
			List<ezs_ezssubstance> glist = null;
			List<ezs_ezssubstance> glistTemp = new ArrayList<>();
			Map<String, Object> mmp = this.industryInfoService.getAllIndustryInfoByParentKinds(Long.valueOf(12),1,3);
			Integer ErrorCode = (Integer) mmp.get("ErrorCode");
			if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
				glist = (List<ezs_ezssubstance>)mmp.get("Obj");
				for (ezs_ezssubstance ezss : glist) {
					ezs_ezssubstance ezssTemp = new ezs_ezssubstance();
					ezssTemp.setMeta(ezss.getMeta());
					ezssTemp.setName(ezss.getName());
					ezssTemp.setId(ezss.getId());
					glistTemp.add(ezssTemp);
				}
			}
			return glistTemp;
		}
		//再生料-实时报价
		@SuppressWarnings("unchecked")
		public List<PriceTrendIfo> getPriceInTime(String kindId){
			List<PriceTrendIfo> plist = new ArrayList<>();
			Map<String, Object> mmp = this.priceConditionService.priceInTimeNew(Long.valueOf(kindId));
			Integer ErrorCode = (Integer) mmp.get("ErrorCode");
			if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
				plist = (List<PriceTrendIfo>)mmp.get("Obj");
			}
			return plist;
		}
		
		//新料-实时报价
		@SuppressWarnings("unchecked")
		public List<PriceTrendIfo> getPriceInTimeNew(String kindId){
			List<PriceTrendIfo> plist = new ArrayList<>();
			Map<String, Object> mmp = this.priceConditionService.priceInTimeNew2(Long.valueOf(kindId));
			Integer ErrorCode = (Integer) mmp.get("ErrorCode");
			if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
				plist = (List<PriceTrendIfo>)mmp.get("Obj");
			}
			return plist;
		}
}
