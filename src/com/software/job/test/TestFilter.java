package com.software.job.test;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/TestFilterServlet")
public class TestFilter implements Filter {

    public TestFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("调用前！！！！");
		chain.doFilter(request, response);
		System.out.println("调用后！！！！！");
	}

	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("*******Filter被初始化了!*******");
	}

}
