package com.sanbang.app.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_dvaluate;
import com.sanbang.bean.ezs_user;
import com.sanbang.buyer.service.GoodsCollectionService;
import com.sanbang.buyer.service.GoodsInvoiceService;
import com.sanbang.buyer.service.OrderEvaluateService;
import com.sanbang.goods.service.GoodsService;
import com.sanbang.index.service.RecommendGoodsService;
import com.sanbang.utils.FilePathUtil;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.GoodsInfo;
import com.sanbang.vo.PriceTrendIfo;
import com.sanbang.vo.goods.GoodsVo;
import com.sanbang.vo.userauth.AuthImageVo;

/**
 * 
 * @author LENOVO
 *
 */
@Controller
@RequestMapping("/app/buy")
public class AppBuyCenterController {
	@Autowired
	private GoodsCollectionService goodsCollectionService;
	@Autowired
	private GoodsInvoiceService goodsInvoiceService;
	@Autowired
	private OrderEvaluateService orderEvaluateService;
	@Autowired
	private GoodsService goodsService;

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");

	private static final String view = "/goods/";

	@Autowired
	private RecommendGoodsService recommendGoodsService;
	/**
	 * 查询收藏夹下商品
	 * @param request
	 * @param response
	 * @param goodsName 商品名称
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectGoodByName")
	@ResponseBody
	public Object selectGoodByName(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="goodsName",required=true)String goodsName){
		Map<String, Object> mmp = null;
		List<GoodsInfo> glist = null;
		Result 	rs = Result.failure();
		ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
		if (upi == null) {
			rs.setSuccess(false);
			rs.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			rs.setObj(new ArrayList<>());
			rs.setMsg("用户未登录");
			return rs;
		}
		mmp = this.recommendGoodsService.getGoodsInCollectionByName(goodsName,upi.getId());
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			glist = (List<GoodsInfo>) mmp.get("Obj");
			rs.setSuccess(true);
			rs.setObj(glist);
			rs.setMsg("收藏列表查询成功！");
		}else{
			rs.setSuccess(false);
			rs.setObj(new ArrayList<>());
			rs.setMsg("查询失败！");
		}
		return rs;
	}
	/**
	 * 移除收藏夹商品
	 * 
	 * @param request
	 * @param response
	 * @param gId
	 * @return
	 */
	@RequestMapping("/removeFromCollection")
	@ResponseBody
	public Object removeFromCollection(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> mmp = null;
		Result rs = Result.failure();
		ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
		if (upi == null) {
			rs = Result.failure();
			rs.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			rs.setMsg("用户未登录");
			return rs;
		}
		String gId = request.getParameter("gIds");
		if (Tools.isEmpty(gId)) {
			rs.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			rs.setMsg("参数错误");
			return rs;
		}
		String[] gooids = gId.split(",");
		mmp = this.goodsCollectionService.removeGoodFromCollect(gooids, upi.getId());
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if (ErrorCode != null && ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)) {
			rs = Result.success();
			rs.setMsg(mmp.get("Msg").toString());
		} else {
			rs = Result.failure();
			rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
			rs.setMsg(mmp.get("Msg").toString());
		}
		return rs;
	}

	/**
	 * 获取收藏夹中该用户下的所有商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getGoodsFromCollection")
	@ResponseBody
	public Object getGoodsFromCollection(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> mmp = null;
		Result rs = null;
		List<Object> glist = null;
		ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
		if (upi == null) {
			rs = Result.failure();
			rs.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			rs.setMsg("用户未登录");
			return rs;
		}
		mmp = this.goodsCollectionService.getCollectGoodsByUser(upi.getId());
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if (ErrorCode != null && ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)) {
			glist = (List<Object>) mmp.get("Obj");
			rs = Result.success();
			rs.setObj(glist);
			rs.setMsg("");
		} else {
			rs = Result.failure();
			rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
			rs.setMsg("参数传递有误");
		}
		return rs;
	}

	/**
	 * 商品最近价格变化趋势
	 * 
	 * @param request
	 * @param response
	 * @param gId
	 *            商品ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/goodPriceChanges")
	@ResponseBody
	public Object goodPriceChanges(HttpServletRequest request, HttpServletResponse response, Long gId) {
		Map<String, Object> mmp = null;
		List<PriceTrendIfo> plist = null;
		Result rs = null;
		mmp = this.goodsCollectionService.selectPriceChanges(gId);
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if (ErrorCode != null && ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)) {
			plist = (List<PriceTrendIfo>) mmp.get("Obj");
			rs = Result.success();
			rs.setObj(plist);
		} else {
			rs = Result.failure();
			rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
			rs.setMsg(mmp.get("Msg").toString());
		}
		return rs;
	}

	/**
	 * 确认收票
	 * @param request
	 * @param response
	 * @param invoiceId
	 * @return
	 */
	@RequestMapping("/changeInvoiceStateByKey")
	@ResponseBody
	public Object changeInvoiceStateByKey(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "invoiceId", defaultValue = "-1") Long invoiceId) {
		Map<String, Object> mmp = null;
		Result rs = null;
		ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
		if (upi == null) {
			rs = Result.failure();
			rs.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			rs.setMsg("用户未登录");
			return rs;
		}
		mmp = this.goodsInvoiceService.changeInvoiceStateById(invoiceId);
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if (ErrorCode != null && ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)) {
			rs = Result.success();
			rs.setMsg("确认成功");
		} else {
			rs = Result.failure();
			rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
			rs.setMsg(mmp.get("Msg").toString());
		}
		return rs;
	}

	/**
	 * 评价列表
	 * 
	 * @param request
	 * @param pageNo
	 * @param goodsid
	 * @param model
	 * @return
	 */
	@RequestMapping("/getEvaluateList")
	public Object getEvaluateList(HttpServletRequest request,
			@RequestParam(name = "pageNo", defaultValue = "1") int pageNo, @RequestParam("goodsid") int goodsid,
			org.springframework.ui.Model model) {

		// 用户校验begin
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		long userid = 0;
		if (null == upi) {
			upi = RedisUserSession.getUserInfoByKeyForApp(request);
		}
		userid = null == upi ? 0 : upi.getId();
		model.addAttribute("userkey", null == upi ? "" : upi.getUserkey());
		// 用户校验end

		List<ezs_dvaluate> dvaluatelist = orderEvaluateService.getEvaluateList(pageNo, goodsid);
		model.addAttribute("dvaluatelist", dvaluatelist);
		GoodsVo goodsvo = goodsService.getgoodsinfo(goodsid, userid);
		model.addAttribute("good", goodsvo);
		return view + "evaluatelist";
	}

	/**
	 * 订单评价
	 * 
	 * @author zhaibin
	 * @param logistice
	 *            物流速度
	 * @param goodQuality
	 *            商品质量
	 * @param serviceQuality
	 *            服务态度
	 * @param orderNo
	 *            订单号
	 * @param goodId
	 *            商品Id
	 * @param path
	 *            图片路径
	 * @param imgName
	 *            图片名称(不支持多张图片)
	 * @return
	 */
	@RequestMapping("/evaluateAboutOrder")
	@ResponseBody
	public Object evaluateAboutOrder(HttpServletRequest request, HttpServletResponse response, Double logistice,
			Double goodQuality, Double serviceQuality, String orderNo, Long goodId, String content, String pinurl) {

		Map<String, Object> mmp = null;
		ezs_dvaluate dvaluate = new ezs_dvaluate();
		ezs_accessory accessory = null;
		Result rs = Result.failure();
		try {
			ezs_user user = RedisUserSession.getUserInfoByKeyForApp(request);
			if (user == null) {
				rs = Result.failure();
				rs.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				rs.setMsg("用户未登录");
				return rs;
			}
			// 参数设置
			if ((orderNo != null && !orderNo.trim().equals(""))) {
				dvaluate.setConttent(content);
				dvaluate.setLogistics(logistice);
				dvaluate.setGoodQuality(goodQuality);
				dvaluate.setServiceQuality(goodQuality);
				dvaluate.setOrder_no(orderNo);
			} else {
				rs = Result.failure();
				rs.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				rs.setMsg("订单号不能为NULL");
				return rs;
			}
			if (!Tools.isEmpty(pinurl)) {
				List<AuthImageVo> list = new ArrayList<>();
				savepic(pinurl, list);
				if (null == list || list.size() == 0) {
					rs.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					rs.setSuccess(false);
					rs.setMsg("请上传图片");
					return rs;
				}

				if (list.size() > 1) {
					rs.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					rs.setSuccess(false);
					rs.setMsg("只能上传一张图片");
					return rs;
				}

				// 票据记录
				AuthImageVo vo = list.get(0);

				if (!vo.getImgcode().equals("PINIMG")) {
					rs.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					rs.setSuccess(false);
					rs.setMsg("标识错误");
					return rs;
				}
				accessory = new ezs_accessory();
				accessory.setName(FilePathUtil.getimageName(vo.getImgurl()));
				accessory.setPath(FilePathUtil.getmiddelPath(vo.getImgurl()));
			}
			// 数据入库
			mmp = this.orderEvaluateService.orderEvaluate(dvaluate, accessory, user);
			Integer ErrorCode = (Integer) mmp.get("ErrorCode");
			if (ErrorCode != null && ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)) {
				rs = Result.success();
				rs.setMsg(mmp.get("Msg").toString());
			} else {
				rs = Result.failure();
				rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
				rs.setMsg(mmp.get("Msg").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setSuccess(false);
			rs.setMsg("系统错误");
			rs.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return rs;
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

}