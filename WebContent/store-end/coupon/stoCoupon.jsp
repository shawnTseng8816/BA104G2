<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.coupon.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%-- �����m�߱ĥ� EL ���g�k���� --%>
	


<%	

	CouponService couponSvc = new CouponService ();
		List list = new ArrayList();
				
		String sto_num = (String)session.getAttribute("sto_num");
		list = couponSvc.getOneStoCoupon(sto_num);
		request.setAttribute("couponVO", list);

%>

<html>
<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<title>�ӽЪ�������T</title>
<style>
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
	
	

<center>



<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h3><div class="alert alert-infoeric"> 
         	  �����ӽи�T</div></h3>
            <table class="table">
                 <tbody>   
                    <tr align='center' valign='middle'>
                    <td>�����s��</td>
						<td>������B</td>
						<td>�Ѿl�i��</td>
						<td>�ӽбi��</td>
						<td>�ӽФ��</td>			
						<td>���A</td> 
						<td>�Ա�</td>   
                    </tr>
                 
	<c:forEach varStatus="valuecount" var="couponVO" items="${couponVO}">
		<tr align='center' valign='middle'  <c:if test="${valuecount.count%2!=0}" > class="success"</c:if>><!--�N�ק諸���@���[�J����Ӥw-->
			<td>${couponVO.coupon_num}</td>
			<td>${couponVO.coupon_cash}</td>
			<td>${couponVO.left}</td>
			<td>${couponVO.total}</td>
			<td><fmt:formatDate value="${couponVO.apply_date}" pattern="yyyy/MM/dd"/></td>
			<td>${couponVO.coupon_status}</td>
			<td>
 <!--   		������O�c -->
			<p class="text-right"><a href='#${couponVO.coupon_num}' data-toggle="modal" class="btn btn-green">����</a></p>
          	<div class="modal fade" id="${couponVO.coupon_num}">  
			<div class="modal-dialog" style="width: 640px;">
				<div class="modal-content">
					<div class="modal-header">
					<h3 >������T</h3>					
					</div>				
					<div class="modal-body">						
						<div class="container body">					
		<div class="row">
				<div class="col-xs-12 col-sm-6 "style="margin-left: 15px;">
					<table class="table table-hover table-striped ">
					<tr><img  class="img-thumbnail" width="200" src="<%=request.getContextPath()%>/GetPicEric?coupon_num=${couponVO.coupon_num}"></tr>					
						<tr><td colspan="2" class="alert-info"><h3>���ʸ�T</h3></td></tr>
							<tr>
							<td style="width:140px" >���ʶ}�l�ɶ� :</td>
							<td><fmt:formatDate value="${couponVO.up_date}" pattern="yyyy/MM/dd HH:mm"/></td>
						</tr>
						<tr>
							<td>���ʵ����ɶ�: </td>
							<td><fmt:formatDate value="${couponVO.down_date}" pattern="yyyy/MM/dd HH:mm"/></td>
						</tr>
						<tr>
							<td>���ʱԭz :</td>
							<td>${couponVO.coupon_desc}</td>				
						</tr>
						<tr><td colspan="2"  style="background-color:#DDD;"><h3>�w�i��T</h3></td></tr>
						<tr>
							<td>�w�i�}�l�ɶ� :</td>
							<td><fmt:formatDate value="${couponVO.notice_up_date}" pattern="yyyy/MM/dd HH:mm" /></td>
						</tr>
						<tr>
							<td>�w�i�����ɶ�: </td>
							<td><fmt:formatDate value="${couponVO.notice_down_date}" pattern="yyyy/MM/dd HH:mm" /></td>
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
		
		
		</tr>
	</c:forEach>
	                 
	                  <c:if test="${couponVO.isEmpty()}">
       	<div class="text-center" style="color:red; font-size:48px;">�z�|���ӽЧ����!!</div>
       </c:if>   
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