<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.card.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%-- �����m�߱ĥ� EL ���g�k���� --%>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">




<html>
<head>
<title>���I�d��T</title>

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
		 <h3>���a���I�d��T</h3>
		 
	</td></tr>
</table>

<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table>
	<tr align='center'  valign='middle'>
		<th>���I�d�s��</th>
		<th>�ݶ���</th>
		<th>�i�馩���B</th>
		<th>�����ԭz</th>
		<th>���Ĵ���</th>
		<th>���A</th>
		<th>�ާ@</th>
	</tr>
	<c:forEach var="cardVO" items="${cardVO2}">
		<tr align='center' valign='middle' ><!--�N�ק諸���@���[�J����Ӥw-->
			<td>${cardVO.card_kinds}</td>
			<td>${cardVO.points}</td>
			<td>${cardVO.points_cash}</td>
			<td>${cardVO.card_des}</td>
			<td>${cardVO.exp_date}</td>
			<td id="cardstatus">${cardVO.status}</td>	
			<td>
			<c:if test="${cardVO.status.equals('�U�[')}">
			
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/CardServlet" style="margin-bottom: 0px;">			
			     <input type="submit" value="�W�["> 
			     <input type="hidden" name="card_kinds"      value="${cardVO.card_kinds}">
			      <input type="hidden" name="sto_num"      value="${cardVO.sto_num}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->            
			     <input type="hidden" name="action"	    value="upCard"></FORM>
			</td>
			</c:if>
			<c:if test="${cardVO.status.equals('�W�[')}">
			 <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/CardServlet" style="margin-bottom: 0px;">
			     <input type="submit" value="�U�["> 
			     <!--�n�^�Ǫ��ƭ�-->
			     <input type="hidden" name="sto_num"     value="${cardVO.sto_num}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->		             
			     <input type="hidden" name="action"	    value="downCard"></FORM>
			</td>
			</c:if>
	
		</tr>
	</c:forEach>
</table>
</center>


</body>
</html>