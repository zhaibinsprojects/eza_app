package com.sanbang.returnGoods.service.imp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sanbang.bean.ReturnOrder;
import com.sanbang.returnGoods.service.ReturnGoodsService;

/**
 * 退货流程
 * @author hanlongfei
 * 2018年05月23日
 */
@Service("returnGoodsService")
public class ReturnGoodsServiceImpl implements ReturnGoodsService{
	@Resource(name="ReturnOrderMapper")
	private com.sanbang.dao.ReturnOrderMapper returnOrderMapper;
	/**
	 * 查询所有退货订单
	 */
	public List returnOrder(){
		List<ReturnOrder> list = new ArrayList();
		list = returnOrderMapper.selectListByState();
		return list;
	}
	
}
