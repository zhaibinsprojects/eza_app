package com.sanbang.analysis.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.analysis.service.DataAnalyService;
import com.sanbang.bean.ezs_goodscart;
import com.sanbang.utils.DateUtils;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;

@Controller
@RequestMapping("/goodscart")
public class DataAnalyController {
	
	@Autowired
	private DataAnalyService dataAnalyService;
	
	/**
	 * 数据统计
	 * @param searchType (按日:day;按月:month;自定义搜索:custom)
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping("/analysis")
	@ResponseBody
	public Result getDataAnalyCharts(String searchType,HttpServletRequest request){
			Result result = new Result().success();
			List<ezs_goodscart> goodscartList = null;
		if("day".equals(searchType)){
			
			 goodscartList = dataAnalyService.selectDataAnalyByDay(DateUtils.getNeedate1(-29,"yyyy-MM-dd"));			
		}else if("month".equals(searchType)){
			
			 goodscartList = dataAnalyService.selectDataAnalyByMonth(DateUtils.getNeedate2(-11,"yyyy-MM"));
		}else if("custom".equals(searchType)){
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			if(Tools.compare_date(starttime, endtime)!=1){
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("请选择正确的时间");
				result.setSuccess(false);
				return result;
			}	
			goodscartList = dataAnalyService.selectDataAnalyByCustom(starttime,endtime);
		}
		result.setObj(goodscartList);
		return result;
	}
	
	
	
	
	
	
	

}
