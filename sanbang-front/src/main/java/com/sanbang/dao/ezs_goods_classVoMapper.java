package com.sanbang.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_goods_class;
import com.sanbang.vo.goods.GoodsClassVo;
import com.sanbang.vo.goods.ezs_goods_classVo;

@Repository
public interface ezs_goods_classVoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_goods_class record);

    int insertSelective(ezs_goods_class record);

    ezs_goods_classVo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_goods_class record);

    int updateByPrimaryKey(ezs_goods_class record);
    
    List<GoodsClassVo>  gethanqParentClassCheck(@Param("userid")long userid);
    
    List<GoodsClassVo>  gethanqChildClassCheck(@Param("userid")long userid,@Param("parentid")long parentid);
    
    
    List<GoodsClassVo>  gethanqParentClassCheckAll();
    
    List<GoodsClassVo>  gethanqChildClassCheckAll(@Param("parentid")long parentid);
    
}