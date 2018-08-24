package com.sanbang.hangq.servive.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.dao.ezs_goods_classVoMapper;
import com.sanbang.dao.ezs_hangqareaMapper;
import com.sanbang.hangq.servive.HangqAreaService;
import com.sanbang.utils.JsonUtils;
import com.sanbang.vo.goods.GoodsClassVo;
import com.sanbang.vo.hangq.HangqDzAreaVo;
import com.sanbang.vo.hangq.HangqParamCommonVo;


@Service("hangqAreaService")
public class HangqAreaServiceImpl implements HangqAreaService{
	
	
	@Autowired
	private ezs_hangqareaMapper ezs_hangqareaMapper;
	@Autowired
	private ezs_goods_classVoMapper ezs_goods_classVoMapper;
	
	@Override
	public Map<String, Object> getHangqParamDate(String reqtype,Map<String, Object> redate) {
		
		try {
			redate.put("area", getProvices(reqtype));
			redate.put("cata", getDingYueCata());
			redate.put("cata1", cataToJson());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return redate;
	}
	
	
	/**
	 * 首页
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public 	List<Map<String, Object>> getDingYueCata(){
		List<Map<String, Object>> calist=cataToJson();
		Map<String, Object> listone=(Map<String, Object>) calist.get(0);
		Map<String, Object> listtwo=(Map<String, Object>) calist.get(1);
		List<Map<String, Object>>  children=(List<Map<String, Object>>) listone.get("children");
		children.add(listtwo);
		Map<String, Object> map=new HashMap<>();
		map.put("id", 0);
		map.put("price", "0");
		map.put("name", "全部分类");
		map.put("children", cataToJson());
		children.add(map);
		return children;
	}
	
	
	/**
	 * 分类  
	 * @return
	 */
	public List<Map<String, Object>>  cataToJson(){
		List<Map<String, Object>> list=new ArrayList<>();
		List<GoodsClassVo>  listp=ezs_goods_classVoMapper.gethanqParentClassCheckAll();
		 for (GoodsClassVo ezs_goods_class : listp) {
			Map<String, Object> map=new HashMap<>();
			map.put("id", ezs_goods_class.getId());
			map.put("name", ezs_goods_class.getName());
			map.put("price", ezs_goods_class.getPrice());
			map.put("children", getcataarraytwo(ezs_goods_class));
			list.add(map);
		}
		 System.err.println(JsonUtils.jsonOut(list));
		return list; 
	}
	
	private  List<Map<String, Object>>   getcataarraytwo(GoodsClassVo ezs_goods_class){
		List<Map<String, Object>> list=new ArrayList<>();
		List<GoodsClassVo>  listp=	ezs_goods_classVoMapper.gethanqChildClassCheckAll(ezs_goods_class.getId());
		
		for (GoodsClassVo ezs_area : listp) {
			Map<String, Object> map=new HashMap<>();
			map.put("id", ezs_area.getId());
			map.put("name", ezs_area.getName());
			map.put("price", ezs_area.getPrice());
			map.put("children", getcataarraythree(ezs_area));
			list.add(map);
		}
		
		
		return list;
	}
	
	
	private  List<Map<String, Object>>   getcataarraythree(GoodsClassVo GoodsClass){
		List<Map<String, Object>> list=new ArrayList<>();
		List<GoodsClassVo>  listp1=	ezs_goods_classVoMapper.gethanqChildClassCheckAll(GoodsClass.getId());
		
		for (GoodsClassVo ezs_area : listp1) {
			Map<String, Object> map=new HashMap<>();
			map.put("id", ezs_area.getId());
			map.put("name", ezs_area.getName());
			map.put("price", ezs_area.getPrice());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 分类  
	 * @return
	 */
	public List<Map<String, Object>>  cataToJson(long parentid,String level,String reqtype){
		List<Map<String, Object>> list=new ArrayList<>();
		List<GoodsClassVo>  listp=ezs_goods_classVoMapper.gethangqCataBylevel(parentid, level, reqtype);
		 for (GoodsClassVo ezs_goods_class : listp) {
			Map<String, Object> map=new HashMap<>();
			map.put("id", ezs_goods_class.getId());
			map.put("name", ezs_goods_class.getName());
			map.put("price", ezs_goods_class.getPrice());
			map.put("children", getcataarraytwo(ezs_goods_class,reqtype));
			list.add(map);
		}
		 System.err.println(JsonUtils.jsonOut(list));
		return list; 
	}
	
	private  List<Map<String, Object>>   getcataarraytwo(GoodsClassVo ezs_goods_class,String reqtype){
		List<Map<String, Object>> list=new ArrayList<>();
		List<GoodsClassVo>  listp=	ezs_goods_classVoMapper.gethangqCataBylevel(ezs_goods_class.getId(), "2", reqtype);
		
		for (GoodsClassVo ezs_area : listp) {
			Map<String, Object> map=new HashMap<>();
			map.put("id", ezs_area.getId());
			map.put("name", ezs_area.getName());
			map.put("price", ezs_area.getPrice());
			map.put("children", getcataarraythree(ezs_area,reqtype));
			list.add(map);
		}
		
		
		return list;
	}
	
	
	private  List<Map<String, Object>>   getcataarraythree(GoodsClassVo GoodsClass,String reqtype){
		List<Map<String, Object>> list=new ArrayList<>();
		List<GoodsClassVo>  listp1=	ezs_goods_classVoMapper.gethangqCataBylevel(GoodsClass.getId(), "3", reqtype);
		
		for (GoodsClassVo ezs_area : listp1) {
			Map<String, Object> map=new HashMap<>();
			map.put("id", ezs_area.getId());
			map.put("name", ezs_area.getName());
			map.put("price", ezs_area.getPrice());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 大区
	 * @return
	 */
	private List<Map<String, Object>> getdaQu() {
		List<Map<String, Object>> list=new ArrayList<>();
		Map<String, Object> map=new HashMap<>();
		map.put("1", "华东");
		map.put("2", "华南");
		map.put("3", "华北");
		map.put("4", "华中");
		map.put("5", "西北");
		map.put("6", "西南");
		map.put("7", "东北");
		for (Entry<String, Object> index : map.entrySet()) {
			Map<String, Object> chace=new HashMap<>();
			chace.put(index.getKey(), index.getValue());
			list.add(chace);
		}
		return list;
	}
	
	private Map<String, Object> getProvices(String reqtype){
		Map<String, Object> res=new HashMap<>();
		List<HangqParamCommonVo> list=new ArrayList<>();
		List<HangqDzAreaVo> areal=new ArrayList<>();
		List<Map<String, Object>> list1=new ArrayList<>();
		List<Map<String, Object>> areal1=new ArrayList<>();
		//data_sources;// 数据来源  1 .实时成交价 2.供应商报价 3.站外市场价  4.网络数据 
		//再生报价
		if(reqtype.equals("zsbj")) {
			list=ezs_hangqareaMapper.getAreaBySourcesOrStatus("2,3", 2);
			areal=ezs_hangqareaMapper.getPriceTrendCitys("2,3", 2);
		//再生走势	
		}else if(reqtype.equals("zszs")){
			list=ezs_hangqareaMapper.getAreaBySourcesOrStatus("1", 2);
			areal=ezs_hangqareaMapper.getPriceTrendCitys("1", 2);
		}else if(reqtype.equals("xlbj")) {
			list=ezs_hangqareaMapper.getHangqXlAreaList();
			areal=ezs_hangqareaMapper.getPriceTrendXlCitys();
		}else if(reqtype.equals("all")) {
			List<HangqParamCommonVo> zsbj=ezs_hangqareaMapper.getAreaBySourcesOrStatus("1,2,3", 2);
			List<HangqParamCommonVo> xlbj=ezs_hangqareaMapper.getHangqXlAreaList();
			xlbj.removeAll(zsbj);
			xlbj.addAll(zsbj);
			list=xlbj;
			List<HangqDzAreaVo> zsbj1=ezs_hangqareaMapper.getPriceTrendCitys("2,3", 2);
			List<HangqDzAreaVo> xlbj1=ezs_hangqareaMapper.getPriceTrendXlCitys();
			xlbj1.removeAll(zsbj1);
			xlbj1.addAll(zsbj1);
			areal=xlbj1;
		}
		
		for (HangqParamCommonVo map : list) {
			Map<String, Object> cache=new HashMap<>();
			cache.put("id", map.getAreaid());
			cache.put("AreaName", map.getAreaname());
			list1.add(cache);
		}
		
		for (HangqDzAreaVo map : areal) {
			Map<String, Object> cache=new HashMap<>();
			cache.put("id", map.getAreaid());
			cache.put("AreaName", map.getAreaname());
			areal1.add(cache);
		}
		res.put("daqu", getdaQu());
		res.put("provice", list1);
		res.put("citys", areal1);
		
		return res;
		
	} 
}
