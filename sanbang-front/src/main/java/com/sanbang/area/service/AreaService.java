package com.sanbang.area.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sanbang.bean.ezs_area;

public interface AreaService {

	/**
     * 得到一级数据
     * @return
     */
    List<ezs_area> getAreaParentList();
    
    /**
     * 得到子子级数据
     * @return
     */
    List<ezs_area> getAreaListByParId(long areaid);
}
