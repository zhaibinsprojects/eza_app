package com.sanbang.buyer.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_address;
import com.sanbang.bean.ezs_area;
import com.sanbang.bean.ezs_order_info;
import com.sanbang.bean.ezs_pact;
import com.sanbang.bean.ezs_user;
import com.sanbang.buyer.service.BuyerService;
import com.sanbang.dao.ezs_addressMapper;
import com.sanbang.dao.ezs_areaMapper;
import com.sanbang.dao.ezs_orderformMapper;
import com.sanbang.dao.ezs_pactMapper;
import com.sanbang.utils.JsonUtils;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.utils.httpclient.HttpRemoteRequestUtils;
import com.sanbang.utils.httpclient.HttpRequestParam;
import com.sanbang.vo.CertSignInfoBean;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.MapTools;
import com.sanbang.vo.PagerOrder;

import net.sf.json.JSONObject;

@Service("buyerService")
public class BuyerServiceimpl implements BuyerService {

	@Value("${config.sign.callbackurl}")
	private String callbackurl;

	@Value("${config.sign.baseurl}")
	private String signbase;
	
	@Autowired
	private ezs_orderformMapper ezs_orderformMapper;

	@Autowired
	private ezs_addressMapper ezs_addressMapper;

	@Autowired
	private ezs_areaMapper ezs_areaMapper;

	@Autowired
	private ezs_pactMapper ezs_pactMapper;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");

	@Override
	public List<ezs_order_info> getOrderListByValue(PagerOrder pager) {
		pager.setPageNow(pager.getPageNow() - 1);
		pager.setSecount(pager.getPageSize() * pager.getPageNow());
		int totalcount = ezs_orderformMapper.getOrderListByValueCount(pager);
		pager.setTotalCount(totalcount);
		return ezs_orderformMapper.getOrderListByValue(pager);
	}

	@Override
	public Map<String, Object> getOrderInfoShow(String order_no) {
		Map<String, Object> map = new HashMap<String, Object>();

		ezs_order_info orderinfo = ezs_orderformMapper.getOrderListByOrderno(order_no);

		// 收货地址处理
		long addressid = orderinfo.getAddress_id();
		ezs_address ezs_address = ezs_addressMapper.selectByPrimaryKey(addressid);
		if (null != ezs_address) {
			String str = getaddressinfo(ezs_address.getArea_id());
			ezs_address.setArea_info(str += ezs_address.getArea_info());
		}

		map.put("address", ezs_address);// 收货地址
		map.put("name", orderinfo.getName());
		map.put("price", orderinfo.getPrice());
		map.put("goods_amount", orderinfo.getGoods_amount());
		map.put("total_price", orderinfo.getTotal_price());
		map.put("order_no", orderinfo.getOrder_no());
		map.put("addTime", sdf.format(orderinfo.getAddTime()));
		map.put("order_status", orderinfo.getOrder_status());
		return map;
	}

	@SuppressWarnings("null")
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
	public Result showOrderContent(HttpServletRequest request, String order_no) {
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
			// 先查一下合同的pdf在不在
			List<ezs_pact> pact = ezs_pactMapper.selectPactByOrderNo(order_no);

			if (pact == null) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("合同确认中，请稍后。如有疑问，咨询400-6666-890");
				return result;

			}

			String url = signbase+"/website/certSign/forh5/showcontentpdf.do";
			Map<String, Object> mv = new HashMap<>();

			mv.put("orderid", pact.get(0).getOrder_no());
			JSONObject callBackRet = null;
			HttpRequestParam httpParam = new HttpRequestParam();
			for (Entry<String, Object> en : mv.entrySet()) {
				httpParam.addUrlParams(new BasicNameValuePair(en.getKey(), String.valueOf(en.getValue())));
			}

			callBackRet = HttpRemoteRequestUtils.doPost(url, httpParam);

			if (null == callBackRet) {
				result.setSuccess(false);
				result.setMsg("系统错误");
				result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			}

