package com.sanbang.dict.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_companyType_dict;
import com.sanbang.bean.ezs_dict;
import com.sanbang.bean.ezs_industry_dict;
import com.sanbang.dao.ezs_companyType_dictMapper;
import com.sanbang.dao.ezs_dictMapper;
import com.sanbang.dao.ezs_industry_dictMapper;
import com.sanbang.dict.service.DictService;


@Service("dictService")
public class DictServiceImpl implements DictService {

	@Autowired
	private ezs_dictMapper ezs_dictMapper;
	
	//关于用户公司类型存储
	@Resource(name="ezs_companyType_dictMapper")
	private ezs_companyType_dictMapper  ezs_companyType_dictMapper;

	//关于用户公司主营行业存储
	@Resource(name="ezs_industry_dictMapper")
	private ezs_industry_dictMapper  ezs_industry_dictMapper;
	
	@Override
	public ezs_dict getDictById(String code) {
		return ezs_dictMapper.getDictById(code);
	}

	@Override
	public List<ezs_dict> getDictByParentId(String code) {
		return ezs_dictMapper.getDictByParentId(code);
	}

	@Override
	public ezs_dict getDictByThisId(long id) {
		return ezs_dictMapper.selectByPrimaryKey(id);
	}

	@Override
	public List< ezs_industry_dict> getIndustryByThisId(long storeid) {
		return ezs_industry_dictMapper.getIndustryByThisId(storeid);
	}

	@Override
	public List<ezs_companyType_dict> getCompanyTypeByThisId(long storeid) {
		return ezs_companyType_dictMapper.getCompanyTypeByThisId(storeid);
	}

	@Override
	public String getCodeByAuditingId(Long auditingusertype_id) {
		return ezs_dictMapper.selectAuditingById(auditingusertype_id);
	}

	
	
}
