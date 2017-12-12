<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.0.5/sweetalert2.min.css">
<style>
	.bar{
		margin-right:20px;
	}

</style>
</head>

<body>

 <h2 class="text-center"><strong>後臺人員管理</strong></h2>

<ul class="nav nav-pills page-header">

		<div class=" btn-group" >

			<a class="btn btn-green " href="<%= request.getContextPath() %>/back-end/bks_mng/bksmng_select_page.jsp">員工列表</a>				
			<a class="btn btn-green " href="<%= request.getContextPath() %>/back-end/bks_mng/addStaff.jsp">新增員工</a>
			
		</div>

</ul>


	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.0.5/sweetalert2.min.js"></script>

</body>
</html>