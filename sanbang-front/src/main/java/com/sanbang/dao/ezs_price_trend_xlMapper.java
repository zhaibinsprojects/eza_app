package com.sanbang.dao;

import java.util.List;
import java.util.Map;

import com.sanbang.bean.ezs_price_trend_xl;
import com.sanbang.vo.PriceTrendIfo;

public interface ezs_price_trend_xlMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_price_trend_xl record);

    int insertSelective(ezs_price_trend_xl record);

    ezs_price_trend_xl selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_price_trend_xl record);

    int updateByPrimaryKey(ezs_price_trend_xl record);
    /*实时报价 新料*/
    List<PriceTrendIfo> priceInTimeNew(Long goodClassId);
    /*实时报价 新料-列表-多条件查询*/
    List<PriceTrendIfo> selectByAreaIdAndOtherCondition(Map<String, Object> mp);
}