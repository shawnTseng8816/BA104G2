<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String currentSto_num = (String) request.getSession().getAttribute("currentSto_num");
	String meror_num = "meror_num" + currentSto_num;
	pageContext.setAttribute("meror_num", request.getSession().getAttribute(meror_num));
%>

<jsp:useBean id="proTypeService" scope="page" class="com.product_type.model.ProductTypeService" />
<jsp:useBean id="productService" scope="page" class="com.product.model.ProductService" />
<jsp:useBean id="iceListService" scope="page" class="com.ice_list.model.IceListService" />
<jsp:useBean id="sweetService" scope="page" class="com.sweetness.model.SweetnessService" />
<jsp:useBean id="extraService" scope="page" class="com.extra.model.ExtraService" />
<jsp:useBean id="memberProfileService" scope="page" class="com.member_profile.model.MemberProfileService" />
<jsp:useBean id="storeProfileService" scope="page" class="com.store_profile.model.StoreProfileService" />
<jsp:useBean id="cardListService" scope="page" class="com.card_list.model.CardListService" />
<jsp:useBean id="cardService" scope="page" class="com.card.model.CardService" />
<jsp:useBean id="couponListService" scope="page" class="com.coupon_list.model.CouponListService" />
<jsp:useBean id="couponService" scope="page" class="com.coupon.model.CouponService" />
<jsp:useBean id="friendList" scope="page" class="com.friend_list.model.FriendListService" />
<jsp:useBean id="mergedOrderService" scope="page" class="com.merged_order.model.MergedOrderService" />
<jsp:useBean id="storeImgService" scope="page" class="com.store_image.model.StoreImageService" />

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Examples</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script src="<%=request.getContextPath()%>/js/dist/sweetalert2.all.min.js"></script>
<script src="<%=request.getContextPath()%>/js/dist/sweetalert2.min.js"></script>

