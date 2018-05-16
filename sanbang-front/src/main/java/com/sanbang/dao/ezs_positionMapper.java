package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_position;

@Repository
public interface ezs_positionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_position record);

    int insertSelective(ezs_position record);

    ezs_position selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_position record);

    int updateByPrimaryKey(ezs_position record);
    
    ezs_position selectByStoryid(Long storeid);
}