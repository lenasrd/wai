<%@ page language="java" contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<title>Main menu</title>
		<style>body {background-color: powderblue;}</style>
		<script>
	        function sendAction(key, target)
	        {
	        	document.getElementById('key').value	=key;
	        	document.getElementById('target').value	=target;
	        	document.getElementById('mainMenuTable').submit();
	        } 
        </script>
	</head>
	<body>
		<h1 align="center" >Main menu</h1>
		<form action="main_menu" name="appform" method="post" id="mainMenuTable">
			<table align="center" cellpadding="2" cellspacing="2">
				<tr>
					<td align="center">Available cameras</td>
				</tr>
				<tr>
					<td>
						<div style="width:500px;height:465px;overflow:auto;padding:5px;border:1px solid black;">
							<table cellpadding="2" cellspacing="5">
								<tr>
									<th>ID</th>
									<th>Name</th>
									<th>Last record</th>
									<td>&nbsp;</td>
									<th>Timestamp</th>
									<td>&nbsp;</td>
								</tr>
								<c:forEach items="${CamList}" var="caminthisrow" varStatus="status">
									<tr>
								        <td>${caminthisrow.id}</td>
								        <td>${caminthisrow.name}</td>
								        <td><img src="${pageContext.request.contextPath}${ImageList[status.index].thumbPath}" height="80" width="80"></td>
								       	<td><button onclick="sendAction('zoom', '${ImageList[status.index].id}')" style="background: url(https://image.flaticon.com/icons/png/512/49/49116.png); height: 30px; width: 30px; background-size: 24px 24px"></button></td>
								        <td>${ImageList[status.index].timeStamp}</td>
										<td><input type="submit" onclick="sendAction('browse_history', '${caminthisrow.id}')" value="browse history"/></td>
								    </tr>
								</c:forEach>
							</table>
						</div>
					</td>
				<tr>
				<tr>
					<td align="center"><input type="submit" onclick="sendAction('Browse_history_of_all_cameras')" value="Browse history of all cameras"/></td>
				</tr>
				<tr>
					<td align="center"><input type="${adminVisibility}" onclick="sendAction('Administration')" value="Administration"/></td>
				</tr>
				<tr>
					<td align="center"><input type="submit" onclick="sendAction('Logout')" value="Logout"/></td>
				</tr>
				
			</table>
			<input id="key" 	type=hidden name="key">
			<input id="target" 	type=hidden name="target">
		</form>
	</body>
</html>