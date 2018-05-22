package com.sanbang.seller.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_invoice;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_accessoryMapper;
import com.sanbang.seller.service.SellerReceiptService;
import com.sanbang.upload.sevice.FileUploadService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;

@Controller
@RequestMapping("/seller")
public class SellerReceiptController {
	
	@Autowired
	SellerReceiptService sellerReceiptService;
	
	@Autowired
	ezs_accessoryMapper accessoryMapper;
	
	@Resource(name="fileUploadService")
	private FileUploadService fileUploadService;
	/**
	 * 票据管理页面
	 * @param userId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getReceiptInfo")
	public Object getReceiptInfo(HttpServletRequest request, HttpServletResponse response){
		
		Map<String, Object> map = new HashMap<>();
		
		Result result=Result.failure();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		Long userId = upi.getId();

//		ezs_user seller = new ezs_user();
//		seller.setId((long) 2);
//		Long userId = seller.getId();

		if (userId != null && String.valueOf(userId) != "") {
			List<ezs_invoice> invoice = sellerReceiptService.getInvoiceListInfoById(userId);
			result.setErrorcode(DictionaryCode.ERROR_WEB_QUERY_BILL_INFO_SUCCESS);
			result.setSuccess(true);
			result.setObj(invoice);
		}else{
			result.setErrorcode(DictionaryCode.ERROR_WEB_QUERY_BILL_INFO_FAIL);
			result.setSuccess(false);
			return result;
		}
		
		return result;
	}
	
	/**
	 * 根据订单编号和时间查询发票
	 * @param orderno
	 * @param startTime
	 * @param endTime
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryInvoiceByIdOrDate")
	public Object queryInvoiceByIdOrDate(String orderno, String startTime,String endTime,HttpServletRequest request, HttpServletResponse response){
		Result result=Result.failure();
		Object object = sellerReceiptService.queryInvoiceByIdOrDate(result, orderno, startTime, endTime, request, response);
		return object;
	} 
	
//	/**
//	 * 根据id
//	 * @param byerId
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("/getByerBillInfoById")
//	public Object getByerBillInfoById(String byerId, HttpServletRequest request, HttpServletResponse response){
//		Map<String, Object> map = new HashMap<>();
//		
//		Result result=Result.failure();
//		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
//		if(upi==null){
//			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
//			result.setMsg("用户未登录");
//			return result;
//		}
//		
//		Long userId = upi.getId();
//		ezs_bill bill = sellerReceiptService.getBillInfoById(userId);
//		Long billId = bill.getId();
//		ezs_payinfo payInfo = sellerReceiptService.getPayInfoById(billId);
//		Long paymentUser_id = payInfo.getPaymentUser_id();
//		
//		ezs_user buyer = sellerReceiptService.getUserInfoById(paymentUser_id);
//		
//		
//		String companyName = bill.getCompanyName();
//		String dutyNo = bill.getDutyNo();
//		String address = bill.getAddress();
//		String phone = bill.getPhone();
//		String bank = bill.getBank();
//		String number = bill.getNumber();
//		
//		String name = buyer.getName();
//		String moblie = buyer.getPhone();
//		Long position_id = buyer.getPosition_id();
//		
//		map.put("companyName", companyName);
//		map.put("dutyNo", dutyNo);
//		map.put("address", address);
//		map.put("phone", phone);
//		map.put("bank", bank);
//		map.put("number", number);
//		map.put("name", name);
//		map.put("moblie", moblie);
//		map.put("position_id", position_id);
//
//		return map;
//	}
	/**
	 * 根据订单编号查询票据详情
	 * @param invoiceId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getBuyerInvoiceInfoById")
	public Object getBuyerInvoiceInfoById(String orderno, HttpServletRequest request, HttpServletResponse response){
		
		Map<String, Object> map = new HashMap<>();
		
		Result result=Result.failure();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		ezs_invoice invoice = sellerReceiptService.getInvoiceInfoById(orderno);
		
		Long id = invoice.getId();
		Date express_time = invoice.getExpress_time();
		String express_name = invoice.getExpress_name();
		String express_no = invoice.getExpress_no();
		Integer invoice_status = invoice.getInvoice_status();
		Long receipt_id = invoice.getReceipt_id();
		
		ezs_accessory accessory = accessoryMapper.selectByPrimaryKey(receipt_id);
		String path = accessory.getPath();
		
		map.put("id", id);
		map.put("express_time", express_time);
		map.put("express_name", express_name);
		map.put("express_no", express_no);
		map.put("invoice_status", invoice_status);
		map.put("path", path);
		
		result.setObj(map);
		return result;
	}
	
	
	/**
	 * 其实前台直接调用FileUploadController这个控制层就可以
	 * @param request
	 * @param response
	 * @return
	 */
	public Result uploadFile(HttpServletRequest request, HttpServletResponse response){
		Result result = Result.failure();
		//调用文件上传方法，并返回上传图片路径
		try {
			Map<String , Object> map=fileUploadService.uploadFile(request,0,0,10*1024*1024l);
			if("000".equals(map.get("code"))){
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				result.setMsg("上传成功");
				result.setObj(new HashMap<>().put("url", map.get("url")));	//前台从这个map中获取图片保存路径（前台处用a标签指向这里或者text标签，然后从这里取数据）
				result.setSuccess(true);
			}else{
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("上传失败");
				result.setObj("");
				result.setSuccess(false);
			}
		} catch (Exception e) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("文件：上传接口调用失败"+e.toString());
			result.setObj("");
			result.setSuccess(false);
		}
		return result;
	}
	/**
	 * 插入票据信息
	 * @param request
	 * @return
	 */
	public Result insertInvoice(HttpServletRequest request){
		Result result = Result.failure();
		ezs_invoice invoice = new ezs_invoice();
		//下面这些暂时是伪代码
		invoice.setAddTime(new Date());	//添加时间
		invoice.setDeleteStatus(false); //删除状态
		invoice.setExpress_time(new Date());  //邮寄时间
		invoice.setExpress_no("邮寄单号");	
		invoice.setExpress_name("邮寄方式");
		invoice.setReceipt_id(new Long(1));
		int n = sellerReceiptService.insertInvoice(invoice);
		if(n>0){
			result.setMsg("添加成功");
		}else{
			result.setMsg("添加失败");
		}
		return result;
	}
	
	
}
