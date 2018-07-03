package com.sanbang.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_goods_cartography;
import com.sanbang.bean.ezs_goods_photo;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_accessoryMapper;
import com.sanbang.dao.ezs_goods_cartographyMapper;
import com.sanbang.goods.service.GoodsService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.vo.goods.GoodsVo;

@Controller
@RequestMapping("/app/home")
public class AppH5HomeGoodsMessController {
	// 日志
	private static Logger log = Logger.getLogger(AppH5HomeGoodsMessController.class);
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private ezs_accessoryMapper accessoryMapper;
	
	private static final String view="/goods/";
	
	@RequestMapping("/toGoodsInfoShow")
	public String toGoodsShow(HttpServletRequest request,Long id,Model model){
		//用户校验begin
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		long userid=0;
		if(null==upi){
			upi=RedisUserSession.getUserInfoByKeyForApp(request);
		}
		userid=null==upi?0:upi.getId();
		model.addAttribute("userkey", null==upi?"":upi.getUserkey());
		//用户校验end
		GoodsVo  goodsvo = goodsService.getgoodsinfo(id,userid);
		//
		List<ezs_goods_cartography> cartographyList = goodsvo.getCartographys();
		List<ezs_goods_cartography> cartographyListTemp = new ArrayList<>();
		for (ezs_goods_cartography goodscartography : cartographyList) {
			ezs_accessory photo = this.accessoryMapper.selectByPrimaryKey(goodscartography.getCartography_id());
			if(photo!=null)
				goodscartography.setPhoto(photo);
			cartographyListTemp.add(goodscartography);
		}
		goodsvo.setCartographys(cartographyListTemp);
		model.addAttribute("good", goodsvo);
		return view+"goodsinfoshow";
	}
}
