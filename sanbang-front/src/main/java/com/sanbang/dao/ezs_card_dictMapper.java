package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_card_dict;

@Repository
public interface ezs_card_dictMapper {
    int insert(ezs_card_dict record);

    int insertSelective(ezs_card_dict record);
}