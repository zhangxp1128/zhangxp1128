package org.zhangxuping.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zhangxuping.bean.User;
import org.zhangxuping.dao.UserDao;
import org.zhangxuping.util.EncryptTool;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
request.setCharacterEncoding("utf-8");
		
		String stuId = request.getParameter("stuId");
		String password = request.getParameter("password");
		String rememberMe = request.getParameter("rememberMe");
		
		boolean result = false;
		UserDao userDao = new UserDao();
		User user = userDao.query(stuId);
		if (user != null && user.getPassword().equals(DigestUtils.sha1Hex(password))) {
			result = true;
			
			// 设置session
			HttpSession session = request.getSession(true);
			user.setPassword("");
			session.setAttribute("User", user);
			
			// 存取cookie
			if (rememberMe != null && "on".equals(rememberMe)) {
				Cookie cookieStuId = new Cookie("stuId", EncryptTool.encodeBase64(stuId));
				Cookie cookiePassword = new Cookie("password", EncryptTool.encodeBase64(password));
				cookieStuId.setMaxAge(3600*24*365);
				cookiePassword.setMaxAge(3600*24*365);
				response.addCookie(cookieStuId);
				response.addCookie(cookiePassword);
			} else {
				Cookie[] cookies = request.getCookies();
				for(Cookie cookie : cookies) {
					if (cookie.getName().equals("stuId") || cookie.getName().equals("password")) {
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					}						
				}
			}
			
			response.sendRedirect("index.jsp");
		} else {		
			request.setAttribute("tip", "Login failed.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

}
