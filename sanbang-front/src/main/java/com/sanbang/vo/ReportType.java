package com.sanbang.vo;

import java.util.Date;
import java.util.List;

import com.sanbang.bean.ezs_ezssubstance;

public class ReportType {
	//文章类型：价格评析、研究报告
	private Long reportId;
	
	private String reportName;
	
	private Date date;
	//文章类型列表
	private List<ezs_ezssubstance> reportList1;
	
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<ezs_ezssubstance> getReportList1() {
		return reportList1;
	}
	public void setReportList1(List<ezs_ezssubstance> reportList1) {
		this.reportList1 = reportList1;
	}
}
