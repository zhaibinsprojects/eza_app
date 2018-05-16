package com.sanbang.area.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.area.service.AreaService;
import com.sanbang.bean.ezs_area;
import com.sanbang.dao.ezs_areaMapper;

@Service("areaService")
public class AreaServiceImpl implements AreaService{

	@Autowired
	private ezs_areaMapper  ezs_areaMapper;
	
	@Override
	public List<ezs_area> getAreaParentList() {
		return ezs_areaMapper.getAreaParentList();
	}

	@Override
	public List<ezs_area> getAreaListByParId(long areaid) {
		return ezs_areaMapper.getAreaListByParId(areaid);
	}

	@Override
	public ezs_area getAreaById(long areaid) {
		return ezs_areaMapper.selectByPrimaryKey(areaid);
	}

}
