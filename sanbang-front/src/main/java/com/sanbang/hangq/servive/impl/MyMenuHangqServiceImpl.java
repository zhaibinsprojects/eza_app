package com.sanbang.hangq.servive.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.sanbang.alipay.api.MainDoAlipayUtil;
import com.sanbang.bean.ezs_column;
import com.sanbang.bean.ezs_customizedhq;
import com.sanbang.bean.ezs_documentshare;
import com.sanbang.bean.ezs_ezssubstance;
import com.sanbang.bean.ezs_memberorder;
import com.sanbang.bean.ezs_probation;
import com.sanbang.bean.ezs_subscribehq;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_columnMapper;
import com.sanbang.dao.ezs_customizedhqMapper;
import com.sanbang.dao.ezs_documentshareMapper;
import com.sanbang.dao.ezs_ezssubstanceMapper;
import com.sanbang.dao.ezs_memberorderMapper;
import com.sanbang.dao.ezs_probationMapper;
import com.sanbang.dao.ezs_subscribehqMapper;
import com.sanbang.dao.ezs_userMapper;
import com.sanbang.hangq.controller.HomeHangqIndexController;
import com.sanbang.hangq.servive.HangqAreaService;
import com.sanbang.hangq.servive.MyMenuHangqService;
import com.sanbang.redis.RedisConstants;
import com.sanbang.redis.RedisResult;
import com.sanbang.utils.DateUtils;
import com.sanbang.utils.FilePathUtil;
import com.sanbang.utils.Html2TextUtil;
import com.sanbang.utils.MD5Util;
import com.sanbang.utils.Page;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.RedisUtils;
import com.sanbang.utils.Result;
import com.sanbang.utils.StringUtil;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.goods.GoodsClassVo;
import com.sanbang.vo.goods.ezs_Dzgoods_classVo;
import com.sanbang.vo.goods.ezs_goods_classVo;
import com.sanbang.vo.hangq.AreaData;
import com.sanbang.vo.hangq.CataData;
import com.sanbang.vo.hangq.HangqCollectedVo;
import com.sanbang.vo.userauth.AuthImageVo;

@Service("myMenuHangqService")
public class MyMenuHangqServiceImpl implements MyMenuHangqService {

	@Value("${consparam.ezaisheng.base.url}")
	public String BASEURL;
	@Value("${consparam.imgs.baseurl}")
	public String BASEIMGURL;
	
	// rediskey有效期
	@Value("${consparam.redis.redisuserkeyexpir}")
	private String redisuserkeyexpir;
	@Autowired
	private ezs_subscribehqMapper ezs_subscribehqMapper;
	@Autowired
	private ezs_documentshareMapper ezs_documentshareMapper;
	@Autowired
	private com.sanbang.dao.ezs_goods_classVoMapper ezs_goods_classVoMapper;
	@Autowired
	private ezs_memberorderMapper ezs_memberorderMapper;
	@Autowired
	private ezs_customizedhqMapper ezs_customizedhqMapper;
	@Autowired
	private HangqAreaService hangqAreaService;
	@Autowired
	private ezs_ezssubstanceMapper ezs_ezssubstanceMapper;
	@Autowired
	private ezs_columnMapper columnMapper;
	// 用户登陆信息
	@Resource(name = "ezs_userMapper")
	private ezs_userMapper ezs_userMapper;
	@Autowired
	private ezs_probationMapper ezs_probationMapper;

	private static Logger log = Logger.getLogger(HomeHangqIndexController.class);

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Result myDingyueListShow(ezs_user upi, HttpServletRequest request, Result result, int pageNo) {
		List<ezs_subscribehq> list = ezs_subscribehqMapper.getDingyueRecoudList(upi.getId(), 1,
				(pageNo < 0 ? 0 : pageNo - 1) * 10, 10);
		int count = ezs_subscribehqMapper.getDingyueRecoudCount(upi.getId());
		Map<String, Object> map = new HashMap<>();
		map.put("dylist", list);
		result.setMsg("请求成功");
		result.setSuccess(true);
		result.setObj(map);
		result.setMeta(new Page(count, pageNo));
		return result;
	}

	@Override
	public Result myShoucListShow(ezs_user upi, HttpServletRequest request, Result result, int pageNo) {
		Map<String, Object> map = new HashMap<>();
		List<HangqCollectedVo> list = ezs_documentshareMapper.selectHangqCollectionedOwen(upi.getId(),
				(pageNo < 0 ? 0 : pageNo - 1) * 10, 10);
		int count = ezs_documentshareMapper.selectHangqCollectionedCountOwen(upi.getId());
		map.put("hqced", list);
		result.setMsg("请求成功");
		result.setSuccess(true);
		result.setObj(map);
		result.setMeta(new Page(count, pageNo));
		return result;
	}

