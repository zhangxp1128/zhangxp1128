<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	double upper = Double.parseDouble(request.getAttribute("upper").toString());
	double base = Double.parseDouble(request.getAttribute("base").toString());
	double height = Double.parseDouble(request.getAttribute("height").toString());
	double area = (upper+base)*height/2.0;
	response.getWriter().print("梯形的面积是:" + area);
%>
</body>
</html>