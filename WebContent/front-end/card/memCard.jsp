<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.card_list.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%-- 此頁練習採用 EL 的寫法取值 --%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">

<jsp:useBean id="cardSvc" scope="page" class="com.card.model.CardService" />
<jsp:useBean id="stoSvc" scope="page" class="com.store_profile.model.StoreProfileService" />

<html>
<head>
<title>集點卡資訊</title>

<style>
.alert-infoeric {
    color: #fcf8e3;
    background-color: #3c9682;
    
}
</style>


</head>


<jsp:include page="/front-end/member_top.jsp" />
<jsp:include page="/front-end/coupon_notify.jsp" />
<body bgcolor='white'>

<%	

		CardListService cardlistSvc = new CardListService();
		List list = new ArrayList();
		String mem_num = (String)session.getAttribute("mem_num");
		list = cardlistSvc.getCardListByMem_num(mem_num);
		request.setAttribute("cardlistVO", list);

%>

<center>


<div class="container">
    <div class="row">
        <div class="col-md-12">
           <h3><div class="alert alert-infoeric">  
         	       會員集點卡資訊 </div></h3>
            <div class="alert alert-success" style="display:none;">
             </div>
            <table class="table">

                    
                <tbody>   
                 <tr align='center' valign='middle' >
                 	<td>編號</td>
                 	<td>店家名稱</td>
					<td>需集點數</td>
					<td>完成進度</td>
					<td>可折扣金額</td>
					<td>狀態</td>
					<td>有效期限</td>
                    </tr>
       
     
	<c:forEach  var="cardlistVO" items="${cardlistVO}" varStatus="valuecount" >
		<tr align='center' valign='middle' <c:if test="${valuecount.count%2!=0}" > class="success"</c:if>  ><!--將修改的那一筆加入對比色而已-->
			<td>${cardlistVO.card_num}</td>
			<td>${stoSvc.getOneStoInfo(cardSvc.getOneCradInfo(cardlistVO.card_kinds).sto_num).sto_name}</td>
			<td>${cardSvc.getOneCradInfo(cardlistVO.card_kinds).points}</td>
			<td><div class="progress-bar progress-bar-striped" role="progressbar" style="width: ${100/cardSvc.getOneCradInfo(cardlistVO.card_kinds).points * cardlistVO.value}%" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100">${100/cardSvc.getOneCradInfo(cardlistVO.card_kinds).points * cardlistVO.value}%</div>
			</td>
			<td>${cardSvc.getOneCradInfo(cardlistVO.card_kinds).points_cash}</td>
			<td>${cardlistVO.status}</td>
			<td><fmt:formatDate value="${cardlistVO.exp_date}" pattern="yyyy/MM/dd"/></td>
		</tr>
	</c:forEach>
	                    <c:if test="${cardlistVO.isEmpty()}">
	   	<div class="text-center" style="color:red; font-size:100px;">暫無資料!!</div>
	   </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>



</center>

<jsp:include page="/front-end/member_foot.jsp" />
</body>
</html>