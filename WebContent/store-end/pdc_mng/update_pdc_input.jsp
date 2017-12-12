<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.product.model.*"%>
<%@ page import="com.merged_commodity.model.*"%>
<jsp:useBean id="pdSvc" scope="request" class="com.product.model.ProductService" />
<jsp:useBean id="mcSvc" scope="request" class="com.merged_commodity.model.MergedCommodityService" />
 <jsp:useBean id="pdcTSvc" scope="request" class="com.product_type.model.ProductTypeService" />	
<% 
	ProductVO productVO = (ProductVO) request.getAttribute("productVO"); 
%>

<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<title>�ӫ~��ƭק� - update_pdc_input.jsp</title>
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
	img{
		display:block;
		margin: 0 auto;
	}

	</style>
</head>

<body>
	<jsp:include page="/store-end/store_top.jsp" /> <!-- navbar -->
	<!-- 1�h�j�خ� -->
	<div class="container-fluid">
		<div class="row">
	<!-- 2�h�إ� -->	
			<jsp:include page="/store-end/store_left.jsp" /> <!-- leftSidebar -->
	<!-- 2�h�إk -->		
			<div class="col-xs-12 col-sm-8 col-sm-offset-3" >				
				<div class="block-center panelheight">
	<!--========================== �\���o�� =============================================-->

<%-- �d��+ListAll���s --%>

<jsp:include page="/store-end/pdc_mng/btn_select.jsp" />

<div class="col-xs-12 col-sm-9">
	<div class="row">	
		<div class="panel">
        	<div class="panel-heading panel-green"><h3 class="text-center">�ӫ~��ƭק�</h3></div>
	
				<div class="panel-body panel-green">

				<%-- ���~��C --%>
				<c:if test="${not empty errorMsgs}">
					<font style="color:red">�Эץ��H�U���~:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color:red">${message.value}</li>
						</c:forEach>
					</ul>
				</c:if>
				
				<div class="form-horizontal">

				<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/pdc_mng/StoPdcMng.do" enctype="multipart/form-data">
				
				<div class="container">
					<div class="row">
						<div class="col-xs-12 col-sm-3">
						<!-- �ӫ~�Ϥ� -->
							<div class="form-group panel-form">
								<label for="img" class="col-sm-12 text-center">�ӫ~�Ϥ�</label>
								<div class="col-sm-12">
									<img src="<%=request.getContextPath()%>/PdcGifReader?com_num=${productVO.com_num}"><br>
									<input type="File" name="img" id="img" class="form-control"/>	
								</div>
							</div>	
						</div>
						
						<div class="col-xs-12 col-sm-6">
						<!-- �ӫ~��� -->
							<div class="form-group panel-form">
								<label for="com_num" class="col-sm-3 control-label">�ӫ~�s��</label>
								<div class="col-sm-8">
									<input type="TEXT" name="com_num" id="com_num" value="${productVO.com_num}" disabled class="form-control"/>	
								</div>
							</div>
						
							<div class="form-group panel-form">
								<label for="com_name" class="col-sm-3 control-label">�ӫ~�W��</label>
								<div class="col-sm-8">
									<input type="TEXT" name="com_name" id="com_name" value="${productVO.com_name}" class="form-control"/>	
								</div>
							</div>
							
							<div class="form-group panel-form">
								<label for="m_price" class="col-sm-3 control-label">�p�M�ӫ~����</label>
								<div class="col-sm-8">
									<input type="TEXT" name="m_price" id="m_price" value="${productVO.m_price}" class="form-control"/>	
								</div>
							</div>						
							
							<div class="form-group panel-form">
								<label for="l_price" class="col-sm-3 control-label">�j�M�ӫ~����</label>
								<div class="col-sm-8">
									<input type="TEXT" name="l_price" id="l_price" value="${productVO.l_price}" class="form-control"/>	
								</div>
							</div>	
	
							<div class="form-group panel-form">
								<label for="discribe" class="col-sm-3 control-label">�ӫ~�ԭz</label>
								<div class="col-sm-8">
									<input type="TEXT" name="discribe" id="discribe" value="${productVO.discribe}" class="form-control"/>	
								</div>
							</div>	

							<div class="form-group panel-form">
								<label for="pt_num" class="col-sm-3 control-label">�ӫ~���O�s��</label>
								<div class="col-sm-8">
									<select size="1" name="pt_num" class="form-control">
							         <c:forEach var="pdcTVO" items="${pdcTSvc.all}" > 
							         	<option value="${pdcTVO.pt_num}" ${(productVO.pt_num==pdcTVO.pt_num)? 'selected':'' } >${pdcTVO.pt_name}
							         </c:forEach>   
						       		</select>	
								</div>
							</div>		 
		 
							<div class="form-group panel-form">
								<label for="status" class="col-sm-3 control-label">�ӫ~���A</label>
								<div class="col-sm-8">
									<select size="1" name="status" class="form-control">
										<option value="���W�[" ${(productVO.status=='���W�[')? 'selected':'' } >���W�[
										<option value="�w�W�[" ${(productVO.status=='�w�W�[')? 'selected':'' } >�w�W�[
									</select>	
								</div>
							</div>		 
	
							<div class="form-group panel-form">
								<label for="" class="col-sm-3 control-label">�X�ְӫ~</label>
								<div class="col-sm-8">
									<c:forEach var="mcVO" items="${mcSvc.getMerList(productVO.mercom_num)}" varStatus="p">
										<small>
										${pdSvc.getOneProduct(mcVO.com_num).com_name} 
										�p�M ${pdSvc.getOneProduct(mcVO.com_num).m_price}
										�j�M ${pdSvc.getOneProduct(mcVO.com_num).l_price}</small><br>
									</c:forEach>
								</div>
							</div>		
							<input type="hidden" name="sto_num" value="${sto_num}">					
							<input type="hidden" name="com_num" value="${productVO.com_num}">					
							<input type="hidden" name="mercom_num" value="${productVO.mercom_num}">
							<input type="hidden" name="action" value="update">
						</div>				
					</div>
				</div>
				
					<div class="panel-footer ">
						<input type="submit" value="�e�X�ק�" class="btn btn-green btn-block panel-form">
					</div>

					</FORM>
				</div><!-- from horizon -->	
			</div><!-- panel body -->
		</div><!-- panel -->	
	</div><!-- row -->	
	
		
<!--========================== �\���o�� =============================================-->			
				</div><!-- class="block-center panelheight" -->			
			</div><!-- class="col-xs-12 col-sm-8 col-sm-offset-3" -->
		</div><!-- div class="row" -->
	</div><!-- div class="container-fluid" -->
	
	<jsp:include page="/store-end/store_foot.jsp" />

	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>