package com.sanbang.app.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_column;
import com.sanbang.bean.ezs_ezssubstance;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_ezssubstanceMapper;
import com.sanbang.index.service.IndustryInfoService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.ExPage;
import com.sanbang.vo.goods.GoodsVo;

@Controller
@RequestMapping("/app/home")
public class AppHomeIndustryInfoController {
	@Autowired
	private IndustryInfoService industryInfoService;
	
	@Autowired
	private ezs_ezssubstanceMapper ezs_ezssubstanceMapper;
	
	private static final String view="/cata/";
	
	/**
	 * 获取行业资讯的二级栏目（一级为行业资讯）
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
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
	@SuppressWarnings("unchecked")
	@RequestMapping("/getSubstance")
	@ResponseBody
	public Object getSubjectByTheme(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(name="id",defaultValue="57")Long id,
			int currentPage){
		Map<String, Object> mmp = null;
		List<ezs_ezssubstance> elist = null;
		Result rs = null;
		if(currentPage<=0)
			currentPage = 1;
		mmp = this.industryInfoService.getIndustryInfoByKinds(id, currentPage);
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
	
	/**
	 * 详情页面
	 * @param request
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/subjectshowinfo")
	public String Subjectshowinfo(HttpServletRequest request,Long id,Model model){
		//用户校验begin
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(null==upi){
			upi=RedisUserSession.getUserInfoByKeyForApp(request);
		}
		model.addAttribute("userkey", null==upi?"":upi.getUserkey());
		//用户校验end
		
		
		 ezs_ezssubstance info=ezs_ezssubstanceMapper.selectByPrimaryKey(id);
		model.addAttribute("info", info);
		return view+"subjectshowinfo";
	
	}
	
	
}
