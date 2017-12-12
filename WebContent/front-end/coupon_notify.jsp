<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.sun.xml.internal.bind.v2.schemagen.xmlschema.SimpleDerivation"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ page import="java.util.*"%>
<%@ page import="com.coupon.model.*"%>
<%@ page import="com.store_profile.model.*"%>
<jsp:useBean id="cpSvc" scope="request" class="com.coupon.model.CouponService" />
<jsp:useBean id="spSvc" scope="request" class="com.store_profile.model.StoreProfileService" />
<%
SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
pageContext.setAttribute("simpleDateFormat",simpleDateFormat);
List<CouponVO> cpList = cpSvc.getCoupon();
pageContext.setAttribute("cpList",cpList);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">

    <style>
    div{
    	border: 0px solid #AAAAAA;
    }
    
   .coupon{
   		 background-image: url(<%= request.getContextPath() %>/img/coupon_bg.png);
   		 background-repeat: repeat;
   		 height:50px;   		    		 
   }
   .coupon-title{
   		color:orange; 
   		font-size:16pt;
   		margin:0 5px;
   }
	
   .coupon-text{
   		color:#3C9682; 
   		font-size:16pt;
   		display:inline-block;
   		height:50px;
        line-height:50px;
        margin:0 5px;
   }
   .imgrvs{
  	 	-webkit-transform: scaleX(-1);
    	transform: scaleX(-1);
   }

    </style>
</head>
  
<body>


		<!-- �����w�i============================================================= -->

		<div class="container-fluid area50">
			<div class="row">
				<div id="carousel-id" class="carousel slide col-xs-12 col-sm-10 col-sm-offset-1" data-ride="carousel">
				    <!-- �����ϰ� -->
				    <div class="carousel-inner radius5">
				    <c:forEach var="cpMsg" items="${cpList}" varStatus="i">
				        <div class="item coupon text-center ${(i.count==1)?'active':''}" >
				        	<span class="coupon-title">�����w�i <span class=" glyphicon glyphicon-bullhorn"></span></span>
				        	<span class="coupon-text"> ${simpleDateFormat.format(cpMsg.up_date)} �_  </span>
				        	<span class="coupon-text"> ${spSvc.getOneStoName(cpMsg.sto_num).sto_name } ${cpMsg.coupon_cash}�� �@${cpMsg.total}�i</span>
				        	<span class="coupon-title"> <span class=" glyphicon glyphicon-bullhorn imgrvs"></span> �����w�i</span>
						</div>
					</c:forEach>
				    </div>
				</div>
			</div>
		</div>


<!-- 		<script src="https://code.jquery.com/jquery.js"></script> -->
<!-- 		<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script> -->

</body>
</html>
