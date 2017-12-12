<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.coupon.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%-- 此頁練習採用 EL 的寫法取值 --%>
	


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
<title>申請的折價券資訊</title>
<style>
.alert-infoeric {
    color: #fcf8e3;
    background-color: #3c9682;
    
}
</style>

</head>
<body bgcolor='white'>

<jsp:include page="/store-end/store_top.jsp" /> <!-- navbar -->
	<!-- 1層大框框 -->
	<div class="container-fluid">
		<div class="row">
	<!-- 2層框左 -->	
			<jsp:include page="/store-end/store_left.jsp" /> <!-- leftSidebar -->
	<!-- 2層框右 -->		
			<div class="col-xs-12 col-sm-8 col-sm-offset-3" >				
				<div class="block-center panelheight">
				
	<!--========================== 功能放這邊 ↓↓↓↓↓↓===============================-->
	
	

<center>



<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h3><div class="alert alert-infoeric"> 
         	  折價券申請資訊</div></h3>
            <table class="table">
                 <tbody>   
                    <tr align='center' valign='middle'>
                    <td>折價券編號</td>
						<td>折價金額</td>
						<td>剩餘張數</td>
						<td>申請張數</td>
						<td>申請日期</td>			
						<td>狀態</td> 
						<td>詳情</td>   
                    </tr>
                 
	<c:forEach varStatus="valuecount" var="couponVO" items="${couponVO}">
		<tr align='center' valign='middle'  <c:if test="${valuecount.count%2!=0}" > class="success"</c:if>><!--將修改的那一筆加入對比色而已-->
			<td>${couponVO.coupon_num}</td>
			<td>${couponVO.coupon_cash}</td>
			<td>${couponVO.left}</td>
			<td>${couponVO.total}</td>
			<td><fmt:formatDate value="${couponVO.apply_date}" pattern="yyyy/MM/dd"/></td>
			<td>${couponVO.coupon_status}</td>
			<td>
 <!--   		折價卷燈箱 -->
			<p class="text-right"><a href='#${couponVO.coupon_num}' data-toggle="modal" class="btn btn-green">明細</a></p>
          	<div class="modal fade" id="${couponVO.coupon_num}">  
			<div class="modal-dialog" style="width: 640px;">
				<div class="modal-content">
					<div class="modal-header">
					<h3 >折價券資訊</h3>					
					</div>				
					<div class="modal-body">						
						<div class="container body">					
		<div class="row">
				<div class="col-xs-12 col-sm-6 "style="margin-left: 15px;">
					<table class="table table-hover table-striped ">
					<tr><img  class="img-thumbnail" width="200" src="<%=request.getContextPath()%>/GetPicEric?coupon_num=${couponVO.coupon_num}"></tr>					
						<tr><td colspan="2" class="alert-info"><h3>活動資訊</h3></td></tr>
							<tr>
							<td style="width:140px" >活動開始時間 :</td>
							<td><fmt:formatDate value="${couponVO.up_date}" pattern="yyyy/MM/dd HH:mm"/></td>
						</tr>
						<tr>
							<td>活動結束時間: </td>
							<td><fmt:formatDate value="${couponVO.down_date}" pattern="yyyy/MM/dd HH:mm"/></td>
						</tr>
						<tr>
							<td>活動敘述 :</td>
							<td>${couponVO.coupon_desc}</td>				
						</tr>
						<tr><td colspan="2"  style="background-color:#DDD;"><h3>預告資訊</h3></td></tr>
						<tr>
							<td>預告開始時間 :</td>
							<td><fmt:formatDate value="${couponVO.notice_up_date}" pattern="yyyy/MM/dd HH:mm" /></td>
						</tr>
						<tr>
							<td>預告結束時間: </td>
							<td><fmt:formatDate value="${couponVO.notice_down_date}" pattern="yyyy/MM/dd HH:mm" /></td>
						</tr>
						<tr>
							<td>預告敘述 :</td>
							<td>${couponVO.notice_desc}</td>				
						</tr>
					
					</table>		
				</div>
		</div>
	</div>	
	
	
					</div>
					
					
					<div class="modal-footer">			
					
						<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
					</div>
				</div>
			</div>
		</div>
		
		
		</td>
		
		
		</tr>
	</c:forEach>
	                 
	                  <c:if test="${couponVO.isEmpty()}">
       	<div class="text-center" style="color:red; font-size:48px;">您尚未申請折價券!!</div>
       </c:if>   
                </tbody>
            </table>
        </div>
    </div>
</div>





</center>


	<!--========================== 功能放這邊 ↑↑↑↑↑↑↑=======================================-->			
				</div><!-- class="block-center panelheight" -->			
			</div><!-- class="col-xs-12 col-sm-8 col-sm-offset-3" -->
		</div><!-- div class="row" -->
	</div><!-- div class="container-fluid" -->
	
	<jsp:include page="/store-end/store_foot.jsp" />

</body>

<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
</html>