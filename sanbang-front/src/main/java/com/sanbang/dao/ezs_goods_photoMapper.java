package com.sanbang.dao;

import java.util.List;

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
}