<style>

		.titlePic img {
		
			width: 100%;
		}
		
		.section2-L {
			margin-top: 20px;
		}
		
		.buyShow img {
			display: block;
			height: 30%;
			width: auto;
		}
		
		.friends img, .tab-content img {
			width: 100%;
		}
		
		.icon img {
			display: block;
			margin: auto;
			width: 75%;
		}
		
		.friends h4 {
			text-align: center;
		}
		
		.friends, table {
			margin-top: 1em;
		}
		
		.tab-content td {
			padding: 3px;
			text-align: center;
		}
		
		.cut, .add {
			border-radius: 1em;
		}
		
		.addone {
			border-radius: 0.5em;
			width: 3em;
		}
		
		.section2-R {
			height: 68.5vh;
			/*height: 74.4vh;*/
			overflow-y: scroll;
		}
		
		.tab-content {
			margin-top: 1em;
			height: 53vh;
			/*height: 66vh;*/
			/*overflow-y: scroll;*/
		}
		
		.img-check {
			max-width: 200px;
			max-height: 300px
		}
		
		.section1-R {
			margin-bottom: 1em;
		}
		
		.totalMoney h3 {
			margin-top: 1.5em;
		}
		
		.footer {
			position: absolute;
			bottom: 0;
			width: 100%;
			background-color: #b0c4de;
		}
		
		.modal-header {
			text-align: center;
			border-bottom: 1px solid #c0c0c0;
		}
		
		.modal-footer {
			border-top: 1px solid #c0c0c0;
		}
		
		.friendcheck {
			position: absolute;
			left: 0;
			top: 0;
		}
		
		.addone, .cut, .add:focus {
			outline: 0;
		}
		
		.selcls {
			border-radius: 0.7em;
			padding: 4px;
			border: solid 0px #800000;
			outline: 0;
			background: -webkit-gradient(linear, left top, left 25, from(#FFFFFF),
				color-stop(4%, #b0e0e6), to(#FFFFFF));
		}
		
		.element-card {
			position: relative;
			width: 18em;
			height: 24em;
			-webkit-transform-style: preserve-3d;
			transform-style: preserve-3d;
			-webkit-transform: rotatey(0deg) translatex(0px) translatey(0px);
			transform: rotatey(0deg) translatex(0px) translatey(0px);
			-webkit-transition: all 0.6s cubic-bezier(0.68, -0.55, 0.265, 1.55);
			transition: all 0.6s cubic-bezier(0.68, -0.55, 0.265, 1.55);
			box-shadow: 6px 6px 20px rgba(0, 0, 0, 0.4);
			margin: 5px;
			/*margin-left: 5em;*/
			cursor: pointer;
			border-radius: 8%;
		}
		
		.element-card:hover {
			/*   width: 16em; */
			/*   height: 21em; */
			-webkit-transform: rotatey(-180deg) translatex(0px) translatey(0px);
			transform: rotatey(-180deg) translatex(0px) translatey(0px);
		}
		
		.element-card .front-facing {
			-webkit-transform: rotateY(0deg) translateZ(2px);
			transform: rotateY(0deg) translateZ(2px);
			position: absolute;
			top: 0;
			left: 0;
			bottom: 0;
			right: 0;
			padding: 0.7em;
			border-radius: 8%;
			background: linear-gradient(150deg, #ffffff 0%, #ffffff 100%);
			filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#ffffff',
				endColorstr='#ffffff', GradientType=1);
		}
		
		.element-card .front-facing .title {
			width: 100%;
			position: absolute;
			top: 55%;
			left: 0;
			text-transform: uppercase;
			font-size: 1.8em;
			margin: 2.2em 0 0 0;
			text-align: center;
			color: #696969;
		}
		
		.element-card .front-facing .cost_l {
			position: absolute;
			bottom: 0.8em;
			right: 1em;
			font-size: 1.3em;
			color: #0000ff;
		}
		
		.element-card .front-facing .cost_m {
			position: absolute;
			bottom: 0.8em;
			left: 1em;
			font-size: 1.3em;
			color: #800000;
		}
		
		.element-card .back-facing {
			-webkit-transform: rotateY(180deg) translateZ(0px);
			transform: rotateY(180deg) translateZ(0px);
			display: -webkit-box;
			display: -ms-flexbox;
			display: flex;
			-webkit-box-orient: vertical;
			-webkit-box-direction: normal;
			-ms-flex-direction: column;
			flex-direction: column;
			-ms-flex-wrap: nowrap;
			flex-wrap: nowrap;
			-webkit-box-pack: center;
			-ms-flex-pack: center;
			justify-content: center;
			-webkit-box-align: center;
			-ms-flex-align: center;
			align-items: center;
			position: absolute;
			top: 0;
			left: 0;
			bottom: 0;
			right: 0;
			border-radius: 8%;
			padding: 0.8em;
			text-align: center;
			background: linear-gradient(150deg, #ffffff 0%, #ffffff 100%);
			filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#ffffff',
				endColorstr='#ffffff', GradientType=1);
		}
		
		.front-facing img {
			width: 220px;
			height: 220px;
			max-width: 220px;
			max-height: 220px;
			display: block;
			margin: auto;
			border-radius: 0.8em;
		}
		
		.panel.with-nav-tabs .panel-heading {
			padding: 5px 5px 0 5px;
		}
		
		.panel.with-nav-tabs .nav-tabs {
			border-bottom: none;
		}
		
		.panel.with-nav-tabs .nav-justified {
			margin-bottom: -1px;
		}
		
		.with-nav-tabs.panel-primary .nav-tabs>li>a, .with-nav-tabs.panel-primary .nav-tabs>li>a:hover,
			.with-nav-tabs.panel-primary .nav-tabs>li>a:focus {
			color: #fff;
		}
		
		.with-nav-tabs.panel-primary .nav-tabs>.open>a, .with-nav-tabs.panel-primary .nav-tabs>.open>a:hover,
			.with-nav-tabs.panel-primary .nav-tabs>.open>a:focus, .with-nav-tabs.panel-primary .nav-tabs>li>a:hover,
			.with-nav-tabs.panel-primary .nav-tabs>li>a:focus {
			color: #fff;
			background-color: #3071a9;
			border-color: transparent;
		}
		
		.with-nav-tabs.panel-primary .nav-tabs>li.active>a, .with-nav-tabs.panel-primary .nav-tabs>li.active>a:hover,
			.with-nav-tabs.panel-primary .nav-tabs>li.active>a:focus {
			color: #428bca;
			background-color: #fff;
			border-color: #428bca;
			border-bottom-color: transparent;
		}
		
		.with-nav-tabs.panel-primary .nav-tabs>li.dropdown .dropdown-menu {
			background-color: #428bca;
			border-color: #3071a9;
		}
		
		.with-nav-tabs.panel-primary .nav-tabs>li.dropdown .dropdown-menu>li>a {
			color: #fff;
		}
		
		.with-nav-tabs.panel-primary .nav-tabs>li.dropdown .dropdown-menu>li>a:hover,
			.with-nav-tabs.panel-primary .nav-tabs>li.dropdown .dropdown-menu>li>a:focus
			{
			background-color: #3071a9;
		}
		
		.with-nav-tabs.panel-primary .nav-tabs>li.dropdown .dropdown-menu>.active>a,
			.with-nav-tabs.panel-primary .nav-tabs>li.dropdown .dropdown-menu>.active>a:hover,
			.with-nav-tabs.panel-primary .nav-tabs>li.dropdown .dropdown-menu>.active>a:focus
			{
			background-color: #4a9fe9;
		}
		
		.btn3d {
			position: relative;
			top: -6px;
			border: 0;
			transition: all 40ms linear;
			margin-top: 10px;
			margin-bottom: 10px;
			margin-left: 2px;
			margin-right: 2px;
		}
		
		.btn3d:active:focus, .btn3d:focus:hover, .btn3d:focus {
			-moz-outline-style: none;
			outline: medium none;
		}
		
		.btn3d:active, .btn3d.active {
			top: 2px;
		}
		
		.btn3d.btn-success {
			box-shadow: 0 0 0 1px #31c300 inset, 0 0 0 2px rgba(255, 255, 255, 0.15)
				inset, 0 8px 0 0 #5eb924, 0 8px 8px 1px rgba(0, 0, 0, 0.5);
			background-color: #78d739;
		}
		
		.btn3d.btn-success:active, .btn3d.btn-success.active {
			box-shadow: 0 0 0 1px #30cd00 inset, 0 0 0 1px rgba(255, 255, 255, 0.15)
				inset, 0 1px 3px 1px rgba(0, 0, 0, 0.3);
			background-color: #78d739;
		}
		
		.btn3d.btn-info {
			box-shadow: 0 0 0 1px #00a5c3 inset, 0 0 0 2px rgba(255, 255, 255, 0.15)
				inset, 0 8px 0 0 #348FD2, 0 8px 8px 1px rgba(0, 0, 0, 0.5);
			background-color: #39B3D7;
		}
		
		.btn3d.btn-info:active, .btn3d.btn-info.active {
			box-shadow: 0 0 0 1px #00a5c3 inset, 0 0 0 1px rgba(255, 255, 255, 0.15)
				inset, 0 1px 3px 1px rgba(0, 0, 0, 0.3);
			background-color: #39B3D7;
		}
		
		.btn3d.btn-danger {
			box-shadow: 0 0 0 1px #b93802 inset, 0 0 0 2px rgba(255, 255, 255, 0.15)
				inset, 0 8px 0 0 #AA0000, 0 8px 8px 1px rgba(0, 0, 0, 0.5);
			background-color: #D73814;
		}
		
		.btn3d.btn-danger:active, .btn3d.btn-danger.active {
			box-shadow: 0 0 0 1px #b93802 inset, 0 0 0 1px rgba(255, 255, 255, 0.15)
				inset, 0 1px 3px 1px rgba(0, 0, 0, 0.3);
			background-color: #D73814;
		}
		
		.radio-button label {
			padding: 6px 12px;
			margin-bottom: 0px;
		}
		
		.radio-button .pagination>li>a {
			padding: 0px;
		}
		
		.radio-button input[type="radio"] {
			width: 0px;
			height: 0px;
			position: absolute;
			color: transparent;
			visibility: hidden;
		}
		
		#shoppingCar {
			width: 100%;
			border: none;
		}
		
		.xdsoft_datetimepicker .xdsoft_datepicker {
			width: 300px; /* width:  300px; */
		}
		
		.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
			height: 151px; /* height:  151px; */
		}
		
		.friendName {
			margin-top: 3px;
			text-align: center;
		}
		
		#groupBuyChatBoxContainer {
		  position: fixed;
		  bottom: 30px;
		  left: 57px;
		}

		.top_menu {
		  background-color: #1e90ff;
		  width: 100%;
		  padding: 5px 0 5px;
		}

		.top_menu .title {
		  text-align: center;
		  color: #bcbdc0;
		  font-size: 2em;
		}

		.messages {
		  position: relative;
		  list-style: none;
		  padding: 20px 10px 0 10px;
		  margin: 0;
		  height: 347px;
		  overflow-y: scroll;
		}
		.messages .message {
		  clear: both;
		  overflow: hidden;
		  margin-bottom: 20px;
		  transition: all 0.5s linear;
		  opacity: 0;
		}

		.messages .message.left .avatar {
		  width: 60px;
		  height: 60px;
		  border-radius: 50%;
		  background-size: 100%;
		  display: inline-block;
		  float: left;
		}

		.messages .message.left .text_wrapper {
		  background-color: #ffe6cb;
		  margin-left: 20px;
		}
		.messages .message.left .text_wrapper::after, .messages .message.left .text_wrapper::before {
		  right: 100%;
		  border-right-color: #ffe6cb;
		}
		.messages .message.left .text {
		  color: #c48843;
		}
		.messages .message.right .avatar {
		  width: 60px;
		  height: 60px;
		  border-radius: 50%;
		  background-size: 100%;
		  display: inline-block;
		  float: right;
		}
		.messages .message.right .text_wrapper {
		  background-color: #c7eafc;
		  margin-right: 20px;
		  float: right;
		}
		.messages .message.right .text_wrapper::after, .messages .message.right .text_wrapper::before {
		  left: 100%;
		  border-left-color: #c7eafc;
		}
		.messages .message.right .text {
		  color: #45829b;
		}
		.messages .message.appeared {
		  opacity: 1;
		}

		.messages .message .text_wrapper {
		  display: inline-block;
		  padding: 20px;
		  border-radius: 6px;
/* 		  width: calc(100% - 130px); */
/* 		  min-width: 100px; */
		  position: relative;
		}
		.messages .message .text_wrapper::after, .messages .message .text_wrapper:before {
		  top: 18px;
		  border: solid transparent;
		  content: " ";
		  height: 0;
		  width: 0;
		  position: absolute;
		  pointer-events: none;
		}
		.messages .message .text_wrapper::after {
		  border-width: 13px;
		  margin-top: 0px;
		}
		.messages .message .text_wrapper::before {
		  border-width: 15px;
		  margin-top: -2px;
		}

		.messages .message .text_wrapper .text {
		  font-size: 18px;
		  font-weight: 300;
		}

		.bottom_wrapper {
		  position: relative;
		  background-color: #fff;
		  padding: 5px 5px;
		}

		.bottom_wrapper .message_input_wrapper {
		  display: inline-block;
		  height: 50px;
		  border-radius: 25px;
		  border: 1px solid #bcbdc0;
		  width: calc(100% - 150px);
		  position: relative;
		  padding: 0 20px;
		  margin-left: 0.4em;
		}

		.bottom_wrapper .message_input_wrapper .message_input {
		  border: none;
		  height: 100%;
		  box-sizing: border-box;
		  width: calc(100% - 40px);
		  position: absolute;
		  outline-width: 0;
		  color: gray;
		}

		.bottom_wrapper .send_message {
		  width: 100px;
		  height: 50px;
		  display: inline-block;
		  border-radius: 50px;
		  background-color: #a3d063;
		  border: 2px solid #a3d063;
		  color: #fff;
		  cursor: pointer;
		  transition: all 0.2s linear;
		  text-align: center;
		  float: right;
		}

		.bottom_wrapper .send_message:hover {
		  color: #a3d063;
		  background-color: #fff;
		}

		.bottom_wrapper .send_message .text {
		  font-size: 18px;
		  font-weight: 300;
		  display: inline-block;
		  line-height: 48px;
		}

		.message_template {
		  display: none;
		}

		.sendImg {
			width: 40px;
		    height: 40px;
		    margin-top: 0.4em;
		    display: inline-block;
		    float: left;
		}
		
		.reciveImg {
			max-width: 120px;
			max-height: 120px;	
		}
		
		.chatImg {
			bottom: 50px;
 			width: 65px; 
 			height: 65px; 
			margin-bottom: 3em;
  			display: inline-block;  
 			float: left;
		}
		
		.groupBuyNewTalk {
			width: 35px;
			left: -10px;
	 		position: absolute;
	 		margin-bottom: 3.5em;
 	 	 	display: none;  
		}
		
		.previewImg {
			dispaly: inline-block;
			max-width: 300px;
			max-height: 300px;
		}
	
		.inputIMG {
			display: inline-block;
			float: left;
		}
		
		.errMessage {
			display: block;
			margin: auto;
		}
		
		.sizeGroup {
			margin-top: 1em;
			margin-bottom: 1em;
		    font-size: 1.1em;
		}
		
		#pickUpChoose {
			padding: 5px;
			background-color: #C4E1E1;
		}
		
		#payment {
			padding: 5px;
			background-color: #FFDAC8;
		}
		
		#payment img {
			max-width: 50px;
			max-height: 50px
		}
		
		#payment label {
			padding: 0 15px;
		}
		
		#myCards {
			padding: 5px;
			background-color: #C4E1FF;
		}
		
		#myCoupons {
			padding: 5px;
			background-color: #CEFFCE;
		}
		
		#chooseAgain , #sendOrderBtn {
			display:inline-block;
		}
		
		#btnGroup {
			text-align: center;
		}
		
		#groupBuyContainer {
		  position: absolute; 
		  width: 150px;
		  bottom: 45vh;
		  left: 20px;
		}
		
		#groupBuyInvite {
		  position: absolute; 
		  width: 150px;
		  bottom: 60vh;
		  left: 20px;
		}
		
		.container {
			margin-top: 1em;
		}
		
		#errorMsgsModal {
			margin:auto;
		}
		
		#getGroupBuyInfoBtn img {
			width:40%;
			display: block;
			margin: auto;
		}
		
		#groupBuyInvite img {
			width:60%;
			display: block;
			margin: auto;
		}
		
		.groupBuyChatWindow {
		  width: 30em;
		  height: 35em;
		  border-radius: 10px;
		  background-color: #fff;
		  display: none;
		  vertical-align: bottom;
		  position: relative;
		  margin: 0 5px;
		  background-color: #e0ffff;
		  overflow: hidden;
		}
		
		.modal-open .modal, a:active, a:focus, a:link, a:visited{
		    outline:none!important;
		    border: none;
			text-decoration: none!important;
			-moz-outline-style: none;
		}
		
		#groupBuyInvite p, #groupBuyContainer p {
			font-weight: bold;
		}
		
		.isRead {
			display: inline-block;
			float: right;
			margin-right: 5px;			
		}
		
		.carousel-inner img{
			max-height: 220px;
		}
		
