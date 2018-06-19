package com.sanbang.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_column;
import com.sanbang.bean.ezs_customized;
import com.sanbang.bean.ezs_customized_record;
import com.sanbang.bean.ezs_ezssubstance;
import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goods_class;
import com.sanbang.bean.ezs_user;
import com.sanbang.index.service.AddressService;
import com.sanbang.index.service.CustomizedRecordService;
import com.sanbang.index.service.CustomizedService;
import com.sanbang.index.service.GoodsClassService;
import com.sanbang.index.service.IndustryInfoService;
import com.sanbang.index.service.PriceConditionService;
import com.sanbang.index.service.RecommendGoodsService;
import com.sanbang.upload.sevice.impl.FileUploadServiceImpl;
import com.sanbang.utils.Page;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.Advices;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.ExPage;
import com.sanbang.vo.GoodsInfo;
import com.sanbang.vo.HomePageMessInfo;
import com.sanbang.vo.goods.GoodsVo;

@Controller
@RequestMapping("/app/home")
public class AppHomeGoodsMessController {
	// 日志
	private static Logger log = Logger.getLogger(FileUploadServiceImpl.class);
	@Autowired
	private RecommendGoodsService recommendGoodsService;
	@Autowired
	private GoodsClassService goodsClassService;
	@Autowired
	private CustomizedService customizedService;
	@Autowired
	private CustomizedRecordService customizedRecordService;
	@Autowired
	private IndustryInfoService industryInfoService;
	
	
	/**
	 * 根据商品名称进行商品列表的查询
	 * @param request
	 * @param response
	 * @param goodsName
	 * @return
	 */
	@SuppressWarnings("unchecked")
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
	 * @param addressId 当前地址编码
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/goodsIntroduce")
	@ResponseBody
	public Object allGoodsDetail(HttpServletRequest request,HttpServletResponse response,String currentPage,String addressId){
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
	@SuppressWarnings("unchecked")
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
			,ezs_customized customized) throws Exception{
		Map<String, Object> mmp = null;
		Result rs = null;
		//判断用户是否登录
		ezs_user user = RedisUserSession.getUserInfoByKeyForApp(request);
		if (user == null) {
			rs = Result.failure();
			rs.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			rs.setMsg("用户未登录");
			return rs;
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
	/**
	 * 返回全部首页相关内容
	 * @author zhaiBin
	 * @param request
	 * @param response
	 * @param addressId 首页定位地址信息
	 * @param currentPage 当前内容展示页面
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getFirstPageMessage")
	@ResponseBody
	public Object getFirstPageMessage(HttpServletRequest request,HttpServletResponse response,String addressId,String currentPage){
		HomePageMessInfo homePageMessInfo = new HomePageMessInfo();
		Result rs = null;
		Result goodsInfo = null;
		Result PriceAnalyzeInfo = null;
		Result advicesInfo = null;
		try {
			goodsInfo = goodsIntroduceInfo(currentPage,addressId);
			PriceAnalyzeInfo = getPriceAnalyzeInfo(currentPage);
			advicesInfo = getAdvicesInfo();
			if(goodsInfo.getObj()!=null){
				homePageMessInfo.setGoodsInfoList((List<GoodsInfo>)goodsInfo.getObj());
				homePageMessInfo.setPage(goodsInfo.getMeta());
			}else{
				homePageMessInfo.setGoodsInfoList(new ArrayList<GoodsInfo>());
				homePageMessInfo.setPage(goodsInfo.getMeta());
			}
			if(PriceAnalyzeInfo.getObj()!=null){
				homePageMessInfo.setEzssubstanceList((List<ezs_ezssubstance>)PriceAnalyzeInfo.getObj());
			}
			if(advicesInfo.getObj()!=null){
				homePageMessInfo.setAdvicesList((List<Advices>)advicesInfo.getObj());
			}
			//添加订阅栏（图片，连接）
			homePageMessInfo.setSubscribeList(getSubscribeList(request));
			//添加商品类别三级展示（图片，ID，name）
			homePageMessInfo.setThirdGoodClassList(getThirdGoodClass());
			
			rs = Result.success();
			rs.setObj(homePageMessInfo);
			rs.setMeta(goodsInfo.getMeta());
			rs.setMsg("查询信息成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			rs = Result.failure();
			rs.setMsg("参数传递有误！");
			log.error("getFirstPageMessage:查询失败："+e.toString());
		}
		return rs;
	}
	
	@SuppressWarnings("unchecked")
	private Result goodsIntroduceInfo(String currentPage,String addressId){
		log.info("优品推荐商品查询begin..........................");
		Map<String, Object> mmp = null;
		List<GoodsVo> glist = null;
		Result rs = null;
		Page page = null;
		if(currentPage==null){
			currentPage = "1";
		}
		mmp = this.recommendGoodsService.goodsIntroduceTwo(currentPage);
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			glist = (List<GoodsVo>) mmp.get("Obj");
			List<GoodsInfo> glistTemp = new ArrayList<>();
			for (GoodsVo gVo : glist) {
				GoodsInfo goodInfo = new GoodsInfo();
				goodInfo.setId(gVo.getId());
				goodInfo.setName(gVo.getName());
				if(gVo.getMainphoto()!=null&&gVo.getMainphoto().size()>0)
					goodInfo.setMainPhoto(gVo.getMainphoto().get(0));
				//地址
				//goodInfo.setAddess(gVo.getAddess());
				goodInfo.setAreaName(gVo.getArea().getAreaName());
				//库存
				goodInfo.setInventory(gVo.getInventory());
				//单位
				goodInfo.setUtilName(gVo.getUtil().getName());
				//价格
				goodInfo.setPrice(gVo.getPrice());
				glistTemp.add(goodInfo);
			}
			page = (Page) mmp.get("Page");
			rs = Result.success();
			rs.setObj(glistTemp);
			rs.setMeta(page);
			log.info("优品推荐商品查询完成..........................");
		}else{
			rs = Result.failure();
			rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
			rs.setMsg(mmp.get("Msg").toString());
			log.error("优品推荐商品查询 Mess:"+mmp.get("Msg").toString());
		}
		return rs;
	}
	@SuppressWarnings("unchecked")
	private Result getPriceAnalyzeInfo(String currentPage){
		log.info("行情分析查询begin..........................");
		Map<String, Object> mmp = null;
		List<ezs_ezssubstance> elist = null;
		Result rs = null;
		if(currentPage==null)
			currentPage = "1";
		mmp = this.industryInfoService.getAllIndustryInfoByParentKinds(Long.valueOf(12), currentPage);
		ExPage page = (ExPage) mmp.get("Page");
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			elist = (List<ezs_ezssubstance>) mmp.get("Obj");
			List<ezs_ezssubstance> eelist = new ArrayList<>();
			for (ezs_ezssubstance eze : elist) {
				//去除不必要信息
				ezs_ezssubstance tempEze = new ezs_ezssubstance();
				ezs_column parentColumn = new ezs_column(); 
				tempEze.setId(eze.getId());
				tempEze.setName(eze.getName());
				tempEze.setMeta(eze.getMeta());
				parentColumn.setName(eze.getParentColumn().getName());
				tempEze.setParentColumn(parentColumn);
				eelist.add(tempEze);
			}
			rs = Result.success();
			rs.setObj(eelist);
			rs.setMeta(page);
			log.info("行情分析查询完成");
		}else{
			rs = Result.failure();
			rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
			rs.setMsg(mmp.get("Msg").toString());
			log.error("行情分析查询 Mess:"+mmp.get("Msg").toString());
		}
		return rs;
	}
	
	private Result getAdvicesInfo(){
		Result rs = null;
		List<Advices> adviceList = new ArrayList<Advices>();
		try {
			log.info("获取广告信息begin。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
			for (int i = 0; i < 3; i++) {
				Advices advice = new Advices();
				advice.setPath("qwer/qweqwe/qweqwe");
				advice.setLink("qwdsasd/asdasd/asdasd");
				advice.setpName("你猜猜");
				adviceList.add(advice);
			}
			if(adviceList.size()>0){
				rs = Result.success();
				rs.setObj(adviceList);
			}else{
				rs = Result.failure();
			}	
			log.info("获取广告信息成功。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.toString());
		}
		return rs;
	}
	/**
	 * 获取三级商品种类
	 * @author zhaibin
	 * @param level
	 * @return
	 */
	@SuppressWarnings({ "unchecked"})
	private List<ezs_goods_class> getThirdGoodClass(){
		log.info("查询三级商品类别信息begin。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
		Map<String, Object> mmp = null;
		List<ezs_goods_class> goodClassList = null;
		List<ezs_goods_class> goodClassListTemp = new ArrayList<>();
		mmp = goodsClassService.queryThirdGoodsClass("3");
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			goodClassList = (List<ezs_goods_class>) mmp.get("Obj");
			for (ezs_goods_class goodsClass : goodClassList) {
				ezs_goods_class goodsClassTemp = new ezs_goods_class();
				goodsClassTemp.setId(goodsClass.getId());
				goodsClassTemp.setLevel(goodsClass.getLevel());
				goodsClassTemp.setName(goodsClass.getName());
				//http://10.10.10.85/front/resource/indeximg/%E9%A6%96%E9%A1%B5-1_03.png
				if(goodsClass.getPhoto()!=null){
					ezs_accessory photo = new ezs_accessory();
					photo.setId(goodsClass.getPhoto().getId());
					photo.setName(goodsClass.getPhoto().getName());
					photo.setPath(goodsClass.getPhoto().getPath());
					goodsClassTemp.setPhoto(photo);
				}
				goodClassListTemp.add(goodsClassTemp);
			}
			log.info("查询三级商品类别信息end。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
			return goodClassListTemp;
		}else{
			return null;
		}
	}
	
	private List<Advices> getSubscribeList(HttpServletRequest request){
		String path = request.getServletContext().getContextPath();
		System.out.println(path);
		return null;
	}
}
