package com.sanbang.cata.service.imp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_goods_class;
import com.sanbang.cata.service.CataService;
import com.sanbang.vo.GoodsClass;

/**
 * 品类相关处理
 * @author hanlongfei
 * 2018年05月11日
 */
@Service("cataService")
public class CataServiceImpl implements CataService{
	@Resource(name="ezs_cataMapper")
	private com.sanbang.dao.ezs_cataMapper ezs_cataMapper;
	//一级
	public List<ezs_goods_class> getFirstList(){
		List<ezs_goods_class> list = new ArrayList<ezs_goods_class>();
		list = ezs_cataMapper.getFirstList();
		return list;
	}
	//二级三级
	public List<GoodsClass> getChildList(){
		List<GoodsClass> list = new ArrayList<GoodsClass>();
		list = ezs_cataMapper.getChildList();
		return list;
	}
}
