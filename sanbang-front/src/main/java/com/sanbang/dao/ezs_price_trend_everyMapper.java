package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_price_trend_every;

@Repository
public interface ezs_price_trend_everyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_price_trend_every record);

    int insertSelective(ezs_price_trend_every record);

    ezs_price_trend_every selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_price_trend_every record);

    int updateByPrimaryKey(ezs_price_trend_every record);
}