package com.sanbang.index.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanbang.bean.ezs_customized;
import com.sanbang.bean.ezs_customized_record;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_customizedMapper;
import com.sanbang.dao.ezs_customized_recordMapper;
import com.sanbang.index.service.CustomizedService;
import com.sanbang.vo.DictionaryCode;

@Service
public class CustomizedServiceImpl implements CustomizedService {
	@Autowired
	private ezs_customizedMapper customizedMapper;
	@Autowired
	private ezs_customized_recordMapper customizedRecordMapper; 
	
	@Override
	public int insert(ezs_customized record) {
		return this.customizedMapper.insert(record);
	}

	@Override
	@Transactional
	public Map<String, Object> addCustomized(ezs_user user, ezs_customized customized,
			ezs_customized_record customizedRecord) {
		// TODO Auto-generated method stub
		int insertFlag01 = 0;
		int insertFlag02 = 0;
		Map<String, Object> mmp = new HashMap<>();
		try {
			customized.setAddTime(new Date());
			customized.setPurchaser_id(user.getId());
			customized.setDeleteStatus(false);
			insertFlag01 = this.customizedMapper.insert(customized);
			customizedRecord.setCustomized_id(customized.getId());
			customizedRecord.setAddTime(new Date());
			customizedRecord.setOperater_id(user.getId());
			customizedRecord.setPurchaser_id(user.getId());
			customizedRecord.setDeleteStatus(false);
			insertFlag02 = this.customizedRecordMapper.insert(customizedRecord);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Msg", "定制采购添加成功");
			if(insertFlag02<1){
				this.customizedMapper.deleteByPrimaryKey(customized.getId());
				mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
				mmp.put("Msg", "参数传递有误");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			if(insertFlag01>0&&insertFlag02<1){
				this.customizedMapper.deleteByPrimaryKey(customized.getId());
			}
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
		}
		return mmp;
	}
}
