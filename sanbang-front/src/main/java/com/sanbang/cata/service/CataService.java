package com.sanbang.cata.service;

import java.util.List;

import com.sanbang.bean.ezs_goods_class;
import com.sanbang.vo.GoodsClass;

/**
 * 品类相关业务处理
 * @author hanlongfei
 * 2018年05月11日
 */
public interface CataService {
	//一级
	public List<ezs_goods_class> getFirstList();
	//二级三级
	public List<GoodsClass> getChildList();
}
