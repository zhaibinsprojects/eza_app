package com.sanbang.index.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goods_class;
import com.sanbang.dao.ezs_goods_classMapper;
import com.sanbang.index.service.GoodsClassService;
import com.sanbang.vo.DictionaryCode;

@Service
public class GoodsClassServiceImpl implements GoodsClassService {
	
	@Autowired
	private ezs_goods_classMapper goodClassMapper;

	@Override
	public List<ezs_goods_class> queryGoodsClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ezs_goods_class queryByClassId(Long id) {
		// TODO Auto-generated method stub
		return this.goodClassMapper.selectByPrimaryKey(id);
	}

	@Override
	public ezs_goods_class queryByGoods(ezs_goods goods) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 查询所有产品分类（三级）
	 */
	@Override
	public Map<String, Object> queryAllGoodsClass() {
		Map<String, Object> mmp = new HashMap<>();
		List<ezs_goods_class> eslist = null;
		//按三级目录查询种类
		try {
			eslist = this.goodClassMapper.selectAllGoodClassByLevel("3");
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(eslist!=null){
			mmp.put("Obj", eslist);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		}else{
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_CODE_ERROR);
			mmp.put("Msg", "参数传递有误");
		}
		return mmp;
	}

}
