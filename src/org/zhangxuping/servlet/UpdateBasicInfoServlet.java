package org.zhangxuping.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.zhangxuping.bean.User;
import org.zhangxuping.dao.UserDao;

/**
 * Servlet implementation class UpdateBasicInfoServlet
 */
@WebServlet("/UpdateBasicInfoServlet")
public class UpdateBasicInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateBasicInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String stuName = request.getParameter("stuName");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		String bio = request.getParameter("bio");
		String gitUrl = request.getParameter("gitUrl");
		String stuId = ((User)request.getSession().getAttribute("User")).getStuId();
		UserDao userDao = new UserDao();
		User user = userDao.query(stuId);
		
		user.setStuName(stuName);
		user.setGender(gender);
		user.setBio(bio);
		user.setGitUrl(gitUrl);
		if (!"".equals(password)) user.setPassword(DigestUtils.sha1Hex(password));
		
		if (userDao.update(user) == true) {
			request.setAttribute("tip", "update successfully.");
			HttpSession session = request.getSession(true);
			user.setPassword("");
			session.setAttribute("User", user);
			request.getRequestDispatcher("userprofile.jsp").forward(request, response);
		} else {
			
		}
	}

}
