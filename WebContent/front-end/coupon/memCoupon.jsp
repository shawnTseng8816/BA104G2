<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.coupon_list.model.*"%>
<%@ page import="com.coupon.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%-- 此頁練習採用 EL 的寫法取值 --%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">

<jsp:useBean id="couponSvc" scope="page" class="com.coupon.model.CouponService" />
<jsp:useBean id="StoProSvc" scope="page" class="com.store_profile.model.StoreProfileService" />
<%	

	CouponListService couponListSvc = new CouponListService ();
		List list = new ArrayList();
		


		String mem_num = (String)session.getAttribute("mem_num");
		list = couponListSvc.getMycoupon(mem_num);
		request.setAttribute("couponListVO", list);

%>



<html>
<head>
<title>我的折價券資訊</title>
<style>
.alert-infoeric {
    color: #fcf8e3;
    background-color: #3c9682;
    
}
</style>
</head>
<jsp:include page="/front-end/member_top.jsp" flush="true" />
<jsp:include page="/front-end/coupon_notify.jsp" />

<body bgcolor='white'>


<center>

<div class="container">
    <div class="row">
        <div class="col-md-12">
             <h3><div class="alert alert-infoeric">
         	   我的折價券資訊 </div></h3>
            <div class="alert alert-success" style="display:none;">
                <span class="glyphicon glyphicon-ok"></span> Drag table row and cange Order</div>
            <table class="table">
                 <tbody>   
                    <tr align='center' valign='middle'>
                    <td>店家名稱</td>
					<td>取得時間</td>
					<td>折價金額</td>
					<td>使用時間</td>
					<td>到期日期</td>	
                    </tr>
                 
     <%@ include file="pages/page1.file" %> 
	<c:forEach  var="couponList" items="${couponListVO}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" varStatus="valuecount"  >
		<tr align='center' valign='middle' <c:if test="${valuecount.count%2!=0}" > class="success"</c:if> > <!--將修改的那一筆加入對比色而已-->
			<td>${StoProSvc.getOneStoInfo(couponSvc.getOneCoupon(couponList.coupon_num).sto_num).sto_name}</td>
		<td><fmt:formatDate value="${couponList.get_date}" pattern="yyyy/MM/dd HH:mm" /></td>	
		<td>${couponSvc.getOneCoupon(couponList.coupon_num).coupon_cash}</td>
		<td>
		<c:if test="${couponList.used_date==null}" > 尚未使用 </c:if>
		<c:if test="${couponList.used_date!=null}" > <fmt:formatDate  value="${couponList.used_date}" pattern="yyyy/MM/dd HH:mm:ss"/>  </c:if></td>
		<td><fmt:formatDate value="${couponSvc.getOneCoupon(couponList.coupon_num).exp_date}" pattern="yyyy/MM/dd" /></td>		
		</tr>
	</c:forEach>
        <c:if test="${couponListVO.isEmpty()}">
        
        	<div class="text-center" style="color:red; font-size:100px;">暫無資料!!</div>
        
        </c:if>
           
                            
                </tbody>
               
            </table>
        </div>
    </div>
</div>
      <%@ include file="pages/page2.file" %> 



</center>

<jsp:include page="/front-end/member_foot.jsp" flush="true" />
</body>
</html>