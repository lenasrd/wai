<%@ page language="java" contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<title>Administration</title>
		<script>
	        function sendAction(key, target)
	        {
	        	document.getElementById('key').value	=key;
	        	document.getElementById('target').value	=target;
	        	document.getElementById('AdministrationTable').submit();
	        } 
        </script>
	</head>
	<body>
		<h1 align="center" >Administration</h1>
		<form action="admin" name="appform" method="post" id="AdministrationTable">
			<table align="center" cellpadding="2" cellspacing="2">
				<tr>
					<td>Users</td>
					<td>Cameras</td>
				</tr>
				<tr>
					<td>
						<div style="width:300px;height:250px;overflow:auto;padding:5px;border:1px solid black;">
							<table cellpadding="2" cellspacing="2">
								<c:forEach items="${UserList}" var="userinthisrow">
									<tr>
										<td>${userinthisrow.id}</td>
								        <td>${userinthisrow.username}</td>
								    	<td><input type="submit" onclick="sendAction('edit_user', 	'${userinthisrow.id}')" value="edit"	/></td>
								    	<td><input type="submit" onclick="sendAction('delete_user', '${userinthisrow.id}')" value="remove"	/></td>
								    	
								    </tr>
								</c:forEach>
							</table>
						</div>
					</td>
					<td>
						<div style="width:300px;height:250px;overflow:auto;padding:5px;border:1px solid black;">
							<table cellpadding="2" cellspacing="2">
								<c:forEach items="${CamList}" var="caminthisrow">
									<tr>
										<td>${caminthisrow.id}</td>
								        <td>${caminthisrow.name}</td>
								    	<td><input type="submit" onclick="sendAction('edit_cam', 	'${caminthisrow.id}')" value="edit"	/></td>
								    	<td><input type="submit" onclick="sendAction('delete_cam', 	'${caminthisrow.id}')" value="remove"	/></td>
								    	
								    </tr>
								</c:forEach>
							</table>
						</div>
					</td>
				<tr>
				<tr>
					<td><input type="submit" onclick="sendAction('add_new_user')" 	value="Add new user"/></td>
					<td><input type="submit" onclick="sendAction('add_new_camera')" value="Add new camera"/></td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>	
				<tr>
					<td  colspan=2><input type="submit" onclick="sendAction('back_to_main')" value="back to mainmenu"/></td>
				</tr>
				
				
			</table>
			
			<input id="key" 	type=hidden name="key">
			<input id="target" 	type=hidden name="target">
		</form>
	</body>
</html>
