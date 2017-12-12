<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="StoProSvc" class="com.store_profile.model.StoreProfileService"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<jsp:useBean id="remSvc" scope="page" class="com.rem_record.model.RemRecordService" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="<%=request.getContextPath()%>/js/dist/sweetalert2.all.min.js"></script>
<script src="<%=request.getContextPath()%>/js/dist/sweetalert2.min.js"></script>
<title>店家 提款申請 </title>
<style type="text/css">
.alert-infoeric {
    color: #fcf8e3;
    background-color: #3c9682;
    
}
</style>
</head>
<body>

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


<c:if test="${not empty errorMsgs}">
	<font style="color:red">訊息提示:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
 <div class="alert alert-infoeric"> <h3>
 店家提款申請<br><br>
         	     目前餘額: ${StoProSvc.getOneStoInfo(sessionScope.sto_num).rem_point} </div>
<FORM  method=post  enctype="text/html"><br>
<table border='1' bordercolor='#CCCCFF'>
<tr>
<th> 請輸入要匯入的帳號 :</th> 
<td><input type="text"  id="rem_acc" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" name="rem_acc" ></td>
<tr>
<th> 請輸入要匯出的金額 :</th>
<td><input type="text" id="rem_cash"  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  name="rem_cash"></td>
</tr>
</table>
<br>
<input  style="width:30px;height:20px;" onClick="inPutInfo()" type="button"  />
<input type="hidden"  id="action" name="action" value="remPoint">
<input type="button" id="doSubmit"  class="btn-green btn btn-lg" value="送出" >

</FORM>




<div class="container">
    <div class="row">
        <div class="col-md-12">
             <h3><div class="alert " style="background: #ddd;">
         				提款申請紀錄 </div></h3>
            <table class="table">
                 <tbody>   
                    <tr align='center' valign='middle'>
                   <th>匯款編號</th>
		
		<th>匯入帳號</th>
		<th>匯款金額</th>
		<th>申請時間</th>
		<th>申請狀態</th>	
		<th>匯款時間</th>
                    </tr>
              <c:forEach var="remrecordVO" items="${remSvc.getOneStoRemInfo(sessionScope.sto_num)}">
		<tr align='center' valign='middle' ><!--將修改的那一筆加入對比色而已-->
			<td>${remrecordVO.rem_num}</td>
	
			<td>${remrecordVO.rem_account}</td>
			<td>${remrecordVO.rem_cash}</td>
			<td><fmt:formatDate value="${remrecordVO.apply_date}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
			<td>${remrecordVO.rem_status}</td>
			<td><fmt:formatDate value="${remrecordVO.rem_date}" pattern="yyyy/MM/dd HH:mm:ss"/></td>		
		</tr>
	</c:forEach>
      <c:if test="${remSvc.getOneStoRemInfo(sessionScope.sto_num).isEmpty()}">
       	<div class="text-center" style="color:red; font-size:48px;">暫無提款申請!!</div>
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
	
<script>

function inPutInfo(){


	document.getElementsByName("rem_acc")[0].value= '129541175412';
	
}





function goRem(){
	
	var rem_acc = $('#rem_acc').val();
	var rem_cash = $('#rem_cash').val();
	var action =  $('#action').val();
	$.ajax({
		 type: "POST",
		 url: "<%=request.getContextPath()%>/RemRecordServlet",
		 data: {"rem_acc": rem_acc, "rem_cash":rem_cash, "action":action},
		 dataType: "json",
		 async: false, // true預設是非同步，也就是"不會等待"; false是同步，"會等待"
		 success: function(data){
			  console.log(data.message);
			  if(data.message != null){
				  swal({
					  position: 'center',
					  type: 'success',
					  title: data.message,
					  showConfirmButton: true,
					  timer: 2000
					})
				setTimeout('startRequest()',1500);
				}	
			  if(data.errormessage != null){
				  swal({
					  position: 'center',
					  type: 'error',
					  title: data.errormessage,
					  showConfirmButton: true,
					  timer: 2000
					})
				}		
			},
		error: function(){alert("輸入有誤 !")}
    })
}


$(function(){
	$('#doSubmit').on('click', function(){
		var rem_acc = $('#rem_acc').val();
		var rem_cash = $('#rem_cash').val();
		swal({
			  title: '確認您的匯出帳號是否正確',
			  html: '<table ><tr><th style=" padding-left: 142px; border-left-width: 50px;">'+
				'帳號 :</th><td>'+rem_acc+ '</td></tr><tr><th style="padding-left: 105px;">匯款金額 :</th><td>'+rem_cash+'</td></tr></table>', 
			  type: 'warning',
			  allowOutsideClick: false,
			  showCancelButton: true,
			  confirmButtonColor: '#3c9682',
			  cancelButtonColor: '#FA5532',
			  confirmButtonText: '確認無誤'
			}).then((result) => {	 
				goRem();		  
			})

	});
});

function startRequest()
{
	

	location.reload(); 
}


</script>
</html>