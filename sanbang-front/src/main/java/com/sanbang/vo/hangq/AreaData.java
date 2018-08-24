package com.sanbang.vo.hangq;


public class AreaData {

	
	public  static String area1="1:华东@上海市,福建省,浙江省,安徽省,江苏省,山东省";
	public  static String area2="2:华南@广东省,广西壮族自治区,海南省";
	public  static String area3="3:华北@内蒙古自治区,天津市,山西省,河北省,北京市";
	public  static String area4="4:华中@湖南省,湖北省,河南省,江西省";
	public  static String area5="5:西北@新疆维吾尔自治区,青海省,宁夏回族自治区,甘肃省,陕西省";
	public  static String area6="6:西南@西藏自治区,重庆市,四川省,贵州市,云南省";
	public  static String area7="7:东北@辽宁省,吉林省,黑龙江省,香港";
	
	
	
	public static String[] getareaName(String areaName) {
		String[] aa=new String[2];
		if(area1.indexOf(areaName)>0) {
			aa[0]=area1.split(":")[0];
			aa[1]=area1.split(":")[1].split("@")[0];
		}
		
		if(area2.indexOf(areaName)>0) {
			aa[0]=area2.split(":")[0];
			aa[1]=area2.split(":")[1].split("@")[0];
		}
		
		if(area3.indexOf(areaName)>0) {
			aa[0]=area3.split(":")[0];
			aa[1]=area3.split(":")[1].split("@")[0];
		}
		if(area4.indexOf(areaName)>0) {
			aa[0]=area4.split(":")[0];
			aa[1]=area4.split(":")[1].split("@")[0];
		}
		if(area5.indexOf(areaName)>0) {
			aa[0]=area5.split(":")[0];
			aa[1]=area5.split(":")[1].split("@")[0];
		}
		if(area1.indexOf(areaName)>0) {
			aa[0]=area6.split(":")[0];
			aa[1]=area6.split(":")[1].split("@")[0];
		}
		
		if(area7.indexOf(areaName)>0) {
			aa[0]=area7.split(":")[0];
			aa[1]=area7.split(":")[1].split("@")[0];
		}
		return aa; 
	}
	
	public static void main(String[] args) {
		System.out.println(getareaName("上海市")[0]+getareaName("上海市")[1]);
	}
}
