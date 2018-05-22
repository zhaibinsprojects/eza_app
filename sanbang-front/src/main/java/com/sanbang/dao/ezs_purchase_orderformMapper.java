package com.sanbang.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_purchase_orderform;
import com.sanbang.utils.Page;

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

	List<ezs_purchase_orderform> queryOrders(@Param("page")Page page, @Param("userId")Long userId, @Param("orderType")String orderType, @Param("orderStatus")Integer orderStatus);
}