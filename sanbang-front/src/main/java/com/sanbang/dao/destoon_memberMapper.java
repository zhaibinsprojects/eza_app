package com.sanbang.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sanbang.bean.destoon_member;

public interface destoon_memberMapper {
    int deleteByPrimaryKey(Long userid);

    int insert(destoon_member record);

    int insertSelective(destoon_member record);

    destoon_member selectByPrimaryKey(Long userid);

    int updateByPrimaryKeySelective(destoon_member record);

    int updateByPrimaryKey(destoon_member record);
    
    List<destoon_member> selectdestoon_memberList(@Param("num")long num);
}