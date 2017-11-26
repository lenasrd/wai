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
					<td>
						<select id="whichMonth" name="whichMonth" style="width: 80px;">
								<c:forEach items="${months}" var="month">
							        <option value="${month}">${month}</option>
							    </c:forEach>
						</select>
					</td>
					<td><input type="submit" name="key" value="Submit_month"/></td>
				</tr>
				<tr>
				<td align="center" colspan=2 style="font-size: 35px;">&#8226 &#8226 &#9702 &#9702 &#9702</td>
				</tr>
			</table>
		</form>
	</body>
</html>