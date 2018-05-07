package com.sanbang.utils;

import java.util.Random;

public class RandomStr32 {

	private static String strs="0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLMNBVCXZ";
	public static String getStr32(){
		Random r=new Random();
		StringBuilder sbd=new StringBuilder();
		for(int i=0;i<32;i++){
			int tempInt=r.nextInt(strs.length());
			sbd.append(strs.charAt(tempInt));
		}
		return sbd.toString();
	}
	//自定义字符串长度
	public static String getStrDefined(int num){
		Random r=new Random();
		StringBuilder sbd=new StringBuilder();
		for(int i=0;i<num;i++){
			int tempInt=r.nextInt(strs.length());
			sbd.append(strs.charAt(tempInt));
		}
		return sbd.toString();
	}
	public static String getStr7(){
		Random r=new Random();
		StringBuilder sbd=new StringBuilder();
		for(int i=0;i<7;i++){
			int tempInt=r.nextInt(strs.length());
			sbd.append(strs.charAt(tempInt));
		}
		return sbd.toString();
	}
}
