package com.sanbang.buyer.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanbang.addressmanage.service.AddressService;
import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_address;
import com.sanbang.bean.ezs_area;
import com.sanbang.bean.ezs_logistics;
import com.sanbang.bean.ezs_order_cance;
import com.sanbang.bean.ezs_order_info;
import com.sanbang.bean.ezs_orderform;
import com.sanbang.bean.ezs_payinfo;
import com.sanbang.bean.ezs_user;
import com.sanbang.buyer.service.BuyerService;
import com.sanbang.dao.ezs_addressMapper;
import com.sanbang.dao.ezs_areaMapper;
import com.sanbang.dao.ezs_invoiceMapper;
import com.sanbang.dao.ezs_logisticsMapper;
import com.sanbang.dao.ezs_order_canceMapper;
import com.sanbang.dao.ezs_orderformMapper;
import com.sanbang.dao.ezs_payinfoMapper;
import com.sanbang.dao.ezs_purchase_orderformMapper;
import com.sanbang.utils.FilePathUtil;
import com.sanbang.utils.JsonListUtil;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.utils.httpclient.HttpRemoteRequestUtils;
import com.sanbang.utils.httpclient.HttpRequestParam;
import com.sanbang.vo.CertSignInfoBean;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.PagerOrder;
import com.sanbang.vo.userauth.AuthImageVo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("buyerService")
public class BuyerServiceimpl implements BuyerService {
	private Logger log =Logger.getLogger(BuyerServiceimpl.class);

	@Value("${config.sign.buyercallbackurl}")
	private String callbackurl;

	@Value("${config.sign.baseurl}")
	private String signbase;
	
	private String name="天津桑德贸易有限公司";
	private String bank="中国工商银行天津静海支行";
	private String banknum="0302 0951 0930 0310 174";
	
	@Autowired
	private ezs_orderformMapper ezs_orderformMapper;

	@Autowired
	private ezs_addressMapper ezs_addressMapper;

	@Autowired
	private ezs_areaMapper ezs_areaMapper;

	@Autowired
	private ezs_invoiceMapper ezs_invoiceMapper;
	
	@Autowired
	private ezs_logisticsMapper ezs_logisticsMapper;
	
	@Autowired
	private com.sanbang.dao.ezs_accessoryMapper ezs_accessoryMapper;
	
	@Autowired
	private ezs_payinfoMapper ezs_payinfoMapper;
	
	@Autowired
	private ezs_order_canceMapper ezs_order_canceMapper;
	
	@Autowired
	private AddressService addressService;
	
	
	@Autowired
	ezs_purchase_orderformMapper purchaseOrderformMapper;
	

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
	static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public List<ezs_order_info> getOrderListByValue(PagerOrder pager) {
		if(pager.getOrder_status().equals("10,20")){
			pager.setOrder_status("1,10,20");
		}
		//新订单流程增加  200待付款
		if(pager.getOrder_status().indexOf("201")>0) {
			pager.setOrder_status(pager.getOrder_status().replaceAll("201", "200"));
		}
		pager.setPageNow(pager.getPageNow() - 1);
		pager.setSecount(pager.getPageSize() * pager.getPageNow());
		int totalcount = ezs_orderformMapper.getOrderListByValueCount(pager);
		pager.setTotalCount(totalcount);
		return ezs_orderformMapper.getOrderListByValue(pager);
				
	}

