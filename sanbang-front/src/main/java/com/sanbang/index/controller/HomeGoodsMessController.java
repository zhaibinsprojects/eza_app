package com.sanbang.index.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_customized;
import com.sanbang.bean.ezs_customized_record;
import com.sanbang.bean.ezs_goods_class;
import com.sanbang.bean.ezs_user;
import com.sanbang.index.service.CustomizedRecordService;
import com.sanbang.index.service.CustomizedService;
import com.sanbang.index.service.GoodsClassService;
import com.sanbang.index.service.RecommendGoodsService;
import com.sanbang.utils.Page;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.GoodsInfo;

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
		List<GoodsInfo> glist = null;
		Result 	rs = Result.failure();
		mmp = this.recommendGoodsService.queryByName(goodsName);
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			glist = (List<GoodsInfo>) mmp.get("Obj");
			 rs.setSuccess(true);
			rs.setObj(glist);
		}else{
			 rs.setSuccess(false);
			rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
			rs.setMsg(mmp.get("Msg").toString());
		}
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
		List<GoodsInfo> glist = null;
		Result rs = null;
		Page page = null;
		if(currentPage==null){
			currentPage = "1";
		}
		mmp = this.recommendGoodsService.goodsIntroduce(currentPage);
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			glist = (List<GoodsInfo>) mmp.get("Obj");
			page = (Page) mmp.get("Page");
			rs = Result.success();
			rs.setObj(glist);
			rs.setMeta(page);
		}else{
			rs = Result.failure();
			rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
			rs.setMsg(mmp.get("Msg").toString());
		}
		return rs;
	}
	/**
	 * 获取所有商品种类信息(三级分类)
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/allGoodsClass")
	@ResponseBody
	public Object allGoodsClassDetail(HttpServletRequest request,HttpServletResponse response){
		List<ezs_goods_class> gclist = null;
		Result rs = null;
		Map<String, Object> mmp = this.goodsClassService.queryAllGoodsClass();
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			gclist = (List<ezs_goods_class>) mmp.get("Obj");
			rs = Result.success();
			rs.setObj(gclist);
		}else{
			rs = Result.failure();
			rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
			rs.setMsg(mmp.get("Msg").toString());
		}
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
			,ezs_customized customized,String ppre_time) throws Exception{
		Map<String, Object> mmp = null;
		Result rs = null;
		//判断用户是否登录
		ezs_user user = RedisUserSession.getLoginUserInfo(request);
		if (user == null) {
			rs = Result.failure();
			rs.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			rs.setMsg("用户未登录");
			return rs;
		}
		if(ppre_time!=null){
			//格式化时间格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(ppre_time);
			customized.setPre_time(date);
		}
		mmp = this.customizedService.addCustomized(user, customized, customizedrecord);
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			rs = Result.success();
			rs.setMsg(mmp.get("Msg").toString());
		}else{
			rs = Result.failure();
			rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
			rs.setMsg(mmp.get("Msg").toString());
		}
		return rs;
	}
}
