package com.sanbang.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sanbang.bean.ezs_dict;
import com.sanbang.vo.CurrencyClass;

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
	 
	 
	 List<CurrencyClass> colorList();
	 
	 List<CurrencyClass> formList();

	 /**
	  * 通过商品id或的属性
	  */
	String selectPropertyById(Long propertyId);
	/**
	 * 通过auditingusertype_id 查询用户类型
	 * @param auditingusertype_id
	 * @return
	 */
	String selectAuditingById(Long auditingusertype_id);
	
}