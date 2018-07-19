package com.sanbang.index.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_column;
import com.sanbang.bean.ezs_ezssubstance;
import com.sanbang.dao.ezs_columnMapper;
import com.sanbang.dao.ezs_ezssubstanceMapper;
import com.sanbang.index.service.ReportEssayServer;
import com.sanbang.upload.sevice.impl.FileUploadServiceImpl;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.ExPage;

@Service
public class ReportEssayServerImpl implements ReportEssayServer {
	
	private static Logger log = Logger.getLogger(ReportEssayServerImpl.class);
	
	@Autowired
	private ezs_columnMapper columnMapper;
	@Autowired
	private ezs_ezssubstanceMapper ezssubstanceMapper;

	/**
	 * 获取二级标题，如（周评-月评-日评。。。。。）
	 */
	@Override
	public Map<String, Object> getSecondTheme(Long parentId) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		List<ezs_column> columnList = null;
		try {
			columnList = this.columnMapper.getSecondThemeByFirstTheme(parentId);
			mmp.put("Obj", columnList);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Msg", "查询成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
			e.printStackTrace();
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
		}
		return mmp;
	}
	/**
	 * 分页查询，根据二级标题查询文章报告（本方法返回文章全部内容）
	 */
	@Override
	public Map<String, Object> getReportEssayTheme(Long parentId,int currentPage) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		List<ezs_ezssubstance> substanceList = null;
		//获取总条目数，供分页使用
		int totalCount = this.ezssubstanceMapper.goodsIndustryCountByKinds(parentId);
		ExPage page = new ExPage(totalCount, currentPage);
		page.setContent(String.valueOf(parentId));
		if(currentPage<=0||currentPage>page.getTotalPageCount()){
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			//须返回本结构
			mmp.put("Obj", new ArrayList<ezs_ezssubstance>());
			mmp.put("Msg", "查询成功，暂无数据");
			return mmp;
		}
		try {
			substanceList = this.ezssubstanceMapper.goodsIndustryByPage(page);  
			mmp.put("Obj", substanceList);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Msg", "查询成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
			e.printStackTrace();
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
		}
		return mmp;
	}
	/**
	 * 根据文章报告的ID进行查询
	 */
	@Override
	public Map<String, Object> getReportEssayContext(Long reportId) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		ezs_ezssubstance substance = null; 
		try {
			substance = this.ezssubstanceMapper.selectByPrimaryKey(reportId);
			mmp.put("Obj", substance);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Msg", "查询成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
			e.printStackTrace();
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
		}
		return mmp;
	}

}
