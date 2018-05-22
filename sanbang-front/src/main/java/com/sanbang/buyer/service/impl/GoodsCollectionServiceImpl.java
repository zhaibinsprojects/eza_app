package com.sanbang.buyer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_goods;
import com.sanbang.buyer.service.GoodsCollectionService;
import com.sanbang.dao.ezs_documentshareMapper;
import com.sanbang.dao.ezs_goodsMapper;
import com.sanbang.dao.ezs_price_trendMapper;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.PriceTrendIfo;

@Service
public class GoodsCollectionServiceImpl implements GoodsCollectionService {
	@Autowired
	private ezs_documentshareMapper documentshareMapper;
	@Autowired
	private ezs_goodsMapper goodsMapper; 
	@Autowired
	private ezs_price_trendMapper price_trendMapper; 

	@Override
	public Map<String, Object> getCollectGoodsByUser(Long userId) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		List<ezs_goods> glist = null;
		try {
			glist = this.goodsMapper.getGoodsFromCollection(userId);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Obj", glist);
		} catch (Exception e) {
			// TODO: handle exception
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递异常");
		}
		return mmp;
	}
	/**
	 *已存在，暂不开发此方法
	 */
	@Override
	public Map<String, Object> addGoodToCollection(Long Id,Long userIed) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		Map<String, Object> mp = new HashMap<>();
		mp.put("gId", Id);
		mp.put("userId",userIed);
		
		return mmp;
	}

	@Override
	public Map<String, Object> removeGoodFromCollect(Long gId,Long userId) {
		Map<String, Object> mmp = new HashMap<>();
		Map<String, Object> mp = new HashMap<>();
		mp.put("gId", gId);
		mp.put("userId", userId);
		try{
			this.documentshareMapper.updateCollectByUserId(mp);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Msg", "移除成功");
		}catch(Exception e){
			e.printStackTrace();
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数异常");
		}
		return mmp;
	}

	@Override
	public Map<String, Object> addGoodCart(Long gId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Map<String, Object> selectPriceChanges(Long gId) {
		Map<String, Object> mmp = new HashMap<>();
		List<PriceTrendIfo> plist = null; 
		List<PriceTrendIfo> dealPList = new ArrayList<>();
		try {
			plist = this.price_trendMapper.selectPriceChangesByGood(gId);
 			mmp.put("Obj", plist);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Msg", "查询成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
		}
		return mmp;
	}

}
