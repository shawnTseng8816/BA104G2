<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.store_profile.model.*"%>
<%@ page import="com.product.model.*"%>
<%@ page import="com.getpic.model.*"%>
<%@ page import="com.store_image.model.*"%>
<%@ page import="com.store_comment.model.*"%>
<%@ page import="com.favorite_store.model.*"%>
<%
	StoreProfileVO storeProfileVO = (StoreProfileVO) request.getAttribute("storeProfileVO");
	List<ProductVO> productList = (List<ProductVO>)request.getAttribute("getProductImageList");
	List<StoreImageVO> storeImageList = (List<StoreImageVO>)request.getAttribute("storeImageList");
	List<StoreCommentVO> storeCommentList = (List<StoreCommentVO>) request.getAttribute("storeCommentList");
	FavoriteStoreVO favoriteStoreVO = (FavoriteStoreVO) request.getAttribute("favoriteStoreVO");
	pageContext.setAttribute("storeCommentList",storeCommentList);
%>


<jsp:include page="/front-end/member_top.jsp" />

<!DOCTYPE html>
<html lang="">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<title>Title Page</title>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
		<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
		<script src="<%=request.getContextPath()%>/js/dist/sweetalert2.all.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/dist/sweetalert2.min.js"></script>
		
		<style type="text/css">
			.logo img{
				width: 85%;
			}
			
			.page-header{
				border-bottom: 5px solid #cccccc;
			}
			
			.order,.more{
				margin-right: 0;
				margin-top: 2em;
			}
			
			.com_title,.hot_title,p{
				font-size: 20px;
			}
			
			.panel-title{
				font-size: 25px;
				font-weight: bold;
			}
			
			.modal{
				text-align: center;
				padding: 0!important;
			}
			
			.modal:before{
				content: '';
				display: inline-block;
				height: 100%;
				vertical-align: middle;
				margin-right: -4px;
			}
			
			.modal .modal-dialog{
				display: inline-block;
				text-align: left;
				vertical-align: middle;
			}
			
//商品列表  .gallery > div:hover {
			  	z-index: 1;
			}
						
			.gallery {
		        width: 1060px;
		        margin: 0 auto;
		        padding: 5px;
		    }
		
		    .gallery > div {
		        position: relative;
		        float: left;
		        padding: 5px;
		    }
		
		    .gallery > div > img {
		        display: block;
		        width: 100px;
		        transition: .1s transform;
		        transform: translateZ(0); 
		    }
		
		    .gallery > div:hover > img {
		        transform: scale(2,2);
		        transition: .2s transform;
		    }
		
		    .img-responsive {
		          -webkit-box-shadow: 0px 2px 6px 2px rgba(0,0,0,0.75);
		          -moz-box-shadow: 0px 2px 6px 2px rgba(0,0,0,0.75);
		          box-shadow: 0px 2px 6px 2px rgba(0,0,0,0.75);
		          margin-bottom:20px;
		    }
		
		     .img-responsive:hover {
		        filter: none;
		        -webkit-filter: grayscale(0); 
			}
			
			.breakAll{word-break:break-all;
			}
			
			.logo{
				margin: 0 auto;
				width:400px;
			}
			
			p{
			  font-size: 14px;
			}
			
			h3,h1{
			  color: #f00;
			}  	
			
/*評價星星*/	.star-vote{		
                width:120px;
                height:20px;
                position:relative;
                overflow:hidden;
            }
            
            .star-vote>span{
                position:absolute;
                width:120px;
                height:20px;
                background-size:cover;
            }
            
            .add-star{
                background-image:url("/BA104G2/img/stars.png");
            }
            
            .del-star{
                background-image:url("/BA104G2/img/stars1.png");
/*評價星星*/	}	
            

