package com.sanbang.index.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goods_class;
import com.sanbang.index.service.GoodsClassService;
import com.sanbang.index.service.RecommendGoodsService;
import com.sanbang.utils.Page;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;

@Controller
@RequestMapping("/home")
public class HomeGoodsMessController {
	@Autowired
	private RecommendGoodsService recommendGoodsService;
	@Autowired
	private GoodsClassService goodsClassService;
	/**
	 * 根据商品名称进行商品列表的查询
	 * @param request
	 * @param response
	 * @param goodsName
	 * @return
	 */
	@RequestMapping("/goodByName")
	@ResponseBody
	public Object goodsDetailByName(HttpServletRequest request,HttpServletResponse response,String goodsName){
		Map<String, Object> mmp = null;
		mmp = this.recommendGoodsService.queryByName(goodsName);
		List<ezs_goods> glist = (List<ezs_goods>) mmp.get("Obj");
		Result rs = Result.success();
		rs.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		rs.setObj(glist);
		return rs;
	}
	/**
	 * 优品推荐商品
	 * @param request
	 * @param response
	 * @param currentPage 当前页面[1,00]
	 * @return 
	 */
	@RequestMapping("/goodsIntroduce")
	@ResponseBody
	public Object allGoodsDetail(HttpServletRequest request,HttpServletResponse response,String currentPage){
		Map<String, Object> mmp = null;
		List<ezs_goods> glist = null;
		Page page = null;
		if(currentPage==null){
			currentPage = "1";
		}
		mmp = this.recommendGoodsService.goodsIntroduce(currentPage);
		glist = (List<ezs_goods>) mmp.get("Obj");
		int tErrorCode = (int) mmp.get("ErrorCode");
		page = (Page) mmp.get("Page");
		Result rs = Result.success(); 
		rs.setObj(glist);
		rs.setMeta(page);
		rs.setErrorcode(tErrorCode);
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
