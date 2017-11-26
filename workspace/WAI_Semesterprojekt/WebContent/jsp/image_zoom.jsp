<%@ page language="java" contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<title>Image zoom</title>
	</head>
	<body>
		<h1 align="center">Cam "camName" (ID xxx)</h1>
		<h2 align="center">15.11.17, 15:00</h2>
		<form action="image" name="appform" method="post">
			<table align="center" cellpadding="2" cellspacing="2">
				<tr>
					<td><img src="${pageContext.request.contextPath}${image.path}" height="400"></td>
				</tr>
			</table>
		</form>
	</body>
</html>