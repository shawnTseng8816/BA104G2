<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="proTypeService" scope="page" class="com.product_type.model.ProductTypeService"/>
<jsp:useBean id="productService" scope="page" class="com.product.model.ProductService"/>
<jsp:useBean id="iceListService" scope="page" class="com.ice_list.model.IceListService"/>
<jsp:useBean id="sweetService" scope="page" class="com.sweetness.model.SweetnessService"/>
<jsp:useBean id="extraService" scope="page" class="com.extra.model.ExtraService"/>
<jsp:useBean id="memberProfileService" scope="page" class="com.member_profile.model.MemberProfileService"/>

<style>

	.memberName {
		padding: 5px;
		background-color: #afeeee;
	}
	
	.inviter img, .invited img {
		max-width: 100px;
		max-height: 100px;
	}
	
	.itemPic {
		max-width: 90px;
		max-height: 90px;
	}
	
	#inviter {
		width: 30%;
		top: -1.5em;
	}
	
	.status {
		position: absolute;
		left: 0;
		top: 0;
		width: 60px;
		height: 50px;
	}
	
</style>

	<div class="modal-header">
		<h3 class="modal-title text-center">團購資訊</h3>
		<hr style="color: #28FF28, width: 2px;">
		<div id="invitedMems" class="container-fluid">
			<div class="row">
				<div class="col-sm-2 inviter">
					<img src="<%=request.getContextPath()%>/getPic?mem_num=${inviter.getMem_num()}" class="img-thumbnail img-check">
					<img src="<%=request.getContextPath()%>/img/inviter.png" class="status" id="inviter">
					<h4 class="text-center">${inviter.getMem_name()}</h4>
				</div>
			
			<c:forEach var="groupBuyMem" items="${groupBuyMems}">
				<div class="col-sm-2 invited">
					<img src="<%=request.getContextPath()%>/getPic?mem_num=${memberProfileService.getMyProfile(groupBuyMem.getInvd_mem_num()).getMem_num()}" class="img-thumbnail img-check">
					<c:if test="${groupBuyMem.getIsaccept() == 'Y'}">
						<img src="<%=request.getContextPath()%>/img/check.png" class="status">
					</c:if>
					<c:if test="${groupBuyMem.getIsaccept() == 'N'}">
						<img src="<%=request.getContextPath()%>/img/xx.png" class="status">
					</c:if>
					<c:if test="${groupBuyMem.getIsaccept() == 'ONCONFIRM'}">
						<img src="<%=request.getContextPath()%>/img/loading.gif" class="friendcheck img-check status">
					</c:if>
					<h4>${memberProfileService.getMyProfile(groupBuyMem.getInvd_mem_num()).getMem_name()}</h4>
				</div>
			</c:forEach>
  			</div>
		</div>
	</div>


	<div class="modal-body container-fluid gbFriendList">
		<div class="row">
			<div class="form-group">
			
				<c:set var="cashTotal" value="${0*1}" />
				<c:set var="pointTotal" value="${0*1}" />
		
				<c:forEach var="eachMemShoppingCar" items="${groupBuyInfo}" varStatus="g">
			
					<div class="modal-header text-center memberName">
						<b class="memberName" style="font-size: 250%;">${eachMemShoppingCar[0]}</b>
					</div>
				
					<div class="modal-body container-fluid">
						<div class="row">
						<input type="hidden" name="or_nums" value="${eachMemShoppingCar[1]}">
					
							<c:forEach var="orders" items="${eachMemShoppingCar[4]}" varStatus="o">
				
							<div class="col-md-4">.
								<table class="table" style="border: 2px solid #FA5532;">
									<tr>
										<td rowspan="3">
											<img class="itemPic" src="<%=request.getContextPath()%>/getPic?com_num=${orders.getOrderDetail().getCom_num()}">
										</td>
										<td>
											${iceListService.getIceType(orders.getOrderDetail().getIce_num()).getIce_type()}
										</td>
										<td rowspan="3" colspan="2">
											<c:forEach var="extras" items="${orders.getExtNames()}" varStatus="ex">
												${extras} <br>
											</c:forEach></td>
									</tr>
									<tr>
										<td>
											${sweetService.getSweetType(orders.getOrderDetail().getSweet_num()).getSweet_type()}
										</td>
									</tr>
									<tr>
										<c:if test="${orders.getIsKeep() == 'keep'}">
											<td>寄杯</td>
										</c:if>
										<c:if test="${orders.getIsKeep() == 'notkeep'}">
											<td>不寄杯</td>
										</c:if>
									</tr>
									<tr>
										<td>
											<b class="test-center">${productService.getProductName(orders.getOrderDetail().getCom_num()).getCom_name()}</b>
										</td>
										<c:if test="${orders.getOrderDetail().getCom_size() == 'L'}">
											<td>大杯</td>
										</c:if> 
										<c:if test="${orders.getOrderDetail().getCom_size() == 'M'}">
											<td>中杯</td>
										</c:if>
										<td>${orders.getTotal()}杯</td>
										<td>${orders.getOrderDetail().getOd_price() * orders.getTotal()}元</td>
									</tr>
								</table>
							</div>
							
						</c:forEach>
					</div>
				</div>
				
				<div class="modal-footer">
					<h4 id="cost">共${eachMemShoppingCar[3]}元 (${eachMemShoppingCar[2]})</h4>
				</div>
				
				<c:if test="${eachMemShoppingCar[2] == '現金'}">
					<c:set var="cashTotal" value="${cashTotal + eachMemShoppingCar[3]}" />
				</c:if>
				<c:if test="${eachMemShoppingCar[2] == '點數'}">
					<c:set var="pointTotal" value="${pointTotal + eachMemShoppingCar[3]}" />
				</c:if>
				
			</c:forEach>
			
				<h3 class="text-center">合併訂單共${cashTotal + pointTotal}元 (現金:${cashTotal}元, 點數:${pointTotal}元)</h3>
				
			</div>
		</div>
	</div>
