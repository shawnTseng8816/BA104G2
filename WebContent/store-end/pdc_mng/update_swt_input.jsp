<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.sweetness.model.*"%>

<% 
	SweetnessVO sweetnessVO = (SweetnessVO) request.getAttribute("sweetnessVO"); 
%>

<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<title>修改甜度資料</title>
	<style>
	.magic{
		background-color:#FFFFFF;
		color:#ffd280;
	}
	.panel-green{
		border:1px solid #3C9682;
		color:#3C9682;	
	}
	.panel-form{
		margin-top:20px;
		margin-bottom:20px;
	}
	.productList{
		font-size:11pt;
	}
	</style>
</head>

<body>
	<jsp:include page="/store-end/store_top.jsp" /> <!-- navbar -->
	<!-- 1層大框框 -->
	<div class="container-fluid">
		<div class="row">
	<!-- 2層框左 -->	
			<jsp:include page="/store-end/store_left.jsp" /> <!-- leftSidebar -->
	<!-- 2層框右 -->		
			<div class="col-xs-12 col-sm-8 col-sm-offset-3" >				
				<div class="block-center panelheight">
	<!--========================== 功能放這邊 =============================================-->

<%-- 查詢+ListAll按鈕 --%>
<jsp:include page="/store-end/pdc_mng/btn_select.jsp" />

<div class="col-xs-12 col-sm-5">
	<div class="row">	
		<div class="panel ">
        	<div class="panel-heading panel-green"><h3 class="text-center">甜度資料修改</h3></div>
	
				<div class="panel-body panel-green">

				<%-- 錯誤表列 --%>				
				<c:if test="${not empty errorMsgs}">
					<font style="color:red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color:red">${message.value}</li>
						</c:forEach>
					</ul>
				</c:if>
				
				<div class="form-horizontal">
					<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/pdc_mng/StoSwtMng.do">
						
						<div class="form-group panel-form">
							<label for="sweet_num" class="col-sm-3 control-label">甜度編號</label>
							<div class="col-sm-9">
								<input type="TEXT" name="sweet_num" id="sweet_num" value="${sweetnessVO.sweet_num}" disabled class="form-control"/>	
							</div>
						</div>
						
						<div class="form-group panel-form">
							<label for="sweet_type" class="col-sm-3 control-label">甜度名稱</label>
							<div class="col-sm-9">
								<input type="TEXT" name="sweet_type" id="sweet_type" value="${sweetnessVO.sweet_type}" class="form-control"/>	
							</div>
						</div>						
						
						<div class="form-group panel-form">
							<label for="status" class="col-sm-3 control-label">甜度狀態</label>
							<div class="col-sm-9">
								<select size="1" name="status" class="form-control">
									<option value="上架" ${(sweetnessVO.status=='上架')? 'selected':'' } >上架
									<option value="下架" ${(sweetnessVO.status=='下架')? 'selected':'' } >下架
								</select>
							</div>						
						</div>								
												
						<input type="hidden" name="action" value="update">
						<input type="hidden" name="sweet_num" value="${sweetnessVO.sweet_num}">
						<input type="hidden" name="sto_num" value="${sweetnessVO.sto_num}">
						<input type="hidden" name="requestURL" value="<%=request.getAttribute("requestURL")%>">
						<div class="panel-footer ">						
							<input type="submit" value="送出修改" class="btn btn-green btn-block panel-form">
						</div>
							
					</FORM>
				</div><!-- from horizon -->	
			</div><!-- panel body -->
		</div><!-- panel -->	
	</div><!-- row -->
	
<!--========================== 功能放這邊 =============================================-->			
				</div><!-- class="block-center panelheight" -->			
			</div><!-- class="col-xs-12 col-sm-8 col-sm-offset-3" -->
		</div><!-- div class="row" -->
	</div><!-- div class="container-fluid" -->
	
	<jsp:include page="/store-end/store_foot.jsp" />

	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>