package com.sanbang.utils;

import java.util.Date;

import org.apache.log4j.Logger;

import com.sanbang.bean.ezs_goods_log;

public class GoodsLogUtil {

	private  static Logger log=Logger.getLogger(GoodsLogUtil.class);
	
	/**
	 * 商品操作记录
	 * @param goodsid
	 * @param userid
	 * @param remake
	 * @return
	 */
	public static ezs_goods_log goodsLog(long goodsid,long userid,String remake){
		ezs_goods_log  goodslog=new ezs_goods_log();
		goodslog.setAddTime(new Date());
		goodslog.setDeleteStatus(false);
		goodslog.setGoodsId(goodsid);
		goodslog.setActionId(Integer.valueOf(String.valueOf(userid)));
		goodslog.setRemarks(remake);
		goodslog.setUser_id(userid);
		log.info("商品操作记录"+goodslog.toString());
		return goodslog;
	}
}
