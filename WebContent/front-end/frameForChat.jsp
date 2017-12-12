<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%	
	String mem_num = (String) request.getSession().getAttribute("mem_num");
 	if (mem_num == null || (mem_num.trim()).length() <= 0) {
 		
 		response.sendRedirect(request.getContextPath() + "/front-end/index.jsp");
		return;
		
	}
%>

<jsp:useBean id="loginInfo" scope="page" class="login.test.LoginInfo" />
<jsp:useBean id="friendService" scope="page" class="com.friend_list.model.FriendListService" />
<jsp:useBean id="memberProfileService" scope="page" class="com.member_profile.model.MemberProfileService" />

<!DOCTYPE html>
<html lang="">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>揪茶趣:訂飲料好方便</title>
<link rel="icon" href="<%= request.getContextPath() %>/img/favicon.ico" type="image/x-icon" /> 
<link rel="shortcut icon" href="<%= request.getContextPath() %>/img/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/dist/sweetalert2.all.min.js"></script>
<script src="<%=request.getContextPath()%>/js/dist/sweetalert2.min.js"></script>

<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
		
<style>
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

		#chatBoxContainer {
		  position: fixed;
		  bottom: 0;
		  right: 140px;
		}
		
		#myFriendList{
		  position: fixed;
		  width: 50px;
		  bottom: 7em;
		  right: 65px;
		}

		.chat_window {
		  width: 30em;
		  height: 35em;
		  border-radius: 10px;
		  background-color: #fff;
		  display: inline-block;
		  vertical-align: bottom;
		  position: relative;
		  margin: 0 5px;
		  background-color: #e0ffff;
		  overflow: hidden;
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
		
		.previewImg {
			dispaly: inline-block;
			max-width: 300px;
			max-height: 300px;
		}
		
		.inputIMG {
			display: inline-block;
			float: left;
		}
		
        body, html
        {
            overflow: hidden;
        }

        #content
        {
            position:absolute; left: 0; right: 0; bottom: 0; top: 0px; 
        }
        
        #friendList {
        	position: fixed;
			bottom: 50px;
			right: 55px;
 			width: 65px; 
 			height: 65px; 
  			display: inline-block;  
		}
		
		#newTalk {
			position: fixed;
			bottom: 80px;
			right: 10px;
			width: 60px;
			height: 25px;
 	 	 	display: none;  
		}
		
		.friendHead{
			width: 50px;
			height:50px;
			border-radius: 50%;
			margin: 0 0 5px;
			display: none;
			position: relative;
		}
		
		#infomation {
			position: fixed;
			bottom: 9em;
			right: 3em;
			width: 13.5em;
			height: 5em;
			padding: 1.2em;
			text-align: center;
 			background-color: #C4E1E1; 
			border-style:solid;
		    border-width:medium;
			border-radius: 50%;
 	 	 	display: none;  
		}
		
		#infomation b {
			font-size: 1.2em;
		}
		
		.online {
			width:15px;
			height:15px;
			border-radius: 100%;
			position: relative;
			bottom: 55px;
			background-color:#7cfc00;
			display: none;
		}
		
		.offline {
			width:15px;
			height:15px;
			border-radius: 100%;
			position: relative;
			bottom: 55px;
			background-color:#a9a9a9;
			display: none;
		}
		
		.isRead {
			display: inline-block;
			float: right;
			margin-right: 5px;			
		}
		
</style>


