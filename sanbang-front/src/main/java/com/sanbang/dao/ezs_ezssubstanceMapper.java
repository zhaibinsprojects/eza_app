package com.sanbang.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_ezssubstance;
import com.sanbang.vo.ExPage;

@Repository
public interface ezs_ezssubstanceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_ezssubstance record);

    int insertSelective(ezs_ezssubstance record);

    ezs_ezssubstance selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_ezssubstance record);

    int updateByPrimaryKey(ezs_ezssubstance record);
    
    int goodsIndustryCountByKinds(Long kindId);
    
    List<ezs_ezssubstance> goodsIndustryByPage(ExPage page);
    
    int goodsAllIndustryCount(Long parentKindId);
    
    List<ezs_ezssubstance> selectAllGoodsIndustryByPage(ExPage page);
}