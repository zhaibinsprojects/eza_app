package com.sanbang.utils;

public class TenderConstant {
	//权限
	public static enum Status {

		ISSIGN(1, "是否可以报名"), ISPAY(2, "是否可以支付"), ISQUOTE(3, "是否开标");

		private final int value;
		private final String content;

		private Status(int value, String content) {
			this.value = value;
			this.content = content;
		}

		public int v() {
			return value;
		}

		public String c() {
			return content;
		}
	}
	
	//支付保障金状态
	public static enum MarginStatus {

		NOTPAY(0, "未支付"), ISPAY(1, "已支付"), REFUND(2, "是已退款");

		private final int value;
		private final String content;

		private MarginStatus(int value, String content) {
			this.value = value;
			this.content = content;
		}

		public int v() {
			return value;
		}

		public String c() {
			return content;
		}
	}
	
	//报名流程
	public static enum SignStatus {

		NOSTATUS(0, "无状态"), SIGN(1, "报名"), CERT(2, "提交资质"),LOOKGOODS(3,"申请看货"),PAYMARGIN(4,"支付保证金"),QUOTE(5,"提交报价"),BINGO(6,"发布中标"),PAYAMOUNT(7,"支付货款");

		private final int value;
		private final String content;

		private SignStatus(int value, String content) {
			this.value = value;
			this.content = content;
		}

		public int v() {
			return value;
		}

		public String c() {
			return content;
		}
	}
	
	public static enum SendMsg{
		
		TITLE(0, "易再生网");

		private final int value;
		private final String content;

		private SendMsg(int value, String content) {
			this.value = value;
			this.content = content;
		}

		public int v() {
			return value;
		}

		public String c() {
			return content;
		}

	}

}
