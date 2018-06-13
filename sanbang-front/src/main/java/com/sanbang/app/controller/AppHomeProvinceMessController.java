package com.sanbang.app.controller;

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
@RequestMapping("/app/home")
public class AppHomeProvinceMessController {
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
	@SuppressWarnings("unchecked")
	@RequestMapping("/hotProvince")
	@ResponseBody
	public Object hotProvinces(HttpServletRequest request,HttpServletResponse response){
		List<ezs_area> alist = new ArrayList<>();
		//获取热门省份ID
		Map<String, Object> mmp = this.addressService.getHotAddress();
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		List<HotProvince> clist = null;
		Result rs = null;
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			clist = (List<HotProvince>) mmp.get("HotAreasList");
			rs = Result.success();
			rs.setObj(clist);
		}
		else{
			rs = Result.failure();
			rs.setErrorcode(Integer.parseInt(mmp.get("ErrorCode").toString()));
			rs.setMsg("查询热门城市异常");
		}
		return rs;
	}
	/**
	 * 查询省份信息（只含有省份）
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getProvinces")
	@ResponseBody
	public Object getProvinces(HttpServletRequest request,HttpServletResponse response){
		List<ezs_area> elist = null;
		Map<String,Object> mmp = null;
		Result rs = null;
		mmp = this.addressService.getProvince();
		if(mmp!=null&&mmp.size()>0){
			if(mmp.get("ErrorCode").equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
				elist = (List<ezs_area>) mmp.get("Obj");
				rs = Result.success();
				rs.setObj(elist);
			}else{
				rs = Result.failure();
				rs.setMsg(mmp.get("Msg").toString());
				rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
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
	@SuppressWarnings("unchecked")
	@RequestMapping("/getChildByParents")
	@ResponseBody
	public Object getChildByParents(HttpServletRequest request,HttpServletResponse response,Long aid){
		List<ezs_area> elist = null;
		Map<String,Object> mmp = null;
		Result rs = null;
		mmp = this.addressService.getChildByParents(aid);
		elist = (List<ezs_area>) mmp.get("Obj");
		if(mmp.get("ErrorCode").equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			rs = Result.success();
			rs.setObj(elist);
			rs.setMsg("请求成功");
		}else{
			rs = Result.failure(); 
			rs.setErrorcode(Integer.parseInt(mmp.get("ErrorCode").toString()));
			rs.setMsg("请求失败");
		}
		return rs;
	}
}
