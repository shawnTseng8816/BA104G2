<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.keep_record.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="keepSvc" class="com.keep_record.model.KeepRecordService"/>
<jsp:useBean id="memSvc" class="com.member_profile.model.MemberProfileService"/>
<%

	KeepRecordService keepRecordSvc = new KeepRecordService();
	List list = new ArrayList();
	String sto_num = (String)session.getAttribute("sto_num");
	list = keepRecordSvc.getOneStoKeepRecordNoFinish(sto_num);
	request.setAttribute("getOneStoKeepRecordNoFinish",  list);
	list = keepRecordSvc.getOneStoKeepRecordFinish(sto_num);
	request.setAttribute("getOneStoKeepRecordFinish",  list);
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
.stoordere {
background-color: #3c9682;
color:white;
}

.input-group{
	margin-top: 20px;
}
.btn-greenc {
    background: #3C9682;
    color: #FFFFFF;
}

</style>
	</head>

	<body>
	
<c:if test="${not empty errorMsgs}">
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
		<center><li style="color:red">${message}</li></center>
		</c:forEach>
	</ul>
</c:if>	

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

	
	<div class="container">
    <div class="row">
		<div class="col-md-12">
			<h3>寄杯管理　</h3>
			
			<div class="tabbable-panel">
				<div class="tabbable-line">
					<ul class="nav nav-tabs ">
						<li><a class="stoordere" href="#tab_default_1" data-toggle="tab">寄杯查詢</a></li>
						<li><a class="stoordere" href="#tab_default_2" data-toggle="tab">寄杯申請處理</a></li>
						<li><a class="stoordere" href="#tab_default_3" data-toggle="tab">已完成紀錄</a></li>
						
					</ul>

	<!-- 	// TAB1　開始******************新增集點卡********************** -->
						<div class="tab-content">
						<div class="tab-pane  <% if("tab1".equals(request.getParameter("tabs")) || request.getParameter("tabs")==null){out.print("active");}%> " id="tab_default_1">
						<div style="background-color:#f5f5f5;">								
				 <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/KeepRecordServlet" >
<!-- 					        <b>輸入寄杯編號 (如: KN0000000101):</b> -->
<!-- 					        <input type="text" name="keep_num"> -->
<!-- 					        <br><b>或輸入會員編號 (如: MB0000000001):</b> -->
<!-- 					        <input type="text" name="mem_num"> -->
								
					         <br><b>輸入電話後3碼  (如: 123  此查詢僅供申請中及審核通過之訂單):</b>
					        <input type="text" name="mobile">
					         <input type="hidden" name="sto_num" value="">
					        <input type="submit" value="送出">       					      
					        <input type="hidden" name="action" value="getKeepInfoByMobile">
					    </FORM>
					     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/KeepRecordServlet" >
					          <br>
					        <b>輸入寄杯編號 (如: KN0000000101):</b>				     
					        <input type="text" name="keep_num"><b>或輸入會員編號 (如: MB0000000001):</b>
					        <input type="text" name="mem_num">				     
					         <input type="hidden" name="sto_num" value="${sessionScope.sto_num}">
					        <input type="submit" value="送出">       					      
					        <input type="hidden" name="action" value="getKeepInfo">
					    </FORM>
				</div>	
				
					
								<div class="panel ">
					<div class="panel-heading stoordere">
					<b><h3><div style="color:white" class="text-center"  >查詢明細如下</div></h3></b>
				    </div>
				<!-- <div class="panel-body"> -->
					    <table class="table table-hover">
					    	<!-- <caption>我是表格標題</caption> -->
					    <thead>
						<tr align='center'  valign='middle'>
							<th>寄杯編號</th>
							<th>會員編號</th>
							<th>會員名稱</th>
							<th>寄杯時間</th>
							<th>寄杯狀態</th>
							<th>操作</th>
						</tr>
				    	</thead>
				   <tbody>
					<c:forEach var="keepVO2" varStatus="valuecount" items="${getKeepInfo}">	
					<tr align='center' valign='middle'  <c:if test="${valuecount.count%2!=0}" > style="background-color:#f5f5f5;"</c:if> ><!--將修改的那一筆加入對比色而已-->
						<td>${keepVO2.keep_num}</td>
						<td>${keepVO2.mem_num}</td>
						<td>${memSvc.getOneMemInfo(keepVO2.mem_num).mem_name}</td>
						<td>${keepVO2.keep_time}</td>
						<td>${keepVO2.keep_status}</td>	
						<td>	
						<c:if test="${keepVO2.keep_status.equals('申請中')}">
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/KeepRecordServlet" style="margin-bottom: 0px;">			
			     <input type="submit" class="btn btn-greenc" value="通過"> 
			     <input type="hidden" name="keep_num"      value="${keepVO2.keep_num}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->            
			     <input type="hidden" name="action"	    value="passKeep"></FORM>
			       <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/KeepRecordServlet" style="margin-bottom: 0px;">			
			     <input type="submit" class="btn btn-org" value="退回"> 
			     <input type="hidden" name="keep_num"      value="${keepVO2.keep_num}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->            
			     <input type="hidden" name="action"	    value="noPassKeep"></FORM>
			     </c:if>
			     	<c:if test="${keepVO2.keep_status.equals('審核通過')}">
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/KeepRecordServlet" style="margin-bottom: 0px;">			
			     <input type="submit" value="已領取"> 
			     <input type="hidden" name="keep_num"      value="${keepVO2.keep_num}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->            
			     <input type="hidden" name="action"	    value="FinishKeep"></FORM>
			     </c:if>
			</td>
			
			<td>
						<p class="text-right"><a href='#${keepVO2.ordet_num}' data-toggle="modal" class="btn btn-info">詳情</a></p>
			 	 	<div class="modal fade" id="${keepVO2.ordet_num}">  
						<div class="modal-dialog ">
							<div class="modal-content">
							<div class="modal-header">
							<h4 class="modal-title">訂單明細 </h4>
								</div>
								<div class="modal-body">
								<table class="table">	
								<tr>
								<th>訂購人姓名 :</th><td>${memSvc.getOneMemInfo(keepVO2.mem_num).mem_name}</td>
								</tr>
								<tr>
								<th>訂購人電話 :</th><td>${memSvc.getOneMemInfo(keepVO2.mem_num).mobile} </td>
								</tr>
								<tr>
								<th>名稱</th>
								<th>甜度</th>
								<th>冰塊</th>
								<th>加料</th>
								<th>尺寸</th>
								<th>單價</th>
								<th>數量</th>
								</tr>
