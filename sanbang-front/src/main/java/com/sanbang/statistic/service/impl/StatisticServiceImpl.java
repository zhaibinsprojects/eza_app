package com.sanbang.statistic.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_goodscart;
import com.sanbang.statistic.service.StatisticService;
import com.sanbang.dao.ezs_goodscartMapper;

@Service("StatisticService")
public class StatisticServiceImpl implements StatisticService{
    //日志
	private static Logger log = Logger.getLogger(StatisticServiceImpl.class.getName());
	
	@Autowired
	private ezs_goodscartMapper ezs_goodscartMapper;
	
	private static final String DAY = "day";
	private static final String MONTH = "month";
	
	@Override
	public List<ezs_goodscart> selectDataForStatisticMap(String dayOrMonthType) {
	
		Calendar cal = Calendar.getInstance();	
		cal.setTime(new Date());
		cal.add(cal.MONTH,1);
		Date startTime = cal.getTime();
		Date endTime = null;
		if(DAY.equals(dayOrMonthType)){
			cal.add(cal.DAY_OF_YEAR, -30);
			endTime = cal.getTime();
		}else if(MONTH.equals(dayOrMonthType)){
			cal.add(cal.MONTH, -12);
			endTime = cal.getTime();
		}
		log.info("startTime = " + DateFormatUtils.format(startTime,"yyyy-MM-dd HH:mm:ss") + "endTime = " + DateFormatUtils.format(endTime ,"yyyy-MM-dd HH:mm:ss"));
		//List<ezs_goodscart> list = ezs_goodscartMapper.selectBeanByStartEndTime(DateFormatUtils.format(startTime,"yyyy-MM-dd HH:mm:ss"),DateFormatUtils.format(endTime ,"yyyy-MM-dd HH:mm:ss"));
		List<ezs_goodscart> list = ezs_goodscartMapper.selectBeanByStartEndTime("2017-06-22 08:50:51",DateFormatUtils.format(endTime ,"yyyy-MM-dd HH:mm:ss"));
		
		return list;
	}

}
