package org.zhangxuping.servlet;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.codec.digest.DigestUtils;
import org.zhangxuping.bean.User;
import org.zhangxuping.dao.UserDao;

import java.io.File;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
@MultipartConfig
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("register.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String stuId = request.getParameter("stuId");
		String stuName = request.getParameter("stuName");
		String password = request.getParameter("password");
		//String avatar = request.getParameter("avatar");
		String gender = request.getParameter("gender");
		String bio = request.getParameter("bio");
		String gitUrl = request.getParameter("gitUrl");
		
		Part part = request.getPart("avatar");
		String fileName = part.getSubmittedFileName();
		String avatar = "";
		if (fileName != null && !"".equals(fileName)) {
			String newFileName = UUID.randomUUID().toString() + "_" + fileName;
			String filePath = getServletContext().getRealPath("/img/upload");
			File f = new File(filePath);
			if (!f.exists()) {
				f.mkdirs();
			}
			System.out.println(filePath);
			part.write(filePath + "/" + newFileName);
			avatar = newFileName;
		}

		
		UserDao userDao = new UserDao();
		User user = new User();
		user.setStuId(stuId);
		user.setStuName(stuName);
		user.setPassword(DigestUtils.sha1Hex(password));
		user.setAvatar(avatar);
		user.setGender(gender);
		user.setBio(bio);
		user.setGitUrl(gitUrl);
		boolean result = userDao.create(user);		

		if (result == true) {
			request.setAttribute("tip", "Register successfully, logining automatically.");
			HttpSession session = request.getSession(true);
			user.setPassword("");
			session.setAttribute("User", user);
			request.getRequestDispatcher("result.jsp").forward(request, response);
		} else {
			request.setAttribute("tip", "Register failed, redirecting to home page.");
			request.getRequestDispatcher("result.jsp").forward(request, response);
		}
	}

}