<!-- 	******************************非團購明細 ******************************-->							
								<c:forEach var="orderdetailToolVO" items="${keepSvc.getOrderDetailTool(keepVO2.ordet_num)}">
								<tr>
								<td>${orderdetailToolVO.com_name}</td>
								<td>${orderdetailToolVO.sweet_type}</td>
								<td>${orderdetailToolVO.ice_type}</td>
								<td>${orderdetailToolVO.extras}</td>
								<td>${orderdetailToolVO.com_size}</td>
								<td>${orderdetailToolVO.od_price}</td>
								<td>${orderdetailToolVO.amount}</td>	  
								</tr>
							</c:forEach>
			

					
							</table>
					
					</div>
					<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
					</div>
				</div>
			</div>
		</div>
			
			
			</td>
		</tr>
			</c:forEach>
					</tbody>
					
						</table>
						
						</div>
						
						
						</div>
<!-- 	// TAB1　結束**************************************** -->

		<!-- 	// TAB2內容 *******寄杯申請處理******************************* -->
					
						<div class="tab-pane <% if("tab2".equals(request.getParameter("tabs"))){out.print("active");}%>" id="tab_default_2">							
							<div class="panel ">
					 		 <div class="panel-heading stoordere">
					 		<b><h3><div style="color:white" class="text-center"  >寄杯申請處理</div></h3></b>
						  </div>
					  <!-- <div class="panel-body"> -->
					    <table class="table table-hover">
					    	<!-- <caption>我是表格標題</caption> -->
					    	<thead>
						<tr align='center'  valign='middle'>
							<th>寄杯編號</th>
							<th>會員編號</th>
							<th>商品編號</th>
							<th>訂單明細編號</th>
							<th>寄杯時間</th>
							<th>寄杯狀態</th>
							<th>操作</th>
							<th>明細</th>
						</tr>
				    	</thead>
				    	<tbody>
				<c:forEach var="keepVO" varStatus="valuecount" items="${getOneStoKeepRecordNoFinish}">	
					<tr align='center' valign='middle' <c:if test="${valuecount.count%2!=0}" > style="background-color:#f5f5f5;"</c:if>  ><!--將修改的那一筆加入對比色而已-->
						<td>${keepVO.keep_num}</td>
						<td>${keepVO.mem_num}</td>
						<td>${keepVO.com_num}</td>
						<td>${keepVO.ordet_num}</td>
						<td>${keepVO.keep_time}</td>
						<td>${keepVO.keep_status}</td>	
						<td>	
						<c:if test="${keepVO.keep_status.equals('申請中')}">
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/KeepRecordServlet" style="margin-bottom: 0px;">			
			     <input type="submit" class="btn btn-greenc" value="通過"> 
			     <input type="hidden" name="keep_num"      value="${keepVO.keep_num}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->            
			     <input type="hidden" name="action"	    value="passKeep"></FORM>
			       <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/KeepRecordServlet" style="margin-bottom: 0px;">			
			     <input type="submit" class="btn btn-org" value="退回"> 
			     <input type="hidden" name="keep_num"      value="${keepVO.keep_num}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->            
			     <input type="hidden" name="action"	    value="noPassKeep"></FORM>
			     </c:if>
			     	<c:if test="${keepVO.keep_status.equals('審核通過')}">
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/KeepRecordServlet" style="margin-bottom: 0px;">			
			     <input type="submit"  class="btn btn-greenc" value="已領取"> 
			     <input type="hidden" name="keep_num"      value="${keepVO.keep_num}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->            
			     <input type="hidden" name="action"	    value="FinishKeep"></FORM>
			     </c:if>
			</td>
			
			<td>
						<p class="text-right"><a href='#${keepVO.ordet_num}6' data-toggle="modal" class="btn btn-info">詳情</a></p>
			 	 	<div class="modal fade" id="${keepVO.ordet_num}6">  
						<div class="modal-dialog ">
							<div class="modal-content">
							<div class="modal-header">
							<h4 class="modal-title">訂單明細 </h4>
								</div>
								<div class="modal-body">
								<table class="table">	
								<tr>
								<th>訂購人姓名 :</th><td>${memSvc.getOneMemInfo(keepVO.mem_num).mem_name}</td>
								</tr>
								<tr>
								<th>訂購人電話 :</th><td>${memSvc.getOneMemInfo(keepVO.mem_num).mobile} </td>
								</tr>
								<tr>
								<th>名稱</th>
								<th>甜度</th>
								<th>冰塊</th>
								<th>加料</th>
								<th>尺寸</th>
								<th>單價</th>
								<th>數量</th>
								</tr>
