package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_advert;

@Repository
public interface ezs_advertMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_advert record);

    int insertSelective(ezs_advert record);

    ezs_advert selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_advert record);

    int updateByPrimaryKey(ezs_advert record);
}