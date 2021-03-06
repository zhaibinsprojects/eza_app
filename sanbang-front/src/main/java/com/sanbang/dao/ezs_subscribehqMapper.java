
package com.sanbang.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_subscribehq;
import com.sanbang.vo.PriceTrendIfo;

@Repository
public interface ezs_subscribehqMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_subscribehq record);

    int insertSelective(ezs_subscribehq record);

    ezs_subscribehq selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_subscribehq record);

    int updateByPrimaryKey(ezs_subscribehq record);
    
    /**
        * 用户订阅记录
     * @param userid
     * @return
     */
    List<ezs_subscribehq> getDingyueRecoudList(@Param("userid")long userid,@Param("subtype")int subtype,
     		@Param("pageCount")int pageCount,@Param("pageSize")long pageSize);
    
     int getDingyueRecoudCount(@Param("userid")long userid);
     
     
     /**
      * 
      * @param userid
      * @return
      */
     Map<String, Object> getDingYueTryStatusByUserid(long userid);
     
     Map<String, Object> getDingYueBuyStatusByUserid(long userid);
     
     /**
      * 订阅时间
      * @param userid
      * @return
      */
     Date getDingYueTryAddTimeStatusByUserid(long id);
     
     /**
      * 实时报价再生料
      * @param mp
      * @return
      */
     List<PriceTrendIfo> priceInTimeNewList(Map<String, Object> mp);
     
     
     
     /**
      * 实时报价新料
      * @param mp
      * @return
      */
     List<PriceTrendIfo> priceInTimeNewXLList(Map<String, Object> mp);
     
     
     /**
      * 价格趋势 新
      * @param mp
      * @return
      */
     List<PriceTrendIfo> getPriceTrendcyNew(Map<String, Object> mp);
     
     
     
}