	@Override
	public Map<String, Object> getOrderInfoShow(String order_no,ezs_user upi) {
		Map<String, Object> map = new HashMap<String, Object>();
		ezs_order_info orderinfo = ezs_orderformMapper.getOrderListByOrderno(order_no,upi.getId());

		// 收货地址处理
		StringBuffer sb=new StringBuffer();
		long addressid = orderinfo.getAddress_id();
		if(addressid>0){
			ezs_address  address=addressService.findAddressById(addressid);
			sb.append(getaddressinfo(address.getArea_id()));
			map.put("phone", address.getMobile());// 联系电话
			map.put("rname", address.getTrueName());// 联系人姓名
		}
		
		
		map.put("address", sb.toString());// 收货地址
		map.put("name", orderinfo.getName());//商品名称
		map.put("price", orderinfo.getPrice());//单价
		map.put("goods_amount", orderinfo.getGoods_amount());//数量
		map.put("order_no", orderinfo.getOrder_no());//订单号
		map.put("addTime", orderinfo.getAddTime());//下单时间
		map.put("order_status", orderinfo.getOrder_status());
		map.put("total_price", orderinfo.getTotal_price());//总价 
		map.put("ispg", orderinfo.getIspg());//是否为待评价 
		map.put("goodsid", orderinfo.getGoodsid());//是否为待评价 
		map.put("companyname", name);//卖方收款账户
		map.put("bank", bank);
		map.put("banknum", banknum);
		// 支付方式（0.全款，1：首款+尾款 ）
		if(orderinfo.getPay_mode()==1){
			//首付款
			if(orderinfo.getOrder_status()>=20&&orderinfo.getOrder_status()<80){
				map.put("paytype","首付款");
				map.put("yunfei", orderinfo.getEzs_logistics()==null?0:orderinfo.getEzs_logistics().getTotal_price());
				map.put("small_price", orderinfo.getFirst_price());
				map.put("pay_price", orderinfo.getFirst_price());
				map.put("prop", String.valueOf(orderinfo.getFirst_price().divide(orderinfo.getTotal_price(), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).doubleValue())+"%");
			//尾款	
			}else if(orderinfo.getOrder_status()>=80){
				map.put("paytype","尾款");
				map.put("yunfei", orderinfo.getEzs_logistics()==null?0:orderinfo.getEzs_logistics().getTotal_price());
				map.put("small_price", orderinfo.getEnd_price());
				map.put("pay_price", orderinfo.getEnd_price());
				map.put("prop", String.valueOf(orderinfo.getEnd_price().divide(orderinfo.getTotal_price(), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).doubleValue())+"%");
			}
		}else{
			//首付款
			map.put("paytype","全款");
			map.put("yunfei", orderinfo.getEzs_logistics()==null?0:(orderinfo.getEzs_logistics().getTotal_price()==null?0:orderinfo.getEzs_logistics().getTotal_price()));
			map.put("small_price", orderinfo.getTotal_price());
			map.put("pay_price", orderinfo.getTotal_price());
			map.put("prop", String.valueOf(100)+"%");
		}
		
		return map;
	}

