package com.sanbang.statistic.service;

import java.util.List;

import com.sanbang.bean.ezs_goodscart;

public interface StatisticService {

	List<ezs_goodscart> selectDataForStatisticMap(String dayOrMonthType);

}
