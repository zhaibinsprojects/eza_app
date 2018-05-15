package com.sanbang.index.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		List<ezs_column> elist = this.columnMapper.getSecondThemeByFirstTheme(id);
		mmp.put("Obj", elist);
		mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		return mmp;
	}	
}
