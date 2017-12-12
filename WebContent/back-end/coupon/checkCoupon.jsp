<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%-- �����m�߱ĥ� EL ���g�k���� --%>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">

<jsp:useBean id="couponSvc" scope="page" class="com.coupon.model.CouponService" />


<html>
<head>
<title>�Ҧ��ݬٮָ�� </title>



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
            <div class="alert " style="background: #ddd;"> <h2>
         	  �ݼf�֧����</h2></div>
            <table class="table">
                 <tbody>   
                    <tr align='center' valign='middle'>
                    <th>�����s��</th>
					<th>������B</th>		
					<th>���a�s��</th>
					<th>�ӽбi��</th>
					<th>�ӽЪ��A</th>
					<th>�Ա�</th>
					<th>�q�L</th>
					<th>���q�L</th>
                    </tr>
              
                 <c:forEach varStatus="valuecount" var="couponVO" items="${couponSvc.apply}">
		<tr align='center' valign='middle'  <c:if test="${valuecount.count%2!=0}" > style="background: #ddd;"</c:if>><!--�N�ק諸���@���[�J����Ӥw-->
			<td>${couponVO.coupon_num}</td>
			<td>${couponVO.coupon_cash}</td>
			<td>${couponVO.sto_num}</td>
			<td>${couponVO.total}</td>
			<td>${couponVO.coupon_status}</td>		
			<td>
 <!--   		������O�c -->
			<p class="text-right"><a href='#${couponVO.coupon_num}' data-toggle="modal" class="btn btn-danger btn-xs btn3d addone">����</a></p>
          	<div class="modal fade" id="${couponVO.coupon_num}">  
			<div class="modal-dialog" style="width: 640px;">
				<div class="modal-content">
					<div class="modal-header">
					<h2>������T</h2>					
					</div>				
					<div class="modal-body">						
						<div class="container body">					
		<div class="row">
				<div class="col-xs-12 col-sm-6 ">
					<table class="table table-hover table-striped ">
					<tr><img  class="img-thumbnail" width="200" src="<%=request.getContextPath()%>/GetPicEric?coupon_num=${couponVO.coupon_num}"></tr>					
						<tr><td colspan="2"  style="background-color:#DDD;"><h3>���ʸ�T</h3></td></tr>
						<tr>
							<td style="width:140px" >���ʶ}�l��� :</td>
							<td><fmt:formatDate value="${couponVO.up_date}" pattern="yyyy/MM/dd HH:mm"/></td>
						</tr>
						<tr>
							<td>���ʵ������: </td>
							
							<td><fmt:formatDate value="${couponVO.down_date}" pattern="yyyy/MM/dd HH:mm "/></td>
						</tr>
						<tr>
							<td>���ʱԭz :</td>
							<td>${couponVO.coupon_desc}</td>				
						</tr>
						<tr><td colspan="2"  style="background-color:#DDD;" ><h3>�w�i��T</h3></td></tr>
						<tr>
							<td>�w�i�}�l��� :</td>
							<td><fmt:formatDate value="${couponVO.notice_up_date}" pattern="yyyy/MM/dd HH:mm"/></td>
						</tr>
						<tr>
							<td>�w�i�������: </td>
							<td><fmt:formatDate value="${couponVO.notice_down_date}" pattern="yyyy/MM/dd HH:mm"/></td>							
						</tr>
						<tr>
							<td>�w�i�ԭz :</td>
							<td>${couponVO.notice_desc}</td>				
						</tr>
					
					</table>		
				</div>
		</div>
	</div>	
	
	
					</div>
					
					
					<div class="modal-footer">			
					
						<button type="button" class="btn btn-default" data-dismiss="modal">����</button>
					</div>
				</div>
			</div>
		</div>
		
		
		</td>
		<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/CouponServlet" style="margin-bottom: 0px;">
			     <input type="submit" value="�q�L"> 
			      <input type="hidden" name="total"      value="${couponVO.total}">
			     <input type="hidden" name="coupon_num"  value="${couponVO.coupon_num}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->            
			     <input type="hidden" name="action"	    value="couponPass"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/CouponServlet" style="margin-bottom: 0px;">
			     <input type="submit" value="�h�^"> 
			     <input type="hidden" name="coupon_num"  value="${couponVO.coupon_num}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->            
			     <input type="hidden" name="action"	    value="couponNoPass"></FORM>
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