			if ((boolean) callBackRet.get("success")) {
				mv.clear();
				mv.put("pdfurl", callBackRet.get("pdfurl"));
				result.setSuccess(true);
				result.setMsg("请求成功 ");
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			} else {
				result.setSuccess(false);
				result.setMsg("合同确认中，请稍后。如有疑问，咨询400-6666-890");
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);

			}

		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return result;
	}

	/***
	 * 
	 * @param signMemId
	 *            需盖章签章用户id
	 * @param order_no
	 *            订单号(订单标识)
	 * @param callBackUrl
	 *            客户在签章页面点击签章成功后，签章系统回调URL通知 (参数需配合业务开发)
	 * @param returnUrl
	 *            客户在签章页面点击返回按钮回到URL界面
	 * @param regid
	 *            (企业类型)5为个人 6为 个体和 公司
	 * @param company
	 *            公司名称
	 * @param getijingyinguser
	 *            经营者姓名
	 * @param getijingyingshen
	 *            经营者身份证号
	 * @param getizhuhao
	 *            营业执照注册号
	 * @param qiyereh
	 *            营业执照注册号
	 * @param qiyedaimazheng
	 *            统一社会信用代码
	 * @param accountType
	 *            1.企业认证，2.个体认证
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
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

			ezs_order_info orderinfo = ezs_orderformMapper.getOrderListByOrderno(order_no);
			if (null == orderinfo) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("订单不存在");
				return result;
			}

			if (orderinfo.getPact_status() == 1) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("纸质合同线下签订中，请您耐心等待！");
				return result;
			}

			Map<String, Object> mv = new HashMap<>();
			String company = upi.getEzs_store().getCompanyName();
			String getizhuhao = upi.getEzs_store().getAccount();
			String qiyereh = getizhuhao;
			String getijingyinguser = upi.getEzs_store().getPerson();
			String accountType = upi.getEzs_store().getAccountType() + "";
			String getijingyingshen = upi.getEzs_store().getIdCardNum();
			String qiyedaimazheng = upi.getEzs_store().getUnifyCode();
			mv.put("signMemId", upi.getEzs_store().getNumber());
			mv.put("order_no", order_no);
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
			if ((boolean) callBackRet.get("success")) {
				mv.clear();
				mv.put("pdfurl", callBackRet.get("content"));
				result.setSuccess(true);
				result.setMsg("请求成功 ");
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			} else {
				result.setSuccess(false);
				result.setMsg("合同确认中，请稍后。如有疑问，咨询400-6666-890");
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);

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
	public Map<String, Object> orderconfirm(String order_no) {
		Map<String, Object> map = new HashMap<String, Object>();

		ezs_order_info orderinfo = ezs_orderformMapper.getOrderListByOrderno(order_no);

		// 收货地址处理
		long addressid = orderinfo.getAddress_id();
		ezs_address ezs_address = ezs_addressMapper.selectByPrimaryKey(addressid);
		if (null != ezs_address) {
			String str = getaddressinfo(ezs_address.getArea_id());
			ezs_address.setArea_info(str += ezs_address.getArea_info());
		}

		map.put("address", ezs_address);// 收货地址
		map.put("name", orderinfo.getName());
		map.put("price", orderinfo.getPrice());
		map.put("goods_amount", orderinfo.getGoods_amount());
		map.put("total_price", orderinfo.getTotal_price());
		map.put("order_no", orderinfo.getOrder_no());
		map.put("order_status", orderinfo.getOrder_status());
		return map;
	}

	@Override
	public Result getContentList(String member, int temid, int pageno,HttpServletRequest request) {
		Result result=Result.failure();
		try {
			if (Tools.isEmpty(member)) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("商户编号不能为空");
				return result;
			}

			if (0==temid) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("合同类型不正确！");
				return result;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("member",member);
			map.put("temid", temid);
			map.put("pageno", pageno);

			String url = signbase+"/website/certSign/forh5/contentForOut.do";
			HttpRequestParam httpParam = new HttpRequestParam();
			for (Entry<String, Object> en : map.entrySet()) {
				httpParam.addUrlParams(new BasicNameValuePair(en.getKey(), String.valueOf(en.getValue())));
			}
			JSONObject callBackRet = null;
			callBackRet = HttpRemoteRequestUtils.doPost(url, httpParam);
			if ((boolean) callBackRet.get("success")) {
				map.clear();
				List<Map<String, Object>> list1=MapTools.parseJSON2List(String.valueOf(callBackRet.get("content")));
				map.put("content", list1);
				result.setSuccess(true);
				result.setMsg("请求成功 ");
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			} else {
				result.setSuccess(false);
				result.setMsg("无合同数据");
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);

			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			e.printStackTrace();
		}
		
		return result;
	}

	
	
	
	
}