<script>

	var MyPoint = "/FriendListChatServer/${mem_num}";
	var host = window.location.host;
	var path = window.location.pathname;
	var webCtx = path.substring(0, path.indexOf('/', 1));
	var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
	var webSocket;
	
	function connect() {
		
		webSocket = new WebSocket(endPointURL);
		
		webSocket.onopen = function(event) {
			
			updateStatus("WebSocket 成功連線");
		};
	
		
		webSocket.onmessage = function(event) {
			
	        var jsonObj = JSON.parse(event.data);
	        
			if ("couponInfo" == jsonObj.action) {
	        	
				var message = jsonObj.message ;    
		          swal({
		       			position: 'center',
		       			title: message,
		       			allowOutsideClick: false,
		       			showConfirmButton: true
		     	      })
		     
	            
	        }
   
		if ("memGetOrderMessage" == jsonObj.action) {
	        	
	            var message = jsonObj.message ;    
		        swal({
					  position: 'center',
					  type: 'success',
					  title: message,
					  allowOutsideClick: false,
					  showConfirmButton: true
					})
	        }
	        

		else if ("memGetOrderErrMessage" == jsonObj.action) {
	        	
	            var message = jsonObj.message ;    
		        swal({
					  position: 'center',
					  type: 'error',
					  title: message,
					  timer: 2500
					})
	        }
	        
	        
	        if ("talkMessage" == jsonObj.action) {
	        	
				var messageOrSrc;
				
	        	var targetMessages;
	        	
	        	if (jsonObj.senderNumber == "${mem_num}") {  //sendBackToMyself
	        		
	        		var $message;
	        	
	        		targetMessages = $("#message" + jsonObj.targetNumber);
	        		
	                $message = $(targetMessages.parent().next().clone().html());
	                
					if (jsonObj.message != null) {
						
						messageOrSrc = jsonObj.message + "\r\n";
							
					} else if (jsonObj.sendImg != null) {
						
						var src = jsonObj.sendImg;
						
						messageOrSrc = "<img src='" + src + "' class='reciveImg'>";
						
					}
					
	                $message.addClass('right').find('.text').html(messageOrSrc);
	        		
	                targetMessages.append($message);
	                
		        	if ($("#chat_window" + jsonObj.targetNumber).css("display") == 'none') {
		        		
		        		$("#newTalk").css("display", "inline-block");
		        		
		        		$('#' + jsonObj.senderNumber).css('border', "solid 3px red");  ;
		        		
		        	}
		        	
					var src = "<%=request.getContextPath()%>/getPic?mem_num=${mem_num}";
	        		
	        		$("#chat_window" + jsonObj.targetNumber).find('.right').find('.avatar').last().attr('src', src);
	        		
	        	} else {
	        		
	                if ($('#message' + jsonObj.senderNumber).length <= 0) {
	                	
	                	creatChatBox(jsonObj.senderName, jsonObj.senderNumber).css("display", "none");
	                	
	                	$("#newTalk").css("display", "inline-block");
	                	
	                	$('#' + jsonObj.senderNumber).css('border', "solid 3px red");
	                	
	                }
	                
					var $message;
					
					targetMessages = $("#message" + jsonObj.senderNumber);
	        		
	                $message = $(targetMessages.parent().next().clone().html());
	                
					if (jsonObj.message != null) {
						
						messageOrSrc = jsonObj.message + "\r\n";
							
					} else if (jsonObj.sendImg != null) {
						
						var src = jsonObj.sendImg;
						
						messageOrSrc = "<img src='" + src + "' class='reciveImg'>";
						
					}
					
	                $message.addClass('left').find('.text').html(messageOrSrc);
	        		
	                targetMessages.append($message);
	                
					if ($("#chat_window" + jsonObj.senderNumber).css("display") == "none") {
						
						sendMessage('sendIsRead', 'N', jsonObj.senderNumber);
		        		
		        		$("#newTalk").css("display", "inline-block");
		        		
		        		$('#' + jsonObj.senderNumber).css('border', "solid 3px red");  ;
		        		
		        	} else {
		        		
		        		sendMessage('sendIsRead', 'Y', jsonObj.senderNumber);
		        		
		        	}
					
					var src = "<%=request.getContextPath()%>/getPic?mem_num=" + jsonObj.senderNumber;
					
	        		$("#chat_window" + jsonObj.senderNumber).find('.left').find('.avatar').last().attr('src', src);
	        		
	        	}
	        	
	        	setTimeout(function () {
	
	                return $message.addClass('appeared');
	
	            }, (300 * Math.random()));
	        	
	        	targetMessages.animate({ scrollTop: targetMessages.prop('scrollHeight') }, 200);
	        	
	        } else if ("friendLogin" == jsonObj.action) {
	        	
	        	var friendNumber = jsonObj.friendNumber;
	        	
	        	var friendName = jsonObj.friendName;
	        	
	        	$("#" + friendNumber).next().css("background-color", "#7cfc00");
	        	
	        	$("#infomation").find("b").html(friendName + "上線囉");
	        	
	        	$("#infomation").css("display", "inline").fadeOut(6000);
	        	
	        } else if ("friendLogout" == jsonObj.action) {
	        	
	        	var friendNumber = jsonObj.friendNumber;
	        	
	        	var friendName = jsonObj.friendName;
	        	
	        	$("#" + friendNumber).next().css("background-color", "#a9a9a9");
	        	
	        	$("#infomation").find("b").html(friendName + "下線囉");
	        	
	        	$("#infomation").css("display", "inline").fadeOut(6000);
	        	
	        } else if ("addFriendRequest" == jsonObj.action) {
	        	
	        	$("#infomation").find("b").html(jsonObj.inviter + "想加你為好友");
	        	
	        	$("#infomation").css("display", "inline").fadeOut(6000);
	        	
	        } else if ("sendIsRead" == jsonObj.action) {
	        	
	        	if ('Y' == jsonObj.message) {
	        		
					var date = new Date();
					
					var nowTime = date.getFullYear() + '/' + (date.getMonth() + 1 ) + '/' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
	        		
	        		$("#chat_window" + jsonObj.senderNumber).find(".right").find(".isRead").html('(已讀)' + '<br>' + nowTime.fontsize(1));
	        		
	        	} else if ('N' == jsonObj.message) {
	        		
	        		$("#chat_window" + jsonObj.senderNumber).find(".right").find(".isRead").last().html('(未讀)');
	        		
	        	}
	        	
	        } else if ("groupBuyInvite" == jsonObj.action) {
	        	
				var url = '<%=request.getContextPath()%>/OrderMaster/OMC.html';
	        	
	        	swal({
	        		  title: jsonObj.inviter + '邀請你加入' + jsonObj.storeName + '團購',
	        		  html: "<div><form id='groupBuyInvite' onsubmit='swal.closeModal()' action='" + url + "' method='post' target='iframContent'><button type='submit' class='btn btn-success' id='acceptInvite' name='isAccept' value='AcceptInvite'>接受</button><button type='submit' class='btn btn-danger' id='rejectInvite' name='isAccept' value='RejectInvite'>拒絕</button><input type='hidden' name='meror_num' value='" + jsonObj.meror_num + "'></form></div>",
 	        		  showConfirmButton: false,
	        		})
	        		
	        } else if ("someOneCheckout" == jsonObj.action) {
	        	
	        	swal({
	        		  title: jsonObj.checkoutName + '已確認他的訂單',
	        		  showConfirmButton: false,
	        		  timer: 2000,
	        		})
	        	
	        	if (document.getElementById('iframContent').contentWindow.document.getElementById('shoppingCarList') !== null) {
	        		
	        		var groupBuyOrderMasterFrame = document.getElementById('iframContent').contentWindow.document.getElementById('shoppingCarList');
	        		var xhr = new XMLHttpRequest();
					xhr.onreadystatechange = function() {

						if (xhr.readyState == 4) {
							if (xhr.status == 200) {
								
								groupBuyOrderMasterFrame.innerHTML = xhr.responseText;

							} else {
								
								alert("not found");
							}
						}

					};

					var url = "<%=request.getContextPath()%>/OrderMaster/OMC.html";
					xhr.open("POST", url, true);
					xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
					xhr.send("action=GetGroupBuyInfo&&meror_num="+jsonObj.meror_num);
					
	        	}

	        } else if ("groupBuyOrderHadBeenSend" == jsonObj.action) {
	        	
	        	swal({
	        		  title: jsonObj.inviter + '已送出了' + jsonObj.stoName + '的團購訂單',
	        		  html: $('<div>')
	        		    .addClass('some-class')
	        		    .text(''),
	        		  animation: false,
	        		  customClass: 'animated tada'
	        		})
	        	
	        }
	        	
		};
		
	
		webSocket.onclose = function(event) {
			updateStatus("WebSocket 已離線");
		};
		
	}
	
	
	function sendMessage(action, message, target) {
		
		if ('talkMessage' == action) {
			
			if (message.trim() == '') {
	            return;
	        }
			   
			var jsonObj = {
							"action"       : action,
							"senderNumber" : "${mem_num}",
							"message"      : message,
							"targetNumber" : target };
			
			setTimeout(function () {
				
				webSocket.send(JSON.stringify(jsonObj));

            }, (300 * Math.random()));
		
		} else if ('sendIsRead' == action) {
			
			var jsonObj = {
							"action"       : action,
							"senderNumber" : "${mem_num}",
							"message"      : message,
							"targetNumber" : target };
			
			setTimeout(function () {
				
				webSocket.send(JSON.stringify(jsonObj));

            }, (300 * Math.random()));
			
		} else if ('sendImg' == action) {
			   
			var jsonObj = {
							"action"       : action,
							"senderNumber" : "${mem_num}",
							"message"      : message,
							"targetNumber" : target };
			
			setTimeout(function () {
				
				webSocket.send(JSON.stringify(jsonObj));

            }, (300 * Math.random()));
		
		}
		
	}
	
	function disconnect () {
		webSocket.close();
	}
	
	function updateStatus(newStatus) {
	}
	
	
	function creatChatBox(e_targetName, e_targetNumber) {
		
		var chatBox = "<div id='chat_window" + e_targetNumber + "' class='chat_window'><div class='top_menu'><div class='title'>" + e_targetName + "</div></div><ul class='messages' id='message" + e_targetNumber + "'></ul><div class='bottom_wrapper clearfix'><button class='sendImg btn btn-info' data-toggle='modal' href='#modal-photo" + e_targetNumber + "' type='button'><span class='glyphicon glyphicon-picture'></span></button><div class='message_input_wrapper'><input class='message_input' placeholder='請輸入訊息' /></div><div class='send_message'><div class='icon'></div><div class='text'>送出</div></div></div></div><div class='message_template'><li class='message'><img class='avatar'><div class='text_wrapper'><div class='text'></div></div><div class='isRead'></div></li></div>";
		
		var chatBoxModal = "<div class='modal' id='modal-photo" + e_targetNumber + "'><div class='modal-dialog'><div class='modal-content'><div class='modal-header'><button type='button' class='close' data-dismiss='modal'>x</button><h4 class='modal-title text-center'>選擇圖片</h4></div><div class='modal-body'><input type='file' name ='photo' class='inputIMG' accept='image/*'><img class='previewImg'></div><div class='modal-footer'><button class='btn btn-primary validation'>送出</button></div></div></div></div>";
		
		$("#chatBoxContainer").append(chatBox);
		
		$('#body').append(chatBoxModal);
		
		$('.top_menu').click(function(){
	
			$(this).parent().css("display", "none");
	
		});
		
		$('.send_message').click(function (e) {
			
			var messageBoard = $(this).prev().find(".message_input");
			
			sendMessage('talkMessage', messageBoard.val(), e_targetNumber); 
			
			messageBoard.val('');
	
	    });
	
	    $('.message_input').keyup(function (e) {
	
	        if (e.which === 13) {
	        	
	        	sendMessage('talkMessage', $(this).val(), e_targetNumber); 
	        	
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
	    		
	    		sendMessage('sendImg', reader.result, e_targetNumber); 
	    		
	    		$(this).parent().prev().find(".inputIMG").val('');
	    	}
	    	
	    });
	    
	    return $("#chat_window" + e_targetNumber) ;
		
	}
	
	$(document).ready(function(){
		
		var src = window.sessionStorage["currentSRC"];
		
		if (src == null || src == 'about:blank') {
			
			src = '<%=request.getContextPath()%>/front-end/index.jsp';
			sessionStorage.removeItem("currentSRC");
		}
		
		$("#iframContent").attr('src', src);
		
		$(window).bind('beforeunload',function(){

			window.sessionStorage["currentSRC"] = document.getElementById("iframContent").contentWindow.location.href;

		});
		
		$("#friendList").click(function(){
			
			$("#newTalk").css("display", "none");
			
			var target = null;
			var href = null;
			
			if ($(".friendHead").css("display") == "none") {
				
				$(".friendHead").css("display", "inline-block");
				$(".online").css("display", "inline-block");
				$(".offline").css("display", "inline-block");
				
			} else {
				
				$(".friendHead").css("display", "none");
				$(".online").css("display", "none");
				$(".offline").css("display", "none");
			}
			
			$(".friendHead").click(function(){
				
				$("#newTalk").css("display", "none");
				
				$(this).css('border', "none");
				
				targetNumber = $(this).attr('id');
				
				targetName = $(this).parent().find($('input[type=hidden][name=friendName]')).val();
				
				if ($("#chat_window" + targetNumber).length <= 0) {
					
					creatChatBox(targetName, targetNumber);
					
				} else if ($("#chat_window" + targetNumber).css("display") == "none") {
					
					$("#chat_window" + targetNumber).css("display", "inline-block");
					
					$("#message" + targetNumber).animate({ scrollTop: $("#message" + targetNumber).prop('scrollHeight') }, 300);
					
					sendMessage('sendIsRead', 'Y', targetNumber);
					
				} else if ($("#chat_window" + targetNumber).css("display") == "inline-block") {
					
					$("#chat_window" + targetNumber).css("display", "none");
				}
				
			});
			
		});
		
	});
	
</script>

</head>
	<body onload="connect();" onunload="disconnect();" id="body">
		<div id="content">
			<iframe id="iframContent" name="iframContent" width="100%" height="100%" frameborder="0" >
			</iframe>
		</div>
		<img id="friendList" alt="" src="<%=request.getContextPath()%>/img/myFriend.png">
		<img id="newTalk" src="<%=request.getContextPath()%>/img/talkNew.png">
		<span id="infomation">
			<b></b>
		</span>
		<div id="chatBoxContainer">
		</div>
		
		<div id="myFriendList">
			<c:forEach var="memVOFriends" items="${friendService.getFirends(mem_num)}" varStatus="m">
				<div class="eachMyFriend">
					<img class="friendHead" id="${memVOFriends.mem_num}" src="<%=request.getContextPath()%>/getPic?mem_num=${memVOFriends.mem_num}">
					<c:if test="${loginInfo.getLoginRecord().containsKey(memVOFriends.mem_num)}" var="isOnline">
						<div class="online"></div>
					</c:if>
					<c:if test="${!isOnline}">
						<div class="offline"></div>
					</c:if>
					<input type="hidden" name="friendName" value="${memVOFriends.mem_name}">
				</div>
			</c:forEach>
		</div>
	
	</body>
</html>