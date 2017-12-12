<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>


							
<!-- ****訂單明細******************************** -->		
	
					<td><p class="text-right"><a href='#${ordermasterVO.or_num}' data-toggle="modal" class="btn btn-info btn-xs btn3d addone">個人明細</a></p>
				 	 	<div class="modal fade" id="${ordermasterVO.or_num}">  
						<div class="modal-dialog ">
							<div class="modal-content">
							<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
							<h3 class="modal-title">訂單資訊</h3>
								</div>							
									<div class="modal-body">						
								<table  style=" font-size:20px" ><tr><th  style="padding-right: 15px;" >訂購人姓名 :</th><td>${memSvc.getOneMemInfo(ordermasterVO.mem_num).mem_name}</td></tr>	
										<tr><th style="padding-left: 60px;">電話 :</th><td> ${memSvc.getOneMemInfo(ordermasterVO.mem_num).mobile}</td></tr>
								<c:if test="${ordermasterVO.meror_num==null}"><tr><th style="padding-left: 20px;">付款方式 : </th><td> ${ordermasterVO.pay_men} </td></tr></c:if>						
										<tr><th style="padding-left: 20px;">取貨方式  : </th><td>${ordermasterVO.rece}</td></tr>
								<c:if test="${ordermasterVO.rece.equals('外送')}"><tr><th  style="padding-left: 20px;">外送地址 :</th><td> ${ordermasterVO.address}</td></tr></c:if>						
								</table>
									<p><h3>訂單明細 </h3></p>
									
									
									
									
									<table class="table">	
									<tr>
									<th>產品名稱</th>
									<th>甜度</th>
									<th>冰塊</th>
									<th>加料</th>
									<th>尺寸</th>
									<th>單價</th>
									<th>數量</th>
									</tr>
<!-- 	**************************會員訂單明細*****************************-->																					
								<c:forEach var="orderdetailToolVO" items="${orderSvc.getNotKeepDetailTool(ordermasterVO.or_num)}">
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
								<c:forEach var="orderdetailToolVO" items="${orderSvc.getKeepDetailTool(ordermasterVO.or_num)}">
								<tr>
								<td>${orderdetailToolVO.com_name} **寄杯</td>
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
									<c:set var="grtotal" value="${orderSvc.getGroupOrderPoint(ordermasterVO.meror_num).tol_amount}"/>
									<c:set var="grpoint" value="${orderSvc.getGroupOrderPoint(ordermasterVO.meror_num).point}"/>
									<table class="table">
									<tr>
									<th>折價券  : ${couponSvc.getCouponCash(ordermasterVO.or_num)}</th>								
									<th>集點卡 : ${cardSvc.getCardCash(ordermasterVO.or_num)}</th>
									<th></th>
									</tr>
									<tr><th>訂單金額 &nbsp;&nbsp;<img src="<%=request.getContextPath()%>/img/order/cash.png"></th><th><font size="5">NT $&nbsp;&nbsp;&nbsp;&nbsp;${ordermasterVO.or_amount}</font></th></tr>
									<tr><th>點數付款&nbsp;&nbsp;&nbsp;<img src="<%=request.getContextPath()%>/img/order/coins.png"></th>
										<th><font size="5">-NT $&nbsp;&nbsp;&nbsp;<c:if test="${ordermasterVO.pay_men.equals('點數')}">${ordermasterVO.or_amount}</font></c:if>
										<c:if test="${!ordermasterVO.pay_men.equals('點數')}">0</font></c:if></th></tr>			
									<tr><th>總金額  &nbsp;&nbsp;<img src="<%=request.getContextPath()%>/img/order/payeric.png"></th>
										<th><font size="5">NT $&nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${ordermasterVO.pay_men.equals('點數')}">0</font></c:if>
										<c:if test="${!ordermasterVO.pay_men.equals('點數')}">${ordermasterVO.or_amount}</font></c:if>
										</th></tr>				
									</table>		
								<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
								</div>
							</div>
						</div>
					</div>
					
	<!-- ************會員團購訂單明細******************************** -->
					<c:if test="${ordermasterVO.meror_num!=null}">
						<p class="text-right"><a href='#${ordermasterVO.or_num}2' data-toggle="modal" class="btn btn-info btn-xs btn3d addone">團購明細</a></p>
			 	 		<div class="modal fade" id="${ordermasterVO.or_num}2">  
						<div class="modal-dialog ">
							<div class="modal-content">
							<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h3 class="modal-title">團購訂購資訊</h3>
								</div>
								<div class="modal-body">
	
								<table  style=" font-size:20px" ><tr><th  style="padding-right: 20px;" >團購發起人 :</th><td>${memSvc.getOneMemInfo(orderSvc.getGroupMaster(ordermasterVO.or_num)).mem_name}</td></tr>	
										<tr><th style="padding-left: 60px;">電話  :</th><td> ${memSvc.getOneMemInfo(orderSvc.getGroupMaster(ordermasterVO.or_num)).mobile}</td></tr>
								<c:if test="${ordermasterVO.meror_num==null}"><tr><th>付款方式  : </th><td> ${ordermasterVO.pay_men} </td></tr></c:if>						
										<tr><th style="padding-left: 20px;">取貨方式  : </th><td>${ordermasterVO.rece}</td></tr>
								<c:if test="${ordermasterVO.rece.equals('外送')}"><tr><th  style="padding-left: 20px;">外送地址  :</th><td> ${ordermasterVO.address}</td></tr></c:if>						
								</table>
								
								
								
								
								<p><h3>訂單明細 </h3></p>
							
