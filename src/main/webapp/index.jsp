<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    response.sendRedirect(request.getContextPath() + "/dashboard");
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="refresh" content="0;url=${pageContext.request.contextPath}/dashboard">
    <title>Redirecting to Dashboard...</title>
</head>
<body>
    <p>Redirecting to <a href="${pageContext.request.contextPath}/dashboard">Student Registration Dashboard</a>...</p>
</body>
</html>

