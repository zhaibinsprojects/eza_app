package com.sanbang.index.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goods_class;
import com.sanbang.index.service.GoodsClassService;
import com.sanbang.index.service.GoodsService;
import com.sanbang.utils.Result;

@Controller
@RequestMapping("/home")
public class HomeGoodsMessController {
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private GoodsClassService goodsClassService;
	/**
	 * 根据商品名称进行商品列表的查询
	 * @param request
	 * @param response
	 * @param goodsName
	 * @return
	 */
	@RequestMapping("/goodsDetailByName")
	@ResponseBody
	public Object goodsDetailByName(HttpServletRequest request,HttpServletResponse response,String goodsName){
		List<ezs_goods> glist = this.goodsService.queryByName(goodsName);
		Result rs = Result.success();
		rs.setObj(glist);
		return rs;
	}
	/**
	 * 根据条件获取商品列表
	 * @param request
	 * @param response
	 * @param goodsName
	 * @param condition 条件类型
	 * @return
	 */
	@RequestMapping("/goodsDetailByCondition")
	@ResponseBody
	public Object goodsDetailByCondition(HttpServletRequest request,HttpServletResponse response,String goodsName,String condition){
		List<ezs_goods> glist = new ArrayList<>();
		Result rs = Result.success();
		rs.setObj(glist);
		return rs;
	}
	/**
	 * 获取所有商品列表信息
	 * @param request
	 * @param response
	 * @param goodsName
	 * @return
	 */
	@RequestMapping("/allGoodsDetail")
	@ResponseBody
	public Object allGoodsDetail(HttpServletRequest request,HttpServletResponse response,String goodsName){
		List<ezs_goods> glist = new ArrayList<>();
		Result rs = Result.success();
		rs.setObj(glist);
		return rs;
	}
	/**
	 * 获取所有商品种类信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/allGoodsClass")
	@ResponseBody
	public Object allGoodsClassDetail(HttpServletRequest request,HttpServletResponse response){
		List<ezs_goods_class> glist = this.goodsClassService.queryAllGoodsClass();
		Result rs = Result.success();
		rs.setObj(glist);
		return rs;
	}
	
	

}
