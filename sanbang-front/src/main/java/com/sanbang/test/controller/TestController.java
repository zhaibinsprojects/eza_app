package com.sanbang.test.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author zhangxiantao
 *  
 * 2016年7月28日
 */
@Controller
@RequestMapping("/test")
public class TestController {
	
	@RequestMapping("/test3")
	public String testxysc(){
		return "xyindex";
	}
	
	@RequestMapping("/test2")
	public String testme(){
		System.out.println("sldf");
		 Runtime currRuntime = Runtime.getRuntime ();

	       int nFreeMemory = ( int ) (currRuntime.freeMemory() / 1024 / 
	1024);

	       int nTotalMemory = ( int ) (currRuntime.totalMemory() / 1024 / 
	1024);

	       String aa= nFreeMemory + "M/" + nTotalMemory + "M(free/total)" ;
	       System.out.println(aa);
		return "tet";
	}
	
	public static void main(String[] args) {

	       System. out .println( " 内存信息 :" + toMemoryInfo ());

	    }

	    public static String toMemoryInfo() {

	       Runtime currRuntime = Runtime.getRuntime ();

	       int nFreeMemory = ( int ) (currRuntime.freeMemory() / 1024 / 
	1024);

	       int nTotalMemory = ( int ) (currRuntime.totalMemory() / 1024 / 
	1024);

	       return nFreeMemory + "M/" + nTotalMemory + "M(free/total)" ;
	    
	}
	
}









