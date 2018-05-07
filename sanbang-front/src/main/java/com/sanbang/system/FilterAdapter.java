package com.sanbang.system;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 过滤器适配器(适配器模式)
 * 
 * @author zhangxiantao
 *  
 * 2016年8月9日
 */
public abstract class FilterAdapter implements javax.servlet.Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public abstract void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException;

	@Override
	public void destroy() {
	}

	

}
