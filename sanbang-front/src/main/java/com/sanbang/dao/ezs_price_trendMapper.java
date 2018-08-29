package com.sanbang.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_price_trend;
import com.sanbang.vo.PriceTrendIfo;

@Repository
public interface ezs_price_trendMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_price_trend record);

    int insertSelective(ezs_price_trend record);

    ezs_price_trend selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_price_trend record);

    int updateByPrimaryKey(ezs_price_trend record);
    
    List<PriceTrendIfo> selectByCondition(Map<String, Object> mp);
    
    List<PriceTrendIfo> getPriceTrendcy(Map<String, Object> mp);
    
    List<PriceTrendIfo> selectPriceChangesByGood(Long gId);
    
    List<PriceTrendIfo> selectByAreaIdAndOtherCondition(Map<String, Object> mp);
    
    int getPriceConditionCount(Map<String, Object> mp);
    /*实时报价 新*/
    List<PriceTrendIfo> priceInTimeNew(Long goodClassId);
    /*实时报价 列表 多条件查询*/
    List<PriceTrendIfo> priceInTimeNewList(Map<String, Object> mp);
    /*价格趋势 新*/
    List<PriceTrendIfo> getPriceTrendcyNew(Map<String, Object> mp);
    /*价格趋势 新-分页*/
    List<PriceTrendIfo> getPriceTrendcyNewPage(Map<String, Object> mp);
    /*再生料-实时报价-详情页面*/
    List<PriceTrendIfo> priceInTimeNewDetail(Map<String, Object> mp);
    /*再生料-实时报价-详情页面-分页展示*/
    List<PriceTrendIfo> priceInTimeNewDetailPage(Map<String, Object> mp);
}