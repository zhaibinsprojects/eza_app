package com.sanbang.goods.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.addressmanage.service.AddressService;
import com.sanbang.bean.ezs_address;
import com.sanbang.bean.ezs_area;
import com.sanbang.bean.ezs_customized;
import com.sanbang.bean.ezs_dict;
import com.sanbang.bean.ezs_documentshare;
import com.sanbang.bean.ezs_dvaluate;
import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goods_class;
import com.sanbang.bean.ezs_goodscart;
import com.sanbang.bean.ezs_orderform;
import com.sanbang.bean.ezs_user;
import com.sanbang.buyer.service.OrderEvaluateService;
import com.sanbang.dao.ezs_addressMapper;
import com.sanbang.dao.ezs_areaMapper;
import com.sanbang.dao.ezs_goodsMapper;
import com.sanbang.dao.ezs_goods_classMapper;
import com.sanbang.dao.ezs_goodscartMapper;
import com.sanbang.dict.service.DictService;
import com.sanbang.goods.service.ChildCompanyGoodsService;
import com.sanbang.goods.service.GoodsService;
import com.sanbang.upload.sevice.FileUploadService;
import com.sanbang.upload.sevice.impl.FileUploadServiceImpl;
import com.sanbang.utils.CommUtil;
import com.sanbang.utils.Page;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.CurrencyClass;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.GoodsInfo;
import com.sanbang.vo.goods.GoodsVo;

@Controller
@RequestMapping("/goods")
public class GoodsController {
	@Autowired
	private GoodsService goodsService;
	@Resource(name = "fileUploadService")
	private FileUploadService fileUploadService;
	@Autowired
	private OrderEvaluateService orderEvaluateService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private ezs_areaMapper ezs_areaMapper;
	@Autowired
	private DictService dictService;
	@Autowired
	private ezs_goodscartMapper ezs_goodscartMapper;
	@Autowired
	private ezs_goodsMapper ezs_goodsMapper;
	@Autowired
	private ezs_goods_classMapper goodsClassMapper;
	@Autowired
	private com.sanbang.dao.ezs_orderformMapper ezs_orderformMapper;
	@Autowired
	private ezs_addressMapper addressMapper;
	@Autowired
	private ChildCompanyGoodsService childCompanyGoodsService;
	
	private static int num = 100;
	
