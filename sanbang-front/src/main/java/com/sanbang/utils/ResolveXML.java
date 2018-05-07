package com.sanbang.utils;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

public class ResolveXML  {
	
	
	public void importFromXml(String filePath) {
		try {
			//创建解析器 	使用的是dom的解析方式
			SAXReader reader = new SAXReader();
			//获取当前线程对应的类加载器
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			//读取指定资源的输入流
			InputStream in = loader.getResourceAsStream(filePath);
			//读取文件 生成dom树
			Document document = reader.read(in);
			
			document.selectSingleNode("/sys-data/user-info/user");
			document.selectNodes("");
			document.selectSingleNode("");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ResolveXML() {
	}


	/*private User saveUser(Element userElt) {
		User user = new User();
		user.setAccountNo(userElt.attributeValue("login-account"));
		user.setName(userElt.attributeValue("name"));
		user.setPassword(userElt.attributeValue("password"));
		userDao.save(user);
		return user;
	}*/

	
}
