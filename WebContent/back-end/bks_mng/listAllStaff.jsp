<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.backstage_management.model.*"%>


<%
	String bm_no = (String) session.getAttribute("bm_no");
	BackstageManagementService bmSvc = new BackstageManagementService();	
	List<BackstageManagementVO> list = bmSvc.getAll();
    pageContext.setAttribute("list",list);
%>

<html>
<head>
	<title>所有後臺人員</title>
	<style>
		.list>tbody>tr>td{
			vertical-align:middle;
		}
	</style>
</head>

<body>
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
			<th>人員編號</th>		
			<th>名稱</th>
			<th>手機</th>
			<th>信箱</th>
			<th>銀行帳號</th>
			<th>大頭貼</th>
			<th>人員帳號</th>
			<th>狀態</th>
			<th>修改</th>
		</tr>
		
	
		<c:forEach var="bmVO" items="${list}" >
		<tr ${(bmVO.bm_no==param.bm_no)?'bgcolor=#DCE6D2':''}>
			<td>${bmVO.bm_no}</td>	
			<td>${bmVO.bm_name}</td>
			<td>${bmVO.bm_number}</td>
			<td>${bmVO.bm_mail}</td>
			<td>${bmVO.bm_banknum}</td>
			<td><img height=50 src="<%=request.getContextPath()%>/BmGifReader?bm_no=${bmVO.bm_no}"></td> 
			<td>${bmVO.bm_num}</td>
			<td>${bmVO.bm_jstatus}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%= request.getContextPath() %>/bks_mng/BksMng.do" style="margin-bottom: 0px">
			     <input type="submit" value="修改" class="btn btn-green">
			     <input type="hidden" name="bm_no" value="${bmVO.bm_no}">
			      <input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
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


</script>
</body>
</html>