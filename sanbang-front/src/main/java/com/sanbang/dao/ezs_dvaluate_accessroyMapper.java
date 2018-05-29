package com.sanbang.dao;

import com.sanbang.bean.ezs_dvaluate_accessroy;

public interface ezs_dvaluate_accessroyMapper {
    int deleteByPrimaryKey(Long dvaluate_id);

    int insert(ezs_dvaluate_accessroy record);

    int insertSelective(ezs_dvaluate_accessroy record);

    ezs_dvaluate_accessroy selectByPrimaryKey(Long dvaluate_id);

    int updateByPrimaryKeySelective(ezs_dvaluate_accessroy record);

    int updateByPrimaryKey(ezs_dvaluate_accessroy record);
}