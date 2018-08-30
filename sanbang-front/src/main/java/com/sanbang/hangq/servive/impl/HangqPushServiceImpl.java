package com.sanbang.hangq.servive.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.sanbang.bean.ezs_ezssubstance;
import com.sanbang.dao.ezs_goods_classVoMapper;
import com.sanbang.hangq.servive.HangqPushService;
import com.sanbang.index.service.IndustryInfoService;
import com.sanbang.index.service.PriceConditionService;
import com.sanbang.redis.RedisConstants;
import com.sanbang.redis.RedisResult;
import com.sanbang.utils.RedisUtils;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.PriceTrendIfo;
import com.sanbang.vo.goods.ezs_Dzgoods_classVo;
import com.sanbang.vo.goods.ezs_goods_classVo;

public class HangqPushServiceImpl implements HangqPushService {

	private static SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private ezs_goods_classVoMapper ezs_goods_classVoMapper;
	@Autowired
	private PriceConditionService priceConditionService;
	@Autowired
	private IndustryInfoService industryInfoService;

	@Override
	public Result getPushDate(HttpServletRequest request, String pushcode, Result result) {
		try {
			@SuppressWarnings("unchecked")
			RedisResult<Map<String, Object>> tempCached = (RedisResult<Map<String, Object>>) RedisUtils.get(pushcode,
					Map.class);
			if (tempCached != null && tempCached.getCode() == RedisConstants.SUCCESS) {
				// 缓存中已经存在了 说明该用户已经登陆了
				Map<String, Object> pushrecode = tempCached.getResult();
				Date pushdate = smf.parse(String.valueOf(pushrecode.get("pushtime")));

				String[] cataids = String.valueOf(pushrecode.get("pushcataids")).split(",");
				String[] areaids = String.valueOf(pushrecode.get("pushareaids")).split(",");

				result = getThisPushDate(result, cataids, areaids, pushdate);
			} else {
				result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
				result.setSuccess(false);
				result.setMsg("链接已失效");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("链接已失效");

		}
		return result;
	}

	private Result getThisPushDate(Result result, String cataids[], String[] areaids, Date pushdate) throws Exception {

		Map<String, Object> ret =new HashMap<>();
		List<Map<String, Object>> listone = new ArrayList<>();// 再生报价
		List<Map<String, Object>> listtwo = new ArrayList<>();// 新料报价
		List<Map<String, Object>> listthree = new ArrayList<>();// 再生走势

		for (String cataid : cataids) {
			ezs_goods_classVo cclass = ezs_goods_classVoMapper.selectByPrimaryKey(Long.valueOf(cataid));

			List<ezs_Dzgoods_classVo> namevo = ezs_goods_classVoMapper
					.getClassNamesByclasses(String.valueOf(cclass.getId()));

			if (namevo.size() == 0) {
				continue;
			}

			for (String areaid : areaids) {
				Map<String, Object> map = new HashMap<>();
				if (cclass.getDisplay()) {
					// 新料
					List<PriceTrendIfo> list = getPriceInTimeNew(cataid, areaid);
					map.put(namevo.get(0).getName(), list);
					listone.add(map);
				} else {
					// 再生料
					Map<String, Object> map1 = new HashMap<>();
					List<PriceTrendIfo> list = getPriceInTime(cataid, areaid);
					map.put(namevo.get(0).getName(), list);
					listtwo.add(map);
					map1.put(namevo.get(0).getName(), list);
					List<PriceTrendIfo> list2 = getPriceTrend(cataid, areaid);
					map1.put(namevo.get(0).getName(), list2);
					listthree.add(map1);
				}

			}
			
		}
		//报告
		List<ezs_ezssubstance> list4=getPriceReport(1, "");
		ret.put("zsbj", listone);
		ret.put("xlbj", listtwo);
		ret.put("zszs", listthree);
		ret.put("yzbg", list4);
		
		result.setObj(ret);
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setSuccess(true);
		return result;
	}

	// 新料-实时报价
	@SuppressWarnings("unchecked")
	public List<PriceTrendIfo> getPriceInTimeNew(String kindId, String areaid) {
		List<PriceTrendIfo> plist = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("areaIds", areaid);
		map.put("goodClassId", kindId);
		Map<String, Object> mmp = this.priceConditionService.getPriceInTimeNew(map, 1, 50);
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if (ErrorCode != null && ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)) {
			plist = (List<PriceTrendIfo>) mmp.get("Obj");
		}
		return plist;
	}

	// 再生料-实时报价
	@SuppressWarnings("unchecked")
	public List<PriceTrendIfo> getPriceInTime(String kindId, String areaid) {
		Map<String, Object> map = new HashMap<>();
		map.put("areaIds", areaid);
		map.put("goodClassId", kindId);
		List<PriceTrendIfo> plist = new ArrayList<>();
		Map<String, Object> mmp = this.priceConditionService.getPriceInTimeOld(map, 1, 50);
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if (ErrorCode != null && ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)) {
			plist = (List<PriceTrendIfo>) mmp.get("Obj");
		}
		return plist;
	}

	// 价格趋势
	@SuppressWarnings("unchecked")
	public List<PriceTrendIfo> getPriceTrend(String kindId, String areaId) {
		List<PriceTrendIfo> plist = null;
		// 返回數據容器
		Map<String, Object> mmp = new HashMap<>();
		// 查詢條件
		Map<String, Object> tMp = new HashMap<>();
		// 参数传递
		tMp.clear();
		tMp.put("kindId", kindId);
		tMp.put("areaId", areaId);
		tMp.put("dateBetweenType", "WEEK");
		// 修改为取近一个月数据
		mmp = this.priceConditionService.getPriceTrendcyNew(tMp, 1, 100);
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if (ErrorCode != null && ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)) {
			plist = (List<PriceTrendIfo>) mmp.get("Obj");
			// 对列表进行转向处理
			Collections.reverse(plist);
		}
		return plist;
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
}
