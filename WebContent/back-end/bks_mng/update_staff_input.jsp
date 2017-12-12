<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.backstage_management.model.*"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="flSvc" scope="request" class="com.func_list.model.FuncListService" />
	
<% 
	BackstageManagementVO bmVO = (BackstageManagementVO) request.getAttribute("bmVO"); 
	List<String> funcList = (List<String>)request.getAttribute("funcList");
%>

<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<title>員工資料修改</title>
	<style>
		.magic{
			background-color:#FFFFFF;
			color:#ffd280;
		}
		.panel-green{
			border:1px solid #595942;
			color:#595942;	
		}
		.panel-form{
			margin-top:20px;
			margin-bottom:20px;
		}
		.productList{
			font-size:11pt;
		}
		img{
			display:block;
			margin: 0 auto;
		}

	</style>
</head>

<body>
	<jsp:include page="/back-end/back_top.jsp" /> <!-- navbar -->
	<!-- 1層大框框 -->
	<div class="container-fluid">
		<div class="row">
	<!-- 2層框左 -->	
			<jsp:include page="/back-end/back_left.jsp" /> <!-- leftSidebar -->
	<!-- 2層框右 -->		
			<div class="col-xs-12 col-sm-8 col-sm-offset-3" >				
				<div class="block-center panelheight">
	<!--========================== 功能放這邊 =============================================-->




<%-- 查詢+ListAll按鈕 --%>
<jsp:include page="/back-end/bks_mng/btn_select.jsp" />

<div class="col-xs-12 col-sm-9">
	<div class="row">	
		<div class="panel">
        	<div class="panel-heading panel-green"><h3 class="text-center">後臺人員修改</h3></div>
	
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

					<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/bks_mng/BksMng.do" enctype="multipart/form-data">
					
					<div class="container">
						<div class="row">
						
							<div class="col-xs-12 col-sm-3">
							<!-- 員工圖片 -->
								<div class="form-group panel-form">
									<label for="bm_img" class="col-sm-12 text-center">員工圖片</label>
									<div class="col-sm-12">
										<img src="<%=request.getContextPath()%>/BmGifReader?bm_no=${bmVO.bm_no}"><br>
										<input type="File" name="bm_img" id="bm_img" class="form-control"/>	
									</div>
								</div>						
							</div><!-- col-3 -->
							
							<div class="col-xs-12 col-sm-6">
							<!-- 員工資料 -->
							
								<div class="form-group panel-form">
									<label for="bm_no" class="col-sm-3 control-label">員工編號</label>
									<div class="col-sm-8">
										<input type="TEXT" name="bm_no" id="bm_no" value="${bmVO.bm_no}" disabled class="form-control"/>	
									</div>
								</div>							
								
								<div class="form-group panel-form">
									<label for="bm_name" class="col-sm-3 control-label">員工名稱</label>
									<div class="col-sm-8">
										<input type="TEXT" name="bm_name" id="bm_name" value="${bmVO.bm_name}" class="form-control"/>	
									</div>
								</div>									
							
								<div class="form-group panel-form">
									<label for="bm_num" class="col-sm-3 control-label">員工帳號</label>
									<div class="col-sm-8">
										<input type="TEXT" name="bm_num" id="bm_num" value="${bmVO.bm_num}" class="form-control"/>	
									</div>
								</div>
								
								<c:if test="${sessionScope.bm_no == bmVO.bm_no }">
									<div class="form-group panel-form">
										<label for="bm_pwd" class="col-sm-3 control-label">員工密碼</label>
										<div class="col-sm-8">
											<input type="TEXT" name="bm_pwd" id="bm_pwd" value="${bmVO.bm_pwd}" class="form-control"/>	
										</div>
									</div>
								</c:if>
								
								<div class="form-group panel-form">
									<label for="bm_number" class="col-sm-3 control-label">員工手機</label>
									<div class="col-sm-8">
										<input type="TEXT" name="bm_number" id="bm_number" value="${bmVO.bm_number}" class="form-control"/>	
									</div>
								</div>

								<div class="form-group panel-form">
									<label for="bm_mail" class="col-sm-3 control-label">員工信箱</label>
									<div class="col-sm-8">
										<input type="TEXT" name="bm_mail" id="bm_mail" value="${bmVO.bm_mail}" class="form-control"/>	
									</div>
								</div>
								
								<div class="form-group panel-form">
									<label for="bm_banknum" class="col-sm-3 control-label">員工銀行帳號</label>
									<div class="col-sm-8">
										<input type="TEXT" name="bm_banknum" id="bm_banknum" value="${bmVO.bm_banknum}" class="form-control"/>	
									</div>
								</div>								
																								
								<div class="form-group panel-form">
									<label for="bm_jstatus" class="col-sm-3 control-label">員工狀態</label>
									<div class="col-sm-8">
										<select size="1" name="bm_jstatus" class="form-control">
											<option value="在職" ${(bmVO.bm_jstatus=='在職')? 'selected':'' } >在職
											<option value="離職" ${(bmVO.bm_jstatus=='離職')? 'selected':'' } >離職
										</select>
									</div>
								</div>								
							
								<div class="form-group panel-form">
									<label for="func" class="col-sm-3 control-label">員工權限</label>
									<div class="col-sm-8">
										
										<c:forEach var="funcVO" items="${flSvc.all}">
										<label class="h4 checkbox-inline">
											<input type="checkbox" name=func value="${funcVO.func_no}"
												
													<c:forEach var="funcList" items="${funcList}">
														${(funcList==funcVO.func_no)? 'checked':'' }
													</c:forEach>					
												
												> ${funcVO.func_name}
										</label><br>		
										</c:forEach>
										
									</div>
								</div>								
							
							
							
							</div><!-- col-6 -->
											
						</div><!-- row -->
					</div><!-- container -->	
					

					<input type="hidden" name="bm_no" value="${bmVO.bm_no}">
					<input type="hidden" name="bm_no" value="${bmVO.bm_no}">
					<input type="hidden" name="action" value="update">
					<div class="panel-footer ">
						<input type="submit" value="送出修改" class="btn btn-green btn-block panel-form">
					</div>
					</FORM>
				</div><!-- from horizon -->	
			</div><!-- panel body -->
		</div><!-- panel -->	
	</div><!-- row -->	
</div>						
	
<!--========================== 功能放這邊 =============================================-->			
				</div><!-- class="block-center panelheight" -->			
			</div><!-- class="col-xs-12 col-sm-8 col-sm-offset-3" -->
		</div><!-- div class="row" -->
	</div><!-- div class="container-fluid" -->
	
	<jsp:include page="/back-end/back_foot.jsp" />

	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script>

	</script>
</body>
</html>