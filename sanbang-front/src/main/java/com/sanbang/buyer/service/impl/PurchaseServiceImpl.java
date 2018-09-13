package com.sanbang.buyer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_customized;
import com.sanbang.bean.ezs_customized_record;
import com.sanbang.bean.ezs_customized_res;
import com.sanbang.buyer.service.PurchaseService;
import com.sanbang.dao.ezs_customizedMapper;
import com.sanbang.utils.StringUtil;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;

@Service
public class PurchaseServiceImpl implements PurchaseService {
	@Autowired
	private ezs_customizedMapper customizedMapper;
	
	@Override
	public Map<String, Object> getPurchaseGoodsByUser(HttpServletRequest request,Long userId) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		List<ezs_customized> elist = null;
		
		String pageNow=request.getParameter("pageNow");
		int pageNo=1;
		if(!Tools.isEmpty(pageNow)){
			pageNo=Integer.valueOf(pageNow);
			if(pageNo<=1){
				pageNo=1;
			}
		}else{
			pageNo=1;
		}
		
		try {
			elist = this.customizedMapper.getPurchaseByUserId(userId,Long.valueOf((pageNo-1)*10),10);
			mmp.put("Obj", elist);
			mmp.put("Msg", "响应成功");
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		} catch (Exception e) {
			// TODO: handle exception\
			e.printStackTrace();
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "响应失败");
		}
		return mmp;
	}

	@Override
	public Map<String, Object> getPurchaseById(Long Id) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		ezs_customized customized = null;
		try{
			customized = this.customizedMapper.getPurchaseById(Id);
			
			List<ezs_customized_record> recordlist=customized.getRecordlist();
			List<ezs_customized_res>  reslist=new ArrayList<>();
			
			int i=0;
			for (ezs_customized_record record : recordlist) {
				if("1".equals(record.getFlag())) {
					String goodsid= "";
					try {
						goodsid=record.getRemark().substring(record.getRemark().lastIndexOf("_")+1, record.getRemark().lastIndexOf("."));
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(!StringUtil.isEmpty(goodsid)) {
						ezs_customized_res res=new ezs_customized_res();
						res.setAddTime(record.getAddTime());
						res.setCustomized_id(record.getCustomized_id());
						res.setCustomized_status("1");
						res.setDeleteStatus(false);
						res.setGoods_id(Long.valueOf(goodsid));
						res.setId(record.getId());
						res.setRemark("已解决");
						res.setSupplier_id(record.getPurchaser_id());
						reslist.add(res);
						recordlist.remove(i);
					}
					
				}
				i++;
			}
			customized.setRecordlist(recordlist);
			customized.setReslist(reslist);
			
			mmp.put("Obj", customized);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Msg", "响应成功");
		}catch(Exception e){
			e.printStackTrace();
			customized=new ezs_customized();
			mmp.put("Obj", customized);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "响应失败");
		}
		return mmp;
	}

	@Override
	public Map<String, Object> submitOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> submitQuestion(String Msg) {
		// TODO Auto-generated method stub
		return null;
	}

}
