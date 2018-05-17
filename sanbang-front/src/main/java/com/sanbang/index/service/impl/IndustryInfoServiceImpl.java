package com.sanbang.index.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_column;
import com.sanbang.bean.ezs_ezssubstance;
import com.sanbang.dao.ezs_columnMapper;
import com.sanbang.dao.ezs_ezssubstanceMapper;
import com.sanbang.index.service.IndustryInfoService;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.ExPage;
import com.sanbang.vo.HomeDictionaryCode;

@Service
public class IndustryInfoServiceImpl implements IndustryInfoService {
	@Autowired
	private ezs_columnMapper columnMapper;
	@Autowired
	private ezs_ezssubstanceMapper ezssubstanceMapper; 
	
	@Override
	public Map<String, Object> getSecondTheme(Long id) {
		// TODO Auto-generated method stub
		List<ezs_column> elist = null;
		Map<String, Object> mmp = new HashMap<>();
		try {
			elist = this.columnMapper.getSecondThemeByFirstTheme(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(elist!=null){
			mmp.put("Obj", elist);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		}else{
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg","参数传递有误");
		}
		return mmp;
	}

	@Override
	public Map<String, Object> getIndustryInfoByKinds(Long kindsId, String currentPage) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		//获取总页数
		int totalCount = this.ezssubstanceMapper.goodsIndustryCountByKinds(kindsId);
		ExPage page = new ExPage(totalCount, Integer.valueOf(currentPage));
		page.setPageSize(10);
		page.setContent(String.valueOf(kindsId));
		if(Integer.valueOf(currentPage)>=1||Integer.valueOf(currentPage)<=page.getTotalPageCount()){
			List<ezs_ezssubstance> glist = this.ezssubstanceMapper.goodsIndustryByPage(page);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Page", page);
			mmp.put("Obj", glist);
		}else{
			mmp.put("ErrorCode", HomeDictionaryCode.ERROR_HOME_PAGE_FAIL);
			mmp.put("Page", page);
		}
		return mmp;
	}
}
