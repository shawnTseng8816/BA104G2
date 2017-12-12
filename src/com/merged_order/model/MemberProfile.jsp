<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>揪茶趣</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/index_base.css">
<script src="https://code.jquery.com/jquery.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>

<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->


<style>
.body {
	margin-top: 2.5em;
}

.picUpdate, #myPic ~div .glyphicon-ok {
	margin-top: 1em;
}

.editable {
	font-size: 1.3em;
}

.glyphicon-ok {
	display: none;
}

input {
	background-color: transparent;
	border: none;
}
</style>


<script>
	$(document).ready(
			function() {

				$("tr:even").addClass("info");
				$("tr:odd").addClass("danger");

				$(".glyphicon-pencil").click(
						function() {
							$(this).css("display", "none");
							$(this).parent().find("button.glyphicon-ok").css(
									"display", "inline");
							$(this).parent().prev().find("input.editable")
									.prop('disabled', false).css("border",
											"1px solid #66afe9");
						});

				var reader = new FileReader();
				$("#files").change(
						function(e) {

							$(this).parent().find("button.glyphicon-ok").css(
									"display", "inline");

							var goal = $(this);
							reader.onload = function(e) {
								$("#myPic").attr('src', e.target.result);
							}
							reader.readAsDataURL(e.target.files[0]);
						});
			});
</script>

