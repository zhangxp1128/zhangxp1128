<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%--
	double upper=10;
	double base=30;
	double height=20;
	double area = (upper+base)*height/2.0;
	out.print("梯形的面积是:" + area);
--%>
<p>
查看梯形面积
</p>

<%-- <jsp:include page="testCalcArea.jsp">
	<jsp:param value="20" name="upper" />
	<jsp:param value="30" name="base" />
	<jsp:param value="20" name="height" />
</jsp:include> --%>


<%
	request.setAttribute("upper", 10);
	request.setAttribute("base", 30);
	request.setAttribute("height", 20);
%>
<%@ include file="testCalcArea.jsp" %>
</body>
</html>