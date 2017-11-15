<%@ page language="java" contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<title>Administration</title>
	</head>
	<body>
		<h1 align="center" >Administration</h1>
		<form action="Controller" name="appform" method="get">
			<table align="center" cellpadding="2" cellspacing="2">
				<tr>
					<td>Users</td>
					<td>Cameras</td>
				</tr>
				<tr>
					<td>
						<div style="width:200px;height:250px;overflow:auto;padding:5px;border:1px solid black;">
							<table cellpadding="2" cellspacing="2">
								<tr>
									<td>User 1</td>
									<td><input type="submit" name="key" value="edit"/></td>
									<td><input type="submit" name="key" value="remove"/></td>
								</tr>
							</table>
						</div>
					</td>
					<td>
						<div style="width:200px;height:250px;overflow:auto;padding:5px;border:1px solid black;">
							<table cellpadding="2" cellspacing="2">
								<tr>
									<td>Camera 1</td>
									<td><input type="submit" name="key" value="edit"/></td>
									<td><input type="submit" name="key" value="remove"/></td>
								</tr>
							</table>
						</div>
					</td>
				<tr>
				<tr>
					<td><input type="submit" name="key" value="Add new user"/></td>
					<td><input type="submit" name="key" value="Add new camera"/></td>
				</tr>
			</table>
		</form>
	</body>
</html>



