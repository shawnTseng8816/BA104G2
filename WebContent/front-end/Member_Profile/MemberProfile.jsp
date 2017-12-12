<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>揪茶趣</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery.js"></script>


<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->


<jsp:include page="/front-end/member_top.jsp" flush="true" />


<style>
	.body {
		margin-top: 2.5em;
	}
	
	.picUpdate, #myPic ~div .glyphicon-ok {
		margin-top: 1em;
	}
	
	.editable {
	 	padding: 3px;
		font-size: 1.3em;
	}
	
	#mem_pwdConfirm {
		padding: 3px;
		font-size: 1.3em;
	}
	
	.glyphicon-ok {
		display: none;
	}
	
	input {
		background-color: transparent;
		border: 1.2px solid transparent;
	}
	
	#myPic {
		max-width:320px;
		max-height:320px;
	}
	
	.picSide {
		float: right;
	}
	
	.errorMsg {
		text-align: center;
		color: #ff0000;
	}
	
	#mem_pwdConfirm, #mem_pwdConfirmTitle {
		display: none;
	}
</style>


<script>
	$(document).ready(function() {

		$("tr:even").addClass("info");
		$("tr:odd").addClass("danger");

		$(".glyphicon-pencil").click(function() {
			$(this).css("display", "none");
			$(this).parent().find("button.glyphicon-ok").css("display", "inline");
			$(this).parent().prev().find("input.editable").prop('disabled', false).css("border", "1.2px solid #EA0000");
			
			var target = $(this).parent().prev().find("input.editable");
			
			setTimeout(function() {
				target.css("border", "1.2px solid transparent");
			}, 500);
			
			setTimeout(function() {
			    setInterval(function() {
			    	target.css("border", "1.2px solid transparent");
			    }, 1000);
			}, 500);

			setInterval(function() {
				target.css("border", "1.2px solid #EA0000");
			}, 1000);
			
			if ($(this).parent().prev().find(".editable").attr("type") == "password") {
				
				$("#mem_pwdConfirm").css({"display":"block", "border":"1.2px solid #EA0000"});
				$("#mem_pwdConfirmTitle").css("display", "block");
				
				setTimeout(function() {
					$("#mem_pwdConfirm").css("border", "1.2px solid transparent");
				}, 500);
				
				setTimeout(function() {
				    setInterval(function() {
				    	$("#mem_pwdConfirm").css("border", "1.2px solid transparent");
				    }, 1000);
				}, 500);

				setInterval(function() {
					$("#mem_pwdConfirm").css("border", "1.2px solid #EA0000");
				}, 1000);
				
			}
			
		});

		var reader = new FileReader();
		$("#files").change(function(e) {

			$(this).parent().find("button.glyphicon-ok").css("display", "inline");

			reader.onload = function(e) {
				$("#myPic").attr('src', e.target.result);
			}
			reader.readAsDataURL(e.target.files[0]);
		});
	});
</script>

</head>
<body>

	

	<!-- =============================Body==================================== -->
	<div class="container body">
		<div class="row">

			<form action="<%=request.getContextPath()%>/MemberProfile/MPC.html" method="post" enctype="multipart/form-data">
				<div class="col-xs-12 col-sm-4 col-sm-offset-1">
					<div class="picSide">
						<img id="myPic" src="<%=request.getContextPath()%>/getPic?mem_num=${mem_num}"><br>
						<div>
							<label for="files" class="btn btn-info glyphicon glyphicon-camera picUpdate"></label>
							<button type="submit" class="btn btn-success glyphicon glyphicon-ok" name="action" value="EDITE"></button>
							<input id="files" style="visibility: hidden;" type="file" name="mem_pic">
						</div>
						<c:if test="${errorMsgs.size() > 0}">
							<div class="errorMsgs">
								<c:forEach var="errorMsgss" items="${errorMsgs}">
									<h4 class="errorMsg">${errorMsgss}</h4>
								</c:forEach>
							</div>
						</c:if>
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
							<td>
								<input class="editable" type="text" name="mem_name"value="${myProfile.mem_name}" size="25" maxlength="20" disabled>
							</td>
							<td>
								<button type="button" class="btn btn-info glyphicon glyphicon-pencil"></button>
								<button type="submit" class="btn btn-success glyphicon glyphicon-ok" name="action" value="EDITE"></button>
							</td>
						</tr>
						<tr>
							<td>帳號</td>
							<td>
								<h4>${myProfile.mem_acc}</h4>
							</td>
							<td></td>
						</tr>
						<tr>
							<td>密碼
								<p id="mem_pwdConfirmTitle"><br>請輸入您目前的密碼</p>
							</td>
							<td>
								<input class="editable" type="password" name="mem_pwd" value="*********" size="25" maxlength="15" disabled><br>
								<input id="mem_pwdConfirm" type="password" name="mem_pwdConfirm" size="25" maxlength="15">
							</td>
							<td>
								<button type="button" class="btn btn-info glyphicon glyphicon-pencil"></button>
								<button type="submit" class="btn btn-success glyphicon glyphicon-ok" name="action" value="EDITE"></button>
							</td>
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
							<td>
								<input class="editable" type="text" name="mobile" value="${myProfile.mobile}" size="25" maxlength="15" disabled>
							</td>
							<td>
								<button type="button" class="btn btn-info glyphicon glyphicon-pencil"></button>
								<button type="submit"class="btn btn-success glyphicon glyphicon-ok" name="action" value="EDITE"></button>
							</td>
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
							<td>
								<input class="editable" type="text" name="email" value="${myProfile.email}" size="25" maxlength="20" disabled>
							</td>
							<td>
								<button type="button" class="btn btn-info glyphicon glyphicon-pencil"></button>
								<button type="submit" class="btn btn-success glyphicon glyphicon-ok" name="action" value="EDITE"></button>
							</td>
						</tr>
						<tr>
							<td>住址</td>
							<td>
								<input class="editable" type="text" name="address" value="${myProfile.address}" size="25" maxlength="20" disabled>
							</td>
							<td>
								<button type="button" class="btn btn-info glyphicon glyphicon-pencil"></button>
								<button type="submit" class="btn btn-success glyphicon glyphicon-ok" name="action" value="EDITE"></button>
							</td>
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

	<jsp:include page="/front-end/Footer.jsp" flush="true" />

</body>
</html>