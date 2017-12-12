<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List" %>
<%@ page import="com.order_detail.model.OrderDetailForShoppingCar" %>

<%
	String buylistName = "buylist" + (String) request.getSession().getAttribute("currentSto_num");
	@SuppressWarnings("unchecked")
	List<OrderDetailForShoppingCar> buylist = (List<OrderDetailForShoppingCar>)session.getAttribute(buylistName);
	pageContext.setAttribute("buylist", buylist);
%>

<jsp:useBean id="iceListService" scope="page" class="com.ice_list.model.IceListService" />
<jsp:useBean id="sweetService" scope="page" class="com.sweetness.model.SweetnessService" />
<jsp:useBean id="productService" scope="page" class="com.product.model.ProductService" />

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Examples</title>
<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>

	.icon img {
		width:100px;
		height:100px;
	}
	
	.modal-footer {
		text-align: center;
		border-top: 1px solid #c0c0c0;
	}
	
	.itemPic {
		width: 100px;
		height: 100px;
	}
	
	#cost{
		font-size:3em;
	}
	
	body{
		font-family: 微軟正黑體, Gotham;
		color:#595942; 
		padding-top:12px;
	}
	
	.productName{
		margin-left: 1.3em;
	}
</style>

<script>
	$(document).ready(function() {
		
		 $('#btnPopModal').on('click', function(e) {
			 
				var xhr = new XMLHttpRequest();
				xhr.onreadystatechange = function() {

					if (xhr.readyState == 4) {
						if (xhr.status == 200) {
							
						} else {
							
							alert("not found");
						}
					}
				};
				
				var url = "<%=request.getContextPath()%>/OrderMaster/OMC.html";
				xhr.open("POST", url, true);
				xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
				xhr.send("action=AddNewOrder");
				
				window.parent.$('#orderModal').modal('show');
	      });
	});
	
</script>
</head>
	
<body>

	<div class="container col-sm-12 section1-R">
		<div class="row">
			<div class="col-sm-3 icon">
				<div class="row">
					<img src="<%=request.getContextPath()%>/img/shoppingcar.jpg"> 
					<img src="<%=request.getContextPath()%>/img/cash.png">
					<c:set var="total" value="${0*1}" />
					<c:forEach var="buyList" items="${buylist}">
						<c:set var="total" value="${total + buyList.getTotal() * buyList.getOrderDetail().getOd_price()}" />
					</c:forEach>
					<b id="cost">${total}</b>
				</div>
				<hr>
			</div>
		</div>			
	</div>
				
				
	<c:if test="${buylist != null && buylist.size() > 0}">
	
	<div class="container col-sm-12 section2-R">
	
		<div class="row">

			<c:forEach var="order" items="${buylist}" varStatus="o">
					
				<form action="<%=request.getContextPath()%>/OrderDetail/ODC.html" method="post">
						
					<table class="table" style="border: 3px solid #FA5532;">
						<tr>
							<td rowspan="3">
								<img class="itemPic" src="<%=request.getContextPath()%>/getPic?com_num=${order.getOrderDetail().getCom_num()}">
							</td>
							<td>
								${iceListService.getIceType(order.getOrderDetail().getIce_num()).getIce_type()}
							</td>
							<td rowspan="3">
								<c:forEach var="extras" items="${order.getExtNames()}" varStatus="ex">
									${extras} <br>
								</c:forEach>
							</td>
							<td rowspan="3">
								<button type="submit" class="btn btn-danger btn-sm glyphicon glyphicon-remove" name="action" value="Remove"></button>
							</td>
						</tr>
						<tr>
							<td>
								${sweetService.getSweetTypeKAHN(order.getOrderDetail().getSweet_num()).getSweet_type()}
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
								<b class="productName">${productService.getProductName(order.getOrderDetail().getCom_num()).getCom_name()}</b>
							</td>
							<c:if test="${order.getOrderDetail().getCom_size() == 'L'}">
								<td>大杯</td>
							</c:if>
							<c:if test="${order.getOrderDetail().getCom_size() == 'M'}">
								<td>中杯</td>
							</c:if>
							<td>${order.getTotal()}杯</td>
							<td>${order.getOrderDetail().getOd_price() * order.getTotal()}元
							</td>
						</tr>
					</table>
					<input type="hidden" name="sto_num" value="${sto_num == null ? param.sto_num : sto_num}">
					<input type="hidden" name="index" value="${o.index}">
				</form>
			</c:forEach>
			
			<div class="modal-footer">
				<button id="btnPopModal" class="btn btn-success btn-block">確認訂購</button>
			</div>
		</div>
		
	</div>
</c:if>
	
</body>
</html>