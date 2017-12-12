<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.sweetness.model.*"%>
<%@ page import="java.util.*"%>
<%
	SweetnessVO sweetnessVO = (SweetnessVO) request.getAttribute("sweetnessVO");
	String str = request.getQueryString();
	String sto_num = (String) session.getAttribute("sto_num");
	SweetnessService swtSvc = new SweetnessService();
	List<SweetnessVO> list = swtSvc.getSweetness(sto_num);
    pageContext.setAttribute("list",list);
%>

<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<title>�s�W����</title>
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
	<!-- 1�h�j�خ� -->
	<div class="container-fluid">
		<div class="row">
	<!-- 2�h�إ� -->	
			<jsp:include page="/store-end/store_left.jsp" /> <!-- leftSidebar -->
	<!-- 2�h�إk -->		
			<div class="col-xs-12 col-sm-8 col-sm-offset-3" >				
				<div class="block-center panelheight">
	<!--========================== �\���o�� =============================================-->


<%-- �d��+ListAll���s --%>
<jsp:include page="/store-end/pdc_mng/btn_select.jsp" />

<div class="col-xs-12 col-sm-5">
	<div class="row">	
		<div class="panel ">
        	<div class="panel-heading panel-green"><h3 class="text-center">�s�W����</h3></div>
	
				<div class="panel-body panel-green">

				<%-- ���~��C --%>				
				<c:if test="${not empty errorMsgs}">
					<font style="color:red">�Эץ��H�U���~:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color:red">${message.value}</li>
						</c:forEach>
					</ul>
				</c:if>
				
				<div class="form-horizontal">
					<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/pdc_mng/StoSwtMng.do">
						
						<div class="form-group panel-form">
							<label for="sto_num" class="col-sm-3 control-label">���a�s��</label>
							<div class="col-sm-9">
								<input type="TEXT" name="sto_num" id="sto_num" value="${sto_num}" disabled class="form-control"/>	
							</div>
						</div>
						
						<div class="form-group panel-form">
							<label for="sweet_type" class="col-sm-3 control-label">���צW��</label>
							<div class="col-sm-9">
								<input type="TEXT" name="sweet_type" id="sweet_type" value="${sweetnessVO.sweet_type}" class="form-control"/>	
							</div>
						</div>
						
																
						<input type="hidden" name="sto_num" value="${sto_num}">
						<input type="hidden" name="action" value="insert">
						<div class="panel-footer ">
							<input type="submit" value="�e�X�s�W" class="btn btn-green btn-block panel-form">
						</div>
					</FORM>
				</div><!-- from horizon -->	
			</div><!-- panel body -->
		</div><!-- panel -->	
	</div><!-- row -->
	
		<!-- magic btn -->
	<button id="swt1" class="btn btn-xs magic">�L�}</button>
	<button id="swt2" class="btn btn-xs magic">�L�}</button>
	<button id="swt3" class="btn btn-xs magic">�b�}</button>
	<button id="swt4" class="btn btn-xs magic">�ֿ}</button>
	<button id="swt5" class="btn btn-xs magic">���}</button>	
	
</div><!-- <div class="col-xs-12 col-sm-5"> -->		
	
<div class="col-xs-12 col-sm-7">
	
	<table class="table productList">
		<tr>
			<th>���צW��</th>
			<th>���A</th>
		</tr>
		
		<c:forEach var="swtVO" items="${list}">
			
			<tr ${(swtVO.sweet_num==param.sweet_num)?'bgcolor=#DCE6D2':''}>
				<td>${swtVO.sweet_type}</td>
				<td>${swtVO.status}</td>
			</tr>
			
		</c:forEach>
		
	</table>
		
</div>
	
	
		
<!--========================== �\���o�� =============================================-->			
				</div><!-- class="block-center panelheight" -->			
			</div><!-- class="col-xs-12 col-sm-8 col-sm-offset-3" -->
		</div><!-- div class="row" -->
	</div><!-- div class="container-fluid" -->
	
	<jsp:include page="/store-end/store_foot.jsp" />
	<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.0.5/sweetalert2.min.js"></script>	
	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script>
	
	$('#swt1').click(function(){
		$('input[name=sweet_type]').val("�L�}");
	});
	$('#swt2').click(function(){
		$('input[name=sweet_type]').val("�L�}");
	});
	$('#swt3').click(function(){
		$('input[name=sweet_type]').val("�b�}");
	});
	$('#swt4').click(function(){
		$('input[name=sweet_type]').val("�ֿ}");
	});
	$('#swt5').click(function(){
		$('input[name=sweet_type]').val("���}");		
	});
	$('tr:even').css('background-color','#f1f3f3');
	
	$('document').ready(function(){
		
		var queryStr='<%= str %>';
		
		
		if(queryStr!='null'){
			swal({
				  position: 'center',
				  type: 'success',
				  title: '�s�W���\',
				  showConfirmButton: false,
				  timer: 1000
			 	})	
		}
	});
	
	//prevent F5
	$(document).keydown(function(e) {			
	    if( e.keyCode == '116' ){
	    	window.location.replace("<%= request.getContextPath() %>/store-end/pdc_mng/addSweetness.jsp");
	    }
	});
	</script>
</body>
</html>