</style>

<script>

	var MyPoint = "/FriendListChatServer/${meror_num}";
	var host = window.location.host;
	var path = window.location.pathname;
	var webCtx = path.substring(0, path.indexOf('/', 1));
	var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
	var webSocket;
	var readTrueOrFalse = false;
	
	function connect() {
		
		webSocket = new WebSocket(endPointURL);
		
		webSocket.onopen = function(event) {
			updateStatus("WebSocket 成功連線");
		};
		
		webSocket.onmessage = function(event) {
			
	        var jsonObj = JSON.parse(event.data);
	        
	        if ("groupBuyTalk" == jsonObj.action) {
	        	
				var messageOrSrc;
				
	        	var targetMessages = $(".messages");
	        	
	        	var $message = $(targetMessages.parent().next().clone().html());
	        	
	        	if (jsonObj.message != null) {
					
					messageOrSrc = jsonObj.message + "\r\n";
						
				} else if (jsonObj.sendImg != null) {
					
					var src = jsonObj.sendImg;
					
					messageOrSrc = "<img src='" + src + "' class='reciveImg'>";
					
				}
	        	
	        	if (jsonObj.senderNumber == "${mem_num}") {  //sendBackToMyself
					
	                $message.addClass('right').find('.text').html(messageOrSrc);
	        		
	                targetMessages.append($message);
	                
		        	if ($(".groupBuyChatWindow").css("display") == 'none') {
		        		
		        		$(".groupBuyNewTalk").css("display", "inline-block");
		        		
		        	}
		        	
					var src = "<%=request.getContextPath()%>/getPic?mem_num=${mem_num}";
	        		
	        		$(".groupBuyChatWindow").find('.right').find('.avatar').last().attr('src', src);
	        		
	        	} else {
	        		
	                $message.addClass('left').find('.text').html(messageOrSrc);
	        		
	                targetMessages.append($message);
	                
					if ($(".groupBuyChatWindow").css("display") == "none") {
						
						$(".groupBuyNewTalk").css("display", "inline-block");
						
						readTrueOrFalse = true;
		        		
		        	} else {
		        		
		        		var radtime = 300 * Math.random();
		        		
		        		setTimeout(function () {
		        			
		        			sendMessage('groupBuysendIsRead', 'Y', jsonObj.merOr, jsonObj.mySessionID);
			
			            }, radtime);
		        		
		        	}
					
					var src = "<%=request.getContextPath()%>/getPic?mem_num=" + jsonObj.senderNumber;
					
	        		$(".groupBuyChatWindow").find('.left').find('.avatar').last().attr('src', src);
	        		
	        	}
	        	
	        	setTimeout(function () {
	
	                return $message.addClass('appeared');
	
	            }, 100);
	        	
	        	targetMessages.animate({ scrollTop: targetMessages.prop('scrollHeight') }, 300);
	        	
	        } else if ("groupBuysendIsRead" == jsonObj.action) {
	        	
	        	var isReadCounter =  $(".groupBuyChatWindow").find(".right").find(".isRead");
	        	
				if ('Y' == jsonObj.message) {
	        		
					isReadCounter.html('(已讀)');
	        		
	        	} 
				
	        }
	        
		}
	
		webSocket.onclose = function(event) {
			updateStatus("WebSocket 已離線");
		};
		
	}
	
	function sendMessage(action, message, target, targetSessionID) {
		
		if ('groupBuyTalk' == action) {
			
			if (message.trim() == '') {
	            return;
	        }
			   
			var jsonObj = {
							"action"       : action,
							"senderNumber" : '${mem_num}',
							"message"      : message,
							"merOr"        : target };
		
		    webSocket.send(JSON.stringify(jsonObj));
		    
		} else if ('groupBuysendIsRead' == action) {
			
			var jsonObj = {
							"action"          : action,
							"message"         : message,
							"merOr"           : target,
							"senderNumber"    : '${mem_num}',
							"targetSessionID" : targetSessionID};
			
   			webSocket.send(JSON.stringify(jsonObj));
			
		} else if ('GroupBuySendImg' == action) {
			
			var jsonObj = {
							"action"       : action,
							"senderNumber" : '${mem_num}',
							"message"      : message,
							"merOr"        : target };
			
   			webSocket.send(JSON.stringify(jsonObj));
   			
		}
		
	}
	
	function disconnect () {
		webSocket.close();
	}
	
	function updateStatus(newStatus) {
	}

	$(document).ready(function() {
		
			$('#errorMsgsModal').modal('show');
		
			//for GroupBuy Invite
			$(".img-check").click(function() {
				$(this).parent().find("img.friendcheck").toggle();
			});

			//for Counter
			$(".counter").on("change", function() {
				var counter = $(this).parent().find("input.counter")[0];
				var i = parseInt(counter.value);
				if (i < 1) {
					counter.value = 1;
				}
			});

			//for Counter
			$(".cut").click(function() {
				var counter = $(this).parent().find("input.counter")[0];
				var i = parseInt(counter.value);
				if (i > 1) {
					counter.value = i - 1;
				} else {
					counter.value = 1;
				}
			});

			//for Counter
			$(".add").click(function() {
				var counter = $(this).parent().find("input.counter")[0];
				var i = parseInt(counter.value);
				counter.value = i + 1;
			});

			//checkBoxStyle
			$('.button-checkbox').each(function() {

			// Settings
			var $widget = $(this), 
			$button = $widget.find('button'), 
			$checkbox = $widget.find('input:checkbox'), 
			color = $button.data('color'),
			settings = {
				on : {
					icon : 'glyphicon glyphicon-check'
				},
				off : {
					icon : 'glyphicon glyphicon-unchecked'
				}
			};

			// Event Handlers
			$button.on('click',function() {
				$checkbox.prop('checked', !$checkbox.is(':checked'));
				$checkbox.triggerHandler('change');
				updateDisplay();
			});
											
			$checkbox.on('change', function() {
				updateDisplay();
			});

			// Actions
			function updateDisplay() {
				var isChecked = $checkbox.is(':checked');

				// Set the button's state
				$button.data('state', (isChecked) ? "on" : "off");

				// Set the button's icon
				$button.find('.state-icon').removeClass().addClass('state-icon ' + settings[$button.data('state')].icon);

												// Update the button's color
						if (isChecked) {
							$button.removeClass('btn-default').addClass('btn-'+ color+ ' active');
						} else {
							$button.removeClass('btn-'+ color + ' active').addClass('btn-default');
						}
				}

											// Initialization
											function init() {

												updateDisplay();

												// Inject the icon if applicable
												if ($button.find('.state-icon').length == 0) {
													$button.prepend('<i class="state-icon ' + settings[$button.data('state')].icon + '"></i>');
												}
											}
											
											init();
										});

					$("#getGroupBuyInfoBtn").click( function() {

						var xhr = new XMLHttpRequest();
						xhr.onreadystatechange = function() {

							if (xhr.readyState == 4) {
								if (xhr.status == 200) {
	
									$("#groupBuyInfo").empty();
									$("#groupBuyInfo").append(xhr.responseText);
	
								} else {
									alert("not found");
								}
							}

						};

						var url = "<%=request.getContextPath()%>/OrderMaster/OMC.html";
						xhr.open("POST", url, true);
						xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
						xhr.send("action=GetGroupBuyInfo");

					});
						
						
					$('.top_menu').click(function(){
							
//					 	$(this).parent().hide();

						$(this).parent().css("display", "none");
							
						$(".chatImg").css("display", "inline-block");
					});
						
					$(".chatImg").click(function(){
							
//					 	$(this).parent().hide();

						$(this).css("display", "none");
							
						$(".groupBuyNewTalk").css("display", "none");
							
						$(".groupBuyChatWindow").css("display", "inline-block");
							
						$(".messages").animate({ scrollTop: $(".messages").prop('scrollHeight') }, 300);
							
						if (readTrueOrFalse) {
								
							sendMessage('groupBuysendIsRead', 'Y', '${meror_num}');
								
							readTrueOrFalse = false;
						}
					});
						
						
					$('.send_message').click(function (e) {
							
						var messageBoard = $(this).prev().find(".message_input");
							
						sendMessage('groupBuyTalk', messageBoard.val(), '${meror_num}'); 
							
						messageBoard.val('');
					});
					
			$('.message_input').keyup(function (e) {
					
				 if (e.which === 13) {
					        	
					 sendMessage('groupBuyTalk', $(this).val(), '${meror_num}'); 
					        	
					 $(this).val('');
				 }
			});
			
			var reader = new FileReader();		    
			$(".inputIMG").change(function(e) {
				
				var goal = $(this);
				
				reader.onload = function(e) {
					
					goal.next().attr('src', e.target.result);
					
				}
				
				reader.readAsDataURL(e.target.files[0]);
			});
		    
		    $(".validation").click(function(){
		    	
		    	if ($(this).parent().prev().find(".inputIMG").prop('files').length > 0) {
		    		
		    		sendMessage('GroupBuySendImg', reader.result, '${meror_num}'); 
		    		
		            $(this).parent().prev().find(".inputIMG").val('');
		    		
		    	}
		    	
		    });
			
					    
			$('input[type=radio][name=pickUpType]').change(function() {
					    	
				if (this.value == '外送') {
					        	
					 var address = "<span id='sendAdrress'> 外送地址: <input type='text' pattern='.{3,}' required title='3 characters minimum' name='ADDRESS'> ex:台北市中山路10號</span>";
					        	
					$("#pickUpChoose").find($('input[type=text][name=ADDRESS]')).remove();
					$("#pickUpChoose").append(address);
					        	
				} else {
					        	
					$('#sendAdrress').remove();
				}
			});
					    
			$('#chooseAgain').click(function(){
					    	
				$('#sendAdrress').remove();
					    	
			});
					    
	});
