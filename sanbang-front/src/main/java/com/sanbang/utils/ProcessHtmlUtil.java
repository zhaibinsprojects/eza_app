package com.sanbang.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class ProcessHtmlUtil {
	
//	private  static String ftlPath="E:\\templent";
	
	private  static String ftlPath="/data/www/file/templet/";

    /**
      * 通过freemarker生成静态HTML页面
     * @param ftlName                       模版名称
     * @param targetFileName        模版生成后的文件名
     * @param map                           freemarker生成的数据都存储在MAP中，
     * @创建时间：2018年9月30日21:41:06
     */
    public static void createHtml(String OutFilePath,String targetFileName,Map<String, Object> map) throws Exception{
        //创建fm的配置
        Configuration config = new Configuration();
        //指定默认编码格式 
        config.setDefaultEncoding("UTF-8");
        //设置模版文件的路径 
//        config.setClassForTemplateLoading(ProcessHtmlUtil.class, "../../../../../ftl/");
        config.setDirectoryForTemplateLoading(new File(ftlPath));
        //获得模版包
        Template template = config.getTemplate(targetFileName);
        //定义输出流，注意必须指定编码
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(OutFilePath)),"UTF-8"));
        //生成模版
        template.process(map, writer);
    }
    
    public static void main(String[] args) {
    	Map<String, Object> map=new HashMap<>();
    	String OutFilePath="D://index.html";
    	try {
			createHtml(OutFilePath, "xiaoshou5.ftl", map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
