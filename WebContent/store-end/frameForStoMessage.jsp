<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%	
	String sto_num = (String) request.getSession().getAttribute("sto_num");
 	if (sto_num == null || (sto_num.trim()).length() <= 0) {
 		
 		response.sendRedirect(request.getContextPath() + "/front-end/index.jsp");
		return;
		
	}
%>


<!DOCTYPE html>
<html lang="">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>揪茶趣:訂飲料好方便</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/dist/sweetalert2.all.min.js"></script>
<script src="<%=request.getContextPath()%>/js/dist/sweetalert2.min.js"></script>

<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
		
	<style type="text/css">
	 #content
        {
            position:absolute; left: 0; right: 0; bottom: 0; top: 0px; 
        }
        
       body, html
        {
            /*margin: 0; padding: 0; height: 100%; */
            overflow: hidden;
        }
	</style>




<script>

	var MyPoint = "/FriendListChatServer/${sto_num}";
	var host = window.location.host;
	var path = window.location.pathname;
	var webCtx = path.substring(0, path.indexOf('/', 1));
	var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
	var webSocket;
	
	function connect() {
		
		webSocket = new WebSocket(endPointURL);
		
		webSocket.onopen = function(event) {
			
		};
	
		
		webSocket.onmessage = function(event) {
			
	        var jsonObj = JSON.parse(event.data);
	        
  			 if ("stoGetOrderMessage" == jsonObj.action) {
	        	
	            var message = jsonObj.message ;    
		        swal({
					  position: 'center',
					  type: 'success',
					  title: message,
					  allowOutsideClick: false,
					  showConfirmButton: true
					})
	        }
  			 else if ("stoGetErrMessage" == jsonObj.action) {
 	        	
 	            var message = jsonObj.message ;    
 		        swal({
 					  position: 'center',
 					  type: 'error',
 					  title: message,
 					  timer: 2500
 					})
 	        }
   
  			 else if ("stoGetMessage" == jsonObj.action) {
  	        	
  	            var message = jsonObj.message ;    
  		        swal({
  					  position: 'center',
  					  type: 'success',
  					  title: message,
  					  timer: 2500
  					})
  	        }
         	
	        }
	        
		}
	


	
	$(document).ready(function(){
		
		
		var src = window.sessionStorage["currentSRC"];
		
		if (src == null || src == 'about:blank') {
			
			src = '<%=request.getContextPath()%>/store-end/pdc_mng/store_select_page.jsp';
			sessionStorage.removeItem("currentSRC");
		}
		
		$("#iframContent").attr('src', src);
		
		$(window).bind('beforeunload',function(){

			window.sessionStorage["currentSRC"] = document.getElementById("iframContent").contentWindow.location.href;

		});
		
		
		
		
	
	});
</script>


</head>
	<body onload="connect();" onunload="disconnect();" id="body">
		<div id="content">
			<iframe id="iframContent" width="100%" height="100%" frameborder="0" >
			</iframe>
		</div>

	</body>
</html>