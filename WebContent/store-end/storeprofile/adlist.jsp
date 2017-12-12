<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.shop_ad.model.*"%>
<%@ page import="com.store_profile.model.*"%>
<%@ page import="com.getpic.controller.*"%>

<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	StoreProfileVO storeprofileVO = (StoreProfileVO) session.getAttribute("storeprofileVO");
	ShopAdService shopadSrc = new ShopAdService();
	String sto_num = storeprofileVO.getSto_num();
	List<ShopAdVO> shopadlist = shopadSrc.getAllBySto_num(sto_num);
	request.setAttribute("shopadlist", shopadlist);
%>


<html>
<head>
<title>廣告紀錄</title>

	<style type="text/css">
		.body{
			font-family:Microsoft JhengHei;
		}
		.caption {
			font-size: 36px;
			color:#3C9682;
			font-weight:bold;
		}
		table{
			border:1px solid #3C9682; 
			table-layout:automatic;
			border-collapse: collapse;
/* 			width: 1600px; */
 			word-break: keep-all;
/*  			word-wrap:break-word; */
/*   		    word-break:break-all; */
/*   		    word-break: normal; */
		}
/* 		:after, :before{ */
/* 			width:500px; */
/* 		} */
		td, th{
			padding:5px;
			border:1px solid #3C9682; 
			border-style:dashed;
			/*https://www.1keydata.com/css-tutorial/tw/border.php*/ 
			color:#555;
			vertical-align: middle;
		}
		tr:hover{
			font-weight: bold;
			background-color:#f3f3f3;
		}
		.headerrow{ 
			background-color:#3C9682 !important;
		}
		.headerrow th{
			border:1px solid #3C9682; 
			color:#ffffff; 
			text-align:center;
		}
		tr:nth-child(even) {
			background-color:#cbd8d6;
		}
		tr:nth-child(odd) {
			background-color:#ffffff;
			font-color:#555555;
		}
		button, html input[type=button], input[type=reset], input[type=submit]{
			-webkit-appearance:button;
			cursor:pointer;
			border-color:#3C9682;
			color:#3C9682;
			background-color:#ffffff;
			border:1px #3C9682;
			border-style:solid;
			font-family:Microsoft JhengHei;

		}
		button, html input[type=button], input[type=reset], input[type=submit]:hover{
			border-color:#3C9682;
			color:#ffffff;
			background-color:#3C9682; 
		}
		div#contain{
			height:100px;
			border-radius: 10px;
			overflow-y:scroll;
			overflow-x:no;
			-webkit-overflow-scrolling: touch;
			text-align:left;
/* 			https://scotch.io/tutorials/customize-the-browsers-scrollbar-with-css */
/* 			https://codepen.io/devstreak/pen/dMYgeO */
		}
		
		#contain::-webkit-scrollbar-track{
			-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
			border-radius: 10px;
			background-color: #F5F5F5;
		}

		#contain::-webkit-scrollbar{
			width: 12px;
			height:12px;
			border-radius: 10px;
			background-color: #F5F5F5;
		}

		#contain::-webkit-scrollbar-thumb{
			border-radius: 10px;
			-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
			background-color: #3C9682;
		}
		
		#outtag{
			width:150px;  !important
		}
		
		td>img{
/* 		 	position:relative; */
			width:60%; 
			border-radius: 5px;
    		cursor: pointer;
    		transition: 0.3s;
		}

        td>img:hover{
			float:left;
            width: 150%;
            position: relative;
            border: 1px dotted #3C9682;
		}

	</style>

</head>
<body bgcolor='white'>


