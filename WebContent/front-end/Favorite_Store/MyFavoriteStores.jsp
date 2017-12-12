<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.favorite_store.model.*"%>


<script>
	$(document).ready(function() {
		
		$(".deletes").click(function(){
			
			var stoTarget = $(this).parent().parent().parent().next();
			
			var xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function() {

				if (xhr.readyState == 4) {
					if (xhr.status == 200) {

						$("#favoriteStoreContent").empty();
						$("#favoriteStoreContent").append(xhr.responseText);

					} else {
						
						alert("not found");
					}
				}
			};

			var url = "<%=request.getContextPath()%>/FavoriteStore/FSC.html";
			xhr.open("POST", url, true);
			xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
			xhr.send("action=DELETE&sto_num=" + stoTarget.val());
		});
	});
</script>


			<c:forEach var="storeVO" items="${favoStoreList}">
				<div class="col-sm-3 stores">
					
						<div class="card">
							<img class="card-img-top" src="<%=request.getContextPath()%>/getPic?sto_num=${storeVO.sto_num}">
							<div class="card-block">
								<figure class="profile">
									<button type="button" style="border: 0; background: transparent" class="deletes">
										<img src="<%=request.getContextPath()%>/img/favorite.png" class="profile-avatar favorite" alt=""> 
										<img src="<%=request.getContextPath()%>/img/remove.jpg" class="profile-avatar remove" alt="" style="display: none;">
									</button>
								</figure>
								<h4 class="card-title mt-3">${storeVO.sto_name}</h4>
								<div class="meta">
									${storeVO.mobile}
								</div>
								<div class="card-text">${storeVO.area}${storeVO.address}</div>
							</div>
							<div class="card-footer">
								<c:if test="${storeVO.sto_status=='未上架'}">
									<p class="text-center">目前已下架</p>
								</c:if>

								<c:if test="${storeVO.sto_status=='停權'}">
									<p class="text-center">店家已被停權</p>
								</c:if>

								<c:if test="${storeVO.sto_status=='已上架'}">
									<center>
									<a href="<%=request.getContextPath()%>/store_detail/store_detail.do?sto_num=${storeVO.sto_num}">
										<button class="btn btn-success" name="action" value="TRANSFER">GO!</button>
									</a>
									</center>
								</c:if>
							</div>
						</div>
						<input type="hidden" name="sto_num" value="${storeVO.sto_num}">
				
				</div>
			</c:forEach>
