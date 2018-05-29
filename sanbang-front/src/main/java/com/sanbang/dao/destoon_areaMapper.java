package com.sanbang.dao;

import com.sanbang.bean.destoon_area;

public interface destoon_areaMapper {
    int deleteByPrimaryKey(Integer areaid);

    int insert(destoon_area record);

    int insertSelective(destoon_area record);

    destoon_area selectByPrimaryKey(Integer areaid);

    int updateByPrimaryKeySelective(destoon_area record);

    int updateByPrimaryKeyWithBLOBs(destoon_area record);

    int updateByPrimaryKey(destoon_area record);
}