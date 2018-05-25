package com.sanbang.dao;

import java.util.List;

import com.sanbang.bean.ezs_customized;

public interface ezs_customizedMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_customized record);

    int insertSelective(ezs_customized record);

    ezs_customized selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_customized record);

    int updateByPrimaryKey(ezs_customized record);
    
<<<<<<< HEAD
    List customizedList(Long user_id);
=======
    //add by zhaibin
    List<ezs_customized> getPurchaseByUserId(Long userId);
    
>>>>>>> ee2118a5be8ac47398a497d035aa92883507e3ca
}