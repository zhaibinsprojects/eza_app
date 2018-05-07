package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_introduce_code;

@Repository
public interface ezs_introduce_codeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_introduce_code record);

    int insertSelective(ezs_introduce_code record);

    ezs_introduce_code selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_introduce_code record);

    int updateByPrimaryKey(ezs_introduce_code record);
}