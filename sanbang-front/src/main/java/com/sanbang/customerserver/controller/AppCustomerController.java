package com.sanbang.customerserver.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanbang.bean.ezs_user;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.vo.goods.GoodsVo;


@Controller
@RequestMapping("/app/cus")
public class AppCustomerController {
	
	private static final String view="/customer/";
	/**
	 * @author langjf
	 * forapp 
	 * 查询货品详情
	 * @param request
	 * @param id 货品id
	 * @return
	 */
	@RequestMapping("/index")
	public String toGoodsShow(HttpServletRequest request,Long id,Model model){
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		long userid=0;
		if(null==upi){
			upi=RedisUserSession.getUserInfoByKeyForApp(request);
		}
		userid=null==upi?0:upi.getId();
		model.addAttribute("userkey", null==upi?"":upi.getUserkey());
		//用户校验end
		return view+"cusindex";
	}
}
