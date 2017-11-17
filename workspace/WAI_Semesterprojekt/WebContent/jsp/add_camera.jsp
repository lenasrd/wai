<%@ page language="java" contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<title>Add camera</title>
	</head>
	<body>
		<h1 align="center" >Add camera</h1>
		<form action="Controller" name="appform" method="get">
			<table align="center" cellpadding="2" cellspacing="2">
				<tr>
					<td>ID</td>
					<td><input type="text" name="key" size="20" /></td>
				</tr>
				<tr>
					<td>Name</td>
					<td><input type="text" name="key" size="20" /></td>
				</tr>
				<tr>
					<td>Source</td>
					<td><input type="text" name="key" size="20" /></td>
				</tr>
				<tr>
					<td><input type="checkbox" name="zutat" value="Admin rights"></td>
					<td>acess to all users</td>
				</tr>
				<tr>
					<td colspan=2><input type="submit" value="Add camera"/></td>
				</tr>
			</table>
		</form>
	</body>
</html>