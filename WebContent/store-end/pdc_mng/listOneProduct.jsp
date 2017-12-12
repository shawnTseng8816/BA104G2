<%@ page contentType="text/html; charset=Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.product.model.*"%>
<jsp:useBean id="pdcTSvc" scope="request" class="com.product_type.model.ProductTypeService" />	

<%
  ProductVO productVO = (ProductVO) request.getAttribute("productVO"); 
%>

<html>
<head>
	<title></title>
	
	<style>
	  table#table-1 {
		background-color: #CCCCFF;
	    border: 2px solid black;
	    text-align: center;
	  }
	  table#table-1 h4 {
	    color: red;
	    display: block;
	    margin-bottom: 1px;
	  }
	  h4 {
	    color: blue;
	    display: inline;
	  }
	</style>

	<style>
	  table {
		width: 600px;
		background-color: white;
		margin-top: 5px;
		margin-bottom: 5px;
	  }
	  table, th, td {
	    border: 1px solid #CCCCFF;
	  }
	  th, td {
	    padding: 5px;
	    text-align: center;
	  }
	</style>

</head>
<body >

	<table id="table-1">
		<tr>
			<td><h3>以更新以下資料</h3></td>
			<td><h4><a href="<%= request.getContextPath() %>/store-end/pdc_mng/store_select_page.jsp">回商品管理首頁</a></h4></td>
		</tr>
	</table>
	
	<table>
		<tr>
			<th>商品編號</th>		
			<th>商品名稱</th>
			<th>小杯價錢</th>
			<th>大杯價錢</th>
			<th>描述</th>
			<th>圖片</th>
			<th>商品類別</th>
			<th>狀態</th>
		</tr>		
		<tr>
			<td>${productVO.com_num}</td>	
			<td>${productVO.com_name}</td>
			<td>${productVO.m_price}</td>
			<td>${productVO.l_price}</td>
			<td>${productVO.discribe}</td>
			<td><img height=50 src="<%=request.getContextPath()%>/PdcGifReader?com_num=${productVO.com_num}"></td> 
			<td> <c:forEach var="pdcTSvc" items="${pdcTSvc.all}" > 
		         	<c:if test="${pdcTSvc.pt_num==productVO.pt_num}" var="condition" scope="page">
		         		${pdcTSvc.pt_name}
		         	</c:if>
		     	 </c:forEach>
		     </td>		
			<td>${productVO.status}</td>			
		</tr>
	</table>

</body>
</html>