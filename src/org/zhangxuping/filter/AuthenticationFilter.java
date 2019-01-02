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
@WebFilter(
		filterName = "/AuthenticationFilter",
		urlPatterns = {"/newpost.jsp", "/editpost.jsp", "/userprofile.jsp"},  // 
		servletNames = {
			"org.panmiaomiao.servlet.EditPostServlet", 
			"org.panmiaomiao.servlet.NewPostServlet", 
			"org.panmiaomiao.servlet.NewCommentServlet", 
			"org.panmiaomiao.servlet.RemoveCommentServlet",
			"org.panmiaomiao.servlet.RemovePostServlet", 
			"org.panmiaomiao.servlet.UpdateBasicInfoServlet", 
			"org.panmiaomiao.servlet.UpdateAvatarServlet", 
			"org.panmiaomiao.servlet.LogoutServlet"},
		dispatcherTypes = {DispatcherType.REQUEST}
		)
public class AuthenticationFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AuthenticationFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		String queryString = req.getQueryString();
		System.out.println(req.getRequestURL() + (null!=queryString ? "?" + queryString : ""));
		if (req.getSession(true).getAttribute("User") == null) {
			// 未登�?
			//res.sendRedirect("signin.jsp");
			req.setAttribute("tip", "You have not signed in!");
			req.getRequestDispatcher("login.jsp").forward(req, res);
		} else {	
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
