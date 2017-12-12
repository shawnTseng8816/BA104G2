<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List" %>
<%@ page import="com.order_detail.model.OrderDetailForShoppingCar" %>

<%
	String buylistName = "buylist" + (String) request.getSession().getAttribute("currentSto_num");
	@SuppressWarnings("unchecked")
	List<OrderDetailForShoppingCar> buylist = (List<OrderDetailForShoppingCar>) request.getAttribute(buylistName);
	pageContext.setAttribute("buylist", buylist);
%>
 
<jsp:useBean id="proTypeService" scope="page" class="com.product_type.model.ProductTypeService" />
<jsp:useBean id="productService" scope="page" class="com.product.model.ProductService" />
<jsp:useBean id="iceListService" scope="page" class="com.ice_list.model.IceListService" />
<jsp:useBean id="sweetService" scope="page" class="com.sweetness.model.SweetnessService" />
<jsp:useBean id="extraService" scope="page" class="com.extra.model.ExtraService" />
<jsp:useBean id="orderMasterService" scope="page" class="com.order_master.model.OrderMasterService" />
<jsp:useBean id="mergedOrderService" scope="page" class="com.merged_order.model.MergedOrderService" />
<jsp:useBean id="memberProfileService" scope="page" class="com.member_profile.model.MemberProfileService" />

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Examples</title>
<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script src="<%=request.getContextPath()%>/js/dist/sweetalert2.all.min.js"></script>
<script src="<%=request.getContextPath()%>/js/dist/sweetalert2.min.js"></script>

<style>

	.pickUpType {
		font-size: 1.5em;
	}
	
	.pickTypeBtn {
		display: block;
		margin: auto;
	}
	
	.modal-content{
		margin-top: 1.5em;
		margin-bottom: 2.5em;
		position: relative;
	}
	
	.itemPic {
		max-width: 130px;
		max-height: 130px;
	}
	
</style>

<script>

	$(document).ready(function() {
		
	    swal({
			 position: 'center',
			 type: 'success',
			 title: '成功新增一筆訂單!!!',
			 showConfirmButton: false,
			 timer: 1500
		});
	    	
		
		window.history.pushState(null, "", window.location.href);        
	    window.onpopstate = function() {
	       	window.history.pushState(null, "", window.location.href);
	    };

		$('#sendOrderBtn').click(function(){
					    	
			swal({
					position: 'center',
					type: 'success',
					title: '成功送出團購訂單!!!',
					showConfirmButton: false,
					timer: 1500
				});
					    	
			setTimeout(function(){ $('#sendGroupBuyOrder').submit(); }, 1500);
					    	
		});
		
	});
	
</script>
</head>
<body>

	<jsp:include page="/front-end/member_top.jsp" flush="true" />

<!-- ================================MyOrder======================================= -->
	<div class="container">
		<div class="row">
		
			<div class="modal-content myOrder container">
				<h3 class="text-center">我的訂單</h3>
				<hr style="color: #28FF28, width: 2px;">
				<c:forEach var="order" items="${buylist}" varStatus="o">
					<div class="col-sm-4">
						<table class="table text-center" style="border: 3px solid #FA5532;">
							<tr>
								<td rowspan="3">
									<img class="itemPic" src="<%=request.getContextPath()%>/getPic?com_num=${order.getOrderDetail().getCom_num()}">
								</td>
								<td>
									${iceListService.getIceType(order.getOrderDetail().getIce_num()).getIce_type()}
								</td>
								<td rowspan="3" colspan="2">
									<c:forEach var="extras" items="${order.getExtNames()}" varStatus="ex">
										${extras} <br>
									</c:forEach>
								</td>
							</tr>
							<tr>
								<td>
									${sweetService.getSweetType(order.getOrderDetail().getSweet_num()).getSweet_type()}
								</td>
							</tr>
							<tr>
								<c:if test="${order.getIsKeep() == 'keep'}">
									<td>寄杯</td>
								</c:if>
								<c:if test="${order.getIsKeep() == 'notkeep'}">
									<td>不寄杯</td>
								</c:if>
							</tr>
							<tr>
								<td>
									<b class="text-center">${productService.getProductName(order.getOrderDetail().getCom_num()).getCom_name()}</b>
								</td>
								<c:if test="${order.getOrderDetail().getCom_size() == 'L'}">
									<td>大杯</td>
								</c:if> <c:if test="${order.getOrderDetail().getCom_size() == 'M'}">
									<td>中杯</td>
								</c:if>
								<td>${order.getTotal()}杯</td>
								<td>${order.getOrderDetail().getOd_price() * order.getTotal()}元</td>
							</tr>
						</table>
					</div>
				</c:forEach>
				
				<div class="col-sm-12">
					<c:if test="${param.myCards != null}">
						<h4>您使用了集點卡</h4>
						<h4>減免${cardDiscount}元</h4>
					</c:if>
		
					<c:if test="${param.myCoupons != null}">
						<h4>您使用了折價券</h4>
						<h4>折價${couponDiscount}元</h4>
					</c:if>
					<div class="modal-footer">
						<h3 class="text-right">您總共消費了:${cost}元   (${payment})</h3>
					</div>
				</div>
			</div>
<!-- ================================MyOrder======================================= -->			
			
			
<!-- ====================== =======GroupBuyOrder==================================== -->
			<c:if test="${meror_num != null}">
				<div class="modal-content container" id="shoppingCarList">
					<jsp:include page="/front-end/Group_Buy/GroupBuyInfo.jsp" flush="true" />
				</div>

				<c:if test="${mergedOrderService.getMergedOrder(meror_num).getMem_num() == mem_num}">
					<form action="<%=request.getContextPath()%>/OrderMaster/OMC.html" method="post" id="sendGroupBuyOrder">
						<input type="hidden" name="or_num" value="${orderNum}">
						<input type="hidden" name="action" value="ConfirmGroupBuy">
						<button type="button" class="btn btn-info pickTypeBtn center-block" id="sendOrderBtn">送出團購訂單</button>
					</form>
				</c:if>
			</c:if>
		</div>
	</div>
<!-- ====================== =======GroupBuyOrder==================================== -->

	<jsp:include page="/front-end/Footer.jsp" flush="true" />
	
</body>
</html>