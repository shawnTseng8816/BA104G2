<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>


						<div class="modal-dialog ">
							<div class="modal-content">
							<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h3 class="modal-title info">訂購人資訊</h3>
								</div>
								<div class="modal-body">${imgnum.first?'active':''}
								<div class="text-center"     style="padding-left: 120px;" >
								<table  style=" font-size:20px" ><tr><th  style="padding-right: 25px;" >訂購人姓名 :  </th><td>${memSvc.getOneMemInfo(ordermasterVO.mem_num).mem_name}</td></tr>	
										<tr><th style="padding-left: 42px;">電話 :</th><td> ${memSvc.getOneMemInfo(ordermasterVO.mem_num).mobile}</td></tr>
								<c:if test="${ordermasterVO.meror_num==null}"><tr><th>付款方式 : </th><td> ${ordermasterVO.pay_men} </td></tr></c:if>						
										<tr><th>取貨方式  : </th><td>${ordermasterVO.rece}</td></tr>
								<c:if test="${ordermasterVO.rece.equals('外送')}"><tr><th>外送地址 :</th><td> ${ordermasterVO.address}</td></tr></c:if>						
								</table>
								</div>
								<table class="table">	
								<tr><th colspan="7">訂單明細 :</th></tr>
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
								<c:if test="${ordermasterVO.meror_num==null}">					
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
								<c:forEach varStatus="showkeep" var="orderdetailToolVO" items="${orderSvc.getKeepDetailTool(ordermasterVO.or_num)}">						
								${showkeep.first?'<tr><th colspan="7">**********以下為寄杯**********  </th></tr>':''}
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
								</c:if>
			
<!-- 	************************團購明細 ***************************************-->		
								<c:if test="${ordermasterVO.meror_num!=null}">
								<c:forEach var="orderdetailToolVO" items="${orderSvc.getGroupNotKeepDetailTool(ordermasterVO.meror_num)}">
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
								<c:forEach  varStatus="showkeep2" var="orderdetailToolVO" items="${orderSvc.getGroupKeepDetailTool(ordermasterVO.meror_num)}">
								${showkeep2.first?'<tr><th colspan="7">**********以下為寄杯**********  </th></tr>':''}
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
								</c:if>	
					
							</table>
					
					</div>
					<div class="modal-footer">
					<c:if test="${ordermasterVO.meror_num!=null}">
					
					<c:set var="grtotal" value="${orderSvc.getGroupOrderPoint(ordermasterVO.meror_num).tol_amount}"/>
					<c:set var="grpoint" value="${orderSvc.getGroupOrderPoint(ordermasterVO.meror_num).point}"/>
					<table class="table">
					<tr><th>商品總金額 &nbsp;&nbsp;<img src="<%=request.getContextPath()%>/img/order/cash.png"></th><th><font size="5">NT $&nbsp;&nbsp;&nbsp;&nbsp;${grtotal}</font></th></tr>
					<tr><th>點數付款&nbsp;&nbsp;<img src="<%=request.getContextPath()%>/img/order/coins.png"></th><th><font size="5">-NT $&nbsp;&nbsp;&nbsp;&nbsp;${grpoint}</font></th></tr>
					<tr><th>應收總金額 &nbsp;&nbsp;<img src="<%=request.getContextPath()%>/img/order/get-money.png"></th><th><font size="5">NT $&nbsp;&nbsp;&nbsp;&nbsp;${grtotal-grpoint}</font></th></tr>				
					</table>						
					</c:if>		
					
								
					<c:if test="${ordermasterVO.meror_num==null}">
					<table class="table">
					<tr><th>商品總金額 &nbsp;&nbsp;<img src="<%=request.getContextPath()%>/img/order/cash.png"></th><th><font size="5">NT $&nbsp;&nbsp;&nbsp;&nbsp;${ordermasterVO.or_amount}</font></th></tr>
					<tr><th>點數付款&nbsp;&nbsp;<img src="<%=request.getContextPath()%>/img/order/coins.png"></th>
						<th><font size="5">-NT $&nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${ordermasterVO.pay_men.equals('點數')}">${ordermasterVO.or_amount}</font></c:if>
						<c:if test="${!ordermasterVO.pay_men.equals('點數')}">0</font></c:if></th></tr>			
					<tr><th>應收總金額  &nbsp;&nbsp;<img src="<%=request.getContextPath()%>/img/order/get-money.png"></th>
						<th><font size="5">NT $&nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${ordermasterVO.pay_men.equals('點數')}">0</font></c:if>
						<c:if test="${!ordermasterVO.pay_men.equals('點數')}">${ordermasterVO.or_amount}</font></c:if>
						</th></tr>				
					</table>						
					</c:if>
					
						<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
					</div>
				</div>
			</div>