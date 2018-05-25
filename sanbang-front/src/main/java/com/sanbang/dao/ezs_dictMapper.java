package com.sanbang.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_dict;

public interface ezs_dictMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_dict record);

    int insertSelective(ezs_dict record);

    ezs_dict selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_dict record);

    int updateByPrimaryKey(ezs_dict record);
    
    /**
	  * 得到字典表数据通过name
	  * @param id
	  * @return
	  */
	 ezs_dict  getDictById(@Param("code")String code);
	 
	 /**
	  * 得到字典表数据通过父name
	  * @param id
	  * @return
	  */
	 List<ezs_dict>  getDictByParentId(@Param("code")String code);
	 
	 List<ezs_dict> conditionList();
	 
}