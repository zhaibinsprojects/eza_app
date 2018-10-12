package com.sanbang.buyer.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_area;
import com.sanbang.bean.ezs_bill;
import com.sanbang.bean.ezs_check_order_items;
import com.sanbang.bean.ezs_check_order_main;
import com.sanbang.bean.ezs_checkm_photo;
import com.sanbang.bean.ezs_goodscart;
import com.sanbang.bean.ezs_orderform;
import com.sanbang.bean.ezs_pact;
import com.sanbang.bean.ezs_payinfo;
import com.sanbang.bean.ezs_purchase_orderform;
import com.sanbang.bean.ezs_quality;
import com.sanbang.bean.ezs_quality_detail;
import com.sanbang.bean.ezs_user;
import com.sanbang.buyer.service.CheckOrderService;
import com.sanbang.dao.ezs_accessoryMapper;
import com.sanbang.dao.ezs_billMapper;
import com.sanbang.dao.ezs_check_order_itemsMapper;
import com.sanbang.dao.ezs_check_order_mainMapper;
import com.sanbang.dao.ezs_checkm_photoMapper;
import com.sanbang.dao.ezs_goodscartMapper;
import com.sanbang.dao.ezs_orderformMapper;
import com.sanbang.dao.ezs_pactMapper;
import com.sanbang.dao.ezs_payinfoMapper;
import com.sanbang.dao.ezs_purchase_orderformMapper;
import com.sanbang.dao.ezs_qualityMapper;
import com.sanbang.dao.ezs_userMapper;
import com.sanbang.goods.service.GoodsService;
import com.sanbang.utils.FilePathUtil;
import com.sanbang.utils.NumberToCN;
import com.sanbang.utils.ProcessHtmlUtil;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.utils.httpclient.HttpRemoteRequestUtils;
import com.sanbang.utils.httpclient.HttpRequestParam;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.goods.GoodsVo;
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

	@Value("${config.sign.buyercallbackurl}")
	private String callbackurl;

	@Value("${config.sign.baseurl}")
	private String signbase;

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
	private ezs_goodscartMapper ezs_goodscartMapper;

	@Autowired
	private ezs_pactMapper ezs_pactMapper;

	@Autowired
	private ezs_billMapper ezs_billMapper;

	@Autowired
	private ezs_userMapper ezs_userMapper;

	@Autowired
	private com.sanbang.dao.ezs_areaMapper ezs_areaMapper;

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private ezs_qualityMapper ezs_qualityMapper;

	private SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd");

	// 上下文地址
	@Value("${consparam.imgs.baseurl}")
	public String BASEURL;

	// 临时路径
	@Value("${consparam.imgs.tempsavepath}")
	public String TEMPSAVEPATH;

	// 临时路径标识
	@Value("${consparam.imgs.tempathflag}")
	public String TEMPATHFLAG;

	//简易合同模板查看
	private static String TEMPNAME = "xiaoshou5.ftl";

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");

	@Override
	public Result getCheckOrderH(HttpServletRequest request, Result result, ezs_user upi, String orderNo)
			throws Exception {

		Map<String, Object> map = new HashMap<>();
		Map<String, Object> res = new HashMap<>();
		List<String> imglist = new ArrayList<>();

		BigDecimal cnum = new BigDecimal(0);
		BigDecimal cprice = new BigDecimal(0);
		BigDecimal only_price=new BigDecimal(0);

		List<Map<String, Object>> listitem = new ArrayList<>();
		List<Map<String, Object>> listitemp = new ArrayList<>();
		ezs_check_order_main checkorder = new ezs_check_order_main();
		if (Tools.isEmpty(orderNo)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setMsg("订单号不能为空");
			result.setSuccess(false);
		} else {

			ezs_goodscart cart = new ezs_goodscart();
			ezs_orderform ezs_orderform = new ezs_orderform();
			ezs_purchase_orderform purchaseOrder = new ezs_purchase_orderform();
			ezs_orderform = ezs_orderformMapper.selectByorderno(orderNo);
			if (null == ezs_orderform) {
				purchaseOrder = purchaseOrderformMapper.selectByOrderNo(orderNo);
				if (null != purchaseOrder) {
					cart = ezs_goodscartMapper.selectGoodsCartByOfidOrPofid(0, purchaseOrder.getId());
					if (null != cart) {
						ezs_orderform = ezs_orderformMapper.selectByPrimaryKey(cart.getOf_id());
					} else {
						result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
						result.setSuccess(false);
						result.setMsg("订单状态异常");
						return result;
					}
				} else {
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("订单不存在");
					return result;
				}

			} else {
				cart = ezs_goodscartMapper.selectGoodsCartByOfidOrPofid(ezs_orderform.getId(), 0);
				if (null != cart) {
					purchaseOrder = purchaseOrderformMapper.selectByPrimaryKey(cart.getPof_id());
				} else {
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("订单状态异常");
					return result;
				}
			}

			
			GoodsVo good=goodsService.getgoodsinfo(purchaseOrder.getGoodsId(), 0);
			if(null==good) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("订单状态异常");
				return result;
			}
			checkorder = ezs_check_order_mainMapper.getCheckOrderForOrderNO(ezs_orderform.getOrder_no());
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
					if(only_price.compareTo(new BigDecimal("0"))==0) {
						only_price=item.getItem_price();
					}
					Map<String, Object> chace = new HashMap<>();
					chace.put("item_name", item.getItem_name());// 产品名称
					chace.put("deliveryDate", item.getDeliveryDate()==null?"":smp.format(item.getDeliveryDate()));
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
				map.put("name", good.getName());// 联系人
				map.put("cnum", cnum);// 联系人
				map.put("cprice", cprice);
				map.put("only_price", only_price);
				map.put("imglist", imglist);
				map.put("items", listitemp);
				res.put("checkorder", map);
				res.put("hashdata", true);
			} else {
				map.put("name", good.getName());// 联系人
				map.put("cnum", cnum);// 联系人
				map.put("cprice", cprice);
				map.put("only_price", cart.getPrice());
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
		//固定商品单价
		String onlyprice=request.getParameter("only_price");
		
		if(StringUtils.isEmpty(onlyprice)||StringUtils.isBlank(onlyprice)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setMsg("商品单价不能为空");
			result.setSuccess(false);
			return result;
		}
		
		List<ezs_check_order_items> list = new ArrayList<>();
		ezs_check_order_main checkorder = new ezs_check_order_main();
		BigDecimal Imblance_money = new BigDecimal(0);
		/*
		 * Enumeration<String> pNames = request.getParameterNames(); while
		 * (pNames.hasMoreElements()) { System.out.println(pNames.nextElement()); } //
		 * 4.获取所有请求参数 Map<String, String[]> map= request.getParameterMap();
		 * Set<Entry<String, String[]>> set=map.entrySet();
		 * Iterator<Entry<String,String[]>> iter=set.iterator(); while(iter.hasNext()) {
		 * Entry<String, String[]> entry=iter.next();
		 * System.out.println(entry.getKey()+"=="+Arrays.toString(entry.getValue())); }
		 */

		String items = request.getParameter("items");
		String orderno = request.getParameter("orderno");
		String issave = request.getParameter("issave");// 是否提交
		String memo = request.getParameter("memo");
		String username = request.getParameter("username");// 联系人
		String linkphone = request.getParameter("linkphone");// 联系电话
		log.info("添加修改对账单=======items=======" + items);
		log.info("=======username=======" + username);
		log.info("======linkphone========" + linkphone);

		String urlParam = request.getParameter("urlParam");

		ezs_goodscart cart = new ezs_goodscart();
		ezs_orderform ezs_orderform = new ezs_orderform();
		ezs_purchase_orderform purchaseOrder = new ezs_purchase_orderform();
		ezs_orderform = ezs_orderformMapper.selectByorderno(orderno);
		if (null == ezs_orderform) {
			purchaseOrder = purchaseOrderformMapper.selectByOrderNo(orderno);
			if (null != purchaseOrder) {
				cart = ezs_goodscartMapper.selectGoodsCartByOfidOrPofid(0, purchaseOrder.getId());
				if (null != cart) {
					ezs_orderform = ezs_orderformMapper.selectByPrimaryKey(cart.getOf_id());
				} else {
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("订单状态异常");
					return result;
				}
			} else {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("订单不存在");
				return result;
			}

		} else {
			cart = ezs_goodscartMapper.selectGoodsCartByOfidOrPofid(ezs_orderform.getId(), 0);
			if (null != cart) {
				purchaseOrder = purchaseOrderformMapper.selectByPrimaryKey(cart.getPof_id());
			} else {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("订单状态异常");
				return result;
			}
		}

		// 查看 是否 有买家支付记录
		Map<String, BigDecimal> price = ezs_payinfoMapper.getOrderPayInfoForUser(purchaseOrder.getBuyUser_id(),
				ezs_orderform.getOrder_no());
		BigDecimal income = price.get("income");// 已支付
		BigDecimal spending = price.get("spending");// 已收到

		if (income.compareTo(new BigDecimal("0")) == 0) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("买家首次未完成首次订单支付,请稍后再进行对账单编辑！");
			return result;
		}

		checkorder = ezs_check_order_mainMapper.getCheckOrderForOrderNO(ezs_orderform.getOrder_no());
		if (checkorder == null) {
			checkorder = new ezs_check_order_main();
			checkorder.setAddTime(new Date());
			checkorder.setDeleteStatus(false);
			checkorder.setImblance_money(new BigDecimal(0));
			checkorder.setLastModifyDate(new Date());
			checkorder.setMemo(memo);
			checkorder.setOrder_money(ezs_orderform.getTotal_price());
			checkorder.setOrder_no(ezs_orderform.getOrder_no());
			checkorder.setUsername(username);
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
			double item_price = Double.valueOf(onlyprice);//json.getDouble("item_price");// 金额
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

			item_totalmoney = item_totalmoney.setScale(2, BigDecimal.ROUND_HALF_DOWN);
			// 是否为保存
			item.setFlag(issave);
			item.setId(id);

			item.setItem_count(new BigDecimal(item_count));
			item.setItem_price(new BigDecimal(item_price));
			if (item_name.equals("其他费用")) {
				item.setItem_totalmoney(new BigDecimal(item_price));
				item_totalmoney = new BigDecimal(item_price);
			} else {
				item.setItem_totalmoney(item_totalmoney);
			}

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
		Collections.sort(list);

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

		int status = Imblance_money.compareTo(income.subtract(spending));
		Imblance_money = Imblance_money.subtract(income).add(spending);
		String bz = "货款已付" + income + "元,收到退款" + spending + "元。实际应付" + income.subtract(spending).add(Imblance_money)
				+ "元" + (status > 0 ? "当前应付" + Imblance_money + "元"
						: "应收" + new BigDecimal("0").subtract(Imblance_money) + "元");

		log.info(bz);
		if ("1".equals(issave) && status > 0) {
			ezs_orderform.setOrder_status(200);
			purchaseOrder.setOrder_status(200);
		} else if (status < 0) {
			ezs_orderform.setOrder_status(205);
			purchaseOrder.setOrder_status(205);
		} else {
			ezs_orderform.setOrder_status(93);
			purchaseOrder.setOrder_status(70);
		}
		ezs_orderformMapper.updateByPrimaryKeySelective(ezs_orderform);
		purchaseOrderformMapper.updateByPrimaryKeySelective(purchaseOrder);

		checkorder.setMemo(bz);
		checkorder.setUsername(username);
		checkorder.setLinkphone(linkphone);
		checkorder.setImblance_money(Imblance_money);
		if (null == checkorder.getId()) {
			ezs_check_order_mainMapper.insertSelective(checkorder);
		} else {
			ezs_check_order_mainMapper.updateByPrimaryKeySelective(checkorder);
		}

		List<Long> dels = new ArrayList<>();

		for (ezs_check_order_items item : list) {
			item.setCheckOrderMain_id(checkorder.getId());
			if (item.getId() > 0) {
				ezs_check_order_itemsMapper.updateByPrimaryKeySelective(item);
			} else {
				ezs_check_order_itemsMapper.insertSelective(item);
			}
			dels.add(item.getId());
		}
		String delids = dels.toString().substring(1, dels.toString().length() - 1);
		ezs_check_order_itemsMapper.deleteByDelids(delids, checkorder.getId());
		log.info("添加修改对账单=========" + dels.toString().substring(1, dels.toString().length() - 1));
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
	public Result getCheckOrderInit(HttpServletRequest request, ezs_user upi, Result result, String orderno)
			throws Exception {
		try {
			// 查订单表
			ezs_goodscart cart = new ezs_goodscart();
			ezs_orderform ezs_orderform = new ezs_orderform();
			ezs_purchase_orderform purchaseOrder = new ezs_purchase_orderform();
			ezs_orderform = ezs_orderformMapper.selectByorderno(orderno);
			if (null == ezs_orderform) {
				purchaseOrder = purchaseOrderformMapper.selectByOrderNo(orderno);
				if (null != purchaseOrder) {
					cart = ezs_goodscartMapper.selectGoodsCartByOfidOrPofid(0, purchaseOrder.getId());
					if (null != cart) {
						ezs_orderform = ezs_orderformMapper.selectByPrimaryKey(cart.getOf_id());
					} else {
						result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
						result.setSuccess(false);
						result.setMsg("订单状态异常");
						return result;
					}
				} else {
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("订单不存在");
					return result;
				}

			} else {
				cart = ezs_goodscartMapper.selectGoodsCartByOfidOrPofid(ezs_orderform.getId(), 0);
				if (null != cart) {
					purchaseOrder = purchaseOrderformMapper.selectByPrimaryKey(cart.getPof_id());
				} else {
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("订单状态异常");
					return result;
				}
			}

			String[] ac = cata.split(",");
			Map<String, Object> map = new HashMap<>();
			List<Map<String, Object>> listitem = new ArrayList<>();
			for (String string : ac) {
				Map<String, Object> chace = new HashMap<>();
				chace.put("id", 0);// 产品名称
				chace.put("item_name", string);// 产品名称
				chace.put("deliveryDate", smp.format(new Date()));
				chace.put("flag", "0");// 是否参与计算标记，0：参与计算；1：不参与计算，默认参与计算
				switch (string) {
				case "商品名称":
					chace.put("item_price", new BigDecimal(String.valueOf(cart.getPrice())));// 单价
					chace.put("item_count", new BigDecimal(cart.getCount()));// 数量
					break;
				case "吨袋扣款":
					chace.put("item_price", new BigDecimal("0"));// 单价
					chace.put("item_count", new BigDecimal("0"));// 数量
					break;
				default:
					chace.put("item_price", new BigDecimal(0));// 单价
					chace.put("item_count", new BigDecimal(0));// 数量
					break;
				}

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
//		List<Long> dels = new ArrayList<>();
//		dels.add((long) 1);
//		dels.add((long) 2);
//		dels.add((long) 3);
//		System.out.println(dels.toString().substring(1, dels.toString().length() - 1));
		String bz = "货款已付" + 480000 + "元,收到退款" + 0 + "元。实际应付"
				+ new BigDecimal("45900").subtract(new BigDecimal("0")).add(new BigDecimal("-5100")) + "元"
				+ (-1 > 0 ? "当前应付" + new BigDecimal("-5100") + "元"
						: "应收" + new BigDecimal("0").subtract(new BigDecimal("-5100")) + "元");
		System.err.println(bz);
	}

	/**
	 * 上传支付凭证
	 */
	// PAYIMG
	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Result orderpaysubmitForNow(HttpServletRequest request, String order_no, String urlParam, ezs_user upi)
			throws Exception {

		Result result = Result.failure();
		ezs_orderform ezs_orderform = new ezs_orderform();
		ezs_purchase_orderform purchaseOrder = new ezs_purchase_orderform();
		ezs_orderform = ezs_orderformMapper.selectByorderno(order_no);
		if (null == ezs_orderform) {
			purchaseOrder = purchaseOrderformMapper.selectByOrderNo(order_no);
			if (null != purchaseOrder) {
				ezs_goodscart cart = ezs_goodscartMapper.selectGoodsCartByOfidOrPofid(0, purchaseOrder.getId());
				if (null != cart) {
					ezs_orderform = ezs_orderformMapper.selectByPrimaryKey(cart.getOf_id());
				} else {
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("订单状态异常");
					return result;
				}
			} else {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("订单不存在");
				return result;
			}

		} else {
			ezs_goodscart cart = ezs_goodscartMapper.selectGoodsCartByOfidOrPofid(ezs_orderform.getId(), 0);
			if (null != cart) {
				purchaseOrder = purchaseOrderformMapper.selectByPrimaryKey(cart.getPof_id());
			} else {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("订单状态异常");
				return result;
			}
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
		// 判断是否在支付状态 买家代付款
		if (ezs_orderform.getOrder_status() == 200) {
			// 票据记录
			ezs_payinfo payinfo = new ezs_payinfo();
			payinfo.setAddTime(new Date());
			payinfo.setPaymentUser_id(upi.getId());
			payinfo.setPrice(ezs_orderform.getTotal_price());
			payinfo.setDeleteStatus(false);
			payinfo.setOrder_no(order_no);
			// 1.采购单，2.订单
			payinfo.setOrder_type(2);
			payinfo.setPay_no(Tools.getH5PayNO());// 流水号
			payinfo.setBill_id(vo.getAccid());// 票据id
			payinfo.setPaymentUser_id(purchaseOrder.getBuyUser_id());// 支付人id
			payinfo.setReceUser_id(purchaseOrder.getSellerUser_id());// 收款人id
			payinfo.setPay_mode(1);
			ezs_payinfoMapper.insert(payinfo);
			// 卖家代付款
		} else if (ezs_orderform.getOrder_status() == 205) {
			// 票据记录
			ezs_payinfo payinfo = new ezs_payinfo();
			payinfo.setAddTime(new Date());
			payinfo.setPaymentUser_id(upi.getId());
			payinfo.setPrice(ezs_orderform.getTotal_price());
			payinfo.setDeleteStatus(false);
			payinfo.setOrder_no(order_no);
			// 1.采购单，2.订单
			payinfo.setOrder_type(2);
			payinfo.setPay_no(Tools.getH5PayNO());// 流水号
			payinfo.setBill_id(vo.getAccid());// 票据id
			payinfo.setPaymentUser_id(purchaseOrder.getSellerUser_id());// 支付人id
			payinfo.setReceUser_id(purchaseOrder.getBuyUser_id());// 收款人id
			payinfo.setPay_mode(1);
			ezs_payinfoMapper.insert(payinfo);
		} else {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("不在支付状态");
			return result;
		}

		result.setSuccess(true);
		result.setMsg("上传成功");
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		// result.setObj(payinfo);//方便修改
		// 订单操作

		checkStatisPriceForBuyer(ezs_orderform, purchaseOrder, order_no, result);

		return result;
	}

	@Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	@Async
	private Result checkStatisPriceForBuyer(ezs_orderform ezs_orderform, ezs_purchase_orderform purchaseOrder,
			String orderno, Result result) {

		ezs_check_order_main checkorder = ezs_check_order_mainMapper.getCheckOrderForOrderNO(orderno);
		if (checkorder == null) {
			ezs_orderform.setOrder_status(210);
			purchaseOrder.setOrder_status(210);
			ezs_orderformMapper.updateByPrimaryKeySelective(ezs_orderform);
			purchaseOrderformMapper.updateByPrimaryKeySelective(purchaseOrder);
			result.setSuccess(true);
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setMsg("计算成功");
			return result;
		}

		List<ezs_check_order_items> items = checkorder.getItems();
		if (items == null) {
			result.setSuccess(true);
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setMsg("计算成功");
			return result;
		}
		BigDecimal Imblance_money = new BigDecimal("0");
		for (ezs_check_order_items item : items) {
			BigDecimal item_totalmoney = item.getItem_totalmoney();
			// 总金额
			Imblance_money = Imblance_money.add(item_totalmoney);
		}

		Map<String, BigDecimal> price = ezs_payinfoMapper.getOrderPayInfoForUser(ezs_orderform.getUser_id(), orderno);
		BigDecimal income = price.get("income");// 已支付
		BigDecimal spending = price.get("spending");// 已收到

		int status = Imblance_money.compareTo(income.subtract(spending));

		String bz = "货款已付" + income + "元," + (status > 0 ? "应付" + Imblance_money.subtract(income) + "元"
				: "应收" + income.subtract(Imblance_money) + "元");
		checkorder.setMemo(bz);
		checkorder.setImblance_money(Imblance_money.subtract(income));
		if (null == checkorder.getId()) {
			ezs_check_order_mainMapper.insertSelective(checkorder);
		} else {
			ezs_check_order_mainMapper.updateByPrimaryKeySelective(checkorder);
		}

		if (status > 0) {
			ezs_orderform.setOrder_status(200);
			purchaseOrder.setOrder_status(200);
		} else if (status < 0) {
			ezs_orderform.setOrder_status(205);
			purchaseOrder.setOrder_status(205);
		} else {
			ezs_orderform.setOrder_status(93);
			purchaseOrder.setOrder_status(70);
		}
		ezs_orderformMapper.updateByPrimaryKeySelective(ezs_orderform);
		purchaseOrderformMapper.updateByPrimaryKeySelective(purchaseOrder);

		result.setSuccess(true);
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("计算成功");
		return result;
	}

	@Override
	public Result signContentProcess(Result result, String orderno) {
		try {

			// 先查一下合同的pdf在不在
			List<ezs_pact> pacts = ezs_pactMapper.selectPactByOrderNo(orderno);
			ezs_pact pactnow = new ezs_pact();
			ezs_goodscart cart = new ezs_goodscart();
			// 查订单表
			ezs_orderform ezs_orderform = new ezs_orderform();
			ezs_purchase_orderform purchaseOrder = new ezs_purchase_orderform();
			ezs_orderform = ezs_orderformMapper.selectByorderno(orderno);
			if (null == ezs_orderform) {
				purchaseOrder = purchaseOrderformMapper.selectByOrderNo(orderno);
				if (null != purchaseOrder) {
					cart = ezs_goodscartMapper.selectGoodsCartByOfidOrPofid(0, purchaseOrder.getId());
					if (null != cart) {
						ezs_orderform = ezs_orderformMapper.selectByPrimaryKey(cart.getOf_id());
					} else {
						result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
						result.setSuccess(false);
						result.setMsg("订单状态异常");
						return result;
					}
				} else {
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("订单不存在");
					return result;
				}

			} else {
				cart = ezs_goodscartMapper.selectGoodsCartByOfidOrPofid(ezs_orderform.getId(), 0);
				if (null != cart) {
					purchaseOrder = purchaseOrderformMapper.selectByPrimaryKey(cart.getPof_id());
				} else {
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("订单状态异常");
					return result;
				}
			}

			// 商品信息
			GoodsVo goodsvo = goodsService.getgoodsinfo(purchaseOrder.getGoodsId(), 0);
			if (null == goodsvo) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("商品不存在");
				return result;
			}
			ezs_quality qu = ezs_qualityMapper.selectByPrimaryKey(goodsvo.getQuality_id());
			if (qu == null) {
				qu = new ezs_quality();
			}

			String address = getaddressinfo(goodsvo.getArea_id()) + goodsvo.getAddess();
			goodsvo.setAreaName(address);

			if (pacts == null || pacts.size() == 0) {
				// 生成合同信息

				pactnow.setOrder_no(orderno);
				pactnow.setDeleteStatus(false);
				pactnow.setSellerUser_id(purchaseOrder.getSellerUser_id());
				pactnow.setBuyUser_id(purchaseOrder.getBuyUser_id());
				pactnow.setStatus(0);
				pactnow.setAddTime(new Date());
				pactnow.setPact_mold(0);
				pactnow.setPact_type(1);
				ezs_pactMapper.insertSelective(pactnow);
			} else {
				pactnow = pacts.get(0);
			}

			// 查票据表

			ezs_bill bill = ezs_billMapper.selectByPrimaryKey(pactnow.getBuyUser_id());
			ezs_user buyer = ezs_userMapper.selectByPrimaryKey(pactnow.getBuyUser_id());
			ezs_user seller = ezs_userMapper.selectByPrimaryKey(pactnow.getSellerUser_id());
			// httpclient调用合同的接口
			net.sf.json.JSONObject callBackRet = null;

			HttpRequestParam httpParam = new HttpRequestParam();
			// 订单号
			httpParam.addUrlParams(new BasicNameValuePair("orderid", orderno));

			// 合同所有签章用户id,格式：userid1,userid2 （如果签章方为系统用户，userid统一100001）
			httpParam.addUrlParams(new BasicNameValuePair("memIds",
					buyer.getEzs_store().getNumber() + "," + seller.getEzs_store().getNumber()));
			// 操作合同用户id
			httpParam.addUrlParams(new BasicNameValuePair("upmemId", "100002"));

			httpParam.addUrlParams(new BasicNameValuePair("temId", "12"));

			httpParam.addUrlParams(new BasicNameValuePair("customerName", buyer.getEzs_store().getCompanyName()));
			// 客户签章用户地址（供应商实名认证地址）
			httpParam.addUrlParams(new BasicNameValuePair("customerAddress",
					getaddressinfo(buyer.getEzs_store().getArea_id()) + buyer.getEzs_store().getAddress()));
			// 法定代表人（取实名认证数据）
			httpParam.addUrlParams(new BasicNameValuePair("legalName", buyer.getEzs_store().getPerson()));
			if (null != purchaseOrder) {
				BigDecimal totalprice = purchaseOrder.getTotal_price();

				// 产品规格
				httpParam.addUrlParams(new BasicNameValuePair("productSpec", goodsvo.getName()));
				// 产品包装
				httpParam.addUrlParams(new BasicNameValuePair("productPack", "/"));
				// 产品数量
				httpParam.addUrlParams(new BasicNameValuePair("productNum", cart.getCount() + ""));
				// 产品价格

				httpParam.addUrlParams(new BasicNameValuePair("productPrice", cart.getPrice().toString()));
				// 产品总价
				httpParam.addUrlParams(new BasicNameValuePair("productAmount", totalprice.toString()));
				httpParam.addUrlParams(
						new BasicNameValuePair("productAmountCase", NumberToCN.number2CNMontrayUnit(totalprice)));
				// 交货时间 YYYY-MM-DD （预计交货时间）
				httpParam.addUrlParams(new BasicNameValuePair("productDelTime",
						Tools.date2Str(ezs_orderform.getEstimateTime(), "yyyy-MM-dd")));

				httpParam.addUrlParams(new BasicNameValuePair("buyerAddress", ""));
				// 交货地点addinfoo
				httpParam.addUrlParams(new BasicNameValuePair("productDelPlace", goodsvo.getAreaName()));
				// 首付款
				httpParam.addUrlParams(new BasicNameValuePair("downpayment", ezs_orderform.getFirst_price() + ""));
				// 尾款金额
				httpParam.addUrlParams(new BasicNameValuePair("finalpayment", ezs_orderform.getEnd_price() + ""));
				// 密度
				httpParam.addUrlParams(new BasicNameValuePair("density", goodsvo.getDensity()));
				// 熔融指数
				httpParam.addUrlParams(new BasicNameValuePair("melt", goodsvo.getLipolysis()));
				// 拉伸强度
				httpParam.addUrlParams(new BasicNameValuePair("tensile", goodsvo.getTensile()));
				// 断裂伸长率
				httpParam.addUrlParams(new BasicNameValuePair("elongation", goodsvo.getCrack()));
				// 悬臂梁缺口冲击强度
				httpParam.addUrlParams(new BasicNameValuePair("impact", goodsvo.getCantilever()));
				// 弹性模量
				httpParam.addUrlParams(new BasicNameValuePair("elasticity", goodsvo.getFlexural()));
				// 灰分含量
				httpParam.addUrlParams(new BasicNameValuePair("ash", goodsvo.getAsh()));
				// 邵氏硬度
				httpParam.addUrlParams(new BasicNameValuePair("hardness", ""));
				// RoHS限值
				httpParam.addUrlParams(new BasicNameValuePair("rohs", ""));
				// 水分含量
				httpParam.addUrlParams(new BasicNameValuePair("moisture", goodsvo.getWater()));

				// 产品名称(商品名称 )
				httpParam.addUrlParams(new BasicNameValuePair("productName", goodsvo.getName()));
				// 产品编号
				httpParam.addUrlParams(new BasicNameValuePair("goodno", goodsvo.getGood_no()));
				// 报告编号
				httpParam.addUrlParams(new BasicNameValuePair("rnumber", qu.getRnumber()));
				// 产品批号
				httpParam.addUrlParams(new BasicNameValuePair("bnumber", qu.getBnumber()));
				List<ezs_quality_detail> lists = qu.getItems();
				if (null == lists) {
					lists = new ArrayList<>();
				}
				for (int i = 0; i < lists.size(); i++) {
					// 项目名称
					httpParam.addUrlParams(new BasicNameValuePair("qualityDetail[" + i + "].name",
							lists.get(i).getEzs_qualityitem().getName()));
					// 实验方法
					httpParam.addUrlParams(new BasicNameValuePair("qualityDetail[" + i + "].measure",
							lists.get(i).getEzs_qualityitem().getMeasure()));
					// 实验条件
					httpParam.addUrlParams(new BasicNameValuePair("qualityDetail[" + i + "].term",
							lists.get(i).getEzs_qualityitem().getTerm()));
					// 单位
					httpParam.addUrlParams(new BasicNameValuePair("qualityDetail[" + i + "].unit",
							lists.get(i).getEzs_qualityitem().getUnit()));
					// 产品规格
					httpParam.addUrlParams(new BasicNameValuePair("qualityDetail[" + i + "].product_format",
							lists.get(i).getEzs_qualityitem().getProduct_format()));
					// 检测结果
					httpParam.addUrlParams(new BasicNameValuePair("qualityDetail[" + i + "].q_result",
							lists.get(i).getEzs_qualityitem().getQ_result()));
				}

			}
			// 纳税人识别号
			httpParam.addUrlParams(new BasicNameValuePair("taxpayerNum", bill.getDutyNo()));
			// 地址、电话
			httpParam.addUrlParams(new BasicNameValuePair("taxpayeInfo", bill.getAddress() + bill.getPhone()));
			// 开户行及账号
			httpParam.addUrlParams(new BasicNameValuePair("bankAccount", bill.getBank() + bill.getNumber()));

			// 电话（收款电话）
			httpParam.addUrlParams(new BasicNameValuePair("customerTel", buyer.getEzs_userinfo().getTel()));
			// 通讯地址（供应商收款地址）
			httpParam.addUrlParams(new BasicNameValuePair("customerTelAddress", buyer.getEzs_store().getAddress()));
			// 邮箱（注册邮箱）
			httpParam.addUrlParams(new BasicNameValuePair("customerEmail", buyer.getEzs_userinfo().getEmail()));

			/**
			 * 卖家
			 */
			httpParam
					.addUrlParams(new BasicNameValuePair("sellercustomerName", seller.getEzs_store().getCompanyName()));
			// 客户签章用户地址（供应商实名认证地址）
			httpParam.addUrlParams(new BasicNameValuePair("sellercustomerAddress",
					getaddressinfo(seller.getEzs_store().getArea_id()) + seller.getEzs_store().getAddress()));
			// 法定代表人（取实名认证数据）
			httpParam.addUrlParams(new BasicNameValuePair("sellerlegalName", seller.getEzs_store().getPerson()));
			// 纳税人识别号
			httpParam.addUrlParams(new BasicNameValuePair("sellertaxpayerNum", seller.getEzs_bill().getDutyNo()));
			// 地址、电话
			httpParam.addUrlParams(new BasicNameValuePair("sellertaxpayeInfo",
					seller.getEzs_bill().getAddress() + seller.getEzs_bill().getPhone()));
			// 开户行及账号
			httpParam.addUrlParams(new BasicNameValuePair("sellerbankAccount",
					seller.getEzs_bill().getBank() + seller.getEzs_bill().getNumber()));

			// 电话（收款电话）
			httpParam.addUrlParams(new BasicNameValuePair("sellercustomerTel", seller.getEzs_userinfo().getTel()));
			// 通讯地址（供应商收款地址）
			httpParam.addUrlParams(new BasicNameValuePair("sellercustomerTelAddress",
					getaddressinfo(seller.getEzs_store().getArea_id()) + seller.getEzs_store().getAddress()));
			// 邮箱（注册邮箱）
			httpParam.addUrlParams(new BasicNameValuePair("sellercustomerEmail", seller.getEzs_userinfo().getEmail()));
			// 邮箱（注册邮箱）
			httpParam.addUrlParams(new BasicNameValuePair("sellercustomerTelAddress",
					getaddressinfo(seller.getEzs_store().getArea_id()) + seller.getEzs_store().getAddress()));
			// post调用接口
			callBackRet = HttpRemoteRequestUtils
					.doPost(signbase + "/website/certSign/forout/version3/processNewContractFast.do", httpParam);
			// 获取合同的pdf地址
//		String pdfpath=(String) callBackRet.get("content");
			Map object = (Map) JSON.parseObject(callBackRet.toString());
			Map content = (Map) JSON.parseObject(object.get("content").toString());

			if (content == null) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("生成合同失败");
				result.setSuccess(false);
			} else {
				String pdfpath = content.get("pdfpath").toString();
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				result.setMsg("请求成功");
				result.setSuccess(true);
				Map<String, Object> map = new HashMap<>();
				map.put("pdfurl",
						pdfpath);
				result.setObj(map);
			}

		} catch (Exception e) {
			// e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误");
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 * 地址
	 * 
	 * @param areaid
	 * @return
	 */
	private String getaddressinfo(long areaid) {
		StringBuilder sb = new StringBuilder();
		String threeinfo = "";
		String twoinfo = "";
		String oneinfo = "";
		ezs_area ezs_threeinfo = ezs_areaMapper.selectByPrimaryKey(areaid);
		if (ezs_threeinfo != null) {
			threeinfo = ezs_threeinfo.getAreaName();
			ezs_area ezs_twoinfo = ezs_areaMapper.selectByPrimaryKey(ezs_threeinfo.getParent_id());
			if (ezs_twoinfo != null) {
				twoinfo = ezs_twoinfo.getAreaName();
				ezs_area ezs_oneinfo = ezs_areaMapper.selectByPrimaryKey(ezs_twoinfo.getParent_id());
				if (ezs_oneinfo != null) {
					oneinfo = ezs_oneinfo.getAreaName();
				}
			}
		}

		if (!Tools.isEmpty(threeinfo)) {
			sb = new StringBuilder().append(threeinfo);
		}
		if (!Tools.isEmpty(twoinfo)) {
			sb = new StringBuilder().append(twoinfo).append("-").append(threeinfo);
		}
		if (!Tools.isEmpty(oneinfo)) {
			sb = new StringBuilder().append(oneinfo).append("-").append(twoinfo).append("-").append(threeinfo);
		}

		return sb.toString();
	}

	@Override
	public Result signContentForAdd(Result result, String orderno) {
		// 先查一下合同的pdf在不在
		List<ezs_pact> pacts = ezs_pactMapper.selectPactByOrderNo(orderno);
		ezs_pact pactnow = new ezs_pact();
		ezs_goodscart cart = new ezs_goodscart();
		// 查订单表
		ezs_orderform ezs_orderform = new ezs_orderform();
		ezs_purchase_orderform purchaseOrder = new ezs_purchase_orderform();
		ezs_orderform = ezs_orderformMapper.selectByorderno(orderno);
		if (null == ezs_orderform) {
			purchaseOrder = purchaseOrderformMapper.selectByOrderNo(orderno);
			if (null != purchaseOrder) {
				cart = ezs_goodscartMapper.selectGoodsCartByOfidOrPofid(0, purchaseOrder.getId());
				if (null != cart) {
					ezs_orderform = ezs_orderformMapper.selectByPrimaryKey(cart.getOf_id());
				} else {
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("订单状态异常");
					return result;
				}
			} else {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("订单不存在");
				return result;
			}

		} else {
			cart = ezs_goodscartMapper.selectGoodsCartByOfidOrPofid(ezs_orderform.getId(), 0);
			if (null != cart) {
				purchaseOrder = purchaseOrderformMapper.selectByPrimaryKey(cart.getPof_id());
			} else {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("订单状态异常");
				return result;
			}
		}

		if (pacts == null || pacts.size() == 0) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("订单状态异常");
			return result;
		} else {
			pactnow = pacts.get(0);
		}

		// 签订合同
		ezs_user buyer = ezs_userMapper.selectByPrimaryKey(pactnow.getBuyUser_id());
		ezs_user seller = ezs_userMapper.selectByPrimaryKey(pactnow.getSellerUser_id());
		result = seller_order_signature(orderno, buyer);
		if (result.getSuccess()) {
			result = seller_order_signature(orderno, seller);
		}

		return result;
	}

	public Result seller_order_signature(String order_no, ezs_user upi) {
		Result result = Result.failure();
		try {

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

			String url = signbase + "/website/certSign/forout/version3/signContentFast.do";
			HttpRequestParam httpParam = new HttpRequestParam();
			for (Entry<String, Object> en : mv.entrySet()) {
				httpParam.addUrlParams(new BasicNameValuePair(en.getKey(), String.valueOf(en.getValue())));
			}
			JSONObject callBackRet = null;
			callBackRet = HttpRemoteRequestUtils.doPost(url, httpParam);
			com.sanbang.vo.sign.Result res = (com.sanbang.vo.sign.Result) JSONObject.toBean(callBackRet,
					com.sanbang.vo.sign.Result.class);
			if (res.isSuccess()) {
				mv.clear();
				mv.put("pdfurl", res.getContent());
				result.setSuccess(true);
				result.setMsg("请求成功 ");
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				result.setObj(mv);
			} else {
				result.setSuccess(false);
				result.setMsg(res.getMessage());
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);

			}
			log.info("h5请求签章返回：" + callBackRet);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}

		return result;
	}

	@Override
	public Result payconfirm(HttpServletRequest request, String order_no, ezs_user upi) {
		Result result = Result.failure();
		ezs_goodscart cart = new ezs_goodscart();
		// 查订单表
		ezs_orderform ezs_orderform = new ezs_orderform();
		ezs_purchase_orderform purchaseOrder = new ezs_purchase_orderform();
		ezs_orderform = ezs_orderformMapper.selectByorderno(order_no);
		if (null == ezs_orderform) {
			purchaseOrder = purchaseOrderformMapper.selectByOrderNo(order_no);
			if (null != purchaseOrder) {
				cart = ezs_goodscartMapper.selectGoodsCartByOfidOrPofid(0, purchaseOrder.getId());
				if (null != cart) {
					ezs_orderform = ezs_orderformMapper.selectByPrimaryKey(cart.getOf_id());
				} else {
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("订单状态异常");
					return result;
				}
			} else {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("订单不存在");
				return result;
			}

		} else {
			cart = ezs_goodscartMapper.selectGoodsCartByOfidOrPofid(ezs_orderform.getId(), 0);
			if (null != cart) {
				purchaseOrder = purchaseOrderformMapper.selectByPrimaryKey(cart.getPof_id());
			} else {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("订单状态异常");
				return result;
			}
		}

		long buyerid = purchaseOrder.getBuyUser_id();
		long sellerid = purchaseOrder.getSellerUser_id();
		ezs_bill buybill = ezs_billMapper.selectByPrimaryKey(buyerid);
		ezs_bill sellbill = ezs_billMapper.selectByPrimaryKey(sellerid);
		Map<String, Object> map = new HashMap<String, Object>();
		ezs_check_order_main checkorder = ezs_check_order_mainMapper
				.getCheckOrderForOrderNO(ezs_orderform.getOrder_no());
		// 商品信息
		GoodsVo goodsvo = goodsService.getgoodsinfo(purchaseOrder.getGoodsId(), 0);
		if (null == goodsvo) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("商品不存在");
			return result;
		}

		BigDecimal Imblance_money = new BigDecimal("0");
		if (null == checkorder) {
			Imblance_money = purchaseOrder.getTotal_price();
		} else {
			Imblance_money = checkorder.getImblance_money();
		}

		// 买家
		if (upi.getId() == buyerid) {
			// 判断是否在待支付状态 200
			if (ezs_orderform.getOrder_status() == 200) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(true);
				result.setMsg("请求成功");
				map.put("order_status", "201");
				map.put("companyname", buybill.getCompanyName());// 卖方收款账户
				map.put("bank", buybill.getBank());
				map.put("banknum", buybill.getNumber());

				Map<String, BigDecimal> price = ezs_payinfoMapper.getOrderPayInfoForUser(buyerid,
						ezs_orderform.getOrder_no());
				BigDecimal income = price.get("income");// 已支付
				BigDecimal spending = price.get("spending");// 已支付
				int status = Imblance_money.compareTo(new BigDecimal("0"));
				String bz = "货款已付" + income + "元,收到退款" + spending + "元。实际应付"
						+ income.subtract(spending).add(Imblance_money) + "元"
						+ (status > 0 ? "当前应付" + Imblance_money + "元"
								: "应收" + new BigDecimal("0").subtract(Imblance_money) + "元");
				map.put("paytype", bz);
				map.put("small_price", (status > 0 ? Imblance_money : new BigDecimal("0").subtract(Imblance_money)));
				map.put("pay_price", (status > 0 ? Imblance_money : new BigDecimal("0").subtract(Imblance_money)));
			} else {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("此订单不在支付状态");
				return result;
			}
		} else if (upi.getId() == sellerid) {
			// 判断是否在待支付状态 210
			if (purchaseOrder.getOrder_status() == 205) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(true);
				result.setMsg("请求成功");
				map.put("order_status", purchaseOrder.getOrder_status());
				map.put("companyname", sellbill.getCompanyName());// 卖方收款账户
				map.put("bank", sellbill.getBank());
				map.put("banknum", sellbill.getNumber());
				Map<String, BigDecimal> price = ezs_payinfoMapper.getOrderPayInfoForUser(buyerid,
						ezs_orderform.getOrder_no());
				BigDecimal income = price.get("income");// 已支付
				BigDecimal spending = price.get("spending");// 已支付
				int status = Imblance_money.compareTo(new BigDecimal("0"));
				String bz = "已收货款" + income + "元,已退款" + spending + "元" + (status > 0 ? "应收" + Imblance_money + "元"
						: "实际应收货款" + income.subtract(new BigDecimal("0").subtract(Imblance_money)).subtract(spending)
								+ "元，应退" + new BigDecimal("0").subtract(Imblance_money) + "元");
				map.put("paytype", bz);
				map.put("small_price", (status > 0 ? Imblance_money : new BigDecimal("0").subtract(Imblance_money)));
				map.put("pay_price", (status > 0 ? Imblance_money : new BigDecimal("0").subtract(Imblance_money)));
			} else {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("此订单不在支付状态");
				return result;
			}
		}

		map.put("phone", "联系电话");// 联系电话
		map.put("rname", "联系人姓名");// 联系人姓名
		map.put("address", "收货地址");// 收货地址
		map.put("name", goodsvo.getName());// 商品名称
		map.put("price", cart.getPrice());// 单价
		map.put("goods_amount", cart.getCount());// 数量
		map.put("order_no", ezs_orderform.getOrder_no());// 订单号
		map.put("addTime", ezs_orderform.getAddTime());// 下单时间
		map.put("total_price", ezs_orderform.getTotal_price());// 总价
		map.put("ispg", 0);// 是否为待评价
		map.put("goodsid", goodsvo.getId());// 是否为待评价
		map.put("yunfei", 0);
		map.put("prop", String.valueOf(100) + "%");
		result.setObj(map);
		return result;
	}

	@Override
	public Result contentPreview(Result result, HttpServletRequest request, String order_no, long buyerid,
			long sellerid, BigDecimal count,  long goodsId) throws Exception {
		Map<String, Object> param = new HashMap<>();
		// 查票据表
		ezs_bill bill = ezs_billMapper.selectByPrimaryKey(buyerid);
		ezs_user buyer = ezs_userMapper.selectByPrimaryKey(sellerid);
		ezs_user seller = ezs_userMapper.selectByPrimaryKey(sellerid);
		GoodsVo goods = goodsService.getgoodsinfo(goodsId, 0);
		param.put("customerName", buyer.getEzs_store().getCompanyName());
		param.put("customerAddress",
				getaddressinfo(buyer.getEzs_store().getArea_id()) + buyer.getEzs_store().getAddress());
		param.put("legalName", buyer.getEzs_store().getPerson());
		BigDecimal totalprice = count.multiply(goods.getSaleprice()).setScale(2, BigDecimal.ROUND_UP);
		param.put("productSpec", goods.getName());
		param.put("productPack", "/");
		param.put("productNum", count);
		param.put("productPrice", goods.getSaleprice());
		param.put("productAmount", totalprice.toString());
		param.put("productAmountCase", NumberToCN.number2CNMontrayUnit(totalprice));
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 3);
		param.put("productDelTime", Tools.date2Str(c.getTime(), "yyyy-MM-dd"));
		param.put("buyerAddress", "/");
		param.put("productDelPlace", goods.getAreaName());
		param.put("downpayment", totalprice.divide(new BigDecimal("100")).multiply(new BigDecimal("20")).setScale(2, BigDecimal.ROUND_UP));
		param.put("finalpayment", totalprice.divide(new BigDecimal("100")).multiply(new BigDecimal("80")).setScale(2, BigDecimal.ROUND_UP));
		param.put("density", goods.getDensity());
		param.put("melt", goods.getLipolysis());
		param.put("tensile", goods.getTensile());
		param.put("elongation", goods.getCrack());
		param.put("impact", goods.getCantilever());
		param.put("elasticity", goods.getFlexural());
		param.put("ash", goods.getAsh());
		param.put("hardness", "/");

		param.put("rohs", "/");
		param.put("moisture", goods.getWater());
		param.put("productName", goods.getName());
		ezs_quality qu = ezs_qualityMapper.selectByPrimaryKey(goods.getQuality_id());
		if (qu == null) {
			qu = new ezs_quality();
		}
		param.put("goodno", goods.getGood_no());
		param.put("rnumber", qu.getRnumber());
		param.put("bnumber", qu.getBnumber());

		// 纳税人识别号
		param.put("taxpayerNum", bill.getDutyNo());
		// 地址、电话
		param.put("taxpayeInfo", bill.getAddress() + bill.getPhone());
		// 开户行及账号
		param.put("bankAccount", bill.getBank() + bill.getNumber());

		// 电话（收款电话）
		param.put("customerTel", buyer.getEzs_userinfo().getTel());
		// 通讯地址（供应商收款地址）
		param.put("customerTelAddress", buyer.getEzs_store().getAddress());
		// 邮箱（注册邮箱）
		param.put("customerEmail", buyer.getEzs_userinfo().getEmail());

		/**
		 * 卖家
		 */
		param.put("sellercustomerName", seller.getEzs_store().getCompanyName());
		// 客户签章用户地址（供应商实名认证地址）
		param.put("sellercustomerAddress",
				getaddressinfo(seller.getEzs_store().getArea_id()) + seller.getEzs_store().getAddress());
		// 法定代表人（取实名认证数据）
		param.put("sellerlegalName", seller.getEzs_store().getPerson());
		// 纳税人识别号
		param.put("sellertaxpayerNum", seller.getEzs_bill().getDutyNo());
		// 地址、电话
		param.put("sellertaxpayeInfo", seller.getEzs_bill().getAddress() + seller.getEzs_bill().getPhone());
		// 开户行及账号
		param.put("sellerbankAccount", seller.getEzs_bill().getBank() + seller.getEzs_bill().getNumber());

		// 电话（收款电话）
		param.put("sellercustomerTel", seller.getEzs_userinfo().getTel());
		// 通讯地址（供应商收款地址）
		param.put("sellercustomerTelAddress",
				getaddressinfo(seller.getEzs_store().getArea_id()) + seller.getEzs_store().getAddress());
		// 邮箱（注册邮箱）
		param.put("sellercustomerEmail", seller.getEzs_userinfo().getEmail());
		// 邮箱（注册邮箱）
		param.put("sellercustomerTelAddress",
				getaddressinfo(seller.getEzs_store().getArea_id()) + seller.getEzs_store().getAddress());

		// 法定代表人（取实名认证数据）
		param.put("legalName", buyer.getEzs_store().getPerson());
		
		
		List<ezs_quality_detail> lists = new ArrayList<>();
		/*if (null != purchaseOrder) {
			 * for (int i = 0; i < lists.size(); i++) { // 项目名称 httpParam.addUrlParams(new
			 * BasicNameValuePair("qualityDetail[" + i + "].name",
			 * lists.get(i).getEzs_qualityitem().getName())); // 实验方法
			 * httpParam.addUrlParams(new BasicNameValuePair("qualityDetail[" + i +
			 * "].measure", lists.get(i).getEzs_qualityitem().getMeasure())); // 实验条件
			 * httpParam.addUrlParams(new BasicNameValuePair("qualityDetail[" + i +
			 * "].term", lists.get(i).getEzs_qualityitem().getTerm())); // 单位
			 * httpParam.addUrlParams(new BasicNameValuePair("qualityDetail[" + i +
			 * "].unit", lists.get(i).getEzs_qualityitem().getUnit())); // 产品规格
			 * httpParam.addUrlParams(new BasicNameValuePair("qualityDetail[" + i +
			 * "].product_format", lists.get(i).getEzs_qualityitem().getProduct_format()));
			 * // 检测结果 httpParam.addUrlParams(new BasicNameValuePair("qualityDetail[" + i +
			 * "].q_result", lists.get(i).getEzs_qualityitem().getQ_result())); }
			 

		}*/
		param.put("baog", lists);
		result = uploadFile(request, result, param);
		return result;
	}

	/**
	 * 上传图片到临时路径 width 和 height都为0代表 不检查图片长宽 width 和 height都不为0代表检查长宽 传多少 限制
	 * 图片宽和长就是多少 size是当前图片的大小 单位是字节 返回 json串 code为000代表操作成功
	 */
	@SuppressWarnings("unused")
	private Result uploadFile(HttpServletRequest request, Result result, Map<String, Object> param) throws Exception {
		String path = null;
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder ym = this.dateUtil(0).append(this.dateUtil(1)).append(File.separator);
		StringBuilder d = this.dateUtil(2).append(File.separator);
		StringBuilder hms = this.dateUtil(3).append("-").append(this.dateUtil(4)).append("-").append(this.dateUtil(5))
				.append("-").append(this.dateUtil(6)).append("-").append(this.dateUtil(7));
		log.info("合同预览");
		result = saveFileMethod(ym, d, hms, path, result, param);
		return result;
	}

	/**
	 * 封装相同方法
	 * 
	 * @param ym
	 * @param d
	 * @param hms
	 * @param file
	 * @param path
	 * @param map
	 * @throws Exception
	 */
	public Result saveFileMethod(StringBuilder ym, StringBuilder d, StringBuilder hms, String path, Result result,
			Map<String, Object> param) throws Exception {
		Map<String, Object> map = new HashMap<>();
		isExist(TEMPSAVEPATH + ym);
		isExist(TEMPSAVEPATH + ym + d);
		String suffix = System.currentTimeMillis() + ".html";
		path = TEMPSAVEPATH + ym + d + hms + suffix;
		ProcessHtmlUtil.createHtml(path, TEMPNAME, param);

		String url = this.getUrl(path, BASEURL, TEMPATHFLAG);

		map.put("code", "000");
		map.put("message", "操作成功");
		if (null != map.get("url")) {
			String url1 = String.valueOf(map.get("url"));
			map.put("url", new StringBuffer().append(url1).append(",").append(url).toString());
		} else {
			map.put("url", url);
		}
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("查看合同成功");
		result.setObj(map);
		result.setSuccess(true);
		return result;
	}

	/**
	 * 不存在就创建该文件夹
	 * 
	 * @param path
	 */
	public void isExist(String path) {
		File file = new File(path);
		// 判断文件夹是否存在,如果不存在则创建文件夹
		if (!file.exists()) {
			file.mkdir();
		}
		file = null;
	}

	/**
	 * 将物理路径转换成url
	 * 
	 * @param path
	 * @param baseurl 基础地址
	 * @param flag
	 * @return
	 */
	public String getUrl(String path, String baseurl, String flag) throws Exception {
		return baseurl + path.substring(path.indexOf(flag));
	}

	public StringBuilder dateUtil(int type) {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		int param = 0;
		StringBuilder sbd = new StringBuilder();
		switch (type) {
		case 0:// 年
			param = c.get(Calendar.YEAR);
			break;
		case 1:// 月
			param = c.get(Calendar.MONTH) + 1;
			break;
		case 2:// 日
			param = c.get(Calendar.DAY_OF_MONTH);
			break;
		case 3:// 时
			param = c.get(Calendar.HOUR_OF_DAY);
			break;
		case 4:// 分
			param = c.get(Calendar.MINUTE);
			break;
		case 5:// 秒
			param = c.get(Calendar.SECOND);
			break;
		case 6:// 2位随机整数
			Random rand = new Random();
			param = rand.nextInt(91) + 9;
			if (param == 9) {
				param = 10;
			}
			break;
		case 7:// 5位随机整数
			Random rand2 = new Random();
			param = (int) (rand2.nextDouble() * (100000 - 10000) + 10000);
			break;
		}
		if (param < 10) {
			sbd.append("0").append(param);
		} else {
			sbd.append(param);
		}
		return sbd;
	}
}
