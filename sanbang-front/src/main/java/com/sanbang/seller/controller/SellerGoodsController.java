package com.sanbang.seller.controller;

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

import com.sanbang.area.service.AreaService;
import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_dict;
import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goods_class;
import com.sanbang.bean.ezs_store;
import com.sanbang.bean.ezs_user;
import com.sanbang.cata.service.CataService;
import com.sanbang.dict.service.DictService;
import com.sanbang.seller.service.SellerGoodsService;
import com.sanbang.upload.sevice.FileUploadService;
import com.sanbang.utils.Page;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCate;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.GoodsClass;

@Controller
@RequestMapping("/seller")
public class SellerGoodsController {
	//日志
	private Logger log=Logger.getLogger(SellerGoodsController.class);
	
	@Autowired
	SellerGoodsService sellerGoodsService;
	
	@Autowired
	FileUploadService fileUploadService;
	
	@Autowired
	DictService dictService;
	
	@Autowired
	AreaService areaService;
	
	@Autowired
	CataService cataService; 
	
	
	
	/**
	 * 查询货品列表   status:1 正常上架    status:0（默认值） 待审核     status:2 下架
	 * @param status
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryGoodsList")
	@ResponseBody
	public Object queryGoodsList(int status, HttpServletRequest request, HttpServletResponse response,String currentPage){
		Result result = Result.failure();
		List<ezs_goods> list = new ArrayList<>();
		Map<String, Object> map = null;

		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		Long useId = upi.getId();
		
		//验证用户是否激活，拥有卖家权限
//		ezs_store store = upi.getEzs_store();
//		Integer storeStatus = store.getStatus();
//		Long auditingusertype_id = store.getAuditingusertype_id();
//		String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
//		if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
//			result.setSuccess(false);
//			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
//			result.setMsg("用户未激活，没有卖家权限。");
//			return result;
//		}
		
		Page page = null;
		if(currentPage==null){
			currentPage = "1";
		}
		map = sellerGoodsService.queryGoodsListBySellerId(useId, status, currentPage);
		
		list = (List<ezs_goods>)map.get("Obj");
		
		int errorCode = (int) map.get("ErrorCode");
		
		page = (Page) map.get("page");
		result.setObj(list);
		result.setMeta(page);
		result.setErrorcode(errorCode);
		
		return result;
	}
	/**
	 * 查看货品属性
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryGoodsInfoById")
	@ResponseBody
	public Object queryGoodsInfoById(long id, HttpServletRequest request, HttpServletResponse response){
		Result result = Result.failure();
		Map<String,Object> map = new HashMap<>();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		
		//验证用户是否激活，拥有卖家权限
//		ezs_store store = upi.getEzs_store();
//		Integer storeStatus = store.getStatus();
//		Long auditingusertype_id = store.getAuditingusertype_id();
//		String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
//		if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
//			result.setSuccess(false);
//			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
//			result.setMsg("用户未激活，没有卖家权限。");
//			return result;
//		}
		
		
		ezs_goods goods = sellerGoodsService.queryGoodsInfoById(id);
		
		Long goodsId = goods.getId();
		List<ezs_accessory> photoList = sellerGoodsService.queryPhotoById(goodsId);
		List<ezs_accessory> cartographyList = sellerGoodsService.queryCartographyById(goodsId);
		
		
		Long color_id = goods.getColor_id();
		String color = sellerGoodsService.getGoodsProperty(color_id);
		Long form_id = goods.getForm_id();
		String form = sellerGoodsService.getGoodsProperty(form_id);
		
		
		
		map.put("goods", goods);
		map.put("photoList", photoList);
		map.put("cartographyList", cartographyList);
		
		map.put("color", color);
		map.put("form",form);
		//		//颜色
//		map.put("EZS_COLOR", dictService.getDictByParentId(DictionaryCate.EZS_COLOR));
		
//		//形态
//		map.put("EZS_FORM", dictService.getDictByParentId(DictionaryCate.EZS_FORM));
		
//		map.put("EZS_FORM", dictService.getDictByParentId(DictionaryCate.EZS_FORM));
//		//地址
//		map.put("area", areaService.getAreaParentList());
//		//分类
//		map.put("cata",cataService.getOnelevelList());
		
		
		
		result.setObj(map);
		
		return result;
	}

	/**
	 * 商品添加初始化
	 * @param userName
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/goodsInit")
	@ResponseBody
	public Object goodsInit(HttpServletRequest request) throws Exception{
		Result result=Result.failure();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		
		//验证用户是否激活，拥有卖家权限
//		ezs_store store = upi.getEzs_store();
//		Integer storeStatus = store.getStatus();
//		Long auditingusertype_id = store.getAuditingusertype_id();
//		String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
//		if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
//			result.setSuccess(false);
//			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
//			result.setMsg("用户未激活，没有卖家权限。");
//			return result;
//		}
		
		
		if(null!=upi.getEzs_userinfo()){
			Map<String, Object> map=new HashMap<>();
			//颜色
			map.put("EZS_COLOR", dictService.getDictByParentId(DictionaryCate.EZS_COLOR));
			//形态
			map.put("EZS_FORM", dictService.getDictByParentId(DictionaryCate.EZS_FORM));
			//形态
			map.put("EZS_FORM", dictService.getDictByParentId(DictionaryCate.EZS_FORM));
			//地址
			map.put("area", areaService.getAreaParentList());
			//分类
			map.put("cata",cataService.getFirstList());
 			result.setObj(map);
		};
		
		return result;
	}
	
	//查询父级分类
	@RequestMapping("/getCataListByparid")
	@ResponseBody
	public Result getCataListByparid(HttpServletRequest request,long parentsId){
		Result result=Result.success();
		List<GoodsClass> list = cataService.getChildList();
		result.setMeta(new Page(1, 1, 1,1, 1, false, false, false, false));
		result.setObj(list);
		return result;
	}
	
	
	/**
	 * 添加货品
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/addGoodsInfo")
	@ResponseBody
	public Object addGoodsInfo(HttpServletRequest request, HttpServletResponse response){
		Result result=Result.failure();
		Map<String,Object> map = new HashMap<>();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
		
		//验证用户是否激活，拥有卖家权限
//		ezs_store store = upi.getEzs_store();
//		Integer storeStatus = store.getStatus();
//		Long auditingusertype_id = store.getAuditingusertype_id();
//		String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
//		if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
//			result.setSuccess(false);
//			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
//			result.setMsg("用户未激活，没有卖家权限。");
//			return result;
//		}
		
		try {
			result = sellerGoodsService.addGoodsInfo(result, upi, request,response);
		} catch (Exception e) {
			log.info("供应商"+upi.getName()+"添加货品出错"+e.toString());
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}
		
		return result;
	}
	/**
	 * 供应商货品下架
	 * @param goodsId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/pullOffShelves")
	@ResponseBody
	public Object pullOffShelves(long goodsId, HttpServletRequest request, HttpServletResponse response){
		Result result=Result.failure();
		Map<String,Object> map = new HashMap<>();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
		
		//验证用户是否激活，拥有卖家权限
//		ezs_store store = upi.getEzs_store();
//		Integer storeStatus = store.getStatus();
//		Long auditingusertype_id = store.getAuditingusertype_id();
//		String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
//		if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
//			result.setSuccess(false);
//			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
//			result.setMsg("用户未激活，没有卖家权限。");
//			return result;
//		}
		
		result = sellerGoodsService.pullOffShelvesById(result, goodsId);
		return result;
	}
	

	/**
	 * 供应商货品属性修改， 图片修改未完成
	 * @param goodsId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/updateGoodsInfoById")
	@ResponseBody
	public Object updateGoodsInfoById(long goodsId, HttpServletRequest request, HttpServletResponse response){
		Result result=Result.failure();
		Map<String,Object> map = new HashMap<>();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
		
		//验证用户是否激活，拥有卖家权限
//		ezs_store store = upi.getEzs_store();
//		Integer storeStatus = store.getStatus();
//		Long auditingusertype_id = store.getAuditingusertype_id();
//		String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
//		if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
//			result.setSuccess(false);
//			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
//			result.setMsg("用户未激活，没有卖家权限。");
//			return result;
//		}
		
		result = sellerGoodsService.updateGoodsInfoById(result, goodsId,upi, request,response);
		
		return result;
	}
	/**
	 * 提交审核
	 * @param goodsId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/submitGoodsForAudit")
	@ResponseBody
	public Object submitGoodsForAudit(long goodsId, HttpServletRequest request, HttpServletResponse response){
		Result result=Result.failure();
		Map<String,Object> map = new HashMap<>();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
		
		//验证用户是否激活，拥有卖家权限
//		ezs_store store = upi.getEzs_store();
//		Integer storeStatus = store.getStatus();
//		Long auditingusertype_id = store.getAuditingusertype_id();
//		String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
//		if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
//			result.setSuccess(false);
//			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
//			result.setMsg("用户未激活，没有卖家权限。");
//			return result;
//		}
		
		
		result = sellerGoodsService.submitGoodsForAudit(result, goodsId, request,response);	
		return result;
	}
}