package com.sanbang.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_goods_cartography;
import com.sanbang.bean.ezs_goods_photo;

@Repository
public interface ezs_goods_cartographyMapper {
    int insert(ezs_goods_cartography record);

    int insertSelective(ezs_goods_cartography record);

	List<ezs_accessory> selectCartographyById(Long goodsId);

	void updateSelective(ezs_goods_cartography cartography);

	void deleteByGoodsId(long goodsId);
	
	//商品所有图片
	List<ezs_goods_photo> selectcartinfoBygoods_id(@Param("goodsId")long goodsId);
	
	//单张商品图片
	List<ezs_goods_photo> selectcartinfoByPhoto_id(@Param("photoId")long photoId);
}