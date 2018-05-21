package com.sanbang.seller.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goods_audit_process;
import com.sanbang.bean.ezs_goods_log;
import com.sanbang.bean.ezs_purchase_orderform;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_accessoryMapper;
import com.sanbang.dao.ezs_goodsMapper;
import com.sanbang.dao.ezs_goods_audit_processMapper;
import com.sanbang.dao.ezs_goods_logMapper;
import com.sanbang.seller.service.SellerGoodsService;
import com.sanbang.utils.Page;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;
@Service
public class SellerGoodsServiceImpl implements SellerGoodsService {
	
	private Logger log=Logger.getLogger(SellerGoodsServiceImpl.class);
	
	@Autowired
	ezs_goodsMapper goodsMapper;
	
	@Autowired
	ezs_accessoryMapper accessoryMapper;
	
	@Autowired
	ezs_goods_logMapper goodsLogMapper; 
	
	@Autowired
	ezs_goods_audit_processMapper goodsAuditProcessMapper;
	
	@Override
	public Map<String, Object> queryGoodsListBySellerId(Long sellerId, int status, String currentPage) {
		Map<String, Object> mmp = new HashMap<>();
		//获取总页数
		int totalCount = goodsMapper.selectCount(sellerId);
		Page page = new Page(totalCount, Integer.valueOf(currentPage));
		int startPos = 0;
		page.setStartPos(startPos);
		page.setPageSize(10);
		if(Integer.valueOf(currentPage)>=1||Integer.valueOf(currentPage)<=page.getTotalPageCount()){
			List<ezs_goods> list = goodsMapper.selectGoodsListBySellerId(sellerId, status, currentPage);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Page", page);
			mmp.put("Obj", list);
		}else{
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Page", page);
		}
		return mmp;
	}

