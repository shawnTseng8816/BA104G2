<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%-- 此頁練習採用 EL 的寫法取值 --%>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">

<jsp:useBean id="couponSvc" scope="page" class="com.coupon.model.CouponService" />


<html>
<head>
<title>所有折價券已省和資料</title>



</head>


<body bgcolor='white'>


<jsp:include page="/back-end/back_top.jsp" /> <!-- navbar -->
	<!-- 1層大框框 -->
	<div class="container-fluid">
		<div class="row">
	<!-- 2層框左 -->	
			<jsp:include page="/back-end/back_left.jsp" /> <!-- leftSidebar -->
	<!-- 2層框右 -->		
			<div class="col-xs-12 col-sm-8 col-sm-offset-3" >				
				<div class="block-center panelheight">
				
	<!--========================== 功能放這邊 ===↓↓↓↓↓↓↓↓↓↓==========================================-->
	
	
	
	
<center>


<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>





<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="alert " style="background: #ddd;"> <h3>
         	  折價券申請資訊</h3></div>
            <table class="table">
                 <tbody>   
                    <tr align='center' valign='middle'>
                    <td>折價券編號</td>
						<td>折價金額</td>
						<td>剩餘張數</td>
						<td>申請張數</td>
						<td>申請時間</td>			
						<td>狀態</td> 
						<td>詳情</td>   
                    </tr>
              
                 <c:forEach varStatus="valuecount" var="couponVO" items="${couponSvc.finish}">
		<tr align='center' valign='middle'  <c:if test="${valuecount.count%2!=0}" > style="background: #ddd;"</c:if>><!--將修改的那一筆加入對比色而已-->
			<td>${couponVO.coupon_num}</td>
			<td>${couponVO.coupon_cash}</td>
			<td>${couponVO.left}</td>
			<td>${couponVO.total}</td>
			<td><fmt:formatDate value="${couponVO.apply_date}" pattern="yyyy/MM/dd HH:mm" /></td>
			<td>${couponVO.coupon_status}</td>
			<td>
 <!--   		折價卷燈箱 -->
			<p class="text-right"><a href='#${couponVO.coupon_num}' data-toggle="modal" class="btn btn-danger btn-xs btn3d addone">明細</a></p>
          	<div class="modal fade" id="${couponVO.coupon_num}">  
			<div class="modal-dialog" style="width: 640px;">
				<div class="modal-content">
					<div class="modal-header">
					<h3>折價券資訊</h3>					
					</div>				
					<div class="modal-body">						
						<div class="container body">					
		<div class="row">
				<div class="col-xs-12 col-sm-6 ">
					<table class="table table-hover table-striped ">
					<tr><img  class="img-thumbnail" width="200" src="<%=request.getContextPath()%>/GetPicEric?coupon_num=${couponVO.coupon_num}"></tr>					
						<tr><td colspan="2" style="background-color:#DDD;"><h3>活動資訊</h3></td></tr>
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
	                    
                </tbody>
            </table>
        </div>
    </div>
</div>



</center>

<!--========================== 功能放這邊 ====↑↑↑↑↑↑↑↑↑=====================================-->			
				</div><!-- class="block-center panelheight" -->			
			</div><!-- class="col-xs-12 col-sm-8 col-sm-offset-3" -->
		</div><!-- div class="row" -->
	</div><!-- div class="container-fluid" -->
	
	<jsp:include page="/back-end/back_foot.jsp" />
	
	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
</body>

</html>