<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.card.model.*"%>
<%@ page import="com.store_profile.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean  id="stoImgSvc" class="com.store_image.model.StoreImageService"/>


<%
	
	String sto_num = (String)session.getAttribute("sto_num");
	StoreProfileService storeProfileSvc = new StoreProfileService();
	StoreProfileVO storeProfileVO = new StoreProfileVO();		
	storeProfileVO = storeProfileSvc.getOneStoInfo(sto_num);
	request.setAttribute("storeProfileVO", storeProfileVO);
	

%>

<%	
		
		CardService cardSvc = new CardService();
		List list = new ArrayList();
		list = cardSvc.getCardsBySto_num(sto_num);
		request.setAttribute("cardVO2", list);
		CardVO stonowcard  = new CardVO();
		stonowcard = cardSvc.getStoNowCrad(sto_num);
		request.setAttribute("stonowcard", stonowcard);
%>


<!DOCTYPE html>
<html lang="">
	<head>
	
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>Title Page</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
		<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
<script src="<%=request.getContextPath()%>/js/dist/sweetalert2.all.min.js"></script>
<script src="<%=request.getContextPath()%>/js/dist/sweetalert2.min.js"></script>
<style type="text/css">

textarea{/* Text Area 固定大小*/
 max-width:360px;
 max-height:250px;
 min-width:360px;
 min-height:250px;

}

th {
    text-align: center;
}

.btn-greenc {
    background: #3C9682;
    color: #FFFFFF;
}

.previousImg{

	width:100%;
}

.input-group{
	margin-top: 20px;
}

