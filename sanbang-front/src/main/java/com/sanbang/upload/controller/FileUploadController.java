package com.sanbang.upload.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.upload.sevice.FileUploadService;
import com.sanbang.upload.sevice.impl.FileUploadServiceImpl;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;

/**
 * 文件上传
 * @author LENOVO
 *
 */
@Controller
@RequestMapping("/fileUpload")
public class FileUploadController {

	@Resource(name="fileUploadService")
	private FileUploadService fileUploadService;

	// 日志
	private static Logger log = Logger.getLogger(FileUploadServiceImpl.class);
	/**
	 * 上传图片
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/uploadFile",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Result uploadFile(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Result result=Result.failure();

		Map<String, Object> map1=new HashMap<>();
		try {
			Map<String , Object> map=fileUploadService.uploadFile(request,0,0,10*1024*1024l);
			if("000".equals(map.get("code"))){
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				result.setMsg("上传成功");
				map1.put("picurl",  map.get("url"));
				result.setObj(map1);
				result.setSuccess(true);
				return result;
			}else{
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setObj(map1);
				result.setMsg("上传失败");
				result.setSuccess(false);
			}
		} catch (Exception e) {
			log.info("文件：上传接口调用失败"+e.toString());
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("上传失败");
			result.setObj(map1);
			result.setSuccess(false);
		} 
		result.setObj(new HashMap<>());
		
		return result;
	}
	
	

	
}
