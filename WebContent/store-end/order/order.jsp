<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<%@ page import="com.order_master.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/3dbtn.css" />
<jsp:useBean id="memSvc" class="com.member_profile.model.MemberProfileService"/>
<jsp:useBean id="orderSvc" class="com.order_master.model.OrderMasterService"/>

<%	

		OrderMasterService ordermasterSvc = new OrderMasterService();
		List list = new ArrayList();
		String sto_num = (String)session.getAttribute("sto_num");
		list = ordermasterSvc.getOneStoWaitModif(sto_num);
		request.setAttribute("getOneStoWaitModif", list);
		list = ordermasterSvc.getOneStoPassOrder(sto_num);
		request.setAttribute("getOneStoPassOrder", list);
	
	
%>


<!DOCTYPE html>
<html lang="">
	<head>
	
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<title>Title Page</title>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
		<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
		
<style type="text/css">
textarea{/* Text Area 固定大小*/
 max-width:350px;
 max-height:250px;
 min-width:350px;
 min-height:250px;


}

th{
    text-align: center;
}
.input-group{
	margin-top: 20px;
}

.stoordere {
background-color: #3c9682;
color:white;
}
.btn-greenc {
    background: #3C9682;
    color: #FFFFFF;
}



</style>
	</head>



	<body >
	
	
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
	
	
<!-- 	**********錯誤訊息**************************** -->
<c:if test="${not empty errorMsgs}">
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
		<center><li style="color:red">${message}</li></center>
		</c:forEach>
	</ul>
</c:if>	

	<div class="container">
    <div class="row">
		<div class="col-md-12">
			<h3>店家訂單管理　</h3>

			<div class="tabbable-panel">
				<div class="tabbable-line">
					<ul class="nav nav-tabs ">
						<li><a class="stoordere" href="#tab_default_1" data-toggle="tab">訂單查詢</a></li>
						<li><a class="stoordere"  href="#tab_default_2" data-toggle="tab">待審核訂單</a></li>
						<li><a class="stoordere"  href="#tab_default_3" data-toggle="tab">處理中訂單</a></li>	
						<li><a class="stoordere"  href="#tab_default_4" data-toggle="tab">已完成訂單</a></li>
					</ul>
					
					
					
					

			<!-- 	// TAB1 內容 ******訂單查詢**************************** -->
					<div class="tab-content">
						<div class="tab-pane <% if(request.getParameter("tab")==null || "tab1".equals(request.getParameter("tab"))){out.print("active");}%>" id="tab_default_1">
						 <div style="background-color:#f5f5f5;">
						 <FORM METHOD="post" style="margin-bottom: 0px;" ACTION="<%=request.getContextPath()%>/OrderMasterServlet" >
					        <b>輸入訂單編號 (如: OM0000000001):</b>
					        <input type="text" name="or_num">
					        <br><b>或輸入會員編號 (如: MB0000000001):</b>
					        <input type="text" name="mem_num">
					        <input type="submit" value="送出">       
					           <input type="hidden" name="sto_num" value="${sto_num}">
					        <input type="hidden" name="action" value="getOrderInfo">
					    </FORM>
					</div>
					
								<div class="panel" >
					<div class="panel-heading stoordere">
					<b><h3><div style="color:white;" class="text-center"  >查詢明細如下</div></h3></b>
				    </div>
				<!-- <div class="panel-body"> -->
					    <table class="table table-hover">
					    	<!-- <caption>我是表格標題</caption> -->
					    <thead>
						<tr align='center'  valign='middle'>
							<th>訂單編號</th>
							<th>會員編號</th>
							<th>訂單更新時間</th>
							<th>訂單狀態</th>
							<th>訂單明細</th>
							<th>操作</th>
						</tr>
				    	</thead>
				   <tbody>
				<c:forEach varStatus="valuecount" var="ordermasterVO" items="${getOrderInfo}">
					<tr align='center' valign='middle'  <c:if test="${valuecount.count%2!=0}" > style="background-color:#f5f5f5;"</c:if> ><!--將修改的那一筆加入對比色而已-->
					<td>
					<c:if test="${ordermasterVO.meror_num==null}">${ordermasterVO.or_num}</c:if>
					<c:if test="${ordermasterVO.meror_num!=null}">團購訂單* ${ordermasterVO.meror_num}</c:if>
					</td>
					<td>${ordermasterVO.mem_num}</td>
					<td><fmt:formatDate value="${ordermasterVO.or_time}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
					<td>${ordermasterVO.or_stanum}</td>
					</tr>
				</c:forEach>
					</tbody>
					
						</table>
						
						</div>
						
						</div>
		<!-- 	// TAB1 結束 **************************************** -->








		<!-- 	// TAB2 內容 ***** 待審核訂單*********************************** -->
			
		<div class="tab-pane <% if("tab2".equals(request.getParameter("tab"))){out.print("active");}%>" id="tab_default_2">
										
				<div class="panel " >
					<div class="panel-heading stoordere"  >
					<b><h3><div style="color:white"  class="text-center" > 審核中訂單</div></h3></b>
				    </div>
				<!-- <div class="panel-body"> -->
					    <table class="table table-hover">
					    	<!-- <caption>我是表格標題</caption> -->
					    <thead>
						<tr align='center'  valign='middle'>
							<th>訂單編號</th>
							<th>會員編號</th>
							<th>訂單更新時間</th>
							<th>訂單狀態</th>
							<th>訂單明細</th>
							<th>操作</th>
						</tr>
				    	</thead>
				   <tbody>
				<c:forEach var="ordermasterVO"  varStatus="valuecount" items="${getOneStoWaitModif}">
					<tr align='center' valign='middle' <c:if test="${valuecount.count%2!=0}" > style="background-color:#f5f5f5;"</c:if> ><!--將修改的那一筆加入對比色而已-->			
						<td>
						<c:if test="${ordermasterVO.meror_num==null}">${ordermasterVO.or_num}</c:if>
						<c:if test="${ordermasterVO.meror_num!=null}">團購訂單* ${ordermasterVO.meror_num}</c:if>
						</td>
						<td>
						<c:if test="${ordermasterVO.meror_num==null}">${ordermasterVO.mem_num}</c:if>
						<c:if test="${ordermasterVO.meror_num!=null}">團購發起人* ${ordermasterVO.mem_num}</c:if>
						</td>
						<td><fmt:formatDate value="${ordermasterVO.or_time}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
						<td>${ordermasterVO.or_stanum}</td>	
									
