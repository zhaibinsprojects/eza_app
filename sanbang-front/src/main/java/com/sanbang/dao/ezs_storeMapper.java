package com.sanbang.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_store;

@Repository
public interface ezs_storeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_store record);

    int insertSelective(ezs_store record);

    ezs_store selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_store record);

    int updateByPrimaryKeyWithBLOBs(ezs_store record);

    int updateByPrimaryKey(ezs_store record);
    
    /**
     * 得到公司名称
     * @param name
     * @return
     */
    List<ezs_store> getstoreInfoByName(@Param("companyName")String companyName);
    
    /**
     * 通过id获取store 集合
     * @param store_id
     * @return
     */
	ezs_store selectById(Long store_id);
    
}