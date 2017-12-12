<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.extra.model.*"%>
<%@ page import="java.util.*"%>
<%
	ExtraVO extraVO = (ExtraVO) request.getAttribute("extraVO");
	String str = request.getQueryString();
	String sto_num = (String) session.getAttribute("sto_num");
	ExtraService extSvc = new ExtraService();
	List<ExtraVO> list = extSvc.getExtras(sto_num);
    pageContext.setAttribute("list",list);

%>

<html>
<head>

	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	
	<title>新增加料</title>
	<style>
	.magic{
		background-color:#FFFFFF;
		color:#ffd280;
	}
	.panel-green{
		border:1px solid #3C9682;
		color:#3C9682;	
	}
	.panel-form{
		margin-top:20px;
		margin-bottom:20px;
	}
	.productList{
		font-size:11pt;
	}
	</style>
	

</head>

<body>
	<jsp:include page="/store-end/store_top.jsp" /> <!-- navbar -->
	<!-- 1層大框框 -->
	<div class="container-fluid">
		<div class="row">
	<!-- 2層框左 -->	
			<jsp:include page="/store-end/store_left.jsp" /> <!-- leftSidebar -->
	<!-- 2層框右 -->		
			<div class="col-xs-12 col-sm-8 col-sm-offset-3" >				
				<div class="block-center panelheight">
	<!--========================== 功能放這邊 =============================================-->



<%-- 查詢+ListAll按鈕 --%>
<jsp:include page="/store-end/pdc_mng/btn_select.jsp" />


<div class="col-xs-12 col-sm-5">
	<div class="row">	
		<div class="panel ">
        	<div class="panel-heading panel-green"><h3 class="text-center">新增加料</h3></div>
	
				<div class="panel-body panel-green">
	
				<%-- 錯誤表列 --%>
				<c:if test="${not empty errorMsgs}">
					<font style="color:red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color:red">${message.value}</li>
						</c:forEach>
					</ul>
				</c:if>
				
				<div class="form-horizontal">
					<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/pdc_mng/StoExtMng.do">
						
						<div class="form-group panel-form">
							<label for="sto_num" class="col-sm-3 control-label">店家編號</label>
							<div class="col-sm-8">
								<input type="TEXT" name="sto_num" id="sto_num" value="${sto_num}" disabled class="form-control"/>	
							</div>
						</div>
		
						<div class="form-group panel-form">
							<label for="ext_name" class="col-sm-3 control-label">加料名稱</label>
							<div class="col-sm-8">
								<input type="TEXT" name="ext_name" id="ext_name" value="${extraVO.ext_name}" class="form-control"/>	
							</div>
						</div>				
		
						<div class="form-group panel-form">
							<label for="ext_amount" class="col-sm-3 control-label">加料金額</label>
							<div class="col-sm-8">
								<input type="TEXT" name="ext_amount" id="ext_amount" value="${extraVO.ext_amount}" class="form-control"/>	
							</div>
						</div>	
		
						<input type="hidden" name="sto_num" value="${sto_num}">
						<input type="hidden" name="action" value="insert">
						
						<div class="panel-footer ">
							<input type="submit" value="送出新增" class="btn btn-green btn-block panel-form">
						</div>	
						
					</FORM>
				</div><!-- from horizon -->	
			</div><!-- panel body -->
		</div><!-- panel -->	
	</div><!-- row -->	
	
	<!-- magic btn -->
	<button id="add1" class="btn btn-xs magic">珍珠</button>
	<button id="add2" class="btn btn-xs magic">椰果</button>
	<button id="add3" class="btn btn-xs magic">布丁</button>
	<button id="add4" class="btn btn-xs magic">仙草</button>
	<button id="add5" class="btn btn-xs magic">小紫蘇</button>	
	
	
</div><!-- <div class="col-xs-12 col-sm-5"> -->		

<div class="col-xs-12 col-sm-7">


	<table class="table productList">
		<tr>
			<th>加料名稱</th>
			<th>加料金額</th>
			<th>狀態</th>
		</tr>
		
	<c:forEach var="extVO" items="${list}">
			
		<tr ${(extVO.ext_num==param.ext_num)?'bgcolor=#DCE6D2':''}>
			<td>${extVO.ext_name}</td>
			<td>${extVO.ext_amount}</td>
			<td>${extVO.status}</td>
		</tr>
		
	</c:forEach>
	
	</table>
	
</div>




	
	
<!--========================== 功能放這邊 =============================================-->			
				</div><!-- class="block-center panelheight" -->			
			</div><!-- class="col-xs-12 col-sm-8 col-sm-offset-3" -->
		</div><!-- div class="row" -->
	</div><!-- div class="container-fluid" -->
	
	<jsp:include page="/store-end/store_foot.jsp" />
	
	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.0.5/sweetalert2.min.js"></script>	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script>
	$('#add1').click(function(){
		$('input[name=ext_name]').val("珍珠");
		$('input[name=ext_amount]').val("10");
	});
	$('#add2').click(function(){
		$('input[name=ext_name]').val("椰果");
		$('input[name=ext_amount]').val("5");
	});
	$('#add3').click(function(){
		$('input[name=ext_name]').val("布丁");
		$('input[name=ext_amount]').val("10");
	});
	$('#add4').click(function(){
		$('input[name=ext_name]').val("仙草");
		$('input[name=ext_amount]').val("10");
	});
	$('#add5').click(function(){
		$('input[name=ext_name]').val("小紫蘇");
		$('input[name=ext_amount]').val("10");
	});
	$('tr:even').css('background-color','#f1f3f3');
	
	$('document').ready(function(){
		
		var queryStr='<%= str %>';
		
		
		if(queryStr!='null'){
			swal({
				  position: 'center',
				  type: 'success',
				  title: '新增成功',
				  showConfirmButton: false,
				  timer: 1000
			 	})	
		}
	});
	
	//prevent F5
	$(document).keydown(function(e) {			
	    if( e.keyCode == '116' ){
	    	window.location.replace("<%= request.getContextPath() %>/store-end/pdc_mng/addExtra.jsp");
	    }
	});
	</script>
</body>
</html>