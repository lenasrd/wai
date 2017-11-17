<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">		
		<title>Login</title>
	</head>
	
	
	<body>
		<h1>Login</h1><br><br>
		<form name="login" action="login" method="post">
		
			<table border=1>
				<tr>
					<c:if test="${valid==false}">Feld muss ausgefüllt sein</c:if>
					<td>username:</td>
					<td><input type="text" name="username" value="${user.username}"/></td>
				</tr>
				<tr>
					<td>password:</td>
					<td><input type="text" name="password" value="${user.password}"/></td>
				</tr>
				<tr>
					<td><input type="submit" name= "btnLogin" value="Login"></td></td>				
				</tr>
			</table>
			
			
		</form>

	</body>
</html>