package com.sanbang.utils;

import java.util.List;

import com.sanbang.vo.userauth.AuthImageVo;

public class ImageUrlUtil {
	//获取图片
		public static String geturl(String type,List<AuthImageVo> list){
			if(null==list||list.size()==0){
				return "";
			}
			String status="";
			for (AuthImageVo authImageVo : list) {
				if(authImageVo.getImgcode().equals(type)){
					status=authImageVo.getImgurl();
					break;
				}
			}
			return status;
		}
}
