package com.sanbang.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sanbang.bean.ezs_dvaluate;

public interface ezs_dvaluateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_dvaluate record);

    int insertSelective(ezs_dvaluate record);

    ezs_dvaluate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_dvaluate record);

    int updateByPrimaryKey(ezs_dvaluate record);
    
    //单个评论
    List<ezs_dvaluate>   listForEvaluate(Long id);
    
    //评论列表
    List<ezs_dvaluate> getEvaluateList(@Param("totalpage")int totalpage,@Param("pageNo")int pageNo,@Param("goodsid")long goodsid);
}