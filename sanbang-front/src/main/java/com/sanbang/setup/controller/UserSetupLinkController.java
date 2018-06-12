package com.sanbang.setup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.app.controller.AppUserSetupLinkController;
import com.sanbang.bean.ezs_user;
import com.sanbang.dict.service.DictService;
import com.sanbang.userpro.service.UserProService;
import com.sanbang.utils.ImageUrlUtil;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCate;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.LinkUserVo;
import com.sanbang.vo.userauth.AuthImageVo;

@Controller
@RequestMapping("/setup/linkuser/")
public class UserSetupLinkController {

	private Logger log = Logger.getLogger(AppUserSetupLinkController.class);


	@Autowired
	private UserProService userProService;

	// 注册验证码标识
	@Value("${consparam.mobile.recode}")
	private String mobilerecode;

	// 验证码有效时间
	@Value("${consparam.mobile.sendcodeexpir}")
	private String mobilesendcodeexpir;

	// 验证码发送的间隔
	@Value("${consparam.mobile.interval}")
	private String mobileinterval;

	// #短信验证码发送的次数
	@Value("${consparam.mobile.sendtimes}")
	private String mobilesendtimes;

	@Autowired
	private DictService dictService;
	
	@Autowired
	private com.sanbang.setup.service.AuthService authService;

	/**
	 * 设置注册联系人资料
	 * 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/init")
	@ResponseBody
	public Object checkMobile(HttpServletRequest request) throws Exception {
		Result result = Result.failure();
		try {
			ezs_user upi = RedisUserSession.getLoginUserInfo(request);
			if (upi == null) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			Map<String, Object> map=new HashMap<>();
			if (null != upi.getEzs_userinfo()) {
				map.put("Link", new LinkUserVo(ImageUrlUtil.geturl(DictionaryCate.USER_ICON, upi.getAuthimg()), upi.getName(), upi.getEzs_userinfo().getPhone(),
						upi.getTrueName(), upi.getEzs_userinfo().getSex_id(),
						(upi.getEzs_userinfo().getSex_id() != null && upi.getEzs_userinfo().getSex_id() != 0)
								? dictService.getDictByThisId(upi.getEzs_userinfo().getSex_id()).getName() : "",

						upi.getEzs_userinfo().getPosition_id() != null ? upi.getEzs_userinfo().getPosition_id() : 0,

						(upi.getEzs_userinfo().getPosition_id() != null && upi.getEzs_userinfo().getPosition_id() != 0)
								? dictService.getDictByThisId(upi.getEzs_userinfo().getPosition_id()).getName() : "",

						upi.getEzs_userinfo().getTel(), upi.getEzs_userinfo().getPhone(),
						upi.getEzs_userinfo().getEmail(), upi.getEzs_userinfo().getQQ()));
			};
			
			//公司类型
			map.put("area", dictService.getDictByParentId(DictionaryCate.EZS_SEX));
			result.setObj(map);
			result.setSuccess(true);
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setMsg("请求成功");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
			e.printStackTrace();
		}

		return result;
	}

	
	
	/**
	 * 原来手机号发送验证码
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sendUpMoCode")
	public Result sendUpMoCode( @RequestParam(name = "mobile") String mobile,
			HttpServletRequest request) {
		Result result = Result.failure();
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if (upi == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
		
		//查看手机号是否修改过
		if(upi.getEzs_userinfo().getPhoneStatus()==1){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("已经修改过一次不可更改！");
			return result;
		}
		
		StringBuilder code = new StringBuilder();  
		Random random = new Random();  
		// 6位验证码  
		for (int i = 0; i < 6; i++) {  
			code.append(String.valueOf(random.nextInt(10)));  
		}
		try {
			result = userProService.sendUpMoCode(mobile, code.toString());
		} catch (Exception e) {
			log.info("h5设置个人资料" + upi.getName() + "错误" + e.toString());
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}
		return result;
	}

	/**
	 * 检验原来手机号验证码
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkUpMoCode")
	public Result checkUpMoCode(@RequestParam(name = "code") String code, @RequestParam(name = "mobile") String mobile,
			HttpServletRequest request) {
		Result result = Result.failure();

		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if (upi == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
		try {

			result = userProService.checkUpMoCode(mobile, code, upi, request);
		} catch (Exception e) {
			log.info("h5设置个人资料" + upi.getName() + "错误" + e.toString());
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}
		return result;
	}
	
	
	/**
	 * 修改手机号新手机号验证码
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sendToUpMoCode")
	public Result sendToUpMoCode( @RequestParam(name = "mobile") String mobile,
			HttpServletRequest request) {
		Result result = Result.failure();
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if (upi == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
		StringBuilder code = new StringBuilder();  
		Random random = new Random();  
		// 6位验证码  
		for (int i = 0; i < 6; i++) {  
			code.append(String.valueOf(random.nextInt(10)));  
		}
		try {
			result = userProService.sendToUpMoCode(mobile, code.toString());
		} catch (Exception e) {
			log.info("h5设置个人资料" + upi.getName() + "错误" + e.toString());
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}
		return result;
	}
	
	
	
	/**
	 * 修改登陆手机号为新手机号
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkToUpMoCode")
	public Result checkToUpMoCode(@RequestParam(name = "code") String code, @RequestParam(name = "mobile") String mobile,
			HttpServletRequest request) {
		Result result = Result.failure();

		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if (upi == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
		try {

			result = userProService.sendToUpMoPhoneCheck(mobile, code, upi, request);
		} catch (Exception e) {
			log.info("h5设置个人资料" + upi.getName() + "错误" + e.toString());
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}
		return result;
	}

	/**
	 * 修改其他信息
	 * 
	 * @param request
	 * @return typeval truename sex tel email qq  username
	 * 
	 */
	@ResponseBody
	@RequestMapping("/checkUp/{typeval}")
	public Result checkUpMoCode(@PathVariable("typeval") String typeval, @ModelAttribute LinkUserVo linkvo,
			HttpServletRequest request) {
		Result result = Result.failure();
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if (upi == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}

		try {
			result = userProService.upUserInfo(request, upi, typeval, linkvo);
			if (result.getSuccess()) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				result.setMsg("修改成功！");

			}
		} catch (Exception e) {
			log.info("h5设置个人资料" + upi.getName() + "错误" + e.toString());
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}
		return result;
	}
	
	
	
	/**
	 * 添加用户头像
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/headimg")
	public Result saveshouq(
			HttpServletRequest request,
			HttpServletResponse response){
		Result result=Result.failure();  
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
		result=authService.saveHeadPicUrl(result, request, upi, response);
		return result;
	}

	
	
	
}
