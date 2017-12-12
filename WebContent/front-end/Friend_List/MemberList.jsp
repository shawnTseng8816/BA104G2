<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MemberList</title>

<style>
.modal-open .modal, a:active, a:focus, a:link, a:visited{
		    outline:none!important;
		    border: none;
			text-decoration: none!important;
			-moz-outline-style: none;
		}

</style>

<script>

		$(".cancel").click(function(){
			
			var MemberNumber = $(this).parent().parent().next().val();
			
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
			xhr.send("action=CANCEL&invd_mem_num=" + MemberNumber);
			
		});
		
		
		
		$(".invite").click(function(){
			
			var MemberNumber = $(this).parent().parent().next().val();
			
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
			xhr.send("action=ADDFRIEND&invd_mem_num=" + MemberNumber);
			
		});

</script>



</head>

<body>
	<c:forEach var="memVOConfirm" items="${requestScope.memberList.get(0)}">
		<div class="col-sm-4 memberlist">
				<div class="card hovercard">
					<c:if test="${memVOConfirm.sex=='男'}" var="varName">
						<div class="man cardheader"></div>
					</c:if>
					<c:if test="${!varName}">
						<div class="women cardheader"></div>
					</c:if>
					<div class="avatar">
					<a href='#memberProfile${memVOConfirm.mem_num}' data-toggle="modal" >
						<img alt="" src="<%=request.getContextPath()%>/getPic?mem_num=${memVOConfirm.mem_num}">
						</a>
					</div>
					<div class="info">
						<div class="title">${memVOConfirm.mem_name}</div>
						<div class="desc">${memVOConfirm.sex}</div>
					</div>
					<div class="bottom">
						<c:if test="${friendCheckList.contains(memVOConfirm)}" var="isInvited" scope="page">
							<p id="sendedMessage">對方已向您送出邀請</p>
						</c:if>
						<c:if test="${memberList.get(1).contains(memVOConfirm)}" var="isInvite" scope="page">
							<button type="submit" class="btn btn-danger cancel" name="action" value="CANCEL">取消邀請</button>
						</c:if>
						<c:if test="${!isInvite && !isInvited}">
							<button type="submit" class="btn btn-success invite" name="action" value="ADDFRIEND">邀請好友</button>
						</c:if>
					</div>
				</div>
				<input type="hidden" name="invd_mem_num" value="${memVOConfirm.mem_num}">
				
				
				<c:set var="memid" value="${memVOConfirm.mem_num}"/>
											
				<%@ include file="/front-end/MemberDetail/MemberDetail.jsp" %>
				
		</div>
	</c:forEach>
</body>
</html>