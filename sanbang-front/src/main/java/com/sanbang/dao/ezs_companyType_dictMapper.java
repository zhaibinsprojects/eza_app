package com.sanbang.dao;

import java.util.List;

import com.sanbang.bean.ezs_companyType_dict;

public interface ezs_companyType_dictMapper {
    int insert(ezs_companyType_dict record);

    int insertSelective(ezs_companyType_dict record);
    
    int delCompanyTypeByStoreId(long storeid);
    
    List<ezs_companyType_dict> getCompanyTypeByThisId(long storeid);
}