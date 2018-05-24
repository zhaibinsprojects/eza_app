package com.sanbang.seller.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_address;
import com.sanbang.bean.ezs_area;
import com.sanbang.bean.ezs_invoice;
import com.sanbang.bean.ezs_logistics;
import com.sanbang.bean.ezs_order_info;
import com.sanbang.dao.ezs_addressMapper;
import com.sanbang.dao.ezs_areaMapper;
import com.sanbang.dao.ezs_invoiceMapper;
import com.sanbang.dao.ezs_logisticsMapper;
import com.sanbang.dao.ezs_purchase_orderformMapper;
import com.sanbang.seller.service.SellerOrderService;
import com.sanbang.vo.PagerOrder;
@Service
public class SellerOrderServiceImpl implements SellerOrderService {

	@Autowired
	ezs_purchase_orderformMapper purchaseOrderformMapper;
	
	@Autowired
	ezs_invoiceMapper invoiceMapper;
	@Autowired
	ezs_logisticsMapper logisticsMapper;
	
	@Autowired
	private ezs_addressMapper ezs_addressMapper;

	
	@Autowired
	private ezs_areaMapper ezs_areaMapper;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
	
	@Override
	public List<ezs_order_info> getOrderListByValue(PagerOrder pager) {
		
		pager.setPageNow(pager.getPageNow() - 1);
		pager.setSecount(pager.getPageSize() * pager.getPageNow());
		int totalcount = purchaseOrderformMapper.getOrderListByValueCount(pager);
		pager.setTotalCount(totalcount);
		return purchaseOrderformMapper.getOrderListByValue(pager);
	}

	@Override
	public Map<String, Object> queryOrderInfoById(String order_no) {
		Map<String, Object> map = new HashMap<String, Object>();

//		ezs_order_info orderinfo = purchaseOrderformMapper.getOrderListByOrderno(order_no);
		ezs_order_info purchaseOrder = purchaseOrderformMapper.getOrderListByOrderno(order_no);

		// 收货地址处理
		long addressid = purchaseOrder.getAddress_id();
		ezs_address ezs_address = ezs_addressMapper.selectByPrimaryKey(addressid);
		if (null != ezs_address) {
			String str = getaddressinfo(ezs_address.getArea_id());
			ezs_address.setArea_info(str += ezs_address.getArea_info());
		}

		map.put("address", ezs_address);// 收货地址
		map.put("name", purchaseOrder.getName());
		map.put("price", purchaseOrder.getPrice());
		map.put("goods_amount", purchaseOrder.getGoods_amount());
		map.put("total_price", purchaseOrder.getTotal_price());
		map.put("order_no", purchaseOrder.getOrder_no());
		map.put("addTime", sdf.format(purchaseOrder.getAddTime()));
		map.put("order_status", purchaseOrder.getOrder_status());
		return map;
	}
	
	@Override
	public ezs_invoice queryInvoiceByNo(String orderNo) {
		
		return invoiceMapper.selectInvoiceByOrderNo(orderNo);
	}

	@Override
	public ezs_logistics queryLogisticsByNo(String orderNo) {
		return logisticsMapper.selectByOrderNo(orderNo);
	}
	
	private String getaddressinfo(long areaid) {
		StringBuilder sb = new StringBuilder();
		String threeinfo = "";
		String twoinfo = "";
		String oneinfo = "";
		ezs_area ezs_threeinfo = ezs_areaMapper.selectByPrimaryKey(areaid);
		if (ezs_threeinfo == null) {
			threeinfo = ezs_threeinfo.getAreaName();
			ezs_area ezs_twoinfo = ezs_areaMapper.selectByPrimaryKey(ezs_threeinfo.getParent_id());
			if (ezs_twoinfo == null) {
				twoinfo = ezs_twoinfo.getAreaName();
				ezs_area ezs_oneinfo = ezs_areaMapper.selectByPrimaryKey(ezs_twoinfo.getParent_id());
				if (ezs_oneinfo == null) {
					oneinfo = ezs_twoinfo.getAreaName();
				}
			}
		}

		sb.append(oneinfo);
		sb.append(twoinfo);
		sb.append(threeinfo);
		return sb.toString();
	}
	
}
