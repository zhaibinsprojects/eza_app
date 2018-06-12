package com.sanbang.cata.service.imp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_goods_class;
import com.sanbang.cata.service.CataService;
import com.sanbang.vo.GoodsClass;
import com.sanbang.vo.GoodsClass2;

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
	public List<GoodsClass> getChildList(Long id){
		List<GoodsClass> list = new ArrayList<GoodsClass>();
		list = ezs_cataMapper.getChildList(id);
		return list;
	}
	
	//二级
	public List<GoodsClass> getSecondList(Long id){
		List<GoodsClass> list = new ArrayList<GoodsClass>();
		list = ezs_cataMapper.getSecondList(id);
		return list;
	}
	
	//三级
	public List<GoodsClass2> getThirdList(Long id){
		List<GoodsClass2> list = new ArrayList<GoodsClass2>();
		list = ezs_cataMapper.getThirdList(id);
		return list;
	}
}
