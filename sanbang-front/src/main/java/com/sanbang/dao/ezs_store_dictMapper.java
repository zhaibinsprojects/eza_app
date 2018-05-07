package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_store_dict;

@Repository
public interface ezs_store_dictMapper {
    int insert(ezs_store_dict record);

    int insertSelective(ezs_store_dict record);
}