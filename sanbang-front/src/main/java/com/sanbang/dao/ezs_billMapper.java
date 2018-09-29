package com.sanbang.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_bill;

@Repository
public interface ezs_billMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_bill record);

    int insertSelective(ezs_bill record);

    ezs_bill selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_bill record);

    int updateByPrimaryKey(ezs_bill record);
    
    List<ezs_bill>  getBillsByUserId(@Param("userid")long userid);

}