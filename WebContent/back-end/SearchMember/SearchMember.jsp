<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member_profile.model.*"%>

<%	
	MemberProfileService memberProfileService = new MemberProfileService();
	List<MemberProfileVO> list = memberProfileService.getAll();
	request.setAttribute("list", list);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
	
	<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
	<jsp:include page="/back-end/back_top.jsp" /> <!-- navbar -->
</head>
<style type="text/css">
	h3{
		font-weight: 600;
	}
	
	.line{
		margin-bottom: 5px;
	}

	p ,span {
				font-size: 10pt;
				margin: 0px 0px 5px 0px;
				color:#000000;
	}
	
	.glyphicon {
	color:#DCE6D2;
	}
	
	.glyphicon-pencil{
	color:#000000;
	}
	
</style>
<body>


	<!-- 1層大框框 -->
	<div class="container-fluid">
		<div class="row">
	<!-- 2層框左 -->	
			<jsp:include page="/back-end/back_left.jsp" /> <!-- leftSidebar -->
	<!-- 2層框右 -->		
			<div class="col-xs-12 col-sm-8 col-sm-offset-3" >				
				<div class="block-center panelheight">
				
	<!--========================== 功能放這邊 ===↓↓↓↓↓↓↓↓↓↓==========================================-->
	
	<div style="width:520px;margin:0px auto;margin-top:30px;height:100px;">
		  <div class="text-center">
		  	<h2>會員搜尋</h2>
		  </div>
		  <select class="myselect" style="width:500px;">
		  	<option >請輸入會員名稱</option>
		  	<c:forEach var="memberProfileVO" items="${list}">
		  		<option>${memberProfileVO.getMem_name()}</option>
		  	</c:forEach>
		  </select>
	</div>
	<div id="mem_img"></div>
	
	
	<!--========================== 功能放這邊 ====↑↑↑↑↑↑↑↑↑=====================================-->			
				</div><!-- class="block-center panelheight" -->			
			</div><!-- class="col-xs-12 col-sm-8 col-sm-offset-3" -->
		</div><!-- div class="row" -->
	</div><!-- div class="container-fluid" -->
	
	<jsp:include page="/back-end/back_foot.jsp" />


	
	
</body>
<script type="text/javascript" src="js/select/js/select2.min.js"></script>
<link href="js/select/css/select2.min.css" rel="stylesheet" />
<script type="text/javascript">
      $(".myselect").select2();
      $(document).ready(function(){
 		 $('.myselect').change(function(){
 			 $.ajax({
 				 type: "POST",
 				 url: "/BA104G2/searchProfile/searchProfile.do",
 				 data: creatQueryString($(this).val()),
 				 success: function (data){
	  	 			$('#mem_img').empty();
 					$('#mem_img').append(data);
 				 },
 		         error: function(){alert("網路忙線中!")}
 	        })
 		 })
 	})
 	function creatQueryString(mem_name){
 		console.log("mem_name:"+mem_name);
 		var queryString= {"action":"memberName","mem_name":mem_name};
 		return queryString;
 	}
</script>
</html>