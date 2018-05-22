package com.sanbang.statistic.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.DateFormatter;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_goodscart;
import com.sanbang.statistic.service.StatisticService;
import com.sanbang.utils.Result;

@Controller
@RequestMapping("/statistic")
public class StatisticController {
	
	private Logger log = Logger.getLogger(StatisticController.class);
	
	//查询数据统计时从前台传来的标识
	public static final String DAY = "day";     //按日统计
	public static final String MONTH = "month"; //按月统计
	
	@Autowired
	private StatisticService statisticService;
	
	@ResponseBody
	@RequestMapping("/queryStatisticMap")
	public Object queryStatisticMap(String startTime, String endTime, String dayOrMonthType){
		log.info("startTime = " + startTime + "   endTime = " + endTime + "   dayOrMonthType = " + dayOrMonthType);
		Result result = Result.success();
		List<Map<String, Object>> list = new ArrayList<>();
		
		List<ezs_goodscart> goodscartList = statisticService.selectDataForStatisticMap(dayOrMonthType);
		//查询销售额（万元）
 
		//{"2018-05":{"addTime":"2018-05";"amountOfSales":"3333";"count":"4444"}}
		//[{"addTime":"2018-05";"amountOfSales":"3333";"count":"4444"},{"addTime":"2018-05";"amountOfSales":"3333";"count":"4444"}]
		Map<String,Map<String,Object>> bigMap = new HashMap<>();			
		if(MONTH.equals(dayOrMonthType)){
            for(String addTime:getLastTwelveMonth()){
            	Map<String,Object> smallMap = new HashMap<>();
            	smallMap.put("addTime", addTime);
            	smallMap.put("amountOfSales", new BigDecimal("0"));
            	smallMap.put("count", 0);
            	bigMap.put(addTime, smallMap);
            }
		}
		
		for(ezs_goodscart goodscart : goodscartList){
			
			Map<String, Object> map = new HashMap<>();
			//按日统计提供三十天的数据
			if(DAY.equals(dayOrMonthType)){
				BigDecimal amountOfSales = getAmountOfSales(goodscart);
				map.put("amountOfSales", amountOfSales);
				map.put("count", goodscart.getCount());
				map.put("addTime", DateFormatUtils.format(goodscart.getAddTime(), "yyyy-MM-dd"));
				list.add(map);
				
			//按月统计提供十二个月的数据	
			}else if(MONTH.equals(dayOrMonthType)){
				BigDecimal amountOfSales = getAmountOfSales(goodscart);
				Date addTime = goodscart.getAddTime();
				
				//将这个月的数据放到相应的月份的map里，amountOfSales，count 同月相加
				Map<String,Object> smallMap = bigMap.get(DateFormatUtils.format(addTime, "yyyy-MM"));
				BigDecimal sumAmountOfSales = (BigDecimal)smallMap.get("amountOfSales");
				sumAmountOfSales = sumAmountOfSales.add(amountOfSales);
				
				//查询销售量（吨）
				Double sumCount = (Double)smallMap.get("count");
				sumCount+=goodscart.getCount();
				
				smallMap.put("amountOfSales", sumAmountOfSales);
				smallMap.put("count",sumCount);
				
			}
		}
		
		if(MONTH.equals(dayOrMonthType)){
			for(Map.Entry<String,Map<String,Object>> entry:bigMap.entrySet()){
				list.add(entry.getValue());
			}
		}
		
		//查询商品更新次数（次）
		
		return list; 
	}
	
	//返回一个list，里面放着最近的十二个月的日期，格式：yyyy-MM
	private static List<String> getLastTwelveMonth(){
		List list = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(calendar.MONTH, 1);
		
		for(int i=0; i < 12 ; i++){
			calendar.add(calendar.MONTH, -1);
			list.add(DateFormatUtils.format(calendar.getTime(),"yyyy-MM"));
		}
		return list;
		
	}

	//为传入的ezs_goodscart对象计算销售额
	private BigDecimal getAmountOfSales(ezs_goodscart goodscart) {

		Double count = goodscart.getCount();
		BigDecimal price = goodscart.getPrice();
		BigDecimal amountOfSales = new BigDecimal("0");
		if(count != 0 && price != new BigDecimal("0")){
			amountOfSales = new BigDecimal(count).multiply(price);
		}else{
			log.info("获取销售额时发现价格或者数量字段为0");
		}
		return amountOfSales;
	}

}
