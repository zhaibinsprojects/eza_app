package com.sanbang.utils;

import java.util.HashMap;

public class NumberTools {
	//数字位  、、、、、、、、、拾、佰、仟、万、
    public static String[] chnNumChar = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};  
    public static char[] chnNumChinese = {'零','壹','贰','叁','肆','伍','陆','柒','捌','玖'};  
    //节权位  
    public static String[] chnUnitSection = {"","万","亿","万亿"};    
    //权位  
    public static String[] chnUnitChar = {"","拾","佰","仟"};  
//    public static HashMap intList = new HashMap();  
//    static{  
//        for(int i=0;i<chnNumChar.length;i++){  
//            intList.put(chnNumChinese[i], i);  
//        }  
//          
//        intList.put('十',10);  
//        intList.put('百',100);  
//        intList.put('千', 1000);  
//    }  
}
