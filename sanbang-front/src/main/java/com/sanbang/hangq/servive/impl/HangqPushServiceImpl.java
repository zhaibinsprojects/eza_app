package com.sanbang.hangq.servive.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_area;
import com.sanbang.bean.ezs_ezssubstance;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_areaMapper;
import com.sanbang.dao.ezs_goods_classVoMapper;
import com.sanbang.dao.ezs_price_trendMapper;
import com.sanbang.hangq.servive.HangqPushService;
import com.sanbang.index.service.IndustryInfoService;
import com.sanbang.index.service.PriceConditionService;
import com.sanbang.redis.RedisConstants;
import com.sanbang.redis.RedisResult;
import com.sanbang.utils.JiGuanPushUtils;
import com.sanbang.utils.Page;
import com.sanbang.utils.RedisUtils;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.PriceTrendIfo;
import com.sanbang.vo.goods.ezs_Dzgoods_classVo;
import com.sanbang.vo.goods.ezs_goods_classVo;

@Service("hangqPushService")
public class HangqPushServiceImpl implements HangqPushService {
	private Logger log = Logger.getLogger(HangqPushServiceImpl.class);
	
	private static SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private ezs_goods_classVoMapper ezs_goods_classVoMapper;
	@Autowired
	private PriceConditionService priceConditionService;
	@Autowired
	private IndustryInfoService industryInfoService;
	@Autowired
	private ezs_price_trendMapper ezs_price_trendMapper;
	@Autowired
	private ezs_areaMapper ezs_areaMapper;
	@Autowired
	private com.sanbang.dao.ezs_subscribehqMapper ezs_subscribehqMapper;
	/*百分数格式化*/
	private DecimalFormat dftwo = new DecimalFormat("0.00%");

	
	
	private static SimpleDateFormat smfh = new SimpleDateFormat("yyyy年MM月dd日");
	
	@Value("${consparam.ezaisheng.base.url}")
	private   String BASEURL;
	
	@SuppressWarnings("unchecked")
	@Override
	public Result getPushDate(HttpServletRequest request, String pushcode, Result result) {
		try {
			RedisResult<Result> tempCached = (RedisResult<Result>) RedisUtils.get(pushcode, Result.class);
			if (tempCached != null && tempCached.getCode() == RedisConstants.SUCCESS) {
				// 缓存中已经存在了 说明该用户已经登陆了
				Result re = tempCached.getResult();
				String push_key = (String) re.getObj();
				RedisResult<Result> thispush = (RedisResult<Result>) RedisUtils.get(push_key, Result.class);
				if (thispush != null && thispush.getCode() == RedisConstants.SUCCESS) {
					// 缓存中已经存在了 说明该用户已经登陆了
					Result re8 = thispush.getResult();

					// 当前用户所有推送记录
					LinkedHashMap<String, Object> oldList = new LinkedHashMap<String, Object>();
					oldList = (LinkedHashMap<String, Object>) re8.getObj();
					if (oldList == null || oldList.size() == 0) {
						throw new Exception();
					}
//					{	  pushtime=2018-08-31,
//					      title=您的行情定制推送(2018-08-31)已更新，点我查看, 
//					      account=6f8ae9c3587afa4e580d87bc668593a2, 
//					      ispush=0, 
//					      pushareaids=4525200,4525190, 
//					      pushcataids=33,38,32,,
//					      islook=0, 
//					      push_key=pushforapp561
//			       }
					// 当前推送记录
					Map<String, Object> pushrecode = (Map<String, Object>) oldList.get(pushcode);
					Date pushdate = smf.parse(String.valueOf(pushrecode.get("pushtime")));
					String[] cataids = String.valueOf(pushrecode.get("pushcataids")).split(",");
					String[] areaids = String.valueOf(pushrecode.get("pushareaids")).split(",");
					push_key = String.valueOf(pushrecode.get("push_key"));
					result = getThisPushDate(result, cataids, areaids, pushdate);

					// 更新用户推送记录列表
					int islook = (int) pushrecode.get("islook");
					if (islook == 0) {
						pushrecode.put("ispush", 1);// 是否推送
						pushrecode.put("islook", 1);// 是否查看
						oldList.put(pushcode, pushrecode);

						RedisUtils.del(push_key);
						RedisResult<String> rrt;
						Result re2 = Result.success();
						re2.setObj(oldList);
						rrt = (RedisResult<String>) RedisUtils.set(push_key, re2, (long) 0);
						if (rrt.getCode() == RedisConstants.SUCCESS) {
							log.debug("行情分类保存到redis成功执行");
						} else {
							log.debug("行情分类保存到redis失败");
						}
					}

				}
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

		Map<String, Object> ret = new HashMap<>();
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
					List<PriceTrendIfo> list = getPriceInTimeNew(cataid, areaid,pushdate);
					map.put("name",namevo.get(0).getName());
					map.put("list", list);
					listone.add(map);
				} else {
					// 再生料
					 map = new HashMap<>();
					Map<String, Object> map1 = new HashMap<>();
					List<PriceTrendIfo> list = getPriceInTime(cataid, areaid,pushdate);
					map.put("name",namevo.get(0).getName());
					map.put("list", list);
					listtwo.add(map);
					
					List<PriceTrendIfo> list2 = getPriceTrend(cataid, areaid,pushdate);
					map1.put("name",namevo.get(0).getName());
					map1.put("list", list2);
					listthree.add(map1);
				}

			}

		}
		// 报告
		List<ezs_ezssubstance> list4 = getPriceReport(1, "");
		ret.put("zsbj", listtwo);
		ret.put("xlbj",listone );
		ret.put("zszs", listthree);
		ret.put("yzbg", list4);
		ret.put("pushdate", smfh.format(pushdate));
		ret.put("weekday", Tools.getWeekOfDate(pushdate));

