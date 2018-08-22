package com.sanbang.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sanbang.vo.hangq.HangqParamCommonVo;

@Repository("ezs_hangqareaMapper")
public interface ezs_hangqareaMapper {
	
	/**
	 * 
	 * @param data_sources
	 * @param status
	 * @return
	 */
	List<HangqParamCommonVo>  getAreaBySourcesOrStatus(@Param("data_sources")String data_sources,@Param("status")int status);

	/**
	 * 得到地区全称  和  大区
	 * @param areaids
	 * @return
	 */
	List<HangqParamCommonVo> getAreaNamesByAreaids(@Param("areaids")String areaids);
	
}