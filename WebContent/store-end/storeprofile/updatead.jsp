<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.shop_ad.model.*"%>


<%
  ShopAdVO shopadVO = (ShopAdVO) request.getAttribute("shopadVO");
%>


<html>
<head>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/store_base.css">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>商店廣告新增 - addMemberProfile.jsp</title>

<style>
	.body{
		font-family:Microsoft JhengHei;
	}
	.caption {
		font-size: 36px;
		color:#3C9682;
		font-weight:bold;
	}
	
	table{
/* 		border:1px solid #3C9682;  */
		table-layout:automatic;
		border-collapse: collapse;
		width: 700px;
		word-break: keep-all;
/*  		word-wrap:break-word;  */
/*   		word-break:break-all;  */
/*   		word-break: normal;  */
	}
	td{
		text-align:left; !important;
		
	}
	
	.input,
	.input label,
	.input input,
	.input .spin,
	.button,
	.button button {
	   width: 100%;
	}
	
	.input{
		padding:3px;
	}
	
	element.style{
		background-image:none;
	}
	
	input[type="text" i] {
		height:40px;
/*  	border: none; */
		background: transparent;
		font-size: 18px;
		color: rgba(0, 0, 0, 0.8);
		font-weight: 300;
		margin: 0;
/* 		line-height: 18px; */
		border: 1px solid #3C9682;
		border-radius: 6px;
		padding:5px;
		font-size:18px;
		font-family:Microsoft JhengHei;
	}
	
	element.style{
		-webkit-appearance: none;
	}
	
	input[type="submit" i]{
 		width:100%;
		-webkit-appearance:button;
		cursor:pointer;
		line-height:64px;
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
	
	select[multiple], select[size], textarea.form-control:focus{
		outline:none; !important;
		border-color:#3C9682;
		box-shadow: none;
	}
	
	textarea{
		resize:none;
	}
	
	select[multiple], input[type="submit" i], select[size], textarea.form-control:active{
		outline:none; !important;
		box-shadow: none;
		border-color:#3C9682;
	}

	input[type="text" i], select[multiple], select[size], textarea.form-control:hover{
		outline:none; !important;
		box-shadow: none;
		border-color:#3C9682;
	}
	
/* 	input[type="text" i], input[type="submit" i], select[multiple], select[size], textarea.form-control:selected{ */
/* 		outline:none; !important; */
/* 		box-shadow: none; */
/* 		border-color:#3C9682; */
/* 	} */
	
	#selop {
		border-color:#3C9682;
	}

	#output{
		cursor: pointer;
	    line-height: 64px;
	    background-color: #ffffff;
	    /* border: 3px solid rgba(0, 0, 0, 0.1); */
	    font-family: Microsoft JhengHei;
	    /* color: rgba(0, 0, 0, 0.2); */
	}
	#output1{
		max-width:550px;
		max-height:330px;
	
	}
	#output{
		max-width:550px;
		max-height:330px;
	
	}
	#output1{
		border: 1px solid #3C9682;
		box-shadow: 1px 1px 5px #358674, -1px -1px 5px #358674;
		margin-bottom:8px;
	}
	
	textarea.form-control{
		border: 1px solid #3C9682; !important;
		outline:none; !important;
		font-size:18px;
	}
	
	select[size]{
		height:40px; !important;
		font-size:18px;
		width:100%;
		text-align:left; !important;
		border: 1px solid #3C9682;
	}
	
	select option {
  		background: #fff; 
  		color: #3C9682; 
  		box-shadow: 0;
/*   		border-color:#red; */
/*   		outline-color: red; */
	}
            /*
Option control color
*/

	input::-ms-clear{
	  display: none; 
	} /* 殺了IE10的xx   https://www.w3cways.com/1940.html */
	input::-ms-reveal{
	  display: none; 
	} /* 殺了IE10的xx   https://www.w3cways.com/1940.html */
	
	.magic2 {
		padding:2px;
	    background-color: #FFFFFF;
	    color: #ffd280;
	    border:1px solid #ffffff;
	    border-radius: 300px;
	}
	
	.magic2:hover {
		padding:2px;
	    background-color: #ffd280;
	    color: #ffffff;
	    border:1px solid #ffd280;
	    border-radius: 300px;
	}
	
	.magic2:focus {
	    outline:none;
	}
	
	.magicbutton{
		display:inline-table;
		margin-left:20px;
		/* border:1px solid #fff; */
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

<div class="caption">商店廣告新增</div>
<%-- 			<div class="animatediv"><a href="<%=request.getContextPath()%>/store-end/storeprofile/adlist.jsp" class="btn btn-sm animated-button thar-four"><b>BACK</b></a></div> --%>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<ul>
		<img src="<%= request.getContextPath() %>/img/xx.png" height="30" width="30">
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/store-end/storeprofile/sto.do" name="form1" enctype="multipart/form-data">
	<div class="container">
		<div class="row">
			<div class="col-xs-12 col-sm-12">
				<div class="stonameguy" style="font-size:28px;color:#000000;">
					<label for="sto_name"><font>${storeprofileVO.getSto_name()}商店：${storeprofileVO.getGuy()}老闆您好</a></font>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12 col-sm-6">
				
			    <input type="hidden" name="sa_no" size="45"
 					value="${shopadVO.getSa_no()}" /> 
						<!-- 記得hidden -->
		
			    <input type="hidden" name="sto_num" size="45"
						 value="${storeprofileVO.getSto_num()}" />
						<!-- 記得hidden -->
						
				<div class="input">
			       <label for="sa_title">廣告標題</label>
			       <input type="TEXT" name="sa_title" size="45"
						 value="<%= (shopadVO==null)? "" : shopadVO.getSa_title()%>" />
			       <span class="spin"></span>
			    </div>
				
				<div class="input">
			       <label for="sa_text">廣告內容文字</label>
			       <textarea class="form-control" rows="5" id="comment" name="sa_text" size="45"></textarea>
			       <span class="spin"></span>
			    </div>
		
				<div class="input">
			       <label for="sa_addtime">上架時間</label>
			       <input type="TEXT" name="sa_addtime" size="45" id="f_date1" value="${shopadVO.getSa_addtime() }">
			       <span class="spin"></span>
			    </div>
		
				<div class="input">
			       <label for="sa_preofftime">預計下架時間</label>
			       <input type="TEXT" name="sa_preofftime" size="45" id="f_date1" value="${shopadVO.getSa_preofftime() }">
			       <span class="spin"></span>
			    </div>
		
				<div class="input">
			       <label for="adblocksvc">廣告區塊編號</label>
			           <jsp:useBean id="adblockSvc" scope="page" class="com.ad_block.model.AdBlockService" />
			           <select size="1" name="ab_no" size="45" id="selop" class="select op">
			          	 		<option value="請選擇" name="option"></option>
			         	 		<c:forEach var="adblockVO" items="${adblockSvc.all}" > 
			          	 			<option value="${adblockVO.ab_no}">${adblockVO.ab_name} $${adblockVO.ab_price}</option>
			         	 		</c:forEach>   
					   </select>
			       <span class="spin"></span>
			    </div>	
			</div>

			<div class="col-xs-12 col-sm-6">    
			    <div class="input">
			       <label for="sa_img">廣告內容圖片</label>
<%-- 				       <c:if test = "${shopadVO.getSa_no()!=null}"> --%>
							<img id="output1" name="upfile"   src="/BA104G2/GetPic?sa_no=${shopadVO.getSa_no()}&getType=shopad" >
<%-- 						</c:if> --%>
						
<%-- 						<c:if test = "${shopadVO.getSa_no()==null}"> --%>
<!-- 						<img id="output" height="330" width="550" name="upfile"> -->
<%-- 						</c:if>	 --%>
						<input type="file" name="upfile" id="output" size="45" onchange="previewFile()"> 
						<!-- sa_img -->
			       <span class="spin"></span>
			       

			    </div>
		
				<div class="input">
				   <input type="hidden" name="action" value="upup">
				   <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
				   <input type="hidden" name="sa_no" size="45"
 					value="${shopadVO.getSa_no()}" /> 
				   <input type="submit" value="送出新增">
			    </div>
	    	</div>
    		<div class="magicbutton">
				<button class="magic2" onClick="inPutInfo1()" type="button">rulein123</button>
			</div>
		</div>
	</div>
</FORM>



<!--========================== 功能放這邊 ↑↑↑↑↑↑↑=======================================-->			
				</div><!-- class="block-center panelheight" -->			
			</div><!-- class="col-xs-12 col-sm-8 col-sm-offset-3" -->
		</div><!-- div class="row" -->
	</div><!-- div class="container-fluid" -->
	
	<jsp:include page="/store-end/store_foot.jsp" />
	
	
</body>
 <script src="https://code.jquery.com/jquery.js"></script>
	
 <%  
   java.sql.Date sa_apptime = null;

   try {
	   sa_apptime = shopadVO.getSa_apptime();
    } catch (Exception e) {
    	sa_apptime = new java.sql.Date(System.currentTimeMillis());
    }
 %> 

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

         $.datetimepicker.setLocale('zh');
         $('#f_date1').datetimepicker({
 	       theme: '',              //theme: 'dark',
 	       timepicker:false,       //timepicker:true,
 	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
 		   value: '<%=sa_apptime%>', // value:   new Date(),   
            disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
            startDate:	            '2017/07/10',  // 起始日
            minDate:               '-1970-01-01', // 去除今日(不含)之前
            maxDate:               '+1970-01-01'  // 去除今日(不含)之後
  　       });
        
        
   
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
		function previewFile() {
		  var preview = document.querySelector('img[name=upfile]');
		  var file    = document.querySelector('input[type=file]').files[0];
		  var reader  = new FileReader();
		
		  reader.onloadend = function () {
		    preview.src = reader.result;
		  }
		
		  if (file) {
		    reader.readAsDataURL(file);
		  } else {
		    preview.src = "";
		  }
		}
		
		
	    function inPutInfo1(){
			document.getElementsByName("sa_title")[0].value= '爆能';
			document.getElementsByName("sa_text")[0].value= '保證能量充滿~戰鬥力十足';
			document.getElementsByName("sa_addtime")[0].value= '2018-01-15';
	 		document.getElementsByName("sa_preofftime")[0].value= '2018-02-15';
		}
		

	//隱藏TR

	//document.getElementById( "cekmobileSide ").style.visibility= "hidden"; 
	//var result_style = document.getElementById('rempointSide').style;
	//var result_style = document.getElementById('statusSide').style;

        
</script>
</html>