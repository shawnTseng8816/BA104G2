<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
	
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	
	<title>揪茶趣 - 店家登入</title>
	
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/store_base.css">
	<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" />
	<link href="//fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900&subset=latin,latin-ext" rel="stylesheet" />	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
	<!--[if lt IE 9]>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
	
	<style type="text/css">
		.box {
		   position: relative;  !important;
		   top: 0;!important;
		   opacity: 1;!important;
		   float: left;!important;
		   padding: 60px 50px 40px 50px;!important;
		   width: 100%;!important;
		   background: #fff;!important;
		   border-radius: 10px;!important;
		   transform: scale(1);!important;
		   -webkit-transform: scale(1);!important;
		   -ms-transform: scale(1);!important;
		   z-index: 5;!important;
		}
		
		.box.back {
		   transform: scale(.95);!important;
		   -webkit-transform: scale(.95);!important;
		   -ms-transform: scale(.95);!important;
		   top: -20px;!important;
		   opacity: .8;!important;
		   z-index: -1;!important;
		}
		
		.box:before {
		   content: "";!important;
		   width: 100%;!important;
		   height: 30px;!important;
		   border-radius: 10px;!important;
		   position: absolute;!important;
		   top: -10px;!important;
		   background: rgba(255, 255, 255, .6);!important;
		   left: 0;!important;
		   transform: scale(.95);!important;
		   -webkit-transform: scale(.95);!important;
		   -ms-transform: scale(.95);!important;
		   z-index: -1;!important;
		}
		
		.overbox .title {
		   color: #fff;!important;
		}
		
		.overbox .title:before {
		   background: #fff;!important;
		}
		
		.title {
		   width: 100%;!important;
		   float: left;!important;
		   line-height: 46px;!important;
		   font-size: 34px;!important;
		   font-weight: 700;!important;
		   letter-spacing: 2px;!important;
		   color: #AD974F;!important;
		   /* #AB9353比#AD974F暗了一點的樣子 */
		   position: relative;!important;
		}
		
		.title:before {
		   content: "";!important;
		   width: 5px;!important;
		   height: 100%;!important;
		   position: absolute;!important;
		   top: 0;!important;
		   left: -50px;!important;
		   background: #b5a160;!important;
		}
		
		.input, 
		.input label, 
 		.input input, 
 		.input .spin, 
 		.button, 
 		.button button .button.login button i.fa, 
 		.material-button .shape:before, 
 		.material-button .shape:after, 
 		.button.login button { 
 		   transition: 300ms cubic-bezier(.4, 0, .2, 1); !important;
 		   -webkit-transition: 300ms cubic-bezier(.4, 0, .2, 1); !important;
 		   -ms-transition: 300ms cubic-bezier(.4, 0, .2, 1); !important;
 		}
		
 		.material-button, 
 		.alt-2, 
 		.material-button .shape, 
 		.alt-2 .shape, 
 		.box { 
 		   transition: 400ms cubic-bezier(.4, 0, .2, 1); !important;
 		   -webkit-transition: 400ms cubic-bezier(.4, 0, .2, 1); !important;
 		   -ms-transition: 400ms cubic-bezier(.4, 0, .2, 1); !important;
 		} 
		
		.input,
		.input label,
		.input input,
		.input .spin,
		.button,
		.button button {
		   width: 100%;!important;
		   float: left;!important;
		}
		
		.input,
		.button {
		   margin-top: 30px;!important;
		   height: 70px;!important;
		}
		
		.input,
		.input input,
		.button,
		.button button {
		   position: relative;!important;
		}
		
		.input input {
		   height: 60px;!important;
		   top: 10px;!important;
		   border: none;!important;
		   background: transparent;!important;
		}
		
		.input input,
		.input label,
		.button button {
		   font-family:Microsoft JhengHei;
		   font-size: 24px;!important;
		   color: rgba(0, 0, 0, 0.8);!important;
		   font-weight: 300;!important;
		}
		
		.input:before,
		.input .spin {
		   width: 100%;!important;
		   height: 1px;!important;
		   position: absolute;!important;
		   bottom: 0;!important;
		   left: 0;!important;
		}
		
		.input:before {
		   content: "";
		   background: rgba(0, 0, 0, 0.1);!important;
		   z-index: 3;!important;
		}
		
		.input .spin {
		   background: #8E793E;!important;
		   z-index: 4;!important;
		   width: 0;!important;
		}
		
		.overbox .input .spin {
		   background: rgba(255, 255, 255, 1);!important;
		}
		
		.overbox .input:before {
		   background: rgba(255, 255, 255, 0.5);!important;
		}
		
		
		
		.button.login {
		   width: 60%;!important;
		   left: 20%;!important;
		}
		
		.button.login button,
		.button button {
		   width: 100%;!important;
		   line-height: 64px;!important;
		   left: 0%;!important;
		   background-color: transparent;!important;
		   border: 3px solid rgba(0, 0, 0, 0.1);!important;
		   font-weight: 900;!important;
		   font-size: 18px;!important;
		   color: rgba(0, 0, 0, 0.2);!important;
		}
		
		.button.login {
		   margin-top: 30px;!important;
		}
		
		.button {
		   margin-top: 20px;!important;
		}
		
		.button button {
		   background-color: #fff;!important;
		   color: #AD974F;!important;
		   border: none;!important;
		}
		
		.button.login button.active {
		   border: 3px solid transparent;!important;
		   color: #fff !important;!important;
		}
		
		.button.login button.active span {
		   opacity: 0;!important;
		   transform: scale(0);!important;
		   -webkit-transform: scale(0);!important;
		   -ms-transform: scale(0);!important;
		}
		
		.button.login button.active i.fa {
		   opacity: 1;!important;
		   transform: scale(1) rotate(-0deg);!important;
		   -webkit-transform: scale(1) rotate(-0deg);!important;
		   -ms-transform: scale(1) rotate(-0deg);!important;
		}
		
		.button.login button i.fa {
		   width: 100%;!important;
		   height: 100%;!important;
		   position: absolute;!important;
		   top: 0;!important;
		   left: 0;!important;
		   line-height: 60px;!important;
		   transform: scale(0) rotate(-45deg);!important;
		   -webkit-transform: scale(0) rotate(-45deg);!important;
		   -ms-transform: scale(0) rotate(-45deg);!important;
		}
		
		.button.login button:hover {
		   color: #AD974F;
		   border-color: #AD974F;
		}
		
		.button {
		   margin: 40px 0;!important;
		   overflow: hidden;!important;
		   z-index: 2;!important;
		}
		
		.button button {
		   cursor: pointer;!important;
		   position: relative;!important;
		   z-index: 2;!important;
		}
		
		.pass-forgot {
		   width: 100%;!important;
		   float: left;!important;
		   text-align: center;!important;
		   color: #8E793E;!important;
/* 		   color: rgba(0, 0, 0, 0.4); */!important;
		   font-size: 18px;!important;
		   font-family: Microsoft JhengHei, sans-serif;!important;
		}
		
		.box a{
			text-decoration:blink;!important;
		}
		
		.pass-forgot:hover {
			color: #b5a160;!important;
		}
		
		.click-efect {
		   position: absolute;!important;
		   top: 0;!important;
		   left: 0;!important;
		   background: #AD974F;!important;
		   border-radius: 50%;!important;
		}
		
		.overbox {
		   width: 100%;!important;
		   height: 100%;!important;
		   position: absolute;!important;
		   top: 0;!important;
		   left: 0;!important;
		   overflow: inherit;!important;
		   border-radius: 10px;!important;
		   padding: 60px 50px 40px 50px;!important;
		}
		
		.overbox .title,
		.overbox .button,
		.overbox .input {
		   z-index: 111;!important;
		   position: relative;!important;
		   color: #fff !important;!important;
		   display: none;!important;
		}
		
		.overbox .title {
		   width: 80%;!important;
		}
		
		.overbox .input {
		   margin-top: 20px;!important;
		}
		
		.overbox .input input,
		.overbox .input label {
		   color: #fff;!important;
		}
		
 		.overbox .material-button, 
 		.overbox .material-button .shape, 
 		.overbox .alt-2, 
 		.overbox .alt-2 .shape { 
 		   display: block; !important;
 		} 
		
		.material-button,
		.alt-2 {
		   width: 140px;!important;
		   height: 140px;!important;
		   border-radius: 50%;!important;
		   background: #AD974F;!important;
		   position: absolute;!important;
		   top: 40px;!important;
		   right: -70px;!important;
		   cursor: pointer;!important;
		   z-index: 100;!important;
		   transform: translate(0%, 0%);!important;
		   -webkit-transform: translate(0%, 0%);!important;
		   -ms-transform: translate(0%, 0%);!important;
		}
		
 		.material-button .shape, 
 		.alt-2 .shape { 
 		   position: absolute; !important;
 		   top: 0; !important;
 		   right: 0; !important;
 		   width: 100%; !important;
 		   height: 100%; !important;
 		} 
		
 		.material-button .shape:before, 
 		.alt-2 .shape:before, 
 		.material-button .shape:after, 
 		.alt-2 .shape:after { 
 		   content: ""; !important;
 		   background: #fff; !important;
 		   position: absolute; !important;
 		   top: 50%; !important;
 		   left: 50%; !important;
 		   transform: translate(-50%, -50%) rotate(360deg); !important;
 		   -webkit-transform: translate(-50%, -50%) rotate(360deg); !important;
 		   -ms-transform: translate(-50%, -50%) rotate(360deg); !important;
		} 
		
		.material-button .shape:before, 
 		.alt-2 .shape:before { 
 		   width: 25px; !important;
 		   height: 4px; !important;
 		} 
		
		.material-button .shape:after, 
 		.alt-2 .shape:after { 
		   height: 25px; !important;
 		   width: 4px; !important;
 		} 
		
		.material-button.active,
		.alt-2.active {
		   top: 50%;!important;
		   right: 50%;!important;
		   transform: translate(50%, -50%) rotate(0deg);!important;
		   -webkit-transform: translate(50%, -50%) rotate(0deg);!important;
		   -ms-transform: translate(50%, -50%) rotate(0deg);!important;
		}
		
		body {
		/*    background-image: url(https://lh4.googleusercontent.com/-XplyTa1Za-I/VMSgIyAYkHI/AAAAAAAADxM/oL-rD6VP4ts/w1184-h666/Android-Lollipop-wallpapers-Google-Now-Wallpaper-2.png); */
		   background-position: center;!important;
		   background-size: cover;!important;
		   background-repeat: no-repeat;!important;
		   min-height: 100vh;!important;
		   
/* 		   font-family: 'Roboto', sans-serif; */
		}
		
		body,
		html {
		   overflow: hidden;!important;
		}
		
		.materialContainer {
		   width: 100%;!important;
		   max-width: 460px;!important;
		   position: absolute;  !important;
		   /* 改relative，紅+會不能用 */
		   top: 35em;!important;
		   left: 50%;!important;
		   transform: translate(-50%, -50%);!important;
		   -webkit-transform: translate(-50%, -50%);!important;
		   -ms-transform: translate(-50%, -50%);!important;
		}
		
		*,
		*:after,
		*::before {
		   -webkit-box-sizing: border-box;!important;
		   -moz-box-sizing: border-box;!important;
		   box-sizing: border-box;!important;
		   margin: 0;!important;
		   padding: 0;!important;
		   text-decoration: none;!important;
		   list-style-type: none;!important;
		   outline: none;!important;
		}
		
		input[type="submit" i]{
			width:100%;!important;
			-webkit-appearance:button;!important;
			cursor:pointer;!important;
			line-height:64px;!important;
			left:0px;!important;
			border-color:#8E793E;!important;
	/* 		color:#AD974F; */
			background-color:#ffffff;!important;
/* 			border: 3px solid rgba(0, 0, 0, 0.1); */
			border-style:solid;!important;
			font-family:Microsoft JhengHei;!important;
			font-weight:900;!important;
			font-size:18px;!important;
/* 			color: rgba(0, 0, 0, 0.2); */
			color:#8E793E;!important;
		}
		input[type="submit" i]:hover{
/* https://timbdesign.com/web-design-color-palettes */
			color: #b5a160;
		    border-color: #b5a160;
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

<jsp:include page="/store-end/store_top.jsp" flush="true" />

<%-- <jsp:include page="/store-end/store_left.jsp" flush="true" /> --%>
<!-- https://www.uplabs.com/collections/splash-5507ed4f-af7d-45a9-a242-a6a469736b33 -->

<!-- 	<div class="modal fade" id="modal-id">
		<div class="modal-dialog">
			<div class="modal-content"> -->
				<div class="materialContainer">
					<form action="sto.do" method="post" enctype="multipart/form-data">
					<div class="box">
			    		<div class="title">LOGIN</div>
							<%-- 錯誤表列 --%>
							<c:if test="${not empty errorMsgs}">
								<ul>
									<img src="<%= request.getContextPath() %>/img/xx.png" height="30" width="30">
									<c:forEach var="message" items="${errorMsgs}">
										<li style="color:red">${message}</li>
									</c:forEach>
								</ul>
							</c:if>
	
				    	    <div class="input">
						       <label for="name">帳號</label>
						       <input type=text name="sto_acc" value="${storeprofileVO.sto_acc}" id="name">
						       <span class="spin" style="margin: 0px 0px 0px 0px;"></span>
						    </div>
						
						    <div class="input">
						       <label for="pass">密碼</label>
						       <input type=password name="sto_pwd" value="${storeprofileVO.sto_pwd}" id="pass">
						       <span class="spin" style="margin: 0px 0px 0px 0px;"></span>
						    </div>
						    
						    <div class="button login">
						       <input type="hidden" name="action"  value="login">
							   <input type=submit value="  Go  ">
						    </div>
						    <div>
						    	<a href="<%= request.getContextPath() %>/store-end/storeprofile/addStoreProfile.jsp" class="pass-forgot">商店註冊</a>
							</div>
							<div class="magicbutton">
								<button class="magic" onClick="inPutInfo1()" type="button">shop1</button>
								<button class="magic" onClick="inPutInfo2()" type="button">shop2</button>
								<button class="magic" onClick="inPutInfo3()" type="button">shop3</button>
								<button class="magic" onClick="inPutInfo4()" type="button">shop4</button>
								<button class="magic" onClick="inPutInfo5()" type="button">shop5</button>
								<button class="magic" onClick="inPutInfo6()" type="button">shop6</button>
								<button class="magic" onClick="inPutInfo7()" type="button">shop7</button>
								<button class="magic2" onClick="inPutInfo8()" type="button">shop8</button>
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
<jsp:include page="/store-end/store_foot.jsp" flush="true" />		

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
			document.getElementsByName("sto_acc")[0].value= 'shop1';
			document.getElementsByName("sto_pwd")[0].value= 'store1';
		}
	    
	    function inPutInfo2(){
			document.getElementsByName("sto_acc")[0].value= 'shop2';
			document.getElementsByName("sto_pwd")[0].value= 'store2';
		}
	    
	    function inPutInfo3(){
			document.getElementsByName("sto_acc")[0].value= 'shop3';
			document.getElementsByName("sto_pwd")[0].value= 'store3';
		}
	    
	    function inPutInfo4(){
			document.getElementsByName("sto_acc")[0].value= 'shop4';
			document.getElementsByName("sto_pwd")[0].value= 'store4';
		}
	    
	    function inPutInfo5(){
			document.getElementsByName("sto_acc")[0].value= 'shop5';
			document.getElementsByName("sto_pwd")[0].value= 'store5';
		}
	    
	    function inPutInfo6(){
			document.getElementsByName("sto_acc")[0].value= 'shop6';
			document.getElementsByName("sto_pwd")[0].value= 'store6';
		}
	    
	    function inPutInfo7(){
			document.getElementsByName("sto_acc")[0].value= 'shop7';
			document.getElementsByName("sto_pwd")[0].value= 'store7';
		}
	    
	    function inPutInfo8(){
			document.getElementsByName("sto_acc")[0].value= 'shop8';
			document.getElementsByName("sto_pwd")[0].value= 'store8';
		}
	    

	</script>
</html>