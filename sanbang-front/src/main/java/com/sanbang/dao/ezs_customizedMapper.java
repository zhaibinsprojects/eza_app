package com.sanbang.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sanbang.bean.ezs_customized;

public interface ezs_customizedMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_customized record);

    int insertSelective(ezs_customized record);

    ezs_customized selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_customized record);

    int updateByPrimaryKey(ezs_customized record);
    
    List<ezs_customized> customizedList(Long user_id);

    //add by zhaibin
    List<ezs_customized> getPurchaseByUserId(@Param("userId")Long userId,@Param("pageCount")int pageCount,@Param("pageSize")int pageSize);
}