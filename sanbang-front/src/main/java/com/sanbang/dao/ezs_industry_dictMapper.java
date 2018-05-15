package com.sanbang.dao;

import java.util.List;

import com.sanbang.bean.ezs_industry_dict;

public interface ezs_industry_dictMapper {
    int insert(ezs_industry_dict record);

    int insertSelective(ezs_industry_dict record);
    
    int delIndustryDictByStoreId(long storeid);
    
    List<ezs_industry_dict> getIndustryByThisId(long storeid);
    
    
}