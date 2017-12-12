<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.card.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%-- 此頁練習採用 EL 的寫法取值 --%>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">




<html>
<head>
<title>集點卡資訊</title>

	<style>
	
			
						
  table#table-1 {
	background-color: #DCE6D2;
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
	width: 800px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #647D73;
  }
  
  
</style>

</head>
<body bgcolor='white'>

<%	

		CardService cardSvc = new CardService();
		List list = new ArrayList();
		list = cardSvc.getCardsBySto_num("ST0000000001");
		request.setAttribute("cardVO2", list);

%>

<center>
<table id="table-1">
	<tr><td>
		 <h3>店家集點卡資訊</h3>
		 
	</td></tr>
</table>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table>
	<tr align='center'  valign='middle'>
		<th>集點卡編號</th>
		<th>需集滿</th>
		<th>可折扣金額</th>
		<th>折價券敘述</th>
		<th>有效期限</th>
		<th>狀態</th>
		<th>操作</th>
	</tr>
	<c:forEach var="cardVO" items="${cardVO2}">
		<tr align='center' valign='middle' ><!--將修改的那一筆加入對比色而已-->
			<td>${cardVO.card_kinds}</td>
			<td>${cardVO.points}</td>
			<td>${cardVO.points_cash}</td>
			<td>${cardVO.card_des}</td>
			<td>${cardVO.exp_date}</td>
			<td id="cardstatus">${cardVO.status}</td>	
			<td>
			<c:if test="${cardVO.status.equals('下架')}">
			
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/CardServlet" style="margin-bottom: 0px;">			
			     <input type="submit" value="上架"> 
			     <input type="hidden" name="card_kinds"      value="${cardVO.card_kinds}">
			      <input type="hidden" name="sto_num"      value="${cardVO.sto_num}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->            
			     <input type="hidden" name="action"	    value="upCard"></FORM>
			</td>
			</c:if>
			<c:if test="${cardVO.status.equals('上架')}">
			 <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/CardServlet" style="margin-bottom: 0px;">
			     <input type="submit" value="下架"> 
			     <!--要回傳的數值-->
			     <input type="hidden" name="sto_num"     value="${cardVO.sto_num}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->		             
			     <input type="hidden" name="action"	    value="downCard"></FORM>
			</td>
			</c:if>
	
		</tr>
	</c:forEach>
</table>
</center>


</body>
</html>