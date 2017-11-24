<%@ page language="java" contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<title>Browse history camera "camName" (ID xxx)</title>
	</head>
	<body>
		<h1 align="center">Browse history camera "camName" (ID xxx)</h1>
		<form action="history" name="appform" method="post">
			<table align="center" cellpadding="2" cellspacing="2">
				<tr>
					<td>Year</td>
					<td colspan=3>
						<select style="width: 120px;">
							<c:forEach items="${years}" var="year">
						        <option value="${year}">${year}</option>
						    </c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>Month</td>
					<td colspan=3>
						<select style="width: 120px;">
							<c:forEach items="${months}" var="month">
						        <option value="${month}">${month}</option>
						    </c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>Day</td>
					<td colspan=3>
						<select style="width: 120px;">
							<option></option>
						</select>
					</td>
				</tr>
				<tr>
					<td>Timespan</td>
					<td>
						<select style="width: 40px;">
							<option></option>
						</select>
					</td>
					<td>to</td>
					<td>
						<select style="width: 40px;">
							<option></option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan=2><input type="submit" name="key" value="Request_images_one_cam"/></td>
				</tr>
			</table>
		</form>
	</body>
</html>