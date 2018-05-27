package com.sanbang.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_order_info;
import com.sanbang.bean.ezs_purchase_orderform;
import com.sanbang.utils.Page;
import com.sanbang.vo.PagerOrder;

@Repository
public interface ezs_purchase_orderformMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_purchase_orderform record);

    int insertSelective(ezs_purchase_orderform record);

    ezs_purchase_orderform selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_purchase_orderform record);

    int updateByPrimaryKeyWithBLOBs(ezs_purchase_orderform record);

    int updateByPrimaryKey(ezs_purchase_orderform record);

	int selectCount(Long userId);

	int getOrderListByValueCount(@Param("pager")PagerOrder pager);

	List<ezs_order_info> getOrderListByValue(@Param("pager")PagerOrder pager);

	ezs_order_info getOrderListByOrderno(String order_no);

	ezs_purchase_orderform selectByOrderNo(String order_no);
}