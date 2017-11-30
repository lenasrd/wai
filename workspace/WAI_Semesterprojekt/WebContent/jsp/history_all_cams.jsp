<%@ page language="java" contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<title>${headline}</title>
		<style>body {background-color: powderblue;}</style>
		<script>
	        function sendAction(key, target)
	        {
	        	document.getElementById('key').value	=key;
	        	document.getElementById('target').value	=target;
	        	document.getElementById('historyAllTable').submit();
	        } 
        </script>
	</head>
	<body>
		<h1 align="center">${headline}</h1>
		<h2 align="center">${day}.${month}.${year}, ${ImageList[0].time} - ${ImageList[fn:length(ImageList)-1].time}</h2>
		<form action="history" name="appform" method="post" id="historyAllTable">
			<table align="center" cellpadding="2" cellspacing="2">
				<tr>
					<td>
						<div style="width:400px;height:600px;overflow:auto;padding:5px;border:1px solid black;">
							<table cellpadding="2" cellspacing="5">
								<tr>
									<th width="25%">Cam ID</th>
									<th width="25%">Time</th>
									<th width="25%">Image</th>
									<th width="25%">Zoom</th>
								</tr>
								<c:forEach items="${ImageList}" var="imageinthisrow" varStatus="status">
									<tr>
								        <td align="center">${ImageList[status.index].camId}</td>
								        <td align="center">${ImageList[status.index].time}</td>
								        <td align="center"><img src="${pageContext.request.contextPath}${ImageList[status.index].thumbPath}" height="80" width="80"></td>
								       	<td align="center"><button onclick="sendAction('zoom', '${ImageList[status.index].id}')" style="background: url(https://image.flaticon.com/icons/png/512/49/49116.png); height: 30px; width: 30px; background-size: 24px 24px" ></button></td>
								    </tr>
								</c:forEach>
							</table>
						</div>
					</td>
				<tr>
				<tr>
					<td> &nbsp;</td>
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