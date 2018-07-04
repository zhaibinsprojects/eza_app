package com.sanbang.index.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_goods;
import com.sanbang.dao.ezs_accessoryMapper;
import com.sanbang.dao.ezs_documentshareMapper;
import com.sanbang.dao.ezs_goodsMapper;
import com.sanbang.index.service.RecommendGoodsService;
import com.sanbang.upload.sevice.impl.FileUploadServiceImpl;
import com.sanbang.utils.Page;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.GoodsInfo;
import com.sanbang.vo.HomeDictionaryCode;
import com.sanbang.vo.goods.GoodsVo;

@Service
public class RecommendGoodsServiceImpl implements RecommendGoodsService {
	private static Logger log = Logger.getLogger(RecommendGoodsServiceImpl.class);
	@Autowired
	private ezs_goodsMapper goodsMapper;
	@Autowired
	private ezs_accessoryMapper accessoryMapper;
	@Autowired
	private ezs_documentshareMapper documentshareMapper;

	@Override
	public ezs_goods queryById(Long id) {
		// TODO Auto-generated method stub
		return this.goodsMapper.selectByPrimaryKey(id);
	}

	@Override
	public Map<String, Object> queryByName(String name) {
		Map<String, Object> mmp = new HashMap<>();
		List<GoodsInfo> glist = null;
		try {
			glist = this.goodsMapper.selectByGoodName(name);			
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(glist!=null){
			mmp.put("Obj", glist);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		}else{
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
		}
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
		if((Integer.valueOf(currentPage)>=1&&Integer.valueOf(currentPage)<=page.getTotalPageCount())||(page.getTotalPageCount()==0)){
			List<GoodsInfo> glist = this.goodsMapper.goodsIntroduce(page);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Page", page);
			mmp.put("Obj", glist);
		}else{
			mmp.put("ErrorCode", HomeDictionaryCode.ERROR_HOME_PAGE_FAIL);
			mmp.put("Msg", "暂无数据");
			mmp.put("Page", page);
		}
		return mmp;
	}

	@Override
	public Map<String, Object> goodsIntroduceTwo(String currentPage) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		//获取总页数
		int totalCount = this.goodsMapper.goodsIntroduceCount();
		Page page = new Page(totalCount, Integer.valueOf(currentPage));
		page.setPageSize(10);
		if((Integer.valueOf(currentPage)>=1&&Integer.valueOf(currentPage)<=page.getTotalPageCount())||(page.getTotalPageCount()==0)){
			List<GoodsInfo> glist = this.goodsMapper.goodsIntroduceTwo(page);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Page", page);
			mmp.put("Obj", glist);
		}else{
			mmp.put("ErrorCode", HomeDictionaryCode.ERROR_HOME_PAGE_FAIL);
			mmp.put("Msg", "暂无数据");
			mmp.put("Page", page);
		}
		return mmp;
	}

	@Override
	public Map<String, Object> getGoodsInCollectionByName(String goodsName,Long userId) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		List<ezs_goods> goodsInfoList = null;
		try {
			goodsInfoList = this.goodsMapper.getGoodsInCollectionByName("%"+goodsName+"%",userId);
			//数据结构修改
			
			mmp.put("Obj", goodsInfoList);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Msg", "查询成功");
			log.info("查询成功");
		} catch (Exception e) {
			// TODO: handle exception
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "查询失败");
			log.error("查询失败："+e);
		}
		return mmp;
	}
}
