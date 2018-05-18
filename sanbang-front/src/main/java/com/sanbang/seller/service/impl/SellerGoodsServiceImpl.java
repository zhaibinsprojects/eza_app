package com.sanbang.seller.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_accessoryMapper;
import com.sanbang.dao.ezs_goodsMapper;
import com.sanbang.seller.service.SellerGoodsService;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;
@Service
public class SellerGoodsServiceImpl implements SellerGoodsService {
	
	@Autowired
	ezs_goodsMapper goodsMapper;
	
	@Autowired
	ezs_accessoryMapper accessoryMapper;
	
	@Override
	public List<ezs_goods> queryGoodsListBySellerId(Long sellerId, int status) {
		
		return goodsMapper.selectGoodsListBySellerId(sellerId, status);
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
		}
			
		
		return null;
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
	
}
