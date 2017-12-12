<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%-- 此頁練習採用 EL 的寫法取值 --%>


<jsp:useBean id="remSvc" scope="page" class="com.rem_record.model.RemRecordService" />


<html>
<head>
<title>已省核資料匯款紀錄 </title>


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
             <h3><div class="alert " style="background: #ddd;">
         				  已省核資料匯款紀錄 </div></h3>
            <table class="table">
                 <tbody>   
                    <tr align='center' valign='middle'>
                   <th>匯款編號</th>
		<th>店家編號</th>
		<th>匯入帳號</th>
		<th>匯款金額</th>
		<th>申請時間</th>
		<th>申請狀態</th>	
		<th>匯款時間</th>
                    </tr>
              <c:forEach var="remrecordVO" items="${remSvc.finish}">
		<tr align='center' valign='middle' ><!--將修改的那一筆加入對比色而已-->
			<td>${remrecordVO.rem_num}</td>
			<td>${remrecordVO.sto_num}</td>
			<td>${remrecordVO.rem_account}</td>
			<td>${remrecordVO.rem_cash}</td>
			<td><fmt:formatDate value="${remrecordVO.apply_date}" pattern="yyyy/MM/dd HH:mm" /></td>
			<td>${remrecordVO.rem_status}</td>
			<td><fmt:formatDate value="${remrecordVO.rem_date}" pattern="yyyy/MM/dd HH:mm" /></td>		
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