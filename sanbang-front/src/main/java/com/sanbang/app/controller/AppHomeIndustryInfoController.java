package com.sanbang.app.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_column;
import com.sanbang.bean.ezs_ezssubstance;
import com.sanbang.index.service.IndustryInfoService;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.ExPage;

@Controller
@RequestMapping("/home")
public class AppHomeIndustryInfoController {
	@Autowired
	private IndustryInfoService industryInfoService;
	/**
	 * 获取行业资讯的二级栏目（一级为行业资讯）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getTheme")
	@ResponseBody
	public Object getTheme(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> mmp = null;
		List<ezs_column> elist = null;
		Result rs = null;
		mmp = this.industryInfoService.getSecondTheme(Long.valueOf(57));
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			elist = (List<ezs_column>) mmp.get("Obj");
			rs = Result.success();
			rs.setObj(elist);
		}else{
			rs = Result.failure();
			rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
			rs.setMsg(mmp.get("Msg").toString());
		}
		return rs;
	}
	/**
	 * 行情资讯--根据二级栏目id获取相应的文章(分页展示，每页10条)
	 * @param request
	 * @param response
	 * @param id 二级栏目id
	 * @param currentPage 当前页面
	 * @return
	 */
	@RequestMapping("/getSubstance")
	@ResponseBody
	public Object getSubjectByTheme(HttpServletRequest request,HttpServletResponse response,Long id,String currentPage){
		Map<String, Object> mmp = null;
		List<ezs_ezssubstance> elist = null;
		Result rs = null;
		if(currentPage==null)
			currentPage = "1";
		if(id==null){
			mmp = this.industryInfoService.getAllIndustryInfoByParentKinds(Long.valueOf(57), currentPage);
		}else{
			mmp = this.industryInfoService.getIndustryInfoByKinds(id, currentPage);
		}
		ExPage page = (ExPage) mmp.get("Page");
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			elist = (List<ezs_ezssubstance>) mmp.get("Obj");
			rs = Result.success();
			rs.setObj(elist);
			rs.setMeta(page);
			
		}else{
			rs = Result.failure();
			rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
			rs.setMsg(mmp.get("Msg").toString());
		}
		return rs;
	}
	
}
