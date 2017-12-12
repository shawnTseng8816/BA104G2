<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
	
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	
	<title>揪茶趣 - 員工登入</title>
	
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/back_base.css">
	<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" />
	<link href="//fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900&subset=latin,latin-ext" rel="stylesheet" />	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
	<!--[if lt IE 9]>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
	
	<style type="text/css">
		.box {
		   position: relative;
		   top: 0;
		   opacity: 1;
		   float: left;
		   padding: 60px 50px 40px 50px;
		   width: 100%;
		   background: #fff;
		   border-radius: 10px;
		   transform: scale(1);
		   -webkit-transform: scale(1);
		   -ms-transform: scale(1);
		   z-index: 5;
		}
		
		.box.back {
		   transform: scale(.95);
		   -webkit-transform: scale(.95);
		   -ms-transform: scale(.95);
		   top: -20px;
		   opacity: .8;
		   z-index: -1;
		}
		
		.box:before {
		   content: "";
		   width: 100%;
		   height: 30px;
		   border-radius: 10px;
		   position: absolute;
		   top: -10px;
		   background: rgba(255, 255, 255, .6);
		   left: 0;
		   transform: scale(.95);
		   -webkit-transform: scale(.95);
		   -ms-transform: scale(.95);
		   z-index: -1;
		}
		
		.overbox .title {
		   color: #fff;
		}
		
		.overbox .title:before {
		   background: #fff;
		}
		
		.title {
		   width: 100%;
		   float: left;
		   line-height: 46px;
		   font-size: 34px;
		   font-weight: 700;
		   letter-spacing: 2px;
		   color: #595942;
		   position: relative;
		}
		
		.title:before {
		   content: "";
		   width: 5px;
		   height: 100%;
		   position: absolute;
		   top: 0;
		   left: -50px;
		   background: #595942;
		}
		
		.input,
		.input label,
		.input input,
		.input .spin,
		.button,
		.button button {
		   width: 100%;
		   float: left;
		}
		
		.input,
		.button {
		   margin-top: 30px;
		   height: 70px;
		}
		
		.input,
		.input input,
		.button,
		.button button {
		   position: relative;
		}
		
		.input input {
		   height: 60px;
		   top: 10px;
		   border: none;
		   background: transparent;
		}
		
		.input input,
		.input label,
		.button button {
		   font-family:Microsoft JhengHei;
		   font-size: 24px;
		   color: rgba(0, 0, 0, 0.8);
		   font-weight: 300;
		}
		
		.input:before,
		.input .spin {
		   width: 100%;
		   height: 1px;
		   position: absolute;
		   bottom: 0;
		   left: 0;
		}
		
		.input:before {
		   content: "";
		   background: rgba(0, 0, 0, 0.1);
		   z-index: 3;
		}
		
		.input .spin {
		   background: #595942;
		   z-index: 4;
		   width: 0;
		}
		
		.overbox .input .spin {
		   background: rgba(255, 255, 255, 1);
		}
		
		.overbox .input:before {
		   background: rgba(255, 255, 255, 0.5);
		}
		
		.input label {
		   position: absolute;
		   top: 10px;
		   left: 0;
		   z-index: 2;
		   cursor: pointer;
		   line-height: 60px;
		}
		
		.button.login {
		   width: 60%;
		   left: 20%;
		}
		
		.button.login button,
		.button button {
		   width: 100%;
		   line-height: 64px;
		   left: 0%;
		   background-color: transparent;
		   border: 3px solid rgba(0, 0, 0, 0.1);
		   font-weight: 900;
		   font-size: 18px;
		   color: rgba(0, 0, 0, 0.2);
		}
		
		.button.login {
		   margin-top: 30px;
		}
		
		.button {
		   margin-top: 20px;
		}
		
		.button button {
		   background-color: #fff;
		   color: #595942;
		   border: none;
		}
		
		.button.login button.active {
		   border: 3px solid transparent;
		   color: #fff !important;
		}
		
		.button.login button.active span {
		   opacity: 0;
		   transform: scale(0);
		   -webkit-transform: scale(0);
		   -ms-transform: scale(0);
		}
		
		.button.login button.active i.fa {
		   opacity: 1;
		   transform: scale(1) rotate(-0deg);
		   -webkit-transform: scale(1) rotate(-0deg);
		   -ms-transform: scale(1) rotate(-0deg);
		}
		
		.button.login button i.fa {
		   width: 100%;
		   height: 100%;
		   position: absolute;
		   top: 0;
		   left: 0;
		   line-height: 60px;
		   transform: scale(0) rotate(-45deg);
		   -webkit-transform: scale(0) rotate(-45deg);
		   -ms-transform: scale(0) rotate(-45deg);
		}
		
		.button.login button:hover {
		   color: #595942;
		   border-color: #595942;
		}
		
		.button {
		   margin: 40px 0;
		   overflow: hidden;
		   z-index: 2;
		}
		
		.button button {
		   cursor: pointer;
		   position: relative;
		   z-index: 2;
		}
		
		.pass-forgot {
		   width: 100%;
		   float: left;
		   text-align: center;
		   color: #595942;
/* 		   color: rgba(0, 0, 0, 0.4); */
		   font-size: 18px;
		   font-family: Microsoft JhengHei, sans-serif;
		}
		
		.box a{
			text-decoration:blink;
		}
		
		.pass-forgot:hover {
			color: #595942;
		}
		
		.click-efect {
		   position: absolute;
		   top: 0;
		   left: 0;
		   background: #595942;
		   border-radius: 50%;
		}
		
		.overbox {
		   width: 100%;
		   height: 100%;
		   position: absolute;
		   top: 0;
		   left: 0;
		   overflow: inherit;
		   border-radius: 10px;
		   padding: 60px 50px 40px 50px;
		}
		
		.overbox .title,
		.overbox .button,
		.overbox .input {
		   z-index: 111;
		   position: relative;
		   color: #fff !important;
		   display: none;
		}
		
		.overbox .title {
		   width: 80%;
		}
		
		.overbox .input {
		   margin-top: 20px;
		}
		
		.overbox .input input,
		.overbox .input label {
		   color: #fff;
		}
		
