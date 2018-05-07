package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_activity_goods;

@Repository
public interface ezs_activity_goodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_activity_goods record);

    int insertSelective(ezs_activity_goods record);

    ezs_activity_goods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_activity_goods record);

    int updateByPrimaryKey(ezs_activity_goods record);
}