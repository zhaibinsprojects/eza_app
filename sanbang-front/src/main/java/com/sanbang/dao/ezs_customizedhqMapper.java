package com.sanbang.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sanbang.bean.ezs_customizedhq;
import com.sanbang.vo.hangq.CataData;

public interface ezs_customizedhqMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_customizedhq record);

    int insertSelective(ezs_customizedhq record);

    ezs_customizedhq selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_customizedhq record);

    int updateByPrimaryKey(ezs_customizedhq record);
    
    /**
       * 查看我的定制
     * @param userid
     * @param ispush
     * @return
     */
    List<ezs_customizedhq>   getDingZhiListByParam(@Param("userid")long userid,@Param("ispush")boolean ispush,
    		@Param("pageCount")int pageCount,@Param("pageSize")long pageSize);
    
    
    int  getDingZhiListByParamCount(@Param("userid")long userid,@Param("ispush")boolean ispush);
    
    /**
     * 用户定制品类
     * @param userid
     * @param catid
     * @return
     */
    List<Map<String, Object>> getDingYueOwenCata(@Param("catid")long catid,@Param("parent_id")long parent_id);
    
    
    List<CataData>  getDingZhiCataInitData(@Param("userid")long userid);
    
    /**
     * 行情查看权限分类
     * @param userid
     * @return
     */
    public String getHangqUserPushClasses(@Param("id")long userid);
}