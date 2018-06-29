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
        
    ezs_area selectParentByChildKey(Long pId);
    
    
    ezs_area selectParentByareaname(String areaName);
    
    //根据地区名称返回id   add by han
    List<Long> areaToId(String areaName);
    
    //查询省下的市，或市下的区县
    List<Long> queryChildId(Long area);
    
    //一堆市下的所有区县
    List<Long> queryChildIds(List<Long> listId);
    
    
    List<ezs_area> getAreasByParentId(@Param("areaid")long areaid);
    
}