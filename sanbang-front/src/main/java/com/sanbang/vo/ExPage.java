package com.sanbang.vo;

import com.sanbang.utils.Page;

public class ExPage extends Page {
	
	private String content;
	
	private String[] idsArray;
	
	private Long ecId;
	
	

	public Long getEcId() {
		return ecId;
	}

	public void setEcId(Long ecId) {
		this.ecId = ecId;
	}

	public String[] getIdsArray() {
		return idsArray;
	}

	public void setIdsArray(String[] idsArray) {
		this.idsArray = idsArray;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public ExPage(int totalCount, int pageNow){
		super(totalCount, pageNow);
	}

	public ExPage(int pageNow, int pageSize, int totalCount, int totalPageCount, int startPos, boolean hasFirst,
			boolean hasPre, boolean hasNext, boolean hasLast, String content) {
		super(pageNow, pageSize, totalCount, totalPageCount, startPos, hasFirst, hasPre, hasNext, hasLast);
		this.content = content;
	}

	public ExPage(int pageNow, int pageSize, int totalCount, int totalPageCount, int startPos, boolean hasFirst,
			boolean hasPre, boolean hasNext, boolean hasLast) {
		super(pageNow, pageSize, totalCount, totalPageCount, startPos, hasFirst, hasPre, hasNext, hasLast);
	}	
}
