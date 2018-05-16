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
	/**
	 * 一级
	 */
	public List getOnelevelList(){
		List<ezs_goods_class> list = new ArrayList();
		list = ezs_cataMapper.getOnelevelList();
		return list;
	}
	/**
	 * 二级
	 * @param parentId	父id
	 * @return
	 */
	public List getTwolevelList(long parentId){
		List<ezs_goods_class> list = new ArrayList();
		list = ezs_cataMapper.getTwolevelList(parentId);
		return list;
	}
	/**
	 * 三级
	 * @param parentId	父id
	 * @return
	 */
	public List getThreelevelList(long parentId){
		List<ezs_goods_class> list = new ArrayList();
		list = ezs_cataMapper.getThreelevelList(parentId);
		return list;
	}
	/**
	 * 自营，地区、品类筛选
	 * @param area
	 * @param type
	 * @return
	 */
	public List listByAreaAndType(String area,String type){
		List<ezs_goods_class> list = new ArrayList();
		list = ezs_cataMapper.listByAreaAndType(area,type);
		return list;
	}
	/**
	 * 其他筛选
	 * @param terms	字符串数组
	 * @return
	 */
	public List listByOthers(String[] terms){
		List<ezs_goods_class> list = new ArrayList();
		for(String term : terms){
			
			
		}
		list = ezs_cataMapper.listByOthers(terms);
		return list;
	}
	
	
	
}
