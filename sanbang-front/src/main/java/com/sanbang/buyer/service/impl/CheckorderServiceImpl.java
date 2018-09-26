package com.sanbang.buyer.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_check_order_items;
import com.sanbang.bean.ezs_check_order_main;
import com.sanbang.bean.ezs_checkm_photo;
import com.sanbang.bean.ezs_order_info;
import com.sanbang.bean.ezs_orderform;
import com.sanbang.bean.ezs_payinfo;
import com.sanbang.bean.ezs_purchase_orderform;
import com.sanbang.bean.ezs_user;
import com.sanbang.buyer.service.CheckOrderService;
import com.sanbang.dao.ezs_accessoryMapper;
import com.sanbang.dao.ezs_check_order_itemsMapper;
import com.sanbang.dao.ezs_check_order_mainMapper;
import com.sanbang.dao.ezs_checkm_photoMapper;
import com.sanbang.dao.ezs_goodscartMapper;
import com.sanbang.dao.ezs_orderformMapper;
import com.sanbang.dao.ezs_payinfoMapper;
import com.sanbang.dao.ezs_purchase_orderformMapper;
import com.sanbang.utils.FilePathUtil;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.GoodsCarInfo;
import com.sanbang.vo.userauth.AuthImageVo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 对账单操作
 * 
 * @author langf
 * @data 2018-9-17
 *
 */
@Service("checkOrderService")
public class CheckorderServiceImpl implements CheckOrderService {

	private Logger log = Logger.getLogger(CheckorderServiceImpl.class);

	private static String cata = "商品名称,吨袋扣款,吨袋扣重,其他费用";
	private static String cata_validata = "商品名称,吨袋扣款,吨袋扣重";

	@Autowired
	private ezs_check_order_mainMapper ezs_check_order_mainMapper;
	@Autowired
	private ezs_check_order_itemsMapper ezs_check_order_itemsMapper;

	@Autowired
	private ezs_orderformMapper ezs_orderformMapper;

	@Autowired
	private ezs_purchase_orderformMapper purchaseOrderformMapper;

	@Autowired
	private ezs_checkm_photoMapper ezs_checkm_photoMapper;

	@Autowired
	private ezs_accessoryMapper ezs_accessoryMapper;

	@Autowired
	private ezs_payinfoMapper ezs_payinfoMapper;
	
	@Autowired
	private ezs_goodscartMapper  ezs_goodscartMapper;

	private SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");

