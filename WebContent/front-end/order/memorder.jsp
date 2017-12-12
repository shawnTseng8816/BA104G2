<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.order_master.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="memSvc" class="com.member_profile.model.MemberProfileService"/>
<jsp:useBean id="orderSvc" class="com.order_master.model.OrderMasterService"/>
<jsp:useBean id="comSvc" class="com.store_comment.model.StoreCommentService"/>
<jsp:useBean id="couponSvc" class="com.coupon_list.model.CouponListService"/>
<jsp:useBean id="cardSvc" class="com.card_list.model.CardListService"/>
<jsp:useBean id="stoProSvc" class="com.store_profile.model.StoreProfileService"/>
<jsp:useBean id="merOrderSvc" class="com.merged_order.model.MergedOrderService"/>
<jsp:useBean id="groupBuySvc" class="com.group_buy.model.GroupBuyService"/>
<%	
		OrderMasterService ordermasterSvc = new OrderMasterService();
		List list = new ArrayList();
		String mem_num = (String) session.getAttribute("mem_num");
		list = ordermasterSvc.getOneMemFinishOrder(mem_num);
		request.setAttribute("getOneMemFinishOrder", list);
		list = ordermasterSvc.getOneMemNoFinishOrder(mem_num);
		request.setAttribute("getOneMemNoFinishOrder", list);
%>


<!DOCTYPE html>
<html lang="">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/3dbtn.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/stars/starability-minified/starability-all.min.css"/>



