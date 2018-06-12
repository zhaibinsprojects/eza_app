package com.sanbang.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_goods_photo;

@Repository
public interface ezs_goods_photoMapper {
    int insert(ezs_goods_photo record);

    int insertSelective(ezs_goods_photo record);

	List<ezs_accessory> selectPhotoById(Long goodsId);

	void updateSelective(ezs_goods_photo goodsPhoto);
	
	int deleteByGoodsId(long goodsId);
	
	//商品所有图片
	List<ezs_goods_photo> selectPhotoinfoBygoods_id(@Param("goodsId")long goodsId);
	
	//单张商品图片
	List<ezs_goods_photo> selectPhotoinfoByPhoto_id(@Param("photoId")long photoId);
}
