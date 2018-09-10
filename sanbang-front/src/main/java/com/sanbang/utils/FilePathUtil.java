package com.sanbang.utils;

public class FilePathUtil {

	
	public static String getmiddelPath(String url){
		return url.substring(url.indexOf("upload"),url.lastIndexOf("/")+1 );
		
	}
	public static String getimageName(String url){
		return url.substring(url.lastIndexOf("/")+1, url.length());
		
	}
	
	public static String getHangqPath(String url){
		return url.substring(url.indexOf("upload"),url.length());
		
	}
	public static void main(String[] args) {
		String url="http://m.ezaisheng.com/test/upload/h5/201808/06/14-02-30-32-45113.jpg";
		System.out.println(getmiddelPath(url));
		System.out.println(getimageName(url));
		System.out.println(getHangqPath(url));
	}
}
