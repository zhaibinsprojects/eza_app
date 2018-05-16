package com.sanbang.cata.service.imp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_goods_class;
import com.sanbang.cata.service.CataService;

/**
 * 品类相关处理
 * 
 * @author hanlongfei
 *  
 * 2018年05月11日
 */
@Service("cataService")
public class CataServiceImpl implements CataService{
	@Resource(name="ezs_cataMapper")
	private com.sanbang.dao.ezs_cataMapper ezs_cataMapper;
	
	public List getOnelevelList(){
		List<ezs_goods_class> list = new ArrayList();
		list = ezs_cataMapper.getOnelevelList();
		return list;
	}
	
}
