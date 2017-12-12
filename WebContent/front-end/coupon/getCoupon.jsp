<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:useBean id="couponSvc" scope="page" class="com.coupon.model.CouponService" />
<jsp:useBean id="StoProSvc" scope="page" class="com.store_profile.model.StoreProfileService" />
<!DOCTYPE html>
<html lang="">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>Title Page</title>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.2.1.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/3dbtn.css" />
<script src="<%=request.getContextPath()%>/js/dist/sweetalert2.all.min.js"></script>
<script src="<%=request.getContextPath()%>/js/dist/sweetalert2.min.js"></script>


<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
		
<style type="text/css">
.bs-calltoaction {
	position: relative;
	width: auto;
	padding: 15px 25px;
	border: 1px solid black;
	margin-top: 10px;
	margin-bottom: 10px;
	border-radius: 5px;
}

.bs-calltoaction>.row {
	display: table;
	width: calc(100% + 30px);
}

.bs-calltoaction>.row>[class^="col-"], .bs-calltoaction>.row>[class*=" col-"]
	{
	float: none;
	display: table-cell;
	vertical-align: middle;
}

.cta-contents {
	padding-top: 10px;
	padding-bottom: 10px;
}

.cta-title {
	margin: 0 auto 15px;
	padding: 0;
}

.cta-desc {
	padding: 0;
}

.cta-desc p:last-child {
	margin-bottom: 0;
}

.cta-button {
	padding-top: 10px;
	padding-bottom: 10px;
}

img{
	
	width:100%;
		
}

@media ( max-width : 991px) {
	.bs-calltoaction>.row {
		display: block;
		width: auto;
	}
	.bs-calltoaction>.row>[class^="col-"], .bs-calltoaction>.row>[class*=" col-"]
		{
		float: none;
		display: block;
		vertical-align: middle;
		position: relative;
	}
	.cta-contents {
		text-align: center;
	}
}

.bs-calltoaction.bs-calltoaction-default {
	color: #333;
	background-color: #fff;
	border-color: #ccc;
}

.bs-calltoaction.bs-calltoaction-primary {
	color: #fff;
	background-color: #337ab7;
	border-color: #2e6da4;
}


body {
	background: #aaEFEF;
	font-size: 100%;
	text-align: center;
	margin: auto;
	font-family: Verdana, sans-serif;
	color: #333;
}

.count {
	margin-top: 40px;
	color: #FF0066;
	padding: 20px;
	display: inline-block;
	
}




.keepImg{
			margin-top: -40%;/*圖片往上移動*/
			width: 85%;
        }
        .well{
            background: #3C9682;/*圖片框架的顏色*/
            box-shadow: 0 0 0 0;/*圖片升起速度*/
        }
        .pricing-table{
            transition:all 0.3s ease-out;/*圖片升起來的速度*/
        }
        .pricing-table:hover{
             box-shadow:0 70px 150px #444;/*升起圖片高度和陰影*/
             margin-top: -1.7%;
         }
         
         
 
 
 
 
