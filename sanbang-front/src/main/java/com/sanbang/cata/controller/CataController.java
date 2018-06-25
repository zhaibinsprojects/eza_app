package com.sanbang.cata.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_goods_class;
import com.sanbang.cata.service.CataService;
import com.sanbang.utils.Result;
import com.sanbang.vo.GoodsClass;
import com.sanbang.vo.GoodsClass2;

@Controller
@RequestMapping("/cata")
public class CataController {
	
	@Autowired
	private CataService cataService;
	
	//一级
	@RequestMapping("/firstList")
	@ResponseBody
	public Result getFirstList(HttpServletRequest request){
		Result result = new Result();
		try{
			List<ezs_goods_class> list = cataService.getFirstList();
			if(null != list && list.size()>0){
				result.setObj(list);
				result.setSuccess(true);
				result.setMsg("查询成功");
			}else{
				result.setObj(list);
				result.setSuccess(true);
				result.setMsg("后台无一级数据");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("查询出错，请联系管理员");
		}
		return result;
	}
	
	/**
	 * 二级三级列表
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/childList")
	@ResponseBody
	public Result getChildList(HttpServletRequest request,Long id){
		Result result=Result.failure();
		try{
			List<GoodsClass> secondList = cataService.getSecondList(id);
			if(secondList.size()==0){
				result.setMsg("二级列表为空");
				result.setSuccess(true);
				return result;
			}
			List<GoodsClass2> thirdList = new ArrayList<GoodsClass2>();
			Map<String,Object> map2 = new HashMap<String,Object>();	//用map拼前端的json
			List<Object> transList = new ArrayList<Object>();
			for(int n=0; n < secondList.size(); n++){
				Map<String,Object> map = new HashMap<String,Object>();
				GoodsClass gc = (GoodsClass)secondList.get(n);
				thirdList = cataService.getThirdList(gc.getSecondId());
				for(int m =0; m<thirdList.size();m++){	//根据前端app要求，如果名字为空，则将这条移除
					if(null == thirdList.get(m).getThirdName()){
						thirdList.remove(m);
					}
				}
				map.put("third", thirdList);	//为符合前端要求的层级结构
				map.put("secondName", gc.getSecondName());
				transList.add(map);
				map2.put("second",transList);
			}
			if(null!=map2){
				result.setMsg("查询成功");
				result.setSuccess(true);
				result.setObj(map2);
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setMsg("查询出错");
			result.setSuccess(false);
		}
		return result;
	}
	
}
