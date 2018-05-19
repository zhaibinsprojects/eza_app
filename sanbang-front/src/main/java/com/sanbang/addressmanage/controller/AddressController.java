package com.sanbang.addressmanage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.addressmanage.service.AddressService;
import com.sanbang.bean.ezs_address;
import com.sanbang.bean.ezs_user;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;

@Controller
@RequestMapping("/address")
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	@Autowired
	HttpServletRequest httpServletRequest;
	
	
	/**
	 * 添加新地址
	 * @param ezs_address
	 * @return
	 */
	@RequestMapping("/addNewAddress")
	@ResponseBody
	public Result saveNewAddress(ezs_address ezs_address){
		Result result = new Result().failure();
		ezs_user upi=RedisUserSession.getLoginUserInfo(httpServletRequest);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		
		return addressService.save(ezs_address,upi);
	}
	/**
	 * 根据用户ID查找所有收货地址
	 * @param userid
	 * @return
	 */
	@RequestMapping("/getAddressListByUserId")
	@ResponseBody
	public Result getAddressListByUserId(){
		Result result = new Result();
		ezs_user upi=RedisUserSession.getLoginUserInfo(httpServletRequest);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setSuccess(true);
		result.setMsg("请求成功");
		result.setObj(addressService.findAddressByUserId(upi.getId()));
	
		return result;
	}
	/**
	 * 根据地址Id查找对应收货地址
	 * @param id
	 * @return
	 */
	@RequestMapping("/getAddressById")
	@ResponseBody
	public Result getAddressById(@RequestParam("id")Long id){
		Result result = new Result();
		ezs_user upi=RedisUserSession.getLoginUserInfo(httpServletRequest);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setSuccess(true);
		result.setMsg("请求成功");
		result.setObj(addressService.findAddressById(id));
	
		return result;	
	}
	/**
	 * 修改收货地址
	 * @param ezs_address
	 * @return
	 */
	@RequestMapping("/updateAddressById")
	@ResponseBody
	public Result updateAddressById(ezs_address ezs_address){
		Result result = new Result();
		ezs_user upi=RedisUserSession.getLoginUserInfo(httpServletRequest);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
	
		return addressService.updateAddressById(ezs_address,upi);
	}
	/**
	 * 根据收货id删除收货地址(修改deleteStatus字段，0:启用；1:不启用)
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteAddressById")
	@ResponseBody
	public Result deleteAddressById(@RequestParam("id") Long id){
		Result result = new Result();
		ezs_user upi=RedisUserSession.getLoginUserInfo(httpServletRequest);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		return addressService.deleteAddressById(id);
		
	}
	

	
	
	
	
	
	

}