package com.sanbang.analysis.service;

import java.util.List;

import com.sanbang.bean.ezs_goodscart;

public interface DataAnalyService {

	List<ezs_goodscart> selectDataAnalyByDay(String needate1);

	List<ezs_goodscart> selectDataAnalyByMonth(String needate2);

	List<ezs_goodscart> selectDataAnalyByCustom(String starttime, String endtime);
	
	

}
