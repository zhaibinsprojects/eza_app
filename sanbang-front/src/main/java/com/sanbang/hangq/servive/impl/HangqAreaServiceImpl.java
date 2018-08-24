package com.sanbang.hangq.servive.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.dao.ezs_goods_classVoMapper;
import com.sanbang.dao.ezs_hangqareaMapper;
import com.sanbang.hangq.servive.HangqAreaService;
import com.sanbang.utils.FastjsonUtils;
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
			redate.put("cata1", getIndexCata(reqtype));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return redate;
	}
	
	
	/**
	 * 订阅行情
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public 	List<Map<String, Object>> getDingYueCata(){
		List<Map<String, Object>> calist=cataToJson();
		Map<String, Object> listone=(Map<String, Object>) calist.get(0);
		Map<String, Object> listtwo=(Map<String, Object>) calist.get(1);
		List<Map<String, Object>>  children=(List<Map<String, Object>>) listone.get("children");
		List<Map<String, Object>> list=new ArrayList<>();
		for (Map<String, Object> map : children) {
			List<Map<String, Object>> listmap=new ArrayList<>();
			Map<String, Object> map1=new HashMap<>();
			listmap.add(map);
			
			map1.put("id", map.get("id"));
			map1.put("price", map.get("price"));
			map1.put("name",map.get("name"));
			map1.put("children", listmap);
			list.add(map1);
		}
		children=list;
		children.add(listtwo);
		Map<String, Object> map=new HashMap<>();
		map.put("id", 0);
		map.put("price", "0");
		map.put("name", "全部分类");
		
		List<Map<String, Object>> calist1=cataToJson();
		List<Map<String, Object>> mapall=new ArrayList<>();
		Map<String, Object> listthree=(Map<String, Object>) calist1.get(0);
		Map<String, Object> listfour=(Map<String, Object>) calist1.get(1);
		List<Map<String, Object>>  children1=(List<Map<String, Object>>) listthree.get("children");
		List<Map<String, Object>>  children2=(List<Map<String, Object>>) listfour.get("children");
		mapall.addAll(children1);
		mapall.addAll(children2);
		/*for (Map<String, Object> map2 : children1) {
			List<Map<String, Object>>  chace=(List<Map<String, Object>>)map2.get("children");
			mapall.addAll(chace);
		}
		for (Map<String, Object> map2 : children2) { 
			List<Map<String, Object>>  chace=(List<Map<String, Object>>)map2.get("children");
			mapall.addAll(chace);
		}*/
		
		map.put("children",mapall);
		children.add(map);
		return children;
	}
	
	/**
	 * 首页行情
	 * @param reqtype
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getIndexCata(String reqtype){
		List<Map<String, Object>> calist=cataToJson();
		Map<String, Object> listone=(Map<String, Object>) calist.get(0);
		Map<String, Object> listtwo=(Map<String, Object>) calist.get(1);
		List<Map<String, Object>>  children=new ArrayList<>();
		//再生报价
				if(reqtype.equals("zsbj")) {
					  children=(List<Map<String, Object>>) listone.get("children");
				//再生走势	
				}else if(reqtype.equals("zszs")){
					  children=(List<Map<String, Object>>) listone.get("children");
				}else if(reqtype.equals("xlbj")) {
					  children=(List<Map<String, Object>>) listtwo.get("children");
				}
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
			chace.put("id", index.getKey());
			chace.put("AreaName", index.getValue());
			list.add(chace);
		}
		return list;
	}
	
	private Map<String, Object> getProvices(String reqtype) throws Exception{
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
		
		List<Map<String, Object>> hotcitys=new ArrayList<>();
		ListIterator<Map<String, Object>> aa=  list1.listIterator(list1.size()>=5?list1.size()-5:0);
		ListIterator<Map<String, Object>> bb=areal1.listIterator(areal1.size()>=5?areal1.size()-5:0);
		 while(aa.hasNext()) {
			Map<String,Object> map=new HashMap<>();
			 for (Entry<String, Object> map1 : aa.next().entrySet()) {
				map.put(map1.getKey(), map1.getValue());
			}
			 hotcitys.add(map);
		 }
		 
		 while(bb.hasNext()) {
			 Map<String,Object> map=new HashMap<>();
			 for (Entry<String, Object> map1 : bb.next().entrySet()) {
				map.put(map1.getKey(), map1.getValue());
			}
			 hotcitys.add(map);
		 }
		 
		res.put("hotcitys",hotcitys);
		
		return res;
		
	} 
}
