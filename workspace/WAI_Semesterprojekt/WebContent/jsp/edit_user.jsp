<%@ page language="java" contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<title>Edit user</title>
	</head>
	<body>
		<h1 align="center" >Add user</h1>
		<form action="admin" name="appform" method="post">
			<table align="center" cellpadding="2" cellspacing="2">
				<tr>
					<td>Username</td>
					<td><input type="text" name="username" size="20" /></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="text" name="password" size="20" /></td>
				</tr>
				<tr>
					<td>Camera rights</td>
					<td>
						<div style="width:135px;height:100px;overflow:auto;padding:5px;border:1px solid black;">
							<table cellpadding="2" cellspacing="2">
								<tr>
									<td><input type="checkbox" name="key" value="val"/></td>
									<td>Cam 1</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="permission" value="Admin rights"></td>
					<td>Admin rights</td>
				</tr>
				<tr>
					<td colspan=2><input type="submit" name="key" value="Edit_user"/></td>
				</tr>
			</table>
		</form>
	</body>
</html>