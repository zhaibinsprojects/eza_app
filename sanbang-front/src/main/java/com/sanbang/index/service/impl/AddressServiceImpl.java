package com.sanbang.index.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_area;
import com.sanbang.dao.ezs_areaMapper;
import com.sanbang.dao.ezs_userMapper;
import com.sanbang.index.service.AddressService;
import com.sanbang.utils.RedisUtils;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.HomeDictionaryCode;
import com.sanbang.vo.HotProvince;

@Service
public class AddressServiceImpl implements AddressService {
	//日志
	private static Logger log = Logger.getLogger(AddressServiceImpl.class.getName());
	@Resource
	private ezs_areaMapper areaMapper;
	/**
	 * 获取热门省份
	 */
	@Override
	public Map<String, Object> getHotAddress() {
		Map<String,Object> mmp = new HashMap<>();
		List<HotProvince> hlist = null;
		//查询缓存是否已存在
		//hlist = (List<HotProvince>) RedisUtils.getList("hotProvince");
		//hlist =(List<HotProvince>) RedisUtils.get("hotProvince",HotProvince.class);
		if(hlist==null){
			hlist = this.areaMapper.getHotArea();
			if(hlist!=null){
				//存储redis
				//RedisUtils.setList("hotProvince", hlist);
				mmp.put("HotAreasList", hlist);
				mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			}else{
				mmp.put("ErrorCode", HomeDictionaryCode.ERROR_HOME_HOTCITY_FAIL);
				mmp.put("Msg", "查询异常");
				log.error("热门城市查询异常");
			}
		}else{
			mmp.put("HotAreasList", hlist);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		}
		return mmp;
	}
	@Override
	public Map<String, Object> getProvince() {
		Map<String,Object> mmp = new HashMap<String, Object>();
		List<ezs_area> elist = null;
		//elist = (List<ezs_area>) RedisUtils.getList("ProvinceMess");
		if(elist==null){
			elist = this.areaMapper.getAreaParentList();
			if(elist!=null){
				//RedisUtils.setList("ProvinceMess", elist);
				mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				mmp.put("Obj", elist);
			}else{
				mmp.put("ErrorCode", HomeDictionaryCode.ERROR_HOME_HOTCITY_FAIL);
				mmp.put("Msg", "查询异常");
			}
		}else{
			mmp.put("Obj", elist);
			mmp.put("ErrorCode", HomeDictionaryCode.ERROR_HOME_HOTCITY_FAIL);
		}
		return mmp;
	}
	@Override
	public Map<String, Object> getChildByParents(Long aid) {
		Map<String,Object> mmp = new HashMap<String, Object>();
		List<ezs_area> elist = null;
		elist = this.areaMapper.getAreaListByParId(aid);
		if(elist!=null){
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Obj", elist);
		}else{
			mmp.put("ErrorCode", HomeDictionaryCode.ERROR_HOME_HOTCITY_FAIL);
			mmp.put("Msg", "查询异常");
		}
		return mmp;
	}
	@Override
	public Map<String, Object> getParentByChild(Long pId) {
		// TODO Auto-generated method stub
		Map<String,Object> mmp = new HashMap<String, Object>();
		ezs_area pArea = this.areaMapper.selectParentByChildKey(pId);
		if(pArea!=null){
			mmp.put("Obj", pArea);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		}else{
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
		}
		return mmp;
	}
	/**
	 * @author zhaibin
	 * 获取节点的所有子节点（包含叶子节点）
	 */
	@Override
	public Map<String, Object> getAllChildID(Long aid) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		List<ezs_area> areaIDList = new ArrayList<>();
		ezs_area firstArea = this.areaMapper.selectByPrimaryKey(aid);
		List<ezs_area> firstAreaList = null;
		List<ezs_area> secondAreaList = null;
		if(firstArea!=null){
			int firstLevel = firstArea.getLevel();
			//firstArea.level=2 在此终结
			areaIDList.add(firstArea);
			if(firstArea.getLevel()<2){
				//firstAreaList = this.areaMapper.getAreaListByParId(firstArea.getId());
				firstAreaList = this.areaMapper.getAreasByParentId(firstArea.getId());
				//firstArea.level=1 在此终结
				areaIDList.addAll(firstAreaList);
				if(firstAreaList!=null&&firstAreaList.size()>0&&firstLevel==0&&firstAreaList.get(0).getLevel()==1){
					for (ezs_area area : firstAreaList) {
						//secondAreaList = this.areaMapper.getAreaListByParId(area.getId());
						secondAreaList = this.areaMapper.getAreasByParentId(area.getId());
						if(secondAreaList!=null&&secondAreaList.size()>0){
							areaIDList.addAll(secondAreaList);
							secondAreaList.clear();
						}
					}
				}
			}
		}
		mmp.put("Obj", areaIDList);
		mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		return mmp;
	}
}
