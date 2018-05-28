package com.sanbang.seller.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_address;
import com.sanbang.bean.ezs_area;
import com.sanbang.bean.ezs_invoice;
import com.sanbang.bean.ezs_logistics;
import com.sanbang.bean.ezs_order_info;
import com.sanbang.bean.ezs_pact;
import com.sanbang.bean.ezs_purchase_orderform;
import com.sanbang.bean.ezs_user;
import com.sanbang.buyer.service.impl.BuyerServiceimpl;
import com.sanbang.dao.ezs_addressMapper;
import com.sanbang.dao.ezs_areaMapper;
import com.sanbang.dao.ezs_invoiceMapper;
import com.sanbang.dao.ezs_logisticsMapper;
import com.sanbang.dao.ezs_pactMapper;
import com.sanbang.dao.ezs_purchase_orderformMapper;
import com.sanbang.seller.service.SellerOrderService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.utils.httpclient.HttpRemoteRequestUtils;
import com.sanbang.utils.httpclient.HttpRequestParam;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.PagerOrder;

import net.sf.json.JSONObject;
@Service
public class SellerOrderServiceImpl implements SellerOrderService {
	
	private Logger log =Logger.getLogger(SellerOrderServiceImpl.class);
	
	@Value("${config.sign.callbackurl}")
	private String callbackurl;

	@Value("${config.sign.baseurl}")
	private String signbase;
	
	@Autowired
	ezs_purchase_orderformMapper purchaseOrderformMapper;
	
	@Autowired
	ezs_invoiceMapper invoiceMapper;
	@Autowired
	ezs_logisticsMapper logisticsMapper;
	
	@Autowired
	ezs_addressMapper ezs_addressMapper;
	@Autowired
	ezs_pactMapper ezs_pactMapper; 
	
	@Autowired
	ezs_logisticsMapper ezs_logisticsMapper;
	
	@Autowired
	private ezs_areaMapper ezs_areaMapper;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
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
		map.put("addTime", purchaseOrder.getAddTime());
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

