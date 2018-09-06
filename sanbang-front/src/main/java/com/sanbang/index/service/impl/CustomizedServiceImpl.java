package com.sanbang.index.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanbang.bean.ezs_customized;
import com.sanbang.bean.ezs_customized_record;
import com.sanbang.bean.ezs_dict;
import com.sanbang.bean.ezs_goods_class;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_customizedMapper;
import com.sanbang.dao.ezs_customized_recordMapper;
import com.sanbang.dao.ezs_dictMapper;
import com.sanbang.dao.ezs_goods_classMapper;
import com.sanbang.index.service.CustomizedService;
import com.sanbang.vo.DictionaryCode;

@Service
public class CustomizedServiceImpl implements CustomizedService {
	
	private Logger log=Logger.getLogger(CustomizedServiceImpl.class);
	
	@Autowired
	private ezs_customizedMapper customizedMapper;
	@Autowired
	private ezs_customized_recordMapper customizedRecordMapper; 
	@Autowired
	private ezs_goods_classMapper goodsclassMapper;
	@Autowired
	private ezs_dictMapper dictMapper;
	
	@Override
	public int insert(ezs_customized record) {
		return this.customizedMapper.insert(record);
	}

	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
	public Map<String, Object> addCustomized(ezs_user user, ezs_customized customized,
			ezs_customized_record customizedRecord) {
		Map<String, Object> mmp = new HashMap<>();
		try {
			//初始化必需字段数据
			customized.setAddTime(new Date());
			customized.setPurchaser_id(user.getId());
			customized.setRootParentId(user.getId());
			customized.setDeleteStatus(false);
			customized.setStatus("0");
			customized.setSourceType("1");//来源类型:0是预购  1是采购
			//goodsClass.getParent().getName()+"-"+goodsClass.getName()+"-"+color.getName()+"-"+shape.getName()
			customized.setProName(getProName(customized.getCategory_id(),customized.getColour_id(),customized.getShape_id()));
			this.customizedMapper.insertSelective(customized);
			log.info("定制：返回id"+customized.getId());
			//初始化必需字段数据
			customizedRecord.setRemark("您的需求已经提交成功");
			customizedRecord.setCustomized_id(customized.getId());
			customizedRecord.setAddTime(new Date());
			customizedRecord.setOperater_id(user.getId());
			customizedRecord.setPurchaser_id(user.getId());
			customizedRecord.setFlagMsg(false);
			customizedRecord.setDeleteStatus(false);
			this.customizedRecordMapper.insertSelective(customizedRecord);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Msg", "定制采购添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
			throw e;
		}
		return mmp;
	}
	/**
	 * 根据第三级分类查询出第二级分类
	 * @param goodClassId
	 * @return
	 */
	@SuppressWarnings("unused")
	private ezs_goods_class getRootGoodClass(Long goodClassId){
		ezs_goods_class goodclass = this.goodsclassMapper.selectByPrimaryKey(goodClassId);
		if(goodclass!=null){
			String level = goodclass.getLevel();
			while (goodclass.getLevel()!=null&&Long.valueOf(level)>2) {
				goodclass = this.goodsclassMapper.queryRootGoodClass(goodClassId);
				level = goodclass.getLevel();
			}
		}
		return goodclass;
	}
	
	private String getProName(Long goodClassId,Long colorId,Long shapeId){
		ezs_goods_class rootGoodClass = getRootGoodClass(goodClassId);
		ezs_goods_class goodClass = this.goodsclassMapper.selectByPrimaryKey(goodClassId);
		ezs_dict color = this.dictMapper.selectByPrimaryKey(colorId);
		ezs_dict shape = this.dictMapper.selectByPrimaryKey(shapeId);
		String rootGoodClassName = rootGoodClass.getName()+"-"+goodClass.getName()+"-"+color.getName()+"-"+shape.getName();
		return rootGoodClassName; 
	}
	
}
