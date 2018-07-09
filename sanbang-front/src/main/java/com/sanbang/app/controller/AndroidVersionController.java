package com.sanbang.app.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;

@Controller
@RequestMapping("/app/version")
public class AndroidVersionController {
	
	//版本号集合
	private static List<String> versions;
	
	//1 不用更新  2必须更新
    private int forbidden=1;
    //小版本号
    private int serviceCode=2;
    //更新内容
    private String update_content="1.全新3.0设计语言\n2.更加人性化的自营操作\n3.效率极高的交易流程"; 
    
    @Value("${consparam.app.versionspath}")
    public String filebasepath;
    
    @Value("${consparam.ser.baseurl}")
    public String baseurl;
    
    
	@PostConstruct
	public void initdata(){
		versions=new ArrayList<>();
		versions.add("1.0.1");
		filebasepath=filebasepath+"ezaisheng1.0.1.apk";
		baseurl+="front/app/version/download.htm";
	}
	
	
	@RequestMapping(value = "/sendVersion")
	@ResponseBody
	public Result sendVersion(){
		Result result=Result.failure();
		Map<String,Object> map=new HashMap<>();
			try {
				map.put("serviceCode", serviceCode);
				map.put("url", baseurl);
				map.put("forbidden", forbidden);
				map.put("version", versions.get(versions.size()-1));
				map.put("update_content", update_content);
				result.setSuccess(true);
				result.setMsg("请求成功");
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				result.setObj(map);
			} catch (Exception e) {
				e.printStackTrace();
				result.setSuccess(false);
				result.setMsg("系统错误");
				result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
				result.setObj(map);
			}
		return result;
	}
	
	
	@RequestMapping(value = "/download")
	@ResponseBody
	public void downLoadFile(HttpServletResponse response) {
		File file=new File(filebasepath);
		InputStream bin = null;
		try {
			bin = new BufferedInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		if (file == null || !file.exists()) {
			return; } 
		OutputStream out = null; 
		try { 
			response.reset(); response.setContentType("application/octet-stream; charset=utf-8"); 
			response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
			response.setContentLength(bin.available());
			out = response.getOutputStream(); 
			int len;
			 byte[] buffer = new byte[4096];
			 while((len = bin.read(buffer)) != -1){
				 out.write(buffer, 0, len);
		        }
			} catch (IOException e) {
				e.printStackTrace(); 
			} finally { 
				if (out != null) { 
					try { 
						out.close(); 
					} catch (IOException e) { 
						e.printStackTrace(); } 
					} 
				} 
		}
}
