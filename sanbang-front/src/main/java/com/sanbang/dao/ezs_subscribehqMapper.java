package com.sanbang.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_subscribehq;

@Repository
public interface ezs_subscribehqMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_subscribehq record);

    int insertSelective(ezs_subscribehq record);

    ezs_subscribehq selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_subscribehq record);

    int updateByPrimaryKey(ezs_subscribehq record);
    
    /**
        * 用户订阅记录
     * @param userid
     * @return
     */
    List<ezs_subscribehq> getDingyueRecoudList(@Param("userid")long userid,
     		@Param("pageCount")int pageCount,@Param("pageSize")long pageSize);
    
     int getDingyueRecoudCount(@Param("userid")long userid);
     
     
     
}