<!-- 		****訂單明細******************************** -->
						<td>			
						<p class="text-right"><a href='#${ordermasterVO.or_num}' data-toggle="modal" class="btn btn-info">詳情</a></p>
			 	 	<div class="modal fade" id="${ordermasterVO.or_num}">  
<!-- XXXXXXXXXXXXXXXXXXXXXX --> 
							<%@ include file="/store-end/order/orderList.jsp" %>
		</div>

					</td>
<!-- ***************************************訂單明細結束******************************** -->
					
<!-- *************************************************操作按鈕 *************** -->
						<td>
						
			  			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/OrderMasterServlet" style="margin-bottom: 0px;">
					     <p class="text-right"><input type="submit" value="通過" data-toggle="modal" class="btn btn-greenc"></a></p>
					   	 <c:if test="${ordermasterVO.meror_num==null}">
					     <input type="hidden" name="or_num"  	value="${ordermasterVO.or_num}">
					     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->            
					     <input type="hidden" name="action"	    value="passOrder">
					     </c:if>
					 
					     <c:if test="${ordermasterVO.meror_num!=null}">
					     <input type="hidden" name="meror_num"  	value="${ordermasterVO.meror_num}">
					     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->            
					     <input type="hidden" name="action"	    value="groupPassOrder">
					     </c:if>
					      </FORM>
					     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/OrderMasterServlet" style="margin-bottom: 0px;">
					     <p class="text-right"><input type="submit" value="退回" data-toggle="modal" class="btn btn-org"></a></p>
					       <input type="hidden" name="meror_num"  	value="${ordermasterVO.meror_num}">
					     <input type="hidden" name="or_num"  	value="${ordermasterVO.or_num}">
					      <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->            
					     <input type="hidden" name="action"	    value="nopassOrder">
					     
					    </FORM>
						</td>
					</tr>
				</c:forEach>
					</tbody>
					
						</table>
						
						</div>
						</div>
	<!-- 	// TAB2 結束 **************************************** -->



	<!-- 	// TAB３　開始*****************處理中訂單********************* -->
	
			
				<div class="tab-pane <% if("tab3".equals(request.getParameter("tab"))){out.print("active");}%>" id="tab_default_3">
										
				<div class="panel " >
					<div class="panel-heading stoordere">
					<b><h3><div style="color:white" class="text-center" >處理中訂單</div></h3></b>
				    </div>
				<!-- <div class="panel-body"> -->
					    <table class="table table-hover">
					    	<!-- <caption>我是表格標題</caption> -->
					    <thead>
						<tr align='center'  valign='middle'>
							<th>訂單編號</th>
							<th>會員名稱</th>
							<th>訂單更新時間</th>
							<th>訂單狀態</th>
							<th>訂單明細</th>
							<th>操作</th>
						</tr>
				    	</thead>
				   <tbody>
				<c:forEach var="ordermasterVO" varStatus="valuecount" items="${getOneStoPassOrder}">
					<tr align='center' valign='middle'  <c:if test="${valuecount.count%2!=0}" > style="background-color:#f5f5f5;"</c:if>><!--將修改的那一筆加入對比色而已-->
					<td>
					<c:if test="${ordermasterVO.meror_num==null}">${ordermasterVO.or_num}</c:if>
					<c:if test="${ordermasterVO.meror_num!=null}">團購訂單* ${ordermasterVO.meror_num}</c:if>
					</td>
					<td>${memSvc.getOneMemInfo(ordermasterVO.mem_num).mem_name}</td>
					<td><fmt:formatDate value="${ordermasterVO.or_time}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
					<td>${ordermasterVO.or_stanum}</td>	