/*按鈕變色3D*/
			.btn3d {
	          	position:relative;
	          	top: -6px;
	         	border:0;
	           	transition: all 40ms linear;
	           	margin-top:10px;
	           	margin-bottom:10px;
	           	margin-left:2px;
	           	margin-right:2px;
	     	 }
	     	 .btn3d:active:focus,
	     	 .btn3d:focus:hover,
	     	 .btn3d:focus {
	          	-moz-outline-style:none;
	            outline:medium none;
	      	 }
	     	 .btn3d:active, .btn3d.active {
	          	top:2px;
	      	 }
	
	     	 .btn3d.btn-success {
	          	box-shadow:0 0 0 1px #31c300 inset, 0 0 0 2px rgba(255,255,255,0.15) inset, 0 8px 0 0 #5eb924, 0 8px 8px 1px rgba(0,0,0,0.5);
	          	background-color:#78d739;
	      	 }
	
	      	.btn3d.btn-success:active, .btn3d.btn-success.active {
	          	box-shadow:0 0 0 1px #30cd00 inset, 0 0 0 1px rgba(255,255,255,0.15) inset, 0 1px 3px 1px rgba(0,0,0,0.3);
	          	background-color: #78d739;
	      	}
	
	      	.btn3d.btn-danger {
	          	box-shadow:0 0 0 1px #b93802 inset, 0 0 0 2px rgba(255,255,255,0.15) inset, 0 8px 0 0 #AA0000, 0 8px 8px 1px rgba(0,0,0,0.5);
	          	background-color:#D73814;
	      	}
	
	      	.btn3d.btn-danger:active, .btn3d.btn-danger.active {
	          	box-shadow:0 0 0 1px #b93802 inset, 0 0 0 1px rgba(255,255,255,0.15) inset, 0 1px 3px 1px rgba(0,0,0,0.3);
	          	background-color: #D73814;
	      	}
	      
	      	.btn3d.btn-info {
	          	box-shadow:0 0 0 1px #1aa3ff inset, 0 0 0 2px rgba(255,255,255,0.15) inset, 0 8px 0 0 #0099ff, 0 8px 8px 1px rgba(0,0,0,0.5);
	          	background-color:#4db8ff;
	      	}
	
	      	.btn3d.btn-info:active, .btn3d.btn-success.active {
	          	box-shadow:0 0 0 1px #1aa3ff inset, 0 0 0 1px rgba(255,255,255,0.15) inset, 0 1px 3px 1px rgba(0,0,0,0.3);
	          	background-color:#4db8ff;
	      	}
	      	textarea{
 	      	resize:none;
			}
/*按鈕變色3D*/	      	
		</style>
	</head>
	<body>