	@Override
	public ezs_goods queryGoodsInfoById(long id) {
		
		return goodsMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ezs_accessory> queryPhotoById(Long goodsId) {
		
		return accessoryMapper.selectPhotoById(goodsId);
	}

	@Override
	public Result addGoodsInfo(Result result, ezs_user upi, HttpServletRequest request, HttpServletResponse response) {
		result = checkParam(request);
		if(result.getSuccess()){
			String goodClass_id = request.getParameter("goodClass_id");//分类
			String name = request.getParameter("name");//货品名称
			String price = request.getParameter("price");//价格
			String validity = request.getParameter("validity");//有效期
			String inventory = request.getParameter("inventory");//库存
			String area_id = request.getParameter("area_id");//库存地区（县市）
			String addess = request.getParameter("addess");//库存地区详细地址
			String supply_id = request.getParameter("supply_id");//供应情况（是否稳定供货）
			String color_id = request.getParameter("color_id");//颜色
			String form_id = request.getParameter("form_id");//形态
			String source = request.getParameter("source");//来源
			String purpose = request.getParameter("purpose");//用途
			String protection = request.getParameter("protection");//是否环保
			String density = request.getParameter("density");//密度
			String cantilever = request.getParameter("cantilever");//悬臂梁缺口冲击
			String freely = request.getParameter("freely");//简支梁缺口冲击
			String lipolysis = request.getParameter("lipolysis");//溶指
			String ash = request.getParameter("ash");//灰份
			String water = request.getParameter("water");//水分
			String tensile = request.getParameter("tensile");//拉伸强度
			String crack = request.getParameter("crack");//断裂伸长率
			String bending = request.getParameter("bending");//弯曲强度
			String flexural = request.getParameter("flexural");//弯曲模量
			String burning = request.getParameter("burning");//燃烧等级
			String seo_description = request.getParameter("seo_description");//货品详细描述
			
			ezs_goods goods = new ezs_goods();
			goods.setGoodClass_id(Long.parseLong(goodClass_id));
			goods.setName(name);
			goods.setPrice(new BigDecimal(price));
			goods.setValidity(Integer.valueOf(validity));
			goods.setInventory(Double.parseDouble(inventory));
			goods.setArea_id(Long.parseLong(area_id));
			goods.setAddess(addess);
			goods.setSupply_id(Long.parseLong(supply_id));
			goods.setSupply_id(Long.parseLong(color_id));
			goods.setSupply_id(Long.parseLong(form_id));
			goods.setSource(source);
			if (protection == "0") {
				goods.setProtection(true);
			}else if(protection == "1"){
				goods.setProtection(false);
			}else{
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("参数错误");
			}
			goods.setDensity(density);
			goods.setCantilever(cantilever);
			goods.setFreely(freely);
			goods.setLipolysis(lipolysis);
			goods.setAsh(ash);
			goods.setWater(water);
			goods.setTensile(tensile);
			goods.setCrack(crack);
			goods.setBending(bending);
			goods.setFlexural(flexural);
			goods.setBurning(burning);
			goods.setSeo_description(seo_description);
			int aa = goodsMapper.insert(goods);
			if (aa > 0) {
				result.setSuccess(true);
				result.setMsg("添加货品成功");
			} else{
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("参数错误");
			}
		}
		result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
		result.setSuccess(false);
		result.setMsg("参数错误");
		
		return result;
	}
	
	Result checkParam(HttpServletRequest request){
		Result result = Result.success();
		String goodClass_id = request.getParameter("goodClass_id");//分类
		String name = request.getParameter("name");//货品名称
		String price = request.getParameter("price");//价格
		String validity = request.getParameter("validity");//有效期
		String inventory = request.getParameter("inventory");//库存
		String area_id = request.getParameter("area_id");//库存地区（县市）
		String addess = request.getParameter("addess");//库存地区详细地址
		String supply_id = request.getParameter("supply_id");//供应情况（是否稳定供货）
		String color_id = request.getParameter("color_id");//颜色
		String form_id = request.getParameter("form_id");//形态
		String source = request.getParameter("source");//来源
		String purpose = request.getParameter("purpose");//用途
		String protection = request.getParameter("protection");//是否环保
		//非必填
		String density = request.getParameter("density");//密度
		String cantilever = request.getParameter("cantilever");//悬臂梁缺口冲击
		String freely = request.getParameter("freely");//简支梁缺口冲击
		String lipolysis = request.getParameter("lipolysis");//溶指
		String ash = request.getParameter("ash");//灰份
		String water = request.getParameter("water");//水分
		String tensile = request.getParameter("tensile");//拉伸强度
		String crack = request.getParameter("crack");//断裂伸长率
		String bending = request.getParameter("bending");//弯曲强度
		String flexural = request.getParameter("flexural");//弯曲模量
		String burning = request.getParameter("burning");//燃烧等级
		
		if (Tools.isEmpty(goodClass_id)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请选择货品类别");
		}
		if (Tools.isEmpty(name)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入货品名称");
		}
		if (Tools.isEmpty(price)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入货品价格");
			if (!Tools.isNum(price)) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("请输入有效货品价格");
			}
		}
		if (Tools.isEmpty(validity)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入货品有效期");
		}
		if (Tools.isEmpty(inventory)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入货品库存");
		}
		if (Tools.isEmpty(area_id)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入货品库存地区");
		}
		if (Tools.isEmpty(addess)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入货品库存详细地址");
		}
		if (Tools.isEmpty(supply_id)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请选择货品供应情况");
		}
		if (Tools.isEmpty(color_id)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请选择货品颜色");
		}
		if (Tools.isEmpty(form_id)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请选择货品形态");
		}
		if (Tools.isEmpty(source)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入货品来源");
		}
		if (Tools.isEmpty(purpose)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请选择货品用途");
		}
		if (Tools.isEmpty(protection)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请选择货品是否环保");
		}
		return result;
	}

