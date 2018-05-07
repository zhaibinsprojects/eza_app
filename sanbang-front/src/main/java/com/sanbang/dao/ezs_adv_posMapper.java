package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_adv_pos;
import com.sanbang.bean.ezs_adv_posWithBLOBs;

@Repository
public interface ezs_adv_posMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_adv_posWithBLOBs record);

    int insertSelective(ezs_adv_posWithBLOBs record);

    ezs_adv_posWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_adv_posWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ezs_adv_posWithBLOBs record);

    int updateByPrimaryKey(ezs_adv_pos record);
}