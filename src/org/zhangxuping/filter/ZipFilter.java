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
import java.util.zip.GZIPOutputStream;
import javax.servlet.*;
import java.io.*;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
@WebFilter(filterName = "FileZipFilter", urlPatterns = {"/*"})  
public class ZipFilter implements Filter {

	@Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        MyHttpServletResponse myResponse = new MyHttpServletResponse(response);
        chain.doFilter(request, myResponse);
        byte[] byteArrayOutputStream = myResponse.getBuffer();
        byte[] beWriting = gzip(byteArrayOutputStream);
        if (byteArrayOutputStream.length > 0) {
        	System.out.println("18 : 使用Filter内容压缩功能 ---> byteArrayOutputStream流长�? = " + byteArrayOutputStream.length + "byte");
            System.out.println("18 : 使用Filter内容压缩功能  ---> 压缩后beWriting流长�? = " + beWriting.length + "byte");
        }
        response.setHeader("content-encoding", "gzip");
        response.setHeader("content-length", beWriting.length + "");
        response.getOutputStream().write(beWriting);
    }
 
    public byte[] gzip(byte[] beZipping) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        GZIPOutputStream gout = new GZIPOutputStream(bout);
        gout.write(beZipping);
        gout.close();
        return bout.toByteArray();
    }
 
    private class MyHttpServletResponse extends HttpServletResponseWrapper {
        private ByteArrayOutputStream byteArrayOutputStream;
        private PrintWriter pw;
 
        
        public MyHttpServletResponse(HttpServletResponse response) throws UnsupportedEncodingException {
            super(response);
            this.byteArrayOutputStream = new ByteArrayOutputStream();
            
            this.pw = new PrintWriter(new OutputStreamWriter(byteArrayOutputStream, "UTF-8"));
        }
 
        
        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return new MyServletOutputStream(super.getOutputStream(), byteArrayOutputStream);
        }
 
        @Override
        public PrintWriter getWriter() throws UnsupportedEncodingException {
            return pw;
        }
 
        public byte[] getBuffer() throws IOException {
            if (pw != null) {
                pw.close();
            }
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        }
    }
 
    private class MyServletOutputStream extends ServletOutputStream {
        private ServletOutputStream outputStream;
        private ByteArrayOutputStream byteArrayOutputStream;
 

        public MyServletOutputStream(ServletOutputStream outputStream, ByteArrayOutputStream byteArrayOutputStream) {
            super();
            this.outputStream = outputStream;
            this.byteArrayOutputStream = byteArrayOutputStream;
        }
 
       
        @Override
        public void write(int b) {
            byteArrayOutputStream.write(b);
        }
 
        @Override
        public boolean isReady() {
            return outputStream.isReady();
        }
 
        @Override
        public void setWriteListener(WriteListener writeListener) {
            outputStream.setWriteListener(writeListener);
        }
    }
 
    @Override
    public void init(FilterConfig config) throws ServletException {
    }
 
    @Override
    public void destroy() {
    	
    }
}