/* 		.overbox .material-button, */
/* 		.overbox .material-button .shape, */
/* 		.overbox .alt-2, */
/* 		.overbox .alt-2 .shape { */
/* 		   display: block; */
/* 		} */
		
		.material-button,
		.alt-2 {
		   width: 140px;
		   height: 140px;
		   border-radius: 50%;
		   background: #595942;
		   position: absolute;
		   top: 40px;
		   right: -70px;
		   cursor: pointer;
		   z-index: 100;
		   transform: translate(0%, 0%);
		   -webkit-transform: translate(0%, 0%);
		   -ms-transform: translate(0%, 0%);
		}
		
		.material-button.active,
		.alt-2.active {
		   top: 50%;
		   right: 50%;
		   transform: translate(50%, -50%) rotate(0deg);
		   -webkit-transform: translate(50%, -50%) rotate(0deg);
		   -ms-transform: translate(50%, -50%) rotate(0deg);
		}
		
		body {
		/*    background-image: url(https://lh4.googleusercontent.com/-XplyTa1Za-I/VMSgIyAYkHI/AAAAAAAADxM/oL-rD6VP4ts/w1184-h666/Android-Lollipop-wallpapers-Google-Now-Wallpaper-2.png); */
		   background-position: center;
		   background-size: cover;
		   background-repeat: no-repeat;
		   min-height: 100vh;
		   
/* 		   font-family: 'Roboto', sans-serif; */
		}
		
		body,
		html {
		   overflow: hidden;
		}
		
		.materialContainer {
		   width: 100%;
		   max-width: 460px;
		   position: absolute;  
		   /* 改relative，紅+會不能用 */
		   top: 35em;
		   left: 50%;
		   transform: translate(-50%, -50%);
		   -webkit-transform: translate(-50%, -50%);
		   -ms-transform: translate(-50%, -50%);
		}
		
		*,
		*:after,
		*::before {
		   -webkit-box-sizing: border-box;
		   -moz-box-sizing: border-box;
		   box-sizing: border-box;
		   margin: 0;
		   padding: 0;
		   text-decoration: none;
		   list-style-type: none;
		   outline: none;
		}
		
		input[type="submit" i]{
			width:100%;
			-webkit-appearance:button;
			cursor:pointer;
			line-height:64px;
			left:0px;
			border-color:#595942;
	/* 		color:#AD974F; */
			background-color:#ffffff;
/* 			border: 3px solid rgba(0, 0, 0, 0.1); */
			border-style:solid;
			font-family:Microsoft JhengHei;
			font-weight:900;
			font-size:18px;
/* 			color: rgba(0, 0, 0, 0.2); */
			color:#595942;
		}
		input[type="submit" i]:hover{
/* https://timbdesign.com/web-design-color-palettes */
			color: #595942;
		    border-color: #595942;
		    background:#FFFFFF;
		}
		
		.magic {
			padding:2px;!important;
		    background-color: #FFFFFF;!important;
		    color: #ffd280;!important;
		    border:1px solid #ffffff;!important;
		    border-radius: 300px;!important;
		}
		
		.magic:hover {
			padding:2px;!important;
		    background-color: #ffd280;!important;
		    color: #ffffff;!important;
		    border:1px solid #ffd280;!important;
		    border-radius: 300px;!important;
		}
		
		.magic2 {
			padding:2px;!important;
		    background-color: #FFFFFF;!important;
		    color: #fa5532;!important;
		    border:1px solid #ffffff;!important;
		    border-radius: 300px;!important;
		}
		
		.magic2:hover {
			padding:2px;!important;
		    background-color: #fa5532;!important;
		    color: #ffffff;!important;
		    border:1px solid #fa5532;!important;
		    border-radius: 300px;!important;
		    
		}
		.magicbutton{
			display:inline-table;!important;
/* 			border:1px solid #fff; */
		}
	</style>
	