</script>

</head>
<body onload="connect();" onunload="disconnect();">

	<jsp:include page="/front-end/member_top.jsp" />

	<!-- ================================Body======================================= -->
	<div class="container">
		<div class="row">
			<!-- =============================Left-Part==================================== -->
			<div class="col-xs-12 col-sm-8 left-part">
				<!-- =============================Section1-L=================================== -->
				<div class="container col-sm-12 section1-L">
					<div class="row">
						<div class="col-sm-12 titlePic">
						
							<div id="carousel-id" class="carousel slide" data-ride="carousel">
							    <!-- 幻燈片小圓點區 -->
							    <ol class="carousel-indicators">
							    	<c:forEach var="storeImageVO" items="${storeImgService.getStoreImageNum(currentSto_num)}" varStatus = "s">
							    		<li data-target="#carousel-id" data-slide-to="${s.index}" class = "${s.first?'active':''}"></li>
							        </c:forEach>
							    </ol>
							    <!-- 幻燈片主圖區 -->
							    <div class="carousel-inner">				
							    	<c:forEach var="storeImageVO" items="${storeImgService.getStoreImageNum(currentSto_num)}" varStatus = "s">
								        <c:if test = "${s.index==0}" var="result">
								        	<div class="item active">
								        </c:if>
								        <c:if test = "${!result}" >
								       		<div class="item">
								        </c:if>
								       
								            	<img src="<%=request.getContextPath()%>/GetPicShawn?sto_num=${storeImageVO.getSto_num()}&img_num=${storeImageVO.getImg_num()}&getType=storeImage">
								   			</div>
								     </c:forEach>
								 </div>
								 <a class="left carousel-control" href="#carousel-id" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a>
		    					 <a class="right carousel-control" href="#carousel-id" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
							 </div>
							
						</div>
					</div>
				</div>
				<!-- =============================Section1-L=================================== -->
				<!-- =============================Section2-L=================================== -->
				<div class="container col-sm-12 section2-L">
					<div class="row">

						<div class="panel with-nav-tabs panel-primary" style="border: 1px solid #3c9682;">
							<div class="panel-heading" style="background-color:#3c9682;">
								<ul class="nav nav-tabs">
									<c:forEach var="proType" items="${proTypeService.getProtypes(currentSto_num)}" varStatus="p">
										<li class="${p.count==1?'active':''}">
											<a href="#${proType.pt_num}" data-toggle="tab">${proType.pt_name}</a>
										</li>
									</c:forEach>
								</ul>
							</div>

							<div class="panel-body">
								<div class="tab-content">
									<c:forEach var="proTypesContent" items="${proTypeService.getProtypes(currentSto_num)}" varStatus="s">
										<div class="tab-pane fade ${s.count==1?'in active':''}" id="${proTypesContent.pt_num}">

											<c:forEach var="products" items="${productService.getProducts(currentSto_num, proTypesContent.pt_num)}" varStatus="po">

												<div class="col-sm-6">

													<form action="<%=request.getContextPath()%>/OrderDetail/ODC.html" method="post" target="shoppingCar" class="addOneOrder">

														<div class="element-card">
															<div class="front-facing">
																<img src="<%=request.getContextPath()%>/getPic?com_num=${products.com_num}">
																<b class="title">${products.com_name}</b> 
																<span class="cost_l">大杯:${products.l_price}元</span> 
																<span class="cost_m">中杯:${products.m_price}元</span>
																<input type="hidden" name="com_num" value="${products.com_num}">
															</div>

															<div class="back-facing">

																<table>
																	<tr>
																		<td rowspan="3">
																			<c:forEach var="extras" items="${extraService.getExtras(currentSto_num)}" varStatus="ext">
																				<span class="button-checkbox">
																					<button type="button" class="btn" data-color="success">${extras.ext_name}+${extras.ext_amount}元</button>
																					<input type="checkbox" class="hidden" name="extra" value="${extras.ext_num}" />
																				</span>
																			</c:forEach></td>
																		<td><select class="selcls" name="ice">
																				<c:forEach var="iceList" items="${iceListService.getIceList(currentSto_num)}">
																					<option value="${iceList.ice_num}">${iceList.ice_type}</option>
																				</c:forEach>
																		</select></td>
																	</tr>
																	<tr>
																		<td>
																			<select class="selcls" name="sweet">
																				<c:forEach var="sweetList" items="${sweetService.getSweetness(currentSto_num)}">
																					<option value="${sweetList.sweet_num}">${sweetList.sweet_type}</option>
																				</c:forEach>
																			</select>
																		</td>
																	</tr>
																	<tr>
																		<td>
																			<select class="selcls" name="isKeep">
																				<option value="notkeep">不寄杯</option>
																				<option value="keep">寄杯</option>
																			</select>
																		</td>
																	</tr>
																	<tr>
																		<td colspan="2">
																			<div class="btn-group sizeGroup">
																				<label for="M${products.com_num}" class="radio-inline">
																					<input class="size" id="M${products.com_num}" type="radio" name="size${products.com_num}" value="M${products.m_price}" checked="checked" />中杯
																				</label>
																				<label for="L${products.com_num}" class="radio-inline">
																					<input class="size" id="L${products.com_num}" type="radio" name="size${products.com_num}" value="L${products.l_price}" />大杯
																				</label>
																				<input type="hidden" name=size value="">
																			</div>
																		</td>
																	</tr>
																	<tr>
																		<td colspan="2">數量:
																			<div class="input-group">
																				<button type="button" class="cut btn btn-danger btn-sm btn3d addone">
																					<span class="glyphicon glyphicon-minus"></span>
																				</button>
																				<input type="text" id="counter" class="counter text-center" name="total" value="1" style="width: 25%; border: none;" readonly>
																				<button type="button" class="add btn btn-info btn-sm btn3d addone">
																					<span class="glyphicon glyphicon-plus"></span>
																				</button>
																			</div>
																		</td>
																	</tr>
																	<tr>
																		<td colspan="2">
																			<button type="submit" class="btn btn-success btn-lg btn3d addone" name="action" value="AddOne">
																				<span class="glyphicon glyphicon-plus"></span>
																			</button>
																		</td>
																	</tr>

																</table>

															</div>
														</div>
													</form>
												</div>
											</c:forEach>

										</div>
									</c:forEach>

								</div>
							</div>
						</div>

					</div>
				</div>
				<!-- =============================Section2-L=================================== -->
			</div>
			<!-- =============================Left-Part===================================== -->

			<!-- =============================Right-Part==================================== -->
			<div class="col-xs-12 col-sm-4 right-part">
				<iframe height="925" name="shoppingCar" id="shoppingCar" src="<%=request.getContextPath()%>/front-end/Order_Detail/OrderDetail.jsp">
				</iframe>
			</div>
			<!-- =============================Right-Part==================================== -->
		</div>
	</div>
	<!-- ================================Body======================================= -->


	<div class="modal fade" id="orderModal" role="dialog">
		<div class="modal-dialog">

			<div class="modal-content">

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3 class="modal-title">訂購選項</h3>
				</div>

				<form action="<%=request.getContextPath()%>/OrderMaster/OMC.html" method="post" id="sendOrder">
					<div class="modal-body text-center">

						<c:if test="${meror_num == null || mergedOrderService.getMergedOrder(meror_num).getMem_num() == mem_num}">
							<div id="pickUpChoose">
								<h4>取貨方式:</h4> 
								<label for="self"> 
									<input type="radio"name="pickUpType" value="自取" id="self" checked="checked">自取
								</label> 
								<label for="delivery"> 
									<input type="radio" name="pickUpType" value="外送" id="delivery">外送
								</label>
							</div>
						</c:if>
						
						<div id="payment">
							<h4>付款方式: (我的點數:${memberProfileService.getMyProfile(mem_num).getRem_point()})</h4>
							<label for="cash"> 
								<input type="radio" name="payment" value="現金" id="cash" checked="checked">現金
								<img alt="" src="<%=request.getContextPath()%>/img/cash2.png">
							</label>
							<label for="point"> 
								<input type="radio" name="payment" value="點數" id="point">點數
								<img alt="" src="<%=request.getContextPath()%>/img/coins.png">
							</label>
						</div>
						
						<c:if test="${cardListService.getMyCard(currentSto_num, mem_num).size() > 0}">
							<div id="myCards">
								<h4>我的集點卡:</h4>
								<c:forEach var="myCards" items="${cardListService.getMyCard(currentSto_num, mem_num)}">
									<input type="radio" name="myCards" value="${myCards.getCard_num()}">可折價${cardService.getOneCradInfo(myCards.getCard_kinds()).getPoints_cash()}元
	        						<input type="hidden" name="cardType" value="${myCards.getCard_kinds()}">
								</c:forEach>
							</div>
						</c:if>
								
						<c:if test="${couponListService.getMyCoupons(mem_num, currentSto_num).size() > 0}">
							<div id="myCoupons">
								<h4>我的折價卷:</h4>
								<c:forEach var="myCoupons" items="${couponListService.getMyCoupons(mem_num, currentSto_num)}">
									<input type="radio" name="myCoupons" value="${myCoupons.getCoupon_amount()}">可折價${couponService.getOneCoupon(myCoupons.getCoupon_num()).getCoupon_cash()}元
	        						<input type="hidden" name="couponType" value="${myCoupons.getCoupon_num()}">
								</c:forEach>
							</div>
						</c:if>	

					</div>

					<div class="modal-footer">
						<div id="btnGroup">
							<button type="submit" class="btn btn-info" id="sendOrderBtn" name="action" value="ConfirmOrder">我要結帳</button>
							<button type="reset" class="btn btn-danger" id="chooseAgain">重新選擇</button>
						</div>
					</div>

				</form>

			</div>

		</div>
	</div>


	<jsp:include page="/front-end/member_foot.jsp" flush="true" />

