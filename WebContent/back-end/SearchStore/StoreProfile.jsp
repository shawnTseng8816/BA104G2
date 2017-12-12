<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
	
	<style>
	
	#storeThumbnail{
		opacity:0.70;
		-webkit-transition: all 0.5s; 
		transition: all 0.5s;
	}
	#storeThumbnail:hover{
		opacity:1.00;
		box-shadow: 0px 0px 10px #4bc6ff;
	}
	</style>
	</head>
	<body>
	<div class="col-md-3 col-sm-8 col-sm-offset-4">
		<div class="container">
			<div class="row">
		    	<c:forEach var="storeProfileVO" items="${setStoreProfile}" >
			  		<div class="col-md-4 col-sm-6">
			    		<span class="thumbnail" id="storeThumbnail">
			      			<img src="/BA104G2/GetPicShawn?sto_num=${storeProfileVO.getSto_num()}&getType=storeName">
			      			<div class="text-center">
			      				<h3 style="color:black;">${storeProfileVO.getSto_name()}</h3>
			      				<p  style="color:black;">目前狀態: ${storeProfileVO.getSto_status()}</p>
			      			</div>
			      			<div class="text-center">
			      				<form method="get" action="/BA104G2/searchProfile/searchProfile.do">
									<c:if test='${storeProfileVO.getSto_status().equals("未上架")}' var="noonline">
										<select name="sto_status">
											<option value="沒事">---沒事--</option>
											<option value="後端下架">後端下架</option>
											<option value="停權">---停權--</option>
										</select>
										<input type="hidden" name="action" value="changeStoStatus">
										<input type="hidden" name="sto_num" value="${storeProfileVO.getSto_num()}">
										<button class="btn btn-success btn-lg">確定</button>
									</c:if>
								</form>
								<form method="get" action="/BA104G2/searchProfile/searchProfile.do">
									<c:if test='${storeProfileVO.getSto_status().equals("後端下架")}' var="lost">
										<select name="sto_status">
											<option value="沒事">---沒事--</option>
											<option value="未上架">---未上架--</option>
											<option value="停權">---停權--</option>
										</select>
										<input type="hidden" name="action" value="changeStoStatus">
										<input type="hidden" name="sto_num" value="${storeProfileVO.getSto_num()}">
										<button class="btn btn-success btn-lg">確定</button>
									</c:if>
								</form>
								<form method="get" action="/BA104G2/searchProfile/searchProfile.do">
									<c:if test='${storeProfileVO.getSto_status().equals("停權")}' var="stop">
										<select name="sto_status">
											<option value="沒事">---沒事--</option>
											<option value="未上架">---未上架--</option>
											<option value="後端下架">後端下架</option>
										</select>
										<input type="hidden" name="action" value="changeStoStatus">
										<input type="hidden" name="sto_num" value="${storeProfileVO.getSto_num()}">
										<button class="btn btn-success btn-lg" >確定</button>
									</c:if>
								</form>
								<form method="get" action="/BA104G2/searchProfile/searchProfile.do">
									<c:if test='${!noonline && !lost && !stop}'>
										<select name="sto_status">
											<option value="沒事">---沒事--</option>
											<option value="未上架">---未上架--</option>
											<option value="後端下架">後端下架</option>
											<option value="停權">---停權--</option>
										</select>
										<input type="hidden" name="action" value="changeStoStatus">
										<input type="hidden" name="sto_num" value="${storeProfileVO.getSto_num()}">
										<button class="btn btn-success btn-lg" >確定</button>
									</c:if>
								</form>
							</div>
			      			<hr class="line">
			      			<div class="row">
			      				<div class="col-md-6 col-sm-6 col-sm-offset-4">
			      					<a href="<%=request.getContextPath()%>/store_detail/store_detail.do?sto_num=${storeProfileVO.getSto_num()}" target="_blank">
			      						<button class="btn btn-info btn-lg right" >Let's Go</button>
			      					</a>
			      				</div>
			      			</div>
			    		</span>
			  		</div>
		  		</c:forEach>
			</div>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	</body>
</html>