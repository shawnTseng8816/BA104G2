<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.shop_ad.model.*"%>
<%@ page import="com.backstage_management.model.*"%>
<%@ page import="com.store_profile.model.*"%>
<%@ page import="com.getpic.controller.*"%>

<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
List<ShopAdVO> list = (List<ShopAdVO>)request.getAttribute("list");
StoreProfileService stoSvc = new StoreProfileService();
pageContext.setAttribute("stoSvc", stoSvc);
%>



<html>
<head>
<title>後台人員審核廣告</title>

	<style type="text/css">
		.body{
			font-family:Microsoft JhengHei;
		}
		.caption {
			font-size: 36px;
			color:#595942;
			font-weight:bold;
		}
		table{
			border:1px solid #595942; 
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
			border:1px solid #595942; 
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
			background-color:#595942 !important;
		}
		.headerrow th{
			border:1px solid #595942; 
			color:#ffffff; 
			text-align:center;
		}
		tr:nth-child(even) {
			background-color:#acaca0;
		}
		tr:nth-child(odd) {
			background-color:#ffffff;
			font-color:#555555;
		}
		button, html input[type=button], input[type=reset], input[type=submit]{
			-webkit-appearance:button;
			cursor:pointer;
			border-color:#595942;
			color:#595942;
			background-color:#ffffff;
			border:1px #595942;
			border-style:solid;
			font-family:Microsoft JhengHei;

		}
		button, html input[type=button], input[type=reset], input[type=submit]:hover{
			border-color:#595942;
			color:#ffffff;
			background-color:#595942; 
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
			background-color: #595942;
		}
		
		#outtag{
			width:150px;  !important
		}
		
		td>img{
/* 		 	position:relative; */
			width:100%; 
			border-radius: 5px;
    		cursor: pointer;
    		transition: 0.3s;
		}
		
		td>img:hover{
			float:left;
            width: 200%;
            position: relative;
            border: 1px dotted #595942;
		}
		
		.minizeStyle
        {
        	position: relative;
            width: 100%;
        }
        .maximizeStyle
        {
			float:left;
            width: 400%;
            position: relative;
            border: 1px dotted #595942;
        }
       
	</style>

</head>
<body bgcolor='white'>

<jsp:include page="/back-end/back_top.jsp" /> <!-- navbar -->
	<!-- 1層大框框 -->
	<div class="container-fluid">
		<div class="row">
	<!-- 2層框左 -->	
			<jsp:include page="/back-end/back_left.jsp" /> <!-- leftSidebar -->
	<!-- 2層框右 -->		
			<div class="col-xs-12 col-sm-8 col-sm-offset-3" >				
				<div class="block-center panelheight">
				
	<!--========================== 功能放這邊 ===↓↓↓↓↓↓↓↓↓↓==========================================-->
	
	<div class="caption">廣告驗證</div>

 <%@ include file="pages/page1.file" %>	
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<ul>
		<img src="<%= request.getContextPath() %>/img/xx.png" height="30" width="30">
		<c:forEach var="message" items="${errorMsgs}" >
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table id="block">

	<tr>
		後台人員：${backstagemanagementVO.getBm_name()}
	</tr>
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/backstagemanagement/BackstageManagementServletChi" >

	<tr>
       <b><font>選擇審核狀態:</font></b>

      		<select size="1" name="sa_addmode" size="45">
          	 		<option value="all" ${sa_addmode=="all"?"selected":""}>全部</option>
<%--           	 		<option value="edit" ${sa_addmode=="edit"?"selected":""}>廣告編輯中</option> --%>
          	 		<option value="onConfirm" ${sa_addmode=="onConfirm"?"selected":""}>待審核</option>
          	 		<option value="up" ${sa_addmode=="up"?"selected":""}>上架</option>
          	 		<option value="down" ${sa_addmode=="down"?"selected":""}>下架</option>
       	 	</select>
       	   <input type="hidden" name="action"	value="sa_addmode_select">
       	   <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
       	   <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller-->
		   <input type="submit" value="送出">

	</tr>
	</FORM>
	<br>

	<tr class="headerrow">
		<th>廣告編號</th>
		<th>店家名稱</th>
		<th>廣告標題</th>
		<th>廣告內容文字</th>
		<th>廣告圖片</th>
		
		<th>廣告申請時間</th>
		<th>廣告上架時間</th>
		<th>預計下架時間</th>
		<th>廣告區塊</th>
		<th>廣告上架狀態</th>
<!-- 		<th>付款時間</th> -->
<th>處理</th>
<!-- <th>審核失敗</th> -->
<!-- 		<th>修改</th> -->
<!-- 		<th>確認送出</th> -->
	</tr>