<!-- 		****訂單明細******************************** -->
						<td><p class="text-right"><a href='#${ordermasterVO.or_num}' data-toggle="modal" class="btn btn-info">詳情</a></p>
	  		
			 	 	<div class="modal fade" id="${ordermasterVO.or_num}">  
							<%@ include file="/store-end/order/orderList.jsp" %>
					</div>

					</td>
						<!-- 		****訂單明細結束******************************** -->

					<td>
					<c:if test="${ordermasterVO.or_stanum.equals('處理中')}">
			  			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/OrderMasterServlet" style="margin-bottom: 0px;">
					     <input type="submit" value="飲料準備好" class="btn btn-danger">    
					     <input type="hidden" name="or_num"  	value="${ordermasterVO.or_num}">
					     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->            
					     <input type="hidden" name="action"	    value="orderAlready"></FORM>
					</c:if>
					<c:if test="${ordermasterVO.or_stanum.equals('外送中')||ordermasterVO.or_stanum.equals('待取貨')}">
						<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/OrderMasterServlet" style="margin-bottom: 0px;">
					     <input type="submit" value="確認交貨" class="btn btn-green">    
					     <input type="hidden" name="or_num"  	value="${ordermasterVO.or_num}">
					     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->            
					     <input type="hidden" name="action"	    value="delivered"></FORM>
					</c:if>
					</td>
					</tr>
				</c:forEach>
					</tbody>
					
						</table>
						
						</div>
						</div>
					
<!-- 	// TAB３　結束**************************************** -->



<!-- 	// TAB4　開始*********************************** -->
						<div class="tab-pane" id="tab_default_4">
						<div class="panel ">
					<div class="panel-heading stoordere">
					<b><h3><div style="color:white" class="text-center" >完成訂單</div></h3></b>
				    </div>
				<!-- <div class="panel-body"> -->
					    <table class="table table-hover">
					    	<!-- <caption>我是表格標題</caption> -->
					    <thead>
						<tr align='center'  valign='middle'>
							<th>訂單編號</th>
							<th>會員編號</th>
							<th>訂單更新時間</th>
							<th>訂單狀態</th>
							<th>訂單明細</th>
							
						</tr>
				    	</thead>
				   <tbody>
				<c:forEach varStatus="valuecount" var="ordermasterVO" items="${orderSvc.getOneStoFinishOrder(sto_num)}">
					<tr align='center' valign='middle'  <c:if test="${valuecount.count%2!=0}" > style="background-color:#f5f5f5;"</c:if> ><!--將修改的那一筆加入對比色而已-->
					<td>
					<c:if test="${ordermasterVO.meror_num==null}">${ordermasterVO.or_num}</c:if>
					<c:if test="${ordermasterVO.meror_num!=null}">團購訂單* ${ordermasterVO.meror_num}</c:if>
					</td>
					<td>${ordermasterVO.mem_num}</td>
					<td><fmt:formatDate value="${ordermasterVO.or_time}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
					<td>${ordermasterVO.or_stanum}</td>	
<!-- 		****訂單明細******************************** -->
						<td><p class="text-right"><a href='#${ordermasterVO.or_num}8' data-toggle="modal" class="btn btn-info">詳情</a></p>
	  		
			 	 	<div class="modal fade" id="${ordermasterVO.or_num}8">  
							<%@ include file="/store-end/order/orderList.jsp" %>
					</div>

					</td>
						<!-- 		****訂單明細結束******************************** -->

				
					</tr>
				</c:forEach>
					</tbody>
					
						</table>
						
						</div>
						</div>
<!-- 	// TAB4　結束**************************************** -->




					</div>
				</div>
			</div>

		
		</div>
	</div>
</div>

<!--========================== 功能放這邊 ↑↑↑↑↑↑↑=======================================-->			
				</div><!-- class="block-center panelheight" -->			
			</div><!-- class="col-xs-12 col-sm-8 col-sm-offset-3" -->
		</div><!-- div class="row" -->
	</div><!-- div class="container-fluid" -->
	
	<jsp:include page="/store-end/store_foot.jsp" />

		<script src="https://code.jquery.com/jquery.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	</body>
	
 <script> 
//WebSocket*****************************************	
	
 

 </script>
</html>