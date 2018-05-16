package com.sanbang.index.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_goods;
import com.sanbang.dao.ezs_goodsMapper;
import com.sanbang.index.service.RecommendGoodsService;
import com.sanbang.utils.Page;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.HomeDictionaryCode;

@Service
public class RecommendGoodsServiceImpl implements RecommendGoodsService {
	
	@Autowired
	private ezs_goodsMapper goodsMapper;

	@Override
	public ezs_goods queryById(Long id) {
		// TODO Auto-generated method stub
		return this.goodsMapper.selectByPrimaryKey(id);
	}

	@Override
	public Map<String, Object> queryByName(String name) {
		Map<String, Object> mmp = new HashMap<>();
		List<ezs_goods> glist = null;
		glist = this.goodsMapper.selectByGoodName(name);
		mmp.put("Obj", glist);
		return mmp;
	}

	@Override
	public void addGoods(ezs_goods goods) {
		// TODO Auto-generated method stub
		this.goodsMapper.insert(goods);

	}

	@Override
	public void modifyGoodsById(ezs_goods goods) {
		// TODO Auto-generated method stub
		this.goodsMapper.updateByPrimaryKeySelective(goods);
	}

	@Override
	public void dropGoodsById(ezs_goods goods) {
		// TODO Auto-generated method stub
		this.goodsMapper.deleteByPrimaryKey(goods.getId());
	}

	@Override
	public Map<String, Object> goodsIntroduce(String currentPage) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		//获取总页数
		int totalCount = this.goodsMapper.goodsIntroduceCount();
		Page page = new Page(totalCount, Integer.valueOf(currentPage));
		page.setPageSize(10);
		if(Integer.valueOf(currentPage)>=1||Integer.valueOf(currentPage)<=page.getTotalPageCount()){
			List<ezs_goods> glist = this.goodsMapper.goodsIntroduce(page);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Page", page);
			mmp.put("Obj", glist);
		}else{
			mmp.put("ErrorCode", HomeDictionaryCode.ERROR_HOME_PAGE_FAIL);
			mmp.put("Page", page);
		}
		return mmp;
	}

}
