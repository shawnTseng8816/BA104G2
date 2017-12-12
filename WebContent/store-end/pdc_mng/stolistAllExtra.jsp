<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.extra.model.*"%>

<%	
	String sto_num = (String) session.getAttribute("sto_num");
	ExtraService extSvc = new ExtraService();
	List<ExtraVO> list = extSvc.getExtras(sto_num);
    pageContext.setAttribute("list",list);
%>


<html>
<head>
	<title>�Ҧ��[�ưӫ~</title>
	<style>
		.list>tbody>tr>td{
			vertical-align:middle;
		}
	</style>
</head>
<body>

<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

	<table class="table list">
		<tr>
			<th>�[�ƽs��</th>		
			<th>�[�ƦW��</th>
			<th>�[�ƪ��B</th>
			<th>���A</th>
			<th>�ק�</th>
			<th>�R��</th>
		</tr>
		
	<c:forEach var="extVO" items="${list}">
			
		<tr ${(extVO.ext_num==param.ext_num)?'bgcolor=#DCE6D2':''}>
			<td>${extVO.ext_num}</td>	
			<td>${extVO.ext_name}</td>
			<td>${extVO.ext_amount}</td>
			<td>${extVO.status}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/pdc_mng/StoExtMng.do" style="margin-bottom: 0px;">
			     <input type="submit" value="�ק�"  class="btn btn-green">
			     <input type="hidden" name="ext_num" value="${extVO.ext_num}">
			     <input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM id="form_delete" METHOD="post" ACTION="<%=request.getContextPath()%>/pdc_mng/StoExtMng.do" style="margin-bottom: 0px;">
			     <input type="button" value="�R��" class="btn btn-org delete">
			     <input type="hidden" name="ext_num"  value="${extVO.ext_num}">
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