<!-- =============================GroupBuy-FriendList==================================== -->
	<div id="groupBuyInvite">
		<a href="" data-toggle="modal" data-target="#ModalforInvite">
			<img src="<%=request.getContextPath()%>/img/group.jpg">
			<p class="text-center">邀請好友團購</p>
		</a>
	</div>

	<div class="modal fade" id="ModalforInvite" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3 class="modal-title">邀請好友共同購買</h3>
				</div>

				<form action="<%=request.getContextPath()%>/MergedOrder/MOC.html" method="post">
					<div class="modal-body container-fluid gbFriendList">
						<div class="row">
							<div class="form-group">

								<c:forEach var="memberVO" items="${friendList.getFirends(mem_num)}" varStatus="f">
									<div class="col-sm-4 friends">
										<c:if test="${hadInvited != null && hadInvited.contains(memberVO.mem_num)}" var="ishadInvited">
											<div class="" style="padding: 0">
												<img src="<%=request.getContextPath()%>/getPic?mem_num=${memberVO.mem_num}" class=""> 
												<img src="<%=request.getContextPath()%>/img/LOGO_150x150.png" class="friendcheck">
											</div>
										</c:if>
										<c:if test="${!ishadInvited}">
											<label class="btn" style="padding: 0"> 
												<img src="<%=request.getContextPath()%>/getPic?mem_num=${memberVO.mem_num}" class="img-check"> 
												<img src="<%=request.getContextPath()%>/img/LOGO_150x150.png" class="friendcheck img-check" style="display: none;">
												<input type="checkbox" name="invite_friends" id="" value="${memberVO.mem_num}" class="hidden">
											</label>
										</c:if>
										<h3 class="friendName">${memberVO.mem_name}</h3>
									</div>
								</c:forEach>

							</div>
						</div>
					</div>

					<div class="modal-footer">
						<button type="submit" class="btn btn-info center-block" name="action" value="Invite">送出邀請</button>
					</div>
				</form>
			</div>
		</div>
	</div>