</head>
<body>

<jsp:include page="/back-end/back_top.jsp" flush="true" />


<!-- https://www.uplabs.com/collections/splash-5507ed4f-af7d-45a9-a242-a6a469736b33 -->

<!-- 	<div class="modal fade" id="modal-id">
		<div class="modal-dialog">
			<div class="modal-content"> -->
				<div class="materialContainer">
					<form action="<%=request.getContextPath()%>/back-end/backstagemanagement/BackstageManagementServletChi" method="post" enctype="multipart/form-data">
					<div class="box">
			    		<div class="title">LOGIN</div>
							<%-- 錯誤表列 --%>
							<c:if test="${not empty errorMsgs}">
								<font style="color:red">請修正以下錯誤:</font>
								<ul>
									<c:forEach var="message" items="${errorMsgs}">
										<li style="color:red">${message}</li>
									</c:forEach>
								</ul>
							</c:if>
	
				    	    <div class="input">
						       <label for="name">帳號</label>
						       <input type=text name="bm_num" value="${backstagemanagementVO.bm_num}" id="name">
						       <span class="spin" style="margin: 0px 0px 0px 0px;"></span>
						    </div>
						
						    <div class="input">
						       <label for="pass">密碼</label>
						       <input type=password name="bm_pwd" value="${backstagemanagementVO.bm_pwd}" id="pass">
						       <span class="spin" style="margin: 0px 0px 0px 0px;"></span>
						    </div>
						    
						    <div class="button login">
						       <input type="hidden" name="action"  value="login">
							   <input type=submit value="  Go  ">
						    </div>
						    <div class="magicbutton">
								<button class="magic" onClick="inPutInfo1()" type="button">bm001</button>
								<button class="magic" onClick="inPutInfo2()" type="button">bm002</button>
								<button class="magic" onClick="inPutInfo3()" type="button">bm003</button>
								<button class="magic" onClick="inPutInfo4()" type="button">bm004</button>
								<button class="magic" onClick="inPutInfo5()" type="button">bm005</button>
								<button class="magic" onClick="inPutInfo6()" type="button">bm006</button>
								<button class="magic" onClick="inPutInfo7()" type="button">bm007</button>
								<button class="magic2" onClick="inPutInfo8()" type="button">m</button>
							</div>	 
						</div>
						    
<!-- 						<div class="overbox"> -->
<!-- 							<div class="material-button alt-2"><span class="shape"></span></div> -->
	
<!-- 							<div class="title">REGISTER</div> -->
							
<!-- 							<div class="input"> -->
<!-- 							   <label for="regname">Username</label> -->
<!-- 							   <input type="text" name="mem_acc" id="regname"> -->
<!-- 							   <span class="spin"></span> -->
<!-- 							</div> -->
							
<!-- 							<div class="input"> -->
<!-- 							   <label for="regpass">Password</label> -->
<!-- 							   <input type="password" name="mem_pwd"" id="regpass"> -->
<!-- 							   <span class="spin"></span> -->
<!-- 							</div> -->
							
<!-- 							<div class="input"> -->
<!-- 							   <label for="reregpass">Repeat Password</label> -->
<!-- 							   <input type="password" name="mem_pwd" id="reregpass"> -->
<!-- 							   <span class="spin"></span> -->
<!-- 							</div> -->
							
<!-- 							<div class="button"> -->
<!-- 							   <button><span>NEXT</span></button> -->
<!-- 							</div> -->
<!-- 						</div> -->
					</form>
				</div>
<!-- 			</div>
		</div>
	</div> -->
<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<jsp:include page="/back-end/back_foot.jsp" flush="true" />		

</body>
	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

    <script>
