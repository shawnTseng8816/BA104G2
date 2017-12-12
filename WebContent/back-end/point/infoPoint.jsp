<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%-- �����m�߱ĥ� EL ���g�k���� --%>


<jsp:useBean id="valueSvc" scope="page" class="com.value_record.model.ValueRecordService" />


<html>
<head>
<title>�x�Ⱦ��v����  </title>


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
         				   �x�Ⱦ��v����  </div></h3>
            <table class="table">
                 <tbody>   
                    <tr align='center' valign='middle'>
                    <th>�x�Ƚs��</th>
					<th>�|���s��</th>
					<th>�x�Ȥ��</th>
					<th>�x�Ȫ��B</th>
                    </tr>
               <c:forEach var="valuerecordVO" items="${valueSvc.all}">
			<tr align='center' valign='middle' ><!--�N�ק諸���@���[�J����Ӥw-->
			<td>${valuerecordVO.value_num}</td>
			<td>${valuerecordVO.mem_num}</td>	
			<td><fmt:formatDate value="${valuerecordVO.value_date}" pattern="yyyy/MM/dd HH:mm" /></td>
			<td>${valuerecordVO.value_cash}</td>	
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