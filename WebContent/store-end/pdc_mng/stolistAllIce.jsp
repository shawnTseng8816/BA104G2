<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ice_list.model.*"%>

<%	
	String sto_num = (String) session.getAttribute("sto_num");
	IceListService iceSvc = new IceListService(); 	
	List<IceListVO> list = iceSvc.getIceList(sto_num); 
    pageContext.setAttribute("list",list);
%>


<html>
<head>
	<title>所有冰塊商品</title>
	<style>
		.list>tbody>tr>td{
			vertical-align:middle;
		}
	</style>
</head>
<body >

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>


	<table class="table list">
		<tr>
			<th>冰塊編號</th>			
			<th>冰塊名稱</th>
			<th>狀態</th>
			<th>修改</th>
			<th>刪除</th>
		</tr>
		
		<c:forEach var="iceVO" items="${list}">
			
			<tr ${(iceVO.ice_num==param.ice_num)?'bgcolor=#DCE6D2':''}>
				<td>${iceVO.ice_num}</td>				
				<td>${iceVO.ice_type}</td>
				<td>${iceVO.status}</td>
				<td>
				  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/pdc_mng/StoIceMng.do" style="margin-bottom: 0px;">
			     	 <input type="submit" value="修改"  class="btn btn-green">
				     <input type="hidden" name="ice_num" value="${iceVO.ice_num}">
				     <input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
				     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
				</td>
				<td>
				  <FORM id="form_delete" METHOD="post" ACTION="<%=request.getContextPath()%>/pdc_mng/StoIceMng.do" style="margin-bottom: 0px;">
			     	 <input type="button" value="刪除" class="btn btn-org delete">
				     <input type="hidden" name="ice_num"  value="${iceVO.ice_num}">
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
			  title: '確定要刪除資料?',
			  text: "資料刪除後無法回復",
			  type: 'warning',
			  showCancelButton: true,
			  confirmButtonColor: '#3C9682',
			  cancelButtonColor: '#FA5532',
			  confirmButtonText: '刪除',
			  cancelButtonText: '取消'
			}).then((result) => {
			  if (result.value) {
				  swal({
					  position: 'center',
					  type: 'success',
					  title: '資料已刪除',
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