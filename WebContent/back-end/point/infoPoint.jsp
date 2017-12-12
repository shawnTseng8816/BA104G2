<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%-- 此頁練習採用 EL 的寫法取值 --%>


<jsp:useBean id="valueSvc" scope="page" class="com.value_record.model.ValueRecordService" />


<html>
<head>
<title>儲值歷史紀錄  </title>


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
         				   儲值歷史紀錄  </div></h3>
            <table class="table">
                 <tbody>   
                    <tr align='center' valign='middle'>
                    <th>儲值編號</th>
					<th>會員編號</th>
					<th>儲值日期</th>
					<th>儲值金額</th>
                    </tr>
               <c:forEach var="valuerecordVO" items="${valueSvc.all}">
			<tr align='center' valign='middle' ><!--將修改的那一筆加入對比色而已-->
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