<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FriendList</title>

<style>
.modal-open .modal, a:active, a:focus, a:link, a:visited{
		    outline:none!important;
		    border: none;
			text-decoration: none!important;
			-moz-outline-style: none;
		}

</style>

<script>

	$(document).ready(function(){
		
		$(".confirmFriend").click(function(){
			
			var MemberNumber = $(this).parent().parent().next().val();
			
			var xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function() {

				if (xhr.readyState == 4) {
					if (xhr.status == 200) {
						
						$("#friendchecks").empty();
						$("#friendchecks").append(xhr.responseText);
						
					} else {
						alert("not found");
					}
				}
				
			};

			var url = "<%=request.getContextPath()%>/FriendList/FLC.html";
			xhr.open("POST", url, true);
			xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
			xhr.send("action=CONFIRM&invd_mem_num=" + MemberNumber);
			
			
			
			var xhr2 = new XMLHttpRequest();
			xhr2.onreadystatechange = function() {

				if (xhr2.readyState == 4) {
					if (xhr2.status == 200) {
						
						$("#friends").empty();
						$("#friends").append(xhr2.responseText);
						
					} else {
						alert("not found");
					}
				}
				
			};

			var url2 = "<%=request.getContextPath()%>/FriendList/FLC.html";
			xhr2.open("POST", url2, true);
			xhr2.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
			xhr2.send("action=CONFIRMFRIEND");
			
		});
		
		
		
		$(".rejectFriend").click(function(){
			
			var MemberNumber = $(this).parent().parent().next().val();
			
			var xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function() {

				if (xhr.readyState == 4) {
					if (xhr.status == 200) {
						
						$("#friendchecks").empty();
						$("#friendchecks").append(xhr.responseText);
						
					} else {
						alert("not found");
					}
				}
				
			};

			var url = "<%=request.getContextPath()%>/FriendList/FLC.html";
			xhr.open("POST", url, true);
			xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
			xhr.send("action=REJECT&invd_mem_num=" + MemberNumber);
			
		});
		
	});

</script>


</head>

<body>
	<c:forEach var="memVOFriendCheck" items="${requestScope.friendCheckList}">
		<div class="col-sm-4 friendchecksList">
				<div class="card hovercard">

					<c:if test="${memVOFriendCheck.sex=='男'}" var="varName">
						<div class="man cardheader"></div>
					</c:if>

					<c:if test="${!varName}">
						<div class="women cardheader"></div>
					</c:if>

					<div class="avatar">
					<a href='#memberProfile${memVOFriendCheck.mem_num}' data-toggle="modal" >
						<img alt="" src="<%=request.getContextPath()%>/getPic?mem_num=${memVOFriendCheck.mem_num}">
					</a>
					</div>
					<div class="info">
						<div class="title">${memVOFriendCheck.mem_name}</div>
						<div class="desc">${memVOFriendCheck.sex}</div>
						<div class="desc">${memVOFriendCheck.email}</div>
					</div>
					<div class="bottom">
						<button type="submit" class="btn btn-info confirmFriend" name="action" value="CONFIRM">確認邀請</button>
						<button type="submit" class="btn btn-danger rejectFriend" name="action" value="REJECT">拒絕邀請</button>
					</div>
				</div>
				<input type="hidden" name="invd_mem_num" value="${memVOFriendCheck.mem_num}">
		</div>
		
		<c:set var="memid" value="${memVOFriendCheck.mem_num}"/>
											
		<%@ include file="/front-end/MemberDetail/MemberDetail.jsp" %>
		
	</c:forEach>
</body>
</html>