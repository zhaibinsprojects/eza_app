package com.sanbang.index.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_customized;
import com.sanbang.bean.ezs_customized_record;
import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goods_class;
import com.sanbang.bean.ezs_user;
import com.sanbang.index.service.AccessoryService;
import com.sanbang.index.service.CustomizedRecordService;
import com.sanbang.index.service.CustomizedService;
import com.sanbang.index.service.GoodsClassService;
import com.sanbang.index.service.RecommendGoodsService;
import com.sanbang.utils.Page;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.GoodsInfo;
import com.sanbang.vo.HomeDictionaryCode;

@Controller
@RequestMapping("/home")
public class HomeGoodsMessController {
	@Autowired
	private RecommendGoodsService recommendGoodsService;
	@Autowired
	private GoodsClassService goodsClassService;
	@Autowired
	private CustomizedService customizedService;
	@Autowired
	private CustomizedRecordService customizedRecordService;
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
		Map<String, Object> mmp = this.goodsClassService.queryAllGoodsClass();
		List<ezs_goods_class> gclist = (List<ezs_goods_class>) mmp.get("Obj");
		Result rs = Result.success();
		rs.setObj(gclist);
		return rs;
	}
	/**
	 * 采购定制
	 * @param request
	 * @param response
	 * @param customizedrecord  定制采购产品记录
	 * @param customized 定制采购产品
	 * @return
	 */
	@RequestMapping("/customGoods") 
	@ResponseBody
	public Object customGoods(HttpServletRequest request,HttpServletResponse response,ezs_customized_record customizedrecord
			,ezs_customized customized,ezs_user user){
		Result rs = null;
		if(customizedrecord==null||customized==null||user==null){
			rs = Result.failure();
			rs.setMsg("参数不能为空");
			rs.setErrorcode(HomeDictionaryCode.ERROR_HOME_UN_NULL);
		}else{
			this.customizedService.insert(customized);
			//由以上插入并返回产生
			customizedrecord.setCustomized_id(customized.getId());
			customizedrecord.setOperate_id(Integer.parseInt(user.getId().toString()));
			customizedrecord.setPurchase_id(Integer.parseInt(user.getId().toString()));
			this.customizedRecordService.insert(customizedrecord);
			rs = Result.success();
			rs.setMsg("数据插入成功！");
			
		}
		return rs;
	}
}
