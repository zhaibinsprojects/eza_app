package com.sanbang.utils;

public class FilePathUtil {

	
	public static String getmiddelPath(String url){
		return url.substring(url.indexOf("upload"),url.lastIndexOf("/")+1 );
		
	}
	public static String getimageName(String url){
		return url.substring(url.lastIndexOf("/")+1, url.length());
		
	}
	public static void main(String[] args) {
		String url="http://10.10.10.148/upload/201806/14/19-32-42-49-56840.jpg";
		System.out.println(getmiddelPath(url));
		System.out.println(getimageName(url));
	}
}
