package com.sanbang.dao;

import com.sanbang.bean.ezs_return_logistics;

public interface ezs_return_logisticsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_return_logistics record);

    int insertSelective(ezs_return_logistics record);

    ezs_return_logistics selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_return_logistics record);

    int updateByPrimaryKey(ezs_return_logistics record);
    
    /**
     * 得到退货物流信息
     * @param returnid
     * @return
     */
    ezs_return_logistics selectReturnLogisticsForReturnNo(Long setorder_id);
}