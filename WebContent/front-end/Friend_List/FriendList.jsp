<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<jsp:useBean id="memberProfileService" scope="page" class="com.member_profile.model.MemberProfileService" />

<!DOCTYPE html>
<html lang="">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>揪茶趣</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/BA104G2/css/index_base.css">
<script src="https://code.jquery.com/jquery.js"></script>


<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->


<style>

	.board {
		width: 75%;
		margin: auto;
		height: 600px;
		/*background: #fff;*/
		/*box-shadow: 10px 10px #ccc,-10px 20px #ddd;*/
	}
	
	.board .nav-tabs {
		position: relative;
		/* border-bottom: 0; */
		/*width: 40%; */
		margin: 20px auto;
		/*margin-left: 150px;*/
		margin-bottom: 0;
		box-sizing: border-box;
	}
	
	.liner {
		height: 2px;
		background: #ddd;
		position: absolute;
		width: 65%;
		margin: 0 auto;
		left: 0;
		right: 0;
		top: 50%;
		z-index: 1;
	}
	
	.nav-tabs>li.active>a, .nav-tabs>li.active>a:hover, .nav-tabs>li.active>a:focus
		{
		color: #555555;
		cursor: default;
		/* background-color: #ffffff; */
		border: 0;
		border-bottom-color: transparent;
	}
	
	span.round-tabs {
		width: 70px;
		height: 70px;
		line-height: 70px;
		display: inline-block;
		border-radius: 100px;
		background: white;
		z-index: 2;
		position: absolute;
		left: 0em;
		text-align: center;
		font-size: 25px;
	}
	
	span.round-tabs.one {
		color: #6495ed;
		border: 2px solid #6495ed;
	}
	
	li.active span.round-tabs.one {
		/*background: #fff !important;*/
		border: 2px solid #3cb371;
		color: #6495ed;
	}
	
	span.round-tabs.two {
		color: #cd5c5c;
		border: 2px solid #cd5c5c;
	}
	
	li.active span.round-tabs.two {
		/*background: #fff !important;*/
		border: 2px solid #3cb371;
		color: #cd5c5c;
	}
	
	span.round-tabs.three {
		color: #a9a9a9;
		border: 2px solid #a9a9a9;
	}
	
	li.active span.round-tabs.three {
		/*background: #fff !important;*/
		border: 2px solid #3cb371;
		color: #a9a9a9;
	}
	
	.nav-tabs>li.active>a span.round-tabs {
		background: #fafafa;
	}
	
	.nav-tabs>li {
		margin-left: 22%;
		width: 1%;
	}
	
	.nav-tabs>li:after {
		content: " ";
		position: absolute;
		left: 1.75em;
		opacity: 0;
		margin: 0 auto;
		bottom: 0px;
		border: 5px solid transparent;
		border-bottom-color: #ddd;
		transition: 0.1s ease-in-out;
	}
	
	.nav-tabs>li.active:after {
		content: " ";
		position: absolute;
		left: 1.75em;
		opacity: 1;
		margin: 0 auto;
		bottom: 0px;
		border: 10px solid transparent;
		border-bottom-color: #5f9ea0;
	}
	
	.nav-tabs>li a {
		width: 70px;
		height: 70px;
		margin: 20px auto;
		border-radius: 100%;
		padding: 0;
	}
	
	.nav-tabs>li a:hover {
		background: transparent;
	}
	
	.tab-pane {
		position: relative;
		padding-top: 50px;
	}
	
	td {
		padding: 3px;
	}
	
	.memberlist {
		margin-top: 1.5em;
	}
	
	.friendchecksList {
		margin-top: 1.5em;
	}
	
	.searchbar {
		display: inlineblock;
		margin: auto;
	}
	
	.friendList {
		margin-top: 1.5em;
	}
	
	.card {
		width: 90%;
		padding-top: 20px;
		display: block;
		margin: auto;
		border-radius: 1.5em;
		box-shadow: 0px 25px 50px rgba(0, 0, 0, 0.5);
		-webkit-box-sizing: border-box;
		-moz-box-sizing: border-box;
		box-sizing: border-box;
	}
	
	.card.hovercard .cardheader {
		background-size: cover;
		height: 135px;
	}
	
	.women {
		background: url("<%=request.getContextPath()%>/img/women_background-25.jpg");
	}
	
	.man {
		background: url("<%=request.getContextPath()%>/img/man_background.jpg");
	}
	
	.card.hovercard {
		padding-top: 0;
		overflow: hidden;
		position: relative;
		text-align: center;
		background-color: rgba(214, 224, 226, 0.2);
	}
	
	.card.hovercard .avatar {
		position: relative;
		top: -90px;
		margin-bottom: -75px;
	}
	
	.card.hovercard .avatar img {
		width: 130px;
		height: 130px;
		max-width: 130px;
		max-height: 130px;
		-webkit-border-radius: 50%;
		-moz-border-radius: 50%;
		border-radius: 50%;
	}
	
	.card .card-heading.image .card-heading-header {
		display: inline-block;
		vertical-align: top;
	}
	
	.card.hovercard .info {
		padding: 4px 8px 10px;
	}
	
	.card.hovercard .info .title {
		margin-bottom: 8px;
		font-size: 21px;
		line-height: 1;
		color: #262626;
		vertical-align: middle;
	}
	
	.card.hovercard .info .desc {
		font-size: 13px;
		line-height: 20px;
		color: #737373;
	}
	
	.card.hovercard .bottom {
		padding: 0.5em 20px;
		margin-bottom: 0.3em;
	}
	
	#addFriendRequest {
		width: 5%;
	 	position: absolute;
		margin-top: 2em; 
	 	margin-left: 32em;
	 	display:none;
	}

