package com.sanbang.index.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_address;
import com.sanbang.bean.ezs_area;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_addressMapper;
import com.sanbang.dao.ezs_areaMapper;
import com.sanbang.index.service.CustomerService;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.UserInfoMess;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private ezs_addressMapper addressMapper;
	@Autowired
	private ezs_areaMapper areaMapper;
	
	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
	}

	@Override
	public Object queryById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getUserMessByUser(ezs_user user) {
		Map<String, Object> mmp = new HashMap<>();
		UserInfoMess uim = new UserInfoMess();
		//List<ezs_address> elist = this.addressMapper.getAddressByUserId(user.getId());
		//false 表示默认，true 表示非默认
		List<ezs_address> DefaultList = this.addressMapper.getAddressByUserIdAndBestow(user.getId(),false);
		List<ezs_address> eList = this.addressMapper.getAddressByUserIdAndBestow(user.getId(),true);
		if(user!=null&&DefaultList!=null&&DefaultList.size()>0){
			//默认地址
			uim.setUserAddress(DefaultList);
			//获取详细地址信息
			List<String> addressMessList = new ArrayList<>();
			for (ezs_address address : DefaultList) {
				String addressMess = getaddressinfo(address.getArea_id());
				addressMessList.add(addressMess);
			}
			uim.setAddressMess(addressMessList);
			mmp.put("Obj", uim);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		}else if(eList!=null&&eList.size()>0){
			List<ezs_address> elistTemp = new ArrayList<>();
			List<String> addressMessListTemp = new ArrayList<>();
			elistTemp.add(eList.get(0));
			uim.setUserAddress(elistTemp);
			String addressMess = getaddressinfo(eList.get(0).getArea_id());
			addressMessListTemp.add(addressMess);
			uim.setAddressMess(addressMessListTemp);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Obj", uim);
			mmp.put("Msg", "未查询到该用户的默认地址信息,返回任意地址");
		}else{
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			uim.setAddressMess(new ArrayList<String>());
			uim.setUserAddress(new ArrayList<ezs_address>());
			mmp.put("Obj", uim);
			mmp.put("Msg", "未查询到该用户的默认地址信息");
		}
		return mmp;
	}
	
	/**
	 * 借来一用
	 * 获取相关详细地址信息
	 * @param areaid
	 * @return
	 */
	private String getaddressinfo(long areaid) {
		StringBuilder sb = new StringBuilder();
		String threeinfo = "";
		String twoinfo = "";
		String oneinfo = "";
		ezs_area ezs_threeinfo = areaMapper.selectByPrimaryKey(areaid);
		if (ezs_threeinfo != null) {
			threeinfo = ezs_threeinfo.getAreaName();
			ezs_area ezs_twoinfo = areaMapper.selectByPrimaryKey(ezs_threeinfo.getParent_id());
			if (ezs_twoinfo != null) {
				twoinfo =  ezs_twoinfo.getAreaName();
				ezs_area ezs_oneinfo = areaMapper.selectByPrimaryKey(ezs_twoinfo.getParent_id());
				if (ezs_oneinfo != null) {
					oneinfo =  ezs_oneinfo.getAreaName();
				}
			}
		}
		if(!Tools.isEmpty(threeinfo)){
			sb = new StringBuilder().append(threeinfo);	
		}
		if(!Tools.isEmpty(twoinfo)){
			sb = new StringBuilder().append(twoinfo).append("-").append(threeinfo);
		}
		if(!Tools.isEmpty(oneinfo)){
			sb = new StringBuilder().append(oneinfo).append("-").append(twoinfo).append("-").append(threeinfo);
		}
		return sb.toString();
	}
}