	@Override
	public Result myDingzhiListShow(ezs_user upi, HttpServletRequest request, Result result, boolean ispush,
			int pageNo) {
		Map<String, Object> map = new HashMap<>();
		List<ezs_customizedhq> list = ezs_customizedhqMapper.getDingZhiListByParam(upi.getId(), ispush,
				(pageNo < 0 ? 0 : pageNo - 1) * 10, 10);
		int count = ezs_customizedhqMapper.getDingZhiListByParamCount(upi.getId(), ispush);
		map.put("dzlist", list);
		result.setMsg("请求成功");
		result.setSuccess(true);
		result.setObj(map);
		result.setMeta(new Page(count, pageNo));
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result myDingyueInfoShow(ezs_user upi, HttpServletRequest request, Result result, long id) {
		Map<String, Object> map = new HashMap<>();
		ezs_subscribehq subscribehq = ezs_subscribehqMapper.selectByPrimaryKey(id);
		if (null == subscribehq/* ||(subscribehq!=null&&subscribehq.getStore_id()!=upi.getStore_id()) */) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("非法请求");
			return result;
		}

		try {
			map.put("id", subscribehq.getId());
			map.put("addtime", sdf.format(subscribehq.getAddTime()));
			if (subscribehq.getSubType() == 0) {
				map.put("title", "价格行情综合服务(试用)");
				map.put("cycle", subscribehq.getCycle() + "天");
				map.put("paymode", subscribehq.getPaymode());
				map.put("payment", subscribehq.getPayment());
				map.put("opentime", DateUtils.getFormattedString(subscribehq.getAddTime(), "yyyy-MM-dd"));
				Calendar c = Calendar.getInstance();
				c.setTime(subscribehq.getAddTime());
				c.add(Calendar.MONTH, Integer.valueOf(subscribehq.getCycle()));

				map.put("timecycle", "预计服务有效时间为" + DateUtils.getFormattedString(new Date(), "yyyy-MM-dd") + "-"
						+ DateUtils.getFormattedString(c.getTime(), "yyyy-MM-dd"));

				map.put("openmode", subscribehq.getOpenmode());

				Result result1 = getHangqHomeCata("all");
				List<Map<String, Object>> list = (List<Map<String, Object>>) result1.getObj();
				list.remove(0);
				map.put("cata", list);
				result.setObj(map);
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				result.setSuccess(true);
				return result;
			}

			map.put("title", "价格行情综合服务（购买）");
			map.put("cycle", subscribehq.getCycle() + "个月");
			map.put("paymode", subscribehq.getPaymode());
			map.put("payment", subscribehq.getPayment());

			// 是否支付
			ezs_memberorder order = subscribehq.getMemberorder();
			if (null != order && order.getPayState() == 1) {
				// 是否开通
				if (subscribehq.getMemberorder().getOpenStatu() == 1) {
					// 已支付已开通
					map.put("opentime",
							DateUtils.getFormattedString(subscribehq.getMemberorder().getStartTime(), "yyyy-MM-dd"));
					map.put("timecycle", "服务有效时间为" + subscribehq.getMemberorder().getStartTime() + "-"
							+ subscribehq.getMemberorder().getEndTime());
					map.put("openmode", 1);
				} else {
					// 已支付未开通
					map.put("opentime", DateUtils.getFormattedString(new Date(), "yyyy-MM-dd"));
					Calendar c = Calendar.getInstance();
					c.setTime(new Date());
					c.add(Calendar.MONTH, Integer.valueOf(subscribehq.getCycle()));
					map.put("timecycle", "预计服务有效时间为" + DateUtils.getFormattedString(new Date(), "yyyy-MM-dd") + "-"
							+ DateUtils.getFormattedString(c.getTime(), "yyyy-MM-dd"));
					map.put("openmode", 2);
				}

			} else {
				// 未支付未开通
				map.put("opentime", subscribehq.getCycle() + "个月");
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				c.add(Calendar.MONTH, Integer.valueOf(subscribehq.getCycle()));
				map.put("timecycle", "预计服务有效时间为" + DateUtils.getFormattedString(new Date(), "yyyy-MM-dd") + "-"
						+ DateUtils.getFormattedString(c.getTime(), "yyyy-MM-dd"));
				map.put("openmode", 0);
			}

			List<CataData> list = ezs_customizedhqMapper.getDingZhiCataInitData(upi.getId());
			List<Map<String, Object>> datalist = new ArrayList<>();
			for (CataData cataData : list) {

				Map<String, Object> map1 = new HashMap<>();
				List<CataData> list1 = new ArrayList<>();

				map1.put("id", cataData.getId());
				map1.put("name", cataData.getName());

				ezs_goods_classVo vo = ezs_goods_classVoMapper.selectByPrimaryKey(cataData.getId());

				if (vo.getLevel().equals("1")) {
					List<GoodsClassVo> temp1 = ezs_goods_classVoMapper.gethanqChildClassCheckAll(cataData.getId());
					List<Map<String, Object>> list4p = new ArrayList<>();
					for (GoodsClassVo tempc1 : temp1) {
						Map<String, Object> chale1 = new HashMap<>();
						chale1.put("id", tempc1.getId());
						chale1.put("name", tempc1.getName());

						List<GoodsClassVo> tempcc1 = ezs_goods_classVoMapper.gethanqChildClassCheckAll(tempc1.getId());
						List<Map<String, Object>> list4 = new ArrayList<>();
						for (GoodsClassVo tempccc1 : tempcc1) {
							Map<String, Object> chale2 = new HashMap<>();
							chale2.put("id", tempccc1.getId());
							chale2.put("name", tempccc1.getName());

							String subtotal = subscribehq.getSubtotal();
							String indexid = String.valueOf(tempccc1.getId());
							System.err.println(subtotal + "====" + indexid);

							if (getCataIsTrue(subscribehq.getSubtotal().split(","), String.valueOf(tempccc1.getId()))) {
								list4.add(chale2);
								System.err.println("true");
							} else {
								System.err.println("false");
							}

						}
						if (list4.size() > 0) {
							chale1.put("children", list4);
							list4p.add(chale1);
						}

					}
					map1.put("children", list4p);
					datalist.add(map1);
				} else {
					List<Map<String, Object>> list5 = new ArrayList<>();
					List<GoodsClassVo> tempcc1 = ezs_goods_classVoMapper.gethanqChildClassCheckAll(cataData.getId());
					for (GoodsClassVo map2 : tempcc1) {
						Map<String, Object> chale2 = new HashMap<>();
						chale2.put("id", map2.getId());
						chale2.put("name", map2.getName());

						String subtotal = subscribehq.getSubtotal();
						String indexid = String.valueOf(map2.getId());
						System.err.println(subtotal + "====" + indexid);

						if (getCataIsTrue(subscribehq.getSubtotal().split(","), String.valueOf(map2.getId()))) {
							list5.add(chale2);
							System.err.println("true");
						} else {
							System.err.println("false");
						}

					}
					cataData.setChildren(list5);
					list1.add(cataData);
					map1.put("children", list1);
					datalist.add(map1);
				}

			}
			map.put("cata", datalist);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setSuccess(true);
		result.setObj(map);
		return result;
	}

	private boolean getCataIsTrue(String[] parc, String cli) {
		boolean istrue = false;
		for (String str : parc) {
			if (str.equals(cli)) {
				istrue = true;
			}
		}
		return istrue;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result saveDingyuePic(HttpServletRequest request, ezs_user upi, long id, String urlParam, Result result) {
		try {
			ezs_subscribehq recoed = ezs_subscribehqMapper.selectByPrimaryKey(id);
			if (null == recoed) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("订阅记录不存在");
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

			if (!vo.getImgcode().equals("HQPAY")) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("标识错误");
				return result;
			}

			ezs_memberorder order = new ezs_memberorder();
			// 查看是否有支付记录
			if (recoed.getOrder_id() == null || recoed.getOrder_id() < (long) 1) {
				order = new ezs_memberorder();
				order.setAddTime(new Date());
				order.setCreditUsed(Integer.valueOf(String.valueOf(upi.getId())));
				order.setDeleteStatus(false);
				order.setMemberType("价格行情");
				order.setOpenStatu(0);
				order.setOperator(upi.getTrueName());
				order.setOrder_no(Tools.getHangqOrderNO());
				order.setOrderSource("APP");
				order.setPayAmount(recoed.getTotalMoney());
				order.setPayMode(1);
				order.setPayState(1);
				order.setStartTime(new Date());
				order.setStore_id(upi.getStore_id());
				order.setVoucher(FilePathUtil.getHangqPath(vo.getImgurl()));
				int st = ezs_memberorderMapper.insertSelective(order);
				ezs_subscribehq recoed1 = new ezs_subscribehq();
				recoed1.setId(recoed.getId());
				recoed1.setOrder_id(order.getId());
				recoed1.setPaymode(1);
				ezs_subscribehqMapper.updateByPrimaryKeySelective(recoed1);
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(true);
				result.setMsg("你的支付记录已提交,请耐心等待客服审核！");
				return result;
			} else {
				// 查看已有支付记录是否正确
				order = ezs_memberorderMapper.selectByPrimaryKey(recoed.getOrder_id());
				if (order == null) {
					order = new ezs_memberorder();
					order.setAddTime(new Date());
					order.setCreditUsed(Integer.valueOf(String.valueOf(upi.getId())));
					order.setDeleteStatus(false);
					order.setMemberType("价格行情");
					order.setOpenStatu(0);
					order.setOperator(upi.getTrueName());
					order.setOrder_no(Tools.getHangqOrderNO());
					order.setOrderSource("APP");
					order.setPayAmount(recoed.getTotalMoney());
					order.setPayMode(1);
					order.setPayState(1);
					order.setStartTime(new Date());
					order.setStore_id(upi.getStore_id());
					order.setVoucher(FilePathUtil.getHangqPath(vo.getImgurl()));
					int st = ezs_memberorderMapper.insertSelective(order);

					ezs_subscribehq recoed1 = new ezs_subscribehq();
					recoed1.setId(recoed.getId());
					recoed1.setOrder_id(order.getId());
					recoed1.setPaymode(1);
					ezs_subscribehqMapper.updateByPrimaryKeySelective(recoed1);
					result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
					result.setSuccess(true);
					result.setMsg("你的支付记录已提交,请耐心等待客服审核！");
					return result;
				} else {
					if (order.getPayMode() == 1) {
						if (order.getPayState() == 1) {
							result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
							result.setSuccess(false);
							result.setMsg("你的支付记录已提交,请勿重复提交！");
							return result;
						} else {
							order.setPayState(1);
							order.setVoucher(FilePathUtil.getHangqPath(vo.getImgurl()));
							int st = ezs_memberorderMapper.updateByPrimaryKeySelective(order);
							ezs_subscribehq recoed1 = new ezs_subscribehq();
							recoed1.setId(recoed.getId());
							recoed1.setOrder_id(order.getId());
							recoed1.setPaymode(1);
							ezs_subscribehqMapper.updateByPrimaryKeySelective(recoed1);
							result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
							result.setSuccess(true);
							result.setMsg("你的支付记录已提交,请耐心等待客服审核！");
							return result;
						}

					} else {
						if (order.getPayState() == 1) {
							result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
							result.setSuccess(false);
							result.setMsg("你线上支付已完成,请勿重复支付！");
							return result;
						} else {
							order.setVoucher(FilePathUtil.getHangqPath(vo.getImgurl()));
							order.setPayState(1);
							order.setPayMode(1);
							int st = ezs_memberorderMapper.updateByPrimaryKeySelective(order);
							ezs_subscribehq recoed1 = new ezs_subscribehq();
							recoed1.setId(recoed.getId());
							recoed1.setOrder_id(order.getId());
							recoed1.setPaymode(1);
							ezs_subscribehqMapper.updateByPrimaryKeySelective(recoed1);
							result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
							result.setSuccess(true);
							result.setMsg("你的支付记录已提交,请耐心等待客服审核！");
							return result;
						}
					}

				}
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}

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
	@Transactional(rollbackFor = Exception.class)
	public Result myDingYueAdd(HttpServletRequest request, ezs_user upi, String cycle, BigDecimal payment,
			String subtotal, int isall, Result result) {
		Map<String, Object> map = new HashMap<>();
		try {

			if (null == payment) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("支付金额不能为空！");
				return result;
			}
			if (Tools.isEmpty(cycle)) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("先选择订购周期！");
				return result;
			}

			// 是否全选
			if (isall == 1) {
				List<ezs_Dzgoods_classVo> listp = ezs_goods_classVoMapper.gethangqCataBylevel(0, "3", "all");
				StringBuffer sb = new StringBuffer();
				for (ezs_Dzgoods_classVo goodsClassVo : listp) {
					sb.append(goodsClassVo.getId()).append(",");
				}
				subtotal = sb.substring(0, sb.toString().lastIndexOf(","));
			} else {

				if (Tools.isEmpty(subtotal)) {
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("请选择订购品类！");
					return result;
				}
			}

			ezs_memberorder order = new ezs_memberorder();
			order.setAddTime(new Date());
			order.setCreditUsed(Integer.valueOf(String.valueOf(upi.getId())));
			order.setDeleteStatus(true);
			order.setMemberType("价格行情");
			order.setOpenStatu(0);
			order.setOperator(upi.getTrueName());
			order.setOrder_no(Tools.getHangqOrderNO());
			order.setOrderSource("APP");
			order.setPayAmount(payment);
			order.setPayMode(1);
			order.setPayState(0);
			order.setStartTime(new Date());
			order.setStore_id(upi.getStore_id());
			order.setVoucher("");
			order.setUser_id(upi.getId());

			ezs_memberorderMapper.insertSelective(order);

			ezs_subscribehq recoed = new ezs_subscribehq();
			recoed.setAddTime(new Date());
			recoed.setCreditUserd(Integer.valueOf(String.valueOf(upi.getId())));
			recoed.setCycle(cycle);
			recoed.setDeleteStatus(true);
			recoed.setOpenmode(0);
			recoed.setPayment(payment);
			recoed.setPaymode(1);
			recoed.setStore_id(upi.getStore_id());
			recoed.setSubtotal(subtotal);
			recoed.setSubType(1);
			recoed.setTotalMoney(payment);
			recoed.setUser_id(upi.getId());
			recoed.setOrder_id(order.getId());
			ezs_subscribehqMapper.insertSelective(recoed);

			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setSuccess(true);
			result.setMsg("生成订单成功！");
			map.put("recoedid", recoed.getId());
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}
		result.setObj(map);
		return result;
	}

	@Override
	public Result myDingZhi(ezs_user upi, HttpServletRequest request, Result result) {
		try {
			List<CataData> list = ezs_customizedhqMapper.getDingZhiCataInitData(upi.getId());
			List<Map<String, Object>> datalist = new ArrayList<>();
			for (CataData cataData : list) {

				Map<String, Object> map1 = new HashMap<>();
				List<CataData> list1 = new ArrayList<>();

				map1.put("id", cataData.getId());
				map1.put("name", cataData.getName());

				ezs_goods_classVo vo = ezs_goods_classVoMapper.selectByPrimaryKey(cataData.getId());

				if (vo.getLevel().equals("1")) {
					List<GoodsClassVo> temp1 = ezs_goods_classVoMapper.gethanqChildClassCheckAll(cataData.getId());
					List<Map<String, Object>> list4p = new ArrayList<>();
					for (GoodsClassVo tempc1 : temp1) {
						Map<String, Object> chale1 = new HashMap<>();
						chale1.put("id", tempc1.getId());
						chale1.put("name", tempc1.getName());

						List<Map<String, Object>> tempcc1 = ezs_customizedhqMapper.getDingYueOwenCata(upi.getId(),
								tempc1.getId());
						List<Map<String, Object>> list4 = new ArrayList<>();
						for (Map<String, Object> tempccc1 : tempcc1) {
							Map<String, Object> chale2 = new HashMap<>();
							chale2.put("id", tempccc1.get("id"));
							chale2.put("name", tempccc1.get("name"));
							list4.add(chale2);
						}
						chale1.put("children", list4);
						list4p.add(chale1);

					}
					map1.put("children", list4p);
					datalist.add(map1);
				} else {
					list1.add(cataData);
					map1.put("children", list1);
					datalist.add(map1);
				}

			}
			List<Map<String, Object>> alist = hangqAreaService.getAreaData();
			List<Map<String, Object>> listm = AreaData.getDaQuArea(alist);

			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setSuccess(true);
			result.setMsg("请求成功");
			Map<String, Object> map = new HashMap<>();
			map.put("cata", datalist);
			map.put("area", listm);
			map.put("phone", upi.getEzs_userinfo().getPhone());
			map.put("email", upi.getEzs_userinfo().getEmail());
			result.setObj(map);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}
		return result;
	}

	@Override
	public Result myDingZhiIsPush(ezs_user upi, HttpServletRequest request, Result result, boolean isPush, long id) {
		try {
			ezs_customizedhq pushrecode = ezs_customizedhqMapper.selectByPrimaryKey(id);
			if (null == pushrecode) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("非法请求！");
				return result;
			}

			pushrecode.setIsPush(isPush);
			if (isPush) {
				result.setMsg("开通推送成功！");
			} else {
				result.setMsg("取消推送成功！");
			}
			ezs_customizedhqMapper.updateByPrimaryKeySelective(pushrecode);
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}
		return result;
	}

