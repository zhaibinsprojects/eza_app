package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_activity;
import com.sanbang.bean.ezs_activityWithBLOBs;

@Repository
public interface ezs_activityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_activityWithBLOBs record);

    int insertSelective(ezs_activityWithBLOBs record);

    ezs_activityWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_activityWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ezs_activityWithBLOBs record);

    int updateByPrimaryKey(ezs_activity record);
}