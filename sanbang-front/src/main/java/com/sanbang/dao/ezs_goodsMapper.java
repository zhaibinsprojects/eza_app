package com.sanbang.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_goods;
import com.sanbang.utils.Page;

@Repository
public interface ezs_goodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_goods record);

    int insertSelective(ezs_goods record);

    ezs_goods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_goods record);

    int updateByPrimaryKey(ezs_goods record);
    
    List<ezs_goods> selectByGoodName(String name);
    
    List<ezs_goods> goodsIntroduce(Page page);
    
    int goodsIntroduceCount();

	List<ezs_goods> selectGoodsListBySellerId(Long sellerId, int status);
}