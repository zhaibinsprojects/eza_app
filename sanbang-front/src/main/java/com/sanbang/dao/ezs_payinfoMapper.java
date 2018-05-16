package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_payinfo;

@Repository
public interface ezs_payinfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_payinfo record);

    int insertSelective(ezs_payinfo record);

    ezs_payinfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_payinfo record);

    int updateByPrimaryKey(ezs_payinfo record);

	ezs_payinfo selectByBillId(Long billId);
}