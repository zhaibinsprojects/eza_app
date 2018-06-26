package com.sanbang.addressmanage.service;

import java.util.List;

import com.sanbang.bean.ezs_address;
import com.sanbang.bean.ezs_user;
import com.sanbang.utils.Page;
import com.sanbang.utils.Result;

public interface AddressService {	


	Result save(ezs_address ezs_address, ezs_user upi);

	List<ezs_address> findAddressByUserId(Long id,Page page);

	ezs_address findAddressById(Long id);

	Result updateAddressById(ezs_address ezs_address, ezs_user upi);

	Result deleteAddressById(Long id);
	
	Result setBestow(Long id, Boolean bestow,Long userId);
}
