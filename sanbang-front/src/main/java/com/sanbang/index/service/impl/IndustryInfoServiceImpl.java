package com.sanbang.index.service.impl;

import java.util.ArrayList;
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
	public Map<String, Object> getIndustryInfoByKinds(Long kindsId, int currentPage) {
		Map<String, Object> mmp = new HashMap<>();
		//获取总页数
		int totalCount = this.ezssubstanceMapper.goodsIndustryCountByKinds(kindsId);
		List<ezs_ezssubstance> glist=null;
		ExPage page = new ExPage(totalCount, Integer.valueOf(currentPage));
		page.setPageSize(10);
		page.setContent(String.valueOf(kindsId));
		if(totalCount>0){
			if((Integer.valueOf(currentPage)>=1&&Integer.valueOf(currentPage)<=page.getTotalPageCount())||(page.getTotalPageCount()==0)){
				 glist = this.ezssubstanceMapper.goodsIndustryByPage(page);
				mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				mmp.put("Msg", "请求成功");
				mmp.put("Page", page);
				mmp.put("Obj", glist);
			}else{
				 glist=new ArrayList<>();
				mmp.put("ErrorCode", HomeDictionaryCode.ERROR_HOME_PAGE_FAIL);
				mmp.put("Msg", "暂无数据");
				mmp.put("Page", page);
				mmp.put("Obj", glist);
			}
		}else{
			glist=new ArrayList<>();
			mmp.put("ErrorCode", HomeDictionaryCode.ERROR_HOME_KIND_ERROR);
			mmp.put("Msg", "查询类型有误");
			mmp.put("Page", page);
			mmp.put("Obj", glist);
		}
		return mmp;
	}
	/**
	 * 各种文档分页展示（全部展示不按分类）
	 */
	@Override
	public Map<String, Object> getAllIndustryInfoByParentKinds(Long parentKindsId,int currentPage,int pagesize) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>(); 
		//获取总页数
		int totalCount = this.ezssubstanceMapper.goodsAllIndustryCount(parentKindsId); 
		if(totalCount>0){
			ExPage page = new ExPage(totalCount, currentPage); 
			//page.setPageSize(10);
			//app 端做调整
			page.setPageSize(pagesize);
			page.setContent(String.valueOf(parentKindsId));
			if((Integer.valueOf(currentPage)>=1&&Integer.valueOf(currentPage)<=page.getTotalPageCount())||(page.getTotalPageCount()==0)){
				List<ezs_ezssubstance> glist = this.ezssubstanceMapper.selectAllGoodsIndustryByPage(page);
				mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				mmp.put("Page", page);
				mmp.put("Obj", glist);
			}else{
				mmp.put("ErrorCode", HomeDictionaryCode.ERROR_HOME_PAGE_FAIL);
				mmp.put("Msg", "暂无数据");
				mmp.put("Page", page);
			}
		}else{
			mmp.put("ErrorCode", HomeDictionaryCode.ERROR_HOME_KIND_ERROR);
			mmp.put("Msg", "查询类型有误");
		}
		return mmp;
	}

	@Override
	public Map<String, Object> getEssayBySecondTheme(Long parentKindsId, String currentPage) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>(); 
		//获取总页数
		int totalCount = this.ezssubstanceMapper.goodsAllIndustryCount(parentKindsId); 
		if(totalCount>0){
			ExPage page = new ExPage(totalCount, Integer.valueOf(currentPage)); 
			page.setPageSize(10);
			page.setContent(String.valueOf(parentKindsId));
			if((Integer.valueOf(currentPage)>=1&&Integer.valueOf(currentPage)<=page.getTotalPageCount())||(page.getTotalPageCount()==0)){
				//List<ezs_ezssubstance> glist = this.ezssubstanceMapper.selectAllGoodsIndustryByPage(page);
				List<ezs_ezssubstance> glist = this.ezssubstanceMapper.selectEssayThemeByPage(page);
				mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				mmp.put("Page", page);
				mmp.put("Obj", glist);
			}else{
				mmp.put("ErrorCode", HomeDictionaryCode.ERROR_HOME_PAGE_FAIL);
				mmp.put("Msg", "暂无数据");
				mmp.put("Page", page);
			}
		}else{
			mmp.put("ErrorCode", HomeDictionaryCode.ERROR_HOME_KIND_ERROR);
			mmp.put("Msg", "查询类型有误");
		}
		return mmp;
	}

	@Override
	public Map<String, Object> getAllIndustryInfoByParentKinds2(Long parentKindsId,Long ecId,int currentPage, int pagesize) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>(); 
		//获取总页数
		int totalCount = this.ezssubstanceMapper.goodsAllIndustryCount2(parentKindsId,ecId); 
		if(totalCount>0){
			ExPage page = new ExPage(totalCount, currentPage); 
			//page.setPageSize(10);
			//app 端做调整
			page.setPageSize(pagesize);
			page.setContent(String.valueOf(parentKindsId));
			page.setEcId(ecId);
			if((Integer.valueOf(currentPage)>=1&&Integer.valueOf(currentPage)<=page.getTotalPageCount())||(page.getTotalPageCount()==0)){
				List<ezs_ezssubstance> glist = this.ezssubstanceMapper.selectAllGoodsIndustryByPage2(page);
				mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				mmp.put("Page", page);
				mmp.put("Obj", glist);
			}else{
				mmp.put("ErrorCode", HomeDictionaryCode.ERROR_HOME_PAGE_FAIL);
				mmp.put("Msg", "暂无数据");
				mmp.put("Page", page);
			}
		}else{
			mmp.put("ErrorCode", HomeDictionaryCode.ERROR_HOME_KIND_ERROR);
			mmp.put("Msg", "查询类型有误");
		}
		return mmp;
	}
}
