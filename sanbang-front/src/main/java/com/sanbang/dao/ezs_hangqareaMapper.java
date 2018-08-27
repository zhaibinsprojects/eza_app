package com.sanbang.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sanbang.vo.hangq.HangqAreaData;
import com.sanbang.vo.hangq.HangqDzAreaVo;
import com.sanbang.vo.hangq.HangqParamCommonVo;

@Repository("ezs_hangqareaMapper")
public interface ezs_hangqareaMapper {
	
	/**
	 * 再生料
	 * @param data_sources
	 * @param status
	 * @return
	 */
	List<HangqParamCommonVo>  getAreaBySourcesOrStatus(@Param("data_sources")String data_sources,@Param("status")int status);

	/**
	 * 新料
	 * @param data_sources
	 * @param status
	 * @return
	 */
	List<HangqParamCommonVo>  getHangqXlAreaList();
	
	/**
	 * 新料 再生料
	 * @param data_sources
	 * @param status
	 * @return
	 */
	List<HangqAreaData>  getHangqAllAreaList();
	
	
	/**
	 * 新料 再生料
	 * @param data_sources
	 * @param status
	 * @return
	 */
	List<HangqAreaData>  getHangqAllCityList(@Param("parentid")long parentid);
	
	
	/**
	 * 得到地区全称  和  大区
	 * @param areaids
	 * @return
	 */
	List<HangqDzAreaVo> getAreaNamesByAreaids(@Param("areaids")String areaids);
	
	List<HangqDzAreaVo>  getPriceTrendCitys(@Param("data_sources")String data_sources,@Param("status")int status);
	List<HangqDzAreaVo>  getPriceTrendXlCitys();
	
}