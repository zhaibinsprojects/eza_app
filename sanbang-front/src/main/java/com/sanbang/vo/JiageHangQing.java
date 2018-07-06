package com.sanbang.vo;

import java.util.List;

import com.sanbang.bean.ezs_ezssubstance;

public class JiageHangQing {
	public List<ezs_ezssubstance> baogao;
	public List<ezs_ezssubstance> jiagehq;
	public List<PriceTrendIfo> jiageqs;
	public List<PriceTrendIfo> priceIntime;
	
	public List<ezs_ezssubstance> getBaogao() {
		return baogao;
	}
	public void setBaogao(List<ezs_ezssubstance> baogao) {
		this.baogao = baogao;
	}
	public List<ezs_ezssubstance> getJiagehq() {
		return jiagehq;
	}
	public void setJiagehq(List<ezs_ezssubstance> jiagehq) {
		this.jiagehq = jiagehq;
	}
	public List<PriceTrendIfo> getJiageqs() {
		return jiageqs;
	}
	public void setJiageqs(List<PriceTrendIfo> jiageqs) {
		this.jiageqs = jiageqs;
	}
	public List<PriceTrendIfo> getPriceIntime() {
		return priceIntime;
	}
	public void setPriceIntime(List<PriceTrendIfo> priceIntime) {
		this.priceIntime = priceIntime;
	}
	

}
