<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%-- �����m�߱ĥ� EL ���g�k���� --%>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">

<jsp:useBean id="remSvc" scope="page" class="com.rem_record.model.RemRecordService" />


<html>
<head>
<title>�Ҧ��ݶץX���</title>

</head>

<body bgcolor='white'>

<jsp:include page="/back-end/back_top.jsp" /> <!-- navbar -->
	<!-- 1�h�j�خ� -->
	<div class="container-fluid">
		<div class="row">
	<!-- 2�h�إ� -->	
			<jsp:include page="/back-end/back_left.jsp" /> <!-- leftSidebar -->
	<!-- 2�h�إk -->		
			<div class="col-xs-12 col-sm-8 col-sm-offset-3" >				
				<div class="block-center panelheight">
				
	<!--========================== �\���o�� ===��������������������==========================================-->
	
	
	
	
<center>

<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>



<div class="container">
    <div class="row">
        <div class="col-md-12">
             <h3><div class="alert " style="background: #ddd;">
         				   �Ҧ��ݶץX���</div></h3>
            <table class="table">
                 <tbody>   
                    <tr align='center' valign='middle'>
                    <th>�״ڽs��</th>
					<th>���a�s��</th>
					<th>�פJ�b��</th>
					<th>�״ڪ��B</th>
					<th>�ӽЮɶ�</th>
					<th>�ӽЪ��A</th>
					<th>�q�L</th>
					<th>���q�L</th>
                    </tr>
                 	<c:forEach var="remrecordVO" items="${remSvc.apply}">
		<tr align='center' valign='middle' ><!--�N�ק諸���@���[�J����Ӥw-->
			<td>${remrecordVO.rem_num}</td>
			<td>${remrecordVO.sto_num}</td>
			<td>${remrecordVO.rem_account}</td>
			<td>${remrecordVO.rem_cash}</td>
			<td><fmt:formatDate value="${remrecordVO.apply_date}" pattern="yyyy-MM-dd HH:mm:s"/></td>
			<td>${remrecordVO.rem_status}</td>	
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/RemRecordServlet" style="margin-bottom: 0px;">
			     <input type="submit" value="�q�L"> 
			     <input type="hidden" name="rem_num"      value="${remrecordVO.rem_num}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->            
			     <input type="hidden" name="action"	    value="rem_Pass"></FORM>
			</td>
			<td>
			 <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/RemRecordServlet" style="margin-bottom: 0px;">
			     <input type="submit" value="���q�L"> 
			     <!--�n�^�Ǫ��ƭ�-->
			     <input type="hidden" name="rem_num"      value="${remrecordVO.rem_num}">
			     <input type="hidden" name="sto_num"      value="${remrecordVO.sto_num}">
			     <input type="hidden" name="rem_cash"      value="${remrecordVO.rem_cash}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->		             
			     <input type="hidden" name="action"	    value="rem_NoPass"></FORM>
			</td>
	
		</tr>
	</c:forEach>
     
                </tbody>
            </table>
        </div>
    </div>
</div>


</center>


<!--========================== �\���o�� ====������������������=====================================-->			
				</div><!-- class="block-center panelheight" -->			
			</div><!-- class="col-xs-12 col-sm-8 col-sm-offset-3" -->
		</div><!-- div class="row" -->
	</div><!-- div class="container-fluid" -->
	
	<jsp:include page="/back-end/back_foot.jsp" />

	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>

</html>