<!-- 								取得團購名單個人購買資訊 -->
								
								<c:forEach var="ordermasterVO2" varStatus="valuecount" items="${orderSvc.getGroupAllOrderNumByMerorNum(ordermasterVO.meror_num)}">					
								<table class="${valuecount.count%2 != 0 ? "table myTable" : "table myTable2"}">	
								
								<tr >
								<th>產品名稱</th>
								<th>甜度</th>
								<th>冰塊</th>
								<th>加料</th>
								<th>尺寸</th>
								<th>單價</th>
								<th>數量</th>
								</tr>			
								
<!-- 	************************團購明細 ***************************************-->		
						<!-- 	**************************會員訂單明細*****************************-->																					
<!-- 								<table style="border:3px #FFD382 dashed;" cellpadding="10" border='1'> -->
								<c:forEach var="orderdetailToolVO" items="${orderSvc.getNotKeepDetailTool(ordermasterVO2.or_num)}">			
								
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
								<c:forEach var="orderdetailToolVO" items="${orderSvc.getKeepDetailTool(ordermasterVO2.or_num)}">
								<tr>						
								<td>${orderdetailToolVO.com_name} **寄杯</td>
								<td>${orderdetailToolVO.sweet_type}</td>
								<td>${orderdetailToolVO.ice_type}</td>
								<td>${orderdetailToolVO.extras}</td>
								<td>${orderdetailToolVO.com_size}</td>
								<td>${orderdetailToolVO.od_price}</td>
								<td>${orderdetailToolVO.amount}</td>	  
								</tr>
								</c:forEach>
								<tr>
								<th><img width="50" class="img-responsive " src="<%=request.getContextPath()%>/GetPicEric?mem_num=${ordermasterVO2.mem_num}" /></th>
								<th colspan="3"></th>
								<th colspan="3"></th>			
								</tr>	
								<tr>
								<th> ${memSvc.getOneMemInfo(ordermasterVO2.mem_num).mem_name}</th>
								<th>折價券  : ${couponSvc.getCouponCash(ordermasterVO2.or_num)}</th>
								<th></th>
								<th>集點卡 : ${cardSvc.getCardCash(ordermasterVO2.or_num)}</th>
								<th></th>
								<th colspan="2">應付金額 : 
								<c:if test="${ordermasterVO2.pay_men.equals('點數')}"> 點數付款</c:if>
								<c:if test="${!ordermasterVO2.pay_men.equals('點數')}">${ordermasterVO2.or_amount}</c:if>					</th>
								</tr>
<!-- 								</table> -->
								</table>	
								</c:forEach>
							
							
							</div>
					<div class="modal-footer">	
					<c:set var="grtotal" value="${orderSvc.getGroupOrderPoint(ordermasterVO.meror_num).tol_amount}"/>
					<c:set var="grpoint" value="${orderSvc.getGroupOrderPoint(ordermasterVO.meror_num).point}"/>
					<table class="table">
					<tr><th>訂單金額 &nbsp;&nbsp;<img src="<%=request.getContextPath()%>/img/order/cash.png"></th><th><font size="5">NT $&nbsp;&nbsp;&nbsp;&nbsp;${grtotal}</font></th></tr>
					<tr><th>點數付款&nbsp;&nbsp;&nbsp;<img src="<%=request.getContextPath()%>/img/order/coins.png"></th><th><font size="5">-NT $&nbsp;&nbsp;&nbsp;${grpoint}</font></th></tr>
					<tr><th>總金額 &nbsp;&nbsp;<img src="<%=request.getContextPath()%>/img/order/payeric.png"></th><th><font size="5">NT $&nbsp;&nbsp;&nbsp;&nbsp;${grtotal-grpoint}</font></th></tr>				
					</table>						
						<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
					</div>
				</div>
			</div>
		</div>

				</c:if>		
						

			
					
	<!-- ***訂單明細結束******************************** -->