	@Override
	public Result getCheckOrderH(HttpServletRequest request, Result result, ezs_user upi, String orderNo)
			throws Exception {

		Map<String, Object> map = new HashMap<>();
		Map<String, Object> res = new HashMap<>();
		List<String> imglist = new ArrayList<>();

		BigDecimal cnum = new BigDecimal(0);
		BigDecimal cprice = new BigDecimal(0);

		List<Map<String, Object>> listitem = new ArrayList<>();
		List<Map<String, Object>> listitemp = new ArrayList<>();
		ezs_check_order_main checkorder = new ezs_check_order_main();
		if (Tools.isEmpty(orderNo)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setMsg("订单号不能为空");
			result.setSuccess(false);
		} else {

			ezs_order_info orderinfo = ezs_orderformMapper.getOrderListByOrderno(orderNo, upi.getId());
			if (null == orderinfo) {
				orderinfo = purchaseOrderformMapper.getOrderListByOrderno(orderNo);
			}

			if (null == orderinfo) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("订单不存在");
				return result;
			}

			checkorder = ezs_check_order_mainMapper.getCheckOrderForOrderNO(orderNo);
			if (null != checkorder) {
				map.put("order_no", checkorder.getOrder_no());// 订单编号
				map.put("memo", checkorder.getMemo());// 备注
				map.put("linkphone", checkorder.getLinkphone());// 联系电话
				map.put("username", checkorder.getUsername());// 联系人
				map.put("lastModifyDate", smp.format(checkorder.getLastModifyDate()));
				List<ezs_checkm_photo> acclist = checkorder.getAcclist();
				for (ezs_checkm_photo ezs_checkm_photo : acclist) {
					imglist.add(ezs_checkm_photo.getAccessory().getPath());
				}
				List<ezs_check_order_items> items = checkorder.getItems();
				int i = 0;
				for (ezs_check_order_items item : items) {
					Map<String, Object> chace = new HashMap<>();
					chace.put("item_name", item.getItem_name());// 产品名称
					chace.put("deliveryDate", smp.format(item.getDeliveryDate()));
					chace.put("flag", "0");// 是否参与计算标记，0：参与计算；1：不参与计算，默认参与计算
					chace.put("item_count", item.getItem_count());// 数量
					chace.put("item_price", item.getItem_price());// 单价
					chace.put("item_totalmoney", item.getItem_totalmoney());//// 总额
					chace.put("detail", item.getDetail());// 明细
					chace.put("id", item.getId());
					listitem.add(chace);
					if ("产品名称".equals(item.getItem_name())) {
						cnum = cnum.add(item.getItem_count());
					}
					if ("吨袋扣重".equals(item.getItem_name())) {
						cnum = cnum.subtract(item.getItem_count());
					}
					cprice = cprice.add(item.getItem_totalmoney());
					i++;
					if (i == 4) {
						Map<String, Object> chace1 = new HashMap<>();
						chace1.put("item", listitem);
						listitemp.add(chace1);
						i = 0;
						listitem = new ArrayList<>();
					}
				}
				map.put("cnum", cnum);// 联系人
				map.put("cprice", cprice);

				map.put("imglist", imglist);
				map.put("items", listitemp);
				res.put("checkorder", map);
				res.put("hashdata", true);
			} else {
				map.put("imglist", imglist);
				map.put("items", listitem);
				res.put("checkorder", map);
				res.put("hashdata", false);

			}

		}
		result.setSuccess(true);
		result.setMsg("请求成功");
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setObj(res);

		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 3000)
	public Result addOrUpdateCheckOrder(HttpServletRequest request, ezs_user upi, Result result) throws Exception {

		List<ezs_check_order_items> list = new ArrayList<>();
		ezs_check_order_main checkorder = new ezs_check_order_main();
		BigDecimal Imblance_money = new BigDecimal(0);

		String items = request.getParameter("items");
		String orderno = request.getParameter("orderno");
		String issave = request.getParameter("issave");// 是否提交
		String memo = request.getParameter("memo");
		String username = request.getParameter("username");// 联系人
		String linkphone = request.getParameter("linkphone");// 联系电话
		// String usermemo = request.getParameter("usermemo");// 卖家备注

		String urlParam = request.getParameter("urlParam");

		if (Tools.isEmpty(orderno)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setMsg("订单号不能为空");
			result.setSuccess(false);
			return result;
		}

		ezs_order_info orderinfo = ezs_orderformMapper.getOrderListByOrderno(orderno, upi.getId());
		if (null == orderinfo) {
			orderinfo = purchaseOrderformMapper.getOrderListByOrderno(orderno);
		}

		if (null == orderinfo) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("订单不存在");
			return result;
		}
		checkorder = ezs_check_order_mainMapper.getCheckOrderForOrderNO(orderno);
		if (checkorder == null) {
			checkorder = new ezs_check_order_main();
			checkorder.setAddTime(new Date());
			checkorder.setDeleteStatus(false);
			checkorder.setImblance_money(new BigDecimal(0));
			checkorder.setLastModifyDate(new Date());
			checkorder.setMemo(memo);
			checkorder.setOrder_money(orderinfo.getPrice());
			checkorder.setOrder_no(orderno);
			checkorder.setUsername(username);
			// checkorder.setUsermemo(usermemo);
			checkorder.setLinkphone(linkphone);
		}

		if (Tools.isEmpty(issave)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setMsg("请求类型参数不正确");
			result.setSuccess(false);
			return result;
		}

		JSONArray array = JSONArray.fromObject(items);
		System.out.println(array.size());
		System.out.println(array.size() % 4);
		if (null == array || (array.size() % 4 != 0)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setMsg("列表数据拼接格式错误");
			result.setSuccess(false);
			return result;
		}

		for (Object object : array) {
			JSONObject json = JSONObject.fromObject(object);
			ezs_check_order_items item = new ezs_check_order_items();
			long id = json.get("id") == null ? 0 : json.getInt("id");// id
			String item_name = json.getString("item_name");// 标题
			double item_count = json.getDouble("item_count");// 数量
			double item_price = json.getDouble("item_price");// 金额
			String detail = json.getString("detail");// 金额
			String deliveryDate = json.getString("deliveryDate");// 时间

			if (Tools.isEmpty(item_name)) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("标题不能为空");
				result.setSuccess(false);
				return result;
			}

			// 总金额
			BigDecimal item_totalmoney = new BigDecimal(item_count).multiply(new BigDecimal(item_price));

			// 是否为保存
			item.setFlag(issave);
			item.setId(id);
			item.setItem_totalmoney(item_totalmoney);
			item.setItem_count(new BigDecimal(item_count));
			item.setItem_price(new BigDecimal(item_price));

			item.setAddTime(new Date());
			item.setLastModifyDate(new Date());
			item.setCheckOrderMain_id(checkorder.getId());
			item.setDeleteStatus(false);
			item.setDeliveryDate(smp.parse(deliveryDate));
			item.setItem_name(item_name);
			item.setDetail(detail);// 明细
			Imblance_money = Imblance_money.add(item_totalmoney);
			list.add(item);
		}

		List<AuthImageVo> listimg = new ArrayList<>();
		savepic(urlParam, listimg);
		if (null == listimg || listimg.size() == 0) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请上传图片");
			return result;
		}

		// 票据记录
		AuthImageVo vo = listimg.get(0);

		if (!vo.getImgcode().equals("RECIMG")) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("标识错误");
			return result;
		}

		// checkorder.setImblance_money(Imblance_money);
		String bz = "货款已付" + checkorder.getImblance_money() + "元,"
				+ (Imblance_money.compareTo(new BigDecimal(0)) > 0 ? "应付" : "应退") + Imblance_money + "元";
		checkorder.setMemo(bz);
		if (null == checkorder.getId()) {
			ezs_check_order_mainMapper.insertSelective(checkorder);
		} else {
			ezs_check_order_mainMapper.updateByPrimaryKeySelective(checkorder);
		}

		for (ezs_check_order_items item : list) {
			if (item.getId() > 0) {
				ezs_check_order_itemsMapper.updateByPrimaryKeySelective(item);
			} else {
				ezs_check_order_itemsMapper.insertSelective(item);
			}

		}

		// 删除原有图片记录
		ezs_checkm_photoMapper.deleteByPrimaryKey(checkorder.getId());

		for (AuthImageVo img : listimg) {
			ezs_accessory ezs_accessory = new ezs_accessory();
			ezs_accessory.setAddTime(new Date());
			ezs_accessory.setDeleteStatus(false);
			ezs_accessory.setExt("");
			ezs_accessory.setHeight(0);
			ezs_accessory.setInfo(null);
			ezs_accessory.setName("");
			ezs_accessory.setName(FilePathUtil.getimageName(img.getImgurl()));
			ezs_accessory.setPath(FilePathUtil.getmiddelPath(img.getImgurl()));
			ezs_accessory.setSize((float) 100);
			ezs_accessory.setWidth(100);
			ezs_accessory.setUser_id(upi.getId());
			ezs_accessoryMapper.insertSelective(ezs_accessory);
			// upi记录
			img.setAccid(ezs_accessory.getId());

			ezs_checkm_photo checkm = new ezs_checkm_photo();
			checkm.setAcid(img.getAccid());
			checkm.setCkmid(checkorder.getId());
			ezs_checkm_photoMapper.insert(checkm);
		}
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("保存成功");
		result.setSuccess(true);

		return result;
	}

	private static List<AuthImageVo> savepic(String param, List<AuthImageVo> list) throws ParseException {
		if (!Tools.isEmpty(param)) {
			String[] aa = param.split(";");
			if (null == aa || aa.length == 0) {
				return list;
			}
			for (String bb : aa) {
				String[] cc = bb.split(",");
				if (null == cc || cc.length < 2) {
					return list;
				}
				AuthImageVo ImageVo = new AuthImageVo();
				ImageVo.setImgcode(cc[0]);

				if (cc[1].indexOf("@") > 0 && cc[1].split("@").length == 3) {
					ImageVo.setImgurl(cc[1].split("@")[0]);
					ImageVo.setName(cc[1].split("@")[1]);
					ImageVo.setUsetime(sdf.parse(cc[1].split("@")[2]));
					list.add(ImageVo);
					System.out.println(ImageVo.toString());
				} else {
					ImageVo.setImgurl(cc[1].split("@")[0]);
					list.add(ImageVo);
					System.out.println(ImageVo.toString());
				}
			}
		}
		return list;
	}

	@Override
	public Result getCheckOrderInit(HttpServletRequest request, ezs_user upi, Result result) throws Exception {
		try {
			String[] ac = cata.split(",");
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> res = new HashMap<>();
			List<Map<String, Object>> listitem = new ArrayList<>();
			for (String string : ac) {
				Map<String, Object> chace = new HashMap<>();
				chace.put("id", 0);// 产品名称
				chace.put("item_name", string);// 产品名称
				chace.put("deliveryDate", smp.format(new Date()));
				chace.put("flag", "0");// 是否参与计算标记，0：参与计算；1：不参与计算，默认参与计算
				chace.put("item_count", new BigDecimal(0));// 数量
				chace.put("item_price", new BigDecimal(0));// 单价
				chace.put("item_totalmoney", new BigDecimal(0));//// 总额
				chace.put("detail", "");
				listitem.add(chace);
			}
			map.put("items", listitem);
			result.setSuccess(true);
			result.setMsg("请求成功");
			result.setObj(map);
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}

		return result;
	}

	public static void main(String[] args) {
		BigDecimal a = new BigDecimal(1);
		BigDecimal b = new BigDecimal(-1);
		System.out.println(a.add(b));
	}

	/**
	 * 上传支付凭证
	 */
	// PAYIMG
	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Result orderpaysubmitForNow(HttpServletRequest request, String order_no, String urlParam, ezs_user upi) {

		Result result = Result.failure();

		try {

			ezs_order_info orderinfo = ezs_orderformMapper.getOrderListByOrderno(order_no, upi.getId());
			if (orderinfo == null) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("订单不存在");
				return result;
			}

			List<AuthImageVo> list = new ArrayList<>();
			savepic(urlParam, list);
			if (null == list || list.size() == 0) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("请上传图片");
				return result;
			}

			if (list.size() > 1) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("只能上传一张图片");
				return result;
			}

			// 票据记录
			AuthImageVo vo = list.get(0);

			if (!vo.getImgcode().equals("PAYIMG")) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("标识错误");
				return result;
			}

			// 判断是否在支付状态 买家代付款
			if (orderinfo.getOrder_status() == 201) {
				// 票据记录
				ezs_payinfo payinfo = new ezs_payinfo();
				payinfo.setAddTime(new Date());
				payinfo.setPaymentUser_id(upi.getId());
				payinfo.setPrice(orderinfo.getFirst_price());
				payinfo.setDeleteStatus(false);
				payinfo.setOrder_no(order_no);
				// 1.采购单，2.订单
				payinfo.setOrder_type(2);
				payinfo.setPay_no(Tools.getH5PayNO());// 流水号
				payinfo.setBill_id(vo.getAccid());// 票据id
				payinfo.setPaymentUser_id(orderinfo.getBuyerid());// 支付人id
				payinfo.setReceUser_id(orderinfo.getSellerid());// 收款人id
				payinfo.setPay_mode(1);
				ezs_payinfoMapper.insert(payinfo);
				// 卖家代付款
			} else if (orderinfo.getOrder_status() == 205) {
				// 票据记录
				ezs_payinfo payinfo = new ezs_payinfo();
				payinfo.setAddTime(new Date());
				payinfo.setPaymentUser_id(upi.getId());
				payinfo.setPrice(orderinfo.getFirst_price());
				payinfo.setDeleteStatus(false);
				payinfo.setOrder_no(order_no);
				// 1.采购单，2.订单
				payinfo.setOrder_type(2);
				payinfo.setPay_no(Tools.getH5PayNO());// 流水号
				payinfo.setBill_id(vo.getAccid());// 票据id
				payinfo.setPaymentUser_id(orderinfo.getSellerid());// 支付人id
				payinfo.setReceUser_id(orderinfo.getBuyerid());// 收款人id
				payinfo.setPay_mode(1);
				ezs_payinfoMapper.insert(payinfo);
			} else {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("不在支付状态");
				return result;
			}

			// 添加图片记录
			ezs_accessory ezs_accessory = new com.sanbang.bean.ezs_accessory();
			ezs_accessory.setAddTime(new Date());
			ezs_accessory.setDeleteStatus(false);
			ezs_accessory.setExt("");
			ezs_accessory.setHeight(0);
			ezs_accessory.setInfo(null);
			ezs_accessory.setName(FilePathUtil.getimageName(vo.getImgurl()));
			ezs_accessory.setPath(FilePathUtil.getmiddelPath(vo.getImgurl()));
			ezs_accessory.setSize((float) 100);
			ezs_accessory.setWidth(100);
			ezs_accessory.setUser_id(upi.getId());
			ezs_accessoryMapper.insertSelective(ezs_accessory);
			// upi记录
			vo.setAccid(ezs_accessory.getId());

			ezs_orderform orde = ezs_orderformMapper.selectByorderno(order_no);
			ezs_orderform order = new ezs_orderform();
			order.setOrder_no(orderinfo.getOrder_no());
			order.setOrder_status(orderinfo.getOrder_status());
			order.setId(orde.getId());
			ezs_orderformMapper.updateByPrimaryKeySelective(order);
			result.setSuccess(true);
			result.setMsg("上传成功");
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			// result.setObj(payinfo);//方便修改
			return result;
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			e.printStackTrace();
		}
		return result;
	}

	@Transactional(rollbackFor=Exception.class,isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	@Async
	private Result checkStatisPriceForBuyer(String orderno,ezs_user upi,Result result) {

		ezs_orderform ezs_orderform=new ezs_orderform();
		ezs_purchase_orderform purchaseOrder=new ezs_purchase_orderform();
		
		ezs_orderform=ezs_orderformMapper.selectByorderno(orderno);
		if(null==ezs_orderform) {
			purchaseOrder=purchaseOrderformMapper.selectByOrderNo(orderno);
			
		}else {
			
		}
		
		if (null == orderinfo) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("订单不存在");
			return result;
		}
		
		ezs_check_order_main checkorder = ezs_check_order_mainMapper.getCheckOrderForOrderNO(orderno);
		if (checkorder == null) {
			result.setSuccess(true);
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setMsg("计算成功");
			return result;
		}
		
		List<ezs_check_order_items> items=checkorder.getItems();
		if(items==null) {
			result.setSuccess(true);
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setMsg("计算成功");
			return result;
		}
		BigDecimal Imblance_money=new BigDecimal("0");
		for (ezs_check_order_items item : items) {
			BigDecimal item_totalmoney=item.getItem_totalmoney();
			// 总金额
			Imblance_money = Imblance_money.add(item_totalmoney);
		}
		
		Map<String, BigDecimal> price=ezs_payinfoMapper.getOrderPayInfoForUser(upi.getId(), orderno);
		BigDecimal income=price.get("income");//已支付
		BigDecimal spending=price.get("spending");//已收到
		
		String bz = "货款已付" + income + "元,"
				+ ((Imblance_money.subtract(income)).compareTo(new BigDecimal(0))>0?"应付/应退"+Imblance_money.subtract(income)+"元":"应付/应退"+income.subtract(Imblance_money)+"元");
		checkorder.setMemo(bz);
		checkorder.setImblance_money(Imblance_money.subtract(income));
		if (null == checkorder.getId()) {
			ezs_check_order_mainMapper.insertSelective(checkorder);
		} else {
			ezs_check_order_mainMapper.updateByPrimaryKeySelective(checkorder);
		}
		
		ezs_orderform ezs_orderform=new ezs_orderform();
		ezs_orderform.setId(ezs_orderform.getId());
		
		ezs_purchase_orderform  ezs_purchase_orderform=new ezs_purchase_orderform();
		ezs_purchase_orderform.setId(ezs_orderform.getId());
		
		if() {
		}
		if() {
			ezs_orderform.setOrder_status(210);
		}else if() {
			ezs_orderform.setOrder_status(210);
		}else {
			ezs_orderform.setOrder_status(210);
		}
		
		ezs_orderformMapper.updateByPrimaryKey(ezs_orderform);
		
		
		result.setSuccess(true);
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("计算成功");
		return result;
	}  
	
	
}
