package com.sanbang.utils;

public class FilePathUtil {

	
	public static String getmiddelPath(String url){
		return url.substring(url.indexOf("upload"),url.lastIndexOf("\\")+1 );
		
	}
	public static String getimageName(String url){
		return url.substring(url.lastIndexOf("\\")+1, url.length());
		
	}
	public static void main(String[] args) {
		String url="http://10.10.10.95/upload/201806\\13\\18-37-14-89-46671.jpg";
		System.out.println(getmiddelPath(url));
		System.out.println(getimageName(url));
	}
}
