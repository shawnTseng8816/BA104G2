<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@ page import="java.util.*"%>
<%@ page import="com.store_profile.model.*"%>  

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>揪茶趣:看地圖訂飲料好方便</title>
<style type="text/css">
	#map {
	  height: 600px;
	  width: 100%;
	}

</style>

</head>

<body>

<%
	//店家列表
	ServletContext context = getServletContext();
	List<StoreProfileVO> newList = (List<StoreProfileVO>) request.getAttribute("newList");
	pageContext.setAttribute("newList",newList);
%>

<jsp:include page="/front-end/member_top.jsp" />
<jsp:include page="/front-end/coupon_notify.jsp" />


	<div class="container-fluid area50">
	    <div class="row">
	    
	    <div class="col-xs-12 col-sm-6 col-sm-offset-1 ">
		    
		    <!-- 地圖 -->
			<div id="map"></div>	    
	    
	    </div>
	    
	    
	    <div class="col-xs-12 col-sm-4 col-sm-offset-0 ">
	    
	    	<!-- 地圖上可以看到的店家 -->
			<div id="tableOutput" class="table-responsive ">			
			   	<table class="table">
			   		<thead>
			   			<tr>
			   				<th>店名</th>
			    			<th>地址</th>
			    			<th>距離</th>
			   			</tr>
			   		</thead>
			   		<tbody id="place">
			      		</tbody>
			   	</table>
			</div> 
	    
	    </div>
	    
	    
	    
	</div><!-- row end -->
</div><!-- container-fluid -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">


<script src="https://code.jquery.com/jquery.js"></script>
<script>
	
var map;
var initialLocation;
var lat;
var lng;
var markers = [];
var searchResult;

		function initMap() {	
			var g = window.navigator.geolocation;
	        g.getCurrentPosition(succ, fail);
		}
		
		function fail(event) {	//抓點失敗
	        console.log("Get location fail");
	        var position = {
	        lat : 24.9694,
	        lng : 121.1925
	        };
	        map = new google.maps.Map(document.getElementById("map"), {
	        minZoom : 12,
	        zoom : 12,
	        center : position,
	        styles: [{"featureType":"all","elementType":"all","stylers":[{"hue":"#ffaa00"},{"saturation":"-33"},{"lightness":"10"}]},{"featureType":"administrative.locality","elementType":"labels.text.fill","stylers":[{"color":"#9c5e18"}]},{"featureType":"landscape.natural.terrain","elementType":"geometry","stylers":[{"visibility":"simplified"}]},{"featureType":"road.highway","elementType":"geometry","stylers":[{"visibility":"simplified"}]},{"featureType":"road.highway","elementType":"labels.text","stylers":[{"visibility":"on"}]},{"featureType":"road.arterial","elementType":"geometry","stylers":[{"visibility":"simplified"}]},{"featureType":"transit.line","elementType":"all","stylers":[{"visibility":"off"}]},{"featureType":"water","elementType":"geometry.fill","stylers":[{"saturation":"-23"},{"gamma":"2.01"},{"color":"#f2f6f6"}]},{"featureType":"water","elementType":"geometry.stroke","stylers":[{"saturation":"-14"}]}]

	        });
	        console.log(map);
	        var marker = new google.maps.Marker({
	        position : position,
// 	        title : '現在位置',
// 	        label : '現在位置',
	        map : map
	        });
	        
	        map.addListener('center_changed',centerChanged);
	        centerChanged();
	    }
		
		function succ(event) {	//抓點成功
	        console.log("Get location succ");	        
	        initialLocation = {
	        lat : event.coords.latitude,
	        lng : event.coords.longitude
	        };
	        
	        save_position(event);
	        map = new google.maps.Map(document.getElementById("map"), {
	        minZoom : 12,
	        zoom : 12,
	        center : initialLocation,
	        styles: [{"featureType":"all","elementType":"all","stylers":[{"hue":"#ffaa00"},{"saturation":"-33"},{"lightness":"10"}]},{"featureType":"administrative.locality","elementType":"labels.text.fill","stylers":[{"color":"#9c5e18"}]},{"featureType":"landscape.natural.terrain","elementType":"geometry","stylers":[{"visibility":"simplified"}]},{"featureType":"road.highway","elementType":"geometry","stylers":[{"visibility":"simplified"}]},{"featureType":"road.highway","elementType":"labels.text","stylers":[{"visibility":"on"}]},{"featureType":"road.arterial","elementType":"geometry","stylers":[{"visibility":"simplified"}]},{"featureType":"transit.line","elementType":"all","stylers":[{"visibility":"off"}]},{"featureType":"water","elementType":"geometry.fill","stylers":[{"saturation":"-23"},{"gamma":"2.01"},{"color":"#f2f6f6"}]},{"featureType":"water","elementType":"geometry.stroke","stylers":[{"saturation":"-14"}]}]
	        });
	        var marker = new google.maps.Marker({
	        position : initialLocation,
// 	        title : '現在位置',
// 	        label : '現在位置',
	        map : map
	        });
	        
			$.ajax({
			    url: '/BA104G2/index/IndexServlet.do',
			    type: 'GET',
			    data: {
			    	lat : event.coords.latitude,
			        lng : event.coords.longitude
			    },
			    error: function(xhr) {
			      alert('Ajax request 發生錯誤');
			    },
			    success: callback,
			});	        
	        map.addListener('center_changed',centerChanged);
	        centerChanged();
	    }
		
		function centerChanged() {
        	deleteMarkers();
        	var str = 'lat='+lat+'&lng='+lng;
        	$.get('/BA104G2/index/IndexServlet.do',str,callback);
	    }
		
		function callback(data){
			
				searchResult = data;
		        $("#place").empty();
		        var index = 1;
		        for(var i = 0; i < data.length; i++){
		            var sto_num = data[i].sto_num;
		            var sto_name = data[i].sto_name;
		            var address = data[i].address;
		            var distance = data[i].distance;
		            var latLng = new google.maps.LatLng(data[i].lat, data[i].lng);
		            if(map.getBounds().contains(latLng)){
		          		addMarker(latLng, data[i]);
		          		$("#place").append("<tr><td><a href='<%= request.getContextPath()%>/store_detail/store_detail.do?sto_num="+sto_num+"'>"+sto_name+"</a></td><td>"+address+"</td><td>"+distance.toFixed(1)+" km</td></tr>");
		          		index++;
		          	}
		        }
		}

		function addMarker(latLng,data){
			var marker = new google.maps.Marker({
	            position: latLng,
	            title: data.sto_name,
	            map: map,
	            icon:'/BA104G2/img/LOGO_50x50.png'
	        });
			 
			markers.push(marker);
			var infowindow = new google.maps.InfoWindow({
				content: "<h3><a href='<%= request.getContextPath()%>/store_detail/store_detail.do?sto_num="+data.sto_num+"'>"+data.sto_name+"</a></h3><h5>"+data.address+"</h5><h5>距離: "+data.distance+" 公里</h5>"
			});
	          marker.addListener('click', function() {
	              infowindow.open(map, marker);
	        });
		}
	
		function deleteMarkers(){
			setMapOnAll(null);
			markers=[];
		}	
		
		function setMapOnAll(map){
			for(var i = 0; i< markers.length ; i++){
				markers[i].setMap(map);
			}
		}
		
		function save_position(event){
			lat=event.coords.latitude;
	        lng=event.coords.longitude;			
		}
		
		
	
</script>

<!--footer-->
<jsp:include page="/front-end/member_foot.jsp" />

</body>
</html>