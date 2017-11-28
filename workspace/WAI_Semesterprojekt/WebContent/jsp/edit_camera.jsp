<%@ page language="java" contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<title>Add camera</title>
	</head>
	<body>
		<h1 align="center" >Edit camera</h1>
		<form action="admin" name="appform" method="post">
			
			<table align="center" cellpadding="2" cellspacing="2">
				<tr>
					<td>Name</td>
					<td><input type="text" name="name" value="${TargetCam.name}" size="60" width="200px"/></td>
				</tr>
				<tr>
					<td>Source</td>
					<td><input type="text" name="url" value="${TargetCam.url}" size="60" width="200px"/></td>
				</tr>
				
				<tr>
					<td colspan=2><input type="submit" name="key" value="Edit_camera_submit"/></td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan=2><input type="submit" onclick="sendAction('back_to_administration')" value="back"/></td>
				</tr>
			</table>
			<input id="target" 	type=hidden name="target" value="${TargetCam.id}">

		</form>
	</body>
</html>