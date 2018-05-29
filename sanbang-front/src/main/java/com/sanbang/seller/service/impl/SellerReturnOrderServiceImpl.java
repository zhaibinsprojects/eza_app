package com.sanbang.seller.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_return_attach;
import com.sanbang.bean.ezs_return_logistics;
import com.sanbang.bean.ezs_set_return_order;
import com.sanbang.dao.ezs_return_attachMapper;
import com.sanbang.dao.ezs_return_logisticsMapper;
import com.sanbang.seller.service.SellerReturnOrderService;
import com.sanbang.utils.Page;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.returnorder.ReturnOrderVO;
import com.sanbang.vo.userauth.AuthImageVo;

@Service("SellerReturnOrderService")
public class SellerReturnOrderServiceImpl implements SellerReturnOrderService {

	@Autowired
	private com.sanbang.dao.ezs_set_return_orderMapper ezs_set_return_orderMapper;

	@Autowired
	private ezs_return_logisticsMapper ezs_return_logisticsMapper;

	@Autowired
	private ezs_return_attachMapper ezs_return_attachMapper;

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");

	@Override
	public Result returnOrderList(int pageNo, long userid, int order_type, int state2) {
		Result result = Result.failure();
		try {
			if (pageNo <= 0) {
				pageNo = 1;
			}
			List<ReturnOrderVO> list = ezs_set_return_orderMapper.returnOrderListforSeller(userid, 10 * pageNo,
					order_type, state2);
			result.setMeta(new Page(pageNo, 10, 10 * pageNo, 10 * pageNo, 0, true, true, true, true));
			if (list.size() == 0) {
				result.getMeta().setHasFirst(false);
			}
			if (pageNo == 1) {
				result.getMeta().setHasPre(false);
			}
			result.setObj(list);
			result.setSuccess(true);
			result.setMsg("请求成功");
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return result;
	}

	@Override
	public Result returnOrderinfoforBuyer(long returnid) {
		Result result = Result.failure();
		try {
			ReturnOrderVO returninfo = ezs_set_return_orderMapper.returnOrderinfoforBuyer(returnid);
			result.setObj(returninfo);
			result.setSuccess(true);
			result.setMsg("请求成功");
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return result;
	}

	@Override
	public Result getreturnlogistics(long returnid) {
		Result result = Result.failure();
		try {
			ezs_return_logistics returnlogistics = ezs_return_logisticsMapper
					.selectReturnLogisticsForReturnNo(returnid);
			result.setObj(returnlogistics);
			result.setSuccess(true);
			result.setMsg("请求成功");
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return result;
	}

	@Override
	public Result submitReturnPayInfo(HttpServletRequest request, String params, long returnid) {
		Result result = Result.failure();
		try {
			ReturnOrderVO returninfo = ezs_set_return_orderMapper.returnOrderinfoforBuyer(returnid);
			if (null == returninfo) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("退货记录不存在！");
				return result;
			}

			List<AuthImageVo> list = new ArrayList<>();
			savepic(params, list);
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

			List<ezs_return_attach> hasreturnAttash = ezs_return_attachMapper.getEzsReturnAttachByReturnId(returnid);

			if (null != hasreturnAttash && hasreturnAttash.size() > 0) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("请勿重复提交");
				return result;
			}

			// 票据记录
			AuthImageVo vo = list.get(0);

			if (!vo.getImgcode().equals("RPAYIMG")) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("标识错误");
				return result;
			}

			ezs_return_attach returnatta = new ezs_return_attach();
			returnatta.setAddTime(new Date());
			returnatta.setDeleteStatus(false);
			returnatta.setFileName("RPAYIMG");
			returnatta.setRemark("RPAYIMG");
			returnatta.setSetorder_id(returninfo.getId());
			returnatta.setType(0);
			returnatta.setUrlpath(vo.getImgurl());
			ezs_return_attachMapper.insertSelective(returnatta);

			ezs_set_return_order returnorder = new ezs_set_return_order();
			returnorder.setId(returninfo.getId());
			returnorder.setState2("15");
			returnorder.setState1("15");
			ezs_set_return_orderMapper.updateByPrimaryKeySelective(returnorder);

		} catch (ParseException e) {
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
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
}