.stoordere {
background-color: #3c9682;
color:white;
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
	
	<div class="container">
    <div class="row">
		<div class="col-md-12">
			<h3>店家資訊修改　</h3>
			<div class="tabbable-panel">
				<div class="tabbable-line">
					<ul class="nav nav-tabs ">
						<li ><a class="stoordere" href="#tab_default_1" data-toggle="tab">店家資訊修改 </a></li>
						<li><a class="stoordere" href="#tab_default_2" data-toggle="tab">店家狀態修改 </a></li>
						<li><a class="stoordere" href="#tab_default_3" data-toggle="tab">新增集點卡</a></li>
						<li><a class="stoordere" href="#tab_default_4" data-toggle="tab">集點卡上架設定</a></li>
						<li><a class="stoordere" href="#tab_default_5" data-toggle="tab">店家圖片上傳</a></li>
					</ul>

			<!-- 	// TAB1 內容 ********店家資訊修改******************************** -->
				<div class="tab-content">
				<div class="tab-pane <% if("tab1".equals(request.getParameter("tab")) || request.getParameter("tab")==null){out.print("active");}%>" id="tab_default_1">					
				<FORM action="<%=request.getContextPath()%>/StoreProfileServletEric" method=post enctype="multipart/form-data"  >
			
								
														
				<div style="backgroud-color:#ffffff;  margin-top:30px;">
				<div class="col-xs-12 col-sm-3 ">
				<img width="300"   class="img-thumbnail" height="200" id="output1" src="<%=request.getContextPath()%>/GetPicEric?sto_num=${storeProfileVO.sto_num}">
				<input type="file" id="output" name="upfile"   onchange="onFileSelected(event,this.id)"></p>										
				<h3><p>店家簡介：</p></h3>
				<p><textarea  class="form-control" name="sto_introduce">${storeProfileVO.sto_introduce}</textarea></p>			
				</div>
				<div class="col-xs-12 col-sm-6 col-sm-offset-1" ><font size="4">	
				
						<div class="form-horizontal">	
							<div class="form-group">
								<label class="col-sm-3 control-label">店家編號  :</label>
								<div class="col-sm-9">
									<p  style="margin-top:8px;">${storeProfileVO.sto_num}</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">店家名稱  :</label>
								<div class="col-sm-9">
									<p style="margin-top:8px;">${storeProfileVO.sto_name}</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">負責人  :<span style="color:red">*</span></label>
								<div class="col-sm-9">
								<input type="text" name="guy" id="aa" placeholder="" value="${storeProfileVO.guy}"class="form-control">
							
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">統一編號  :</label>
								<div class="col-sm-9">
									<p style="margin-top:8px;">${storeProfileVO.uni_num}</p>
								</div>
							</div>
							<div class="form-group">
								<label for="aa" class="col-sm-3 control-label">電話  :<span style="color:red">*</span></label>

								<div class="col-sm-9">
									<input type="text" name="mobile" id="aa" placeholder="" value="${storeProfileVO.mobile}"class="form-control">
								
								</div>
							</div>
					<div class="form-group">
								<label for="aa" class="col-sm-3 control-label">區域  :</label>

								<div class="col-sm-9">
								<p  style="margin-top:8px;">${storeProfileVO.area}</p>
<%-- 									<input type="text" name="area"  value="${storeProfileVO.area}" placeholder="文字" class="form-control"> --%>
								</div>
							</div>


							<div class="form-group">
								<label for="aa" class="col-sm-3 control-label">地址  :</label>
								<div class="col-sm-9">
								<p  style="margin-top:8px;">${storeProfileVO.address}</p>
<%-- 									<input type="text" name="address" value="${storeProfileVO.address}" placeholder="文字" class="form-control"> --%>
																</div>
							</div>
							
							<div class="form-group">
								<label for="aa" class="col-sm-3 control-label">密碼 :<span style="color:red">*</span></label>
								<div class="col-sm-9">
								<input  class="form-control" name="sto_pwd" type="password" value="${storeProfileVO.sto_pwd}">									
								</div>
							</div>						
							<div class="form-group">
								<label for="aa" class="col-sm-3 control-label">密碼確認  :<span style="color:red">*</span></label>
								<div class="col-sm-9">
								<input  class="form-control" name="sto_pwd2" type="password" value="${storeProfileVO.sto_pwd}">
							</div>
							</div>
													
							<div class="form-group">
								<label for="aa" class="col-sm-3 control-label"></label>
								<div class="col-sm-9">
								
								
									<input name="action" type="hidden" value="updateStoPro"> 
									<input style="margin-top:40px" type="submit" value="確認送出" class="btn btn-green btn-lg" >
								</div>
							</div>
						</div>			
		</font>
				</div>
			</div>

</FORM>
		
						</div>
<!-- 	// TAB1 結束 **************************************** -->


		<!-- 	// TAB2 內容 ***** 店家上架狀態*********************************** -->
			<div class="tab-pane " id="tab_default_2">
				<div class="panel ">
		 <div class="panel-heading stoordere">
			<p><h3><div class="text-center" >目前店家上架狀態為 : ${storeProfileVO.sto_status}</div></h3></p>			

				</div>	
				<p>
							<c:if test="${storeProfileVO.sto_status=='未上架'}">													
								<input name="action" type="hidden" value="upStore"> 						
								<input style="margin-top:40px; margin-left:500px;" type="button" value="上架" class=" storeStatus btn  btn-lg btn-green" >
							</c:if>
							<c:if test="${storeProfileVO.sto_status=='已上架'}">							
								<input name="action" type="hidden" value="downStore"> 
								<input style="margin-top:40px; margin-left:500px;" type="button" value="下架" class="storeStatus btn btn-lg btn-org" >
							</c:if>
							</p>
				</div>	
			
			</div>
	<!-- 	// TAB2 結束 **************************************** -->


	<!-- 	// TAB３　開始******************新增集點卡********************** -->
						<div class="tab-pane" id="tab_default_3">
								<div class="panel ">
			

					
		<FORM action="" method=post enctype="text/html" >
		<div class="col-xs-12 col-sm-3">
				<div class="input-group">				
  					<span class="input-group-addon" id="info-addon1">需收集</span>
 					 <input onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  maxlength="2" type="text" class="form-control" name="points" id="points" aria-describedby="basic-addon1">
 					  <span class="input-group-addon">點</span>
					</div>				
					<div class="input-group">
  					<span class="input-group-addon" id="basic-addon1">可折價金額</span>
 					 <input onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"   maxlength="2" type="text" class="form-control" type="text" class="form-control" id="points_cash" name="points_cash" aria-describedby="basic-addon1">
 					  <span class="input-group-addon">元</span>
					</div>
					<div class="input-group">
  					<span class="input-group-addon" id="basic-addon1">集點卡使用期限</span>
 					<input onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  maxlength="2"  type="text" class="form-control"  type="text" class="form-control" id="exp_date" name="exp_date" aria-describedby="basic-addon1">
 					<span class="input-group-addon">個月</span>
					</div>
					</div>
					<div class="col-xs-12 col-sm-9">
			
					<h3><p>使用規則：</p></h3>
						<p><textarea  class="form-control" name="card_des" id="card_des" name="comments" placeholder="Comment" >

</textarea></p>
					</div>
					<div>
					<input type="hidden" id="action" name="action" value="newCard">
					<input style="margin-top:20px; margin-left:360px;" id="doSubmit" type="button" value="送出" class="btn btn-green btn-lg" >
					<button style="width:30px;height:20px;" onClick="inPutInfo()" type="button"  />
					</div>

						<!--========================== 功能放這邊 =================================================================================================-->
			</FORM>	

			</div>
		
		
			</div>
<!-- 	// TAB３　結束**************************************** -->



<!-- 	// TAB4　開始*******************設定上架集點卡********************* -->
						<div class="tab-pane " id="tab_default_4">
					<div class="panel ">
					  <div class="panel-heading stoordere">
					 	<b><h3><div style="color:white"  class="text-center">目前上架的集點卡編號為 : <c:out value="${stonowcard.card_kinds}" default=" 目前無上架集點卡  !"/></div></h3></b>
					  </div>
					  <!-- <div class="panel-body"> -->
					    <table class="table table-hover">
					    	<!-- <caption>我是表格標題</caption> -->
					    	<thead>
						<tr align='center'  valign='middle'>
							<th>集點卡編號</th>
							<th>需集滿</th>
							<th>可折扣金額</th>
							<th>折價券敘述</th>
							<th>有效期限(月)</th>
							<th>操作</th>
						</tr>
	    	</thead>
	    	<tbody>
	<c:forEach var="cardVO" items="${cardVO2}">

		<tr align='center' valign='middle' ><!--將修改的那一筆加入對比色而已-->
			<td>${cardVO.card_kinds}</td>
			<td>${cardVO.points}</td>
			<td>${cardVO.points_cash}</td>
			<td>${cardVO.card_des}</td>
			<td>${cardVO.exp_date}</td>
			
			<c:if test="${cardVO.status.equals('下架')}">
			<td>
			  <FORM METHOD="post" ACTION="" style="margin-bottom: 0px;">			
			     <input type="button"  class="doCard btn btn-greenc" value="上架"> 
			     <input type="hidden" name="card_kinds"      value="${cardVO.card_kinds}">	
			      <input type="hidden" name="action"	    value="upCard">	     
			     <input type="hidden" name="requestURL"	value="<%=request.getRequestURL()%>"><!--送出本網頁的路徑給Controller-->            
			    </FORM>
			</td>
			</c:if>
			<c:if test="${cardVO.status.equals('上架')}">
			<td>
			 <FORM METHOD="post" ACTION="" style="margin-bottom: 0px;">
			     <input type="button"  class="doCard btn btn-org"  value="下架"> 
			     <input type="hidden" name="card_kinds"      value="${cardVO.card_kinds}">	
			     <input type="hidden" name="action"	    value="downCard">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->		             
			     </FORM>
			</td>
			</c:if>	
		</tr>
	</c:forEach>
		</tbody>
		
	
</table>

						</div>


					</div>
<!-- 	// TAB4　結束**************************************** -->

	
					<div class="tab-pane ${param.tab=='tab5'?'active':'' }" id="tab_default_5">
				
					<div class="panel ">
					  <div class="panel-heading stoordere">
					 	<b><h3><div style="color:white ;" class="text-center">店家圖片上傳預覽</div></h3></b>
					  </div>
					  <!-- <div class="panel-body"> -->
					    <table class="table table-hover">
					    
					    
					    <br>
					    <div class="col-xs-12 col-sm-12 col-sm-offset-0">
				<div id="carousel-id2" class="carousel slide" data-ride="carousel">
				    <!-- 幻燈片小圓點區 -->
				    <ol class="carousel-indicators">
				        <li data-target="#carousel-id2" data-slide-to="0" class="active"></li>
				        <li data-target="#carousel-id2" data-slide-to="1" class=""></li>
				        <li data-target="#carousel-id2" data-slide-to="2" class=""></li>
				    </ol>
				    <!-- 幻燈片主圖區 -->
				    <div class="carousel-inner ">
				    <c:forEach varStatus="imgnum" var="stoImg" items="${stoImgSvc.getStoreImageNum(sto_num)}">
				        <div class="item ${imgnum.first?'active':''} ">
				        		<img  class="adimg" id="output${imgnum.count}1"  src="<%=request.getContextPath()%>/GetPicEric?img_num=${stoImg.img_num}" >
					    </div>
				 	</c:forEach>
				        
				    <c:if test="${stoImgSvc.getStoreImageNum(sto_num).size()!=3}">
						<c:forEach begin="<%=0 %>" end="${3-stoImgSvc.getStoreImageNum(sto_num).size()-1 }" varStatus="imgnum">
							<div class="item ${3-imgnum.index==1?'active':''} ">
				        		<img  class="adimg" id="output${3-imgnum.index}1" src="<%= request.getContextPath()%>/img/back.png"  >
					    	</div>
						</c:forEach>
					</c:if>	 	
	
				    </div>
				    <!-- 上下頁控制區 -->
					    <a class="left carousel-control" href="#carousel-id2" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a>
					    <a class="right carousel-control" href="#carousel-id2" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
				</div>
			</div>
		
					    
				 
		<div class="col-xs-12 col-sm-12 col-sm-offset-0">			    
					    
	<FORM action="<%=request.getContextPath()%>/StoreProfileServletEric" method=post enctype="multipart/form-data"  >
		
	<tr>
	<td>
	<input type="submit"  class="btn btn-green" value="上傳">
	</td>
	</tr>
	<c:forEach varStatus="imgnum" var="stoImg" items="${stoImgSvc.getStoreImageNum(sto_num)}">
		<tr>
		<td>
		<input type="file" id="output${imgnum.count}" name="upfile${imgnum.count}"onchange="onFileSelected(event,this.id)"> 
		<img    class="adimg previousImg" id="output${imgnum.count}2"  src="<%=request.getContextPath()%>/GetPicEric?img_num=${stoImg.img_num}" >		
		<input type="hidden" name="img_num" value="${stoImg.img_num}">	
		</td>
		</tr>		        
	</c:forEach>
	
	<c:if test="${stoImgSvc.getStoreImageNum(sto_num).size()!=3}">
	<c:forEach begin="<%=0 %>" end="${3-stoImgSvc.getStoreImageNum(sto_num).size()-1 }" varStatus="imgnum">
		<tr>
		<td>
		<input  type="file" id="output${3-imgnum.index}" name="upfile${3-imgnum.index}"onchange="onFileSelected(event,this.id)"> 
		<img   class="adimg previousImg" id="output${3-imgnum.index}2"   >	
		<input type="hidden" name="img_num" >	
		</td>
		</tr>
	</c:forEach>
	</c:if>	 		
	
		 <input type="hidden" value="insertImg" name="action">
		 
	</FORM>
	</div>
	
		</table>

						</div>
							
					</div>
					
			<!-- 	// TAB5　結束**************************************** -->		
					
					
					
				</div>
			</div>

		
		</div>
	</div>
</div>


<!-- 	舊式 -->
	
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

	
	document.getElementsByName("card_des")[0].value= '需集滿5點方可折價。 \r\n集點卡有效期限為第一次取得後6個月。 \r\n集點卡僅能使用一次且不找零，一經使用無法恢復。';
	document.getElementsByName("points")[0].value= '5';
	document.getElementsByName("points_cash")[0].value= '25';
	document.getElementsByName("exp_date")[0].value= '6';
	
}