<jsp:include page="/store-end/store_top.jsp" /> <!-- navbar -->
	<!-- 1層大框框 -->
	<div class="container-fluid">
		<div class="row">
	<!-- 2層框左 -->	
			<jsp:include page="/store-end/store_left.jsp" /> <!-- leftSidebar -->
	<!-- 2層框右 -->		
			<div class="col-xs-12 col-sm-8 col-sm-offset-3" >				
				<div class="block-center panelheight">
				
	<!--========================== 功能放這邊 ↓↓↓↓↓↓===============================-->

					<div class="caption">廣告紀錄</div>
					<div>
						<font class="storeowner" style="font-size:28px; color:#000000; font-weight:bold;">${storeprofileVO.getSto_name()}商店：${storeprofileVO.getGuy()}老闆您好</font>
					</div>
				
					<table id="block">
					
						<%-- 錯誤表列 --%>
						<c:if test="${not empty errorMsgs}">
							<ul>
								<img src="<%= request.getContextPath() %>/img/xx.png" height="30" width="30">
								<c:forEach var="message" items="${errorMsgs}">
									<li style="color:red">${message}</li>
								</c:forEach>
							</ul>
						</c:if>
						<tr>
							<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/store-end/storeprofile/sto.do" style="margin-bottom: 0px;">
						    	<input type="submit" value="新增" >
								<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
						    	<input type="hidden" name="action"	value="ad_insert">
						    </FORM>
						</tr>
						<tr class="headerrow">
				<!-- 			<th>廣告編號</th> -->
				<!-- 			<th>店家編號</th> -->
							<th>廣告標題</th>
							<th>廣告內容文字</th>
							<th>廣告圖片</th>
						
							<th>廣告申請時間</th>
							<th>廣告上架時間</th>
							<th>預計下架時間</th>
							<th>廣告區塊</th>
							<th>廣告上架狀態</th>
							
							<th>修改</th>
							<th>審核申請</th>
						</tr>
						
						<c:forEach var="shopadVO" items="${shopadlist}">	
							<tr align='center' valign='middle'>
								<td  style="display:none;">${shopadVO.getSa_no()}</td>
								<td  style="display:none;">${shopadVO.getSto_num()}</td>
								<td>${shopadVO.getSa_title()}</td>
								<td>${shopadVO.getSa_text()}</td>
								<td><img src="/BA104G2/GetPic?sa_no=${shopadVO.getSa_no()}&getType=shopad" width="50%"></td>
									<!-- Sa_img() -->
								
								<td>${shopadVO.getSa_apptime()}</td>
								<td>${shopadVO.getSa_addtime()}</td>
								<td>${shopadVO.getSa_preofftime()}</td>
								<jsp:useBean id="adblockSvc" scope="page" class="com.ad_block.model.AdBlockService" />
								<td>
									<c:forEach var="adblockVO" items="${adblockSvc.all}">
										<c:if test="${shopadVO.ab_no==adblockVO.ab_no}">
										${adblockVO.ab_name}
										</c:if>
									</c:forEach>
								</td>
								<td>${shopadVO.getSa_addmode()}</td>
								
								<td>
								<c:if test="${shopadVO.sa_addmode=='廣告編輯中'}">
								<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/store-end/storeprofile/sto.do" style="margin-bottom: 0px;">
							    	<input type="submit" value="修改">
									<input type="hidden" name="requestURL"	value="<%=request.getContextPath()%>">
									<input type="hidden" name="sa_no"      value="${shopadVO.getSa_no()}">
									<input type="hidden" name="action"	value="ad_update">
							    </FORM>
							    </c:if>
								</td>
								<td>
								<c:if test="${shopadVO.sa_addmode=='廣告編輯中' }">
									<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/store-end/storeprofile/sto.do" style="margin-bottom: 0px;">
								    	<input type="submit" value="確定送出">
								    	<input type="hidden" name="sto_status"  value="${shopadVO.getSto_num()}">
								    	<input type="hidden" name="sa_no"      value="${shopadVO.getSa_no()}">
										<input type="hidden" name="requestURL"	value="<%=request.getContextPath()%>">
								    	<input type="hidden" name="action"	value="adlist_to_bm">
								    </FORM>
								</c:if>
								</td>
							</tr>
						</c:forEach>		
					
					</table>
					<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">


<!--========================== 功能放這邊 ↑↑↑↑↑↑↑=======================================-->			
				</div><!-- class="block-center panelheight" -->			
			</div><!-- class="col-xs-12 col-sm-8 col-sm-offset-3" -->
		</div><!-- div class="row" -->
	</div><!-- div class="container-fluid" -->
	
	<jsp:include page="/store-end/store_foot.jsp" />

	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	
</body>

</html>