package com.sanbang.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.sanbang.bean.ezs_goodscart;

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
}