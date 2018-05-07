package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_remark;

@Repository
public interface ezs_remarkMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_remark record);

    int insertSelective(ezs_remark record);

    ezs_remark selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_remark record);

    int updateByPrimaryKey(ezs_remark record);
}