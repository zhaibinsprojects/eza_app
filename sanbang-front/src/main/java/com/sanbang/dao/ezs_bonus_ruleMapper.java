package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_bonus_rule;

@Repository
public interface ezs_bonus_ruleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_bonus_rule record);

    int insertSelective(ezs_bonus_rule record);

    ezs_bonus_rule selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_bonus_rule record);

    int updateByPrimaryKey(ezs_bonus_rule record);
}