package com.sanbang.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sanbang.bean.ezs_customizedhq;

public interface ezs_customizedhqMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_customizedhq record);

    int insertSelective(ezs_customizedhq record);

    ezs_customizedhq selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_customizedhq record);

    int updateByPrimaryKey(ezs_customizedhq record);
    
    /**
       * 查看我的定制
     * @param userid
     * @param ispush
     * @return
     */
    List<ezs_customizedhq>   getDingZhiListByParam(@Param("userid")long userid,@Param("ispush")boolean ispush,
    		@Param("pageCount")int pageCount,@Param("pageSize")long pageSize);
    
    
    int  getDingZhiListByParamCount(@Param("userid")long userid,@Param("ispush")boolean ispush);
    
    
}