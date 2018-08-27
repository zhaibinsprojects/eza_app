package com.sanbang.hangq.servive;

import java.util.List;
import java.util.Map;

import com.sanbang.vo.hangq.HangqAreaData;

public interface HangqAreaService {
	
	/**
	 * 
	 * @param reqtype
	 * @return
	 */
	public Map<String, Object> getHangqParamDate(String reqtype,Map<String, Object> redate);
	
	
	public List<HangqAreaData> getAreaData();
	
}