	/**
	 * 地址
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
				twoinfo =  ezs_twoinfo.getAreaName();
				ezs_area ezs_oneinfo = ezs_areaMapper.selectByPrimaryKey(ezs_twoinfo.getParent_id());
				if (ezs_oneinfo != null) {
					oneinfo =  ezs_oneinfo.getAreaName();
				}
			}
		}
		
		if(!Tools.isEmpty(threeinfo)){
			sb = new StringBuilder().append(threeinfo);	
		}
		if(!Tools.isEmpty(twoinfo)){
			sb = new StringBuilder().append(twoinfo).append("-").append(threeinfo);
		}
		if(!Tools.isEmpty(oneinfo)){
			sb = new StringBuilder().append(oneinfo).append("-").append(twoinfo).append("-").append(threeinfo);
		}
		
		return sb.toString();
	}

	@Override
	public Result showOrderContent(HttpServletRequest request, String order_no,ezs_user upi) {
		Result result = Result.failure();
		try {


			if (Tools.isEmpty(order_no)) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("订单号不能为空");
				return result;
			}
			// 先查一下合同的pdf在不在
			//List<ezs_pact> pact = ezs_pactMapper.selectPactByOrderNo(order_no);
			ezs_order_info orderinfo = ezs_orderformMapper.getOrderListByOrderno(order_no,upi.getId());
			if(null==orderinfo){
				 orderinfo = purchaseOrderformMapper.getOrderListByOrderno(order_no);
			}
			
			if (null==orderinfo) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("订单不存在");
				return result;
			}
			/*if (pact == null||pact.size()==0) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("合同确认中，请稍后。如有疑问，咨询400-6666-890");
				return result;

			}*/
			if (orderinfo.getPact_status() == 1) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("抱歉，您签订的是纸质合同，无法通过APP在线查看。");
				return result;
			}
			String url = signbase+"/website/certSign/forh5/showcontentpdf.do";
			Map<String, Object> mv = new HashMap<>();

			mv.put("orderid", orderinfo.getOrder_no());
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
	public Result seller_order_signature(String order_no, HttpServletRequest request, HttpServletResponse response,ezs_user upi) {
		Result result = Result.failure();
		try {
			
			if (Tools.isEmpty(order_no)) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("订单号不能为空");
				return result;
			}

			ezs_order_info orderinfo = ezs_orderformMapper.getOrderListByOrderno(order_no,upi.getId());
			if (null == orderinfo) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("订单不存在");
				return result;
			}
			if (orderinfo.getOrder_status()!=30) {
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
				result.setMsg(res.getMessage());
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
	public Map<String, Object> orderconfirm(String order_no,ezs_user upi) {
		Map<String, Object> map = new HashMap<String, Object>();

		ezs_order_info orderinfo = ezs_orderformMapper.getOrderListByOrderno(order_no,upi.getId());

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
	public Result getContentList(String member, String temtype, int currentPage,HttpServletRequest request) {
		Result result=Result.failure();
		try {
			if (Tools.isEmpty(member)) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("商户编号不能为空");
				return result;
			}

			
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("number",member);
			map.put("temtype", temtype);
			map.put("pageno", currentPage);
			map.put("starttime", request.getParameter("starttime"));
			map.put("endtime", request.getParameter("endtime"));
			map.put("ordernoOrcontentno", request.getParameter("ordernoOrcontentno"));
			
			String url = signbase+"/website/certSign/forh5/contentForOut.do";
			HttpRequestParam httpParam = new HttpRequestParam();
			for (Entry<String, Object> en : map.entrySet()) {
				httpParam.addUrlParams(new BasicNameValuePair(en.getKey(), String.valueOf(en.getValue())));
			}
			JSONObject callBackRet = null;
			callBackRet = HttpRemoteRequestUtils.doPost(url, httpParam);
			com.sanbang.vo.sign.Result  res=(com.sanbang.vo.sign.Result) JSONObject.toBean(callBackRet,com.sanbang.vo.sign.Result.class);
			if (res.isSuccess()) {
				map.clear();
				List<CertSignInfoBean> list =new ArrayList<>();
				if(Tools.isEmpty(String.valueOf(res.getContent()))){
					map.put("list", list);
				}else{
					String content=String.valueOf(res.getContent());
					content=content.substring(1, content.length()-1);
					JSONArray array=JSONArray.fromObject(content);
					for (Object object : array) {
						JSONObject json=JSONObject.fromObject(object);
						CertSignInfoBean sign=new CertSignInfoBean();
						sign.setContentno(String.valueOf(json.get("contentno")));
						sign.setOrderid(String.valueOf(json.get("orderid")));
						sign.setSigncomFrout(String.valueOf(json.get("signcomFrout")));
						sign.setSignTime(DateFormatUtils.format(new Date(Long.valueOf(String.valueOf(json.get("signTime")))),"yyyy-MM-dd HH:mm:ss"));
						sign.setSignUrl(String.valueOf(json.get("signUrl")));
						sign.setStatus(Integer.valueOf(String.valueOf(json.get("status"))));
						list.add(sign);
					}
					map.put("list", list);
				}
				
				result.setSuccess(true);
				result.setMsg("请求成功 ");
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				result.setObj(map);
			} else {
				List<CertSignInfoBean> list =new ArrayList<>();
				map.put("list", list);
				result.setSuccess(true);
				result.setMsg("无合同数据");
				result.setObj(map);
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

	@Override
	public Result orderclose(HttpServletRequest request, String order_no,ezs_user upi) {
		Result result=Result.failure();

		String msg=request.getParameter("msg");
		String name=request.getParameter("name");
		if(Tools.isEmpty(msg)){
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("描述不能为空");
			return result;
		}
		//取消原因
		if(Tools.isEmpty(name)){
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请选择取消原因");
			return result;
		}
		
		
		ezs_orderform orderinfo = ezs_orderformMapper.selectByorderno(order_no);
		if(orderinfo==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("订单不存在");
			return result;
		}
		
		if(orderinfo.getOrder_status()>10){
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请在待签约之前进行关闭订单操作。");
			return result;
		}
		
		//取消订单记录
		ezs_order_cance cance=new ezs_order_cance();
		cance.setAddTime(new Date());
		cance.setAppscope("买方");
		cance.setDeleteStatus(true);
		cance.setMsg(msg);
		cance.setOrder_no(order_no);
		cance.setName(name);
		cance.setUser_id(upi.getId());
		ezs_order_canceMapper.insertSelective(cance);
		
		//修改订单状态
		orderinfo.setDeleteStatus(true);
		orderinfo.setOrder_status(130);
		ezs_orderformMapper.updateByPrimaryKey(orderinfo);
		result.setSuccess(true);
		result.setMsg("关闭订单成功");
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		
		
		return result;
	}

	//PAYIMG
	@Override
	@Transactional(isolation=Isolation.SERIALIZABLE,rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Result orderpaysubmit(HttpServletRequest request, String order_no,String urlParam,ezs_user upi) {
		
		Result result=Result.failure();
		
		try {
			
			ezs_order_info orderinfo = ezs_orderformMapper.getOrderListByOrderno(order_no,upi.getId());
			if(orderinfo==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("订单不存在");
				return result;
			}
			
			List<AuthImageVo> list=new ArrayList<>();
			savepic(urlParam, list);
			if(null==list||list.size()==0){
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("请上传图片");
				return result;
			}
			
			if(list.size()>1){
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("只能上传一张图片");
				return result;
			}
			
			//票据记录
			AuthImageVo vo=list.get(0);
			
			if(!vo.getImgcode().equals("PAYIMG")){
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("标识错误");
				return result;
			}
			
			//判断是否在支付状态
			List<ezs_payinfo>	payresc=ezs_payinfoMapper.getpayinfoByOrderno(order_no);
			if(orderinfo.getOrder_status()==40){
				if(payresc.size()==0){
					orderinfo.setOrder_status(50);
				}else{
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					// 支付方式（0.全款，1：首款+尾款 ）
					if(orderinfo.getPay_mode()==0){
						result.setMsg("您已上传过全款支付凭证,如有疑问请联系客服");
					}else{
						result.setMsg("您已上传过首款支付凭证,如有疑问请联系客服");	
					}
					return  result;
				}
				
			}else if(orderinfo.getOrder_status()==80){
				if(payresc.size()==1){
					orderinfo.setOrder_status(90);
				}else{
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("您已上传过尾款支付凭证,如有疑问请联系客服");
					return  result;
				}
			}else{
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("不在支付状态");
				return  result;
			}
			
			//添加图片记录
			ezs_accessory ezs_accessory=new com.sanbang.bean.ezs_accessory();
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
			//upi记录
			vo.setAccid(ezs_accessory.getId());
			
			    
			//票据记录
			ezs_payinfo payinfo=new ezs_payinfo();
			payinfo.setAddTime(new Date());
			payinfo.setPaymentUser_id(upi.getId());
			payinfo.setPrice(orderinfo.getFirst_price());
			payinfo.setDeleteStatus(false);
			payinfo.setOrder_no(order_no);
			 // 1.采购单，2.订单
			payinfo.setOrder_type(2);
			payinfo.setPay_no(Tools.getH5PayNO());//流水号
			payinfo.setBill_id(vo.getAccid());//票据id
			payinfo.setPaymentUser_id(orderinfo.getBuyerid());//支付人id
			payinfo.setReceUser_id(orderinfo.getSellerid());//收款人id 
			payinfo.setPay_mode(1);;
			ezs_payinfoMapper.insert(payinfo);
			
			
			
			ezs_orderform orde = ezs_orderformMapper.selectByorderno(order_no);
			ezs_orderform  order=new ezs_orderform();
			order.setOrder_no(orderinfo.getOrder_no());
			order.setOrder_status(orderinfo.getOrder_status());
			order.setId(orde.getId());
			ezs_orderformMapper.updateByPrimaryKeySelective(order);
			result.setSuccess(true);
			result.setMsg("上传成功");
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			//result.setObj(payinfo);//方便修改
			return result;
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			e.printStackTrace();
		}
		return result;
	}

	
	private static List<AuthImageVo> savepic(String param,List<AuthImageVo> list) throws ParseException{
		if(!Tools.isEmpty(param)){
		String[] aa=param.split(";");
		if(null==aa||aa.length==0){
			return list;
		}
		for (String bb : aa) {
			String[] cc=bb.split(",");
			if(null==cc||cc.length<2){
				return list;
			}
			AuthImageVo ImageVo=new AuthImageVo();
			ImageVo.setImgcode(cc[0]);
			
			if(cc[1].indexOf("@")>0&&cc[1].split("@").length==3){
				ImageVo.setImgurl(cc[1].split("@")[0]);
				ImageVo.setName(cc[1].split("@")[1]);
				ImageVo.setUsetime(sdf.parse(cc[1].split("@")[2]));
				list.add(ImageVo);
				System.out.println(ImageVo.toString());
			}else{
				ImageVo.setImgurl(cc[1].split("@")[0]);
				list.add(ImageVo);
				System.out.println(ImageVo.toString());
			}
		}
		}
		return list;
	}

	@Override
	public Result getezs_invoice(HttpServletRequest request, String order_no,ezs_user upi) {
		Result result = Result.failure();
		try {
			
			if (Tools.isEmpty(order_no)) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("参数错误");
				return result;
			}
	
			com.sanbang.bean.ezs_invoice ezs_invoice =	ezs_invoiceMapper.selectInvoiceByOrderNo(order_no);
			
			if (null == ezs_invoice) {
				ezs_invoice=new com.sanbang.bean.ezs_invoice();
				result.setSuccess(false);
				result.setMsg("暂无发票信息");
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setObj(ezs_invoice);
				return result;
			}else{
				if(null!=ezs_invoice.getReceipt_id()&&ezs_invoice.getReceipt_id()>0){
					ezs_accessory accessory=ezs_accessoryMapper.selectByPrimaryKey(ezs_invoice.getReceipt_id());
					ezs_invoice.setPicurl(accessory.getPath());
				}
				result.setSuccess(true);
				result.setMsg("请求成功");
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				result.setObj(ezs_invoice);
				return result;
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
	public Result getezs_logistics(HttpServletRequest request, String order_no,ezs_user upi) {
		Result result = Result.failure();
		try {
			
			if (Tools.isEmpty(order_no)) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("参数错误");
				return result;
			}
	
			ezs_logistics ezs_logistics =	ezs_logisticsMapper.selectByOrderNo(order_no);

			Map<String, Object> logs=new HashMap<>();
			if (null == ezs_logistics) {
				result.setSuccess(false);
				result.setMsg("暂无物流信息");
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setObj(logs);
				return result;
			}else{
				//判断物流方式
				if(!Tools.isEmpty(ezs_logistics.getCar_no())){
					//物流
					logs.put("logtype", 1);
					logs.put("proples", Tools.isEmpty(ezs_logistics.getProples())?"":ezs_logistics.getProples());
					logs.put("car_no", Tools.isEmpty(ezs_logistics.getCar_no())?"":ezs_logistics.getCar_no());
					logs.put("phone", Tools.isEmpty(ezs_logistics.getPhone())?"":ezs_logistics.getPhone());
					logs.put("service_time", ezs_logistics.getService_time()==null?"":sdf1.format(ezs_logistics.getService_time()));
					logs.put("logistics_name", "");
					logs.put("logistics_no", "");
				}else{
					//快递
					logs.put("logistics_name", Tools.isEmpty(ezs_logistics.getLogistics_name())?"":ezs_logistics.getLogistics_name());
					logs.put("logistics_no",Tools.isEmpty(ezs_logistics.getLogistics_no())?"": ezs_logistics.getLogistics_no());
					logs.put("proples", "");
					logs.put("car_no", "");
					logs.put("phone", "");
					logs.put("service_time", "");
					logs.put("logtype", 0);
					
				}
				result.setSuccess(true);
				result.setMsg("请求成功");
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				result.setObj(logs);
				return result;
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
	public Result payconfirm(HttpServletRequest request, String order_no,ezs_user upi) {
		Result result = Result.failure();
		ezs_order_info orderinfo = ezs_orderformMapper.getOrderListByOrderno(order_no,upi.getId());
		if (orderinfo == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("订单不存在");
			return result;
		}

		// 判断是否在待支付状态 40 80
		if (orderinfo.getOrder_status() == 40 || orderinfo.getOrder_status() == 80||orderinfo.getOrder_status()==200||orderinfo.getOrder_status()==201) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(true);
			result.setMsg("请求成功");
		} else {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("此订单不在支付状态");
			return result;
		}
		return result;
	}
	
	public static void main(String[] args) {
		String aa="[{\"signcomFrout\":\"sign0001\",\"signUrl\":\"http://127.0.0.1:8080/file/cert/temp0\\2018\\05\\f96f77af-558a-46e7-9900-e30839e21d38\\1527237148079.pdf\",\"contentno\":\"四十四\",\"orderid\":\"test001\",\"signTime\":1527240253000}]";
		
		List<CertSignInfoBean> list=JsonListUtil.jsonToList(aa, CertSignInfoBean.class);
		
	
	}

}
