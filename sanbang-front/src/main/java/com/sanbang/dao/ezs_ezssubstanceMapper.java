package com.sanbang.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
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
    
    
    int getEssayCounts(String[] ecIdList);
    
    List<ezs_ezssubstance> getEssayThemes(ExPage page);
    
    
    int goodsAllIndustryCount(Long parentKindId);
    
    List<ezs_ezssubstance> selectAllGoodsIndustryByPage(ExPage page);
   /* 价格行情文档*/
    int goodsAllIndustryCount2(@Param("id")Long id,@Param("ecId")Long ecId);
    List<ezs_ezssubstance> selectAllGoodsIndustryByPage2(ExPage page);
    
    
    List<ezs_ezssubstance> selectEssayThemeByPage(ExPage page);
    
    /**
     * 获取上一条
     * @param id
     * @param catid
     * @return
     */
    ezs_ezssubstance  getTopOneSubstanceByid(@Param("id")long id ,@Param("catid")long catid);
    
    /**
     * 获取下一条
     * @param id
     * @param catid
     * @return
     */
    ezs_ezssubstance  getButtomOneSubstanceByid(@Param("id")long id ,@Param("catid")long catid);
    
    /**
     * 当前分类列表
     * @param pagecount
     * @param pagesize
     * @param catid
     * @return
     */
    List<ezs_ezssubstance>  getButtomOneSubstanceBycatid(@Param("pagecount")long pagecount ,@Param("pagesize")long pagesize ,@Param("catid")long catid);
    
    
    
}