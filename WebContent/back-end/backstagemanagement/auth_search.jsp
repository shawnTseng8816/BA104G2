<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.store_profile.model.*"%>

<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
	
	<title>店家身份審核驗證</title>

	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
	<!--[if lt IE 9]>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
	
	<style type="text/css">
		.body{
			font-family:Microsoft JhengHei;
		}
		.caption {
			font-size: 36px;
			color:#595942;
			font-weight:bold;
		}
		table{
			border:1px solid #595942; 
			table-layout:automatic;
			border-collapse: collapse;
/* 			width: 1600px; */
 			word-break: keep-all;
/*   		    word-break:break-all; */
/*   		    word-break: normal; */
 		    word-wrap:break-word;
		}
/* 		:after, :before{ */
/* 			width:500px; */
/* 		} */
		td, th{
			padding:5px;
			border:1px solid #595942; 
			border-style:dashed;
			/*https://www.1keydata.com/css-tutorial/tw/border.php*/ 
			color:#555;
			vertical-align: middle;
		}
		tr:hover{
			font-weight: bold;
			background-color:#f3f3f3;
		}
		.headerrow{ 
			background-color:#595942 !important;
		}
		.headerrow th{
			border:1px solid #595942; 
			color:#ffffff; 
			text-align:center;
		}
		tr:nth-child(even) {
			background-color:#acaca0;
		}
		tr:nth-child(odd) {
			background-color:#ffffff;
			font-color:#555555;
		}
		button, html input[type=button], input[type=reset], input[type=submit]{
			-webkit-appearance:button;
			cursor:pointer;
			border-color:#595942;
			color:#595942;
			background-color:#ffffff;
			border:1px #595942;
			border-style:solid;
			font-family:Microsoft JhengHei;

		}
		button, html input[type=button], input[type=reset], input[type=submit]:hover{
			border-color:#595942;
			color:#ffffff;
			background-color:#595942; 
		}
		div#contain{
			height:100px; 
			overflow-y:scroll; 
			overflow-x:auto;
			-webkit-overflow-scrolling: touch;
			text-align:left;
/* 			https://scotch.io/tutorials/customize-the-browsers-scrollbar-with-css */
/* 			https://codepen.io/devstreak/pen/dMYgeO */
		}
		
		#contain::-webkit-scrollbar-track{
			-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
			border-radius: 10px;
			background-color: #F5F5F5;
		}

		#contain::-webkit-scrollbar{
			width: 12px;
			background-color: #F5F5F5;
		}

		#contain::-webkit-scrollbar-thumb{
			border-radius: 10px;
			-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
			background-color: #595942;
		}
		
		#outtag{
			width:150px;  !important
		}
		
		td>img{
/* 		 	position:relative; */
			width:100%; 
			border-radius: 5px;
    		cursor: pointer;
    		transition: 0.3s;
		}
		
		td>img:hover{
			float:left;
            width: 200%;
            position: relative;
            border: 1px dotted #595942;
		}
		
		.minizeStyle
        {
        	position: relative;
            width: 100%;
        }
        .maximizeStyle
        {
			float:left;
            width: 400%;
            position: relative;
            border: 1px dotted #595942;
        }
	</style>

</head>

<body bgcolor='white'>

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

<div class="caption">店家身份審核驗證</div>  <!-- /back-end/backstagemanagement/auth_search.jsp -->


<table id="block">
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<ul>
			<img src="<%= request.getContextPath() %>/img/xx.png" height="30" width="30">
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color:red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	<thead>
		<tr class="headerrow">
			<th>商店編號</th>
			
			<th>商店名稱</th>
			<th>商店負責人</th>
			<th>商店電話號碼</th>
			<th>商店所在地區</th>
			<th>商店地址</th>
			<th>商店信箱</th>
			<th>商店設立時間</th>
			<th>商店LOGO</th>
			<th>商店介紹</th>
		
			<th>商店上下架狀態</th>
			<th>篩選按鈕</th>
		</tr>
	</thead>
	
	<tbody>		
		<c:if test="${not empty appstatus}">	
			<c:forEach var="storeprofileVO" items="${appstatus}">
				<tr align='center' valign='middle'>
					<td>${storeprofileVO.getSto_num()}</td>
				
					<td>${storeprofileVO.getSto_name()}</td>
					<td>${storeprofileVO.getGuy()}</td>
					<td>${storeprofileVO.getMobile()}</td>
					<td>${storeprofileVO.getArea()}</td>
					<td>${storeprofileVO.getAddress()}</td>
					<td>${storeprofileVO.getEmail()}</td>
					<td>${storeprofileVO.getSet_time()}</td>
					<td><img src="/BA104G2/GetPic?sto_num=${storeprofileVO.getSto_num()}&getType=storeProfile"></td>
					<td>
						<div id="Contain">
							${storeprofileVO.getSto_introduce()}
						</div>
					</td>
			
					<td>${storeprofileVO.getSto_status()}</td>
					
					<td>
						<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/backstagemanagement/BackstageManagementServletChi" style="margin-bottom: 0px;" enctype="multipart/form-data"> 
					    	<input type="submit" value="申請通過">
					    	<input type="hidden" name="sto_num" 		 value="${storeprofileVO.getSto_num()}">
					    	<input type="hidden" name="sto_acc" 		 value="${storeprofileVO.getSto_acc()}">
					    	<input type="hidden" name="sto_name"  	 value="${storeprofileVO.getSto_name()}">
					    	<input type="hidden" name="guy"     		 value="${storeprofileVO.getGuy()}">
					    	<input type="hidden" name="mobile"  		 value="${storeprofileVO.getMobile()}">
							<input type="hidden" name="area"           value="${storeprofileVO.getArea()}">
							<input type="hidden" name="address"        value="${storeprofileVO.getAddress()}">
							<input type="hidden" name="email"          value="${storeprofileVO.getEmail()}">
							<input type="hidden" name="set_time"       value="${storeprofileVO.getSet_time()}">
							<input type="hidden" name="set_time"       value="${storeprofileVO.getSto_logo()}">
							<input type="hidden" name="sto_introduce"  value="${storeprofileVO.getSto_introduce()}">
							<input type="hidden" name="rem_point"      value="${storeprofileVO.getRem_point()}">
					    	<input type="hidden" name="sto_status" 	 value="${storeprofileVO.getSto_status()}">
							<input type="hidden" name="requestURL"	 value="<%=request.getServletPath()%>">
					    	<input type="hidden" name="action"		 value="auth_change">
					    </FORM>
					</td>
				</tr>
			</c:forEach>
		</c:if>
	</tbody>			
</table>

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