package com.sanbang.buyer.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_dvaluate;
import com.sanbang.bean.ezs_dvaluate_accessroy;
import com.sanbang.bean.ezs_order_info;
import com.sanbang.bean.ezs_user;
import com.sanbang.buyer.service.OrderEvaluateService;
import com.sanbang.dao.ezs_accessoryMapper;
import com.sanbang.dao.ezs_dvaluateMapper;
import com.sanbang.dao.ezs_dvaluate_accessroyMapper;
import com.sanbang.dao.ezs_orderformMapper;
import com.sanbang.vo.DictionaryCode;

@Service
public class OrderEvaluateServiceImpl implements OrderEvaluateService {
	// 日志
	private static Logger log = Logger.getLogger(OrderEvaluateServiceImpl.class);
	@Autowired
	private ezs_dvaluateMapper dvaluateMapper; 
	@Autowired
	private ezs_accessoryMapper accessoryMapper;
	@Autowired
	private ezs_dvaluate_accessroyMapper dvaluateAccessroyMapper;
	@Autowired
	private ezs_orderformMapper ezs_orderformMapper;

	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
	public Map<String, Object> orderEvaluate(ezs_dvaluate dvaluate,ezs_accessory accessory,ezs_user user) {
		Map<String, Object> mmp = new HashMap<>();
		log.info("开始处理订单评价begin...........................................");
		//图片信息
		if(accessory!=null){
			log.info("图片预制信息初始化》》》》》》");
			accessory.setAddTime(new Date());
			accessory.setUser_id(user.getId());
			accessory.setDeleteStatus(false);
			accessory.setHeight(0);
			accessory.setWidth(0);
			accessory.setSize(Float.valueOf(0));
		}
		//评价信息
		ezs_order_info orderinfo = ezs_orderformMapper.getOrderListByOrderno(dvaluate.getOrder_no(),user.getId());
		if(null==orderinfo){
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "订单不存在");
			return mmp;
		}
		if(orderinfo.getIspg()>0){
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "您已评价过该商品");
			return mmp;
			
		}
		dvaluate.setAddTime(new Date());
		dvaluate.setDeleteStatus(false);
		dvaluate.setUser_id(user.getId());
		dvaluate.setGoods_id(orderinfo.getGoodsid());
		try {
			//添加评价记录
			this.dvaluateMapper.insert(dvaluate);
			log.info("评价信息添加成功");
			//添加图片和映射记录
			if(accessory!=null&&accessory.getName()!=null){
				log.info("评价信息图片添加》》》》》》");
				ezs_dvaluate_accessroy dvaluateAccessroy = new ezs_dvaluate_accessroy();
				this.accessoryMapper.insert(accessory);
				dvaluateAccessroy.setAccessroy_id(accessory.getId());
				dvaluateAccessroy.setDvaluate_id(dvaluate.getId());
				dvaluateAccessroyMapper.insertSelective(dvaluateAccessroy);
			}
			log.info("评价功能完成！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Msg", "评价成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("评价处理异常："+e.toString());
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
			throw e;
		}
		return mmp;
	}

	@Override
	public List<ezs_dvaluate> getEvaluateList(int pageNo,long goodsid) {
		if(pageNo<1){
			pageNo=1;
		}
		List<ezs_dvaluate>  dvaluatelist=dvaluateMapper.getEvaluateList(10*(pageNo-1), 10,goodsid);
		return dvaluatelist;
	}

	@Override
	public int getEvaluateListPagerCount(long goodsid) {
		long count=dvaluateMapper.getEvaluateListPagerCount(goodsid);
		return  (int) Math.ceil(count/10);
	}

}
