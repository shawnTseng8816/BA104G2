<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.shop_ad.model.*"%>
<%@ page import="java.util.*"%>

<%
	ShopAdVO shopadVO = (ShopAdVO) session.getAttribute("shopadVO");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<meta http-equiv="refresh" content="3  ; url=<%=request.getContextPath() %>/store-end/storeprofile/adlist.jsp">
<title>廣告已送出</title>
<style>
	table{
		border:1px solid #3C9682;
	}
</style>
</head>
<body>

<jsp:include page="/store-end/store_top.jsp" /> <!-- navbar -->
	<!-- 1層大框框 -->
	<div class="container-fluid">
		<div class="row">
	<!-- 2層框左 -->	
			<jsp:include page="/store-end/store_left.jsp" /> <!-- leftSidebar -->
	<!-- 2層框右 -->		
			<div class="col-xs-12 col-sm-8 col-sm-offset-3" >				
				<div class="block-center panelheight">
				
	<!--========================== 功能放這邊 ↓↓↓↓↓↓===============================-->
	
	
	<br><br><br><br><br><br><br><br><br><br><br>
	<table border='1' cellpadding='5' cellspacing='0' width='210' align='center'>
		<tr bgcolor='#ffffff' align='center' valign='middle' height='20'>
			 <td>   
			    
				     <h3>
<%--  				     	 <jsp:useBean id="storeprofileSvc" scope="page" class="com.store_profile.model.StoreProfileService" />   --%>
<%-- 				     	 <c:forEach var="storeprofileVO" items="${storeprofileSvc.all}">  --%>
				     	 	<c:if test="${shopadVO.sto_num==storeprofileVO.sto_num}">
				     	 		<b>${storeprofileVO.getSto_name()}</b>商店:
				     	 	</c:if>
<%-- 				     	 </c:forEach> --%>
				     	 		<br><font color=red> <b>${storeprofileVO.getGuy()}</b> 老闆</font>您好!
				     </h3>
				            廣告已送出，請等待審核
			 </td>
		</tr>
	</table>              
<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">


<!--========================== 功能放這邊 ↑↑↑↑↑↑↑=======================================-->			
				</div><!-- class="block-center panelheight" -->			
			</div><!-- class="col-xs-12 col-sm-8 col-sm-offset-3" -->
		</div><!-- div class="row" -->
	</div><!-- div class="container-fluid" -->
	
	<jsp:include page="/store-end/store_foot.jsp" />
	
	
</body>
 <script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	

</html>