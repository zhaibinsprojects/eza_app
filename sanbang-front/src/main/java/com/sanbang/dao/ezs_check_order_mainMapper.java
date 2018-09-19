package com.sanbang.dao;

import java.util.List;

import com.sanbang.bean.ezs_check_order_main;

public interface ezs_check_order_mainMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_check_order_main record);

    int insertSelective(ezs_check_order_main record);

    ezs_check_order_main selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_check_order_main record);

    int updateByPrimaryKey(ezs_check_order_main record);
    
    /**
     * 
     * @param orderno
     * @param usertype buyer seller
     * @return
     */
    ezs_check_order_main  getCheckOrderForOrderNO(String orderno);
    
}