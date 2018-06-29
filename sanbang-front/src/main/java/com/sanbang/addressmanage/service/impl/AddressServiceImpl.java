package com.sanbang.addressmanage.service.impl;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.addressmanage.service.AddressService;
import com.sanbang.bean.ezs_address;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_addressMapper;
import com.sanbang.utils.DateUtils;
import com.sanbang.utils.Page;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;

@Service("addressService")
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	private ezs_addressMapper ezs_addressMapper;


	@Override
	public Result save(ezs_address ezs_address, ezs_user upi) {
		Result result = addressValidation(ezs_address);
		if(result.getSuccess()){
			//查询新地址是否已存在
			/*ezs_address address = ezs_addressMapper.findByAreaidAndAreainfoAnduserid(ezs_address.getArea_id(),ezs_address.getArea_info(),upi.getId());
			
			if(address!=null){
				result.setMsg("该地址已存在");				
				return result;
			}*/
			
			if(!ezs_address.getBestow()){//true取消默认；false:设置默认
				//查询是否有默认
				ezs_address address = ezs_addressMapper.findAddressByUseridAndBes(upi.getId(),ezs_address.ADDRESS_BESTOW_0);
				if(address !=null){
					address.setBestow(ezs_address.ADDRESS_BESTOW_1);
					ezs_addressMapper.updateByPrimaryKeySelective(address);
				}
			}
			
			try {
				ezs_address.setAddTime(DateUtils.getCurrentDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			ezs_address.setDeleteStatus(ezs_address.ADDRESS_DELETESTATUS_0);
			ezs_address.setUser_id(upi.getId());
			
			ezs_addressMapper.insert(ezs_address);
			result.setMsg("添加成功");
		}	
		return result;
	}


	private Result addressValidation(ezs_address ezs_address) {
		Result result = new Result().success();
		if(Tools.isEmpty(ezs_address.getTrueName())){
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入收货人姓名");
			return result;
		}
		if(Tools.isEmpty(ezs_address.getArea_id()+"")){//首行“请选择”默认为0
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请选择收货地址");
			return result;
		}
		if(Tools.isEmpty(ezs_address.getArea_info())){
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入详细收货地址");
			return result;
		}
		if(Tools.isEmpty(ezs_address.getMobile())){
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入手机号码");
			return result;	
		}else{
			if(!Tools.isMobile(ezs_address.getMobile())){
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("请输入有效的手机号码");
				return result;
			}
		}
		if(!Tools.isEmpty(ezs_address.getTelephone())){
			if(!Tools.isMobileAndPhone(ezs_address.getTelephone())){
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("请输入有效的电话号码");
				return result;
			}
		
		}
		return result;
	}

	@Override
	public List<ezs_address> findAddressByUserId(Long id,Page page) {
		
		page.setStartPos(page.getPageNow());
		int totalcount = ezs_addressMapper.getCountAddressByUserId(id);
		
		page.setTotalCount(totalcount);
		Map<String,Object> map = new HashMap<>();
		
		map.put("userid", id);
		map.put("startPos", page.getStartPos());
		map.put("pageSize", page.getPageSize());
		
		return ezs_addressMapper.selectByUserId(map);
	}

	@Override
	public ezs_address findAddressById(Long id) {
		
		return ezs_addressMapper.selectByPrimaryKey(id);
	}

	@Override
	public Result updateAddressById(ezs_address ezs_address, ezs_user upi) {
		Result result = addressValidation(ezs_address);
		if(result.getSuccess()){
			//查询新地址是否已存在
			/*ezs_address address = ezs_addressMapper.findByAreaidAndAreainfoAnduserid(ezs_address.getArea_id(),ezs_address.getArea_info(),upi.getId());
			if(address!=null){
				result.setMsg("该地址已存在");				
				return result;
			}*/
			ezs_addressMapper.updateByPrimaryKeySelective(ezs_address);
			result.setMsg("修改成功");
		}
		
		return result;
	}

	@Override
	public Result deleteAddressById(Long id) {
		Result result = new Result();
		ezs_address ezs_address = ezs_addressMapper.selectByPrimaryKey(id);
		if(ezs_address==null){
			result.setMsg("该地址已删除");
			return result;	
		}
		ezs_address.setDeleteStatus(ezs_address.ADDRESS_DELETESTATUS_1);
		//ezs_addressMapper.deleteByPrimaryKey(id);
		ezs_addressMapper.updateByPrimaryKeySelective(ezs_address);
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setSuccess(true);
		result.setMsg("删除成功");
		
		return result;
	}
	@Override
	public Result setBestow(Long id, Boolean bestow,Long userId) {
		Result result = Result.failure();
		ezs_address ezs_address = ezs_addressMapper.selectByPrimaryKey(id);
		if(ezs_address==null){
			result.setMsg("该地址不存在，请刷新后再试！");
			return result;
		}
		if(!bestow){//true取消默认；false:设置默认
			//查询是否有默认
			ezs_address address = ezs_addressMapper.findAddressByUseridAndBes(userId,ezs_address.ADDRESS_BESTOW_0);
			if(address !=null){
				address.setBestow(ezs_address.ADDRESS_BESTOW_1);
				ezs_addressMapper.updateByPrimaryKeySelective(address);
			}
			ezs_address.setBestow(ezs_address.ADDRESS_BESTOW_0);
			ezs_addressMapper.updateByPrimaryKeySelective(ezs_address);
		}else{
			ezs_address.setBestow(ezs_address.ADDRESS_BESTOW_1);
			ezs_addressMapper.updateByPrimaryKeySelective(ezs_address);
		}
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("设置成功");
		result.setSuccess(true);
		
		return result;
	}

}
