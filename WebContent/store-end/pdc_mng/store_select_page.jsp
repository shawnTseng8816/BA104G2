<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>
<jsp:useBean id="pdcTSvc" scope="request" class="com.product_type.model.ProductTypeService"/>

<!-- session attibute(sto_num,sto_num) -->

<html>
<head>
	

	<title>店家商品管理</title>
	<style>
	
	
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
	
	
	
<% 
	if(request.getAttribute("stolistAllProduct2")==null){
		String getAllPdc = "getAllPdc";
 		request.setAttribute("getAllPdc",getAllPdc);	
	}
%>

<jsp:include page="/store-end/pdc_mng/btn_select.jsp" />	



<%-- 查詢+ListAll按鈕+add按鈕 --%>
	
			<div id="listAll">
				<% if (request.getAttribute("getAllExt")!=null){
					request.removeAttribute("getAllPdc");
				%>
       				<jsp:include page="stolistAllExtra.jsp" />
				<% } %>	
				
				<% if (request.getAttribute("getAllIce")!=null){ 
					request.removeAttribute("getAllPdc");
				%>
       				<jsp:include page="stolistAllIce.jsp" />
				<% } %>
				
				<% if (request.getAttribute("getAllSwt")!=null ){ 
					request.removeAttribute("getAllPdc");
				%>
       				<jsp:include page="stolistAllSweet.jsp" />
				<% } %>

				<% if (request.getAttribute("getAllPdc")!=null){ %>
       				<jsp:include page="stolistAllProduct.jsp" />
				<% } %>	
				
				<% if (request.getAttribute("stolistAllProduct2")!=null){ 
					request.removeAttribute("getAllPdc");
				%>
       				<jsp:include page="stolistAllProduct2.jsp" />
				<% } %>							
			</div>

		
		
		
		
		
	<!--========================== 功能放這邊 ↑↑↑↑↑↑↑=======================================-->			
				</div><!-- class="block-center panelheight" -->			
			</div><!-- class="col-xs-12 col-sm-8 col-sm-offset-3" -->
		</div><!-- div class="row" -->
	</div><!-- div class="container-fluid" -->
	
	<jsp:include page="/store-end/store_foot.jsp" />
	
	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script>

	</script>
</body>
</html>