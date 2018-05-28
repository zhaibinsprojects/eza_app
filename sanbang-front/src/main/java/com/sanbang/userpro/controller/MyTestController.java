package com.sanbang.userpro.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.area.service.AreaService;
import com.sanbang.bean.ezs_user;
import com.sanbang.dict.service.DictService;
import com.sanbang.userpro.service.TestService;
import com.sanbang.userpro.service.UserProService;
import com.sanbang.utils.IpUtils;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCate;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.MessageDictionary;

@Controller
@RequestMapping("/adduser")
public class MyTestController {
	
	@Autowired
	private TestService test;
	
	@RequestMapping("/tuser")
	public void test(int num){
	 test.addUserInfo(num);
 }
}