<!-- 	******************************非團購明細 ******************************-->							
								<c:forEach var="orderdetailToolVO" items="${keepSvc.getOrderDetailTool(keepVO.ordet_num)}">
								<tr>
								<td>${orderdetailToolVO.com_name}</td>
								<td>${orderdetailToolVO.sweet_type}</td>
								<td>${orderdetailToolVO.ice_type}</td>
								<td>${orderdetailToolVO.extras}</td>
								<td>${orderdetailToolVO.com_size}</td>
								<td>${orderdetailToolVO.od_price}</td>
								<td>${orderdetailToolVO.amount}</td>	  
								</tr>
							</c:forEach>
			

					
							</table>
					
					</div>
					<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
					</div>
				</div>
			</div>
		</div>
			
			
			</td>
		</tr>
			</c:forEach>
				</tbody>
		</table>
	
			</div>
		</div>


		
				
<!-- 	// TAB2 結束 **************************************** -->


		<!-- 	// TAB23內容 ***** 已領取紀錄******************************** -->
			<div class="tab-pane" id="tab_default_3">	
					<div class="panel ">
					  <div class="panel-heading stoordere">
					 <b><h4><div style="color:white"  > 已完成寄杯明細</div></h4></b>
					  </div>
					  <!-- <div class="panel-body"> -->
					    <table class="table table-hover">
					    	<!-- <caption>我是表格標題</caption> -->
					    	<thead>
						<tr align='center'  valign='middle'>
							<th>寄杯編號</th>
							<th>會員編號</th>
							<th>商品編號</th>
							<th>訂單明細編號</th>
							<th>寄杯時間</th>
							<th>訂單最後更新時間</th>
							<th>寄杯狀態</th>
							<th>明細</th>
						</tr>
				    	</thead>
				    	<tbody>
				<c:forEach var="keepVO"  varStatus="valuecount" items="${getOneStoKeepRecordFinish}">		
					<tr align='center' valign='middle' <c:if test="${valuecount.count%2!=0}" > style="background-color:#f5f5f5;"</c:if>  ><!--將修改的那一筆加入對比色而已-->
						<td>${keepVO.keep_num}</td>
						<td>${keepVO.mem_num}</td>
						<td>${keepVO.com_num}</td>
						<td>${keepVO.ordet_num}</td>
						<td>${keepVO.keep_time}</td>
						<td>${keepVO.rec_time}</td>
						<td>${keepVO.keep_status}</td>	
						<td>
					<p class="text-right"><a href='#${keepVO.ordet_num}' data-toggle="modal" class="btn btn-info ">詳情</a></p>
			 	 	<div class="modal fade" id="${keepVO.ordet_num}">  
						<div class="modal-dialog ">
							<div class="modal-content">
							<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h4 class="modal-title">訂單明細 </h4>
								</div>
								<div class="modal-body">
								<table class="table">	
								<tr>
								<th>名稱</th>
								<th>甜度</th>
								<th>冰塊</th>
								<th>尺寸</th>
								<th>單價</th>
								<th>數量</th>
								</tr>
<!-- 	******************************非團購明細 ******************************-->							
								<c:forEach var="orderdetailToolVO" items="${keepSvc.getOrderDetailTool(keepVO.ordet_num)}">
								<tr>
								<td>${orderdetailToolVO.com_name}</td>
								<td>${orderdetailToolVO.sweet_type}</td>
								<td>${orderdetailToolVO.ice_type}</td>
								<td>${orderdetailToolVO.com_size}</td>
								<td>${orderdetailToolVO.od_price}</td>
								<td>${orderdetailToolVO.amount}</td>	  
								</tr>
							</c:forEach>
			

					
							</table>
					
					</div>
					<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
					</div>
				</div>
			</div>
		</div>
		
						</td>
						</tr>
						
				</c:forEach>
				</tbody>		
			</table>
					</div>
					</div>
	<!-- 	// TAB3結束 **************************************** -->





<!-- 	// TAB4　開始*******************設定上架集點卡********************* -->
						<div class="tab-pane" id="tab_default_4">
					
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




<!-- 	舊式 -->
<script src="https://code.jquery.com/jquery.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	</body>

</html>