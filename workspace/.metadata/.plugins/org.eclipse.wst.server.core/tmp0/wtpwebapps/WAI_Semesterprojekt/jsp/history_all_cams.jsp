<%@ page language="java" contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<title>All cameras history</title>
	</head>
	<body>
		<h1 align="center">All cameras history</h1>
		<h2 align="center">15.11.17, 15:00 - 17:00</h1>
		<form action="history" name="appform" method="post">
			<table align="center" cellpadding="2" cellspacing="2">
				<tr>
					<td>
						<div style="width:300px;height:400px;overflow:auto;padding:5px;border:1px solid black;">
							<table cellpadding="2" celpacing="5">
								<tr>
									<th>ID</th>
									<th>Name</th>
									<th>Record</th>
									<td>&nbsp;</td>
									<th>Timestamp</th>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>ID</td>
									<td>Name</td>
									<td><img src="http://cdn.onlinewebfonts.com/svg/img_148071.png" height="50"></td>
									<td><button name="key" value="zoom" style="background: url(https://image.flaticon.com/icons/png/512/49/49116.png); height: 30px; width: 30px; background-size: 24px 24px"></button></td>
									<td>15:00</td>
								</tr>
							</table>
						</div>
					</td>
				<tr>
			</table>
		</form>
	</body>
</html>