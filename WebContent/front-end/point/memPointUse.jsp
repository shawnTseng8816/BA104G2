<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.value_record.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">

<jsp:useBean id="OrSvc" class="com.order_master.model.OrderMasterService"/>




<head>

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
             <h3><div class="alert alert-infoeric">
         	       �I�ƨϥά��� </div></h3>
            <div class="alert alert-success" style="display:none;">
               </div>
            <table class="table">
                 <tbody>   
                    <tr align='center' valign='middle'>
                    <td>�q��s��</td>
					<td>���a�s��</td>
					<td>����ɶ�</td>  
					<td>�ϥ��I��</td>   
                    </tr>
	<c:forEach varStatus="valuecount" var="orderMasterVO" items="${OrSvc.getPointOrder(sessionScope.mem_num)}">
		<tr align='center' valign='middle' <c:if test="${valuecount.count%2!=0}" > class="success"</c:if> > <!--�N�ק諸���@���[�J����Ӥw-->
			<td>${orderMasterVO.or_num}</td>
			<td>${orderMasterVO.sto_num}</td>
			<td><fmt:formatDate value="${orderMasterVO.pre_rece}" pattern="yyyy/MM/dd HH:mm:ss"/></td>		
			<td>${orderMasterVO.or_amount}</td>			
		</tr>
	</c:forEach>
	   <c:if test="${OrSvc.getPointOrder(sessionScope.mem_num).isEmpty()}">
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