<!-- login ===================================================================================== -->							        
		<div class="modal fade" id="loginModal">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="text-center">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h1 class="modal-title">請登入/註冊會員</h1>
						<br><br>
						<div class="col-xs-12 col-sm-6">
							<form method="get" action="<%=request.getContextPath() %>/front-end/memberprofile/login.jsp">
								<button type="submit" class="btn btn-primary">確定</button>
							</form>
						</div>
						<div class="col-xs-12 col-sm-6">
							<button type="button" class="btn btn-default" data-dismiss="modal">離開</button>
						</div>
						<br><br>
					</div>
				</div>
			</div>
		</div>
		<!-- detail ===================================================================================== -->
		<div id="carousel-id" class="carousel slide" data-ride="carousel">
		    <!-- 幻燈片小圓點區 -->
		    <ol class="carousel-indicators">
		    	<c:forEach var="storeImageVO" items="${storeImageList}" varStatus = "s">
		    		<c:if test = "${s.index==0}" var="result">
		    			<li data-target="#carousel-id" data-slide-to="${s.index}" class = "active"></li>
		    		</c:if>
		        	<c:if test = "${!result}">
		    			<li data-target="#carousel-id" data-slide-to="${s.index}" class = " "></li>
		    		</c:if>
		        </c:forEach>
		    </ol>
		    <!-- 幻燈片主圖區 -->
		    <div class="carousel-inner">				
		    	<c:forEach var="storeImageVO" items="${storeImageList}" varStatus = "s">
			        <c:if test = "${s.index==0}" var="result">
			        		<div class="item active">
			        </c:if>
			        <c:if test = "${!result}" >
			       			<div class="item">
			        </c:if>
			        	<a href="">
			            	<img src="/BA104G2/GetPicShawn?sto_num=${storeImageVO.getSto_num()}&img_num=${storeImageVO.getImg_num()}&getType=storeImage">
			            </a>
			        </div>
		        </c:forEach>
		    </div>
		    <!-- 上下頁控制區 -->
		    <a class="left carousel-control" href="#carousel-id" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a>
		    <a class="right carousel-control" href="#carousel-id" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
		</div>
		<!--under=============================================================================== -->
		<div class="under">
			<div class="container">
				<div class="row">
					<div class="col-xs-12 col-sm-2">
						<div class="panel panel-info">
						  <div class="panel-heading">
						    <h2 class="panel-title">店家詳情</h2>
						  </div>
						   <div class="list-group">
						  	<button href="#" class="list-group-item btn-info" id="sto_intr">店家介紹</button>
						   	<button  class="list-group-item btn btn-info" id="sto_news">商品情報</button>
						   	<button href="#" class="list-group-item btn btn-info" id="sto_comm">店家評論</button>
						   </div>
						</div>
					</div>
		<!--news=============================================================================== -->
					<div class="col-xs-12 col-sm-10">
						<div class="news" id="news" style="display:none;">
							<div class="col-xs-12 col-sm-12 page-header">
								<div class="container">
									<div class="row">
										<div class="col-xs-12 col-sm-7">
											<div class="title">
												<h1>>商品情報</h1>
											</div>
										</div>
										<div class="col-xs-12 col-sm-3">
											<div class="order text-right">
												<c:if test='${sessionScope.mem_num==null && sessionScope.sto_num==null && sessionScope.mem_bn_no==null}' var="nobody_order">
													<button class="btn btn-info btn3d"  href="#loginModal" data-toggle="modal" >訂購飲料</button>
												</c:if>
												<c:if test="${!nobody_order}">
													<c:if test='${sessionScope.mem_num!=null && sessionScope.sto_num==null && sessionScope.mem_bn_no==null}'>
