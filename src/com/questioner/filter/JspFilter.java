package com.questioner.filter;

import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class JspFilter extends HttpFilter {
       
	private static final long serialVersionUID = 3648223327134422150L;

	public JspFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {		
		if (((HttpServletRequest) request).getRequestURI().endsWith(".jsp")) {
			HttpServletResponse res = (HttpServletResponse) response;
			res.sendRedirect("/");
		} else {
		    chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
