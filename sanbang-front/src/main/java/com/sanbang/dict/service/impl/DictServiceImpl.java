package com.sanbang.dict.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_dict;
import com.sanbang.dao.ezs_dictMapper;
import com.sanbang.dict.service.DictService;


@Service("dictService")
public class DictServiceImpl implements DictService {

	@Autowired
	private ezs_dictMapper ezs_dictMapper;
	
	@Override
	public ezs_dict getDictById(String code) {
		return ezs_dictMapper.getDictById(code);
	}

	@Override
	public List<ezs_dict> getDictByParentId(String code) {
		return ezs_dictMapper.getDictByParentId(code);
	}

	@Override
	public ezs_dict getDictByThisId(long id) {
		return ezs_dictMapper.selectByPrimaryKey(id);
	}

	
	
}
