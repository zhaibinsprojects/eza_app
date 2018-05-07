package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_goods_audit_process;

@Repository
public interface ezs_goods_audit_processMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_goods_audit_process record);

    int insertSelective(ezs_goods_audit_process record);

    ezs_goods_audit_process selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_goods_audit_process record);

    int updateByPrimaryKey(ezs_goods_audit_process record);
}