package com.sanbang.utils.httpclient;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class HttpServletResponseFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpServletResponseWrapperImp wrapper = new HttpServletResponseWrapperImp(resp);

		chain.doFilter(request, wrapper);

		ServletOutputStream output = response.getOutputStream();
		output.write(wrapper.getResponseData());
		output.flush();
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
