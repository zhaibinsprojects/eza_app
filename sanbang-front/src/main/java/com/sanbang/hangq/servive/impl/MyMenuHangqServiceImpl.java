package com.sanbang.hangq.servive.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanbang.bean.ezs_customizedhq;
import com.sanbang.bean.ezs_documentshare;
import com.sanbang.bean.ezs_memberorder;
import com.sanbang.bean.ezs_subscribehq;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_customizedhqMapper;
import com.sanbang.dao.ezs_documentshareMapper;
import com.sanbang.dao.ezs_memberorderMapper;
import com.sanbang.dao.ezs_subscribehqMapper;
import com.sanbang.hangq.servive.MyMenuHangqService;
import com.sanbang.utils.DateUtils;
import com.sanbang.utils.Page;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.goods.GoodsClassVo;
import com.sanbang.vo.userauth.AuthImageVo;

@Service("myMenuHangqService")
public class MyMenuHangqServiceImpl implements MyMenuHangqService{

	@Autowired
	private ezs_subscribehqMapper ezs_subscribehqMapper;
	@Autowired
	private  ezs_documentshareMapper ezs_documentshareMapper;
	@Autowired
	private com.sanbang.dao.ezs_goods_classVoMapper ezs_goods_classVoMapper;
	@Autowired
	private ezs_memberorderMapper  ezs_memberorderMapper;
	@Autowired
	private ezs_customizedhqMapper ezs_customizedhqMapper;
	
	
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
	static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public Result myDingyueListShow(ezs_user upi, HttpServletRequest request, Result result,int pageNo) {
		List<ezs_subscribehq> list=ezs_subscribehqMapper.getDingyueRecoudList(upi.getId(),(pageNo<0?1:pageNo-1)*10,10);
		int count=ezs_subscribehqMapper.getDingyueRecoudCount(upi.getId());
		Map<String, Object> map=new HashMap<>();
		map.put("dylist", list);
		result.setMsg("请求成功");
		result.setSuccess(true);
		result.setObj(map);
		result.setMeta(new Page(count, 10));
		return result;
	}

	@Override
	public Result myShoucListShow(ezs_user upi, HttpServletRequest request, Result result,int pageNo) {
		Map<String, Object> map=new HashMap<>();
		List<ezs_documentshare> list=ezs_documentshareMapper.selectHangqCollectionedOwen(upi.getId(),(pageNo<0?1:pageNo-1)*10,10);
		int count=ezs_documentshareMapper.selectHangqCollectionedCountOwen(upi.getId());
		map.put("hqced", list);
		result.setMsg("请求成功");
		result.setSuccess(true);
		result.setObj(map);
		result.setMeta(new Page(count, 10));
		return result;
	}

	@Override
	public Result myDingzhiListShow(ezs_user upi, HttpServletRequest request, Result result,boolean ispush,int pageNo) {
		Map<String, Object> map=new HashMap<>(); 
		List<ezs_customizedhq> list= ezs_customizedhqMapper.getDingZhiListByParam(upi.getId(), ispush, pageNo<0?1:pageNo, 10);
		int count= ezs_customizedhqMapper.getDingZhiListByParamCount(upi.getId(), ispush);
		map.put("dzlist", list);
		result.setMsg("请求成功");
		result.setSuccess(true);
		result.setObj(map);
		result.setMeta(new Page(count, 10));
		return result;
	}

	@Override
	public Result myDingyueInfoShow(ezs_user upi, HttpServletRequest request, Result result,long id) {
		Map<String, Object>  map=new HashMap<>();
		ezs_subscribehq subscribehq=ezs_subscribehqMapper.selectByPrimaryKey(id);
		if(null==subscribehq||subscribehq.getStore_id()!=upi.getStore_id()) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("非法请求");
			return result;
		}
		
