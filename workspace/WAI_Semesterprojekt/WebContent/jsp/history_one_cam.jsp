<%@ page language="java" contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<title>${headline}</title>
	</head>
	<body>
		<h1 align="center">${headline}</h1>
		<h2 align="center">${day}.${month}.${year}, ${hourStart} - ${hourEnd}</h1>
		<form action="history" name="appform" method="post">
			<table align="center" cellpadding="2" cellspacing="2">
				<tr>
					<td>
						<div style="width:250px;height:400px;overflow:auto;padding:5px;border:1px solid black;">
							<table cellpadding="2" cellspacing="5">
								<tr>
									<th>Record</th>
									<td>&nbsp;</td>
									<th>Timestamp</th>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td><img src="http://cdn.onlinewebfonts.com/svg/img_148071.png" height="50"></td>
									<td><button name="key" value="zoom" style="background: url(https://image.flaticon.com/icons/png/512/49/49116.png); height: 30px; width: 30px; background-size: 24px 24px"></button></td>
									<td>15:00</td>
								</tr>
								<c:forEach items="${ImageList}" var="imageinthisrow" varStatus="status">
									<tr>
								        <td>${imageinthisrow.id}</td>
								        <td>${imageinthisrow.camId}</td>
								        <td><img src="${pageContext.request.contextPath}${ImageList[status.index].thumbPath}" height="80" width="80"></td>
								       	<td><button onclick="sendAction('zoom', '${ImageList[status.index].id}')" style="background: url(https://image.flaticon.com/icons/png/512/49/49116.png); height: 30px; width: 30px; background-size: 24px 24px"></button></td>
								        <td>15:00, 15.11.17</td>
								    </tr>
								</c:forEach>
							</table>
						</div>
					</td>
				<tr>
			</table>
		</form>
	</body>
</html>