</style>


<script>

	$(document).ready(function(){
		
		$("#searchMember").click(function(){
			
			var KeyWord = $(this).parent().prev().val();
			
			var xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function() {

				if (xhr.readyState == 4) {
					if (xhr.status == 200) {
						
						$("#searchContent").empty();
						$("#searchContent").append(xhr.responseText);
						
					} else {
						alert("not found");
					}
				}
				
			};

			var url = "<%=request.getContextPath()%>/FriendList/FLC.html";
			xhr.open("POST", url, true);
			xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
			xhr.send("action=SEARCHMEMBER&searchName=" + KeyWord);
			
		});
		
		
		$("#friendcheckPag").click(function(){
			
			$("#addFriendRequest").css("display", "none");
			
		});
		
	});
	
</script>

</head>

<body>

	<jsp:include page="/front-end/member_top.jsp" flush="true" />
	
	<!-- =============================Body==================================== -->
	<div class="container body">
		<div class="row">
			<section>
				<div class="container">
					<div class="row">
						<div class="board">
							<div class="board-inner">
								
								<img src="<%=request.getContextPath()%>/img/talkNew.png" id="addFriendRequest">
								
								<ul class="nav nav-tabs" id="myTab">
									<li class="active">
										<a href="#friends" data-toggle="tab" title="好友專區" style="border:none"> 
											<span class="round-tabs one"> 
												<i class="glyphicon glyphicon-user"></i>
											</span>
										</a>
									</li>
									<li class="">
										<a href="#friendchecks" data-toggle="tab" title="待確認" id="friendcheckPag" style="border:none"> 
											<span class="round-tabs two"> 
												<i class="glyphicon glyphicon-question-sign"></i>
											</span>
										</a>
									</li>
									<li class=""><a href="#searchmember"
										data-toggle="tab" title="會員搜尋" style="border:none">
										 <span class="round-tabs three"> 
										 	<i class="glyphicon glyphicon-search"></i>
										</span>
									</a></li>
								</ul>
							</div>

							<div class="tab-content">
							
								<div class="tab-pane fade in active" id="friends">
									<jsp:include page="/front-end/Friend_List/FriendListShow.jsp" flush="true" />
								</div>

								<div class="tab-pane fade" id="friendchecks">
									<jsp:include page="/front-end/Friend_List/FriendCheckShow.jsp" flush="true" />
								</div>

								<div class="tab-pane fade" id="searchmember">
										<div class="input-group searchbar" style="width: 40%">
											<input type="text" class="form-control" placeholder="請輸入會員名稱" name="searchName"> 
											<span class="input-group-btn">
												<button id="searchMember" class="btn" type="submit" name="action" value="SEARCHMEMBER">
													<span class=" glyphicon glyphicon-search"></span>
												</button>
											</span>
										</div>
										<span id="searchContent">
											<jsp:include page="/front-end/Friend_List/MemberList.jsp" flush="true" />
										</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>

		</div>
	</div>
	
	<!-- =============================Body==================================== -->

	<jsp:include page="/front-end/member_foot.jsp" flush="true" />

</body>
</html>