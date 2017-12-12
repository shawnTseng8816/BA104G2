<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="loginInfo" scope="page" class="login.test.LoginInfo"/>

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
		
		$(".deleteFriend").click(function(){
			
			var MemberNumber = $(this).parent().parent().next().val();
			
			var xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function() {

				if (xhr.readyState == 4) {
					if (xhr.status == 200) {
						
						$("#friends").empty();
						$("#friends").append(xhr.responseText);
						
					} else {
						alert("not found");
					}
				}
				
			};

			var url = "<%=request.getContextPath()%>/FriendList/FLC.html";
			xhr.open("POST", url, true);
			xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
			xhr.send("action=DELETE&invd_mem_num=" + MemberNumber);
			
		});
		
	});

</script>

</head>

<body>
	<c:forEach var="memVOFriends" items="${requestScope.friendList}" varStatus="m">
		<div class="col-sm-4 friendList">
				<div class="card hovercard">

					<c:if test="${memVOFriends.sex=='男'}" var="varName">
						<div class="man cardheader"></div>
					</c:if>

					<c:if test="${!varName}">
						<div class="women cardheader"></div>
					</c:if>

					<div class="avatar">
					<a href='#memberProfile${memVOFriends.mem_num}' data-toggle="modal" >
						<img class="memPic" alt="" src="<%=request.getContextPath()%>/getPic?mem_num=${memVOFriends.mem_num}">
					</a>
					</div>
					<div class="info">
						<div class="title">${memVOFriends.mem_name}</div>
						<div class="desc">${memVOFriends.sex}</div>
						<div class="desc">${memVOFriends.email}</div>
					</div>
					<div class="bottom">
					</div>
					<div class="bottom">
						<button type="submit" class="btn btn-danger deleteFriend" name="action" value="DELETE">刪除好友</button>
					</div>
				</div>
				<input type="hidden" id="${memVOFriends.mem_num}" name="invd_mem_num" value="${memVOFriends.mem_num}">
		</div>
		
		<c:set var="memid" value="${memVOFriends.mem_num}"/>
											
		<%@ include file="/front-end/MemberDetail/MemberDetail.jsp" %>
				
	</c:forEach>
	
</body>
</html>