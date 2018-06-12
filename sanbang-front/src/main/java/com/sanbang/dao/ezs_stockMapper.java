package com.sanbang.dao;

import com.sanbang.bean.ezs_stock;

public interface ezs_stockMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_stock record);

    int insertSelective(ezs_stock record);

    ezs_stock selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_stock record);

    int updateByPrimaryKey(ezs_stock record);
}