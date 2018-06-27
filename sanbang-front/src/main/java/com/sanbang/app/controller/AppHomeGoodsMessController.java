package com.sanbang.app.controller;

import java.io.UnsupportedEncodingException;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_column;
import com.sanbang.bean.ezs_customized;
import com.sanbang.bean.ezs_customized_record;
import com.sanbang.bean.ezs_ezssubstance;
import com.sanbang.bean.ezs_goods_class;
import com.sanbang.bean.ezs_user;
import com.sanbang.index.service.CustomerService;
import com.sanbang.index.service.CustomizedService;
import com.sanbang.index.service.GoodsClassService;
import com.sanbang.index.service.IndustryInfoService;
import com.sanbang.index.service.RecommendGoodsService;
import com.sanbang.index.service.ReportEssayServer;
import com.sanbang.upload.sevice.impl.FileUploadServiceImpl;
import com.sanbang.utils.FieldFilterUtil;
import com.sanbang.utils.Page;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.Advices;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.ExPage;
import com.sanbang.vo.GoodsInfo;
import com.sanbang.vo.HomePageMessInfo;
import com.sanbang.vo.UserInfoMess;
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
	private IndustryInfoService industryInfoService;
	@Autowired
	private ReportEssayServer reportEssayServer;
	@Autowired
	private CustomerService customerService;
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
	//////////////////////////////////采购定制页面初始化\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * 不启用
	 * 获取商品分类（一二三级别）
	 * @author zhaibin
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getAllGoodClassGroupByLevel") 
	@ResponseBody
	public Object getAllGoodClassGroupByLevel(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> mmp = null;
		List<ezs_goods_class> goodsClassList = null;
		List<ezs_goods_class> secondGoodsClassList = new ArrayList<>();
		Result rs = null;
		mmp = this.goodsClassService.queryChildNodes();
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			goodsClassList = (List<ezs_goods_class>) mmp.get("Obj");
			//过滤字段
			for (ezs_goods_class goodclass : goodsClassList) {
				ezs_goods_class goodclassSecond = new ezs_goods_class();
				List<ezs_goods_class> thirdGoodClassList = new ArrayList<>();
				List<ezs_goods_class> thirdGoodClassListTemp = new ArrayList<>();
				goodclassSecond.setId(goodclass.getId());
				goodclassSecond.setName(goodclass.getName());
				//获取子节点集合
				thirdGoodClassList = goodclass.getChildNodeList();
				for (ezs_goods_class thirdGoodClass : thirdGoodClassList) {
					ezs_goods_class thirdGoodClassTemp = new ezs_goods_class();
					thirdGoodClassTemp.setId(thirdGoodClass.getId());
					//防止因name为null导致字段不显示，传入String对象
					thirdGoodClassTemp.setName(new String(thirdGoodClass.getName()==null?"  ":thirdGoodClass.getName()));
					thirdGoodClassListTemp.add(thirdGoodClassTemp);
				}
				goodclassSecond.setChildNodeList(thirdGoodClassListTemp);
				secondGoodsClassList.add(goodclassSecond);
			}
			rs = Result.success();
			rs.setObj(secondGoodsClassList);
			rs.setMsg(mmp.get("Msg").toString());
		}else{
			rs = Result.failure();
			rs.setObj(secondGoodsClassList);
			rs.setMsg(mmp.get("Msg").toString());
		}
		return rs;
	}
	/**
	 * 不启用
	 * 颜色列表+形态列表
	 * @author zhaibin
	 * @return
	 */
	@RequestMapping("/getColorAndFormList") 
	@ResponseBody
	public Object getColorAndFormList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> mmp = null;
		Result rs = null;
		mmp = this.goodsClassService.queryGoodColorAndForm();
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			rs = Result.success();
			rs.setMsg(mmp.get("Msg").toString());
			mmp.remove("ErrorCode");
			mmp.remove("Msg");
			rs.setObj(mmp);
		}else{
			rs = Result.failure();
			rs.setObj(" ");
			rs.setMsg(mmp.get("Msg").toString());
		}
		return rs;
	}
	/**
	 * 启用
	 * 初始化采购定制页面
	 * @author zhaibin
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/initCustomized") 
	@ResponseBody
	public Object initCustomized(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> mmp = null;
		Map<String, Object> mmpp = null;
		Map<String, Object> addressMP = null;
		Map<String, Object> resultMP = new HashMap<>();
		List<ezs_goods_class> secondGoodClassListTemp = new ArrayList<>();
		Result rs = null;
		ezs_user user = RedisUserSession.getUserInfoByKeyForApp(request);
		if(user==null){
			rs = Result.failure();
			rs.setMsg("用户未登录");
			rs.setObj(new Object());
			return rs;
		}
		//二三级商品类别分类
		mmp = this.goodsClassService.queryChildNodes();
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			List<ezs_goods_class> secondGoodClassList = (List<ezs_goods_class>) mmp.get("Obj");
			//过滤字段
			for (ezs_goods_class goodclass : secondGoodClassList) {
				ezs_goods_class goodclassSecond = new ezs_goods_class();
				List<ezs_goods_class> thirdGoodClassList = new ArrayList<>();
				List<ezs_goods_class> thirdGoodClassListTemp = new ArrayList<>();
				goodclassSecond.setId(goodclass.getId());
				goodclassSecond.setName(goodclass.getName());
				//获取子节点集合
				thirdGoodClassList = goodclass.getChildNodeList();
				for (ezs_goods_class thirdGoodClass : thirdGoodClassList) {
					ezs_goods_class thirdGoodClassTemp = new ezs_goods_class();
					thirdGoodClassTemp.setId(thirdGoodClass.getId());
					//防止因name为null导致字段不显示，传入String对象
					thirdGoodClassTemp.setName(new String(thirdGoodClass.getName()==null?"  ":thirdGoodClass.getName()));
					thirdGoodClassListTemp.add(thirdGoodClassTemp);
				}
				goodclassSecond.setChildNodeList(thirdGoodClassListTemp);
				secondGoodClassListTemp.add(goodclassSecond);
			}
			resultMP.put("GoodsClassList", secondGoodClassListTemp);
		}else{
			resultMP.put("GoodsClassList", secondGoodClassListTemp);
		}
		//颜色形态
		mmpp = this.goodsClassService.queryGoodColorAndForm();
		Integer ErrorCodeTwo = (Integer)mmpp.get("ErrorCode");
		if(ErrorCodeTwo!=null&&ErrorCodeTwo.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			resultMP.put("ColorAndForm", mmpp);
		}else{
			resultMP.put("ColorAndForm", mmpp);
		}
		//在线用户地址信息
		addressMP = this.customerService.getUserMessByUser(user);
		Integer ErrorCodeTree = (Integer)addressMP.get("ErrorCode");
		if(ErrorCodeTree!=null&&ErrorCodeTree.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			UserInfoMess uim = (UserInfoMess) addressMP.get("Obj");
			resultMP.put("UserInfo", uim);
		}else{
			resultMP.put("UserInfo", new Object());
		}
		rs = Result.success();
		rs.setObj(resultMP);
		rs.setMsg("查询成功");
		return rs;
	}
	//////////////////////////////////////////////首页相关内容\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
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
		response.setCharacterEncoding("UTF-8");
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
		List<GoodsInfo> glist = null;
		Result rs = null;
		Page page = null;
		if(currentPage==null){
			currentPage = "1";
		}
		mmp = this.recommendGoodsService.goodsIntroduceTwo(currentPage);
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			glist = (List<GoodsInfo>) mmp.get("Obj");
			List<GoodsInfo> glistTemp = new ArrayList<>();
			for (GoodsInfo gVo : glist) {
				GoodsInfo goodInfo = new GoodsInfo();
				goodInfo.setId(gVo.getId());
				goodInfo.setName(gVo.getName());
				if(gVo.getMainPhoto()!=null)
					goodInfo.setMainPhoto(gVo.getMainPhoto());
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
		//mmp = this.industryInfoService.getAllIndustryInfoByParentKinds(Long.valueOf(12), currentPage);
		mmp = this.industryInfoService.getEssayBySecondTheme(Long.valueOf(12), currentPage);
		ExPage page = (ExPage) mmp.get("Page");
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			elist = (List<ezs_ezssubstance>) mmp.get("Obj");
			List<ezs_ezssubstance> eelist = new ArrayList<>();
			for (ezs_ezssubstance eze : elist) {
				//去除不必要信息
				ezs_column parentColumn = new ezs_column(); 
				parentColumn.setName(eze.getParentColumn().getName());
				eze.setParentColumn(parentColumn);
				eelist.add(eze);
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
	/**
	 * 广告-活动展示时可启用，
	 * @author zhaibin
	 * @return
	 */
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
		//mmp = goodsClassService.queryThirdGoodsClass("3");
		mmp = goodsClassService.queryGoodClassIncludePic();
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			goodClassList = (List<ezs_goods_class>) mmp.get("Obj");
			for (ezs_goods_class goodsClass : goodClassList) {
				ezs_accessory photo = new ezs_accessory();
				photo.setId(goodsClass.getPhoto().getId());
				photo.setName(goodsClass.getPhoto().getName());
				photo.setPath(goodsClass.getPhoto().getPath());
				goodsClass.setPhoto(photo);
				goodClassListTemp.add(goodsClass);
			}
			log.info("查询三级商品类别信息end。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
			return goodClassListTemp;
		}else{
			return null;
		}
	}
	/**
	 * 主题展示（暂时为一张静态图片）
	 * @author zhaibin
	 * @param request
	 * @return
	 */
	private List<Advices> getSubscribeList(HttpServletRequest request){
		//String path = request.getServletContext().getContextPath();
		List<Advices> adviceList = new ArrayList<>();
		Advices advices = new Advices();
		//advices.setPath(path+"/resource/indeximg/首页-1_13.png");
		String str = "http://10.10.10.148/front/resource/indeximg/首页-1_13.png";
		String strTemp = "";
		// 2.以UTF-8编码方式获取str的字节数组，再以默认编码构造字符串
		try {
			strTemp = new String(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("字符串转码失败");
			log.error(e.getMessage());
		}
		advices.setPath(strTemp);
		advices.setpName("首页-1_13.png");
		advices.setLink("");
		advices.setContent("标题展示");
		adviceList.add(advices);
		log.info("图片：/resource/indeximg/首页-1_13.png");
		return adviceList;
	}
	//////////////////////////////////////文章报告展示相关\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * 获取首页展示文档二级标题（首页展示点击触发）
	 * 展示二级标题（日评-周评-月评）及相关分析的标题
	 * @author zhaibin
	 * @param request
	 * @param response
	 * @param type 12-行情分析；研究报告
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getTheSecondThemeOfEssayReport")
	@ResponseBody
	public Object getTheSecondThemeOfEssayReport(HttpServletRequest request,HttpServletResponse response,Long type){
		Map<String, Object> mmp = null;
		List<ezs_column> columnListTemp = new ArrayList<>();
		Result rs = null;
		//mmp = this.reportEssayServer.getSecondTheme(Long.valueOf("12"));
		if(type==null){
			rs = Result.failure();
			rs.setObj(columnListTemp.add(new ezs_column()));
			rs.setMsg("类型不能为NULL");
			return rs;
		}
		mmp = this.reportEssayServer.getSecondTheme(type);
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			List<ezs_column> columnList = (List<ezs_column>) mmp.get("Obj");
			//进行显示字段的过滤
			FieldFilterUtil<ezs_column> fieldFilterUtil = new FieldFilterUtil<>();
			String filterFields = "columnLevel,id,name,title";
			try {
				//字段过滤公共方法
				columnListTemp = fieldFilterUtil.getFieldFilterList(columnList, filterFields,ezs_column.class);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rs = Result.success();
			rs.setObj(columnListTemp);
			rs.setMsg(mmp.get("Msg").toString());
		}else{
			rs = Result.failure();
			rs.setObj(columnListTemp.add(new ezs_column()));
			rs.setMsg(mmp.get("Msg").toString());
		}
		return rs;
	}
	/**
	 * 获取报告标题（例如：日评下展示）
	 * 根据二级标题展示报告文章的标题（分页展示）
	 * @author zhaibin
	 * @param request
	 * @param response
	 * @param parentId
	 * @param currentPage
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getReportEssayTheme")
	@ResponseBody
	public Object getReportEssayTheme(HttpServletRequest request,HttpServletResponse response,Long parentId,int currentPage){
		Map<String, Object> mmp = null;
		List<ezs_ezssubstance> substanceListTemp = new ArrayList<>();
		Result rs = null;
		if(currentPage<=0)
			currentPage = 1;
		mmp = this.reportEssayServer.getReportEssayTheme(parentId,currentPage);
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			List<ezs_ezssubstance> substanceList = (List<ezs_ezssubstance>) mmp.get("Obj"); 
			rs = Result.success();
			//进行字段过滤
			FieldFilterUtil<ezs_ezssubstance> fieldFilterUtil = new FieldFilterUtil<>();
			String filterFields = "addTime,id,meta,name";
			try {
				//字段过滤公共方法
				substanceListTemp = fieldFilterUtil.getFieldFilterList(substanceList, filterFields,ezs_ezssubstance.class);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*for (ezs_ezssubstance ss : substanceList) {
				ezs_ezssubstance substanceTemp = new ezs_ezssubstance();
				substanceTemp.setId(ss.getId());
				substanceTemp.setAddTime(ss.getAddTime());
				substanceTemp.setMeta(ss.getMeta());
				substanceTemp.setName(ss.getName());
				substanceListTemp.add(substanceTemp);
			}*/
			rs.setObj(substanceListTemp);
			rs.setMsg(mmp.get("Msg").toString());
		}else{
			rs = Result.failure();
			rs.setObj(substanceListTemp);
			rs.setMsg(mmp.get("Msg").toString());
		}
		return rs;
	}
	/**
	 * 根据文章报告ID获取相关内容
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getReportEssayById")
	@ResponseBody
	public Object getReportEssayById(HttpServletRequest request,HttpServletResponse response,Long id){
		Map<String, Object> mmp = null;
		Result rs = null;
		mmp = this.reportEssayServer.getReportEssayContext(id);
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			ezs_ezssubstance substance = (ezs_ezssubstance) mmp.get("Obj");
			if(substance==null){
				rs = Result.success();
				rs.setMsg("查询内容不存在");
				rs.setObj(new ezs_ezssubstance());
				return rs;
			}
			//进行字段过滤
			FieldFilterUtil<ezs_ezssubstance> fieldFilterUtil = new FieldFilterUtil<>();
			String filterFields = "addTime,id,meta,name,content";
			ezs_ezssubstance substanceTemp = new ezs_ezssubstance(); 
			rs = Result.success();
			try {
				//字段过滤公共方法
				substanceTemp = fieldFilterUtil.getFieldFilter(substance, filterFields, ezs_ezssubstance.class);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rs.setObj(substanceTemp);
			rs.setMsg(mmp.get("Msg").toString());
		}else{
			rs = Result.failure();
			rs.setObj(new ezs_ezssubstance());
			rs.setMsg(mmp.get("Msg").toString());
		}
		return rs;
	}

}
