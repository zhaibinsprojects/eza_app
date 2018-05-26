package com.sanbang.buyer.service.impl;

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

import org.apache.commons.collections.MapUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.ibatis.builder.ResultMapResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_address;
import com.sanbang.bean.ezs_area;
import com.sanbang.bean.ezs_logistics;
import com.sanbang.bean.ezs_order_cance;
import com.sanbang.bean.ezs_order_info;
import com.sanbang.bean.ezs_orderform;
import com.sanbang.bean.ezs_pact;
import com.sanbang.bean.ezs_payinfo;
import com.sanbang.bean.ezs_user;
import com.sanbang.buyer.service.BuyerService;
import com.sanbang.dao.ezs_addressMapper;
import com.sanbang.dao.ezs_areaMapper;
import com.sanbang.dao.ezs_invoiceMapper;
import com.sanbang.dao.ezs_logisticsMapper;
import com.sanbang.dao.ezs_order_canceMapper;
import com.sanbang.dao.ezs_orderformMapper;
import com.sanbang.dao.ezs_pactMapper;
import com.sanbang.dao.ezs_payinfoMapper;
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
import com.sanbang.vo.userauth.AuthImageVo;

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

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");

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
		map.put("name", orderinfo.getName());//商品名称
		map.put("price", orderinfo.getPrice());//单价
		map.put("goods_amount", orderinfo.getGoods_amount());//数量
		map.put("order_no", orderinfo.getOrder_no());//订单号
		map.put("addTime", orderinfo.getAddTime());//下单时间
		map.put("order_status", orderinfo.getOrder_status());
		map.put("total_price", orderinfo.getTotal_price());//总价 
		
		// 支付方式（0.全款，1：首款+尾款 ）
		if(orderinfo.getPay_mode()==1){
			//首付款
			if(orderinfo.getOrder_status()==40){
				map.put("paytype","首付款");
				map.put("yunfei", orderinfo.getEzs_logistics().getTotal_price());
				map.put("small_price", orderinfo.getFirst_price());
				map.put("pay_price", orderinfo.getFirst_price());
			//尾款	
			}else if(orderinfo.getOrder_status()==80){
				map.put("paytype","尾款");
				map.put("yunfei", orderinfo.getEzs_logistics().getTotal_price());
				map.put("small_price", orderinfo.getEnd_price());
				map.put("pay_price", orderinfo.getEnd_price());
			}
		}else{
			//首付款
			map.put("paytype","全款");
			map.put("yunfei", orderinfo.getEzs_logistics().getTotal_price());
			map.put("small_price", orderinfo.getTotal_price());
			map.put("pay_price", orderinfo.getTotal_price());
		}
		
		return map;
	}

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
			ezs_order_info orderinfo = ezs_orderformMapper.getOrderListByOrderno(order_no);
			
			if (pact == null||pact.size()==0) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("合同确认中，请稍后。如有疑问，咨询400-6666-890");
				return result;

			}
			if (orderinfo.getPact_status() == 1) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("纸质合同线下签订中，请您耐心等待！");
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
	public Result seller_order_signature(String order_no, HttpServletRequest request, HttpServletResponse response) {
		Result result = Result.failure();
		try {

			ezs_user upi = RedisUserSession.getLoginUserInfo(request);
			if (upi == null) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			Long sellerId = upi.getId();
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
			
			// 先查一下合同的pdf在不在
			List<ezs_pact> pact = ezs_pactMapper.selectPactByOrderNo(order_no);

			if (pact == null||pact.size()==0) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("合同确认中，请稍后。如有疑问，咨询400-6666-890");
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
			com.sanbang.vo.sign.Result  res=(com.sanbang.vo.sign.Result) JSONObject.toBean(callBackRet,com.sanbang.vo.sign.Result.class);
			if (res.isSuccess()) {
				map.clear();
				map.put("pdfurl", res.getContent());
				result.setSuccess(true);
				result.setMsg("请求成功 ");
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				result.setObj(map);
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

	@Override
	public Result orderclose(HttpServletRequest request, String order_no) {
		Result result=Result.failure();
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if (upi == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}

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
		
		result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
		result.setSuccess(false);
		result.setMsg("订单不存在");
		
		ezs_orderform orderinfo = ezs_orderformMapper.selectByorderno(order_no);
		if(orderinfo==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("订单不存在");
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
		int status=ezs_orderformMapper.updateByPrimaryKey(orderinfo);
		result.setSuccess(true);
		result.setMsg("修改成功");
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		return result;
	}

	//PAYIMG
	@Override
	@Transactional(isolation=Isolation.SERIALIZABLE,rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Result orderpaysubmit(HttpServletRequest request, String order_no,String urlParam) {
		
		Result result=Result.failure();
		
		try {
			ezs_user upi = RedisUserSession.getLoginUserInfo(request);
			if (upi == null) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			
			ezs_order_info orderinfo = ezs_orderformMapper.getOrderListByOrderno(order_no);
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
			
			//添加图片记录
			ezs_accessory ezs_accessory=new com.sanbang.bean.ezs_accessory();
			ezs_accessory.setAddTime(new Date());
			ezs_accessory.setDeleteStatus(false);
			ezs_accessory.setExt("");
			ezs_accessory.setHeight(0);
			ezs_accessory.setInfo(null);
			ezs_accessory.setName("");
			ezs_accessory.setPath(vo.getImgurl());
			ezs_accessory.setSize(null);
			ezs_accessory.setWidth(null);
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
			payinfo.setOrder_type(2);
			payinfo.setPay_no(Tools.getH5PayNO());//流水号
			payinfo.setBill_id(vo.getAccid());//票据id
			payinfo.setPaymentUser_id(orderinfo.getBuyerid());//支付人id
			payinfo.setReceUser_id(orderinfo.getSellerid());//收款人id 
			int status1=ezs_payinfoMapper.insert(payinfo);
			
			orderinfo.setOrder_status(40);
			ezs_orderform  order=new ezs_orderform();
			order.setOrder_no(orderinfo.getOrder_no());
			order.setOrder_status(orderinfo.getOrder_status());
			int status=ezs_orderformMapper.updateByPrimaryKey(order);
			result.setSuccess(true);
			result.setMsg("提交成功");
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setObj(payinfo);//方便修改
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
	public Result getezs_invoice(HttpServletRequest request, String order_no) {
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
				result.setMsg("参数错误");
				return result;
			}
	
			com.sanbang.bean.ezs_invoice ezs_invoice =	ezs_invoiceMapper.selectInvoiceByOrderNo(order_no);

			if (null == ezs_invoice) {
				result.setSuccess(false);
				result.setMsg("暂无发票信息");
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				return result;
			}else{
				result.setSuccess(false);
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
	public Result getezs_logistics(HttpServletRequest request, String order_no) {
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
				result.setMsg("参数错误");
				return result;
			}
	
			ezs_logistics ezs_logistics =	ezs_logisticsMapper.selectByOrderNo(order_no);

			if (null == ezs_logistics) {
				result.setSuccess(false);
				result.setMsg("暂无物流信息");
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				return result;
			}else{
				result.setSuccess(false);
				result.setMsg("请求成功");
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				result.setObj(ezs_logistics);
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
	public Result payconfirm(HttpServletRequest request, String order_no) {
		Result result = Result.failure();
		ezs_order_info orderinfo = ezs_orderformMapper.getOrderListByOrderno(order_no);
		if (orderinfo == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("订单不存在");
			return result;
		}

		// 判断是否在待支付状态 40 80
		if (orderinfo.getSc_status() == 40 || orderinfo.getOrder_status() == 80) {
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

}
