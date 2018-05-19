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

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goods_photo;
import com.sanbang.bean.ezs_user;
import com.sanbang.seller.service.SellerGoodsService;
import com.sanbang.upload.sevice.FileUploadService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;

@Controller
@RequestMapping("/seller")
public class SellerGoodsController {
	//日志
	private Logger log=Logger.getLogger(SellerGoodsController.class);
	
	@Autowired
	SellerGoodsService sellerGoodsService;
	
	@Autowired
	FileUploadService fileUploadService;
	/**
	 * 查询货品列表   status:1 正常上架    status:0（默认值） 待审核     status:2 下架
	 * @param status
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryGoodsList")
	public Object queryGoodsList(int status, HttpServletRequest request, HttpServletResponse response){
		Result result = Result.failure();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		
		Long sellerId = upi.getId();
		
		List<ezs_goods> list = new ArrayList<>();
		try {
			list = sellerGoodsService.queryGoodsListBySellerId(sellerId, status);
			if (list.size() > 0 ) {
				result.setSuccess(true);
				result.setMsg("查询成功");
			}
			if (list.size() == 0 ) {
				result.setSuccess(true);
				result.setMsg("暂无数据");
			}
			if (list.size() < 0 ) {
				result.setSuccess(false);
				result.setMsg("查询失败");
			}
			result.setObj(list);
		} catch (Exception e) {
			log.info("查询货品列表出错"+e.toString());
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
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
	public Object queryGoodsInfoById(long id, HttpServletRequest request, HttpServletResponse response){
		Result result = Result.failure();
		Map<String,Object> map = new HashMap<>();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		ezs_goods goods = new ezs_goods();
		
		goods = sellerGoodsService.queryGoodsInfoById(id);
		
		ezs_accessory accessory = new ezs_accessory();
		
		ezs_goods_photo goodsPhoto = new ezs_goods_photo();
		
		Long goodsId = goods.getId();
		List<ezs_accessory> photoList = sellerGoodsService.queryPhotoById(goodsId);
		
		map.put("goods", goods);
		map.put("photoList", photoList);
		
		result.setObj(map);
		
		return result;
	}
	
	/**
	 * 添加货品， 图片上传未完成
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/addGoodsInfo")
	public Object addGoodsInfo(HttpServletRequest request, HttpServletResponse response){
		Result result=Result.failure();
		Map<String,Object> map = new HashMap<>();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
		try {
			result = sellerGoodsService.addGoodsInfo(result, upi, request,response);
//			map = fileUploadService.uploadFile(request, 0, 0, 10*1024*1024l);
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
	public Object pullOffShelves(long goodsId, HttpServletRequest request, HttpServletResponse response){
		Result result=Result.failure();
		Map<String,Object> map = new HashMap<>();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
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
	public Object updateGoodsInfoById(long goodsId, HttpServletRequest request, HttpServletResponse response){
		Result result=Result.failure();
		Map<String,Object> map = new HashMap<>();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
		result = sellerGoodsService.updateGoodsInfoById(result, goodsId, request,response);
		
		return result;
	}
	
	@RequestMapping("/submitGoodsForAudit")
	public Object submitGoodsForAudit(long goodsId, HttpServletRequest request, HttpServletResponse response){
		Result result=Result.failure();
		Map<String,Object> map = new HashMap<>();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
		result = sellerGoodsService.submitGoodsForAudit(result, goodsId, request,response);	
		return fileUploadService;
	}
	
	
}



















