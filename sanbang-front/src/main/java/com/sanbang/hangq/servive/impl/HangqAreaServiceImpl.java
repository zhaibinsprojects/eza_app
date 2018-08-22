package com.sanbang.hangq.servive.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.dao.ezs_hangqareaMapper;
import com.sanbang.hangq.servive.HangqAreaService;
import com.sanbang.utils.Result;
import com.sanbang.vo.hangq.HangqParamCommonVo;


@Service("hangqAreaService")
public class HangqAreaServiceImpl implements HangqAreaService{
	
	private static final String  AREAJSONPATH="";
	private static final String  CATAJSONPATH="";
	private static final String  COLORANDAPPLAYJSONPATH="";
	
	@Autowired
	private ezs_hangqareaMapper ezs_hangqareaMapper;
	@Override
	public Map<String, Object> getHangqParamDate(String reqtype,Map<String, Object> redate) {
		
		try {
			List<HangqParamCommonVo> list=new ArrayList<>();
			//data_sources;// 数据来源  1 .实时成交价 2.供应商报价 3.站外市场价  4.网络数据 
			//再生报价
			if(reqtype.equals("zsbj")) {
				list=ezs_hangqareaMapper.getAreaBySourcesOrStatus("2,3", 2);
			//再生走势	
			}else if(reqtype.equals("zszs")){
				list=ezs_hangqareaMapper.getAreaBySourcesOrStatus("1", 2);
			}else if(reqtype.equals("xlbj")) {
				//TODO
			}else if(reqtype.equals("xlzs")) {
				//TODO
			}
			redate.put("area", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return redate;
	}
	
	
	

}