<style type="text/css">
   .myTable {border-color:#ffa500; border-style:solid;}
   .myTable2 {border-color:#3C9682; border-style:solid;}
  
textarea{/* Text Area 固定大小*/
 max-width:350px;
 max-height:250px;
 min-width:350px;
 min-height:250px;


}

.input-group{
	margin-top: 20px;
}

thead th{
    text-align: center;
}

.stoordere {
background-color: #3c9682;
color:white;
}

</style>
	</head>

<jsp:include page="/front-end/member_top.jsp" flush="true" />
<jsp:include page="/front-end/coupon_notify.jsp" />
<body>

	
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
			<h3>會員訂單管理　</h3>

			<div class="tabbable-panel">
				<div class="tabbable-line">
					<ul class="nav nav-tabs ">
					
						<li><a  class="stoordere"  href="#tab_default_1" data-toggle="tab">處理中訂單</a></li>
						<li><a  class="stoordere"   href="#tab_default_2" data-toggle="tab">已完成訂單</a></li>
						<li><a  class="stoordere"   href="#tab_default_5" data-toggle="tab">團購邀請</a></li>

					</ul>

			<!-- 	// TAB1 內容 ********處理中訂******************************** -->
					<div class="tab-content">
						<div class="tab-pane active" id="tab_default_1">
							<div class="panel  " >
					<div class=" panel-heading "  style="background-color:#3c9682; ">
					<b><h3><div style="color:white"  align='center' > 處理中訂單</div></h3></b>
				    </div>
				<!-- <div class="panel-body"> -->
					    <table  class="table table-hover">
					    	<!-- <caption>我是表格標題</caption> -->
					    <thead>
						<tr>
							<th>訂單編號</th>
							<th>店家名稱</th>
							<th>訂單更新時間</th>
							<th>訂單狀態</th>
							<th>訂單明細</th>
							<th>操作</th>
						</tr>
				    	</thead>
				   <tbody>
				<c:forEach var="ordermasterVO" items="${getOneMemNoFinishOrder}">
					<tr align='center' valign='middle' >
					<td>${ordermasterVO.or_num}</td>
					<td>${stoProSvc.getOneStoInfo(ordermasterVO.sto_num).sto_name}</td>
					<td><fmt:formatDate value="${ordermasterVO.or_time}" pattern="yyyy/MM/dd HH:mm"/></td>
					<td>${ordermasterVO.or_stanum}</td>
	<!-- ****訂單明細******************************** -->	
				<%@ include file="/front-end/order/memorderList.jsp" %>
					</td>
		<!-- ***訂單明細結束******************************** -->				
					
	<!-- 	****確認領收按鈕**************** 非團購訂單*************** -->		
			
					<td>
					<c:if test="${ordermasterVO.or_stanum.equals('修改中')}">
					<div>
					<p class="text-right"><a onclick="location.href='<%=request.getContextPath()%>/OrderMaster/OMC.html?action=EditOrder&&or_num=${ordermasterVO.or_num}'"  class="btn btn-success btn-xs btn3d addone">修改訂單</a></p>
					
					</div>
					</c:if>
					
					<c:if test="${ordermasterVO.or_stanum.equals('已確認')}">
						<c:if test="${merOrderSvc.getMergedOrder(ordermasterVO.meror_num).mem_num.equals(mem_num)}">
							<div>
<%-- 								<button type="button" onclick="location.href='<%=request.getContextPath()%>/OrderMaster/OMC.html?action=ConfirmGroupBuy&&meror_num=${ordermasterVO.meror_num}&&address=${ordermasterVO.address}&&OrManeger=Y'" class="modife btn btn-success btn-xs btn3d addone" data-dismiss="modal">送出團購訂單</button> --%>
								<button type="button" onclick="location.href='<%=request.getContextPath()%>/OrderMaster/OMC.html?action=ConfirmGroupBuy&&or_num=${ordermasterVO.or_num}&&OrManeger=Y'" class="modife btn btn-success btn-xs btn3d addone" data-dismiss="modal">送出團購訂單</button>
							</div>
						</c:if>
					</c:if>
					
					<c:if test="${ordermasterVO.meror_num==null}">
					<c:if test="${ordermasterVO.or_stanum.equals('已交貨')}">
					 <p class="text-right"><a href='#${ordermasterVO.or_num}3' data-toggle="modal" class="btn btn-danger btn-xs btn3d addone">確認領取</a></p>
					<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/OrderMasterServlet" style="margin-bottom: 0px;">
					<div class="modal fade" id="${ordermasterVO.or_num}3">  				
					<div class="modal-dialog ">
						<div class="modal-content">
							<div class="modal-header">			
								<h4 class="modal-title">訂單確認</h4>
							</div>
							<div class="modal-body" style="font-size:24px;">確定已收到商品!?
							</div>
							
							<div class="modal-footer">	
							<input type="hidden" name="meror_num"  value="${ordermasterVO.meror_num}">	
						     <input type="hidden" name="or_num"  value="${ordermasterVO.or_num}">
						     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->            
						     <input type="hidden" name="action"	    value="finishOrder">
						     	
						     <p class="text-right"><input type="submit" value="確認已收到" data-toggle="modal" class="btn btn-danger btn-xs btn3d addone"></a></p>
							<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
							</div>
							</div>
						</div>
					</div>
					</FORM>
					</c:if>
					</c:if>
					
<!-- 	****確認領收按鈕**************** 團購訂單*************** -->					
					<c:if test="${orderSvc.getGroupMaster(ordermasterVO.or_num).equals(ordermasterVO.mem_num)}">
					<c:if test="${ordermasterVO.or_stanum.equals('已交貨')}">
					 <p class="text-right"><a href='#${ordermasterVO.or_num}4' data-toggle="modal" class="btn btn-danger btn-xs btn3d addone">確認領取</a></p>
					<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/OrderMasterServlet" style="margin-bottom: 0px;">
					<div class="modal fade" id="${ordermasterVO.or_num}4">  				
					<div class="modal-dialog ">
						<div class="modal-content">
							<div class="modal-header">			
								<h4 class="modal-title">訂單確認</h4>
							</div>
							<div class="modal-body" style="font-size:24px;">
							確定已收到商品 !?								 
							</div>
							<div class="modal-footer">	
							<input type="hidden" name="meror_num"  value="${ordermasterVO.meror_num}">	   
						     <input type="hidden" name="or_num"  value="${ordermasterVO.or_num}">
						     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->            
						     <input type="hidden" name="action"	    value="finishOrder">
						     <p class="text-right"><input type="submit" value="確認已收到" data-toggle="modal" class="btn btn-danger btn-xs btn3d addone"></a></p>
							<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
							</div>
							</div>
						</div>
					</div>
					</FORM>
					</c:if>
					</c:if>
					</td>
					</tr>
				</c:forEach>
					</tbody>					
						</table>						
						</div>	
						</div>
<!-- 	// TAB1 結束 **************************************** -->




		<!-- 	// TAB2 內容 ***** 完成訂單紀錄********************************** -->
			
				<div class="tab-pane" id="tab_default_2">										
				<div class="panel  " >
					<div class="panel-heading "  style="background-color:#3c9682; ">
					<b><h3><div style="color:white" align='center'  valign='middle'  > 已完成訂單</div></h3></b>
				    </div>
				<!-- <div class="panel-body"> -->
					    <table class="table table-hover">					  
					    <thead>
						<tr align='center'  valign='middle'>
							<th>訂單編號</th>
							<th>店家名稱</th>
							<th>訂單更新時間</th>
							<th>訂單狀態</th>
							<th>訂單明細</th>		
						</tr>
				    	</thead>
				   <tbody>
				<c:forEach var="ordermasterVO" items="${getOneMemFinishOrder}">
					<tr align='center' valign='middle' ><!--將修改的那一筆加入對比色而已-->
					<td>${ordermasterVO.or_num}</td>
					<td>${stoProSvc.getOneStoInfo(ordermasterVO.sto_num).sto_name}</td>
					<td><fmt:formatDate value="${ordermasterVO.or_time}" pattern="yyyy/MM/dd HH:mm"/></td>
					<td>${ordermasterVO.or_stanum}</td>
														
<!-- 		****訂單明細******************************** -->
			 	 		<!-- ****訂單明細******************************** -->	
				<%@ include file="/front-end/order/memorderList.jsp" %>
		<!-- ***訂單明細結束******************************** -->		
				
		
		 <c:if test="${comSvc.checkComment(ordermasterVO.or_num)==null}">
				<p class="text-right"><a href='#${ordermasterVO.or_num}7' data-toggle="modal" class="btn btn-danger btn-xs btn3d addone"></i>評分</a></p>
<!-- 				****************評分********************** -->
				  <form action="<%=request.getContextPath()%>/OrderMasterServlet" method="post"  >
          	<div class="modal fade" id="${ordermasterVO.or_num}7">  
			<div class="modal-dialog ">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title">評價此店家</h4>
					</div>
					<div class="modal-body">
					
							
					<div class="form-group form-inline">
						<label for="aa">標題  : </label>
						<input  class="form-control" id="aa" name="com_title" >									
					</div>	
	
					<div style="margin-top:50px;"></div>
					 <span class="starability-growRotate">
					      <input type="radio" id="rate1${ordermasterVO.or_num}" class="stars" name="stars${ordermasterVO.or_num}" value="5" />
					      <label for="rate1${ordermasterVO.or_num}"></label>
				
					      <input type="radio" id="rate2${ordermasterVO.or_num}" class="stars" name="stars${ordermasterVO.or_num}" value="4"/>
					      <label for="rate2${ordermasterVO.or_num}"></label>
				
					      <input type="radio" id="rate3${ordermasterVO.or_num}" class="stars" name="stars${ordermasterVO.or_num}" value="3" />
					      <label for="rate3${ordermasterVO.or_num}"></label>
				
					      <input type="radio" id="rate4${ordermasterVO.or_num}" class="stars" name="stars${ordermasterVO.or_num}" value="2" />
					      <label for="rate4${ordermasterVO.or_num}"></label>
				
					      <input type="radio" id="rate5${ordermasterVO.or_num}" class="stars" name="stars${ordermasterVO.or_num}" value="1" />
					      <label for="rate5${ordermasterVO.or_num}"></label>
					    </span>
  
					<textarea class="form-control" name="commentt"></textarea>	
					
					</div>
					<div class="modal-footer">
						<div>評價完後可參加抽獎小遊戲，有機會將頭獎10萬點帶回家 !!</div>
						<input name="stars_point" type="hidden" value="stars${ordermasterVO.or_num}"> 
						<input name="sto_num" type="hidden" value="${ordermasterVO.sto_num}"> 
						<input name="or_num" type="hidden" value="${ordermasterVO.or_num}"> 
						<input name="action" type="hidden" value="insertComment"> 
						<input type="submit" value="送出" class="btn btn-success" >					 
						<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
						 <button style="width:30px;height:20px;" onClick="inPutInfo()" type="button"  />
					
					</div>
				</div>
			</div>
		</div>
		</form>
		
		</c:if>
				</td>
<!-- 		****訂單明細結束******************************** -->
									
					</tr>
				</c:forEach>
					</tbody>				
						</table>					
						</div>
						</div>
<!-- 	// TAB2 結束 **************************************** -->


<!-- 	// TAB３　開始*****************處理中訂單********************* -->
	
			
				<div class="tab-pane" id="tab_default_3">
										
			
						</div>
					
<!-- 	// TAB３　結束**************************************** -->



<!-- 	// TAB4　開始*******************設定上架集點卡********************* -->
						<div class="tab-pane" id="tab_default_4">
					
						</div>
<!-- 	// TAB4　結束**************************************** -->



<!-- 	// TAB5　開始*******************團購邀請********************* -->
			<div class="tab-pane" id="tab_default_5">
				<div class="panel  " >
					<div class="panel-heading "  style="background-color:#3c9682; ">
						<b><h3><div style="color:white" align='center' valign='middle'>團購邀請</div></h3></b>
				    </div>
					    <table  class="table table-hover">
						    <thead>
							<tr>
								<th>店家名稱 </th>
								<th>發起人</th>
								<th>邀請時間</th>
								<th>操作</th>
							</tr>
					    	</thead>
				   			<tbody>
								<c:forEach var="groupBuy" items="${groupBuySvc.getMyGroupBuyInvite(mem_num)}">
									<tr align='center' valign='middle' >
										<td>${stoProSvc.getOneStoName(orderSvc.getGroupAllOrderNumByMerorNum(groupBuy.meror_num)[0].sto_num).sto_name}</td>
										<td>${memSvc.getMyProfile(groupBuy.inv_mem_num).mem_name}</td>
										<td><fmt:formatDate value="${groupBuy.inv_time}" type="date" pattern="yyyy/MM/dd HH:mm:ss"/></td>
										<td>
											<div>
												<button type="button" onclick="location.href='<%=request.getContextPath()%>/OrderMaster/OMC.html?isAccept=AcceptInvite&&meror_num=${groupBuy.meror_num}'" class="modife btn btn-info btn-xs btn3d addone" data-dismiss="modal">接受</button>
												<button type="button" onclick="location.href='<%=request.getContextPath()%>/OrderMaster/OMC.html?isAccept=RejectInvite&&meror_num=${groupBuy.meror_num}'" class="modife btn btn-danger btn-xs btn3d addone" data-dismiss="modal">拒絕</button>
											</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>					
						</table>						
				</div>	
			</div>
<!-- 	// TAB5　結束**************************************** -->





					</div>
				</div>
			</div>

		
		</div>
	</div>
</div>


	




<jsp:include page="/front-end/member_foot.jsp" flush="true" />

	
	</body>
<script>
function inPutInfo(){

	
	
	document.getElementsByName("com_title")[0].value= '口感還不錯';
	document.getElementsByName("commentt")[0].value= '不錯喝!!!';
	
}

	
</script>

</html>