package com.sanbang.dict.service;

import java.util.List;

import com.sanbang.bean.ezs_dict;

public interface DictService {
	
	 /**
	  * 得到字典表数据通过name
	  * @param id
	  * @return
	  */
	 ezs_dict  getDictById(String name);
	 
	 
	 /**
	  * 得到字典表数据通过父name
	  * @param id
	  * @return
	  */
	 List<ezs_dict>  getDictByParentId(String parname);
	 
	
	 
	 ezs_dict  getDictByThisId(long id);
}