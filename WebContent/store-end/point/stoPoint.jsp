<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.order_master.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%-- �����m�߱ĥ� EL ���g�k���� --%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">

<jsp:useBean id="StoProSvc" class="com.store_profile.model.StoreProfileService"/>



<html>
<head>
<title>���a�I�Ƹ�T</title>
<style type="text/css">
.alert-infoeric {
    color: #fcf8e3;
    background-color: #3c9682;
    
}
</style>
</head>
<body bgcolor='white'>

<jsp:include page="/store-end/store_top.jsp" /> <!-- navbar -->
	<!-- 1�h�j�خ� -->
	<div class="container-fluid">
		<div class="row">
	<!-- 2�h�إ� -->	
			<jsp:include page="/store-end/store_left.jsp" /> <!-- leftSidebar -->
	<!-- 2�h�إk -->		
			<div class="col-xs-12 col-sm-8 col-sm-offset-3" >				
				<div class="block-center panelheight">
				
	<!--========================== �\���o�� ������������===============================-->
<%	

	OrderMasterService orderMasterSvc= new OrderMasterService();
		List<OrderMasterVO> list = new ArrayList<OrderMasterVO>();
		String sto_num = (String)session.getAttribute("sto_num");
		list = orderMasterSvc.getStoPointOrder(sto_num);
		request.setAttribute("orderMasterSvc", list);

%>
<center>


<div class="container">
    <div class="row">
        <div class="col-md-12">
           <h3>  <div class="alert alert-infoeric">
         	      �Ѿl�I�� : ${StoProSvc.getOneStoInfo(sessionScope.sto_num).rem_point} </div></h3>
       
            <table class="table">
                 <tbody>   
                    <tr align='center' valign='middle'>
                    <td>�q��s��</td>
					<td>�|���s��</td>
					<td>�q�ʤ��</td>
					<td>��o�I��</td>    
                    </tr>
                 
	<c:forEach varStatus="valuecount" var="orderMasterVO" items="${orderMasterSvc}">
		<tr align='center' valign='middle'  <c:if test="${valuecount.count%2!=0}" > class="success"</c:if>><!--�N�ק諸���@���[�J����Ӥw-->
			<td>${orderMasterVO.or_num}</td>
			<td>${orderMasterVO.mem_num}</td>
			<td>${orderMasterVO.or_time}</td>
			<td>${orderMasterVO.or_amount}</td>		
		</tr>
	</c:forEach>
	
     

                    
                 
                            
                </tbody>
            </table>
        </div>
    </div>
</div>



</center>

<!--========================== �\���o�� ��������������=======================================-->			
				</div><!-- class="block-center panelheight" -->			
			</div><!-- class="col-xs-12 col-sm-8 col-sm-offset-3" -->
		</div><!-- div class="row" -->
	</div><!-- div class="container-fluid" -->
	
	<jsp:include page="/store-end/store_foot.jsp" />
</body>

<script src="https://code.jquery.com/jquery.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
</html>