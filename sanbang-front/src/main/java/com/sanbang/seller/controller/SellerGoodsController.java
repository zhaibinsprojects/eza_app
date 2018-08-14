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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.area.service.AreaService;
import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goods_log;
import com.sanbang.bean.ezs_store;
import com.sanbang.bean.ezs_user;
import com.sanbang.cata.service.CataService;
import com.sanbang.dict.service.DictService;
import com.sanbang.goods.service.GoodsService;
import com.sanbang.seller.service.SellerGoodsService;
import com.sanbang.upload.sevice.FileUploadService;
import com.sanbang.utils.GoodsLogUtil;
import com.sanbang.utils.Page;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCate;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.GoodsClass;
import com.sanbang.vo.goods.GoodsVo;

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
	
	@Autowired
	private GoodsService goodsService;
	
	
	
	/**
	 * 查询货品列表   status:1 正常上架    status:0（默认值） 待审核     status:2 下架
	 * @param status
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryGoodsList")
	@ResponseBody
	public Object queryGoodsList(@RequestParam(name = "status", defaultValue = "-1") int status, HttpServletRequest request, HttpServletResponse response,String currentPage){
		Result result = Result.failure();
		try {
			List<ezs_goods> list = new ArrayList<>();
			Page page = new Page(0, 1);
			Map<String, Object> map = new HashMap<>();

			ezs_user upi=RedisUserSession.getLoginUserInfo(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			Long useId = upi.getId();
			
			//验证用户是否激活，拥有卖家权限
			ezs_store store = upi.getEzs_store();
			Integer storeStatus = store.getStatus();
			Long auditingusertype_id = store.getAuditingusertype_id();
			String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
			if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
				result.setSuccess(false);
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("用户未激活，没有卖家权限。");
				return result;
			}
			try {
				map = sellerGoodsService.queryGoodsListBySellerId(useId, status, currentPage);
				if(null!=(List<ezs_goods>)map.get("Obj")){
					list=(List<ezs_goods>)map.get("Obj");
					page=(Page) map.get("page");
				}
			} catch (Exception e) {
				list=new ArrayList<>();
				e.printStackTrace();
				result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
				result.setSuccess(false);
				result.setObj(list);
				result.setMeta(page);
				result.setMsg("查询失败");
			}
			
			
			int errorCode = (int) map.get("ErrorCode");
			
			result.setObj(list);
			result.setMeta(page);
			result.setSuccess(true);
			result.setMsg("查询成功");
			result.setErrorcode(errorCode);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
			
		}
		
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
		try {
			Map<String,Object> map = new HashMap<>();
			ezs_user upi=RedisUserSession.getLoginUserInfo(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			
			//验证用户是否激活，拥有卖家权限
			ezs_store store = upi.getEzs_store();
			Integer storeStatus = store.getStatus();
			Long auditingusertype_id = store.getAuditingusertype_id();
			String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
			if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
				result.setSuccess(false);
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("用户未激活，没有卖家权限。");
				return result;
			}
			
			
			try {
				GoodsVo  goods=goodsService.getgoodsinfo(id,upi.getId());
				
				map.put("goods", goods);
				result.setObj(map);
				result.setSuccess(true);
				result.setMsg("查询成功");
			} catch (Exception e) {
				e.printStackTrace();
				result.setSuccess(false);
				result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
				result.setMsg("查询失败");
			}
			//		//颜色
		map.put("EZS_COLOR", dictService.getDictByParentId(DictionaryCate.EZS_COLOR));
			
//		//形态
		map.put("EZS_FORM", dictService.getDictByParentId(DictionaryCate.EZS_FORM));
			
		map.put("EZS_SUPPLY", dictService.getDictByParentId(DictionaryCate.EZS_SUPPLY));
//		//地址
		//map.put("area", areaService.getAreaParentList());
//		//分类
		map.put("cata",cataService.getFirstList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}
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
		ezs_store store = upi.getEzs_store();
		Integer storeStatus = store.getStatus();
		Long auditingusertype_id = store.getAuditingusertype_id();
		String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
		if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setMsg("用户未激活，没有卖家权限。");
			return result;
		}
		
		
		if(null!=upi.getEzs_userinfo()){
			Map<String, Object> map=new HashMap<>();
			//颜色
			map.put("EZS_COLOR", dictService.getDictByParentId(DictionaryCate.EZS_COLOR));
			//形态
			map.put("EZS_FORM", dictService.getDictByParentId(DictionaryCate.EZS_FORM));
			//形态
			map.put("EZS_SUPPLY", dictService.getDictByParentId(DictionaryCate.EZS_SUPPLY));
			
			//地址
			//map.put("area", areaService.getAreaParentList());
			//分类
			map.put("cata",cataService.getFirstList());
 			result.setObj(map);
 			result.setMsg("请求成功");
 			result.setSuccess(true);
 			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		};
		
		return result;
	}
	
	//查询父级分类
	@RequestMapping("/getCataListByparid")
	@ResponseBody
	public Result getCataListByparid(HttpServletRequest request,long parentsId){
		Result result=Result.success();
		List<GoodsClass> list = cataService.getSecondList(parentsId);
		result.setMeta(new Page(1, 1, 1,1, 1, false, false, false, false));
		result.setObj(list);
		result.setMsg("请求成功");
		result.setSuccess(true);
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
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
		ezs_store store = upi.getEzs_store();
		Integer storeStatus = store.getStatus();
		Long auditingusertype_id = store.getAuditingusertype_id();
		String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
		if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setMsg("用户未激活，没有卖家权限。");
			return result;
		}
		
		try {
			result = sellerGoodsService.addGoodsInfo(result, upi, request,response);
			
			if(!result.getSuccess()){
				return result;
			}
			//带审核
			ezs_goods goods = (ezs_goods) result.getObj();
			ezs_goods_log log=GoodsLogUtil.goodsLog(goods.getId(), upi.getId(), "用户操作:提交审核"+goods.getName()+"成功");
			result=sellerGoodsService.submitGoodsForAudit(result, goods.getId(), request, response,log,upi);
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
		try {
			Map<String,Object> map = new HashMap<>();
			ezs_user upi=RedisUserSession.getLoginUserInfo(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("请重新登陆！");
				return result;
			}
			
			//验证用户是否激活，拥有卖家权限
			ezs_store store = upi.getEzs_store();
			Integer storeStatus = store.getStatus();
			Long auditingusertype_id = store.getAuditingusertype_id();
			String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
			if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
				result.setSuccess(false);
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("用户未激活，没有卖家权限。");
				return result;
			}
			
			result = sellerGoodsService.pullOffShelvesById(result, goodsId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}
		return result;
	}
	

	/**
	 * 供应商货品属性修改
	 * @param goodsId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/updateGoodsInfoById")
	@ResponseBody
	public Object updateGoodsInfoById(long goodsId, HttpServletRequest request, HttpServletResponse response){
		Result result=Result.failure();
		try {
			Map<String,Object> map = new HashMap<>();
			ezs_user upi=RedisUserSession.getLoginUserInfo(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("请重新登陆！");
				return result;
			}
			
			//验证用户是否激活，拥有卖家权限
			ezs_store store = upi.getEzs_store();
			Integer storeStatus = store.getStatus();
			Long auditingusertype_id = store.getAuditingusertype_id();
			String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
			if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
				result.setSuccess(false);
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("用户未激活，没有卖家权限。");
				return result;
			}
			
			result = sellerGoodsService.updateGoodsInfoById(result, goodsId,upi, request,response);
			
			if(!result.getSuccess()){
				return result;
			}
			//带审核
			ezs_goods goods = sellerGoodsService.queryGoodsInfoById(goodsId);
			ezs_goods_log log=GoodsLogUtil.goodsLog(goods.getId(), upi.getId(), "用户操作:提交审核"+goods.getName()+"成功");
			result=sellerGoodsService.submitGoodsForAudit(result, goods.getId(), request, response,log,upi);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}
		
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
		ezs_store store = upi.getEzs_store();
		Integer storeStatus = store.getStatus();
		Long auditingusertype_id = store.getAuditingusertype_id();
		String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
		if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setMsg("用户未激活，没有卖家权限。");
			return result;
		}
		
		
		//带审核
		ezs_goods goods = sellerGoodsService.queryGoodsInfoById(goodsId);
		ezs_goods_log log=GoodsLogUtil.goodsLog(goodsId, upi.getId(), "用户操作:提交审核"+goods.getName()+"成功");
		result=sellerGoodsService.submitGoodsForAudit(result, goodsId, request, response,log,upi);
		return result;
	}
	
	
	/**
	 * 更改价格或库存数量  初始化
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/updatePriceAndCnclNumInit")
	@ResponseBody
	public Object updatePriceAndCnclNumInit(HttpServletRequest request, String currentPage){
		Result result=Result.failure();
		try {
			List<ezs_goods> list = new ArrayList<>();
			Page page = new Page(0, 1);
			Map<String, Object> map = null;
			int errorCode=DictionaryCode.ERROR_WEB_REQ_SUCCESS;
			
			ezs_user upi=RedisUserSession.getLoginUserInfo(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			
			//验证用户是否激活，拥有卖家权限
			ezs_store store = upi.getEzs_store();
			Integer storeStatus = store.getStatus();
			Long auditingusertype_id = store.getAuditingusertype_id();
			String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
			if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
				result.setSuccess(false);
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("用户未激活，没有卖家权限。");
				result.setObj(list);
				result.setMeta(page);
				return result;
			}
			Long userId = upi.getId();
			if(currentPage==null){
				currentPage = "1";
			}
			map = sellerGoodsService.queryGoodsListBySellerId(userId, 1, currentPage);
			
			list = (List<ezs_goods>)map.get("Obj");
			
			if (null==list||list.size()==0) {
				list=new ArrayList<>();
				result.setSuccess(true);
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setObj(list);
				result.setMsg("查询成功");
				result.setMeta(page);
				return result;
			}else{
			   errorCode = (int) map.get("ErrorCode");
				page = (Page) map.get("Page");
			}

			result.setObj(list);
			result.setMeta(page);
			result.setSuccess(true);
			result.setMsg("查询成功");
			result.setErrorcode(errorCode);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}
		
		return result;
	}
	
	/**
	 * 修改价格和数量
	 */
	@RequestMapping("/updatePriceAndCnclNum")
	@ResponseBody
	public Object updatePriceAndCnclNum(long goodsId, HttpServletRequest request,
			HttpServletResponse  response){
		Result result=Result.failure();
		try {
			ezs_user upi = RedisUserSession.getLoginUserInfo(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("请重新登陆！");
				return result;
			}
			Long userId = upi.getId();
			//验证用户是否激活，拥有卖家权限
			ezs_store store = upi.getEzs_store();
			Integer storeStatus = store.getStatus();
			Long auditingusertype_id = store.getAuditingusertype_id();
			String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
			if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
				result.setSuccess(false);
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("用户未激活，没有卖家权限。");
				return result;
			}
			result = sellerGoodsService.updateGoodsPriceAndNumById(result, goodsId,userId, request,response);
			
			if(!result.getSuccess()){
				return result;
			}
			//带审核
			ezs_goods goods = sellerGoodsService.queryGoodsInfoById(goodsId);
			ezs_goods_log log=GoodsLogUtil.goodsLog(goodsId, userId, "用户操作:修改库存"+goods.getName()+"成功");
			result=sellerGoodsService.submitGoodsForAudit(result, goodsId, request, response,log,upi);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}
		
		return result;
	}
	
	
	
	/**
	 * 供应商货品上架
	 * @param goodsId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/pullNoShelves")
	@ResponseBody
	public Object pullNoShelves(long goodsId, HttpServletRequest request, HttpServletResponse response){
		Result result=Result.failure();
		try {
			ezs_user upi=RedisUserSession.getLoginUserInfo(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("请重新登陆！");
				return result;
			}
			
			//验证用户是否激活，拥有卖家权限
			ezs_store store = upi.getEzs_store();
			Integer storeStatus = store.getStatus();
			Long auditingusertype_id = store.getAuditingusertype_id();
			String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
			if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
				result.setSuccess(false);
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("用户未激活，没有卖家权限。");
				return result;
			}
			
			result = sellerGoodsService.pullNoShelvesById(result, goodsId);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}
		return result;
	}
	
}