package com.sanbang.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_goodscart;
import com.sanbang.vo.GoodsCarInfo;
import com.sanbang.vo.QueryCondition;

@Repository
public interface ezs_goodscartMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_goodscart record);

    int insertSelective(ezs_goodscart record);

    ezs_goodscart selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_goodscart record);

    int updateByPrimaryKey(ezs_goodscart record);
    
    List<ezs_goodscart> selectBeanByStartEndTime(@Param("startTime")String startTime,@Param("endTime") String endTime);
    List<ezs_goodscart> selectByDay(String needate1);
    
    List<ezs_goodscart> selectByMonth(String needate2);
    
    List<ezs_goodscart> selectByCustom(String starttime, String endtime);
    
    List<ezs_goodscart> selectByCondition(QueryCondition queryCondition);
    
    List<ezs_goodscart> selectByStoreCarId(QueryCondition queryCondition);
    
    int getGoodCarNumByUser(QueryCondition queryCondition);
    
    List<GoodsCarInfo> selectByUserId(QueryCondition queryCondition);
    
    //根据购物车id查询sc_id
    List<Long> querySid(String[] ids);
    //批量删除
    int deleteGoodCar(String[] ids);
    
    List<GoodsCarInfo> selectByGoodCarIds(String[] ids);
    //根据id查询未下单的购物车记录
    ezs_goodscart queryNoCommitOrder(Long id);
    
    /**
     * 查询购物车id
     * @return
     */
    ezs_goodscart  selectGoodsCartByOfidOrPofid(@Param("of_id")long of_id,@Param("pof_id")long pofid);
    
}