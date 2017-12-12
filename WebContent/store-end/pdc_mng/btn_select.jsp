<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>

<!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css"> -->
<%-- <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/store_base.css"> --%>
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.0.5/sweetalert2.min.css">
<style>

	.bar{ 
 		margin-right:20px; 
	}

</style>
</head>

<body>
 <h2 class="text-center"><strong>店家商品管理</strong></h2>
 
<ul class="nav nav-pills page-header">
				<li class="bar">
					<div class=" input-group" >
						<div class=" btn-group" >
						<form class="btn-group" METHOD="post" ACTION="<%= request.getContextPath() %>/store-end/pdc_mng/store_select_page.jsp">
							<span class="input-group-btn">
							<input type="submit" value="商品列表" class="btn btn-green ">
							</span>
							<input type="hidden" name="action" value="getAllPdc">
						</form>														
						<form class="btn-group" METHOD="post" ACTION="<%= request.getContextPath() %>/pdc_mng/StoExtMng.do">
							<span class="input-group-btn">
							<input type="submit" value="加料列表" class="btn btn-green">
							</span>
							<input type="hidden" name="action" value="getAllExt">
						</form>
						<form class="btn-group" METHOD="post" ACTION="<%= request.getContextPath() %>/pdc_mng/StoIceMng.do">
							<span class="input-group-btn">
							<input type="submit" value="冰塊列表" class="btn btn-green">
							</span>
							<input type="hidden" name="action" value="getAllIce">
						</form>
						<form class="btn-group" METHOD="post" ACTION="<%= request.getContextPath() %>/pdc_mng/StoSwtMng.do">
							<span class="">
							<input type="submit" value="甜度列表" class="btn btn-green">
							</span>
							<input type="hidden" name="action" value="getAllSwt">
						</form>	
						</div>
					</div>
				</li>
				
					
				<li class="bar">
					<div class="dropdown">
					  <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" >
					   	 新增
					    <span class="caret"></span>
					  </button>
					  <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
					    <li><a href="<%= request.getContextPath() %>/store-end/pdc_mng/addProduct.jsp">新增商品</a></li>    
					    <li><a href="<%= request.getContextPath() %>/store-end/pdc_mng/addSweetness.jsp">新增甜度</a></li>
					    <li><a href="<%= request.getContextPath() %>/store-end/pdc_mng/addIce.jsp">新增冰度</a></li>
					    <li><a href="<%= request.getContextPath() %>/store-end/pdc_mng/addExtra.jsp">新增加料</a></li>
					    <li role="separator" class="divider"></li>
					    <li><a href="<%= request.getContextPath() %>/store-end/pdc_mng/addMerge.jsp">合併商品</a></li>
					  </ul>
					</div>
				</li>
					
				<li class="bar">
					 <form class="form-inline" METHOD="post" ACTION="<%= request.getContextPath() %>/pdc_mng/StoPdcMng.do">					
					
					    <input type="text" name="com_name" value="" class="form-control ml-sm-5" placeholder="搜尋商品">
						<input type="hidden" name="action" value="getName_For_Display">
						<input type="hidden" name="sto_num" value="${sto_num}">
						<input type="submit" value="送出" class="btn btn-green">		
										
					</form>
				</li>
</ul>

	
	
	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.0.5/sweetalert2.min.js"></script>
	
</body>
</html>