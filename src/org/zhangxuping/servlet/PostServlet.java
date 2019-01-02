package org.zhangxuping.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zhangxuping.bean.Comment;
import org.zhangxuping.bean.Post;
import org.zhangxuping.bean.User;
import org.zhangxuping.dao.CommentDao;
import org.zhangxuping.dao.PostDao;
import org.zhangxuping.dao.UserDao;

import com.sun.webkit.PageCache;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Servlet implementation class PostServlet
 */
@WebServlet("/PostServlet")
public class PostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		String author = request.getParameter("author");
		String postid = request.getParameter("postid");
		String strPageNo = request.getParameter("pageNo");
		PostDao postDao = new PostDao();

		if (action.equals("listposts")) {
			int pageSize = 10;
			int postCount = 0;
			List<Post> posts = null;
			int pageNo = 0;
			try {
				pageNo = Integer.parseInt(strPageNo);
			} catch (Exception e) {
				pageNo = 0;
			}
			if (null != author && !"".equals(author)) {
				postCount = postDao.queryPostCountByAuthor(author);
				posts = postDao.pagedQueryByAuthor(author, pageNo, pageSize);
				//posts = postDao.queryByAuthor(author);
			} else {
				postCount = postDao.queryPostCount();
				posts = postDao.pagedQueryAll(pageNo, pageSize);
				//posts = postDao.queryAll();
			}
			int pageCount = postCount / pageSize + ((postCount % pageSize != 0) ? 1 : 0);
			Map<String, User> postAuthors = getPostAuthors(posts);
			Map<Integer, Integer> postCommentCounts = getPostCommentCounts(posts);
			request.setAttribute("pageCount", pageCount);
			request.setAttribute("posts", posts);
			request.setAttribute("postAuthors", postAuthors);
			request.setAttribute("postCommentCounts", postCommentCounts);
			request.getRequestDispatcher("components/posts-content.jsp").include(request, response);
		} else if (action.equals("listpost")) {
			if (null != postid && !"".equals(postid)) {
				Post post = postDao.queryById(Integer.parseInt(postid), true);
				UserDao userDao = new UserDao();
				User postAuthor = userDao.query(post.getAuthor());
				request.setAttribute("post", post);
				request.setAttribute("postAuthor", postAuthor);
			}
		} else if (action.equals("editpost")) {
			if (null != postid && !"".equals(postid)) {
				Post post = postDao.queryById(Integer.parseInt(postid), false);
				request.setAttribute("post", post);
			}
		}  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	private Map<String, User> getPostAuthors(List<Post> posts) {
		Map<String, User> map = new HashMap<String, User>();
		UserDao userDao = new UserDao();
		for (Post post : posts) {
			User user = userDao.query(post.getAuthor());
			if (user != null) {
				user.setPassword("");
				map.put(user.getStuId(), user);
			}
		}
		return map;
	}	

	private Map<Integer, Integer> getPostCommentCounts(List<Post> posts) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		CommentDao commentDao = new CommentDao();
		for (Post post : posts) {
			List<Comment> list = commentDao.queryByPostId(post.getPostId());
			map.put(post.getPostId(), list.size());
		}
		return map;
	}	
}