.outsi {  
    background: #a8c4c0;
    background: -moz-linear-gradient(top,  #a8c4c0 0%, #7aa5a2 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#a8c4c0), color-stop(100%,#7aa5a2));
    background: -webkit-linear-gradient(top,  #a8c4c0 0%,#7aa5a2 100%);
    background: -o-linear-gradient(top,  #a8c4c0 0%,#7aa5a2 100%);
    background: -ms-linear-gradient(top,  #a8c4c0 0%,#7aa5a2 100%);
    background: linear-gradient(top,  #a8c4c0 0%,#7aa5a2 100%);
    filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#a8c4c0', endColorstr='#7aa5a2',GradientType=0 );
    
    font-size: 20px;
    font-weight: bold;
    text-shadow: 1px 1px 3px rgba(0,0,0,0.3);
    padding: 0 20px;  
    line-height: 50px;
    height: 50px;
    margin-top: 5px; 
    margin-left: -35px;   
    position: relative;  
    width: 300px;  
    
    box-shadow: 0px 3px 4px 0px rgba(0,0,0,0.3);
    -moz-box-shadow: 0px 3px 4px 0px rgba(0,0,0,0.3);
    -webkit-box-shadow: 0px 3px 4px 0px rgba(0,0,0,0.3);
  
    color: #3b3a3b;  
    text-shadow: 0 1px 0 rgba(255, 255, 255, 0.4);  
}  
  
.outsi:after {  
    position: absolute;
    content: '';
    width: 0; height: 0;  
    line-height: 0;  
    border-left: 15px solid transparent;  
    border-top: 10px solid #729c9a;  
    bottom: -10px;  
    left: 0;  
    position: absolute;  
    z-index: -1;
}  

.outsi:before {
    content: '';
    position: absolute;
    top: 5px;
    height: 39px;
    border-top: 1px solid rgba(255,255,255,0.5);
    border-bottom: 1px solid rgba(255,255,255,0.5);
    width: 100%;
    left: 0;
}     

.noCoupon {
    background: url(<%=request.getContextPath()%>/img/noCoupon.png) no-repeat center top;
}     

i {
    z-index: 2;
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: none;
 }
 
 
 

	
.wrapecoupon:hover{
		border-color: red;
		border-width: unset;
	}
     
       
</style>
</head>
<jsp:include page="/front-end/member_top.jsp" flush="true" />
<jsp:include page="/front-end/coupon_notify.jsp" />
<script src="<%=request.getContextPath()%>/js/ipush-countdown.js" type="text/javascript"></script>
<body  style="font-family:Microsoft JhengHei;">
 
	<%-- 錯誤表列 --%>
	  <c:if test="${not empty errorMsgs}">
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<div class="container">
		<c:forEach var="couponVO" items="${couponSvc.coupon}">
			<c:set var="time"><fmt:formatDate value="${couponVO.up_date}" pattern="yyyy/MM/dd HH:mm:s"/></c:set>	
			<c:set var="now"><fmt:formatDate value="<%= new Date() %>" pattern="yyyy/MM/dd HH:mm:s"/></c:set>	
			<div class="col-sm-12">
					
				<div class="bs-calltoaction bs-calltoaction-default  pricing-table well wrapecoupon">
					<h3 class="outsi">${StoProSvc.getOneStoInfo(couponVO.sto_num).sto_name}</h3>
					<div class="row">
<!-- 					搶購一空圖片 -->
					<c:if test="${couponVO.left==0}" ><i class="noCoupon"></i></c:if>		
						<div class="col-md-4 cta-contents">
							
							<img  class="img-thumbnail"  height="135" width="240" src="<%=request.getContextPath()%>/GetPicEric?coupon_num=${couponVO.coupon_num}">
															
						</div>
						
						
						<div class="col-md-4 cta-contents">
						<%-- 店家名稱 --%>
						<div class="cta-desc">
						<%-- 時間還沒到 顯示的文字 --%>
						<c:if test="${time > now }"><div class="text-center"  style="font-size:24px;">活動預告 :</div><br><br>	${couponVO.notice_desc}</c:if>
						<%-- 時間到 顯示的文字  --%>
						<c:if test="${time < now }"><div class="text-center"  style="font-size:24px;">注意事項 :</div><br><br>${couponVO.coupon_desc}</c:if>	
								
						</div>
						</div>
						<div class="col-md-4 cta-button">
	
        	
			<%-- 時間還沒到 --%>
						<c:if test="${ time > now }" >
					<div class="text-center"  style="font-size:36px;">開搶剩餘時間 :</div>			
						<%-- 時間顯示 --%>
						<h3><span time="${time}" now="${now}" class="count" ></span></h3>
						</c:if>		
							 
				<%-- 時間到時--%>		
					<c:if test="${couponVO.left!=0 && time < now}" >	
					 <div>倒數  : ${couponVO.left}  張</div>	
			  			<div>	   			 		     					
							 <input type="button" style=" font-weight: bold;"  class="getCoupon btn btn-lg btn-danger  btn3d addone"  value="立即搶購" >
						     <input type="hidden" name="coupon_num"  value="${couponVO.coupon_num}">
						      <input type="hidden" name="action"	    value="get_coupon">
						     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->            		    
						 </div>	
			   		<p style="margin-top: 40px;">活動期間 : <br>
			   		<fmt:formatDate value="${couponVO.up_date}" pattern="yyyy/MM/dd"/> - 
			   		<fmt:formatDate value="${couponVO.down_date}" pattern="yyyy/MM/dd"/></p>
					</c:if>
						<%-- 剩餘張數為0時 --%>	
					<c:if test="${couponVO.left==0}" ><p>搶購一空</p> </c:if>						
					
				</div>
					</div>
				</div>
			</div>
		</c:forEach>


	</div>


<jsp:include page="/front-end/member_foot.jsp" flush="true" />
</body>


<script type="text/javascript">




//取得折價券*******************************************
$(function(){
	$('.getCoupon').on('click', function(){	
		console.log($(this).parent().prev().text());
		var coupon_num = $(this).next().val();
		var action = $(this).next().next().val();	
		var yyy = $(this).parent().prev();
		$.ajax({
			 type: "POST",
			 url: "<%=request.getContextPath()%>/CouponListServlet",
			 data: {"coupon_num":coupon_num, "action":action},
			 dataType: "json",
			 async: false, // true預設是非同步，也就是"不會等待"; false是同步，"會等待"
			 success: function(data){			
				  if(data.message != null){
					  yyy.html(data.left);	
					  swal({
						  position: 'center',
						  type: 'success',
						  title: data.message,
						  showConfirmButton: true,
						  timer: 2000
						})
					}
				  if(data.errormessage!= null){
					  swal({
						  position: 'center',
						  type: 'error',
						  title: data.errormessage,
						  showConfirmButton: true,
						  timer: 1500
						})
						setTimeout('startRequest()',2000);
						
					}	
					if(data.nologin != null){
						  swal({
							  position: 'center',
							  type: 'error',
							  title: data.nologin,
							  showConfirmButton: true,
							  timer: 1500
							})
							setTimeout('goindex()',2700);						
						}	
				  
				},
			error: function(){alert("系統忙碌中 ， 請稍後再試 。")}
        })

	});
});

$(document).ready(function() {
		$(".count").ipushs_countdown({
			daysText : ' 天 ',//設定day顯示的文字
			hoursText : ' : ',//設定hours顯示的文字
			minutesText : ' : ',//設定minutes顯示的文字
			secondsText : '.',//設定seconds顯示的文字
			secondssText : '  ',//設定secondss顯示的文字
			displayZeroDays : true,//設定當小於一天時是否顯示天數
			callback	: startRequest,//設定當時間到時所執行函式
			addClass : 'myclass',//在box增加自定義class
			textAfterCount : '開始搶購 !!'//倒數結束時顯示文字
		});
	});
	

	

function goindex()
{
	
	window.location = "<%=request.getContextPath()%>/front-end/index.jsp";
}


function startRequest()
{
	
	setTimeout('location.reload()',1500);
	
}

	

	

</script>

</html>