		result.setObj(ret);
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setSuccess(true);
		return result;
	}

	// 新料-实时报价
	@SuppressWarnings("unchecked")
	public List<PriceTrendIfo> getPriceInTimeNew(String kindId, String areaid,Date paramDate) {
		List<PriceTrendIfo> plist = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		List<String> areaIdsList = new ArrayList<>();
		areaIdsList.add(areaid);
		map.put("areaIds", areaIdsList);
		map.put("goodClassId", kindId);
		Map<String, Object> mmp = getPriceInTimeNew(map, 0, 100,paramDate);
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if (ErrorCode != null && ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)) {
			plist = (List<PriceTrendIfo>) mmp.get("Obj");
		}
		return plist;
	}

	
	
	public Map<String, Object> getPriceInTimeNew(Map<String, Object> mp, int pageno, int pagesaize,Date paramDate) {
		Map<String, Object> mmp = new HashMap<>();
		List<PriceTrendIfo> plist = new ArrayList<>();
		try {
			mp.put("pagestart", (pageno-1)*pagesaize);
			mp.put("pagesize", pagesaize);
			mp.put("paramDate", paramDate);
			plist = ezs_subscribehqMapper.priceInTimeNewXLList(mp);
			if(plist!=null){
				List<PriceTrendIfo> plistTemp = new ArrayList<>();
				for (PriceTrendIfo item : plist) {
					item.setGoodArea(getAreaName(item.getRegion_id()));
					item.setDealDate(item.getDealDate()!=null?item.getDealDate().substring(5, 10):"");
					Double zf = 0.0;
					try{
						zf = (item.getCurrentPrice()-item.getPrePrice())/item.getPrePrice();
						item.setSandByOne(dftwo.format(zf));						
					}catch(Exception e){
						item.setSandByOne(String.valueOf("0.00%"));
					}
					plistTemp.add(item);
				}
				mmp.put("Obj", plistTemp);
				mmp.put("ErrorCode",DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				mmp.put("Msg", "查询成功");
			}else{
				plist=new ArrayList<>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
		}
		return mmp;
	}
	
	
	// 再生料-实时报价
	@SuppressWarnings("unchecked")
	public List<PriceTrendIfo> getPriceInTime(String kindId, String areaid,Date paramDate) {
		Map<String, Object> map = new HashMap<>();
		List<String> areaIdsList = new ArrayList<>();
		areaIdsList.add(areaid);
		map.put("areaIds", areaIdsList);
		map.put("goodClassId", kindId);
		map.put("paramDate", paramDate);
		List<PriceTrendIfo> plist = new ArrayList<>();
		Map<String, Object> mmp = getPriceInTimeOld(map, 1, 50);
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if (ErrorCode != null && ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)) {
			plist = (List<PriceTrendIfo>) mmp.get("Obj");
		}
		return plist;
	}
	/**
	 * 再生料实时报价
	 * @param mp
	 * @param pageno
	 * @param pagesaize
	 * @return
	 */
	public Map<String, Object> getPriceInTimeOld(Map<String, Object> mp, int pageno, int pagesaize) {
		Map<String, Object> mmp = new HashMap<>();
		List<PriceTrendIfo> plist = new ArrayList<>();
		try {
			mp.put("pagestart", (pageno-1)*pagesaize);
			mp.put("pagesize", pagesaize);
			plist = ezs_subscribehqMapper.priceInTimeNewList(mp);
			if(plist!=null){
				List<PriceTrendIfo> plistTemp = new ArrayList<>();
				for (PriceTrendIfo item : plist) {
					item.setGoodArea(getAreaName(item.getRegion_id()));
					item.setDealDate(item.getDealDate()!=null?item.getDealDate().substring(5, 10):"");
					Double zf = 0.0;
					try{
						zf = (item.getCurrentPrice()-item.getPrePrice())/item.getPrePrice();
						item.setSandByOne(dftwo.format(zf));						
					}catch(Exception e){
						item.setSandByOne(String.valueOf("0.00%"));
					}
					plistTemp.add(item);
				}
				//int totalCount=priceTrendMapper.getPriceConditionCount(mp);
				//ExPage page = new ExPage(totalCount, Integer.valueOf(pageno)); 
				//page.setPageSize(10);
				//mmp.put("Page", page);
				mmp.put("Obj", plistTemp);
				mmp.put("ErrorCode",DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				mmp.put("Msg", "查询成功");
			}else{
				plist=new ArrayList<>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
		}
		return mmp;
	}
	
	
	
	
	// 价格趋势
	@SuppressWarnings("unchecked")
	public List<PriceTrendIfo> getPriceTrend(String kindId, String areaId,Date paramDate) {
		List<PriceTrendIfo> plist = null;
		// 返回數據容器
		Map<String, Object> mmp = new HashMap<>();
		// 查詢條件
		Map<String, Object> tMp = new HashMap<>();
		// 参数传递
		tMp.clear();
		tMp.put("paramDate", paramDate);
		tMp.put("kindId", kindId);
		List<String> areaIdsList = new ArrayList<>();
		areaIdsList.add(areaId);
		tMp.put("areaIds", areaIdsList);
		tMp.put("dateBetweenType", "WEEK");
		// 修改为取近一个月数据
		mmp = getPriceTrendcyNew(tMp, 1, 100);
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if (ErrorCode != null && ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)) {
			plist = (List<PriceTrendIfo>) mmp.get("Obj");
			// 对列表进行转向处理
			Collections.reverse(plist);
		}
		return plist;
	}
	
	public Map<String, Object> getPriceTrendcyNew(Map<String, Object> mp, int currentPage, int pagesaize) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		List<PriceTrendIfo> ppList = new ArrayList<>();
		List<PriceTrendIfo> pList = null;
		try {
			pList = ezs_subscribehqMapper.getPriceTrendcyNew(mp);			
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(pList!=null&&pList.size()>0){
			for (PriceTrendIfo priceTrendIfo : pList) {
				Double zf = 0.0;
				try{
					zf = (priceTrendIfo.getCurrentAVGPrice()-priceTrendIfo.getPreAVGPrice())/priceTrendIfo.getPreAVGPrice();
					priceTrendIfo.setSandByOne(dftwo.format(zf));						
				}catch(Exception e){
					priceTrendIfo.setSandByOne(String.valueOf("0.00%"));
				}
				try{
					//数据格式化
					//String currentavgprice = dfone.format(priceTrendIfo.getCurrentAVGPrice());
					//String preavgprice = dfone.format(priceTrendIfo.getPreAVGPrice());
					//priceTrendIfo.setCurrentAVGPrice(Double.valueOf(currentavgprice));
					//priceTrendIfo.setPreAVGPrice(Double.valueOf(preavgprice));
					BigDecimal currentavgpage = new BigDecimal(0);
					BigDecimal preavgpage = new BigDecimal(0);
					if(priceTrendIfo.getCurrentAVGPrice()!=null&&priceTrendIfo.getCurrentAVGPrice()!=0)
						currentavgpage = new BigDecimal(priceTrendIfo.getCurrentAVGPrice()).setScale(2, RoundingMode.UP);
					if(priceTrendIfo.getPreAVGPrice()!=null&&priceTrendIfo.getPreAVGPrice()!=0)
						preavgpage = new BigDecimal(priceTrendIfo.getPreAVGPrice()).setScale(2, RoundingMode.UP);
					
					priceTrendIfo.setCurrentAVGPrice(currentavgpage.doubleValue());
					priceTrendIfo.setPreAVGPrice(preavgpage.doubleValue());
					//首页-走势app由这两个字段取值
					priceTrendIfo.setCurrentPrice(currentavgpage.doubleValue());
					priceTrendIfo.setPrePrice(preavgpage.doubleValue());
				}catch(Exception e){
					//若上面转化异常，则直接取avg值放入price字段
					priceTrendIfo.setCurrentPrice(priceTrendIfo.getCurrentAVGPrice());
					priceTrendIfo.setPrePrice(priceTrendIfo.getPreAVGPrice());
					e.printStackTrace();
				}
				priceTrendIfo.setDealDate(priceTrendIfo.getDealDate()!=null?priceTrendIfo.getDealDate().substring(5, 10):"");
				//地址信息
				String areaName = getAreaName(priceTrendIfo.getRegion_id());
				priceTrendIfo.setGoodArea(areaName);
				ppList.add(priceTrendIfo);
			}
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Obj", ppList);
			mmp.put("Msg", "查询成功");
		}else{
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Obj", ppList);
			mmp.put("Msg", "未查询到数据");
		}
		return mmp;
	}

	// 研究报告
	@SuppressWarnings("unchecked")
	public List<ezs_ezssubstance> getPriceReport(int pageno, String kindId) {
		List<ezs_ezssubstance> glist = null;
		List<ezs_ezssubstance> glistTemp = new ArrayList<>();
		Map<String, Object> mmp = this.industryInfoService.getAllIndustryInfoByParentKinds(Long.valueOf(17), pageno, 3);
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if (ErrorCode != null && ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)) {
			glist = (List<ezs_ezssubstance>) mmp.get("Obj");
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

	@SuppressWarnings("unchecked")
	@Override
	public void handleHangqPush() {
		String needpushusers = "needpushusers";// 总记录标识
		Map<String, Object> zongpush = new HashMap<>();
		RedisResult<Result> reneedpushusers = (RedisResult<Result>) RedisUtils.get(needpushusers, Result.class);
		if (reneedpushusers.getCode() == RedisConstants.SUCCESS) {
			log.debug("redis查询用户推送记录成功");
			Result result = reneedpushusers.getResult();
			zongpush = (Map<String, Object>) result.getObj();
			for (Entry<String, Object> user : zongpush.entrySet()) {
				String push_key = user.getKey();// 用户标识

				Map<String, Object> push = (Map<String, Object>) user.getValue();// 用户推送记录
				// 是否注册极光
				int isopenpush = Integer.valueOf(String.valueOf(push.get("isopenpush")));
				Integer weidu = Integer.valueOf(String.valueOf(push.get("weidu")));
				Integer zongweidu = Integer.valueOf(String.valueOf(push.get("zongweidu")));

				if (0 == isopenpush) {
					continue;
				}
				List<String> needpush = new ArrayList<>();
				needpush = (List<String>) push.get("needpush");// 需推送记录key
				if (needpush == null) {
					continue;
				}

				// 每个用户推送记录容器
				LinkedHashMap<String, Object> oldList = new LinkedHashMap<String, Object>();
				
				
				List<String> li=new ArrayList<>();
				
			Iterator<String> iterretor=needpush.iterator();
			if(iterretor.hasNext()) {
				String thispushkey=iterretor.next();
				// 缓存获取用户推送记录列表
				RedisResult<Result> redate = (RedisResult<Result>) RedisUtils.get(push_key, Result.class);
				if (redate.getCode() == RedisConstants.SUCCESS) {
					log.debug("redis查询用户推送记录成功");
					Result re = redate.getResult();
					oldList = (LinkedHashMap<String, Object>) re.getObj();
					// 本次推送记录数据
					Map<String, Object> data = (Map<String, Object>) oldList.get(thispushkey);

					String title = String.valueOf(data.get("title"));// 标题
					String pushtime = String.valueOf(data.get("pushtime"));// 推送时间
					push_key = String.valueOf(data.get("push_key"));// 推送用户key(用户打开时用)
					String pushareaids = String.valueOf(data.get("pushareaids"));// 推送地区
					int ispush = Integer.valueOf(String.valueOf(data.get("ispush")));// 是否推送
					int islook = Integer.valueOf(String.valueOf(data.get("islook")));// 是否查看
					String account = String.valueOf(data.get("account"));// 推送标识
					Result result1 = Result.failure();
					if (islook == 1) {
						result1 = Result.success();
					} else {
						String pushurl = BASEURL+"app/hangq/dataShow/" + thispushkey + ".htm";
						log.info("开始推送，查看地址" + pushurl);
						result1 = JiGuanPushUtils.JiGangPushData(pushurl, account, title);
					}


					data.put("ispush", 1);// 是否推送
					//doneedpush.remove(doneedpush.indexOf(thispushkey));
					iterretor.remove();
					oldList.put(thispushkey, data);
					RedisUtils.del(push_key);
					RedisResult<String> rrt;
					Result c = Result.success();
					c.setObj(oldList);
					rrt = (RedisResult<String>) RedisUtils.set(push_key, c, (long) 0);
					if (rrt.getCode() == RedisConstants.SUCCESS) {
						log.debug("推送记录保存到redis成功执行");
					} else {
						log.debug("推送记录保存到redis失败");
					}

				

				}

				
				
			}
				
				push.put("weidu", weidu);
				push.put("zongweidu", zongweidu);
				push.put("isopenpush", 1);
				push.put("needpush", needpush);
				zongpush.put(push_key, push);
				RedisUtils.del(needpushusers);
				RedisResult<String> rrt;
				Result re = Result.success();
				re.setObj(zongpush);
				rrt = (RedisResult<String>) RedisUtils.set(needpushusers, re, (long) 0);
				if (rrt.getCode() == RedisConstants.SUCCESS) {
					log.debug("推送记录保存到redis成功执行");
				} else {
					log.debug("推送记录保存到redis失败");
				}

			}

		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public Result HangqMessagePage(int pageNo, ezs_user upi, Result result) {
		Map<String, Object> resm = new HashMap<>();
		List<Map<String, Object>> mlist = new ArrayList<>();
		try {
			// 每个用户唯一标识key
			String push_key = "pushforapp" + upi.getId();
			int startPosion = (pageNo - 1) * 10;
			int stopPositon = pageNo * 10;

			// 每个用户定时容器
			LinkedHashMap<String, Object> oldList = new LinkedHashMap<String, Object>();
			// 缓存获取用户推送记录列表
			RedisResult<Result> redate = (RedisResult<Result>) RedisUtils.get(push_key, Result.class);
			if (redate.getCode() == RedisConstants.SUCCESS) {
				log.debug("redis查询用户推送记录成功");
				Result re = redate.getResult();
				oldList = (LinkedHashMap<String, Object>) re.getObj();
				int i = 0;
				for (Entry<String, Object> info : oldList.entrySet()) {
					// 是否查看
					Map<String, Object> onedata = (Map<String, Object>) info.getValue();
					int islook = (int) onedata.get("islook");
					if (islook == 0 && (!resm.containsKey("isshow"))) {
						resm.put("isshow", 1);
					}
					if (i >= startPosion && i <= stopPositon) {
						String pushurl = BASEURL+"app/hangq/dataShow/" + info.getKey() + ".htm";
						onedata.put("requrl",pushurl );
						mlist.add(onedata);
					}
				}
			} else {
				resm.put("isshow", 0);
			}
			resm.put("mlist", mlist);
			result.setMeta(new Page(oldList.size(), 10));
			result.setSuccess(true);
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setObj(resm);
			result.setMsg("请求成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMeta(new Page(0, 10));
			result.setObj(resm);
			result.setMsg("请求失败");
		}

		return result;
	}
	
	
	
	
	
	
	/**
	 * 获取地址名称：XX市
	 * @param areaId
	 * @return
	 */
	private String getAreaName(Long areaId){
		ezs_area areaTemp = ezs_areaMapper.selectByPrimaryKey(areaId);
		String areaName = areaTemp.getAreaName();
		while(areaTemp.getLevel()>1){
			areaTemp = ezs_areaMapper.selectByPrimaryKey(areaTemp.getParent_id());
			areaName = areaTemp.getAreaName();
		}
		return areaName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result checkPushStatus(HttpServletRequest request, String pushcode, Result result) {
		Map<String, Object> resm = new HashMap<>();
		try {
			RedisResult<Result> tempCached = (RedisResult<Result>) RedisUtils.get(pushcode, Result.class);
			if (tempCached != null && tempCached.getCode() == RedisConstants.SUCCESS) {
				// 缓存中已经存在了 说明该用户已经登陆了
				Result re = tempCached.getResult();
				String push_key = (String) re.getObj();
				RedisResult<Result> thispush = (RedisResult<Result>) RedisUtils.get(push_key, Result.class);
				if (thispush != null && thispush.getCode() == RedisConstants.SUCCESS) {
					// 缓存中已经存在了 说明该用户已经登陆了
					Result re8 = thispush.getResult();

					// 当前用户所有推送记录
					LinkedHashMap<String, Object> oldList = new LinkedHashMap<String, Object>();
					oldList = (LinkedHashMap<String, Object>) re8.getObj();
					if (oldList == null || oldList.size() == 0) {
						throw new Exception();
					}
					// 当前推送记录
					for (Entry<String, Object> info : oldList.entrySet()) {
						// 是否查看
						Map<String, Object> onedata = (Map<String, Object>) info.getValue();
						int islook = (int) onedata.get("islook");
						if (islook == 0 && (!resm.containsKey("isshow"))) {
							resm.put("isshow", 1);
						}
					}
				} else {
					resm.put("isshow", 0);
				}
				
				result.setSuccess(true);
				result.setMsg("请求成功");
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				result.setObj(resm);
			} else {
				result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
				result.setSuccess(false);
				result.setMsg("链接已失效");
				resm.put("isshow", 0);
				result.setObj(resm);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("链接已失效");
			resm.put("isshow", 0);
			result.setObj(resm);

		}
		return result;
	}
}
