package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_idcard_dict;

@Repository
public interface ezs_idcard_dictMapper {
    int insert(ezs_idcard_dict record);

    int insertSelective(ezs_idcard_dict record);
}