package com.sanbang.buyer.controller;

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
import com.sanbang.bean.ezs_dvaluate;
import com.sanbang.bean.ezs_user;
import com.sanbang.buyer.service.GoodsCollectionService;
import com.sanbang.buyer.service.GoodsInvoiceService;
import com.sanbang.buyer.service.OrderEvaluateService;
import com.sanbang.upload.sevice.FileUploadService;
import com.sanbang.upload.sevice.impl.FileUploadServiceImpl;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.InvoiceInfo;
import com.sanbang.vo.PriceTrendIfo;
/**
 * 
 * @author LENOVO
 *
 */
@Controller
@RequestMapping("/buy")
public class BuyCenterController {
	// 日志
	private static Logger log = Logger.getLogger(BuyCenterController.class);
	
	@Autowired
	private GoodsCollectionService goodsCollectionService;
	@Autowired
	private GoodsInvoiceService goodsInvoiceService;
	@Autowired
	private OrderEvaluateService orderEvaluateService;
	@Autowired
	private FileUploadService fileUploadService; 
	/**
	 * 添加商品到收藏夹（不启用）
	 * @param request
	 * @param response
	 * @param gId
	 * @return
	 */
	@RequestMapping("/addGoodToCollection")
	@ResponseBody
	public Object addGoodToCollection(HttpServletRequest request,HttpServletResponse response,Long gId,Long userId){
		Map<String, Object> mmp = null;
		Result rs = null;
		//首先判断该商品是否已在保存记录中，
		mmp = this.goodsCollectionService.addGoodToCollection(gId,userId);
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			rs = Result.success();
			rs.setMsg("");
		}else{
			rs = Result.failure();
			rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
			rs.setMsg("参数传递有误");
		}
		return rs;
	}
	/**
	 * 移除收藏夹商品
	 * @param request
	 * @param response
	 * @param gId
	 * @return
	 */
	@RequestMapping("/removeFromCollection")
	@ResponseBody
	public Object removeFromCollection(HttpServletRequest request,HttpServletResponse response,Long gId){
		Map<String, Object> mmp = null;
		Result rs = null;
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if (upi == null) {
			rs = Result.failure();
			rs.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			rs.setMsg("用户未登录");
			return rs;
		}
		mmp = this.goodsCollectionService.removeGoodFromCollect(gId,upi.getId());
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
	 * 获取收藏夹中该用户下的所有商品
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getGoodsFromCollection")
	@ResponseBody
	public Object getGoodsFromCollection(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> mmp = null;
		Result rs = null;
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if (upi == null) {
			rs = Result.failure();
			rs.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			rs.setMsg("用户未登录");
			return rs;
		}
		List<Object> glist = null;
		mmp = this.goodsCollectionService.getCollectGoodsByUser(upi.getId());
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			glist = (List<Object>)mmp.get("Obj");
			rs = Result.success();
			rs.setObj(glist);
			rs.setMsg("");
		}else{
			rs = Result.failure();
			rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
			rs.setMsg("参数传递有误");
		}
		return rs;
	}
	/**
	 * 添加商品到购物车（不启用）
	 * @param request
	 * @param response
	 * @param gId
	 * @return
	 */
	@RequestMapping("/addToGoodCart")
	@ResponseBody
	public Object addToGoodCart(HttpServletRequest request,HttpServletResponse response,Long gId){
		Map<String, Object> mmp = null;
		Result rs = null;
		List<Object> glist = null;
		mmp = this.goodsCollectionService.addGoodCart(gId);
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			rs = Result.success();
			rs.setObj(glist);
			rs.setMsg("");
		}else{
			rs = Result.failure();
			rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
			rs.setMsg("参数传递有误");
		}
		return rs;
	}
	/**
	 * 商品最近价格变化趋势
	 * @param request
	 * @param response
	 * @param gId 商品ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/goodPriceChanges")
	@ResponseBody
	public Object goodPriceChanges(HttpServletRequest request,HttpServletResponse response,Long gId){
		Map<String, Object> mmp = null;
		List<PriceTrendIfo> plist = null; 
		Result rs = null;
		mmp = this.goodsCollectionService.selectPriceChanges(gId);
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			plist = (List<PriceTrendIfo>) mmp.get("Obj");
			rs = Result.success();
			rs.setObj(plist);
		}else{
			rs = Result.failure();
			rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
			rs.setMsg(mmp.get("Msg").toString());
		}
		return rs;
	}
	/**
	 * 获取用户票据信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getGoodsInvoiceByUser")
	@ResponseBody
	public Object getGoodsInvoiceByUser(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> mmp = null;
		Result rs = null;
		List<InvoiceInfo> iList = null;
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if (upi == null) {
			rs = Result.failure();
			rs.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			rs.setMsg("用户未登录");
			return rs;
		}
		mmp = this.goodsInvoiceService.getInvoiceByUser(upi.getId());
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			iList = (List<InvoiceInfo>) mmp.get("Obj");
			rs = Result.success();
			rs.setObj(iList);
		}else{
			rs = Result.failure();
			rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
			rs.setMsg(mmp.get("Msg").toString());
		}
		return rs;
	}
	/**
	 * 根据票据ID查询票据信息
	 * @param request
	 * @param response
	 * @param invoiceId
	 * @return
	 */
	@RequestMapping("/getGoodsInvoiceByKey")
	@ResponseBody
	public Object getGoodsInvoiceByKey(HttpServletRequest request,HttpServletResponse response,Long invoiceId){
		Map<String, Object> mmp = null;
		Result rs = null;
		InvoiceInfo invoiceInfo = null;
		mmp = this.goodsInvoiceService.getInvoiceByKey(invoiceId);
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			invoiceInfo = (InvoiceInfo) mmp.get("Obj");
			rs = Result.success();
			rs.setObj(invoiceInfo);
		}else{
			rs = Result.failure();
			rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
			rs.setMsg(mmp.get("Msg").toString());
		}
		return rs;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param invoiceId
	 * @return
	 */
	@RequestMapping("/changeInvoiceStateByKey")
	@ResponseBody
	public Object changeInvoiceStateByKey(HttpServletRequest request,HttpServletResponse response,Long invoiceId){
		Map<String, Object> mmp = null;
		Result rs = null;
		mmp = this.goodsInvoiceService.changeInvoiceStateById(invoiceId);
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			rs = Result.success();
			rs.setMsg("修改成功");
		}else{
			rs = Result.failure();
			rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
			rs.setMsg(mmp.get("Msg").toString());
		}
		return rs;
	}
	/**
	 * 订单评价
	 * @author zhaibin
	 * @param logistice 物流速度
	 * @param goodQuality 商品质量
	 * @param serviceQuality 服务态度
	 * @param orderNo 订单号
	 * @param goodId 商品Id
	 * @param path 图片路径
	 * @param imgName 图片名称(不支持多张图片)
	 * @return
	 */
	@RequestMapping("/evaluateAboutOrder")
	@ResponseBody
	public Object evaluateAboutOrder(HttpServletRequest request,HttpServletResponse response,
			Double logistice,Double goodQuality,Double serviceQuality,String orderNo,Long goodId,String content,
			String path,String imgName){
		
		Map<String, Object> mmp = null;
		ezs_dvaluate dvaluate = new ezs_dvaluate();
		ezs_accessory accessory = null;
		Result rs = null;
		ezs_user user = RedisUserSession.getLoginUserInfo(request);
		if (user == null) {
			rs = Result.failure();
			rs.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			rs.setMsg("用户未登录");
			return rs;
		}
		//参数设置
		if((orderNo!=null&&!orderNo.trim().equals(""))&&(goodId!=null)){
			dvaluate.setConttent(content);
			dvaluate.setLogistics(logistice);
			dvaluate.setGoodQuality(goodQuality);
			dvaluate.setServiceQuality(goodQuality);
			dvaluate.setOrder_no(orderNo);
			dvaluate.setGoods_id(goodId);
		}else{
			rs = Result.failure();
			rs.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			rs.setMsg("订单号不能为NULL");
			return rs;
		}
		if(imgName!=null){			
			accessory = new ezs_accessory();
			accessory.setName(imgName);
			accessory.setPath(path);
		}
		//数据入库
		mmp = this.orderEvaluateService.orderEvaluate(dvaluate,accessory,user);
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