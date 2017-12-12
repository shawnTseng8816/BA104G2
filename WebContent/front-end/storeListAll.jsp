<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@ page import="java.util.*"%>
<%@ page import="com.store_profile.model.*"%>  

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>瀏覽店家</title>

<style type="text/css">
/* 	img{ */
/* 		display: inline-block; */
/* 		float:left; */
/* 		margin: 0 10px 0 0; */
/* 	} */


/* 	#stolist td{ */
/* 		padding: 10px; */
/* 		width:30%; */
/* 		align:center; */
/* 	} */
/* 	.wrap{		 */
/* 		border:1px solid #DCE6D2; */
/* 		padding: 10px; */
/* 		border-radius:10px; */
/* 		height:120px; */
/* 	} */
	
/* 	.wrap:hover{ */
/* 		border-color:#EFBC56; */
/* 		border-width: unset; */
/* 	} */
/* 	.color-org{ */
/* 		color:#FA5532; */
/* 	} */
/* 	.title{ */
/* 		color:#3C9682; */
/* 		font-weight:border; */
		
/* 	} */
/* 	a:hover { */
/*     	text-decoration: none; */
/* 	} */

   	img{ 
 		display: inline-block; 
		float:left; 
 		margin: 0 10px 0 0; 
 	} 
 	#stolist{ 
 		width:100%; 
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
 		height:120px;
		background-color:#FFFFFF;
		opacity:0.8;
 		box-shadow:2px 2px 12px 2px #22615345; 
 	}
 	
	.wrap-block{
		padding: 10px;
 		border-radius:10px;
 		height:120px;
		border:1px solid #FFFFFF;
		opacity:0.7;
		background-color:#FFFFFF;
		border-radius:10px;
		box-shadow:2px 2px 12px 2px #22615345; 
	} 
	
 	.wrap-block:hover{ 
		opacity:1; 
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
</style>
</head>
<script src="https://code.jquery.com/jquery.js"></script>
<body>
<%

	StoreProfileService spSvc = new StoreProfileService();
	List<StoreProfileVO> stoList = spSvc.getAllgeo();	
	pageContext.setAttribute("stoList",stoList);

%>



	<div class="container-fliud">
			<div class="row">
					
					<div class="title-block area70 shadow">
						<div class="text-center title-word"> 優質商家</div>
					</div>
					
				
					<table id="stolist">
						<c:forEach var="stoVO" items="${stoList}" varStatus="status">
							<c:if test="${status.count==0}">
								<tr>
							</c:if>
							<td>
							<div class="wrap-block">
								<img height=100 src="<%= request.getContextPath()%>/StoGifReader?sto_num=${stoVO.sto_num}">	
								<a href="<%=request.getContextPath()%>/store_detail/store_detail.do?sto_num=${stoVO.sto_num}"><h3 class="title">${stoVO.sto_name}</h3></a>
								<span>${stoVO.area}${stoVO.address}</span><br>
<%-- 								<h5 class="glyphicon glyphicon-map-marker color-org"> 距離 ${stoVO.distance} 公里</h5>	 --%>
							</div>														
							</td>
							<c:if test="${status.count%3==0}">
								</tr>${staurs.current}
							</c:if>
						</c:forEach>
					</table>
				
				
				

			</div>
	</div>


</body>
</html>