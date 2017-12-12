<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>login_success.jsp</title>
</head>
<body>
	<br>
	<table border='1' cellpadding='5' cellspacing='0' width='400'>
		<tr bgcolor='orange' align='center' valign='middle' height='20'>
			 <td>   
			 	<h3> 登入成功的頁面 - login_success.jsp           </h3> 
			 	<h3> 歡迎:<font color=red> ${backstagemanagementVO.getBm_name()} </font>您好</h3>
			 </td>
		</tr>
	</table>
	<b> 
		<br>
		<br>                
		以下留空....
	</b>
<!-- 	  	<ul> -->
<%-- 	  		<li><a href='<%=request.getContextPath()%>/back-end/backstagemanagement/bm_ad_control.jsp'>個人廣告list- adlist.jsp</a></li> --%>
<!-- 	  	</ul> -->
	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/backstagemanagement/bm.do" >
		<ul>
	  		<li>
	  			<input type="submit" value="後台人員審核廣告 list- bm_ad_control.jsp"> 
	  			<input type="hidden" name="action"	value="sa_addmode_select">
		
				<input type="text" name="requestURL"	value="<%=request.getServletPath()%>">
	  		</li>
		</ul>
	</FORM>	
</body>
</html>
