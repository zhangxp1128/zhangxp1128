package org.zhangxuping.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtil {
	private static DBUtil dbUtil = null;
	private static String driver;
	private static String user;
	private static String password;
	private static String url;

    private static String FILE_PATH_NAME = "database.properties";  
	
	private DBUtil() {
		try {  
            InputStream in = getClass().getResourceAsStream(FILE_PATH_NAME);  
            Properties props = new Properties();
          
            props.load(in);  
            in.close();   
            driver = props.getProperty("jdbc.driver");
            user = props.getProperty("jdbc.user");  
            password = props.getProperty("jdbc.password");  
            url = props.getProperty("jdbc.url");  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
	}
	
	public static Connection getConnection() {
		if (dbUtil == null) {
			dbUtil = new DBUtil();
		}
		Connection connection = null;
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public static void closeJDBC(ResultSet rs, Statement stmt, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
