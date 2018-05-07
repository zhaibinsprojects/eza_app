package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_sysconfig;
import com.sanbang.bean.ezs_sysconfigWithBLOBs;

@Repository
public interface ezs_sysconfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_sysconfigWithBLOBs record);

    int insertSelective(ezs_sysconfigWithBLOBs record);

    ezs_sysconfigWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_sysconfigWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ezs_sysconfigWithBLOBs record);

    int updateByPrimaryKey(ezs_sysconfig record);
}