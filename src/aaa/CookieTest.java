package aaa;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * Servlet implementation class CookieTest
 */
@WebServlet("/CookieTest")
public class CookieTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CookieTest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Cookie cookieName = new Cookie("Name", Base64.encode("CHEN WEI+FENG".getBytes()));
//		cookieName.setMaxAge(3600*24*365);
//		response.addCookie(cookieName);
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			response.getWriter().println(cookie.getName());

			if (cookie.getName().equals("Name"))
				response.getWriter().println(new String(Base64.decode(cookie.getValue())));
			else
				response.getWriter().println(cookie.getValue());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
