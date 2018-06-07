package com.sanbang.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.addressmanage.service.AddressService;
import com.sanbang.bean.ezs_address;
import com.sanbang.bean.ezs_area;
import com.sanbang.bean.ezs_user;
import com.sanbang.utils.Page;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;

@Controller
@RequestMapping("/app/address")
public class AppAddressController {
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	HttpServletRequest httpServletRequest;
	
	@Autowired
	private com.sanbang.dao.ezs_areaMapper ezs_areaMapper;
	/**
	 * 添加新地址
	 * @param ezs_address
	 * @return
	 */
	@RequestMapping("/addNewAddress")
	@ResponseBody
	public Result saveNewAddress(ezs_address ezs_address){
		Result result = new Result().failure();
		ezs_user upi=RedisUserSession.getUserInfoByKeyForApp(httpServletRequest);
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
	public Result getAddressListByUserId(@RequestParam(name = "pageNow", defaultValue = "1") int pageNow){
		Result result = new Result();
		ezs_user upi=RedisUserSession.getUserInfoByKeyForApp(httpServletRequest);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		Page page = new Page();
		page.setPageNow(pageNow);
		
		List<ezs_address> addressList = addressService.findAddressByUserId(upi.getId(),page);
		
		//area地址
		addressList=SetAddressInfo(addressList);
		
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setSuccess(true);
		result.setMsg("请求成功");
		result.setObj(addressList);
		result.setMeta(new Page(pageNow, page.getPageSize(), page.getTotalCount(), page.getTotalPageCount(), 0, true,
				true, true, true));
		if (addressList.size() == 0) {
			result.getMeta().setHasFirst(false);
		}
		if (pageNow == 1) {
			result.getMeta().setHasPre(false);
		}
		if(pageNow>=page.getTotalPageCount()){
			result.getMeta().setHasNext(false);
			result.getMeta().setHasLast(false);
		}
		
	
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
		ezs_user upi=RedisUserSession.getUserInfoByKeyForApp(httpServletRequest);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setSuccess(true);
		result.setMsg("请求成功");
		ezs_address  address=addressService.findAddressById(id);
		address.setArea(getaddressinfo(address.getArea_id()));
		result.setObj(address);
	
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
		ezs_user upi=RedisUserSession.getUserInfoByKeyForApp(httpServletRequest);
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
		ezs_user upi=RedisUserSession.getUserInfoByKeyForApp(httpServletRequest);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		return addressService.deleteAddressById(id);
		
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
	
	

}