<c:forEach var="shopadVO" items="${list}"  begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" >	
		<tr align='center' valign='middle'>
			<td>${shopadVO.getSa_no()}</td>
			<td>${stoSvc.getOneStoName(shopadVO.getSto_num()).sto_name}</td>
			<td>${shopadVO.getSa_title()}</td>
			<td>${shopadVO.getSa_text()}</td>
			<td><img src="/BA104G2/GetPic?sa_no=${shopadVO.getSa_no()}&getType=shopad" width="50%"></td>
				<!-- Sa_img() -->
			
			<td>${shopadVO.getSa_apptime()}</td>
			<td>${shopadVO.getSa_addtime()}</td>
			<td>${shopadVO.getSa_preofftime()}</td>
			



			<jsp:useBean id="adblockSvc" scope="page" class="com.ad_block.model.AdBlockService" />
			<td><c:forEach var="adblockVO" items="${adblockSvc.all}">
					<c:if test="${shopadVO.ab_no==adblockVO.ab_no}">
					
					${adblockVO.ab_name}
					</c:if>
				</c:forEach>
			</td>
			<td>${shopadVO.getSa_addmode()}</td>
<!-- 			<td> -->
<%-- 				<c:if test = "${shopadVO.getSa_paytime()!=null}"> --%>
<%-- 					${shopadVO.getSa_paytime()} --%>
<%-- 				</c:if> --%>
<%-- 				<c:if test = "${shopadVO.getSa_paytime()==null}"> --%>
<!-- 					<b>尚未驗證</b> -->
<%-- 				</c:if> --%>
<!-- 			</td> -->
			
			
			
			
			<c:if test="${shopadVO.sa_addmode=='待審核'}">
			
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/backstagemanagement/BackstageManagementServletChi" >
			
			<!-- 			審核通過 -->
			<td> 
			
			<input type="hidden" name="sa_addmode" value="${sa_addmode}">
		   <input type="hidden" name="action"	value="sa_confirm">
		   <input type="hidden" name="sa_no"	value="${shopadVO.sa_no}">
       	   <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
       	   <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller-->
		   <input type="submit" value="通過">
		   
		  

			 </FORM>
<!-- 			審核失敗 -->
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/backstagemanagement/BackstageManagementServletChi" >

		
			<input type="hidden" name="sa_addmode" value="${sa_addmode}">
		   <input type="hidden" name="action"	value="sa_reject">
		   <input type="hidden" name="sa_no"	value="${shopadVO.sa_no}">
       	   <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
       	   <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller-->
		   <input type="submit" value="不通過">
		   
		   </td>
		   </FORM>
		   </c:if>
		   
		   <c:if test="${shopadVO.sa_addmode=='上架'}">
		   
		   <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/backstagemanagement/BackstageManagementServletChi" >

			<td> 
			<input type="hidden" name="sa_addmode" value="${sa_addmode}">
		   <input type="hidden" name="action"	value="sa_down">
		   <input type="hidden" name="sa_no"	value="${shopadVO.sa_no}">
       	   <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
       	   <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller-->
		   <input type="submit" value="下架">
		   
		   </td>
		   </FORM>
		   </c:if>
		   
		   <c:if test="${shopadVO.sa_addmode=='下架'}">
		   
		   <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/backstagemanagement/BackstageManagementServletChi" >

			<td> 
			<input type="hidden" name="sa_addmode" value="${sa_addmode}">
		   <input type="hidden" name="action"	value="sa_up">
		   <input type="hidden" name="sa_no"	value="${shopadVO.sa_no}">
       	   <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
       	   <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller-->
		   <input type="submit" value="上架">
		   
		   </td>
		   </FORM>
		   </c:if>
		   
<!-- 			<td> -->
<%-- 				<FORM METHOD="post" ACTION="<%=request.etContextPath()%>/front-end/storeprofile/sto.do" style="margin-bottom: 0px;"> --%>
<!-- 			    	<input type="submit" value="審核"> -->
<%-- 			    	<input type="text" name="sa_no"         value="${shopadVO.getSa_no()}"> --%>
<%-- 					<input type="text" name="requestURL"	value="<%=request.getContextPath()%>"> --%>
<%-- 					<input type="TEXT" name="whichPage"	    value="<%=whichPage%>"> <!--送出當前是第幾頁給Controller--> --%>
<!-- 					<input type="text" name="action"	    value="ad_update"> -->
<!-- 			    </FORM> -->
<!-- 			</td> -->
		</tr>
</c:forEach>		
</table>

<%-- 		<input type="text" name="sa_no"  value="${shopadVO.getSa_no()}"> --%>
<!-- 		<input type="hidden" name="action"	value="bm_ad_control_get_all"> -->
<%-- 		<input type="text" name="requestURL"	value="<%=request.getServletPath()%>"> --%>
<%--   <input type="hidden" name="whichPage"	value="<%=whichPage%>">      <!--送出當前是第幾頁給Controller--> --%>
</FORM>
<%@ include file="pages/page2.file" %>

<!-- <br>本網頁的路徑:<br><b> -->
<%--    <font color=blue>request.getServletPath():</font> <%=request.getServletPath()%><br> --%>
<%--    <font color=blue>request.getRequestURI(): </font> <%=request.getRequestURI()%> </b> --%>
<%-- 	<input type="text" name="requestURL"	value="<%=request.getServletPath()%>"> --%>


<!--========================== 功能放這邊 ====↑↑↑↑↑↑↑↑↑=====================================-->			
				</div><!-- class="block-center panelheight" -->			
			</div><!-- class="col-xs-12 col-sm-8 col-sm-offset-3" -->
		</div><!-- div class="row" -->
	</div><!-- div class="container-fluid" -->
	
	<jsp:include page="/back-end/back_foot.jsp" />

	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
</body>
</html>