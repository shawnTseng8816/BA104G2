<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>
<jsp:useBean id="pdcTSvc" scope="request" class="com.product_type.model.ProductTypeService" />	
<jsp:useBean id="pdSvc" scope="request" class="com.product.model.ProductService" />
<jsp:useBean id="mcSvc" scope="request" class="com.merged_commodity.model.MergedCommodityService" />
<%
	List<ProductVO> list = (List<ProductVO>) request.getAttribute("stolistAllProduct2");
	pageContext.setAttribute("list",list);
%>


<html>
<head>
	<title>���a�d�ӫ~�W��</title>
	<style>
		.list>tbody>tr>td{
			vertical-align:middle;
		}
	</style>
</head>

<body >

<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message.value}</li>
		</c:forEach>
	</ul>
</c:if>


	<table class="table list">
		<tr>
			<th>�ӫ~�s��</th>		
			<th>�ӫ~�W��</th>
			<th>�p�M����</th>
			<th>�j�M����</th>
			<th>�y�z</th>
			<th>�Ϥ�</th>
			<th>�ӫ~���O</th>
			<th>���A</th>
			<th>�X�֪��A</th>
			<th>�ק�</th>
			<th>�R��</th>
		</tr>

	<c:forEach var="PdcVO" items="${list}" >
		
		<tr>
			<td>${PdcVO.com_num}</td>	
			<td>${PdcVO.com_name}</td>
			<td>${PdcVO.m_price}</td>
			<td>${PdcVO.l_price}</td>
			<td>${PdcVO.discribe}</td>
			<td><img height=50 src="<%=request.getContextPath()%>/PdcGifReader?com_num=${PdcVO.com_num}"></td> 
			 <c:forEach var="pdcTSvc" items="${pdcTSvc.all}" > 
	         	<c:if test="${pdcTSvc.pt_num==PdcVO.pt_num}" var="condition" scope="page">
	         		<td>${pdcTSvc.pt_name}</td>
	         	</c:if>
	         </c:forEach>
			<td>${PdcVO.status}</td>
			<td width=200>				
			<c:forEach var="mcVO" items="${mcSvc.getMerList(PdcVO.mercom_num)}" varStatus="p">
				<span>${p.count} -
				${pdSvc.getOneProduct(mcVO.com_num).com_name} 
				�p�M ${pdSvc.getOneProduct(mcVO.com_num).m_price}
				�j�M ${pdSvc.getOneProduct(mcVO.com_num).l_price}</span>
			</c:forEach>
			</td>
			
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/pdc_mng/StoPdcMng.do" style="margin-bottom: 0px;">
			     <input type="submit" value="�ק�"  class="btn btn-green">
			     <input type="hidden" name="com_num"  value="${PdcVO.com_num}">
			     <input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM id="form_delete" METHOD="post" ACTION="<%=request.getContextPath()%>/pdc_mng/StoPdcMng.do" style="margin-bottom: 0px;">
			     <input type="button" value="�R��" class="btn btn-org delete">
			     <input type="hidden" name="com_num"  value="${PdcVO.com_num}">
			     <input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
	</table>

<script>
	//table hover color
	$('tr').hover(
		function(){
			$(this).css("background-color","#ffe4b3");
		},
		function(){
			$(this).css("background-color","#FFFFFF");
		}
	);
	
	//delete alert
	$('.delete').click(function(){
		swal({
			  title: '�T�w�n�R�����?',
			  text: "��ƧR����L�k�^�_",
			  type: 'warning',
			  showCancelButton: true,
			  confirmButtonColor: '#3C9682',
			  cancelButtonColor: '#FA5532',
			  confirmButtonText: '�R��',
			  cancelButtonText: '����'
			}).then((result) => {
			  if (result.value) {
				  swal({
					  position: 'center',
					  type: 'success',
					  title: '��Ƥw�R��',
					  showConfirmButton: false,
					  timer: 1000
				 	}).then((result) => {					  
			 			  $("#form_delete").submit();					  
					})
			  }
			})
	});
</script>
</body>
</html>