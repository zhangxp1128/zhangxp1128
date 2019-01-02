package org.zhangxuping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.zhangxuping.bean.Comment;
import org.zhangxuping.util.DBUtil;

public class CommentDao {
	public boolean create(Comment comment) {
		Connection connection = DBUtil.getConnection();
		String sql = "INSERT INTO tb_comments(author, postId, content) VALUES(?,?,?)";
		PreparedStatement pStatement = null;
		ResultSet rs = null;
		int result = 0;
		try {
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, comment.getAuthor());
			pStatement.setInt(2, comment.getPostId());
			pStatement.setString(3, comment.getContent());
			result = pStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeJDBC(null, pStatement, connection);
		}
		return result==1;
	}
	public boolean delete(int id) {
		Connection connection = DBUtil.getConnection();
		String sql = "DELETE FROM tb_comments WHERE Id=?";
		PreparedStatement pStatement = null;
		int result = 0;
		try {
			pStatement = connection.prepareStatement(sql);
			pStatement.setInt(1, id);
			result = pStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeJDBC(null, pStatement, connection);
		}
		return (result==1);
	}
	public List<Comment> queryByPostId(int postId) {
		Connection connection = DBUtil.getConnection();
		String sql = "SELECT * FROM tb_comments WHERE postId=?";
		PreparedStatement pStatement = null;
		ResultSet rs = null;
		List<Comment> comments = new ArrayList<Comment> ();
		try {
			pStatement = connection.prepareStatement(sql);
			pStatement.setInt(1, postId);
			rs = pStatement.executeQuery();
			
			while(rs.next()) {
				Comment comment = new Comment();
				comment.setCommentId(rs.getInt(1));
				comment.setAuthor(rs.getString(2));
				comment.setPostId(rs.getInt(3));
				comment.setContent(rs.getString(4));
				comment.setPosttime(rs.getTimestamp(5));
				comments.add(comment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeJDBC(rs, pStatement, connection);
		}
		
		return comments;
	}
	
	public Comment queryById(int id) {
		Comment comment = null;
		String sql = "SELECT * FROM tb_comments WHERE Id=?";
		Connection connection = DBUtil.getConnection();
		PreparedStatement pStatement = null;
		ResultSet rs = null;
		try {
			pStatement = connection.prepareStatement(sql);
			pStatement.setInt(1, id);
			rs = pStatement.executeQuery();
			
			if (rs.next()) {
				comment = new Comment();
				comment.setCommentId(rs.getInt(1));
				comment.setAuthor(rs.getString(2));
				comment.setPostId(rs.getInt(3));
				comment.setContent(rs.getString(4));
				comment.setPosttime(rs.getTimestamp(5));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeJDBC(rs, pStatement, connection);
		}
		
		return comment;
	}
}
