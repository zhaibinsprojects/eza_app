package com.sanbang.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_goods_cartography;

@Repository
public interface ezs_goods_cartographyMapper {
    int insert(ezs_goods_cartography record);

    int insertSelective(ezs_goods_cartography record);

	List<ezs_accessory> selectCartographyById(Long goodsId);

	void updateSelective(ezs_goods_cartography cartography);

	void deleteByGoodsId(long goodsId);
}