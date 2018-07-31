package com.sanbang.index.service.impl;

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
import com.sanbang.index.service.PriceConditionService;
import com.sanbang.upload.sevice.impl.FileUploadServiceImpl;
import com.sanbang.utils.Page;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.PriceTrendIfo;

@Service
public class PriceConditionServiceImpl implements PriceConditionService {
	private static Logger log = Logger.getLogger(PriceConditionServiceImpl.class);
	@Autowired
	private ezs_price_trendMapper priceTrendMapper; 
	@Autowired
	private ezs_columnMapper columnMapper;
	@Autowired
	private ezs_areaMapper areaMapper;
	
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
			mp.put("startPos", (currentPage - 1)*pagesaize);
			mp.put("pageSize", pagesaize);
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
				priceTrendIfo.setSandByOne(String.valueOf(increase));
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
}