<a class="btn btn-info btn3d" href="<%=request.getContextPath()%>/OrderMaster/OMC.html?action=Purchase&&currentSto_num=${sto_num}">訂購飲料</a></p>
													</c:if>
													<c:if test = "${sessionScope.mem_num==null && sessionScope.sto_num!=null && sessionScope.mem_bn_no==null}">
														<button class="btn btn-info btn3d" disabled="false">訂購飲料</button>
													</c:if>
													<c:if test = "${sessionScope.mem_num==null && sessionScope.sto_num==null && sessionScope.mem_bn_no!=null}">
														<button class="btn btn-info btn3d" disabled="false">訂購飲料</button>
													</c:if>
												</c:if>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="data">
								<div class="col-xs-12 col-sm-12">									
									<div class="gallery cf">
										<c:forEach var="productVO" items="${productList}">
        								<div class="col-xs-12 col-sm-2">
          									<img class="img-responsive" src="/BA104G2/GetPicShawn?sto_num=${productVO.getSto_num()}&com_num=${productVO.getCom_num()}&getType=product"/>
          									<h3>產品名:</h3>
          									<p>${productVO.getCom_name()}</p>

       									</div>
       									</c:forEach>
       									<c:if test="${productList.size()==0}">
											<div style="color:red; font-size:100px;">暫無資料!!</div>
										</c:if>
      								</div>
								</div>
							</div>
						</div>
		<!--comment=============================================================================== -->
						<div class="comment" id="comment" style="display: none;">
							<div class="col-xs-12 col-sm-12 page-header">
								<div class="container">
									<div class="row">
										<div class="col-xs-12 col-sm-7">
											<div class="title">
												<h1>>店家評論</h1>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="data">
								<table class="table table-hover">
									<thead>
										<tr>
											<th class="com_title">
												<div class="col-xs-12 col-sm-3">時間</div>
												<div class="col-xs-12 col-sm-5">評論標題</div>
												<div class="col-xs-12 col-sm-3">星星</div>
												<div class="col-xs-12 col-sm-1">檢舉</div>
											</th>
										</tr>
									</thead>
									<tbody>
									<%@ include file="StoreDetailPage1.file" %>
										<c:forEach var="storeCommentVO" items="${storeCommentList}" varStatus = "s" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
											
											
											<tr>
												<td>
													<div class="col-xs-12 col-sm-3">
													<a href='#memberProfile${storeCommentVO.mem_num}' data-toggle="modal" >${storeCommentVO.getCom_time()}</a></div>
													<div class="col-xs-12 col-sm-5">
														<a href="#commentt${s.index}" data-toggle="modal" >${storeCommentVO.getCom_title()}</a>
													</div>
													<div class="col-xs-12 col-sm-3">
														<div id="star_con" class="star-vote">
												            <span id="add_star" class="add-star"></span>
												            <span id="del_star" class="del-star"></span>
												        </div>
													</div>
													<div class="col-xs-12 col-sm-1 ">
														<c:if test='${sessionScope.mem_num==null && sessionScope.sto_num==null && sessionScope.mem_bn_no==null}' var="nobody_comment">
															<div class="btn btn-info btn3d" href="#loginModal" data-toggle="modal">檢舉</div>
														</c:if>
														<c:if test="${!nobody_comment}">
															<c:if test='${sessionScope.mem_num!=null && sessionScope.sto_num==null && sessionScope.mem_bn_no==null}'>
																<div class="btn btn-info btn3d"  href="#report_member" data-toggle="modal">檢舉</div>
															</c:if>
															<c:if test="${sessionScope.mem_num==null && sessionScope.sto_num!=null && sessionScope.mem_bn_no==null}">
																<div class="btn btn-info btn3d"  href="#report_store" data-toggle="modal">檢舉</div>
															</c:if>
															<c:if test="${sessionScope.mem_num==null && sessionScope.sto_num==null && sessionScope.mem_bn_no!=null}">
																<div class="btn btn-info btn3d"  disabled="false">檢舉</div>
															</c:if>
															
														</c:if>
													</div>
												</td>
											</tr>
											<div class="modal fade" id="commentt${s.index}" >
												<div class="modal-dialog">
													<div class="modal-content">
														<div class="modal-header">
															<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
															<h1 class="modal-title">標題:</h1>
															<h3 class="modal-title">${storeCommentVO.getCom_title()}</h3>
														</div>
														<div class="modal-body">
															<h4 class="modal-title">內容:</h4>
															<p>${storeCommentVO.getCommentt()}</p>
														</div>
														<div class="modal-footer">
															<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
														</div>
													</div>
												</div>
											</div>
											<div class="modal fade" id="report_member">
												<div class="modal-dialog">
													<div class="modal-content">
													<form method="get" action="/BA104G2/store_detail/store_detail.do" id="formContent">
														<div class="modal-header">
															<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
															<h3 class="modal-title">會員檢舉原因:</h3>
														</div>
														<div class="modal-body">
															<textarea class="form-control" rows="7"  name="mem_comment_text" id="textArea"></textarea>
														</div>
														<div class="modal-footer">
																<input type="hidden" name="action" value="mem_add">
										    					<input type="hidden" name="sto_num" value="${sto_num}">
										    					<input type="hidden" name="com_num" value="${storeCommentVO.getCom_num()}">
										    					<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
																<input type="hidden" name="whichPage"	value="<%=whichPage%>">   
																<input type="hidden" name="section"	value="3">   
															</form>	
															<button type="button" class="btn btn-primary" id="content">送出</button>
															<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
														</div>
														</form>
														
													</div>
												</div>
											</div>
											<div class="modal fade" id="report_store">
												<div class="modal-dialog">
													<div class="modal-content">
													
														<div class="modal-header">
															<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
															
															<h3 class="modal-title">店家檢舉原因:</h3>
														</div>
														<form method="get" action="/BA104G2/store_detail/store_detail.do"  id="formContent1">
														<div class="modal-body">
															<textarea class="form-control"  rows="7"  name="sto_comment_text" id="textArea1"></textarea>
														</div>
														<div class="modal-footer">
														
																<input type="hidden" name="action" value="sto_add">
										    					<input type="hidden" name="sto_num" value="${sto_num}">
										    					<input type="hidden" name="com_num" value="${storeCommentVO.getCom_num()}">
										    					<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
																<input type="hidden" name="whichPage"	value="<%=whichPage%>">
																<input type="hidden" name="section"	value="3">   
														</form>
																<button type="button" class="btn btn-primary" id="content1">送出</button>
															<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
														</div>
													
													</div>
												</div>
											</div>
										</c:forEach>
									</tbody>
								</table>
								<c:if test="${storeCommentList.size()==0}">
									<div style="color:red; font-size:100px;">暫無資料!!</div>
								</c:if>
								<%@ include file="StoreDetailPage2.file" %>
							</div>
						</div>
						
						
						<c:forEach var="storeCommentVO" items="${storeCommentList}" varStatus = "s" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
											
											<c:set var="memid" value="${storeCommentVO.mem_num}"/>
											
											<%@ include file="/front-end/MemberDetail/MemberDetail.jsp" %>
											
										
						</c:forEach>
										
	<!--logo & introduction============================================================================ -->
						<div class="introduction" id="introduction">
							<div class="container">
								<div class="row">
									<div class="col-xs-12 col-sm-6">
										<div class="logo">
											<img src="/BA104G2/GetPicShawn?sto_num=${storeProfileVO.getSto_num()}&getType=storeProfile">
										</div>
									</div>
									<div class="col-xs-12 col-sm-6">
										<div class="intr_text">
											<h1 class="text-center">店家簡介:</h1>
											<p>${storeProfileVO.getSto_introduce()}</p>
										</div>
										<br><br><br><br>
	<!-- favorty ===================================================================================== -->
										<div class="text-center">
											<c:if test='${sessionScope.mem_num==null && sessionScope.sto_num==null && sessionScope.mem_bn_no==null}' var="nobody_favorite">
												<button class="btn btn-lg btn-success btn3d glyphicon glyphicon-ok"  href="#loginModal" data-toggle="modal" >加入最愛</button>
											</c:if>
											<c:if test="${!nobody_favorite}">
												<c:if test='${sessionScope.mem_num!=null && sessionScope.sto_num==null && sessionScope.mem_bn_no==null}'>
													<c:if test = "${favoriteStoreVO.getIs_favo().equals('Y')}" var="its_friend">
														<form method="get" action="/BA104G2/store_detail/store_detail.do">
														    <input type="hidden" name="action" value="cancel">
														    <input type="hidden" name="mem_num" value="${mem_num}">
										    				<input type="hidden" name="sto_num" value="${sto_num}">
														    <button class="btn btn-lg btn-danger btn3d glyphicon glyphicon-remove">取消最愛</button>
														</form>
											    	</c:if>
											    	<c:if test = "${!its_friend}">
										    			<form method="get" action="/BA104G2/store_detail/store_detail.do">
										    				<input type="hidden" name="action" value="add">
										    				<input type="hidden" name="mem_num" value="${mem_num}">
										    				<input type="hidden" name="sto_num" value="${sto_num}">
													        <button class="btn btn-lg btn-success btn3d glyphicon glyphicon-ok ">加入最愛</button>
													    </form>
											    	</c:if>
												</c:if>
												<c:if test="${sessionScope.mem_num==null && sessionScope.sto_num!=null && sessionScope.mem_bn_no==null}">
											        <button class="btn btn-lg btn-success btn3d glyphicon glyphicon-ok" disabled="false">加入最愛</button>
											    </c:if>
											    <c:if test="${sessionScope.mem_num==null && sessionScope.sto_num==null && sessionScope.mem_bn_no!=null}">
											        <button class="btn btn-lg btn-success btn3d glyphicon glyphicon-ok" disabled="false">加入最愛</button>
											    </c:if>
										    </c:if> 
								        </div>
									</div>
								</div>
							</div>
						</div>
					</div>					
				</div>
			</div>
		</div>		
		
		<jsp:include page="/front-end/member_foot.jsp" />

		<script type="text/javascript">
			
			var news1 = document.getElementById("news");
			var com = document.getElementById("comment");
			var intr = document.getElementById("introduction");
			var stonews = document.getElementById("sto_news");
			var stocomm = document.getElementById("sto_comm");
			var stointr = document.getElementById("sto_intr");
			var content = document.getElementById("content");
			var content1 = document.getElementById("content1");
			var textArea = document.getElementById("textArea");
			var textArea1 = document.getElementById("textArea1");

			function news(){
				news1.style.display="";
				com.style.display="none";
				intr.style.display="none";
			}
			
			function comment(){
				news1.style.display="none";
				com.style.display="";
				intr.style.display="none";

				<c:set var="iii" value="0"/>

				<c:forEach var="storeCommentVO" items="${storeCommentList}" varStatus = "s" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
					stars(${storeCommentVO.stars} ,${iii});
					<c:set var="iii" value="${iii}+1"/>

	 			</c:forEach>
	 			
	 			
			}
			
			function stars(n,v){
				var con_wid = document.getElementsByClassName("add-star")[v].offsetWidth;
				var del_star=document.getElementsByClassName("del-star")[v];
                var del_move=(n*con_wid)/5;
                del_star.style.backgroundPosition=-del_move+"px 0px";
                del_star.style.left=del_move+"px";
                
               
			}
			
			function introductiont(){
				news1.style.display="none";
				com.style.display="none";
				intr.style.display="";
			}
			function checkContent(){
				var textAreaValue =textArea.value;
				if(textAreaValue.length==0){
					swal({
						  position: 'center',
						  type: 'error',
						  title: '請勿空白',
						  showConfirmButton: true,
						  timer: 2000
						})
				}else if(!isNaN(textAreaValue)){
					swal({
						  position: 'center',
						  type: 'error',
						  title: '請勿全數字',
						  showConfirmButton: true,
						  timer: 2000
						})
				}else if(textAreaValue.length<=7){
					swal({
						  position: 'center',
						  type: 'error',
						  title: '請勿少於8個字內',
						  showConfirmButton: true,
						  timer: 2000
						})
					
				}else{
					swal({
						  position: 'center',
						  type: 'success',
						  title: '已送出檢舉',
						  showConfirmButton: true,
						  timer: 2000
						})
						setTimeout("test()",2000);
					
				}
				
			}
			function checkContent1(){
				var textAreaValue =textArea1.value;
				if(textAreaValue.length==0){
					swal({
						  position: 'center',
						  type: 'error',
						  title: '請勿空白',
						  showConfirmButton: true,
						  timer: 2000
						})
				}else if(!isNaN(textAreaValue)){
					swal({
						  position: 'center',
						  type: 'error',
						  title: '請勿全數字',
						  showConfirmButton: true,
						  timer: 2000
						})
				}else if(textAreaValue.length<=7){
					swal({
						  position: 'center',
						  type: 'error',
						  title: '請勿少於8個字內',
						  showConfirmButton: true,
						  timer: 2000
						})
					
				}else{
					swal({
						  position: 'center',
						  type: 'success',
						  title: '已送出檢舉',
						  showConfirmButton: true,
						  timer: 2000
						})
						setTimeout("test1()",2000);
					
				}
				
			}
			function test1(){
				$('#formContent1').submit();
			}
			function test(){
				$('#formContent').submit();
			}
			function init(){
				stointr.onclick = introductiont;
				stonews.onclick = news;
				stocomm.onclick = comment;
				content.onclick = checkContent;
				content1.onclick = checkContent1;
			
				<c:if test="${section!=null}">
				if(${section} == "3"){
					
					news1.style.display="none";
					com.style.display="";
					intr.style.display="none";
						
						
					<c:set var="iii" value="0"/>

					<c:forEach var="storeCommentVO" items="${storeCommentList}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
						stars(${storeCommentVO.stars} ,${iii});
						<c:set var="iii" value="${iii}+1"/>

	 				</c:forEach>
				}
				</c:if>
			}

			window.onload=init;
		</script>
	
	</body>
</html>