package com.sanbang.vo;

import java.util.List;

import com.sanbang.bean.ezs_ezssubstance;
import com.sanbang.utils.Page;
/**
 * 用以封装首页展示相关内容
 * @author zhaibin
 */
public class HomePageMessInfo {
	//优品推荐商品列表
	private List<GoodsInfo> goodsInfoList;
	//行情分析信息列表
	private List<ezs_ezssubstance> ezssubstanceList;
	//分页信息
	private Page page;
	//广告列表
	private List<Advices> advicesList;

	public List<Advices> getAdvicesList() {
		return advicesList;
	}

	public void setAdvicesList(List<Advices> advicesList) {
		this.advicesList = advicesList;
	}

	public List<GoodsInfo> getGoodsInfoList() {
		return goodsInfoList;
	}

	public void setGoodsInfoList(List<GoodsInfo> goodsInfoList) {
		this.goodsInfoList = goodsInfoList;
	}

	public List<ezs_ezssubstance> getEzssubstanceList() {
		return ezssubstanceList;
	}

	public void setEzssubstanceList(List<ezs_ezssubstance> ezssubstanceList) {
		this.ezssubstanceList = ezssubstanceList;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public HomePageMessInfo() {
		super();
	}
}
