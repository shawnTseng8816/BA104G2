<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
	
	
	<style>
	
	#memberThumbnail{
		opacity:0.70;
		-webkit-transition: all 0.5s; 
		transition: all 0.5s;
	}
	#memberThumbnail:hover{
		opacity:1.00;
		box-shadow: 0px 0px 10px #4bc6ff;
	}
	
	</style>
	</head>
	<body>
	<div class="col-md-3 col-sm-8 col-sm-offset-4">
		<div class="container">
			<div class="row">
		    	<c:forEach var="memberProfileVO" items="${setMemberProfile}" >
			  		<div class="col-md-4 col-sm-6">
			    		<span class="thumbnail" id="memberThumbnail">
			      			<img src="/BA104G2/GetPicShawn?mem_num=${memberProfileVO.getMem_num()}&getType=memberName">
			      			<div class="text-center">
				      			<h3 style="color:black;">${memberProfileVO.getMem_name()}</h3>
				      			<p style="color:black;">目前狀態: ${memberProfileVO.getStatus()}</p>
				      		</div>
				      		<div class="text-center">
			      				<form method="get" action="/BA104G2/searchProfile/searchProfile.do">
									<c:if test='${memberProfileVO.getStatus().equals("一般")}' var="noonline">
										<select name="mem_status">
											<option value="沒事">---沒事--</option>
											<option value="停權">---停權--</option>
										</select>
										<input type="hidden" name="action" value="changeMemStatus">
										<input type="hidden" name="mem_num" value="${memberProfileVO.getMem_num()}">
										<button class="btn btn-success btn-lg">確定</button>
									</c:if>
								</form>
								<form method="get" action="/BA104G2/searchProfile/searchProfile.do">
									<c:if test='${memberProfileVO.getStatus().equals("停權")}' var="stop">
										<select name="mem_status">
											<option value="沒事">---沒事--</option>
											<option value="一般">---一般--</option>
										</select>
										<input type="hidden" name="action" value="changeMemStatus">
										<input type="hidden" name="mem_num" value="${memberProfileVO.getMem_num()}">
										<button class="btn btn-success btn-lg" >確定</button>
									</c:if>
								</form>
								<form method="get" action="/BA104G2/searchProfile/searchProfile.do">
									<c:if test='${!noonline && !stop}'>
										<select name="mem_status">
											<option value="沒事">---沒事--</option>
											<option value="一般">---一般--</option>
											<option value="停權">---停權--</option>
										</select>
										<input type="hidden" name="action" value="changeMemStatus">
										<input type="hidden" name="mem_num" value="${memberProfileVO.getMem_num()}">
										<button class="btn btn-success btn-lg" >確定</button>
									</c:if>
								</form>
							</div>
			      			<hr class="line">
			      			<div class="row">
			      				<div class="col-md-6 col-sm-6 col-sm-offset-4">
			      					<a href='#memberProfile${memberProfileVO.mem_num}' data-toggle="modal" >
			      						<button class="btn btn-info btn-lg right" >Let's Go</button>
			      					</a>
			      				</div>
			      			</div>
			    		</span>
			  		</div>
			  		
			  		<c:set var="memid" value="${memberProfileVO.mem_num}"/>
											
					<%@ include file="/front-end/MemberDetail/MemberDetail.jsp" %>
		  		</c:forEach>
			</div>
		</div>
	</div>

	</body>
</html>