	@Override
	public Result seller_order_signature(String order_no, HttpServletRequest request, HttpServletResponse response) {
		Result result = Result.failure();
		try {

			ezs_user upi = RedisUserSession.getLoginUserInfo(request);
			if (upi == null) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			if (Tools.isEmpty(order_no)) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("订单号不能为空");
				return result;
			}

			ezs_order_info orderinfo = purchaseOrderformMapper.getOrderListByOrderno(order_no);
			if (null == orderinfo) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("订单不存在");
				return result;
			}
			if (orderinfo.getOrder_status()!=20) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("合同不在签订状态！");
				return result;
			}
			if (orderinfo.getPact_status() == 1) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("纸质合同线下签订中，请您耐心等待！");
				return result;
			}
			
			/*// 先查一下合同的pdf在不在
			List<ezs_pact> pact = ezs_pactMapper.selectPactByOrderNo(order_no);

			if (pact == null||pact.size()==0) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("合同确认中，请稍后。如有疑问，咨询400-6666-890");
				return result;

			}*/
			

			Map<String, Object> mv = new HashMap<>();
			String company = upi.getEzs_store().getCompanyName();
			String getizhuhao = upi.getEzs_store().getAccount();
			String qiyereh = getizhuhao;
			String getijingyinguser = upi.getEzs_store().getPerson();
			String accountType = upi.getEzs_store().getAccountType() + "";
			String getijingyingshen = upi.getEzs_store().getIdCardNum();
			String qiyedaimazheng = upi.getEzs_store().getUnifyCode();
			mv.put("signMemId", upi.getEzs_store().getNumber());
			mv.put("orderid", order_no);
			mv.put("callBackUrl", callbackurl);
			mv.put("regid", 6);// (企业类型)5为个人 6为 个体和 公司
			mv.put("company", company);
			// 经营者姓名
			mv.put("getijingyinguser", getijingyinguser);
			// 经营者身份证号
			mv.put("getijingyingshen", getijingyingshen);
			// 营业执照注册号
			mv.put("getizhuhao", getizhuhao);
			// 营业执照注册号
			mv.put("qiyereh", qiyereh);
			// 统一社会信用代码
			mv.put("qiyedaimazheng", qiyedaimazheng);
			mv.put("accountType", accountType);// 1.企业账号，2.个体户

			String url = signbase+"/website/certSign/forh5/signContractFast.do";
			HttpRequestParam httpParam = new HttpRequestParam();
			for (Entry<String, Object> en : mv.entrySet()) {
				httpParam.addUrlParams(new BasicNameValuePair(en.getKey(), String.valueOf(en.getValue())));
			}
			JSONObject callBackRet = null;
			callBackRet = HttpRemoteRequestUtils.doPost(url, httpParam);
			com.sanbang.vo.sign.Result  res=(com.sanbang.vo.sign.Result) JSONObject.toBean(callBackRet,com.sanbang.vo.sign.Result.class);
			if (res.isSuccess()) {
				mv.clear();
				mv.put("pdfurl", res.getContent());
				result.setSuccess(true);
				result.setMsg("请求成功 ");
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				result.setObj(mv);
			} else {
				result.setSuccess(false);
				result.setMsg("合同确认中，请稍后。如有疑问，咨询400-6666-890");
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);

			}
			log.info("h5请求签章返回："+callBackRet);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}

		return result;
	}

	@Override
	public Result sampleDelivery(Result result, String order_no, HttpServletRequest request, HttpServletResponse response) {
		
		String logistics_name = request.getParameter("logistics_name");//运输公司
		String logistics_no = request.getParameter("logistics_no");//物流单号or运单号？
		String proples = request.getParameter("proples");//司机姓名
		String phone = request.getParameter("phone");//司机电话
		String car_no = request.getParameter("car_no");//车牌号
		String service_time = request.getParameter("service_time");//预计送达时间
		
		ezs_logistics logistics = new ezs_logistics();
		//根据order_no 获取相应采购订单
		ezs_purchase_orderform purOrder = purchaseOrderformMapper.selectByOrderNo(order_no);
		Long orderId = purOrder.getId();

		logistics.setAddTime(new Date());
		logistics.setDeleteStatus(false);
		logistics.setCar_no(car_no);
		logistics.setLogistics_name(logistics_name);
		logistics.setLogistics_no(logistics_no);
		logistics.setPhone(phone);
		logistics.setProples(proples);
		if(null != service_time && !"".equals(service_time)){
			try {
				logistics.setService_time(sdf.parse(service_time));
			} catch (ParseException e) {
				result.setSuccess(false);
				result.setMsg("预计送达时间格式错误");
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				e.printStackTrace();
			}
		}
		logistics.setStatus(0);
		logistics.setOrder_no(order_no);
		int aa = 0;
		try {
			aa = ezs_logisticsMapper.insertSelective(logistics);
			if(aa <= 0){
				result.setSuccess(false);
				result.setMsg("参数错误");
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			}else{
				//更改采购订单发货状态并存库
				purOrder.setOrder_status(70);
				purchaseOrderformMapper.updateByPrimaryKeySelective(purOrder);
				
				result.setSuccess(true);
				result.setMsg("样品订单发货 ");
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return result;
	}

	@Override
	public Result goodsDelivery(Result result, String order_no, HttpServletRequest request,
			HttpServletResponse response) {
		//根据order_no 获取相应采购订单
		ezs_purchase_orderform purOrder = purchaseOrderformMapper.selectByOrderNo(order_no);
		Long orderId = purOrder.getId();
		//更改采购订单发货状态并存库
		purOrder.setOrder_status(70);
		int aa = 0;
		
		try {
			aa = purchaseOrderformMapper.updateByPrimaryKeySelective(purOrder);
			if(aa <= 0){
				result.setSuccess(false);
				result.setMsg("参数错误");
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			}else{
				result.setSuccess(true);
				result.setMsg("货品订单发货 ");
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		
		return result;
	}
	
}