</head>
<body>
	<!-- ====================================      HEADER從這邊開始       ==================================================== -->

	<!-- top nav bar =============================================================== -->
	<div class="navbars navbar-fixed-top">
		<div class="container">
			<div class="row ">
				<ul class="nav navbar-nav navbar-right ">
					<li><a class="item-color" href="#!"> <span
							class="glyphicon glyphicon-log-in icons"></span> 登入
					</a></li>
					<li><a class="item-color" href="#"> <span
							class="glyphicon glyphicon-pencil icons"></span> 註冊
					</a></li>
					<li class="dropdown"><a href="#"
						class="dropdown-toggle item-color" data-toggle="dropdown"> <span
							class="glyphicon glyphicon-user icons"></span> 會員專區

					</a>
						<ul class="dropdown-menu">
							<li><a href="#">訂單管理</a></li>
							<li><a href="#">寄杯</a></li>
							<li><a href="#">點數</a></li>
							<li><a href="#">集點卡</a></li>
							<li><a href="#">最愛店家</a></li>
							<li><a href="#">評論</a></li>
							<li><a href="#">好友</a></li>
							<li><a href="#">個人資料</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
	</div>


	<!-- LOGO + Serch Bar + hotserch + 訂單管理 + 附近店家 + 折價券 ============ -->
	<div class="container">
		<div class="row">

			<!-- LOGO -->
			<div class="col-xs-12 col-sm-3 ">
				<img src="image/LOGO_w_285x150.png">
			</div>

			<!-- serch bar + hot key-->
			<div class="col-xs-12 col-sm-5 ">


				<!-- search bar -->
				<div class="input-group area70">
					<input type="text" class="form-control input-lg"
						placeholder="請輸入關鍵字"> <span class="input-group-btn">
						<button class="btn btn-lg" type="button">
							<span class=" glyphicon glyphicon-search"></span>
						</button>
					</span>
				</div>


				<!-- hot keys 熱門字 24個中文字滿-->

				<div class="hotkey">
					<ul class="list-inline">
						<li>熱門字</li>
						<li>熱門字</li>
						<li>熱門字</li>
						<li>熱門字</li>
						<li>熱門字</li>
						<li>熱門字</li>
						<li>熱門字</li>

					</ul>
				</div>
			</div>



			<!-- 訂單管理 btn+ 附近店家 btn + 折價券btn -->
			<div class="col-xs-12 col-sm-4 ">

				<button type="button" class="btn btn-green area70 btn-lg ">訂單管理</button>
				<button type="button" class="btn btn-green area70 btn-lg">附近店家</button>
				<button type="button" class="btn btn-org area70 btn-lg">瘋折價券</button>

			</div>
		</div>
	</div>

	<!-- ====================================   ↑   HEADER到這邊結束    ↑   ======================================================== -->

	<!-- ====================================   ↓   內容的部分放這邊   ↓   ========================================================= -->

	<!-- =============================Body==================================== -->
	<div class="container body">
		<div class="row">

			<form action="MC.html" method="post" enctype="multipart/form-data">
				<div class="col-xs-12 col-sm-4 col-sm-offset-1">
					<img id="myPic" src="getPic?mem_num=${mem_num}"><br>
					<div>
						<label for="files"
							class="btn btn-info glyphicon glyphicon-camera picUpdate"></label>
						<button type="submit"
							class="btn btn-success glyphicon glyphicon-ok" name="action"
							value="EDITE"></button>
						<input id="files" style="visibility: hidden;" type="file"
							name="mem_pic">
					</div>
				</div>

				<div class="col-xs-12 col-sm-6 ">
					<table class="table table-hover table-striped ">
						<tr>
							<td>會員編號</td>
							<td>
								<h4>${myProfile.mem_num}</h4>
							</td>
							<td></td>
						</tr>
						<tr>
							<td>姓名</td>
							<td><input class="editable" type="text" name="mem_name"
								value="${myProfile.mem_name}" disabled></td>
							<td><a href="#"
								class="btn btn-info glyphicon glyphicon-pencil"></a>
								<button type="submit"
									class="btn btn-success glyphicon glyphicon-ok" name="action"
									value="EDITE"></button></td>
						</tr>
						<tr>
							<td>帳號</td>
							<td>
								<h4>ACCOUNT</h4>
							</td>
							<td></td>
						</tr>
						<tr>
							<td>密碼</td>
							<td><input class="editable" type="text" name="mem_pwd"
								value="${myProfile.mem_pwd}" disabled></td>
							<td><a href="#"
								class="btn btn-info glyphicon glyphicon-pencil"></a>
								<button type="submit"
									class="btn btn-success glyphicon glyphicon-ok" name="action"
									value="EDITE"></button></td>
						</tr>
						<tr>
							<td>性別</td>
							<td>
								<h4>${myProfile.sex}</h4>
							</td>
							<td></td>
						</tr>
						<tr>
							<td>年齡</td>
							<td>
								<h4>${myProfile.age}</h4>
							</td>
							<td></td>
						</tr>
						<tr>
							<td>電話號碼</td>
							<td><input class="editable" type="text" name="mobile"
								value="${myProfile.mobile}" disabled></td>
							<td><a href="#"
								class="btn btn-info glyphicon glyphicon-pencil"></a>
								<button type="submit"
									class="btn btn-success glyphicon glyphicon-ok" name="action"
									value="EDITE"></button></td>
						</tr>
						<tr>
							<td>是否通過電話驗證</td>
							<td>
								<h4>${myProfile.cek_mobile}</h4>
							</td>
							<td></td>
						</tr>
						<tr>
							<td>Email</td>
							<td><input class="editable" type="text" name="email"
								value="${myProfile.email}" disabled></td>
							<td><a href="#"
								class="btn btn-info glyphicon glyphicon-pencil"></a>
								<button type="submit"
									class="btn btn-success glyphicon glyphicon-ok" name="action"
									value="EDITE"></button></td>
						</tr>
						<tr>
							<td>住址</td>
							<td><input class="editable" type="text" name="address"
								value="${myProfile.address}" disabled></td>
							<td><a href="#"
								class="btn btn-info glyphicon glyphicon-pencil"></a>
								<button type="submit"
									class="btn btn-success glyphicon glyphicon-ok" name="action"
									value="EDITE"></button></td>
						</tr>
						<tr>
							<td>剩餘點數</td>
							<td>
								<h4>${myProfile.rem_point}</h4>
							</td>
							<td></td>
						</tr>
					</table>
					<input type="hidden" name="mem_num" value="${myProfile.mem_num}">
				</div>
			</form>

		</div>
	</div>
	<!-- =============================Body==================================== -->



	<!-- ================================================   ↑↑   內容的部分放這邊   ↑↑  ========================================================== -->

	<!--=================================================   ↓↓  footer 從這邊開始   ↓↓  ========================================================== -->
	<div class="navbars">
		<div class="container area70">
			<div class="row">
				<div class="col-xs-12 col-sm-12">
					<nav class="navbar">
						<ul class="nav nav-pills nav-justified radius5">
							<li><a href="#" class="icons item-color">揪茶趣手機版</a></li>
							<li><a href="#" class="icons item-color">關於揪茶趣</a></li>
							<li><a href="#" class="icons item-color">成為店家</a></li>
							<li><a href="#" class="icons item-color">店家登入</a></li>
							<li><a href="#" class="icons item-color">教學專區</a></li>
						</ul>
						<p class="text-center ">版權所有 © 揪茶趣 All rights reserved.</p>
					</nav>

				</div>
			</div>
		</div>
	</div>

	<!--=================================================   ↑↑  footer 從這邊開始   ↑↑  ========================================================== -->

</body>
</html>