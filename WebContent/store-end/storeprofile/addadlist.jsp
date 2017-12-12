<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.shop_ad.model.*"%>
<%@ page import="com.ad_block.model.*"%>


<%
  ShopAdVO shopadVO = (ShopAdVO) session.getAttribute("shopadVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>商店新增廣告訊息確認</title>

<style>
	.body{
		font-family:Microsoft JhengHei;
		color:#000000;
		font-size:18px;
	}
	.caption {
		font-size: 36px;
		color:#3C9682;
		font-weight:bold;
	}
	
	input[type="submit" i]{
 		width:100px;
 		height:40px;
		-webkit-appearance:button;
		cursor:pointer;
		left:0px;
		border-color:#3C9682;
	/* 	color:#AD974F; */
		background-color:#ffffff;
/* 		border: 3px solid rgba(0, 0, 0, 0.1); */
		border-style:solid;
		font-family:Microsoft JhengHei;
		font-weight:900;
		font-size:18px;
/* 		color: rgba(0, 0, 0, 0.2); */
		color:#3C9682;
	}

	font{
		font-size:18px;
		color:#000000;
		font-family:Microsoft JhengHei;
	}
	
	#output{
		max-width:330px;
		max-height:330px;
	
	}
	#output{
		border: 1px solid #3C9682;
		box-shadow: 1px 1px 5px #358674, -1px -1px 5px #358674;
		margin-bottom:8px;
	}
/* 	.animatediv{ */
/* 		border:1px; */
/* 	} */
	
/* 	.animatediv .animated-button.thar-four { */
/* 		width:100px; */
/* 		color: #3C9682; */
/* 		cursor: pointer; */
/* 		position: relative; */
/* 		border: 2px solid #3C9682; */
/* 		transition: all 0.4s cubic-bezier(0.42, 0, 0.58, 1); */
/* 		0s; */
/* 	} */
/* 	.animatediv .animated-button.thar-four:hover { */
/* 		color: #fff !important; */
/* 		background-color: transparent; */
/* 		text-shadow: nfour; */
/* 	} */
/* 	.animatediv .animated-button.thar-four:hover:before { */
/* 		right: 0%; */
/* 		left: auto; */
/* 		width: 100%; */
/* 	} */
/* 	.animatediv .animated-button.thar-four:before { */

/* 		position: absolute; */
/* 		top: 0px; */
/* 		left: 0px; */
/* 		height: 100%; */
/* 		width: 0px; */
/* 		z-index: -1; */
/* 		content: ''; */
/* 		color: #000 !important; */
/* 		background: #3C9682; */
/* 		transition: all 0.4s cubic-bezier(0.42, 0, 0.58, 1); */
/* 		0s; */
/* 	} */
	
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
	
	
 	<div class="caption">廣告訊息確認</div>
<%-- <div class="animatediv"><a href="<%=request.getContextPath()%>/store-end/storeprofile/addad.jsp" class="btn btn-sm animated-button thar-four"><b>BACK</b></a></div> --%>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<ul>
			<img src="<%= request.getContextPath() %>/img/xx.png" height="30" width="30">
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color:red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<c:if test="${not empty shopadVO}">
		<div class="col-xs-12 col-sm-12">
			<div>
				<font class="storeowner" style="font-size:28px; color:#000000; font-weight:bold;">${storeprofileVO.getSto_name()}商店：${storeprofileVO.getGuy()}老闆您好</font>
			</div>
<!-- 			<div class="text"> -->
<!-- 		        <font style="font-size:18px;">廣告編號</font> -->
<%-- 		       	<font style="font-size:18px;">${shopadVO.getSa_no()}</font> --%>
<!-- 			</div> -->
			
<!-- 			<div class="text"> -->
<!-- 		        <font style="font-size:18px;">店家編號</font> -->
<%-- 		       	<font style="font-size:18px;">${shopadVO.getSto_num()}</font> --%>
<!-- 			</div> -->

			<div class="text">
		        <font class="fontlead" style="padding-right:46px;color:#3C9682;font-size: 14px;font-weight: 700;">廣告標題</font>
		       	<font>${shopadVO.getSa_title()}</font>
			</div>

			<div class="text">
		        <font class="fontlead" style="padding-right:18px;color:#3C9682;font-size: 14px;font-weight: 700;">廣告內容文字</font>
		       	<font>${shopadVO.getSa_text()}</font>
			</div>
			
			<c:if test = "${shopadVO!=null}">
				<div class="text">
		        	<font class="fontlead" style="padding-right:47px;color:#3C9682;font-size: 14px;font-weight: 700;">廣告圖片</font>
		       		<font><img id="output" src="/BA104G2/GetPic?sa_no=${shopadVO.getSa_no()}&getType=shopad" width="80%"></font>
		       	</div>
			</c:if>
			
<!-- 			<div class="text"> -->
<!-- 		        <font>瀏覽次數</font> -->
<%-- 		       	<font>${shopadVO.getSa_views()}</td></font> --%>
<!-- 		        基本功能有後在問問XD新增時一定是0，這邊是後做的  -->
<!-- 			</div>			 -->

