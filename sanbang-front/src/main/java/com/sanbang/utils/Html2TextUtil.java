package com.sanbang.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;


public class Html2TextUtil {
	public static String Html2Text(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        Pattern p_script;
        Matcher m_script;
        Pattern p_style;
        Matcher m_style;
        Pattern p_html;
        Matcher m_html;
        Pattern p_html1;
        Matcher m_html1;
        try {
            String regEx_script = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[//s//S]*?<///script>
            String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[//s//S]*?<///style>
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            String regEx_html1 = "<[^>]+";
            String regEx_p="\\s*|\t|\r|\n";
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
            m_html1 = p_html1.matcher(htmlStr);
            htmlStr = m_html1.replaceAll(""); // 过滤html标签
            textStr = htmlStr;
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
    	    Matcher m = p.matcher(textStr);
    	    textStr=m.replaceAll("");
        } catch (Exception e) {
        
        }
        return textStr;// 返回文本字符串
    }
	
	/**
	 * 得到title
	 */
	public static String getTitle(String title,String Scatname,String Fcatname,String gytype){
		StringBuilder sbd=new StringBuilder();
		if(StringUtils.isNotBlank(title)){
			sbd.append(title);
		}
		if(StringUtils.isNotBlank(Scatname)){
			sbd.append("_").append(Scatname);
		}
		if(StringUtils.isNotBlank(Fcatname)){
			sbd.append("_").append(Fcatname);
		}
		if(StringUtil.isNotBlank(gytype)){
			sbd.append("_").append(gytype);
		}
		sbd.append("_").append("易再生网");
		return sbd.toString();
	}
	
	/**
	 * 得到keyWord
	 */
	public static  String getKeyWord(String title,String gytype,String Fcatname,String Scatname){
		
		StringBuilder sbd=new StringBuilder();
		
		if(StringUtils.isNotBlank(title)){
			sbd.append(title);
		}
		if(StringUtils.isNotBlank(gytype)){
			sbd.append(",").append(gytype);
		}
		if(StringUtils.isNotBlank(Fcatname)){
			sbd.append(",").append(Fcatname);
		}
		if(StringUtils.isNotBlank(Scatname)){
			sbd.append(",").append(Scatname);
		}
		
		return sbd.toString();
	}
}
