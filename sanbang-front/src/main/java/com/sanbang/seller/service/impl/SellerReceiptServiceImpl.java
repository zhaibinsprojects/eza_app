package com.sanbang.seller.service.impl;

import java.text.DateFormat;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanbang.advice.service.CommonOrderAdvice;
import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_bill;
import com.sanbang.bean.ezs_invoice;
import com.sanbang.bean.ezs_order_info;
import com.sanbang.bean.ezs_payinfo;
import com.sanbang.bean.ezs_purchase_orderform;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_accessoryMapper;
import com.sanbang.dao.ezs_billMapper;
import com.sanbang.dao.ezs_invoiceMapper;
import com.sanbang.dao.ezs_payinfoMapper;
import com.sanbang.dao.ezs_purchase_orderformMapper;
import com.sanbang.dao.ezs_userMapper;
import com.sanbang.seller.service.SellerReceiptService;
import com.sanbang.utils.CommUtil;
import com.sanbang.utils.FilePathUtil;
import com.sanbang.utils.Page;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.HomeDictionaryCode;
import com.sanbang.vo.userauth.AuthImageVo;

@Service
public class SellerReceiptServiceImpl implements SellerReceiptService {

	// 日志
	private static Logger log = Logger.getLogger(SellerReceiptServiceImpl.class.getName());

	@Autowired
	ezs_billMapper billMapper;

	@Autowired
	ezs_invoiceMapper invoiceMapper;

	@Autowired
	ezs_payinfoMapper payinfoMapper;

	@Autowired
	ezs_userMapper userMapper;
	
	@Autowired
	ezs_accessoryMapper accessoryMapper;
	
	@Autowired
	ezs_purchase_orderformMapper purchaseOrderformMapper;
	
	@Autowired
	private com.sanbang.dao.ezs_accessoryMapper ezs_accessoryMapper;
	
	@Autowired
	private CommonOrderAdvice commonOrderAdvice;
	
	private static DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public ezs_bill getBillInfoById(Long userId) {

		return billMapper.selectByPrimaryKey(userId);
	}

	@Override
	public Map<String, Object> getInvoiceListById(Long userId, String currentPage,int type) {
		Map<String, Object> mmp = new HashMap<>();
		// 获取总页数
		int totalCount = this.invoiceMapper.getInvoiceCountByUserId(userId,type);
		Page page = new Page(totalCount, Integer.valueOf(currentPage));
		page.setPageSize(10);
		int startPos = 0;
		page.setStartPos(startPos);
		List<ezs_invoice> glist=new ArrayList<>();
		if ((Integer.valueOf(currentPage)>=1&&Integer.valueOf(currentPage)<=page.getTotalPageCount())||(page.getTotalPageCount()==0)) {
			 glist = invoiceMapper.goodsInvoiceCountPage(page, userId,type);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Page", page);
			mmp.put("Obj", glist);
		} else {
			mmp.put("ErrorCode", HomeDictionaryCode.ERROR_HOME_PAGE_FAIL);
			mmp.put("Msg", "暂无数据");
			mmp.put("Page", page);
			mmp.put("Obj", glist);
		}
		return mmp;
	}

	@Override
	public ezs_payinfo getPayInfoById(Long billId) {
		return payinfoMapper.selectByBillId(billId);
	}

	@Override
	public ezs_user getUserInfoById(Long paymentUser_id) {

		return userMapper.selectByPrimaryKey(paymentUser_id);
	}

	@Override
	public Map<String, Object> queryInvoiceByIdOrDate(Result result, String orderno, String startTime, String endTime,
			long userId, String currentPage,int type) {
		log.info("条件查询合同**************************");
		Map<String, Object> mmp = new HashMap<>();
		List<ezs_invoice> list = new ArrayList<>();
		// 获取总页数
		int totalCount = invoiceMapper.getInvoiceCountByUserId(userId,type);

		Page page = new Page(totalCount, Integer.valueOf(currentPage));
		page.setPageSize(10);
		if ((Integer.valueOf(currentPage)>=1&&Integer.valueOf(currentPage)<=page.getTotalPageCount())||(page.getTotalPageCount()==0)) {
			if (Tools.notEmpty(startTime) && Tools.notEmpty(endTime)) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date dt1;
				Date dt2;
				try {
					dt1 = df.parse(startTime);
					dt2 = df.parse(endTime);
					if (dt1.getTime() > dt2.getTime()) {
						result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
						result.setMsg("日期格式错误");
					}
					list = invoiceMapper.selectInvoiceByDate(dt1, dt2, userId, page);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				mmp.put("Page", page);
				mmp.put("Obj", list);
			} else {
				ezs_invoice invoice = invoiceMapper.selectInvoiceByOrderNo(orderno);
				mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				mmp.put("Page", page);
				mmp.put("Obj", invoice);
			}
		} else {
			mmp.put("ErrorCode", HomeDictionaryCode.ERROR_HOME_PAGE_FAIL);
			mmp.put("Msg", "暂无数据");
			mmp.put("Page", page);
		}
		return mmp;
	}	

