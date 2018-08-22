package com.sanbang.hangq.servive;

import java.util.Map;

public interface HangqAreaService {
	
	/**
	 * 
	 * @param reqtype
	 * @return
	 */
	public Map<String, Object> getHangqParamDate(String reqtype,Map<String, Object> redate);
	
}
