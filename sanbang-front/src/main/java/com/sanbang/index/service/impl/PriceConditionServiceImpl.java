package com.sanbang.index.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.cookie.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_column;
import com.sanbang.dao.ezs_columnMapper;
import com.sanbang.dao.ezs_price_trendMapper;
import com.sanbang.index.service.PriceConditionService;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.PriceTrendIfo;

@Service
public class PriceConditionServiceImpl implements PriceConditionService {

	@Autowired
	private ezs_price_trendMapper priceTrendMapper; 
	@Autowired
	private ezs_columnMapper columnMapper;
	
	@Override
	public Map<String, Object> getPriceInTime(Map<String, Object> mp) {
		Map<String, Object> mmp = new HashMap<>();
		List<PriceTrendIfo> plist = null;
		// TODO Auto-generated method stub
		try {
			plist = this.priceTrendMapper.selectByCondition(mp);
			if(plist!=null){
				mmp.put("Obj", plist);
				mmp.put("ErrorCode",DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			}
		} catch (Exception e) {
			// TODO: handle exception
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
	public Map<String, Object> getPriceTrendcy(Map<String, Object> mp) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		List<PriceTrendIfo> ppList = new ArrayList<>();
		List<PriceTrendIfo> pList = null;
		try {
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
		}else{
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_CODE_ERROR);
			mmp.put("Msg", "参数传递有误");
		}
		return mmp;
	}
}
