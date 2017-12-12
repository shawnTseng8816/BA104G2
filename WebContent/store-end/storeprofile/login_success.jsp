<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>stologinsuccess.jsp</title>
</head>
<body>

	<br>
	<table border='1' cellpadding='5' cellspacing='0' width='210' align='center'>
		<tr bgcolor='orange' align='center' valign='middle' height='20'>
			 <td>   
			       <h3> 登入成功的頁面 - stologinsuccess.jsp           </h3> 
				     <h3> 歡迎:<font color=red> ${storeprofileVO.getSto_name()} </font>您好</h3>
			 </td>
		</tr>
	</table>              
	<%@ include file="/store-end/storeprofile/login.jsp" %>
	</b>
	
	<ul>
  		<li><a href='<%=request.getContextPath()%>/front-end/storeprofile/addad.jsp'>新增廣告</a></li>
  		<li><a href='<%=request.getContextPath()%>/front-end/storeprofile/adlist.jsp'>個人廣告list- adlist.jsp</a></li>
	</ul>
	
</body>
</html>
