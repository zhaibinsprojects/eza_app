package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_integrallog;

@Repository
public interface ezs_integrallogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_integrallog record);

    int insertSelective(ezs_integrallog record);

    ezs_integrallog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_integrallog record);

    int updateByPrimaryKeyWithBLOBs(ezs_integrallog record);

    int updateByPrimaryKey(ezs_integrallog record);
}