$(document).on('click', '.number-spinner button', function () {    
	var btn = $(this),
		oldValue = btn.closest('.number-spinner').find('input').val().trim(),
		newVal = 0;
	
	if (btn.attr('data-dir') == 'up') {
		newVal = parseInt(oldValue) + 1;
	} else {
		if (oldValue > 1) {
			newVal = parseInt(oldValue) - 1;
		} else {
			newVal = 1;
		}
	}
	btn.closest('.number-spinner').find('input').val(newVal);
});


//***********店家上下架*************
$(function(){
	$('.storeStatus').on('click', function(){
		var action = $(this).prev().val();
		var sto_num =  $(this).prev().prev().val();

		$.ajax({
			 type: "POST",
			 url: "<%=request.getContextPath()%>/StoreProfileServletEric",
			 data: {"sto_num": sto_num, "action":action},
			 dataType: "json",
			 async: false, // true預設是非同步，也就是"不會等待"; false是同步，"會等待"
			 success: function(data){
				  if(data.message != null){
					  swal({
						  position: 'center',
						  type: 'success',
						  title: data.message,
						  showConfirmButton: true,
						  timer: 2000
						})
						 setTimeout('startRequest()',2500);
					}			
				},
			error: function(){alert("系統忙碌中 ， 請稍後再試 。")}
        })

	});
});



