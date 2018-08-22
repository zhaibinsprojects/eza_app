package com.sanbang.hangq.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_dict;
import com.sanbang.bean.ezs_goods_class;
import com.sanbang.cata.service.CataService;
import com.sanbang.dict.service.DictService;
import com.sanbang.hangq.servive.HangqAreaService;
import com.sanbang.redis.RedisConstants;
import com.sanbang.redis.RedisResult;
import com.sanbang.utils.JsonUtils;
import com.sanbang.utils.RedisUtils;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCate;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.GoodsClass;

@Controller
@RequestMapping("/app/hangq/")
public class HomeHangqIndexController {
	
	@Autowired
	private HangqAreaService hangqAreaService;
	@Autowired
	private DictService dictService;
	@Autowired
	private CataService cataService;
	
	/**
	 * 行情数据标识
	 */
	private  static final String HANGQ_DATA="HANGQ_DATA";
	
	private Logger log=Logger.getLogger(HomeHangqIndexController.class);
	
	/**
	 * 价格行情首页
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getHangqHomeMess")
	@ResponseBody
	public Result getHangqHomeMess(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue="bj")String reqtype){
		Result result = Result.success();
		result.setMsg("请求失败");
		Map<String, Object> map=new HashMap<>();
 		try {
 			RedisResult<Result> redate = (RedisResult<Result>) RedisUtils.get(HANGQ_DATA,
 					Result.class);
 			if (redate.getCode() == RedisConstants.SUCCESS) {
				log.debug("查询redis分类成功执行");
				result=redate.getResult();
			} else {
					log.debug("查询redis分类执行失败");
					map=hangqAreaService.getHangqParamDate(reqtype, map);
					map.put("color", commonToJson(dictService.getDictByParentId(DictionaryCate.EZS_COLOR)));
					//形态
					map.put("form", commonToJson(dictService.getDictByParentId(DictionaryCate.EZS_FORM)));
						
					map.put("suppy", commonToJson(dictService.getDictByParentId(DictionaryCate.EZS_SUPPLY)));
					//分类
					map.put("cata",cataToJson());
					
					result.setSuccess(true);
			 		result.setMsg("请求成功");
			 		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			 		result.setObj(map);
			 		
			 		RedisUtils.get(HANGQ_DATA, Result.class);
					RedisResult<String> rrt;
					rrt = (RedisResult<String>) RedisUtils.set(HANGQ_DATA, result,
						Long.valueOf(3600*24));
					if (rrt.getCode() == RedisConstants.SUCCESS) {
						log.debug("行情分类保存到redis成功执行");
					} else {
						log.debug("行情分类保存到redis失败");
					}
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
			result.setObj(map);
		}
 		
		return result;
	}

	/**
	 * common
	 * @return
	 */
	public Object commonToJson(List<ezs_dict> listp){
		List<Map<String, Object>> list=new ArrayList<>();
		 for (ezs_dict ezs_dict : listp) {
			Map<String, Object> map=new HashMap<>();
			map.put("value", ezs_dict.getId());
			map.put("text", ezs_dict.getName());
			list.add(map);
		}
		return JsonUtils.jsonOut(list); 
	}
	
	
	/**
	 * 分类
	 * @return
	 */
	public List<Map<String, Object>>  cataToJson(){
		List<Map<String, Object>> list=new ArrayList<>();
		List<ezs_goods_class> listp = cataService.getFirstList();
		 for (ezs_goods_class ezs_goods_class : listp) {
			Map<String, Object> map=new HashMap<>();
			map.put("value", ezs_goods_class.getId());
			map.put("text", ezs_goods_class.getName());
			map.put("children", getcataarraytwo(ezs_goods_class));
			list.add(map);
		}
		 System.err.println(JsonUtils.jsonOut(list));
		return list; 
	}
	
	private  List<Map<String, Object>>   getcataarraytwo(ezs_goods_class ezs_goods_class){
		List<Map<String, Object>> list=new ArrayList<>();
		List<GoodsClass> listp=	cataService.getSecondList(ezs_goods_class.getId());
		if(listp.size()>0){
			Map<String, Object> map=new HashMap<>();
			map.put("value", "-1");
			map.put("text", "全部");
			list.add(map);
		}
		for (GoodsClass ezs_area : listp) {
			Map<String, Object> map=new HashMap<>();
			map.put("value", ezs_area.getSecondId());
			map.put("text", ezs_area.getSecondName());
			map.put("children", getcataarraythree(ezs_area));
			list.add(map);
		}
		
		
		return list;
	}
	
	
	private  List<Map<String, Object>>   getcataarraythree(GoodsClass GoodsClass){
		List<Map<String, Object>> list=new ArrayList<>();
		List<GoodsClass> listp1=	cataService.getSecondList(GoodsClass.getSecondId());
		if(listp1.size()>0){
			Map<String, Object> map=new HashMap<>();
			map.put("value", "-1");
			map.put("text", "全部");
			list.add(map);
		}
		for (GoodsClass ezs_area : listp1) {
			Map<String, Object> map=new HashMap<>();
			map.put("value", ezs_area.getSecondId());
			map.put("text", ezs_area.getSecondName());
			list.add(map);
		}
		return list;
	}
}