// 		function setBstModalMaxHeight(element) {
// 			  this.$element          = $(element);
// 			  this.$modalContent     = this.$element.find('.modal-content');
// 			  var $window            = $(window);
// 			  var $modalContentOH    = this.$modalContent.outerHeight();
// 			  var $modalContentIH    = this.$modalContent.innerHeight();
// 			  var _modalBorderWidth   = $modalContentOH - $modalContentIH;
// 			  var _modalDialogMargin  = $window.width() < 768 ? 20 : 60;
// 			  var _modalContentHeight = $window.height() - (_modalDialogMargin + _modalBorderWidth);
// 			  var _modalHeaderHeight  = this.$element.find('.modal-header').outerHeight() || 0;
// 			  var _modalFooterHeight  = this.$element.find('.modal-footer').outerHeight() || 0;
// 			  var _modalMaxHeight     = _modalContentHeight - (_modalHeaderHeight + _modalFooterHeight);
			
// 			  this.$modalContent.css({
// 			      'overflow': 'hidden'
// 			  });
			  
// 			  this.$element
// 			    .find('.modal-body').css({
// 			      'max-height': _modalMaxHeight,
// 			      'overflow-y': 'auto'
// 			  });
// 			}
			
// 			$('.modal').on('show.bs.modal', function() {
// 			  $(this).show();
// 			  setBstModalMaxHeight(this);
// 			});
			
// 			$(window).resize(function() {
// 			  if ($('.modal.in').length != 0) {
// 			    setBstModalMaxHeight($('.modal.in'));
// 			  }
// 		});
    
    
		$(function() {
// 		   if((".input").val()==''){
		   $(".input input").focus(function() {
			      $(".input input").parent(".input").each(function() {
			         $("label", this).css({
			            "line-height": "18px",
			            "font-size": "18px",
			            "font-weight": "100",
			            "top": "0px"
			         })
			         $(".spin", this).css({
			            "width": "100%"
			         })	         
			      });
		   })
// 		   .blur(function() {
// 		      $(".spin").css({
// 		         "width": "0px"
// 		      })
// 		      if(($(this).val()=='')){
// 			         $(this).parent(".input").each(function() {
// 		            $("label", this).css({
// 		               "line-height": "60px",
// 		               "font-size": "24px",
// 		               "font-weight": "300",
// 		               "top": "10px"
// 		            })
// 		         });

// 		      }
// 		   });
		
		   $(".button").click(function(e) {
		      var pX = e.pageX,
		         pY = e.pageY,
		         oX = parseInt($(this).offset().left),
		         oY = parseInt($(this).offset().top);
		
		      $(this).append('<span class="click-efect x-' + oX + ' y-' + oY + '" style="margin-left:' + (pX - oX) + 'px;margin-top:' + (pY - oY) + 'px;"></span>')
		      $('.x-' + oX + '.y-' + oY + '').animate({
		         "width": "500px",
		         "height": "500px",
		         "top": "-250px",
		         "left": "-250px",
		
		      }, 600);
		      $("button", this).addClass('active');
		   })
		});
		
		
	    function inPutInfo1(){
			document.getElementsByName("bm_num")[0].value= 'bm001';
			document.getElementsByName("bm_pwd")[0].value= 'bm001';
		}
	    
	    function inPutInfo2(){
			document.getElementsByName("bm_num")[0].value= 'bm002';
			document.getElementsByName("bm_pwd")[0].value= 'bm002';
		}
	    
	    function inPutInfo3(){
			document.getElementsByName("bm_num")[0].value= 'bm003';
			document.getElementsByName("bm_pwd")[0].value= 'bm003';
		}
	    
	    function inPutInfo4(){
			document.getElementsByName("bm_num")[0].value= 'bm004';
			document.getElementsByName("bm_pwd")[0].value= 'bm004';
		}
	    
	    function inPutInfo5(){
			document.getElementsByName("bm_num")[0].value= 'bm005';
			document.getElementsByName("bm_pwd")[0].value= 'bm005';
		}
	    
	    function inPutInfo6(){
			document.getElementsByName("bm_num")[0].value= 'bm006';
			document.getElementsByName("bm_pwd")[0].value= 'bm006';
		}
	    
	    function inPutInfo7(){
			document.getElementsByName("bm_num")[0].value= 'bm007';
			document.getElementsByName("bm_pwd")[0].value= 'bm007';
		}
	    
	    function inPutInfo8(){
			document.getElementsByName("bm_num")[0].value= 'Max';
			document.getElementsByName("bm_pwd")[0].value= 'bm008';
		}
	</script>
</html>