// ***********新增集點卡******************
$(function(){
	$('#doSubmit').on('click', function(){
		var points = $('#points').val();
		var points_cash = $('#points_cash').val();
		var card_des = $('#card_des').val();
		var exp_date = $('#exp_date').val();
		var action =  $('#action').val();
		$.ajax({
			 type: "POST",
			 url: "<%=request.getContextPath()%>/CardServlet",
			 data: {"points": points, "points_cash":points_cash, "action":action, "card_des":card_des, "exp_date":exp_date},
			 dataType: "json",
			 async: false, // true預設是非同步，也就是"不會等待"; false是同步，"會等待"
			 success: function(data){
				  if(data.message != null){
					  swal({
						  position: 'center',
						  type: 'success',
						  title: data.message,
						  showConfirmButton: true,
						  timer: 2000
						})
					setTimeout('startRequest()',2000);
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
			error: function(){alert("輸入資料有誤 。")}
        })

	});
});

//***********集點卡上下架******************



$(function(){
	$('.doCard').on('click', function(){
		var card_kinds = $(this).next().val();
		var action = $(this).next().next().val();
		$.ajax({
			 type: "POST",
			 url: "<%=request.getContextPath()%>/CardServlet",
			 data: {"card_kinds": card_kinds, "action":action,},
			 dataType: "json",
			 async: false, // true預設是非同步，也就是"不會等待"; false是同步，"會等待"
			 success: function(data){
				  if(data.message != null){
					  swal({
						  position: 'center',
						  type: 'success',
						  title: data.message,
						  showConfirmButton: true,
						  timer: 2000
						})
						 setTimeout('startRequest()',2500);
					}	
				 
				},
			error: function(){alert("系統忙碌中 ， 請稍後再試 。")}
        })

	});
});


function startRequest()
{
	location.reload(); 
}

function onFileSelected(event,get){
     
     var selectFile = event.target.files[0];
     var reader = new FileReader();
     
        var imgtag =  document.getElementById(get+"1");
        imgtag.title = selectFile.name;

        var imgtag2 =  document.getElementById(get+"2");
        imgtag2.title = selectFile.name;
        
        reader.onload = function(event){
            imgtag.src = event.target.result;
            imgtag2.src = event.target.result;
        };
        reader.readAsDataURL(selectFile);
        
       
        
    }
</script>	 
</html>