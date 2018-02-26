package com.software.job.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter("/*")
public class TokenFilter implements Filter {

    public TokenFilter() {
    }

	public void destroy() {
		
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String token=request.getParameter("token");
		if(token!=null) {
			HttpServletRequest req=(HttpServletRequest)request;
			long paramToken=Long.parseLong(req.getParameter("token"));
			long sessionToken=(Long)req.getSession().getAttribute("token");
			if(paramToken!=sessionToken) {
				request.setAttribute("error", "表单重复提交！");
				request.getRequestDispatcher("error.jsp").forward(request, response);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