		try {
			map.put("id",subscribehq.getId());
			if(subscribehq.getSubType()==0) {
				
				map.put("title", "价格行情综合服务(试用)");
				map.put("cycle", subscribehq.getCycle()+"天");
				map.put("paymode", subscribehq.getPaymode());
				map.put("payment", subscribehq.getPayment());
				map.put("opentime", DateUtils.getFormattedString(subscribehq.getAddTime(), "yyyy-MM-dd"));
				Calendar c=Calendar.getInstance();
				c.setTime(subscribehq.getAddTime());
				c.add(Integer.valueOf(subscribehq.getCycle()),Calendar.MONTH);
				
				map.put("timecycle", "预计开通时间为"+DateUtils.getFormattedString(new Date(), "yyyy-MM-dd")+"-"
				+DateUtils.getFormattedString(subscribehq.getAddTime(), "yyyy-MM-dd")+"-"+c.getTime());
				
				map.put("openmode", subscribehq.getOpenmode());
				List<GoodsClassVo>  list=ezs_goods_classVoMapper.gethanqParentClassCheck(upi.getId());
						map.put("cata", list);
				result.setObj(map);
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				result.setSuccess(true);
				return result;
			}
			
			map.put("title", "价格行情综合服务（购买）");
			map.put("cycle", subscribehq.getCycle()+"个月");
			map.put("paymode", subscribehq.getPaymode());
			map.put("payment", subscribehq.getPayment());
			
			
			//是否支付
			if(null!=subscribehq.getMemberorder()) {
				//是否开通
				if(subscribehq.getMemberorder().getOpenStatu()==1) {
					//已支付已开通
					map.put("opentime", DateUtils.getFormattedString(subscribehq.getMemberorder().getStartTime(), "yyyy-MM-dd"));
					map.put("timecycle", subscribehq.getMemberorder().getStartTime()+"-"+subscribehq.getMemberorder().getEndTime());
					map.put("openmode", subscribehq.getOpenmode());
				}else {
					//已支付未开通
					map.put("opentime", DateUtils.getFormattedString(new Date(), "yyyy-MM-dd"));
					Calendar c=Calendar.getInstance();
					c.setTime(new Date());
					c.add(Integer.valueOf(subscribehq.getCycle()),Calendar.MONTH);
					map.put("timecycle", "预计开通时间为"+DateUtils.getFormattedString(new Date(), "yyyy-MM-dd")+"-"
					+DateUtils.getFormattedString(new Date(), "yyyy-MM-dd")+"-"+c.getTime());
					map.put("openmode", 2);
				}
				
			}else {
				//未支付未开通
				map.put("opentime", subscribehq.getCycle()+"个月");
				Calendar c=Calendar.getInstance();
				c.setTime(new Date());
				c.add(Integer.valueOf(subscribehq.getCycle()),Calendar.MONTH);
				map.put("timecycle", "预计开通时间为"+DateUtils.getFormattedString(new Date(), "yyyy-MM-dd")+"-"
						+DateUtils.getFormattedString(new Date(), "yyyy-MM-dd")+"-"+c.getTime());
						map.put("openmode", 2);
				map.put("openmode", subscribehq.getOpenmode());
			}
			
			List<GoodsClassVo>  list=ezs_goods_classVoMapper.gethanqParentClassCheckAll();
			map.put("cata", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setSuccess(true);
		result.setObj(map);
		return result;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Result saveDingyuePic(HttpServletRequest request, ezs_user upi, long id,String urlParam, Result result) {
		try {
			ezs_subscribehq  recoed=ezs_subscribehqMapper.selectByPrimaryKey(id);
			if(null==recoed) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("订阅记录不存在");
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
			
			if(!vo.getImgcode().equals("HQPAY")){
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("标识错误");
				return result;
			}
			
			
			ezs_memberorder order=new ezs_memberorder();
			//查看是否有支付记录
			if(recoed.getBuyLog_id()==null||recoed.getBuyLog_id()<(long)1) {
					order=new ezs_memberorder();
					order.setAddTime(new Date());
					order.setCreditUsed(Integer.valueOf(String.valueOf(upi.getId())));
					order.setDeleteStatus(false);
					order.setMemberType("价格行情");
					order.setOpenStatu(0);
					order.setOperator(upi.getTrueName());
					order.setOrder_no(Tools.getHangqOrderNO());
					order.setOrderSource("APP");
					order.setPayAmount(recoed.getTotalMoney());
					order.setPayMode(1);
					order.setPayState(1);
					order.setStartTime(new Date());
					order.setStore_id(upi.getStore_id());
					order.setVoucher(vo.getImgurl());
				int st=	ezs_memberorderMapper.insertSelective(order);
				recoed.setBuyLog_id(order.getId());
				ezs_subscribehqMapper.updateByPrimaryKeySelective(recoed);
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(true);
				result.setMsg("你的支付记录已提交,请耐心等待客服审核！");
			}else {
				//查看已有支付记录是否正确
				   order=ezs_memberorderMapper.selectByPrimaryKey(recoed.getBuyLog_id());
				if(order==null||(!order.getDeleteStatus())) {
					order=new ezs_memberorder();
					order.setAddTime(new Date());
					order.setCreditUsed(Integer.valueOf(String.valueOf(upi.getId())));
					order.setDeleteStatus(false);
					order.setMemberType("价格行情");
					order.setOpenStatu(0);
					order.setOperator(upi.getTrueName());
					order.setOrder_no(Tools.getHangqOrderNO());
					order.setOrderSource("APP");
					order.setPayAmount(recoed.getTotalMoney());
					order.setPayMode(1);
					order.setPayState(1);
					order.setStartTime(new Date());
					order.setStore_id(upi.getStore_id());
					order.setVoucher(vo.getImgurl());
				int st=	ezs_memberorderMapper.insertSelective(order);
				recoed.setBuyLog_id(order.getId());
				ezs_subscribehqMapper.updateByPrimaryKeySelective(recoed);
				
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(true);
				result.setMsg("你的支付记录已提交,请耐心等待客服审核！");
				}else {
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("您已上传过支付凭证！");
				}
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
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

