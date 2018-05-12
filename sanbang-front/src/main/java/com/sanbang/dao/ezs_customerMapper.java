package com.sanbang.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_customer;

@Repository
public interface ezs_customerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_customer record);

    int insertSelective(ezs_customer record);

    ezs_customer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_customer record);

    int updateByPrimaryKey(ezs_customer record);
    //新增
    List<ezs_customer> selectByAreaHot();
}