<!-- 			<div class="text"> -->
<!-- 		        <font>申請時間</font> -->
<%-- 		       	<font>${shopadVO.getSa_apptime()}</font> --%>
<!-- 		       	這邊時間系統自己會給這樣 -->
<!-- 			</div> -->
			
			<div class="text">
		        <font class="fontlead" style="padding-right:46px;color:#3C9682;font-size: 14px;font-weight: 700;">上架時間</font>
		       	<font>${shopadVO.getSa_addtime()}</font>
		       	<!-- 2017-11-13 之類-->
			</div>

			<div class="text">
		        <font class="fontlead" style="padding-right:18px;color:#3C9682;font-size: 14px;font-weight: 700;">預計下架時間</font>
		       	<font>${shopadVO.getSa_preofftime()}</font>
		       	<!-- 2017-12-13 之類-->
			</div>

			<div class="text">
		        <font class="fontlead" style="padding-right:46px;color:#3C9682;font-size: 14px;font-weight: 700;">廣告區塊</font>

		       	<font>
		       		<jsp:useBean id="adblockSvc" scope="page" class="com.ad_block.model.AdBlockService" />
		        	<c:forEach var="adblockVO" items="${adblockSvc.all}">
                    	<c:if test="${shopadVO.ab_no==adblockVO.ab_no}">
	                   		${adblockVO.ab_name} - $${adblockVO.ab_price}
                    	</c:if>
                	</c:forEach>
<%-- 		       		${shopadVO.getAb_no()} ${adblockVO.ab_name} --%>
		       	</font>
			</div>

<!-- 			<div class="text"> -->
<!-- 		        <font>廣告上架狀態</font> -->
<%-- 		       	<font>${shopadVO.getSa_addmode()}</font> --%>
<!-- 		       	上架、下架、審核中的顯示 -->
<!-- 			</div> -->
			
			<div>
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/store-end/storeprofile/sto.do" style="margin-bottom: 0px;">
			    	<input type="submit" value="修改">
					<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
					<input type="hidden" name="sa_no"      value="${shopadVO.getSa_no()}">
					<input type="hidden" name="action"	value="ad_update">
			    </FORM>
			</div>

			<div>
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/store-end/storeprofile/sto.do" style="margin-bottom: 0px;">
			    	<input type="submit" value="確定送出">
					<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
					<input type="hidden" name="sa_no"      value="${shopadVO.getSa_no()}">
			    	<input type="hidden" name="action"	value="adlist_to_bm">
			    </FORM>
		   	</div>
		</div>
	</c:if>


<!--========================== 功能放這邊 ↑↑↑↑↑↑↑=======================================-->			
				</div><!-- class="block-center panelheight" -->			
			</div><!-- class="col-xs-12 col-sm-8 col-sm-offset-3" -->
		</div><!-- div class="row" -->
	</div><!-- div class="container-fluid" -->
	
	<jsp:include page="/store-end/store_foot.jsp" />
	
	
</body>

<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	
	
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/build/jquery.datetimepicker.full.min.js"></script>

<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>

<script>

//          $.datetimepicker.setLocale('zh');
//          $('#f_date1').datetimepicker({
//  	       theme: '',              //theme: 'dark',
//  	       timepicker:false,       //timepicker:true,
//  	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
<%--  		   //value: '<%=sa_addtime%>', // value:   new Date(),    --%>
<%--  		   //value: '<%=sa_preofftime%>', // value:   new Date(),  --%>
//             disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
//             startDate:	            '2017/07/10',  // 起始日
//             minDate:               '-1970-01-01', // 去除今日(不含)之前
//             maxDate:               '+1970-01-01'  // 去除今日(不含)之後
//   　       });
        
        
   
        // ----------------------------------------------------------以下用來排定無法選擇的日期-----------------------------------------------------------

        //      1.以下為某一天之前的日期無法選擇
        //      var somedate1 = new Date('2017-06-15');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() <  somedate1.getYear() || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});

        
        //      2.以下為某一天之後的日期無法選擇
        //      var somedate2 = new Date('2017-06-15');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() >  somedate2.getYear() || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});


        //      3.以下為兩個日期之外的日期無法選擇 (也可按需要換成其他日期)
        //      var somedate1 = new Date('2017-06-15');
        //      var somedate2 = new Date('2017-06-25');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() <  somedate1.getYear() || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
        //		             ||
        //		            date.getYear() >  somedate2.getYear() || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});
// 	function onFileSelected(event,get){
     
//      var selectFile = event.target.files[0];
//      var reader = new FileReader();
     
//         var imgtag =  document.getElementById(get+"1");
//         imgtag.title = selectFile.name;

//         reader.onload = function(event){
//             imgtag.src = event.target.result;
//         };
//         reader.readAsDataURL(selectFile);
        
//     }
	
	//隱藏TR

	//document.getElementById( "cekmobileSide ").style.visibility= "hidden"; 
	//var result_style = document.getElementById('rempointSide').style;
	//var result_style = document.getElementById('statusSide').style;

        
</script>
</html>