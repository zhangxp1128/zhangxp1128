package org.zhangxuping.listener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class VisistCountListener
 *
 */
@WebListener
public class VisistCountListener implements HttpSessionListener, ServletRequestListener, ServletContextListener {
	private int num = 0;
    /**
     * Default constructor. 
     */
    public VisistCountListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0)  { 
    	num++;
    	ServletContext context = arg0.getSession().getServletContext();
    	context.setAttribute("onlineCount", num);
		System.out.println("sessionCreated");
    	
    	Integer count = (Integer)context.getAttribute("totalCount");
    	if (count != null) {
    		context.setAttribute("totalCount", count+1);
    	}
    }

	/**
     * @see ServletRequestListener#requestDestroyed(ServletRequestEvent)
     */
    public void requestDestroyed(ServletRequestEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletRequestListener#requestInitialized(ServletRequestEvent)
     */
    public void requestInitialized(ServletRequestEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0)  { 
    	num--;
    	ServletContext context = arg0.getSession().getServletContext();
    	context.setAttribute("onlineCount", num);
		System.out.println("sessionDestroyed");
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
    	ServletContext context = arg0.getServletContext();
    	Integer count = (Integer)context.getAttribute("totalCount");
    	if (count != null) {
	    	try {
	    		String filePath = context.getRealPath("/") + "/count.txt";
	    		PrintWriter printWriter = new PrintWriter(filePath);
	    		printWriter.println(count.intValue());
	    		printWriter.close();
	    		System.out.println("contextDestroyed: " + filePath);
	    	} catch (Exception e) {
	    		e.printStackTrace();
			}
    	}
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
    	ServletContext context = arg0.getServletContext();
    	int count = 0;
    	try {
    		BufferedReader reader = new BufferedReader(new InputStreamReader(context.getResourceAsStream("count.txt")));
    		String strCount = reader.readLine();
    		if (strCount == null || "".equals(strCount)) {
    			strCount = "0";
    		}
    		reader.close();
    		count = Integer.parseInt(strCount);
    		System.out.println("contextInitialized");
    	} catch (Exception e) {
    		e.printStackTrace();
		}
		context.setAttribute("totalCount", count);
    }
	
}
