package com.sanbang.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_order_info;
import com.sanbang.bean.ezs_orderform;
import com.sanbang.vo.PagerOrder;

@Repository
public interface ezs_orderformMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_orderform record);

    int insertSelective(ezs_orderform record);

    ezs_orderform selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_orderform record);

    int updateByPrimaryKeyWithBLOBs(ezs_orderform record);

    int updateByPrimaryKey(ezs_orderform record);
    
    //订单列表
    List<ezs_order_info>  getOrderListByValue(@Param("pager")PagerOrder pager);
    
    //订单总条数
    int getOrderListByValueCount(@Param("pager")PagerOrder pager);
    
    /**
     * 订单详情
     * @param order_no
     * @return
     */
    ezs_order_info getOrderListByOrderno(String order_no);
    
    /**
     * 订单详细信息
     * @param order_no
     * @return
     */
    ezs_orderform  selectByorderno(String order_no);
    
}