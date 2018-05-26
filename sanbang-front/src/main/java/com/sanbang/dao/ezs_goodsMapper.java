package com.sanbang.dao;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.ibatis.annotations.Param;
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
    
    ezs_goods getGoodsDetail(Long id);
    
    int updateByPrimaryKeySelective(ezs_goods record);

    int updateByPrimaryKey(ezs_goods record);
    
    List<GoodsInfo> selectByGoodName(String name);
    
    List<GoodsInfo> goodsIntroduce(Page page);
    
    int goodsIntroduceCount();


	List<ezs_goods> selectGoodsListBySellerId(@Param("sellerId")Long sellerId, @Param("status")int status, @Param("page")Page page);

	int pullOffShelves(long goodsId);
	
	/**
	 * 同类货品
	 * @param id 级别id
	 * @return
	 */

	List<ezs_goods> listForGoods(Long goodClass_id);

	
	List<ezs_goods> getGoodsFromCollection(Long userId);

	
	/**
	 * 自营，地区、类别筛选
	 * @param area 地区
	 * @param type 类别
	 * @return
	 */
	List<ezs_goods> listByAreaAndType(Map mmp);

//	List<ezs_goods> listByAreaAndType(@Param("area")String area,@Param("type")String type);
//	因为我将listByAreaAndType的参数做了改变，因为同名，不知道这是谁需要的方法，所以暂且将这个方法注释掉，有问题大家再讨论
	
	


    List<GoodsInfo> selectByGoodsName(String name);
	/**
	 * 其他筛选
	 * @param map 查询条件
	 * @return
	 */
	List<ezs_goods> listByOthers(@Param("color")Long color,@Param("form")Long form,@Param("purpose")String purpose,@Param("source")String source,@Param("burning")String burning,@Param("protection")boolean protection);

	List<ezs_goods> listByOthers(Map map);
	
//	List<ezs_goods> listByOthers(@Param("color")Long color,@Param("form")Long form,@Param("purpose")String purpose,@Param("source")String source,@Param("burning")String burning,@Param("protection")boolean protection);
//	同理，这里也是如此
	int selectCount(Long sellerId);

	List<ezs_goods> queryGoods(@Param("page")Page page, @Param("sellerId")Long sellerId, @Param("status")int status);

}