package com.sanbang.index.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_area;
import com.sanbang.index.service.AddressService;
import com.sanbang.index.service.CustomerService;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.HomeDictionaryCode;
import com.sanbang.vo.HotProvince;
/**
 * 
 * @author LENOVO
 *
 */
@Controller
@RequestMapping("/home")
public class HomeProvinceMessController {
	@Autowired
	private AddressService addressService;
	@Autowired
	private CustomerService customerService;
	/**
	 * 热门省份，根据全部客户选中数量显示前3个
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/hotProvince")
	@ResponseBody
	public Object hotProvinces(HttpServletRequest request,HttpServletResponse response){
		List<ezs_area> alist = new ArrayList<>();
		//获取热门省份ID
		Map<String, Object> mmp = this.addressService.getHotAddress();
		String errorcode = (String)mmp.get("ErrorCode");
		List<HotProvince> clist = null;
		Result rs = null;
		if(errorcode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			clist = (List<HotProvince>) mmp.get("HotAreasList");
			rs = Result.success();
			rs.setObj(clist);
		}
		else{
			rs = Result.failure();
			rs.setErrorcode(HomeDictionaryCode.ERROR_HOME_HOTCITY_FAIL);
			rs.setMsg("查询热门城市异常");
		}
		return rs;
	}
	/**
	 * 查询省份信息
	 * @param request
	 * @param response
	 * @return id 省份的id
	 */
	@RequestMapping("/dertailByProvince")
	@ResponseBody
	public Object dertailByProvince(HttpServletRequest request,HttpServletResponse response,String id){
		List<ezs_area> alist = new ArrayList<>();
		Result rs = Result.success();
		rs.setObj(alist);
		return rs;
	}
	/**
	 * 查询省份信息（只含有省份）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getProvinces")
	@ResponseBody
	public Object getProvinces(HttpServletRequest request,HttpServletResponse response){
		List<ezs_area> elist = null;
		Map<String,Object> obj = null;
		Result rs = null;
		obj = this.addressService.getProvince();
		if(obj!=null&&obj.size()>0){
			if(obj.get("ErrorCode").equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
				elist = (List<ezs_area>) obj.get("Obj");
				rs.setObj(elist);
				rs = Result.success();
			}else{
				rs = Result.failure();
				rs.setMsg(obj.get("Msg").toString());
			}
		}else{
			rs = Result.failure();
			rs.setMsg("查询不到地址信息");
		}
		return rs;
	}
	/**
	 * 根据父级地址节点查询子节点
	 * @param request
	 * @param response
	 * @param aid 父级地址节点
	 * @return
	 */
	@RequestMapping("/getChildByParents")
	@ResponseBody
	public Object getChildByParents(HttpServletRequest request,HttpServletResponse response,Long aid){
		List<ezs_area> elist = null;
		Map<String,Object> obj = null;
		Result rs = null;
		obj = this.addressService.getChildByParents(aid);
		elist = (List<ezs_area>) obj.get("Obj");
		if(obj.get("ErrorCode").equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			rs = Result.success();
			rs.setObj(elist);
			rs.setMsg("请求成功");
		}else{
			rs = Result.failure();
			rs.setMsg("请求失败");
		}
		return rs;
	}
}
