package com.sanbang.dao;

import com.sanbang.bean.ezs_huanxin;

public interface ezs_huanxinMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_huanxin record);

    int insertSelective(ezs_huanxin record);

    ezs_huanxin selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_huanxin record);

    int updateByPrimaryKey(ezs_huanxin record);
}