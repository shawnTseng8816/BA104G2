<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ page import="java.util.*"%>
<%@ page import="com.coupon.model.*"%>
<%@ page import="com.store_profile.model.*"%> 
<jsp:useBean id="cpSvc" scope="request" class="com.coupon.model.CouponService" />
<jsp:useBean id="spSvc" scope="request" class="com.store_profile.model.StoreProfileService" />
<jsp:useBean id="adSvc" scope="request" class="com.shop_ad.model.ShopAdService" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<link rel="icon" href="<%= request.getContextPath() %>/img/favicon.ico" type="image/x-icon" /> 
<link rel="shortcut icon" href="<%= request.getContextPath() %>/img/favicon.ico" type="image/x-icon" />  
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/member_base.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"><!-- 星星 -->

<title>揪茶趣:訂飲料好方便</title>
 
<style>

   

	.adimg{
		width:100%;
		height:500px;
	}
	
	.checked {
    	color:  #ffd280;
    	font-size:16pt;
	}	
	
	.star-gray{
		color:#CCCCCC;
		font-size:16pt;
	}

	#comment-rank{
		background-color: #3C9682;
		color:#FFFFFF;
		font-size: 16pt;
    	letter-spacing: 3pt;		
	}

 	#carousel-ctrl .item img{ 
 		height:100%;
 	} 

	.bg-fix{
		position: relative;	
		height:670px;
	}

	.s1{
		height: 800px;
		color: #FFFFFF;
		background-image: url('<%= request.getContextPath() %>/img/bg02.jpg');
		border: 1px solid #cccccc;
	}
	
	.arty{
		margin: auto;
		height: 1020px;
		padding: 40px;
		background-repeat: no-repeat;
		background-position: center center;
		background-size: cover;
		background-attachment: fixed; 
	}

			


	</style>	


</head>
 
<body>
<% 
	ServletContext context = getServletContext();
	List<Map.Entry<String, Integer>> list_RankData  = (List<Map.Entry<String, Integer>>)context.getAttribute("list_RankData");
	StoreProfileService stSvc = new StoreProfileService();
%>


<jsp:include page="/front-end/member_top.jsp" />
<jsp:include page="/front-end/coupon_notify.jsp" />



<div class="bg-fix">

<div class="container-fluid area50">
	<div class="row">

		<!-- 好評排行-->

		<div class="col-xs-12 col-sm-2 col-sm-offset-1 radius5 panel">
		
			<div class="panel-heading text-center" id="comment-rank">好評排行榜</div>
			
				<%	int count = 0; %>
				<% for ( Map.Entry<String, Integer> Key  : list_RankData){ %>
				<%	if(count<5){ %>	
				<div class="panel-body table good-to-drink ">
					<div class="gd-left">
						<img class="imgsize"  src="<%= request.getContextPath()%>/StoGifReader?sto_num=<%= Key.getKey() %>">
					</div>
					<div class="gd-right">
						<p class="store-name">
						<a href = "<%= request.getContextPath()%>/store_detail/store_detail.do?sto_num=<%= Key.getKey() %>">
						<%= stSvc.getOneStoName(Key.getKey()).getSto_name() %>
						</a>
						</p>						
						<span class="rank-style"></span>
						<span class="rank-point">  <%= Key.getValue() %> </span>
					</div>
				</div>
				<%	count ++; %>		
				<% }} %>
		</div>

		<!-- 店家廣告 -->
			
		<div class="col-xs-12 col-sm-8 col-sm-offset-0">
		
			<div id="carousel-id2" class="carousel slide" data-ride="carousel">
			    <!-- 幻燈片小圓點區 -->
			    <ol class="carousel-indicators">
				    <c:forEach varStatus="s" items="${adSvc.getAllBySa_addmode('上架')}">
				    	<li data-target="#carousel-id2" data-slide-to="${s.index}" class="${s.first?'active':''}"></li>
				    </c:forEach>
			    </ol>
			    <!-- 幻燈片主圖區 -->
			    <div class="carousel-inner ">
				    <c:forEach varStatus="s" var="adVO" items="${adSvc.getAllBySa_addmode('上架')}">
				     	<div class="item ${s.first?'active':''}">
				        	<img class="adimg" src="<%= request.getContextPath()%>/GetPic?getType=shopad&sa_no=${adVO.sa_no}" alt="">
					    </div>
				    </c:forEach>
			    </div>
			    <!-- 上下頁控制區 -->
<!-- 				    <a class="left carousel-control" href="#carousel-id2" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a> -->
<!-- 				    <a class="right carousel-control" href="#carousel-id2" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a> -->
			</div>
		</div>
		

	</div>  <!--功能區塊row end-->
</div>   <!--功能區塊container end-->


</div><!-- "bg-fix" -->

<div class="s1 arty col-xs-12 col-sm-12">
	<div class="col-xs-12 col-sm-10 col-sm-offset-1 ">
		<jsp:include page="storeListAll.jsp" />
	</div>
</div>




<!--footer-->
<jsp:include page="/front-end/member_foot.jsp" />

		<script>
		$(document).ready(function () {
			
			
			//評分顯示星星
			$(".rank-style").each(function(){
				var rating = parseInt($(this).next().text());
				for(var i =1 ; i<6 ;i++){
					if(i<=rating){						
						$(this).append('<span class="fa fa-star checked"></span>');
					}else{
						$(this).append('<span class="fa fa-star star-gray"></span>');
					}
				}
			});
			
			
		});	
		</script>
</body>
</html>
