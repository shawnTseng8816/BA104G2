<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.card_record.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%-- �����m�߱ĥ� EL ���g�k���� --%>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">




<html>
<head>
<title>�|�����I����</title>

<style>
.alert-infoeric {
    color: #fcf8e3;
    background-color: #3c9682;
    
}
</style>


</head>
<jsp:include page="/front-end/member_top.jsp" flush="true" />
<jsp:include page="/front-end/coupon_notify.jsp" />


<body >

<%	

		CardRecordService cardRecordSvc = new CardRecordService();
		List list = new ArrayList();
		String mem_num = (String)session.getAttribute("mem_num");
		list = cardRecordSvc.getMemRecordByMemNum(mem_num);
		request.setAttribute("cardRecordVO", list);

%>

<center>


<div class="container">
    <div class="row">
        <div class="col-md-12">
           <h3><div class="alert alert-infoeric">  
         	       �|�����I���� </div></h3>
            <div class="alert alert-success" style="display:none;">
                <span class="glyphicon glyphicon-ok"></span> Drag table row and cange Order</div>
            <table class="table">

                    
                <tbody>   
                 <tr align='center' valign='middle' >
                 	 <td>���I�d�s��</td>
					<td>�q��s��</td>
					<td>��o�I��</td>
					<td>������</td>   
                    </tr>
       
     
	<c:forEach var="cardRecordVO" varStatus="valuecount"  items="${cardRecordVO}">
		<tr align='center' valign='middle' <c:if test="${valuecount.count%2!=0}" > class="success"</c:if>  ><!--�N�ק諸���@���[�J����Ӥw-->
			<td>${cardRecordVO.card_num}</td>
			<td>${cardRecordVO.or_num}</td>
			<td>${cardRecordVO.add_value}</td>
			<td><fmt:formatDate value="${cardRecordVO.get_date}" pattern="yyyy/MM/dd "/></td>
		</tr>
	</c:forEach>
	                     <c:if test="${cardRecordVO.isEmpty()}">
	   	<div class="text-center" style="color:red; font-size:100px;">�ȵL���!!</div>
	   </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>





</center>

<jsp:include page="/front-end/member_foot.jsp" flush="true" />
</body>
</html>