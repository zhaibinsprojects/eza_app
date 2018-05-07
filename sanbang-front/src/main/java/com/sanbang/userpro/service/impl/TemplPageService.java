//package com.sanbang.userpro.service.impl;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStreamWriter;
//import java.io.Writer;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//import java.util.Random;
//
//import javax.annotation.Resource;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
//
//
//import freemarker.template.Configuration;
//import freemarker.template.Template;
//
//@Service("templPageService")
//public class TemplPageService {
//
//	private static Logger log = Logger.getLogger(TemplPageService.class.getName());
//
//	@Resource(name = "freemarkerConfig")
//	private FreeMarkerConfig freemarkerConfig;
//
//
//	
//	@Value("${consparam.indexOut.temPath}")
//	private String indexOutTemPath;
//
//	@Value("${consparam.indexOut.path}")
//	private String indexOutPath;
//	
//	private int minOrderNum1 = 36;
//	
//	private int minOrderNum2 = 72;
//
//	private float minTotalAmount1 = 1361700.00f;
//
//	private float minTotalAmount2 = 2000000.00f;
//
//	public String getIndexMode() throws Exception {
//		
//		log.info("=========>开始生成首页模板<=========");
//		
//		long starTime = System.currentTimeMillis();
//
//		String staticPagePath = indexOutTemPath + new SimpleDateFormat("MM-dd-HH-mm-ss").format(new Date()) + "-index.html";
//
//		Configuration conf = freemarkerConfig.getConfiguration();
//		conf.setLocale(Locale.CHINA);
//		conf.setSetting("number_format", "0.######");
//		conf.setSetting("object_wrapper", "freemarker.ext.beans.BeansWrapper");
//		conf.setDefaultEncoding("utf-8");// 设置编码
//		conf.setClassicCompatible(true);// 允许有空值
//		Template temp = conf.getTemplate("index1.ftl", "utf-8");// 获得模板对象
//		File htmlFile = new File(staticPagePath);
//		if (!htmlFile.exists()) {
//			createFile(htmlFile, staticPagePath);
//		}
//		Writer out = new BufferedWriter(new OutputStreamWriter(
//				new FileOutputStream(htmlFile), "UTF-8"));
//		// 取得数据
//		Map<String, Object> map = new HashMap<>();
//		
//		// 处理模版
//		temp.process(map, out);
//		out.flush();
//		out.close();
//		log.info("=========>生成首页模板完成<=========");
//		
//		// 如果执行成功,则执行文件拷贝
//		InputStream inStream = new FileInputStream(staticPagePath); // 读入原文件
//		FileOutputStream fs = new FileOutputStream(indexOutPath+"index.html");
//		int bytesum = 0;
//		int byteread = 0;
//		byte[] buffer = new byte[4096];
//		while ((byteread = inStream.read(buffer)) != -1) {
//			bytesum += byteread; // 字节数 文件大小
//			fs.write(buffer, 0, byteread);
//		}
//		inStream.close();
//		fs.close();
//
//		long endTime = System.currentTimeMillis();
//		long time = endTime - starTime;
//		
//		log.info("=========>首页模板拷贝完成,文件大小:"+bytesum+",用时:"+time+"<=========");
//		
//		
//		return "success";
//	}
//
//	/**
//	 * 楼层信息
//	 */
//	private Map<String, Object> collectFoolInfo() {
//		
//		Map<String,Object> info = new HashMap<>();
//
//		
//		
//		
//		return info;
//	}
//
//
//	public static void createFile(File file, String filePath) {
//		int potPos = filePath.lastIndexOf('/') + 1;
//		String folderPath = filePath.substring(0, potPos);
//		createFolder(folderPath);
//		FileOutputStream outputStream = null;
//		FileInputStream fileInputStream = null;
//		try {
//			outputStream = new FileOutputStream(filePath);
//			fileInputStream = new FileInputStream(file);
//			byte[] by = new byte[1024];
//			int c;
//			while ((c = fileInputStream.read(by)) != -1) {
//				outputStream.write(by, 0, c);
//			}
//		} catch (IOException e) {
//			e.getStackTrace().toString();
//		}
//		try {
//			outputStream.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		try {
//			fileInputStream.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void createFolder(String filePath) {
//		try {
//			File file = new File(filePath);
//			if (!file.exists()) {
//				file.mkdirs();
//			}
//		} catch (Exception ex) {
//			System.err.println("Make Folder Error:" + ex.getMessage());
//		}
//	}
//
//	/**
//	 * 获取某个文件夹下的所有文件名称和上传时间
//	 * 
//	 * @param filePath
//	 */
//	public static File[] getFileListByFilePath(String filePath) {
//		File file = new File(filePath);
//		File[] tempList = null;
//		if (!file.exists()) {
//			log.info("文件夹不存在：" + filePath);
//		} else if (!file.isDirectory()) {
//			log.info(filePath + "不是文件夹");
//		} else {
//			tempList = file.listFiles();
//		}
//		return tempList;
//	}
//	
//}
