package com.sanbang.index.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.area.service.AreaService;
import com.sanbang.bean.ezs_financial_service_loans;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_financial_service_loansMapper;
import com.sanbang.dict.service.DictService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCate;
import com.sanbang.vo.DictionaryCode;

@Controller
@RequestMapping("/app/home/loan")
public class HomeLoanIndex {

	@Autowired
	private AreaService areaService;
	@Autowired
	private DictService dictService;
	@Autowired
	private ezs_financial_service_loansMapper ezs_financial_service_loansMapper;

	private static String view = "/loan/";

	/**
	 * 供应链金融展示
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index")
	public String getUserMess(HttpServletRequest request, HttpServletResponse response, Model model) {
		return view + "index";
	}

	/**
	 * 供应链金融展示
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/loadalert")
	public String loadalert(HttpServletRequest request, HttpServletResponse response, Model model,String userkey) {
		// 地址
		model.addAttribute("area", areaService.getAreaParentList());
		// 公司类型
		model.addAttribute("comtype", dictService.getDictByParentId(DictionaryCate.EZS_COMPANYTYPE));
		model.addAttribute("userkey", userkey);
		return view + "loadalert";
	}

	/**
	 * 供应链金融申请提交
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/loansubmit")
	@ResponseBody
	public Result loansubmit(HttpServletRequest request, HttpServletResponse response, Model model, String companyName,
			String contacts, String telNum, String email, String address, int applyType, int mainBusiness, Long area_id,
			String loanAmount) {
		Result result = Result.failure();
		result.setMsg("提交失败");
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if (upi == null) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				result.setSuccess(false);
				return result;
			}
			result=sbmloanValidate(companyName, contacts, telNum, email, address, applyType, area_id, mainBusiness, loanAmount);
			if(!result.getSuccess()){
				return result;
			}
			ezs_financial_service_loans financialServiceLoans = new ezs_financial_service_loans();
			financialServiceLoans.setApplyType(String.valueOf(applyType));
			financialServiceLoans.setCompanyName(companyName);
			financialServiceLoans.setContacts(contacts);
			financialServiceLoans.setTelNum(telNum);
			financialServiceLoans.setArea_id(area_id);
			financialServiceLoans.setAddress(address);
			financialServiceLoans.setUser_id(upi.getId());
			financialServiceLoans.setAddTime(new Date());
			financialServiceLoans.setEmail(email);
			financialServiceLoans.setStatus(1);
			financialServiceLoans.setMainBusiness(String.valueOf(mainBusiness));
			financialServiceLoans.setLoanAmount(new BigDecimal(loanAmount));
			financialServiceLoans.setDeleteStatus(false);
			ezs_financial_service_loansMapper.insertSelective(financialServiceLoans);

			// 添加申请编码
			String applyNo = "YZSFS" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			Long financialServiceId = financialServiceLoans.getId();
			applyNo += financialServiceId2str(financialServiceId);

			financialServiceLoans.setApplyNo(applyNo);
			ezs_financial_service_loansMapper.updateByPrimaryKeySelective(financialServiceLoans);
			// 可能有回调
			// TODO
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("您的申请已提交成功,我们的客服会尽快审核");
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误");
			result.setSuccess(false);
		}
		return result;
	}

	private String financialServiceId2str(Long financialId) {
		if (financialId < 10) {
			return "0000" + financialId;
		} else if (financialId < 100) {
			return "000" + financialId;
		} else if (financialId < 1000) {
			return "00" + financialId;
		} else if (financialId < 10000) {
			return "0" + financialId;
		} else {
			return "" + financialId;
		}
	}

	private Result sbmloanValidate(String companyName, String contacts, String telNum, String email, String address,
			int applyType, Long area_id, int mainBusiness, String loanAmount) {
		Result result = Result.failure();
		if (applyType == -1) {
			result.setMsg("请选择服务类型");
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			return result;
		}
		if (mainBusiness == -1) {
			result.setMsg("请选择经营类型");
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			return result;
		}
		if (Tools.isEmpty(companyName)) {
			result.setMsg("请输入企业名称");
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			return result;
		}
		if (Tools.isEmpty(contacts)) {
			result.setMsg("请输入联系人");
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			return result;
		}
		if (Tools.isEmpty(telNum)) {
			result.setMsg("请输入联系电话");
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			return result;
		}else{
			if (!Tools.isMobileAndPhone(telNum)) {
				result.setMsg("联系电话格式不正确");
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				return result;
			}
		}

		/*if (Tools.isEmpty(address)) {
			result.setMsg("请输入详细地址");
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			return result;
		}

		if (Tools.isEmpty(email)) {
			result.setMsg("请输入邮箱信息");
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			return result;
		}
		
		if (Tools.isEmpty(loanAmount)) {
			result.setMsg("请输入贷款金额");
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			return result;
		}else{
			if(!Tools.isNumber(loanAmount)){
				result.setMsg("请输入真确贷款金额");
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				return result;
			}
		}*/
		result.setSuccess(true);
		return result;
	}
	
	
	/**
	 * 供应链金融列表 app
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getloanslist")
	@ResponseBody
	public Result getloanslist(HttpServletRequest request,@RequestParam(name="pageNow" ,defaultValue="1")int pageNow) {
		Result result = Result.failure();
		result.setMsg("提交失败");
		List<ezs_financial_service_loans> list=new ArrayList<>();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if (upi == null) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				result.setSuccess(false);
				return result;
			}
			
			list=ezs_financial_service_loansMapper.selectLoanByUser(upi.getId(),(pageNow-1)*10,10);
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setMsg("查询成功");
			result.setSuccess(true);
			result.setObj(list);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误");
			result.setSuccess(false);
			result.setObj(list);
		}
		return result;
	}
	
	/**
	 * 供应链金融列表 h5
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getloanslistforH5")
	@ResponseBody
	public Result getloanslistforH5(HttpServletRequest request,@RequestParam(name="pageNow" ,defaultValue="1")int pageNow) {
		Result result = Result.failure();
		result.setMsg("提交失败");
		List<ezs_financial_service_loans> list=new ArrayList<>();
		try {
			ezs_user upi = RedisUserSession.getLoginUserInfo(request);
			if (upi == null) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				result.setSuccess(false);
				return result;
			}
			
			list=ezs_financial_service_loansMapper.selectLoanByUser(upi.getId(),(pageNow-1)*10,10);
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setMsg("查询成功");
			result.setSuccess(true);
			result.setObj(list);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误");
			result.setSuccess(false);
			result.setObj(list);
		}
		return result;
	}
	
	@RequestMapping("/getResultPage")
	public String getResultPage(HttpServletRequest request, HttpServletResponse response,boolean dealFlag ,Model model) {
		if(dealFlag)
			return "submit_success";
		else
			return "submit_faile";
	}
	
	
	/** 
     * @param args 
     */  
    public static void main(String[] args) {  
  
        String res = downloadFromUrl("http://www.ezaisheng.com/upload/ezsSubstance/fb1d4d45508145fd925165e5a62a1100_易再生废家电价格指数（第20期）.pdf","d:/");  
        System.out.println(res);  
    }  
  
  
    public static String downloadFromUrl(String url,String dir) {  
  
        try {  
            URL httpurl = new URL(url);  
            String fileName = getFileNameFromUrl(url);  
            System.out.println(fileName);  
            File f = new File(dir + fileName);  
            FileUtils.copyURLToFile(httpurl, f);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return "Fault!";  
        }   
        return "Successful!";  
    }  
      
    public static String getFileNameFromUrl(String url){  
        String name = new Long(System.currentTimeMillis()).toString() + ".X";  
        int index = url.lastIndexOf("/");  
        if(index > 0){  
            name = url.substring(index + 1);  
            if(name.trim().length()>0){  
                return name;  
            }  
        }  
        return name;  
    }

}
