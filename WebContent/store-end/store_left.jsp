<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="spSvc" scope="request" class="com.store_profile.model.StoreProfileService" />
<html>
<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<title>店家首頁</title>

		<style>

			#staff-info {
				margin:auto;
				text-align:center;
				font-size:24px;
				font-weight:bolder;
				color:#595942
			}

		</style>
	
	</head>
<body>	

<div class="container-fluid">
	<div class="row">
		<div class="col-xs-12 col-sm-2 col-sm-offset-1 navbars navbar-fixed-top area50">

				
			<div class="panel panel-default ">
					<!-- 店家圖片 -->

					<div class="panel-body">
						
						    <img class="imgsize thumbnail center-block area20" width="100" height="100" src="<%= request.getContextPath()%>/StoGifReader?sto_num=${sessionScope.sto_num}">
						    <a href="<%= request.getContextPath() %>/store-end/store/storeProfile.jsp" class="center-block">
						   		<div id="staff-info">${spSvc.getOneByPrimary(sessionScope.sto_num).sto_name}<small class="text-muted"><div class="glyphicon glyphicon-pencil"></div></small></div>				   		
						   	</a>
					    
					</div>
				

					<!--左邊功能伸縮 panel-->
					<div class="panel-group text-center mgrb" id="accordion1" role="tablist" aria-multiselectable="true">

						<!--  1.訂單管理 -->
						<div class="panel panel-default">
							<div class="panel-heading" role="tab" id="panel1">				      
						        <a href="#func1" data-parent="#accordion1" data-toggle="collapse" role="button" aria-expanded="true" aria-controls="func1">
						          <h4 class="panel-title funcbtn-normal ">訂單管理</h4>
						        </a>				      
						    </div>
						    <div id="func1" class="panel-collapse collapse " role="tabpanel" aria-labelledby="panel1">
						      	<div class="list-group">
						      		<a href="<%= request.getContextPath() %>/store-end/order/order.jsp" class="list-group-item">瀏覽訂單</a>
						            <a href="<%= request.getContextPath() %>/store-end/keeprecord/keeprecord.jsp" class="list-group-item">瀏覽客戶寄杯</a>						        	
						        </div>	     
						    </div>
						</div>

						<!-- 2.商品管理 -->
						<div class="panel panel-default ">
						    <div class="panel-heading" role="tab" id="panel2">	
						    	 <a href="#func2" data-parent="#accordion1" data-toggle="collapse" role="button" class="collapsed" aria-expanded="false" aria-controls="func2">		      
						        	<a href="<%= request.getContextPath() %>/store-end/pdc_mng/store_select_page.jsp" >
						        	<h4 class="panel-title funcbtn-normal">商品管理</h4>					        
						        	</a>
						       		
						        </a>				      
						    </div>
						</div>

						<!-- 3.店家資訊 -->
						<div class="panel panel-default">
						    <div class="panel-heading" role="tab" id="panel3">
						        <a href="#func3" data-parent="#accordion1" data-toggle="collapse" role="button" class="collapsed" aria-expanded="false" aria-controls="func3">
						          <div class="panel-title funcbtn-normal">店家資訊</div>
						        </a>
						    </div>
						    <div id="func3" class="panel-collapse collapse" role="tabpanel" aria-labelledby="panel3">
						      	<div class="list-group">
						        	<a href="<%= request.getContextPath() %>/store-end/store/storeProfile.jsp" class="list-group-item">修改店家資訊</a>
						        	<a href="<%= request.getContextPath() %>/store-end/store/storeMessage.jsp" class="list-group-item">瀏覽店家評論</a>
						        </div>	
						    </div>
						</div>

						<!-- 4.點數管理 -->
						<div class="panel panel-default">
						    <div class="panel-heading" role="tab" id="panel4">
						        <a href="#func4" data-parent="#accordion1" data-toggle="collapse" role="button" class="collapsed" aria-expanded="false" aria-controls="func4">
						          <div class="panel-title funcbtn-normal">點數管理</div>
						        </a>
						    </div>
						    <div id="func4" class="panel-collapse collapse" role="tabpanel" aria-labelledby="panel4">
						      	<div class="list-group">
						        	<a href="<%= request.getContextPath() %>/store-end/point/stoPoint.jsp" class="list-group-item">查詢點數餘額</a>
						        	<a href="<%= request.getContextPath() %>/store-end/point/remPoint.jsp" class="list-group-item">點數提領</a>
						        </div>	
						    </div>
						</div>

						<!-- 5.廣告管理 -->
						<div class="panel panel-default">
						    <div class="panel-heading" role="tab" id="panel5">
						        <a href="#func5" data-parent="#accordion1" data-toggle="collapse" role="button" class="collapsed" aria-expanded="false" aria-controls="func5">
						          <div class="panel-title funcbtn-normal">廣告管理</div>
						        </a>
						    </div>
						    <div id="func5" class="panel-collapse collapse" role="tabpanel" aria-labelledby="panel5">
						      	<div class="list-group">
						        	<a href="<%= request.getContextPath() %>/store-end/storeprofile/addad.jsp" class="list-group-item">新增廣告</a>
						        	<a href="<%= request.getContextPath() %>/store-end/storeprofile/adlist.jsp" class="list-group-item">廣告紀錄</a>
						        </div>	
						    </div>
						</div>

						<!-- 6.折價券管理 -->
						<div class="panel panel-default">
						    <div class="panel-heading" role="tab" id="panel6">
						        <a href="#func6" data-parent="#accordion1" data-toggle="collapse" role="button" class="collapsed" aria-expanded="false" aria-controls="func6">
						          <div class="panel-title funcbtn-normal">折價券管理</div>
						        </a>
						    </div>
						    <div id="func6" class="panel-collapse collapse" role="tabpanel" aria-labelledby="panel6">
						      	<div class="list-group">
						        	<a href="<%= request.getContextPath() %>/store-end/coupon/couponApply.jsp" class="list-group-item">新增折價券</a>
						        	<a href="<%= request.getContextPath() %>/store-end/coupon/stoCoupon.jsp" class="list-group-item">折價券紀錄</a>
						        </div>
						    </div>
						</div>

						<!-- 7.檢舉專區 -->
						<div class="panel panel-default">
						    <div class="panel-heading" role="tab" id="panel7">
						        <a href="#func7" data-parent="#accordion1" data-toggle="collapse" role="button" class="collapsed" aria-expanded="false" aria-controls="func7">
						          <div class="panel-title funcbtn-normal">檢舉專區</div>
						        </a>
						    </div>
						    <div id="func7" class="panel-collapse collapse" role="tabpanel" aria-labelledby="panel7">
						      	<div class="list-group">
						        	<a href="<%= request.getContextPath() %>/reportStore/reportStore.do" class="list-group-item active">檢舉紀錄</a>
						        	<a href="#" class="list-group-item">聯繫後台人員</a>
						        </div>
						    </div>
						</div>

						<!-- 8.教學專區 -->
						<div class="panel panel-default">
						    <div class="panel-heading" role="tab" id="panel8">
						    	<a href="#func8" data-parent="#accordion1" data-toggle="collapse" role="button" class="collapsed" aria-expanded="false" aria-controls="func8" >
						           <div class="panel-title funcbtn-normal">教學專區</div>
						        </a>
						    </div>
						    <div id="func8" class="panel-collapse collapse" role="tabpanel" aria-labelledby="panel8">
						      	<div class="list-group">
						        	<a href="#" class="list-group-item">店家資訊維護教學</a>
						        	<a href="#" class="list-group-item">訂單處理教學</a>
						        	<a href="#" class="list-group-item">廣告發佈教學</a>
						        	<a href="#" class="list-group-item">折價券申請教學</a>
						        </div>
						    </div>
						</div>				

				</div><!--左邊功能伸縮 panel-->
				
			</div>	<!-- div class="panel panel-default "> -->
						
		</div>
	</div>		
</div>
	
	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<script >
			

			// 改 panel外面 hover 顏色
			$("[class~='panel-heading']").hover(function(){$(this).css("background-color","#DCE6D2")},function(){$(this).css("background-color"," #FFFFFF")});
			
			// 改 panel裡面 hover 顏色
			$(".list-group > a").hover(function(){
				
				if(!$(this).hasClass('active')){
					$(this).css({"background-color":"#DCE6D2","color":"#595942"});
				}
			},function(){
				if(!$(this).hasClass('active')){
					$(this).css({"background-color":"#FFFFFF","color":"#595942"});
				}
			});
			
			$(".list-group > [class|='list-group-item']").on('click',function(){
				$(".list-group > a").removeClass('active').css({"background-color":"#FFFFFF","color":"#595942"});			
				$(this).addClass("active");
				$(this).css({"background-color":"#3C9682","color":"#FFFFFF"});
			});
			
			
			//目前active頁面
// 			var loc = window.location.pathname;
// 			console.log(loc);
// 			$('.panel-collapse').find('a').each(function() {
// 			     $(this).toggleClass('active', $(this).attr('href') == loc);
// 			  });
// 			$('.active').css("background","#3C9682");

		</script>	
</body>
</html>