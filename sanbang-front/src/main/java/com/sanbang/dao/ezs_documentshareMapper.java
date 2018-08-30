package com.sanbang.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_documentshare;
import com.sanbang.vo.hangq.HangqCollectedVo;

@Repository
public interface ezs_documentshareMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_documentshare record);

    int insertSelective(ezs_documentshare record);

    ezs_documentshare selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_documentshare record);

    int updateByPrimaryKey(ezs_documentshare record);
    
    //更新状态
    int updateCollect(Map mmp);
    
    int updateCollectByUserId(Map<String, Object> mp);
    
    ezs_documentshare selectByGoodsIdUserid(@Param("id")Long id,@Param("userid")long userid);
    
    /**
     	* 行情收藏列表
     * @param userid
     * @return
     */
    List<HangqCollectedVo> selectHangqCollectionedOwen(@Param("userid")Long userid,
    		@Param("pageCount")int pageCount,@Param("pageSize")long pageSize);
    int selectHangqCollectionedCountOwen(@Param("userid")Long userid);
    
    /**
     * 行情接口
     * @param docid
     * @param userid
     * @return
     */
    HangqCollectedVo getSubstanceInfoById(@Param("docid")Long docid,@Param("userid")long userid);
    
}