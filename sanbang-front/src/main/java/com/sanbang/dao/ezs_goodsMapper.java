package com.sanbang.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_goods;
import com.sanbang.utils.Page;
import com.sanbang.vo.GoodsInfo;

@Repository
public interface ezs_goodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_goods record);

    int insertSelective(ezs_goods record);

    ezs_goods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_goods record);

    int updateByPrimaryKey(ezs_goods record);
    
    List<GoodsInfo> selectByGoodName(String name);
    
    List<GoodsInfo> goodsIntroduce(Page page);
    
    int goodsIntroduceCount();

	List<ezs_goods> selectGoodsListBySellerId(Long sellerId, int status);
	
	/**
	 * 同类货品
	 * @param id 级别id
	 * @return
	 */
	List<ezs_goods> listForGoods(Long id);
	
	List<ezs_goods> getGoodsFromCollection(Long userId);
	
	/**
	 * 自营，地区、类别筛选
	 * @param area 地区
	 * @param type 类别
	 * @return
	 */
	List<ezs_goods> listByAreaAndType(String area,String type);
	/**
	 * 
	 * @param color 颜色
	 * @param form 形态
	 * @param purpose 用途
	 * @param source
	 * @param burning
	 * @param protection
	 * @return
	 */
	List<ezs_goods> listByOthers(Long color,Long form,String purpose,String source,String burning,boolean protection);
}