	@Override
	public ezs_invoice queryInvoiceByNo(String orderNo) {
		
		return invoiceMapper.selectInvoiceByOrderNo(orderNo);
	}

	@Override
	public ezs_accessory queryAccessoryById(Long receipt_id) {
		
		return accessoryMapper.selectByPrimaryKey(receipt_id);
	}


	@Override
	public ezs_invoice getInvoiceInfoById(String orderno) {
		return invoiceMapper.selectInvoiceByOrderNo(orderno);
	}
	
	public int insertInvoice(ezs_invoice invoice){
		
		
		return invoiceMapper.insert(invoice);
	}

	//INVIMG
	@Override
	@Transactional(isolation=Isolation.SERIALIZABLE,rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Result orderivosubmit(HttpServletRequest request, String order_no, String urlParam,ezs_user upi) {
		
		Result result=Result.failure();
		
		try {
			
			ezs_order_info orderinfo = purchaseOrderformMapper.getOrderListByOrderno(order_no);
			if(orderinfo==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("订单不存在");
				return result;
			}
			
			String  express_name=request.getParameter("express_name");
			String  express_no=request.getParameter("express_no");
			String  express_time=request.getParameter("express_time");
			
			if(Tools.isEmpty(express_name)){
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("邮寄方式不能为空");
				return result;
			}
			if(Tools.isEmpty(express_no)){
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("快递号不能为空");
				return result;
			}
			if(Tools.isEmpty(express_time)){
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("开票时间不能为空");
				return result;
			}
			
			try {
					sdf.parse(express_time);
			} catch (Exception e) {
				e.printStackTrace();
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("开票时间格式不正确");
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
			
			if(!vo.getImgcode().equals("INVIMG")){
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("标识错误");
				return result;
			}
			
			//判断是否在支付状态
			ezs_invoice	payresc=invoiceMapper.selectInvoiceByOrderNo(order_no);
			if(orderinfo.getOrder_status()==70){
				if(null==payresc){
					orderinfo.setOrder_status(80);
				}else{
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("您已上传过发票,如有疑问请联系客服");
					return  result;
				}
				
			}else{
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("不在上传票据状态");
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
			ezs_invoice ezs_invoice=new ezs_invoice();
			ezs_invoice.setAddTime(new Date());
			ezs_invoice.setDeleteStatus(false);
			ezs_invoice.setExpress_name(express_name);
			ezs_invoice.setExpress_no(express_no);
			ezs_invoice.setExpress_time(sdf.parse(express_time));
			ezs_invoice.setFlag(2);
			ezs_invoice.setInvoice_status(2);
			ezs_invoice.setOrder_no(order_no);
			ezs_invoice.setReceipt_id(vo.getAccid());
			ezs_invoice.setUser_id(upi.getId());
			ezs_invoice.setApply_no(Tools.getApplyNo());
			int status1=invoiceMapper.insertSelective(ezs_invoice);
			
			ezs_purchase_orderform  order=new ezs_purchase_orderform();
			order.setOrder_no(orderinfo.getOrder_no());
			order.setOrder_status(orderinfo.getOrder_status());
			order.setId(orderinfo.getOrderid());
			int status=purchaseOrderformMapper.updateByPrimaryKeySelective(order);
			result.setSuccess(true);
			result.setMsg("上传成功");
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			//result.setObj(payinfo);//方便修改
			//wemall回调
			if(result.getSuccess()){
				commonOrderAdvice.returnOrderAdvice(order_no, "");
			}
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
}
