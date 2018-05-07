package com.sanbang.utils;

public class NumberChangeToChinese {

	 public String numberToChinese(int num){//转化一个阿拉伯数字为中文字符串  
	        if(num == 0){  
	            return "零";  
	        }  
	        int unitPos = 0;//节权位标识  
	        String All = new String();  
	        String chineseNum = new String();//中文数字字符串  
	        boolean needZero = false;//下一小结是否需要补零  
	        String strIns = new String();  
	        while(num>0){  
	            int section = num%10000;//取最后面的那一个小节  
	            if(needZero){//判断上一小节千位是否为零，为零就要加上零  
	                All = NumberTools.chnNumChar[0] + All;  
	            }  
	            chineseNum = sectionTOChinese(section,chineseNum);//处理当前小节的数字,然后用chineseNum记录当前小节数字  
	            if( section!=0 ){//此处用if else 选择语句来执行加节权位  
	                strIns = NumberTools.chnUnitSection[unitPos];//当小节不为0，就加上节权位  
	                chineseNum = chineseNum + strIns;  
	            }else{  
	                strIns = NumberTools.chnUnitSection[0];//否则不用加  
	                chineseNum = strIns + chineseNum;  
	            }  
	            All = chineseNum+All;  
	            chineseNum = "";  
	            needZero = (section<1000) && (section>0);  
	            num = num/10000;  
	            unitPos++;  
	        }  
	        return All;  
	    }  
	    public String sectionTOChinese(int section,String chineseNum){  
	        String setionChinese = new String();//小节部分用独立函数操作  
	        int unitPos = 0;//小节内部的权值计数器  
	        boolean zero = true;//小节内部的制零判断，每个小节内只能出现一个零  
	        while(section>0){  
	            int v = section%10;//取当前最末位的值  
	            if(v == 0){  
	                if( !zero ){  
	                    zero = true;//需要补零的操作，确保对连续多个零只是输出一个  
	                    chineseNum = NumberTools.chnNumChar[0] + chineseNum;  
	                }  
	            }else{  
	                zero = false;//有非零的数字，就把制零开关打开  
	                setionChinese = NumberTools.chnNumChar[v];//对应中文数字位  
	                setionChinese = setionChinese + NumberTools.chnUnitChar[unitPos];//对应中文权位  
	                chineseNum = setionChinese + chineseNum;  
	            }  
	            unitPos++;  
	            section = section/10;  
	        }  
	          
	        return chineseNum;  
	    }  
	    
	    public static void main(String[] args) {
	    	 NumberChangeToChinese numToChinese = new NumberChangeToChinese();  
	         System.out.println("0:"+numToChinese.numberToChinese(0));  
	         System.out.println("1:"+numToChinese.numberToChinese(1));  
	         System.out.println("2:"+numToChinese.numberToChinese(2));  
	         System.out.println("3:"+numToChinese.numberToChinese(3));  
	         System.out.println("4:"+numToChinese.numberToChinese(4));  
	         System.out.println("5:"+numToChinese.numberToChinese(5));  
	         System.out.println("6:"+numToChinese.numberToChinese(6));  
	         System.out.println("7:"+numToChinese.numberToChinese(7));  
	         System.out.println("8:"+numToChinese.numberToChinese(8));  
	         System.out.println("9:"+numToChinese.numberToChinese(9));  
	         System.out.println("10:"+numToChinese.numberToChinese(10));  
	         System.out.println("11:"+numToChinese.numberToChinese(11));  
	         System.out.println("110:"+numToChinese.numberToChinese(110));  
	         System.out.println("111:"+numToChinese.numberToChinese(111));  
	         System.out.println("100:"+numToChinese.numberToChinese(100));  
	         System.out.println("102:"+numToChinese.numberToChinese(102));  
	         System.out.println("1020:"+numToChinese.numberToChinese(1020));  
	         System.out.println("1001:"+numToChinese.numberToChinese(1001));  
	         System.out.println("1015:"+numToChinese.numberToChinese(1015));  
	         System.out.println("1000:"+numToChinese.numberToChinese(1000));  
	         System.out.println("10000:"+numToChinese.numberToChinese(10000));  
	         System.out.println("20010"+numToChinese.numberToChinese(20010));  
	         System.out.println("20001"+numToChinese.numberToChinese(20001));  
	         System.out.println("100000:"+numToChinese.numberToChinese(100000));  
	         System.out.println("1000000:"+numToChinese.numberToChinese(1000000));  
	         System.out.println("10000000"+numToChinese.numberToChinese(10000000));  
	         System.out.println("100000000:"+numToChinese.numberToChinese(100000000));  
	         System.out.println("1000000000"+numToChinese.numberToChinese(1000000000));  
	         System.out.println("2000105"+numToChinese.numberToChinese(2000105));  
	         System.out.println("20001007:"+numToChinese.numberToChinese(20001007));  
	         System.out.println("2005010010:"+numToChinese.numberToChinese(2005010010));  
		}
}
