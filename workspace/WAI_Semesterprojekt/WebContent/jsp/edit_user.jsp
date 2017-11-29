<%@ page language="java" contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<title>Edit user</title>
		<script>
	        function sendAction(key, target)
	        {
	        	document.getElementById('key').value	=key;
	        	document.getElementById('target').value	=target;
	        	document.getElementById('mainMenuTable').submit();
	        } 
	        function setPermission()
	        {
	        	if(document.getElementById('permission_checkbox').checked) {
	        		document.getElementById('camTable').disabled = true;
	        	} else {
	        		document.getElementById('camTable').disabled = false;
	        	}
	        } 
        </script>
	</head>
	<body>
		<h1 align="center" >Edit user</h1>
		<form action="admin" name="appform" method="post">
			<table align="center" cellpadding="2" cellspacing="2">
				<tr>
					<td>Username</td>
					<td><input type="text" name="username" value="${TargetUser.username}" size="20" /></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="text" name="password" value="${TargetUser.password}" size="20" /></td>
				</tr>
				<tr>
					<td>Camera rights</td>
				</tr>
				<tr>
					<td colspan="2">
						<div style="width:240px;height:200px;overflow:auto;padding:5px;border:1px solid black;">
							<table id="camTable" cellpadding="2" cellspacing="2" <c:if test="${TargetUser.permissionLevel == TargetUser.PERMISSION_LEVEL_ADMIN}">disabled</c:if>>
								<c:forEach items="${CamList}" var="caminthisrow" varStatus="status">
									<tr>
								    	<td><input type="checkbox" name="check_list[${status.index}]" <c:if test="${allowedCams[status.index]}">checked</c:if> /></td>								    	
								    	<td>${caminthisrow.id}</td>
								        <td>${caminthisrow.name}</td>
								    </tr>
								</c:forEach>
							</table>
						</div>
					</td>
					
				</tr>
				<tr>	
					<td>Admin rights</td>				
					<td><input id="permission_checkbox" type="checkbox" name="permission" <c:if test="${TargetUser.permissionLevel == TargetUser.PERMISSION_LEVEL_ADMIN}">checked</c:if> onclick="setPermission()"/></td>
				</tr>
				<tr>
					<td colspan=2><input type="submit" name="key" value="Edit_user_submit"/></td>
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
			<input id="target" 	type=hidden name="target" value="${TargetUser.id}">
			<input id="key" 	type=hidden name="key">
		</form>
	</body>
</html>