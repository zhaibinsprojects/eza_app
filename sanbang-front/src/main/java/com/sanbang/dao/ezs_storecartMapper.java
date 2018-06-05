package com.sanbang.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.alibaba.druid.stat.TableStat.Condition;
import com.sanbang.bean.ezs_storecart;
import com.sanbang.vo.QueryCondition;

@Repository
public interface ezs_storecartMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_storecart record);

    int insertSelective(ezs_storecart record);

    ezs_storecart selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_storecart record);

    int updateByPrimaryKey(ezs_storecart record);
    
    List<ezs_storecart> getByCondition(QueryCondition queryCondition);
    
    List<ezs_storecart> getByUserId(QueryCondition queryCondition);
}