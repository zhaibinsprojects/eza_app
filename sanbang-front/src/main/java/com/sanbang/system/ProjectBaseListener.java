package com.sanbang.system;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * 在全局范围内存储参数
 * @author zhangxiantao
 *  
 * 2016年6月24日
 */
public class ProjectBaseListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// 获取当前项目的根路径
		String contextPath = event.getServletContext().getContextPath();
		// 在application作用域中设置常量
		event.getServletContext().setAttribute("APP_PATH", contextPath);
	}

}
