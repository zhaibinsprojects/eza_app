package com.sanbang.vo;

import java.util.List;

import com.sanbang.bean.ezs_ezssubstance;

public class HangqHomeMess {
	//实时报价  再生类 新料类
	private List<GoodClassType> baojia;
	//价格趋势
	private List<PriceInTimesVo> priceTrend;
	//价格评析、研究报告 
	private List<ReportType> reportList;
	//广告
	private List<Advices> adviceList;
	//小易头条
	private List<ezs_ezssubstance> touTiaoList;
	
	public List<ezs_ezssubstance> getTouTiaoList() {
		return touTiaoList;
	}
	public void setTouTiaoList(List<ezs_ezssubstance> touTiaoList) {
		this.touTiaoList = touTiaoList;
	}
	public List<Advices> getAdviceList() {
		return adviceList;
	}
	public void setAdviceList(List<Advices> adviceList) {
		this.adviceList = adviceList;
	}
	public List<PriceInTimesVo> getPriceTrend() {
		return priceTrend;
	}
	public void setPriceTrend(List<PriceInTimesVo> priceTrend) {
		this.priceTrend = priceTrend;
	}
	public List<GoodClassType> getBaojia() {
		return baojia;
	}
	public void setBaojia(List<GoodClassType> baojia) {
		this.baojia = baojia;
	}
	public List<ReportType> getReportList() {
		return reportList;
	}
	public void setReportList(List<ReportType> reportList) {
		this.reportList = reportList;
	}
}
