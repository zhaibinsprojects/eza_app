package com.sanbang.goods.service.imp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_goods_class;
import com.sanbang.goods.service.GoodsService;

/**
 * 品类相关处理
 * 
 * @author hanlongfei
 *  
 * 2018年05月12日
 */
@Service("goodsService")
public class GoodsServiceImpl implements GoodsService{
	@Resource(name="ezs_cataMapper")
	private com.sanbang.dao.ezs_cataMapper ezs_cataMapper;
	
	public List getListForClass(){
		List<ezs_goods_class> list = new ArrayList();
		
		list = ezs_cataMapper.getListForClass();
		
		
		return list;
	}
	
}