	@Override
	public Result myDingZhiSubmit(ezs_user upi, HttpServletRequest request, Result result, String areaids,
			String category, String pushMethod) {
		try {
			ezs_customizedhq pushrecode = new ezs_customizedhq();
			if (StringUtil.isEmpty(areaids)) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("请选择定制地区！");
				return result;
			}

			if (StringUtil.isEmpty(category)) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("请选择定制品类！");
				return result;
			} else {
				String title = "多品类推送";
				List<ezs_Dzgoods_classVo> classnames = ezs_goods_classVoMapper.getParentNamesByClassIds(category);
				StringBuffer sb = new StringBuffer();
				if (classnames.size() > 0) {
					for (ezs_Dzgoods_classVo item : classnames) {
						sb.append(item.getName());
					}
				}
				title = sb.toString();
				pushrecode.setTitle(title);
			}

			if (StringUtil.isEmpty(pushMethod)) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("请选择推送地区！");
				return result;
			}
			pushrecode.setAddTime(new Date());
			pushrecode.setAreaids(areaids.substring(0, areaids.lastIndexOf(",")));
			pushrecode.setCategory(category.substring(0, category.lastIndexOf(",")));
			pushrecode.setDeleteStatus(false);
			pushrecode.setIsPush(true);
			pushrecode.setPushMethod(pushMethod);
			pushrecode.setStore_id(upi.getStore_id());
			pushrecode.setUser_id(upi.getId());
			ezs_customizedhqMapper.insertSelective(pushrecode);
			result.setSuccess(true);
			result.setMsg("您的定制服务已提交成功！");
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result HangqPushData(long dzid, Result result) {
		try {
			ezs_customizedhq dzrecord = ezs_customizedhqMapper.selectByPrimaryKey(dzid);
			if (dzrecord == null) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("定制记录不存在");
				return result;
			}
			StringBuffer sb1 = new StringBuffer();
			sb1.append("push").append(dzid).append(sdf1.format(new Date()));// 本次记录标识定制
			// 添加本次推送记录
			String thispushkey = MD5Util.md5Encode(sb1.toString());

			String catp = ezs_customizedhqMapper.getHangqUserPushClasses(dzrecord.getUser_id());
			// 每个用户定时容器
			LinkedHashMap<String, Object> oldList = new LinkedHashMap<String, Object>();
			// 每个用户唯一标识key
			String push_key = "pushforapp" + dzrecord.getUser_id();

			// 缓存获取用户推送记录列表
			RedisResult<Result> redate = (RedisResult<Result>) RedisUtils.get(push_key, Result.class);
			if (redate.getCode() == RedisConstants.SUCCESS) {
				log.debug("redis查询用户推送记录成功");
				Result re = redate.getResult();
				oldList = (LinkedHashMap<String, Object>) re.getObj();
				if (oldList.size() > 60) {
					for (Entry<String, Object> old : oldList.entrySet()) {
						oldList.remove(old.getKey());// 最多保存（60）天
						break;
					}

				}
			}

			// 本次推送记录数据
			Map<String, Object> data = new HashMap<>();
			data.put("title", "您的行情定制推送(" + sdf1.format(new Date()) + ")已更新，点我查看");
			data.put("pushtime", sdf1.format(new Date()));
			data.put("push_key", push_key);
			data.put("pushareaids", dzrecord.getAreaids());
			data.put("ispush", 0);// 是否推送
			data.put("islook", 0);// 是否查看
			data.put("account", MD5Util.md5Encode("pushcode" + dzrecord.getUser_id()));
			StringBuffer sb = new StringBuffer();
			String[] pushckpt = dzrecord.getCategory().split(",");
			for (String inc : pushckpt) {
				if (catp.indexOf(inc) > 0) {
					sb.append(inc).append(",");
				}
			}
			data.put("pushcataids",
					sb.toString().length() > 0 ? sb.subSequence(0, sb.toString().length()) : sb.toString());

			// 添加本次推送记录
			oldList.put(thispushkey, data);

			// 重新存入本地记录(永久有效)
			RedisUtils.del(push_key);
			RedisResult<String> rrt;
			Result re = Result.success();
			re.setObj(oldList);
			rrt = (RedisResult<String>) RedisUtils.set(push_key, re, (long) 0);
			if (rrt.getCode() == RedisConstants.SUCCESS) {
				log.debug("推送记录保存到redis成功执行");
			} else {
				log.debug("推送记录保存到redis失败");
			}

			// 本次请求链接记录重新存入本地记录(永久有效)
			RedisUtils.del(thispushkey);
			RedisResult<String> thisre;
			Result re5 = Result.success();
			re5.setObj(push_key);
			thisre = (RedisResult<String>) RedisUtils.set(thispushkey, re5, (long) 0);
			if (thisre.getCode() == RedisConstants.SUCCESS) {
				log.debug("推送记录保存到redis成功执行");
			} else {
				log.debug("推送记录保存到redis失败");
			}

			// 推送总记录操作
			String needpushusers = "needpushusers";// 总记录标识
			Map<String, Object> zongpush = new HashMap<>();
			RedisResult<Result> reneedpushusers = (RedisResult<Result>) RedisUtils.get(needpushusers, Result.class);
			if (reneedpushusers.getCode() == RedisConstants.SUCCESS) {
				log.debug("redis查询用户推送记录成功");
				Result re1 = reneedpushusers.getResult();
				zongpush = (Map<String, Object>) re1.getObj();
			}

			Map<String, Object> pushuserinfo = new HashMap<>();
			List<String> needpush = new ArrayList<>();
			if (zongpush.containsKey(push_key)) {
				pushuserinfo = (Map<String, Object>) zongpush.get(push_key);
				needpush = (List<String>) pushuserinfo.get("needpush");// 需推送标识
				if (!needpush.contains(thispushkey)) {
					needpush.add(thispushkey);
					pushuserinfo.put("weidu", (int) pushuserinfo.get("weidu") + 1);
					pushuserinfo.put("zongweidu", (int) pushuserinfo.get("zongweidu") + 1);
				} else {
					pushuserinfo.put("weidu", (int) pushuserinfo.get("weidu"));
					pushuserinfo.put("zongweidu", (int) pushuserinfo.get("zongweidu"));
				}
				pushuserinfo.put("needpush", needpush);
			} else {
				pushuserinfo.put("weidu", 1);
				pushuserinfo.put("zongweidu", 1);
				pushuserinfo.put("isopenpush", 0);
				needpush = (List<String>) pushuserinfo.get("needpush");// 需推送标识
				if (null == needpush) {
					needpush = new ArrayList<>();
				}
				if (!needpush.contains(thispushkey)) {
					needpush.add(thispushkey);
				}
				pushuserinfo.put("needpush", needpush);
			}
			zongpush.put(push_key, pushuserinfo);
			// 重新存入本地记录(永久有效)
			RedisUtils.del(needpushusers);
			RedisResult<String> rrt1;
			Result re1 = Result.success();
			re1.setObj(zongpush);
			rrt1 = (RedisResult<String>) RedisUtils.set(needpushusers, re1, (long) 0);
			if (rrt1.getCode() == RedisConstants.SUCCESS) {
				log.debug("用户总推送记录保存到redis成功执行");
			} else {
				log.debug("用户总推送记录保存到redis失败");
			}

			result.setSuccess(true);
			result.setMsg("推送成功");
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
			e.printStackTrace();
		}
		result.setObj("");
		return result;
	}

	private static final String HANGQ_DATA = "HANGQ_INDEX_DATA";

	@SuppressWarnings("unchecked")
	public Result getHangqHomeCata(String reqtype) {
		Result result = Result.success();
		result.setMsg("请求失败");
		Map<String, Object> map = new HashMap<>();
		try {
			RedisResult<Result> redate = (RedisResult<Result>) RedisUtils.get(HANGQ_DATA + reqtype, Result.class);
			if (redate.getCode() == RedisConstants.SUCCESS) {
				log.debug("查询redis分类成功执行");
				result = redate.getResult();
			} else {
				log.debug("查询redis分类执行失败");
				map = hangqAreaService.getHangqParamDate(reqtype, map);
				result.setSuccess(true);
				result.setMsg("请求成功");
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				result.setObj(map);

				RedisUtils.get(HANGQ_DATA, Result.class);
				RedisResult<String> rrt;
				rrt = (RedisResult<String>) RedisUtils.set(HANGQ_DATA + reqtype, result, Long.valueOf(3600 * 24));
				if (rrt.getCode() == RedisConstants.SUCCESS) {
					log.debug("行情分类保存到redis成功执行");
				} else {
					log.debug("行情分类保存到redis失败");
				}
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
			result.setObj(map);
		}
		if (result.getSuccess()) {
			Map<String, Object> map1 = (Map<String, Object>) result.getObj();
			result.setObj(map1.get("cata"));
		}
		return result;
	}

	@Override
	public Result getDingYueStatusByUserid(ezs_user upi, HttpServletRequest request, Result result) {
		Map<String, Object> resmap = new HashMap<>();
		try {
			String hqpushstr = upi.getHqpushstr();
			Date hqtrytime = upi.getHqtrytime();
			log.info("try**********" + upi.getName() + "===" + upi.getTrueName());
			log.info("try**********hqpushstr=" + hqpushstr + "===hqtrytime=" + hqtrytime);
			if (Tools.isEmpty(hqpushstr)) {
				if (null == hqtrytime) {
					resmap.put("try", 0);// 未使用
					resmap.put("overdate", "");
					resmap.put("getDatens", "");
				} else {
					Calendar c = Calendar.getInstance();
					c.setTime(hqtrytime);
					c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 30);
					if (c.getTime().after(new Date())) {
						resmap.put("try", 1);// 使用中
						resmap.put("overdate", Tools.getDatePoor(c.getTime(), new Date()));
						resmap.put("getDatens", Tools.getDatens(c.getTime(), new Date()));
					} else {
						resmap.put("try", 2);// 过期
						resmap.put("overdate", "");
						resmap.put("getDatens", "");
					}

				}
			} else {
				resmap.put("try", 3);// 已订阅
				resmap.put("overdate", "");
				resmap.put("getDatens", "");
			}
			log.info("try**********" + resmap.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		result.setSuccess(true);
		result.setMsg("请求成功");
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setObj(resmap);

		return result;
	}

	@Override
	public Result getDingyuePayPic(HttpServletRequest request, ezs_user upi, long id, Result result) {
		try {
			Map<String, Object> map = new HashMap<>();
			ezs_subscribehq recoed = ezs_subscribehqMapper.selectByPrimaryKey(id);
			if (null == recoed) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("订阅记录不存在");
				result.setObj(map);
				return result;
			}
			ezs_memberorder order = ezs_memberorderMapper.selectByPrimaryKey(recoed.getOrder_id());

			if (null == order) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("支付记录不存在");
				result.setObj(map);
				return result;
			}
			map.put("imgurl", BASEIMGURL + order.getVoucher());
			result.setSuccess(true);
			result.setMsg("请求成功");
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setObj(map);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}

		return result;
	}

	@Override
	public Result myDingYueTryAdd(HttpServletRequest request, ezs_user upi, Result result) {

		try {
			ezs_probation probation = ezs_probationMapper.selectProbationByUserId(upi.getId());
			if (null != probation) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("您已试用过本产品！");
				return result;
			}

			probation = new ezs_probation();
			probation.setAddTime(new Date());
			probation.setDeleteStatus(false);

			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 30);

			probation.setEndTime(c.getTime());
			probation.setIsDiscontinuation(0);
			probation.setStartTime(new Date());
			probation.setUser_id(upi.getId());
			ezs_probationMapper.insertSelective(probation);
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setSuccess(true);
			result.setMsg("申请试用成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}

		if (result.getSuccess()) {
			ezs_user upi1 = ezs_userMapper.getUserInfoByUserNameFromBack(upi.getName()).get(0);
			RedisUserSession.updateUserInfo(upi.getUserkey(), upi1, Long.parseLong(redisuserkeyexpir));
		}
		return result;
	}

	@Override
	public void hangqgShow(long id, Model model) {
		ezs_ezssubstance show = ezs_ezssubstanceMapper.selectByPrimaryKey(id);
		String description = "";
		try {
			description = Html2TextUtil.Html2Text(show.getContent() == null ? "" : show.getContent());
		} catch (Exception e) {
			e.printStackTrace();
		}

		ezs_column column = columnMapper.selectByPrimaryKey(show.getEc_id());
		model.addAttribute("show", show);
		model.addAttribute("title", column.getName());
		model.addAttribute("description", StringUtil.isEmpty(description) ? column.getName() : description);
		model.addAttribute("docid", id);
	}

	public static void main(String[] args) throws ParseException {
		Date starttime = sdf.parse("2018-09-04 16:08:06");
		Date gaa = sdf.parse("2018-09-04 16:08:06");
		Calendar c = Calendar.getInstance();
		c.setTime(gaa);
		c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 30);

		System.err.println(sdf.format(c.getTime()));
		System.err.println(Tools.getDatePoor(c.getTime(), new Date()));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result submitOrder(HttpServletRequest request, ezs_user upi, long id, int paymode1, Result result) {
		try {
			Map<String, Object> map=new HashMap<>();
			result.setObj(map);
			int paymode = paymode1;
			if (paymode1 != 1) {
				paymode = 0;
			}

			ezs_subscribehq recoed = ezs_subscribehqMapper.selectByPrimaryKey(id);
			if (null == recoed) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("提交订单失败！");
				return result;
			}
			ezs_memberorder order = ezs_memberorderMapper.selectByPrimaryKey(recoed.getOrder_id());

			if (null == order) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("提交订单失败！");
				return result;
			}

			if (paymode == 1) {
				ezs_subscribehq recoed1 = new ezs_subscribehq();
				recoed1.setDeleteStatus(false);
				recoed1.setPaymode(paymode);
				recoed1.setId(recoed.getId());
				ezs_subscribehqMapper.updateByPrimaryKeySelective(recoed1);

				ezs_memberorder order1 = new ezs_memberorder();
				order1.setId(order.getId());
				order1.setPayMode(paymode);
				order1.setDeleteStatus(false);
				int st = ezs_memberorderMapper.updateByPrimaryKeySelective(order1);

				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				result.setSuccess(true);
				result.setMsg("您的订单已提交,请到个人中心进行支付!");
			} else {
				// 支付宝
				if (paymode1 == 2) {
					result = MainDoAlipayUtil.DoPayAli("我的订阅支付确认", "价格行情", order.getOrder_no(), order.getPayAmount(),
							"", result);
					log.info("价格行情支付宝支付======返回结果" + result.toString());
					if (result.getSuccess()) {
						//TODO
						//支付系统插入数据
						return result;
					} else {
						throw new Exception("价格行情支付宝支付请求支付宝支付失败");
					}
				} else {
					result.setSuccess(false);
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setMsg("暂不支持微信支付！");
				}

			}

		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}

		return result;
	}

	@Override
	public Result getDocStatusForUser(HttpServletRequest request, ezs_user upi, long id, Result result) {
		Map<String, Object> map = new HashMap<>();
		try {
			HangqCollectedVo share = ezs_documentshareMapper.getSubstanceInfoById(id, upi.getId());
			ezs_ezssubstance show = ezs_ezssubstanceMapper.selectByPrimaryKey(id);
			boolean ispass = Tools.HangqValidate(upi, show.getEc_id());
			map.put("ispass", ispass);
			if (null == share) {
				map.put("give", 0);
				map.put("house", 0);
			} else {
				map.put("give", share.getGive());
				map.put("house", share.getHouse());
			}
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setSuccess(true);
			result.setMsg("请求成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}
		result.setObj(map);
		return result;
	}

	@Override
	public Result doGiveOrHouse(HttpServletRequest request, ezs_user upi, long id, Result result, String reqtype) {
		Map<String, Object> map = new HashMap<>();
		try {
			HangqCollectedVo share = ezs_documentshareMapper.getSubstanceInfoById(id, upi.getId());
			if (reqtype.equals("give")) {
				if (null == share) {
					ezs_documentshare share1 = new ezs_documentshare();
					share1.setAddTime(new Date());
					share1.setDeleteStatus(false);
					share1.setEzsSubstance_id(id);
					share1.setGive(1);
					share1.setGood_id(null);
					share1.setHouse(0);
					share1.setUser_id(upi.getId());
					ezs_documentshareMapper.insertSelective(share1);
				} else {
					ezs_documentshare share1 = new ezs_documentshare();
					share1.setId(share.getId());
					share1.setGive(share.getGive() == 1 ? 0 : 1);
					ezs_documentshareMapper.updateByPrimaryKeySelective(share1);
				}
			} else {
				if (null == share) {
					ezs_documentshare share1 = new ezs_documentshare();
					share1.setAddTime(new Date());
					share1.setDeleteStatus(false);
					share1.setEzsSubstance_id(id);
					share1.setGive(0);
					share1.setGood_id(null);
					share1.setHouse(1);
					share1.setUser_id(upi.getId());
					ezs_documentshareMapper.insertSelective(share1);
				} else {
					ezs_documentshare share1 = new ezs_documentshare();
					share1.setId(share.getId());
					share1.setHouse(share.getHouse() == 1 ? 0 : 1);
					ezs_documentshareMapper.updateByPrimaryKeySelective(share1);
				}
			}
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setSuccess(true);
			result.setMsg("请求成功");
		} catch (Exception e) {
			e.getStackTrace();
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}
		result.setObj(map);
		return result;
	}

	
	
}