<!-- =============================GroupBuy-FriendList==================================== -->
<!-- =============================GroupBuy-Info========================================== -->
	<c:if test="${meror_num != null}">
		<div class="modal fade" id="ModalforGroupBuyInfo" role="dialog">
			<div class="modal-dialog modal-lg">
				<div class="modal-content" id="groupBuyInfo">
				
				</div>
			</div>
		</div>
		
		<div id="groupBuyContainer">
			<a href="" id="getGroupBuyInfoBtn" data-toggle="modal" data-target="#ModalforGroupBuyInfo"> 
				<img src="<%=request.getContextPath()%>/img/groupbuyinfo.png">
				<p class="text-center">團購資訊</p>
			</a>
		</div>
		
		<div id="groupBuyChatBoxContainer">
			<img class="chatImg" alt="" src="<%=request.getContextPath()%>/img/messageImg.png">
			<img src="<%=request.getContextPath()%>/img/gbtalkNew.png" class="groupBuyNewTalk">
			<div class='groupBuyChatWindow'><div class='top_menu'><div class='title'>團購聊天室</div></div><ul class='messages'></ul><div class='bottom_wrapper clearfix'><button class='sendImg btn btn-info' data-toggle='modal' href='#modal-photo' type='button'><span class='glyphicon glyphicon-picture'></span></button><div class='message_input_wrapper'><input class='message_input' placeholder='請輸入訊息' /></div><div class='send_message'><div class='icon'></div><div class='text'>送出</div></div></div></div><div class='message_template'><li class='message'><img class='avatar'><div class='text_wrapper'><div class='text'></div></div><div class='isRead'></div></li></div>
		</div>
		<div class='modal fade' id='modal-photo'><div class='modal-dialog'><div class='modal-content'><div class='modal-header'><button type='button' class='close' data-dismiss='modal'>x</button><h4 class='modal-title text-center'>選擇圖片</h4></div><div class='modal-body'><input type='file' name ='photo' class='inputIMG' accept='image/*'><img class='previewImg'></div><div class='modal-footer'><button class='btn btn-primary validation'>送出</button></div></div></div></div>
	
	</c:if>
<!-- =============================GroupBuy===================================== -->
<!-- =============================ErrorMsgs==================================== -->
	<c:if test="${errorMsgs.size() > 0}">
		<div class="modal fade" id="errorMsgsModal" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-body">
						<c:forEach var="errorMsg" items="${errorMsgs}" varStatus="em">
							<h2 class="text-center errMessage" style="color:red">${errorMsg}</h2>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</c:if>
<!-- =============================ErrorMsgs==================================== -->
</body>
</html>