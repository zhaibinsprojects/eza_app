package com.sanbang.analysis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.analysis.service.DataAnalyService;
import com.sanbang.bean.ezs_goodscart;
import com.sanbang.dao.ezs_goodscartMapper;

@Service
public class DataAnalyServiceImpl implements DataAnalyService {
	
	@Autowired
	private ezs_goodscartMapper ezs_goodscartMapper;
	
	

	@Override
	public List<ezs_goodscart> selectDataAnalyByDay(String needate1) {
		
		return ezs_goodscartMapper.selectByDay(needate1);
	}



	@Override
	public List<ezs_goodscart> selectDataAnalyByMonth(String needate2) {
		
		return ezs_goodscartMapper.selectByMonth(needate2);
	}



	@Override
	public List<ezs_goodscart> selectDataAnalyByCustom(String starttime, String endtime) {
		
		return ezs_goodscartMapper.selectByCustom(starttime,endtime);
	}
	
	
	
	
	

}
