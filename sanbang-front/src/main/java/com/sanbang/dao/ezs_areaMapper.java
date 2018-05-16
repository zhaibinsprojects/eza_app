package com.sanbang.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_area;
import com.sanbang.vo.HotProvince;

@Repository
public interface ezs_areaMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_area record);

    int insertSelective(ezs_area record);

    ezs_area selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_area record);

    int updateByPrimaryKey(ezs_area record);
    
    /**
     * 得到一级数据
     * @return
     */
    List<ezs_area> getAreaParentList();
    
    /**
     * 得到子子级数据
     * @return
     */
    List<ezs_area> getAreaListByParId(@Param("areaid")long areaid);
    
    List<HotProvince> getHotArea(); 
}