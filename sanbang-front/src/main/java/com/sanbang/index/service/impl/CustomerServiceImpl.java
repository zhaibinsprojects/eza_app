package com.sanbang.index.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_address;
import com.sanbang.bean.ezs_area;
import com.sanbang.bean.ezs_user;
import com.sanbang.bean.ezs_userinfo;
import com.sanbang.dao.ezs_addressMapper;
import com.sanbang.dao.ezs_areaMapper;
import com.sanbang.dao.ezs_customerMapper;
import com.sanbang.dao.ezs_userMapper;
import com.sanbang.dao.ezs_userinfoMapper;
import com.sanbang.index.service.CustomerService;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.UserInfoMess;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private ezs_addressMapper addressMapper;
	
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
		List<ezs_address> elist = this.addressMapper.getAddressByUserId(user.getId());
		if(user!=null&&elist!=null){
			uim.setUser(user);
			//包含默认地址的非默认地址
			uim.setUserAddress(elist);
			mmp.put("Obj", uim);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		}else{
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
		}
		uim.setUser(user);
		//包含默认地址的非默认地址
		uim.setUserAddress(elist);
		mmp.put("Obj", uim);
		return mmp;
	}

}
