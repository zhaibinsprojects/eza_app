package com.sanbang.index.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_area;
import com.sanbang.bean.ezs_column;
import com.sanbang.dao.ezs_areaMapper;
import com.sanbang.dao.ezs_columnMapper;
import com.sanbang.dao.ezs_price_trendMapper;
import com.sanbang.dao.ezs_price_trend_xlMapper;
import com.sanbang.index.service.PriceConditionService;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.ExPage;
import com.sanbang.vo.PriceTrendIfo;

@Service
public class PriceConditionServiceImpl implements PriceConditionService {
	private static Logger log = Logger.getLogger(PriceConditionServiceImpl.class);
	@Autowired
	private ezs_price_trendMapper priceTrendMapper; 
	@Autowired
	private ezs_price_trend_xlMapper priceTrendXLMapper;
	@Autowired
	private ezs_columnMapper columnMapper;
	@Autowired
	private ezs_areaMapper areaMapper;
	/*百分数格式化*/
	private DecimalFormat dftwo = new DecimalFormat("0.00%");
	
	@Override
	public Map<String, Object> getPriceInTime(Map<String, Object> mp,int pageno,int pagesaize) {
		Map<String, Object> mmp = new HashMap<>();
		List<PriceTrendIfo> plist = new ArrayList<>();
		try {
			/*mp.put("pagecount", (pageno-1)*10);
			mp.put("pagesize", 10);*/
			//app 端做页面展示调整
		
			mp.put("pagecount", (pageno-1)*pagesaize);
			mp.put("pagesize", pagesaize);
			plist = this.priceTrendMapper.selectByAreaIdAndOtherCondition(mp);
			if(plist!=null){
				List<PriceTrendIfo> plistTemp = new ArrayList<>();
				for (PriceTrendIfo item : plist) {
					item.setGoodArea(getaddressinfo(item.getRegion_id()));
					plistTemp.add(item);
				}
				int totalCount=priceTrendMapper.getPriceConditionCount(mmp);
				ExPage page = new ExPage(totalCount, Integer.valueOf(pageno)); 
				page.setPageSize(10);
				mmp.put("Page", page);
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


	@Override
	public Map<String, Object> getSecondTheme(Long id) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		List<ezs_column> elist = null;
		try {
			elist = this.columnMapper.getSecondThemeByFirstTheme(id);
			mmp.put("Obj", elist);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);			
		} catch (Exception e) {
			// TODO: handle exception
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
		}
		return mmp;
	}
	
	//获取价格趋势信息
	@Override
	public Map<String, Object> getPriceTrendcy(Map<String, Object> mp,int currentPage,int pagesaize) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		List<PriceTrendIfo> ppList = new ArrayList<>();
		List<PriceTrendIfo> pList = null;
		try {
			//int totalCount = this.priceTrendMapper.getPriceConditionCount(mp);
			//Page page = new Page(totalCount, currentPage);
			/*mp.put("startPos", (currentPage - 1)*10);//每页起始位子
			mp.put("pageSize", 10);//页面大小*/
			//app 做页面展示调整
			//mp.put("startPos", (currentPage - 1)*pagesaize);
			//mp.put("pageSize", pagesaize);
			pList = this.priceTrendMapper.getPriceTrendcy(mp);			
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(pList!=null&&pList.size()>0){
			for (PriceTrendIfo priceTrendIfo : pList) {
				//涨幅
				Double increase = null;
				try {
					increase = (priceTrendIfo.getCurrentAVGPrice()-priceTrendIfo.getPreAVGPrice())/priceTrendIfo.getPreAVGPrice();				
				} catch (Exception e) {
					increase = 0.00;
				}
				//涨幅
				priceTrendIfo.setSandByOne(String.valueOf(Math.abs(increase)));
				priceTrendIfo.setIncreaseValue(increase);
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
	
	/**
	 * 获取地址名称：XX市
	 * @param areaId
	 * @return
	 */
	private String getAreaName(Long areaId){
		ezs_area areaTemp = this.areaMapper.selectByPrimaryKey(areaId);
		String areaName = areaTemp.getAreaName();
		while(areaTemp.getLevel()>1){
			areaTemp = this.areaMapper.selectByPrimaryKey(areaTemp.getParent_id());
			areaName = areaTemp.getAreaName();
		}
		return areaName;
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
		ezs_area ezs_threeinfo = areaMapper.selectByPrimaryKey(areaid);
		if (ezs_threeinfo != null) {
			threeinfo = ezs_threeinfo.getAreaName();
			ezs_area ezs_twoinfo = areaMapper.selectByPrimaryKey(ezs_threeinfo.getParent_id());
			if (ezs_twoinfo != null) {
				twoinfo =  ezs_twoinfo.getAreaName();
				ezs_area ezs_oneinfo = areaMapper.selectByPrimaryKey(ezs_twoinfo.getParent_id());
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

	@Override
	public Map<String, Object> priceInTimeNew(Long goodClassId) {
		Map<String, Object> mmp = new HashMap<>();
		List<PriceTrendIfo> priceInTimelist = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("0.000");
		DecimalFormat dftwo = new DecimalFormat("0.00%");
		try {
			priceInTimelist = this.priceTrendMapper.priceInTimeNew(goodClassId);
			if(priceInTimelist!=null){
				List<PriceTrendIfo> plistTemp = new ArrayList<>();
				for (PriceTrendIfo item : priceInTimelist) {
					item.setGoodArea(getAreaName(item.getRegion_id()));
					Double zf = 0.0;
					try{
						zf = (item.getCurrentPrice()-item.getPrePrice())/item.getPrePrice();
						item.setSandByOne(dftwo.format(zf));						
					}catch(Exception e){
						item.setSandByOne(String.valueOf(zf));
					}
					//item.setIncreaseValue(zf);
					item.setDealDate(item.getDealDate()!=null?item.getDealDate().substring(0, 10):"");
					plistTemp.add(item);
				}
				mmp.put("Obj", plistTemp);
				mmp.put("ErrorCode",DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				mmp.put("Msg", "查询成功");
			}else{
				priceInTimelist=new ArrayList<>();
				mmp.put("Obj", priceInTimelist);
				mmp.put("ErrorCode",DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				mmp.put("Msg", "查询成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
		}
		return mmp;
	}


	@Override
	public Map<String, Object> getPriceTrendcyNew(Map<String, Object> mp, int currentPage, int pagesaize) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		List<PriceTrendIfo> ppList = new ArrayList<>();
		List<PriceTrendIfo> pList = null;
		try {
			pList = this.priceTrendMapper.getPriceTrendcyNew(mp);			
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
	
	@Override
	public Map<String, Object> getPriceTrendcyNewPage(Map<String, Object> mp, int currentPage, int pagesaize) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		List<PriceTrendIfo> ppList = new ArrayList<>();
		List<PriceTrendIfo> pList = null;
		try {
			mp.put("pagestart", (currentPage-1)*pagesaize);
			mp.put("pagesize", pagesaize);
			pList = this.priceTrendMapper.getPriceTrendcyNewPage(mp);	
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
					//首页-走势app由这两个字段取值
					//priceTrendIfo.setCurrentPrice(Double.valueOf(currentavgprice));
					//priceTrendIfo.setPrePrice(Double.valueOf(preavgprice));
					
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


	@Override
	public Map<String, Object> priceInTimeNew2(Long goodClassId) {
		Map<String, Object> mmp = new HashMap<>();
		List<PriceTrendIfo> priceInTimelist = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("0.000");
		DecimalFormat dftwo = new DecimalFormat("0.00%");
		try {
			priceInTimelist = this.priceTrendXLMapper.priceInTimeNew(goodClassId);
			if(priceInTimelist!=null){
				List<PriceTrendIfo> plistTemp = new ArrayList<>();
				for (PriceTrendIfo item : priceInTimelist) {
					item.setGoodArea(getAreaName(item.getRegion_id()));
					Double zf = 0.0;
					try{
						zf = (item.getCurrentPrice()-item.getPrePrice())/item.getPrePrice();
						item.setSandByOne(dftwo.format(zf));						
					}catch(Exception e){
						item.setSandByOne(String.valueOf(zf));
					}
					//item.setIncreaseValue(zf);
					item.setDealDate(item.getDealDate()!=null?item.getDealDate().substring(0, 10):"");
					plistTemp.add(item);
				}
				mmp.put("Obj", plistTemp);
				mmp.put("ErrorCode",DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				mmp.put("Msg", "查询成功");
			}else{
				priceInTimelist=new ArrayList<>();
				mmp.put("Obj", priceInTimelist);
				mmp.put("ErrorCode",DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				mmp.put("Msg", "查询成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
		}
		return mmp;
	}


	@Override
	public Map<String, Object> getPriceInTimeNew(Map<String, Object> mp, int pageno, int pagesaize) {
		Map<String, Object> mmp = new HashMap<>();
		List<PriceTrendIfo> plist = new ArrayList<>();
		try {
			mp.put("pagestart", (pageno-1)*pagesaize);
			mp.put("pagesize", pagesaize);
			plist = this.priceTrendXLMapper.priceInTimeNewList(mp);
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
	@Override
	public Map<String, Object> getPriceInTimeOld(Map<String, Object> mp, int pageno, int pagesaize) {
		Map<String, Object> mmp = new HashMap<>();
		List<PriceTrendIfo> plist = new ArrayList<>();
		try {
			mp.put("pagestart", (pageno-1)*pagesaize);
			mp.put("pagesize", pagesaize);
			plist = this.priceTrendMapper.priceInTimeNewList(mp);
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


	@Override
	public Map<String, Object> priceInTimeNewDetail(Map<String, Object> mp) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		List<PriceTrendIfo> plist = new ArrayList<>();
		try {
			plist = this.priceTrendXLMapper.priceInTimeNewDetail(mp);
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


	@Override
	public Map<String, Object> priceInTimeNewDetailPage(Map<String, Object> mp, int currentPage, int pagesaize) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		List<PriceTrendIfo> plist = new ArrayList<>();
		mp.put("pagestart", (currentPage-1)*pagesaize);
		mp.put("pagesize", pagesaize);
		try {
			plist = this.priceTrendXLMapper.priceInTimeNewDetailPage(mp);
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


	@Override
	public Map<String, Object> priceInTimeOldDetail(Map<String, Object> mp) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		List<PriceTrendIfo> plist = new ArrayList<>();
		try {
			plist = this.priceTrendMapper.priceInTimeNewDetail(mp);
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


	@Override
	public Map<String, Object> priceInTimeOldDetailPage(Map<String, Object> mp, int currentPage, int pagesaize) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		List<PriceTrendIfo> plist = new ArrayList<>();
		mp.put("pagestart", (currentPage-1)*pagesaize);
		mp.put("pagesize", pagesaize);
		try {
			plist = this.priceTrendMapper.priceInTimeNewDetailPage(mp);
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
	
}
