package com.sanbang.dao;

import java.util.List;

import com.sanbang.bean.ReturnOrder;

public interface ReturnOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ReturnOrder record);

    int insertSelective(ReturnOrder record);

    ReturnOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReturnOrder record);

    int updateByPrimaryKey(ReturnOrder record);
    
    //新增
    List<ReturnOrder> selectListByState();
    
}