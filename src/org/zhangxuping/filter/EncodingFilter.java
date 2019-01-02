package org.zhangxuping.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.DispatcherType;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
@WebFilter(filterName = "EncodingFilter", urlPatterns = {"/*"}) 
public class EncodingFilter implements Filter {

	private String ENCODING = "UTF-8";
	protected FilterConfig filterConfig;
	
	@Override
	public void destroy() {
		this.ENCODING = null;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		request.setCharacterEncoding(ENCODING);
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		if (filterConfig.getInitParameter(ENCODING) != null) {
			ENCODING = 	filterConfig.getInitParameter(ENCODING);
		}
		
	}
	
}