	// 日志
	private static Logger log = Logger.getLogger(FileUploadServiceImpl.class);
	/**
	 * 查询货品详情
	 * @param request
	 * @param id 货品id
	 * @return
	 */
	@RequestMapping("/goodsDetail")
	@ResponseBody
	public Result getGoodsDetail(HttpServletRequest request,Long id){
		Result result = new Result();
		ezs_goods goods = goodsService.getGoodsDetail(id);
		if(null != goods){
			result.setObj(goods);
			result.setMsg("查询成功！");
			result.setSuccess(true);
		}else{
			result.setMsg("查询失败！");
			result.setSuccess(false);
		}
		return result;
	}
	/**
	 * 查询货品评价列表
	 * @param request
	 * @param id 货品id
	 * @return
	 */
	@RequestMapping("/listForEvaluate")
	@ResponseBody
	public Result listForEvaluate(HttpServletRequest request,Long id,int pageNow){
		Result result = new Result();
		try {
			//用户校验begin
			ezs_user upi=RedisUserSession.getLoginUserInfo(request);
			long userid=0;
			if(null==upi){
				upi=RedisUserSession.getUserInfoByKeyForApp(request);
			}
			userid=null==upi?0:upi.getId();
			//用户校验end
			
			List<ezs_dvaluate>  dvaluatelist=orderEvaluateService.getEvaluateList(pageNow,id);
			GoodsVo  goodsvo=goodsService.getgoodsinfo(id,userid);
			Map<String, Object> map=new HashMap<>();
			map.put("list", dvaluatelist);
			map.put("highp", goodsvo.getHighp());
			result.setObj(map);
			result.setSuccess(true);
			result.setMsg("查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("查询失败");
		}
		
		return result;
	}
	/**
	 * 当第一次收藏时，是insert,取消收藏时是update更新状态（货品收藏）
	 * @param request
	 * @param goodId	商品id
	 * @return
	 */
	@RequestMapping("/updateShare")
	@ResponseBody
	public Result updateShare(HttpServletRequest request,Long goodId){
		Result result = new Result();
		ezs_user user = RedisUserSession.getLoginUserInfo(request);
		if(null==user){
			result.setMsg("用户未登陆");
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			return result;
		}
		if(null != goodId){
			ezs_documentshare share = goodsService.getCollect(goodId,user.getId());
			if(null != share){
				if(share.getDeleteStatus().equals(true)){
					goodsService.updateCollect(goodId,user.getId(),false);
					result.setMsg("取消收藏成功");
					result.setSuccess(true);
					result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				}else{
					goodsService.updateCollect(goodId,user.getId(),true);
					result.setMsg("收藏成功");
					result.setSuccess(true);
					result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				}
			}else{
				try{
					goodsService.insertCollect(goodId,user.getId());
					result.setMsg("收藏成功");
					result.setSuccess(true);
					result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				}catch(Exception e){
					e.printStackTrace();
					result.setMsg("系统错误");
					result.setSuccess(false);
					result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
				}
			}
		}else{
			result.setMsg("收藏出错");
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
		}
		return result;
	}
	
	
	/**
	 * 预约定制采购单列表（待完善）
	 * @param request
	 * @param user_id
	 * @return
	 */
	@RequestMapping("/customizedList")
	@ResponseBody
	public Result customizedList(HttpServletRequest request,Long user_id){
		List<ezs_customized> list = new ArrayList<ezs_customized>();
		Result result = new Result();
		list =  goodsService.customizedList(user_id);
		if(null != list && list.size()>0){
			result.setObj(list);
			result.setSuccess(true);
			result.setMsg("查询成功");
		}else{
			result.setSuccess(false);
			result.setMsg("采购单为空");
		}
		return result;
	}
	
	/**
	 * 预约预定（已改）
	 * @param request
	 * @param customized 预约实体类（前端传的参数：描述remark，数量pre_num，要货时间pre_time，采购预算budget，商品id：goods_id）
	 * 参数是这几个参数，需要跟前端约定字段名称（必须是这几个名称，否则塞不进实体中，跟前端以及app商量一哈儿）
	 * @return
	 */
	@RequestMapping("/insertCustomized")	
	@ResponseBody
	public Result insertCustomized(HttpServletRequest request,ezs_customized customized){
		Result result = Result.failure();
		ezs_user user = RedisUserSession.getLoginUserInfo(request);
		if (user == null) {
			result = Result.failure();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}else{ 
			Long auditingusertype_id = user.getEzs_store().getAuditingusertype_id();
			ezs_dict dictCode = dictService.getDictByThisId(auditingusertype_id);
			if(dictCode.getSequence()<=3){
			if(user.getEzs_store().getStatus()!=2){
				result = Result.failure();
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("您还未完成实名认证，请去个人中心完成实名认证！");
				return result;
			}
			}
		}
		String goodsId = customized.getGoods_id().toString();
		if(null != goodsId && "".equals(goodsId)){
			ezs_goods goods = goodsService.selectByPrimaryKey(Long.valueOf(goodsId));
			if(null != goods){
				customized.setAddress(goods.getAddess());
				customized.setColour_id(goods.getColor_id());//这一块儿呢，有的说要存名称，哥们儿我觉得要存id（因为查询效率问题）
				customized.setDensity(goods.getDensity());	//还有这一块儿，goods设计商品表时这个字段是字符类型，预约预定这一块儿同样的字段却又是浮点型，何故？？？
				customized.setPurpose(goods.getPurpose());
				customized.setShape_id(goods.getForm_id());
				//剩下的需要塞的字段，劳烦彬哥了
			}else{
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("改商品不存在");
				result.setSuccess(false);
				return result;
			}
			try{
				if(null != customized.getRemark() && null != customized.getRemark() && null != customized.getPre_num() && null != customized.getPre_time()){
					//加入预约定制
					Map<String,Object> map = goodsService.insertCustomized(customized,user);
					if(map.get("Msg").equals("插入成功")){
						result.setMsg("插入成功");
						result.setSuccess(true);
					}
					if(map.get("Msg").equals("插入失败")){
						result.setMsg("插入失败");
						result.setSuccess(false);
					}
				}else{
					result.setMsg("请将信息填写完整");
					result.setSuccess(false);
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				}
			}catch(Exception e){
				result.setMsg("插入出错，数据异常");
				result.setSuccess(false);
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			}
		}else{
			result.setMsg("参数为空，请重新添加数据");
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * 同类货品（待完善）
	 * @param id 商品类别id
	 * @return
	 */
	@RequestMapping("/listForGoods")
	@ResponseBody
	public Result listForGoods(Long goodClass_id){
		Result result = new Result();
		List<GoodsVo> list = new ArrayList<GoodsVo>();
		if(null != goodClass_id){
			list = goodsService.listForGoods(goodClass_id);
			result.setObj(list);
			result.setSuccess(true);
			result.setMsg("返回成功");
		}else{
			result.setMsg("返回失败");
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * 多条件查询
	 * @param request
	 * @param areaId	地区id
	 * @param typeId	品类id
	 * @param defaultId	默认
	 * @param inventory	库存量
	 * @param colorId	颜色id
	 * @param formId	形态id
	 * @param source	来源
	 * @param purpose	用途
	 * @param density	密度字符串数组
	 * @param cantilever	悬臂梁缺口冲击
	 * @param freely	简支梁缺口冲击
	 * @param lipolysis	熔融指数（溶脂）
	 * @param ash	灰分
	 * @param water	水分
	 * @param tensile	拉伸强度
	 * @param crack	断裂伸长率
	 * @param bending	弯曲强度
	 * @param flexural	弯曲模量
	 * @param burning	燃烧等级
	 * @param isProtection	是否环保
	 * @param goodsName	搜索框条件：商品名称
	 * @param pageNow 当前页
	 * @return
	 */
	@RequestMapping("/queryGoodsList")
	@ResponseBody
	public Result queryGoodsList(HttpServletRequest request,
			@RequestParam(name = "areaId",required=true)String areaId,
			@RequestParam(name = "typeId",required=false)String typeId,
			@RequestParam(name = "defaultId",required=false)String defaultId,	//默认值
			@RequestParam(name = "inventory",required=false)String inventory,	//库存量
			@RequestParam(name = "sales", required = false) String sales,//销量    sales=1    按销量排序-（默认、库存、销量 此三种排序方式互斥）
			@RequestParam(name = "colorId",required=false)String colorId,
			@RequestParam(name = "formId",required=false)String formId,
			@RequestParam(name = "source",required=false)String source,
			@RequestParam(name = "purpose",required=false)String purpose,
			@RequestParam(name = "price",required=false)String price,	//价格区间
			@RequestParam(name = "density",required=false)String density,	//密度
			@RequestParam(name = "cantilever",required=false)String cantilever,	//悬臂梁缺口冲击
			@RequestParam(name = "freely",required=false)String freely,	//简支梁缺口冲击
			@RequestParam(name = "lipolysis",required=false)String lipolysis,	//熔融指数（溶脂）
			@RequestParam(name = "ash",required=false)String ash,	//灰分
			@RequestParam(name = "water",required=false)String water,	//水分
			@RequestParam(name = "tensile",required=false)String tensile,	//拉伸强度
			@RequestParam(name = "crack",required=false)String crack,	//断裂伸长率
			@RequestParam(name = "bending",required=false)String bending,	//弯曲强度
			@RequestParam(name = "flexural",required=false)String flexural,	//弯曲模量
			@RequestParam(name = "burning",required=false)String burning,	//燃烧等级
			@RequestParam(name = "goodsName",required=false)String goodsName,
			@RequestParam(name = "pageNow", defaultValue = "1") int pageNow){
		log.info("GoodsController---商品列表查询--接口可能调用错了XXXXXXXXXXXXXXXXXXXXXXXXXXX");
		Result result = Result.failure();
		try{
		List<Long> areaList = new ArrayList<Long>();
		if(!"".equals(areaId) && null != areaId){
			Long area = Long.valueOf(areaId);
			List<Long> listId = goodsService.queryChildId(area);
			if(null != listId && listId.size() != 0){
				List<Long> listIds = goodsService.queryChildIds(listId);
				if(null != listIds && listIds.size() != 0){   //area是省
					areaList = listIds;
				}else{	//area是市
					areaList = listId;
				}
			}else{	//area是县、区
				areaList.add(area);
			}
		}
		String[] typeIds = null;
		String[] colorIds = null;
		String[] formIds = null;
		String[] prices = null;
		//重要参数
		String[] densitys = null;
		String[] cantilevers = null;
		String[] freelys = null;
		String[] lipolysises = null;
		String[] ashs = null;
		String[] waters = null;
		String[] tensiles = null;
		String[] cracks = null;
		String[] bendings = null;
		String[] flexurals = null;
		String[] burnings = null;
		if(null != typeId && !"".equals(typeId)){
			typeIds = typeId.split(",");
		}
		if(null != colorId && !"".equals(colorId)){
			colorIds = colorId.split(",");
		}
		if(null != formId && !"".equals(formId)){
			formIds = formId.split(",");
		}
		if(null != price && !"".equals(price)){
			prices = price.split(",");
		}
		//重要参数
		if(null != density && !"".equals(density)){
			densitys = density.split(",");
		}
		if(null != cantilever && !"".equals(cantilever)){
			cantilevers = cantilever.split(",");
		}
		if(null != freely && !"".equals(freely)){
			freelys = freely.split(",");
		}
		if(null != lipolysis && !"".equals(lipolysis)){
			lipolysises = lipolysis.split(",");
		}
		if(null != ash && !"".equals(ash)){
			ashs = ash.split(",");
		}
		if(null != water && !"".equals(water)){
			waters = water.split(",");
		}
		if(null != tensile && !"".equals(tensile)){
			tensiles = tensile.split(",");
		}
		if(null != crack && !"".equals(crack)){
			cracks = crack.split(",");
		}
		if(null != bending && !"".equals(bending)){
			bendings = bending.split(",");
		}
		if(null != flexural && !"".equals(flexural)){
			flexurals = flexural.split(",");
		}
		if(null != burning && !"".equals(burning)){
			burnings = burning.split(",");
		}
		int pageStart = (pageNow - 1) * 10;	//起始页，每页10条
		
			List<GoodsInfo> list = new ArrayList<GoodsInfo>();
			list = goodsService.queryGoodsList(areaList,typeIds,defaultId,inventory,sales,colorIds,formIds,source,purpose,prices,densitys,cantilevers,freelys,
					lipolysises,ashs,waters,tensiles,cracks,bendings,flexurals,burnings,goodsName,pageStart);
			if(null != list && list.size() > 0){
				result.setSuccess(true);
				result.setMsg("筛选成功");
				result.setObj(list);
				Page page = new Page(list.size(), pageNow);
				result.setMeta(page);
			}else{
				result.setSuccess(true);
				result.setObj(list);
				result.setMsg("数据为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("查询异常，数据出错");
		}
		return result;
	}
	
	/**
	 * 根据地区名返回id
	 * @param request
	 * @return
	 */
	@RequestMapping("/areaToId")
	@ResponseBody
	public Result areaToId(HttpServletRequest request,String areaName){
		Result result = Result.failure();
		if(null != areaName && !"".equals(areaName)){
			try{
				List<Long> ids = goodsService.areaToId(areaName);
				//直辖市
				if(areaName.contains("北京")||areaName.contains("上海")||areaName.contains("天津")||areaName.contains("重庆")){
					if(null != ids && ids.size()==2){
						Long id1 = ids.get(0);
						Long id2 = ids.get(1);
						if(id1<id2){
							result.setObj(id2);
						}else{
							result.setObj(id1);
						}
					}
					result.setSuccess(true);
					result.setMsg("返回成功");
				}else if(null != ids && ids.size() != 0){
					result.setObj(ids.get(0));
					result.setSuccess(true);
					result.setMsg("返回成功");
				}else{
					result.setObj("");
					result.setSuccess(true);
					result.setMsg("数据为空");
				}
			}catch(Exception e){
				e.printStackTrace();
				result.setSuccess(false);
				result.setMsg("查询出错，数据异常");
			}
		}else{
			result.setMsg("参数为空,请传入正确参数");
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * 返回其他筛选所需的条件:颜色 形态
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/colorAndFormList")
	@ResponseBody
	public Result colorAndFormList(HttpServletRequest request){
		Result result = Result.failure();
		try{
			List<CurrencyClass> colorList = goodsService.colorList();	//颜色
			List<CurrencyClass> formList = goodsService.formList();	//形态
			HashMap<String, Object> map1 = new HashMap<String,Object>();
			HashMap<String,Object> map2 = new HashMap<String,Object>();
			map1.put("second", colorList);
			map1.put("type", "颜色");
			map2.put("second", formList);
			map2.put("type", "形态");
			List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
			list.add(map1);
			list.add(map2);
			if(colorList.size() > 0 && null == formList){
				result.setMsg("颜色有值，形态为空");
				result.setObj(list);
				result.setSuccess(true);
			}
			if(null == colorList && formList.size()>0){
				result.setMsg("形态有值，颜色为空");
				result.setObj(list);
				result.setSuccess(true);
			}
			if(formList.size()>0 && colorList.size() > 0){
				result.setMsg("颜色形态都有值");
				result.setObj(list);
				result.setSuccess(true);
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setMsg("查询出错，数据异常");
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * 上传发票图片，返回url
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/uploadinvoice",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Result uploadImg(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Result result=Result.failure();
		try {
			Map<String , Object> map=fileUploadService.uploadFile(request,0,0,10*1024*1024l);
			if("000".equals(map.get("code"))){
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				result.setMsg("上传图片成功");
				Map<String, Object> map1=new HashMap<>();
				map1.put("url", map.get("url"));	//返回前台上传的图片路径
				result.setSuccess(true);
				result.setObj(map1);
				return result;
			}else{
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("上传图片失败");
				result.setObj("");
				result.setSuccess(false);
				return result;
			}
		} catch (Exception e) {
			log.info("文件：上传接口调用失败"+e.toString());
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("上传图片失败");
			result.setObj("");
			result.setSuccess(false);
			return result;
		} 
	}
	
	/**
	 * 添加购物车
	 * @author zhaibin
	 * @param request
	 * @param response
	 * @param goodsId
	 * @param count
	 * @return
	 */
	@RequestMapping(value="/addToSelfGoodCar")
	@ResponseBody
	public Object addToSelfGoodCar(HttpServletRequest request,HttpServletResponse response,Long goodsId,Double count){
		Map<String, Object> mmp = null;
		Result rs = null;
		ezs_user user = RedisUserSession.getLoginUserInfo(request);
		if (user == null) {
			rs = Result.failure();
			rs.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			rs.setMsg("用户未登录");
			return rs;
		}/*else{ 
			Long auditingusertype_id = user.getEzs_store().getAuditingusertype_id();
			ezs_dict dictCode = dictService.getDictByThisId(auditingusertype_id);
			if(dictCode.getSequence()<=3){
			if(user.getEzs_store().getStatus()!=2){
				rs = Result.failure();
				rs.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				rs.setMsg("您还未完成实名认证，请去个人中心完成实名认证！");
				return rs;
			}
			}
		}*/
		ezs_goodscart goodsCart = new ezs_goodscart();
		goodsCart.setCount(count);
		goodsCart.setGoods_id(goodsId);
		try {
			mmp = this.goodsService.addGoodsCartFunc(goodsCart, user);
			Integer ErrorCode = (Integer) mmp.get("ErrorCode");
			if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
				rs = Result.success();
				rs.setMsg(mmp.get("Msg").toString());
			}else{
				rs = Result.failure();
				rs.setMsg(mmp.get("Msg").toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			rs = Result.failure();
			rs.setMsg("数据传递有误");
		}
		return rs;
	}
	/**
	 * 编辑购物车
	 * @author han
	 * @param request
	 * @param response
	 * @param goodsCartId	当前购物车id
	 * @param count	  货品数量
	 * @return
	 */
	@RequestMapping(value="/editToSelfGoodCar")
	@ResponseBody
	public Result editToSelfGoodCar(HttpServletRequest request,HttpServletResponse response,Long goodsCartId,Double count){
		Map<String, Object> map = null;
		Result result = Result.failure();
		ezs_user user = RedisUserSession.getUserInfoByKeyForApp(request);
		if (null == user) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		try {
			map = goodsService.editGoodsCart(goodsCartId,count,user);
			Integer ErrorCode = (Integer) map.get("ErrorCode");
			String msg = map.get("Msg").toString();
			Map<String,Object> map1=new HashMap<>();
			if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_PARAM_ERROR)&&msg.equals("参数传递有误")){
				result.setSuccess(false);
			}else{
				result.setSuccess(true);
			}
			result.setMsg(map.get("Msg").toString());
			map1.put("inventory", map.get("inventory"));
			result.setObj(map1);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("数据传递有误");
		}
		return result;
	}
	//删除购物车（多选删除）
	@RequestMapping(value="/deleteToSelfGoodCar")
	@ResponseBody
	public Result deleteToSelfGoodCar(HttpServletRequest request,HttpServletResponse response,String goodsCartId){
		String[] ids = goodsCartId.split(",");
		Result result = new Result();
		ezs_user user = RedisUserSession.getLoginUserInfo(request);
		if (null == user) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		try{
			Map<String,Object> map = goodsService.deleteGoodCar(ids);
			Integer ErrorCode = (Integer) map.get("ErrorCode");
			if(null != ErrorCode && ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
				result.setSuccess(true);
				result.setMsg(map.get("Msg").toString());
			}else{
				result.setSuccess(false);
				result.setMsg(map.get("Msg").toString());
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setMsg("数据传递有误");
		}
		return result;
	}
	
	/**
	 * 立即购买(商品)
	 * @author zhaibin
	 * @param request
	 * @param response
	 * @param WeAddressId
	 * @param goodsId
	 * @param count
	 * @return
	 */
	@RequestMapping(value="/dealImmediatelyBuyGood")
	@ResponseBody
	public Object dealImmediatelyBuyGood(HttpServletRequest request,HttpServletResponse response,Long WeAddressId,Long goodsId,Double count){
		Map<String, Object> mmp = null;
		Result rs = Result.failure();
		try {
			ezs_user user = RedisUserSession.getUserInfoByKeyForApp(request);
			if (user == null) {
				rs = Result.failure();
				rs.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				rs.setMsg("用户未登录");
				return rs;
			}else{ 
				Long auditingusertype_id = user.getEzs_store().getAuditingusertype_id();
				ezs_dict dictCode = dictService.getDictByThisId(auditingusertype_id);
				if(dictCode.getSequence()<=3){
				if(user.getEzs_store().getStatus()!=2){
					rs = Result.failure();
					rs.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					rs.setMsg("您还未完成实名认证，请去个人中心完成实名认证！");
					return rs;
				}
				}
			}
			ezs_orderform orderForm = new ezs_orderform();
			ezs_goods buyGoods = null;
			// 修改订单号生成规则
			try{
				buyGoods = this.ezs_goodsMapper.selectByPrimaryKey(goodsId);
				orderForm.setOrder_no(createOrderNo(buyGoods));
			}catch(Exception e){
				e.printStackTrace();
				log.info("订单号生成失败");
			}
			if(this.childCompanyGoodsService.isChildCompanyGood(buyGoods)){
				mmp = this.childCompanyGoodsService.immediateAddOrderFormFunc(orderForm, user, "GOODS", WeAddressId, buyGoods, count);
			}else{
				mmp = this.goodsService.immediateAddOrderFormFunc(orderForm, user, "GOODS", WeAddressId, buyGoods, count);
			}
			Integer ErrorCode = (Integer) mmp.get("ErrorCode");
			if (ErrorCode != null && ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)) {
				rs = Result.success();
				rs.setMsg(mmp.get("Msg").toString());
			} else {
				rs = Result.failure();
				rs.setMsg(mmp.get("Msg").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setSuccess(false);
			rs.setMsg("提交订单失败");
		}
		return rs;
	}
	
	/**
	 * 添加订单，在此可进行多个商品提交订单，
	 * 针对单个订单进行库存校验并更新库存信息，不足的返回商品不足信息并回撤库信息，
	 * @author zhaibin
	 * @param request
	 * @param response
	 * @param orderForm(ezs_orderform类型的JSON串)
	 * @param goodCartIds(购物车ID)
	 * @return
	 */
	@RequestMapping(value="/AddGoodsToSelfOrderFormArry")
	@ResponseBody
	public Object AddGoodsToSelfOrderFormArry(HttpServletRequest request,HttpServletResponse response,Long WeAddressId,String goodCartIds){
		log.info("添加订单beginning...........................");
		//校验结果集合
		Map<Object, Object> tempMP = null;
		Result rs = null;
		ezs_user user = RedisUserSession.getLoginUserInfo(request);
		if (user == null) {
			rs = Result.failure();
			rs.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			rs.setMsg("用户未登录");
			return rs;
		}else{ 
			Long auditingusertype_id = user.getEzs_store().getAuditingusertype_id();
			ezs_dict dictCode = dictService.getDictByThisId(auditingusertype_id);
			if(dictCode.getSequence()<=3){
			if(user.getEzs_store().getStatus()!=2){
				rs = Result.failure();
				rs.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				rs.setMsg("您还未完成实名认证，请去个人中心完成实名认证！");
				return rs;
			}
			}
		}
		if(goodCartIds==null){
			rs = Result.failure();
			rs.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			rs.setMsg("请输入购物车ID");
			return rs;
		}
		try {
			// 获取选中的购物车ID数组
			String[] goodCartIdTemps = goodCartIds.split(",");
			// 进行下单前预提交库存校验，tempMP含有校验返回信息
			tempMP = checkOrderForm(user, "GOODS", goodCartIdTemps);
			boolean orderFormFlag = (boolean) tempMP.get("SuccessFlag");
			// 校验全部通过标志，全部通过，通过后进行下单处理
			if (orderFormFlag == true) {
				// 循环下单方法
				for (int i = 0; i < goodCartIdTemps.length; i++) {
					ezs_orderform tOrderForm = new ezs_orderform();
					Map<String, Object> tmp = null;
					// tOrderForm.setWeAddress_id(WeAddressId);
					tOrderForm.setAddress_id(WeAddressId);
					ezs_goods buyGoods = null;
					// 订单号
					try {
						ezs_goodscart buyGoodsCar = this.ezs_goodscartMapper
								.selectByPrimaryKey(Long.valueOf(goodCartIdTemps[i]));
						buyGoods = this.ezs_goodsMapper.selectByPrimaryKey(buyGoodsCar.getGoods_id());
						tOrderForm.setOrder_no(createOrderNo(buyGoods));
					} catch (Exception e) {
						e.printStackTrace();
						log.info("订单号生成失败。。。。。。。。。。。。。。。。。。。。。。。");
					}
					//判断订单类型：判断是否为子公司订单
					if(this.childCompanyGoodsService.isChildCompanyGood(buyGoods)){
						tmp = this.childCompanyGoodsService.addOrderFormFunc(tOrderForm, user, "GOODS",
								Long.valueOf(goodCartIdTemps[i]),buyGoods);
					}else{
						// 进行下单处理
						tmp = this.goodsService.addOrderFormFunc(tOrderForm, user, "GOODS",
								Long.valueOf(goodCartIdTemps[i]),buyGoods);
					}
					
					Integer ErrorCode = (Integer) tmp.get("ErrorCode");
					if (ErrorCode != null && ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)) {
						// rs.setMsg(tmp.get("Msg").toString());
						// 下单成功,在此不足任何提示
					} else {
						// 下单失败，获取失败原因
						log.info("添加订单校验通过，参数有误，添加失败XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
					}
				}
				rs = Result.success();
				// 检验通过无需返回前台信息
				// tempMP.remove("SuccessFlag");
				rs.setObj(new ArrayList<>());
				rs.setMsg("下单成功");
			} else {
				// 校验未通过（未全部通过）
				rs = Result.failure();
				tempMP.remove("SuccessFlag");
				// 由此查询返回查询购物车相关信息
				Map<String, Object> mMp = this.goodsService.getGoodInfoFromGoodCart(tempMP);
				if (mMp != null) {
					rs.setObj(mMp.get("Obj"));
				}
				rs.setMsg("有未通过预提交测试订单");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs = Result.failure();
			rs.setObj(new ArrayList<>());
			rs.setMsg("数据传递有误");
			log.error(e.toString());
		}
		return rs;
	}
	
	/**
	 * 获取购物车数据
	 * @author zhaibin 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getGoodCar")
	@ResponseBody
	public Object getGoodCar(@RequestParam(defaultValue="1",name="pageNow")int pageNow,
			HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> mmp = null;
		Result rs = null;
		ezs_user user = RedisUserSession.getLoginUserInfo(request);
		if (user == null) {
			rs = Result.failure();
			rs.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			rs.setMsg("用户未登录");
			return rs;
		}
		try {
			List<ezs_goodscart> goodCarList=new ArrayList<>();
			mmp = this.goodsService.getGoodCarFunc(user,pageNow);
			Integer ErrorCode = (Integer) mmp.get("ErrorCode");
			if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
				goodCarList = (List<ezs_goodscart>) mmp.get("Obj");
				rs = Result.success();
				rs.setObj(goodCarList);
				rs.setMsg(mmp.get("Msg").toString());
			}else{
				rs = Result.failure();
				rs.setObj(goodCarList);
				rs.setMsg(mmp.get("Msg").toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			rs = Result.failure();
			rs.setMsg(mmp.get("Msg").toString());
		}
		return rs;
	}
	/**
	 * 校验下单状态
	 * @author zhaibin
	 * @return
	 */
	private synchronized Map<Object, Object> checkOrderForm(ezs_user user,String orderType,String[] goodsCartIds){
		Map<Object, Object> mMp = new HashMap<>();
		boolean SuccessFlag = true;
		for(int i=0;i<goodsCartIds.length;i++){
			Map<String, Object> mmp = this.goodsService.preOrderFormFunc(user, orderType, Long.valueOf(goodsCartIds[i]));
			Integer ErrorCode = (Integer)mmp.get("ErrorCode");
			if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
				//执行成功
				boolean checkFlag = (boolean) mmp.get("GoodCartIDFlag");
				if(checkFlag==true){
					mMp.put(mmp.get("GoodCartID"), true);
				}else{
					mMp.put(mmp.get("GoodCartID"), false);
					SuccessFlag = false;
				}
			}else{
				SuccessFlag = false;
			}
		}
		//mMp.put("399",true);
		//mMp.put("404",false);
		mMp.put("SuccessFlag",SuccessFlag);
		return mMp;
	}
	
	/**
	 * 商品id得到质检报告
	 * @param goodsId
	 * @return
	 */
	@RequestMapping("/getGoodsPdf")
	@ResponseBody
	public Object getGoodsPdf(@RequestParam("goodsId")long goodsId){
		Result result=Result.failure();
		result=goodsService.getGoodsPdf(goodsId);
		return result;
	}
	
	/**
	 * 订单确认初始化
	 * @param goodsId
	 * @return
	 */
	@RequestMapping("/orderConfirminit")
	@ResponseBody
	public Object orderConfirminit(HttpServletRequest  request){
		Result result=Result.failure();
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if (upi == null) {
			result = Result.failure();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}else{
			Long auditingusertype_id = upi.getEzs_store().getAuditingusertype_id();
			ezs_dict dictCode = dictService.getDictByThisId(auditingusertype_id);
			if(dictCode.getSequence()<=3){
			if(upi.getEzs_store().getStatus()!=2){
				result = Result.failure();
				result.setErrorcode(DictionaryCode.ERROR_WEB_NOAUTH_ERROR);
				result.setMsg("您还未完成实名认证，请去个人中心完成实名认证！");
				return result;
			}
			}
		}
		//收货地址
		Page page = new Page();
		page.setPageNow(1);
		List<ezs_address> addressList = addressService.findAddressByUserId(upi.getId(),page);
		Map<String, Object> map=new HashMap<>();
		if(null!=addressList&&addressList.size()>0){
			//area地址
			addressList=SetAddressInfo(addressList);
		    map.put("readdress", addressList.get(0));
		    map.put("hasd", true);
		}else{
			map.put("readdress", null);
			map.put("hasd", false);
		}
		
		if(null==upi.getEzs_bill()){
			map.put("hasb", false);
		}else{
			map.put("hasb", true);
		}
		map.put("bill", upi.getEzs_bill());
		
		result.setObj(map);
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setSuccess(true);
		
		return result;
	}
	private List<ezs_address> SetAddressInfo(List<ezs_address> addressList){
		for (ezs_address ezs_address : addressList) {
			ezs_address.setArea(getaddressinfo(ezs_address.getArea_id()));
		}
		return addressList;
	}
	
	/**
	 * 地址
	 * @param areaid
	 * @return
	 */
	private String getaddressinfo(long areaid) {
		StringBuilder sb = new StringBuilder();
		String threeinfo = "";
		String twoinfo = "";
		String oneinfo = "";
		ezs_area ezs_threeinfo = ezs_areaMapper.selectByPrimaryKey(areaid);
		if (ezs_threeinfo != null) {
			threeinfo = ezs_threeinfo.getAreaName();
			ezs_area ezs_twoinfo = ezs_areaMapper.selectByPrimaryKey(ezs_threeinfo.getParent_id());
			if (ezs_twoinfo != null) {
				twoinfo =  ezs_twoinfo.getAreaName();
				ezs_area ezs_oneinfo = ezs_areaMapper.selectByPrimaryKey(ezs_twoinfo.getParent_id());
				if (ezs_oneinfo != null) {
					oneinfo =  ezs_oneinfo.getAreaName();
				}
			}
		}
		
		if(!Tools.isEmpty(threeinfo)){
			sb = new StringBuilder().append(threeinfo);	
		}
		if(!Tools.isEmpty(twoinfo)){
			sb = new StringBuilder().append(twoinfo).append("-").append(threeinfo);
		}
		if(!Tools.isEmpty(oneinfo)){
			sb = new StringBuilder().append(oneinfo).append("-").append(twoinfo).append("-").append(threeinfo);
		}
		
		return sb.toString();
	}
	
	public static synchronized String getOrderNO() {
		SimpleDateFormat sf = new SimpleDateFormat("MMddHHmmss");
		String str = sf.format(System.currentTimeMillis());
		String result = "EM" + str + num;
		num++;
		if (num == 1000) {
			num = 100;
		}
		return result;
	}
	public String createOrderNo(ezs_goods goods) {
		try {
			log.info("FunctionName:" + "createOrderNo " + ",context:" + "创建订单号。。。。。。。");
			int folwnum = this.ezs_orderformMapper.selectOrderNumByDate();
			Long rootGoodsClass = getRootOfTheGoodClass(goods.getGoodClass_id());
			// 移动端EMM PC端EMP
			StringBuffer orderNo = new StringBuffer("EMM");
			orderNo.append(CommUtil.getNumber(rootGoodsClass, "00"));
			orderNo.append(CommUtil.dateToString(new Date(), "YYMMdd"));
			orderNo.append(CommUtil.getNumber(folwnum + 1, "00000"));
			log.info("FunctionName:" + "createOrderNo " + ",context:" + "创建订单号成功");
			return orderNo.toString();
		} catch (Exception e) {
			log.error("生成订单号失败：" + e.toString());
			throw e;
		}
	}
	/**
	 * 循环获取最高级商品种类ID
	 * 
	 * @author
	 * @param goodClassId
	 * @return
	 */
	private Long getRootOfTheGoodClass(Long goodClassId) {
		ezs_goods_class goodsClass = null;
		goodsClass = this.goodsClassMapper.selectByPrimaryKey(goodClassId);
		if (goodsClass != null) {
			while (!goodsClass.getLevel().equals("1")) {
				goodsClass = this.goodsClassMapper.selectByPrimaryKey(goodsClass.getParent_id());
			}
			return goodsClass.getId();
		}
		return 0L;
	}
}
