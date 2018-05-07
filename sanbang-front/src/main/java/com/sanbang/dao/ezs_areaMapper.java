package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_area;

@Repository
public interface ezs_areaMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_area record);

    int insertSelective(ezs_area record);

    ezs_area selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_area record);

    int updateByPrimaryKey(ezs_area record);
}