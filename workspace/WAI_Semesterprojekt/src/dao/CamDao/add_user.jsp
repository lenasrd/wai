<%@ page language="java" contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<title>Add user</title>
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
		<h1 align="center" >Add user</h1>
		<form action="admin" name="appform" method="post">
			<table align="center" cellpadding="2" cellspacing="2">
				<tr>
					<td>Username</td>
					<td><input type="text" name="username" placeholder="username" size="20" /></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="text" name="password" placeholder="password" size="20" /></td>
				</tr>
				<tr>
					<td colspan="2">
						<div style="width:240px;height:200px;overflow:auto;padding:5px;border:1px solid black;">
							<table cellpadding="2" cellspacing="2">
								<c:forEach items="${CamList}" var="caminthisrow" varStatus="status">
									<tr>
								    	<td><input type="checkbox" name="check_list[${status.index}]"  /></td>								    	
								    	<td>${caminthisrow.id}</td>
								        <td>${caminthisrow.name}</td>
								    </tr>
								</c:forEach>
							</table>
						</div>
					</td>
					
				</tr>
				<tr>
					<td><input type="checkbox" name="permission" value="Admin rights"></td>
					<td>Admin rights</td>
				</tr>
				
				<tr>
					<td colspan=2><input type="submit" name="key" value="Add_user_submit"/></td>
				</tr>
				
				<tr>
				</tr>
				
				<tr>
					<td align="center"><input type="submit" onclick="sendAction('back_to_administration')" value="back"/></td>
				</tr>
			</table>
			<input id="key" 	type=hidden name="key">
		</form>
	</body>
</html>