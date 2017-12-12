<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@ page import="java.util.*"%>
<%@ page import="com.store_profile.model.*"%>  

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>瀏覽店家</title>

<style type="text/css">
	img{
		display: inline-block;
		float:left;
		margin: 0 10px 0 0;
	}
	#stolist{
		width:100%;
		overflow: auto;
		
	}

	#stolist td{
		padding: 10px;
		width:30%;
		align:center;
	}
	.wrap{		
		border:1px solid #DCE6D2;
		padding: 10px;
		border-radius:10px;
		background:#FFFFFF;
	}
	
	.wrap:hover{
		border-color:#EFBC56;
		border-width: unset;
	}
	.color-org{
		color:#FA5532;
	}
	.title{
		color:#3C9682;
		font-weight:border;	
	}
	a:hover {
    	text-decoration: none;
	}
	.bg-img{
				margin: auto;
		height: 1020px;
		padding: 40px;
		background-repeat: no-repeat;
		background-position: center center;
		background-size: cover;
		background-attachment: fixed; 
		
		height:1020px;
		background-image: url('<%= request.getContextPath() %>/img/bg02.jpg');
	}
	.title-block{				
				margin: 10px;
				height:80px;
				color:#FFFFFF;
				border:1px solid #FFFFFF;
				border-radius:10px;
	}
	.title-notfound{
		margin: 5px 0px ;
		font-size:36px;
		color:#FFFFFF;
		letter-spacing:2px;
		text-shadow: rgb(44, 66, 58) 0px 1px 2px;
	}
</style>
</head>
<script src="https://code.jquery.com/jquery.js"></script>
<body>
<%
	TreeSet<StoreProfileVO> stoList = (TreeSet<StoreProfileVO>) request.getAttribute("stoList");
	pageContext.setAttribute("stoList",stoList);

%>
<jsp:include page="/front-end/member_top.jsp" />
<jsp:include page="/front-end/coupon_notify.jsp" />




	<div class="container-fliud area50  bg-img">
			<div class="row">
				<div class="col-xs-12 col-sm-10 col-sm-offset-1">

				<% if (stoList.size()==0){ %>
					
					
					<h1 class="title-notfound text-center">找不到符合' ${keywordorg} '的關鍵字，或許您可以參考看看其他店家：</h1>
							
					
				
					<jsp:include page="storeListAll.jsp"/>
				
				<% } %>
				
				<% if (stoList.size()>0){ %>
					<div class="title">
						<div class="text-center title-block area70 shadow"> 
							<div class="title-word">符合關鍵字' ${keywordorg} '搜尋結果：</div>
						</div>
					</div>
					<table id="stolist">
						<c:forEach var="stoVO" items="${stoList}" varStatus="i">
							<c:if test="${i.count%4==0 || i.last }">
								<tr>
							</c:if>
							<td>
							<div class="wrap">
								<img height=100 src="<%= request.getContextPath()%>/StoGifReader?sto_num=${stoVO.sto_num}">	
								<a href="<%=request.getContextPath()%>/store_detail/store_detail.do?sto_num=${stoVO.sto_num}"><h3 class="title">${stoVO.sto_name}</h3></a>
								<span>${stoVO.address}</span><br>
								<h5 class="glyphicon glyphicon-map-marker color-org"> 距離 ${stoVO.distance} 公里</h5>	
							</div>							
							</td>
						</c:forEach>
					</table>
				<% } %>
				</div>

			</div>
	</div>
	
	<!--footer-->
<jsp:include page="/front-end/member_foot.jsp" />

</body>
</html>