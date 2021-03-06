package com.sanbang.hangq.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_column;
import com.sanbang.bean.ezs_ezssubstance;
import com.sanbang.bean.ezs_user;
import com.sanbang.hangq.servive.HangqAreaService;
import com.sanbang.hangq.servive.MyMenuHangqService;
import com.sanbang.redis.RedisConstants;
import com.sanbang.redis.RedisResult;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.RedisUtils;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;

@Controller
@RequestMapping("/app/menuhq")
public class MyMenuHanqController {

	@Autowired
	private MyMenuHangqService myMenuHangqService;
	@Autowired
	private HangqAreaService hangqAreaService;
	
	private static Logger log = Logger.getLogger(HomeHangqIndexController.class);
	
	/**
	 * 行情定制数据标识
	 */
	private  static final String HANGQ_DATA="DINGZHIINIT001";
	
	private static String view="/hangqv2/";
	
	
	/**
	 * 我的订阅
	 * @param request
	 * @return
	 */
	@RequestMapping("/myDingYue")
	@ResponseBody
	public Result myDingyueShow(HttpServletRequest request,@RequestParam(defaultValue="1",name="pageNo")int pageNo) {
		Result result=Result.failure();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			result=myMenuHangqService.myDingyueListShow(upi, request, result,pageNo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		
		return result;
	}
	
	/**
	 * 订阅详情查看
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDingyueInfo")
	@ResponseBody
	public  Result getDingyueInfo(@RequestParam("id") long id,
			HttpServletRequest request) {
		Result result=Result.failure();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			result=myMenuHangqService.myDingyueInfoShow(upi, request, result,id);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		
		return result;
	}
	
	/**
	 * 我的收藏
	 * @param request
	 * @return
	 */
	@RequestMapping("/myCollected")
	@ResponseBody
	public Result myCollected(HttpServletRequest request,@RequestParam(defaultValue="1",name="pageNo")int pageNo) {
		Result result=Result.failure();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			result=myMenuHangqService.myShoucListShow(upi, request, result,pageNo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		
		return result;
	}
	
	
	
	/**
	  * 订阅支付
	 * @param request   HQPAY
	 * @return
	 */
	@RequestMapping("/DingyuePay")
	@ResponseBody
	public Result saveDingyueOrder(HttpServletRequest request,
			@RequestParam(defaultValue="",name="id")int id,
			@RequestParam(defaultValue="",name="urlParam")String urlParam) {
		Result result=Result.failure();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			result=myMenuHangqService.saveDingyuePic(request, upi, id, urlParam, result);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		
		return result;
	}
	
	
	/**
	 * 我的定制
	 * @param request
	 * @return
	 */
	@RequestMapping("/myDingZhi")
	@ResponseBody
	public Result myDingzhiListShow(HttpServletRequest request,
			@RequestParam(defaultValue="1",name="pageNo")int pageNo,
			@RequestParam(defaultValue="true",name="isuse")boolean isuse) {
		Result result=Result.failure();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			result=myMenuHangqService.myDingzhiListShow(upi, request, result, isuse, pageNo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		
		return result;
	}
	
	
	
	
	/**
	 * 我要定阅初始化
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/myDingYueInit")
	@ResponseBody
	public Result myDingYueInit(HttpServletRequest request) {

		Result result = Result.failure();
		result.setMsg("请求失败");
		Map<String, Object> map=new HashMap<>();
 		try {
 			RedisResult<Result> redate = (RedisResult<Result>) RedisUtils.get(HANGQ_DATA,
 					Result.class);
 			if (redate.getCode() == RedisConstants.SUCCESS) {
				log.debug("查询redis分类成功执行");
				result=redate.getResult();
			} else {
					log.debug("查询redis分类执行失败");
					map=hangqAreaService.getHangqParamDate("all", map);
					result.setSuccess(true);
			 		result.setMsg("请求成功");
			 		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			 		result.setObj(map);
			 		
			 		RedisUtils.get(HANGQ_DATA, Result.class);
					RedisResult<String> rrt;
					rrt = (RedisResult<String>) RedisUtils.set(HANGQ_DATA, result,
						Long.valueOf(3600*24));
					if (rrt.getCode() == RedisConstants.SUCCESS) {
						log.debug("行情分类保存到redis成功执行");
					} else {
						log.debug("行情分类保存到redis失败");
					}
			}
 			if(result.getSuccess()) {
 				Map<String, Object> map1=(Map<String, Object>) result.getObj();
 				result.setObj(map1.get("cata"));
 			}
 			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
			result.setObj(map);
		}
 		
		return result;
	}
	
	/**
	 * 提交订阅订单
	 * @param request
	 * @return
	 */
	@RequestMapping("/myDingYueAdd")
	@ResponseBody
	public Result myDingYueAdd(HttpServletRequest request,
			@RequestParam(defaultValue="",name="cycle")String cycle,
			@RequestParam(defaultValue="0",name="payment")BigDecimal payment,
			@RequestParam(defaultValue="",name="subtotal")String subtotal,
			@RequestParam(defaultValue="0",name="isall")int isall) {
		Result result=Result.failure();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			result=myMenuHangqService.myDingYueAdd(request, upi, cycle, payment, subtotal,isall, result);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		
		return result;
	}
	
	
	/**
	 * 我要定制初始化
	 * @param request
	 * @return
	 */
	@RequestMapping("/myDingZhiInit")
	@ResponseBody
	public Result myDingZhiInit(HttpServletRequest request) {
		Result result=Result.failure();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			result=myMenuHangqService.myDingZhi(upi, request, result);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		
		return result;
	}
	
	
	/**
	 * 我的定制是否推送
	 * @param request
	 * @return
	 */
	@RequestMapping("/myDingZhiIsPush")
	@ResponseBody
	public Result myDingZhiIsPush(HttpServletRequest request,
			@RequestParam(defaultValue="0",name="id")int id,
			@RequestParam(defaultValue="true",name="ispush")boolean ispush) {
		
		log.info("我的定制是否推送请求====》id:"+id+"ispush:"+ispush);
		Result result=Result.failure();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			result=myMenuHangqService.myDingZhiIsPush(upi, request, result, ispush, id);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		
		return result;
	}
	
	
	/**
	 * 我的定制添加
	 * @param request
	 * @return
	 */
	@RequestMapping("/myDingZhiSubmit")
	@ResponseBody
	public Result myDingZhiSubmit(HttpServletRequest request,
			@RequestParam(defaultValue="",name="areaids")String areaids,
			@RequestParam(defaultValue="",name="category")String category,
			@RequestParam(defaultValue="",name="pushMethod")String pushMethod) {
		Result result=Result.failure();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			result=myMenuHangqService.myDingZhiSubmit(upi, request, result, areaids, category, pushMethod);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		
		return result;
	}
	
	
	/**
	 * 推送接口
	 * @param dzid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/HangqPushData")
	public Result HangqPushData(@RequestParam(defaultValue="0") long dzid) {
		Result result=Result.failure();
		try {
			result=myMenuHangqService.HangqPushData(dzid, result);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		return result;
	}
	
	/**
	 * 查看试用状态
	 * @param request
	 * @return
	 */
	@RequestMapping("/myTryStatus")
	@ResponseBody
	public Result myTryStatus(HttpServletRequest request) {
		Result result=Result.failure();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			result=myMenuHangqService.getDingYueStatusByUserid(upi, request, result);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		
		return result;
	}
	
	
	/**
	 * pay pic
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDingyuePayPic")
	@ResponseBody
	public  Result getDingyuePayPic(@RequestParam("id") long id,
			HttpServletRequest request) {
		Result result=Result.failure();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			result=myMenuHangqService.getDingyuePayPic(request, upi, id, result);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		
		return result;
	}
	
	
	/**
	 * 提交订阅订单
	 * @param request
	 * @return
	 */
	@RequestMapping("/myDingYueTryAdd")
	@ResponseBody
	public Result myDingYueAdd(HttpServletRequest request) {
		Result result=Result.failure();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			result=myMenuHangqService.myDingYueTryAdd(request, upi, result);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		
		return result;
	}
	
	/**
	  * 订阅提交订单
	 * @param request   
	 * @return
	 */
	@RequestMapping("/submitOrder")
	@ResponseBody
	public Result submitOrder(HttpServletRequest request,
			@RequestParam(defaultValue="",name="recoedid")int recoedid,
			@RequestParam(defaultValue="1",name="paymode")int paymode) {
		Result result=Result.failure();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			result=myMenuHangqService.submitOrder(request, upi, recoedid, paymode, result);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		
		return result;
	}
	
	
	/**
	  * 订阅提交订单
	 * @param request   
	 * @return
	 */
	@RequestMapping("/getDocStatusForUser")
	@ResponseBody
	public Result getDocStatusForUser(HttpServletRequest request,
			@RequestParam(defaultValue="",name="docid")int docid
			) {
		Result result=Result.failure();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			result=myMenuHangqService.getDocStatusForUser(request, upi, docid, result);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		
		return result;
	}
	
	
	/**
	  * 点赞 /收藏
	  *give;// 文章的状态,0表示初始化，1表示点赞，2表示踩
	  * house;// 收藏状态 0表示未收藏1收藏
	 * @param request   
	 * @return
	 */
	@RequestMapping("/doGiveOrHouse")
	@ResponseBody
	public Result doGiveOrHouse(HttpServletRequest request,
			@RequestParam(defaultValue="",name="docid")int docid,
			@RequestParam(defaultValue="",name="reqtype")String reqtype
			) {
		Result result=Result.failure();
		try {
			ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
			if(upi==null){
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			result=myMenuHangqService.doGiveOrHouse(request, upi, docid, result, reqtype);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		
		return result;
	}
	
	/**
	 * 查看文章详情
	 * @param id
	 * @param catid
	 * @return
	 */
	@RequestMapping("/hangqShow")
	public String hangqgShow(@RequestParam(name="id",required=true)long id,
			Model model){
		myMenuHangqService.hangqgShow(id, model);
		return view+"infoshow";
	}
	
}
