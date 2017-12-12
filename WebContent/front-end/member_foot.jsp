<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<html>

<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
	<title>揪茶趣</title>

	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/member_base.css">

</head>
	<style>
		
	</style>
<body>

		<!--=================================================   ↓↓  footer 從這邊開始   ↓↓  ========================================================== -->

<br>
<br>
<br>
<br>
<div class="container-fluid navbars navbar-fixed-bottom">
	<div class="col-xs-12 col-sm-8 col-sm-offset-2 navbars-menu">
		<div class="list-inline text-center seperate_align">								      
			 <a href="#" class="item-color">揪茶趣手機版</a>			 
		     <a href="#" class="item-color">關於揪茶趣</a>				
		    <a href="<%= request.getContextPath()%>/store-end/storeprofile/addStoreProfile.jsp"  class="item-color">成為店家</a>				 
			<a id="stologin"  href="<%= request.getContextPath()%>/store-end/storeprofile/login.jsp" class="item-color">店家登入</a>			 
		     <a href="#" class=" item-color">教學專區</a>
		</div>	
	</div>
	<div class="col-xs-12 col-sm-12 ">
		<h5 class="text-center item-color">版權所有　<span class="glyphicon glyphicon-copyright-mark">　</span>揪茶趣 All rights reserved.</h5>
	</div>
</div>	



		<!--=================================================   ↑↑  footer 從這邊開始   ↑↑  ========================================================== -->

		<script>



	document.getElementById('stologin').onclick = function(){
		
		sessionStorage.removeItem("currentSRC");
	};
		</script>
</body>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBgGwpHCYQMEnC2S0l-ycO9Df87WvE2gLk&callback=initMap&libraries=geometry,places"></script>

</html>