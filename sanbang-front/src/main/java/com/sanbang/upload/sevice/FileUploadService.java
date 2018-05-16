package com.sanbang.upload.sevice;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface FileUploadService {

	/**
	 * 上传图片到临时路径
	 * width 和 height都为0代表 不检查图片像素
	 * width 和 height都不为0代表检查像素  传多少  限制  图片宽和长就是多少
	 * size是当前图片的大小 单位是字节
	 */
	Map<String,Object> uploadFile(HttpServletRequest request,int width,int height,long size) throws Exception;
}
