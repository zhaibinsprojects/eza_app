package com.sanbang.index.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.fabric.xmlrpc.base.Array;
import com.sanbang.bean.ezs_dict;
import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goods_class;
import com.sanbang.dao.ezs_dictMapper;
import com.sanbang.dao.ezs_goods_classMapper;
import com.sanbang.index.service.GoodsClassService;
import com.sanbang.upload.sevice.impl.FileUploadServiceImpl;
import com.sanbang.vo.CurrencyClass;
import com.sanbang.vo.DictionaryCode;

@Service
public class GoodsClassServiceImpl implements GoodsClassService {
	// 日志
	private static Logger log = Logger.getLogger(GoodsClassServiceImpl.class);
	@Autowired
	private ezs_goods_classMapper goodClassMapper;
	@Autowired
	private ezs_dictMapper dictMapper; 
	
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
			mmp.put("Obj", eslist);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
		}
		return mmp;
	}

	@Override
	public Map<String, Object> queryThirdGoodsClass(String level) {
		Map<String, Object> mmp = new HashMap<>();
		List<ezs_goods_class> eslist = null;
		//按三级目录查询种类
		try {
			log.info("Function:queryThirdGoodsClass,Msg:查询开始");
			eslist = this.goodClassMapper.selectAllGoodClassByLevel(level);
			mmp.put("Obj", eslist);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			log.info("Function:queryThirdGoodsClass,Msg:查询完成");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
			log.error(e.toString());
		}
		return mmp;
	}

	@Override
	public Map<String, Object> queryChildNodes() {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		List<ezs_goods_class> goodsClassList = null;
		try {
			goodsClassList = this.goodClassMapper.selectAllChildNodeExceptFirst();
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Obj", goodsClassList);
			mmp.put("Msg", "查询成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
			log.error(e.toString());
		}
		return mmp;
	}

	@Override
	public Map<String, Object> queryGoodColorAndForm() {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		List<CurrencyClass> colorList = null;
		List<CurrencyClass> formList = null;
		try {
			colorList = this.dictMapper.colorList();
			formList = this.dictMapper.formList();
			mmp.put("ColorList", colorList);
			mmp.put("FormList", formList);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Msg", "查询成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "查询失败");
		}
		return mmp;
	}

	@Override
	public Map<String, Object> queryGoodClassIncludePic() {
		Map<String, Object> mmp = new HashMap<>();
		List<ezs_goods_class> eslist = null;
		//按三级目录查询种类
		try {
			eslist = this.goodClassMapper.queryGoodClassIncludePhoto();
			mmp.put("Obj", eslist);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
		}
		return mmp;
	}
}
