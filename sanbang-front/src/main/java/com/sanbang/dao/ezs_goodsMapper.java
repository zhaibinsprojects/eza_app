package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_goods;

@Repository
public interface ezs_goodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_goods record);

    int insertSelective(ezs_goods record);

    ezs_goods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_goods record);

    int updateByPrimaryKey(ezs_goods record);
}