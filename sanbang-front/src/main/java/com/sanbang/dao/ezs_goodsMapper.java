package com.sanbang.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.sanbang.bean.ezs_goods;
import com.sanbang.utils.Page;
import com.sanbang.vo.GoodsInfo;
import com.sanbang.vo.goods.GoodsVo;

@Repository
public interface ezs_goodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_goods record);
    int insertSelective(ezs_goods record);

    ezs_goods selectByPrimaryKey(Long id);
    
    ezs_goods getGoodsDetail(Long id);
    
    int updateByPrimaryKeySelective(ezs_goods record);

    int updateByPrimaryKey(ezs_goods record);
    
    List<GoodsInfo> selectByGoodName(String name);
    
    List<GoodsInfo> goodsIntroduce(Page page);
    
    int goodsIntroduceCount();


	List<ezs_goods> selectGoodsListBySellerId(@Param("sellerId")Long sellerId, @Param("status")int status, @Param("page")Page page);

	int pullOffShelves(long goodsId);
	
	List<ezs_goods> listForGoods(Long goodClass_id);

	
	List<ezs_goods> getGoodsFromCollection(Long userId);
	

    List<GoodsInfo> selectByGoodsName(String name);
	
	List<ezs_goods> listByOthers(@Param("color")Long color,@Param("form")Long form,@Param("purpose")String purpose,@Param("source")String source,@Param("burning")String burning,@Param("protection")boolean protection);	
	
	int selectCount(Long sellerId);

	List<ezs_goods> queryGoods(@Param("page")Page page, @Param("sellerId")Long sellerId, @Param("status")int status);
	
	/**
	 * 多条件筛选
	 * @param map
	 * @return
	 */
	List<ezs_goods> queryGoodsList(Map map);
	
	
	/**
	 * @author langjf
	 * app订单详情  
	 * @param goodsid
	 * @return
	 */
	GoodsVo  getgoodsinfo(@Param("goodsid")long goodsid);
	
	/**
	 * 优品推荐 2
	 * @author zhaiBin
	 * @param page
	 * @return
	 */
	List<GoodsVo> goodsIntroduceTwo(Page page);
	
}