<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>糾茶趣</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/index_base.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/starability-css/starability-all.css">
<script src="https://code.jquery.com/jquery.js"></script>


<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->


<jsp:include page="/front-end/member_top.jsp" flush="true" />


<style>
	.text, .panel-title input {
		border: none;
		resize: none;
		background-color: transparent;
	}
	
	input {
		background-color: transparent;
		border: 1.2px solid transparent;
	}
	
	.text {
		font-size: 1.1em;
	}
	
	.storePic {
		width: 100%;
	}
	
	.timeline {
		position: relative;
		padding: 21px 0px 10px;
		margin-top: 4px;
		margin-bottom: 30px;
	}
	
	.timeline .line {
		position: absolute;
		width: 4px;
		display: block;
		background: currentColor;
		top: 0px;
		bottom: 0px;
		margin-left: 30px;
	}
	
	.timeline .separator {
		border-top: 1px solid currentColor;
		padding: 5px;
		padding-left: 40px;
		font-style: italic;
		font-size: .9em;
		margin-left: 30px;
	}
	
	.timeline .line::before {
		top: -4px;
	}
	
	.timeline .line::after {
		bottom: -4px;
	}
	
	.timeline .line::before, .timeline .line::after {
		position: absolute;
		left: -4px;
		width: 12px;
		height: 12px;
		display: block;
		border-radius: 50%;
		background: currentColor;
	}
	
	.timeline .panel {
		position: relative;
		margin: 10px 0px 21px 70px;
		clear: both;
	}
	
	.timeline .panel::before {
		position: absolute;
		display: block;
		top: 8px;
		left: -24px;
		content: '';
		width: 0px;
		height: 0px;
		border: inherit;
		border-width: 12px;
		border-top-color: transparent;
		border-bottom-color: transparent;
		border-left-color: transparent;
	}
	
	.timeline .panel .panel-heading.icon * {
		font-size: 20px;
		vertical-align: middle;
		line-height: 40px;
	}
	
	.timeline .panel .panel-heading.icon {
		position: absolute;
		left: -59px;
		display: block;
		width: 40px;
		height: 40px;
		padding: 0px;
		border-radius: 50%;
		text-align: center;
		float: left;
	}
	
	.timeline .panel-outline {
		border-color: transparent;
		background: transparent;
		box-shadow: none;
	}
	
	.timeline .panel-outline .panel-body {
		padding: 10px 0px;
	}
	
	.timeline .panel-outline .panel-heading:not (.icon ), .timeline .panel-outline .panel-footer
		{
		display: none;
	}
	
	.panel-heading {
		height: 3.7em;
	}
	
	.panel-title {
		font-size: 1.8em;
		margin-left: 0.7em;
	}
	
	.panel-body h4 {
		font-weight: bold;
	}
	
	.glyphicon-ok {
		display: none;
	}
	
	#alert {
		display:none;
		margin:auto;
	}
</style>


<script>

	$(document).ready(function() {
		
		var date = new Date();
		
		$("#date").append(date.getFullYear() + "/" + (date.getMonth()+1) + "/" + date.getDate());
		
		var rows = 1;
		
		$(window).scroll(function() {
			
			if ($(window).scrollTop() >= $(document).height() - $(window).height()) {
				rows += 2;
				
				var xhr = new XMLHttpRequest();
				xhr.onreadystatechange = function() {
					
					if (xhr.readyState == 1) {
						
						$("#alert").css("display", "block");
						$("#alert").modal({backdrop: false});
						$("#alert").modal('show');
						
					}

					if (xhr.readyState == 4) {
						if (xhr.status == 200) {
							
							$("#alert").modal('hide')
							$(".timeline").append(xhr.responseText);
							
// 							if(xhr.responseText.length < 10){
// 								$("#alert").remove();
// 							}
							
						} else {
							alert("not found");
						}
					}
					
				};

				var url = "<%=request.getContextPath()%>/StoreComment/SCC.html";
				xhr.open("POST", url, true);
				xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
				xhr.send("action=LOAD&rows="+ rows);
			}
	});

});
</script>

</head>
<body>
	
	
	
	<!-- =============================Body==================================== -->
	<div class="container">
		<div class="col-sm-10 col-sm-offset-1">
			<div class="page-header">
				<h3>
					<i id="date"></i>
				</h3>
			</div>

			<div class="timeline">

				<div class="line text-muted"></div>
				
				<jsp:include page="/front-end/Store_Comment/MyComment.jsp" flush="true" />
				
			</div>

		</div>
	</div>
	
	<img class="modal fade modal-sm" role="dialog" id="alert" src="<%=request.getContextPath()%>/img/loading.gif">
	<!-- =============================Body==================================== -->

	<jsp:include page="/front-end/member_foot.jsp" flush="true" />

</body>
</html>