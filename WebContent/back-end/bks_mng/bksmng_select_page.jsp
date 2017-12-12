<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.backstage_management.model.*"%>
<jsp:useBean id="pdcTSvc" scope="request" class="com.product_type.model.ProductTypeService"/>

<!-- session attibute(sto_num,sto_num) -->

<html>
<head>
<title>後台人員管理</title>
<style>
</style>

</head>

<body>
	<jsp:include page="/back-end/back_top.jsp" /> <!-- navbar -->
	<!-- 1層大框框 -->
	<div class="container-fluid">
		<div class="row">
	<!-- 2層框左 -->	
			<jsp:include page="/back-end/back_left.jsp" /> <!-- leftSidebar -->
	<!-- 2層框右 -->		
			<div class="col-xs-12 col-sm-8 col-sm-offset-3" >				
				<div class="block-center panelheight">
				
	<!--========================== 功能放這邊 ===↓↓↓↓↓↓↓↓↓↓==========================================-->
	
	
	
<% 
// 	BackstageManagementService bmSvc = new BackstageManagementService();		
// 	List<BackstageManagementVO> getAllStaff = bmSvc.getAll();
//  	request.setAttribute("getAllStaff",getAllStaff);	

%>

  <jsp:include page="/back-end/bks_mng/btn_select.jsp" />	

				
<%-- 查詢+ListAll按鈕+add按鈕 --%>


				
<!-- 			<div id="listAll"> -->
<%-- 				<% if (request.getAttribute("getAllStaff")!=null){ %> --%>
       				<jsp:include page="listAllStaff.jsp" />
<%-- 				<% } %>	 --%>

<!-- 			</div> -->

		
		
		
		
	<!--========================== 功能放這邊 ====↑↑↑↑↑↑↑↑↑=====================================-->			
				</div><!-- class="block-center panelheight" -->			
			</div><!-- class="col-xs-12 col-sm-8 col-sm-offset-3" -->
		</div><!-- div class="row" -->
	</div><!-- div class="container-fluid" -->
	
	<jsp:include page="/back-end/back_foot.jsp" />

	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>