	@Override
	public Result pullOffShelvesById(Result result, long goodsId) {
		
		int aa = 0;
		
		try {
			aa = goodsMapper.pullOffShelves(goodsId);
		} catch (Exception e) {
			log.info("商品下架操作出错"+e.toString());
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误！");
		}
		
		if (aa <= 0 ) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("下架操作异常");
		}else{
			result.setSuccess(true);
			result.setMsg("操作成功");
		}
		return result;	
	}

	@Override
	public Result updateGoodsInfoById(Result result, long goodsId, HttpServletRequest request,
			HttpServletResponse response) {
		
		result = checkParam(request);
		if(result.getSuccess()){
			ezs_goods goods = goodsMapper.selectByPrimaryKey(goodsId);
			ezs_goods_log goodsLog = new ezs_goods_log();
			String goodClass_id = request.getParameter("goodClass_id");//分类
			String name = request.getParameter("name");//货品名称
			String price = request.getParameter("price");//价格
			String validity = request.getParameter("validity");//有效期
			String inventory = request.getParameter("inventory");//库存
			String area_id = request.getParameter("area_id");//库存地区（县市）
			String addess = request.getParameter("addess");//库存地区详细地址
			String supply_id = request.getParameter("supply_id");//供应情况（是否稳定供货）
			String color_id = request.getParameter("color_id");//颜色
			String form_id = request.getParameter("form_id");//形态
			String source = request.getParameter("source");//来源
			String purpose = request.getParameter("purpose");//用途
			String protection = request.getParameter("protection");//是否环保
			String density = request.getParameter("density");//密度
			String cantilever = request.getParameter("cantilever");//悬臂梁缺口冲击
			String freely = request.getParameter("freely");//简支梁缺口冲击
			String lipolysis = request.getParameter("lipolysis");//溶指
			String ash = request.getParameter("ash");//灰份
			String water = request.getParameter("water");//水分
			String tensile = request.getParameter("tensile");//拉伸强度
			String crack = request.getParameter("crack");//断裂伸长率
			String bending = request.getParameter("bending");//弯曲强度
			String flexural = request.getParameter("flexural");//弯曲模量
			String burning = request.getParameter("burning");//燃烧等级
			String seo_description = request.getParameter("seo_description");//货品详细描述
			
			goods.setGoodClass_id(Long.parseLong(goodClass_id));
			goods.setName(name);
			goods.setPrice(new BigDecimal(price));
			goods.setValidity(Integer.valueOf(validity));
			goods.setInventory(Double.parseDouble(inventory));
			goods.setArea_id(Long.parseLong(area_id));
			goods.setAddess(addess);
			goods.setSupply_id(Long.parseLong(supply_id));
			goods.setSupply_id(Long.parseLong(color_id));
			goods.setSupply_id(Long.parseLong(form_id));
			goods.setSource(source);
			if (protection == "0") {
				goods.setProtection(true);
			}else if(protection == "1"){
				goods.setProtection(false);
			}else{
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("参数错误");
			}
			goods.setDensity(density);
			goods.setCantilever(cantilever);
			goods.setFreely(freely);
			goods.setLipolysis(lipolysis);
			goods.setAsh(ash);
			goods.setWater(water);
			goods.setTensile(tensile);
			goods.setCrack(crack);
			goods.setBending(bending);
			goods.setFlexural(flexural);
			goods.setBurning(burning);
			goods.setSeo_description(seo_description);
			
			
			int aa = goodsMapper.updateByPrimaryKey(goods);
			if (aa > 0) {
				result.setSuccess(true);
				result.setMsg("修改货品成功");
				goodsLog.setGoodsId(goodsId);
				goodsLogMapper.insert(goodsLog);
			} else{
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("参数错误");
			}
		}
		result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
		result.setSuccess(false);
		result.setMsg("参数错误");
		return result;
	}

	@Override
	public Result submitGoodsForAudit(Result result, long goodsId, HttpServletRequest request,
			HttpServletResponse response) {
		
		ezs_goods_audit_process goodsAudit = new ezs_goods_audit_process();
		
		goodsAudit.setGoods_id(goodsId);
		
		goodsAudit.setStatus(540);
		
		int aa = goodsAuditProcessMapper.insertSelective(goodsAudit);
		
		if (aa <= 0) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		
		result.setSuccess(true);
		result.setMsg("提交审核成功，请静待结果");
		
		return result;
	}
}	
