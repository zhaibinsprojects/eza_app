package com.sanbang.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sanbang.bean.ezs_stock;

public interface ezs_stockMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_stock record);

    int insertSelective(ezs_stock record);

    ezs_stock selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_stock record);

    int updateByPrimaryKey(ezs_stock record);
    
    List<ezs_stock> getStockByGoods(@Param("goodId")Long goodId, @Param("ckType")int ckType);
}