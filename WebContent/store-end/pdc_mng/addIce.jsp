<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.ice_list.model.*"%>
<%@ page import="java.util.*"%>

<%
	IceListVO iceListVO = (IceListVO) request.getAttribute("iceListVO");
	String str = request.getQueryString();
	String sto_num = (String) session.getAttribute("sto_num");
	IceListService iceSvc = new IceListService(); 	
	List<IceListVO> list = iceSvc.getIceList(sto_num);
    pageContext.setAttribute("list",list);
%>

<html>
<head>

	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<title>�B����Ʒs�W</title>
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
        	<div class="panel-heading panel-green"><h3 class="text-center">�s�W�B��</h3></div>
	
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
					<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/pdc_mng/StoIceMng.do">
						
						<div class="form-group panel-form">
							<label for="sto_num" class="col-sm-3 control-label">���a�s��</label>
							<div class="col-sm-9">
								<input type="TEXT" name="sto_num" id="sto_num" value="${sto_num}" disabled class="form-control"/>	
							</div>
						</div>
						
						<div class="form-group panel-form">
							<label for="ice_type" class="col-sm-3 control-label">�B������</label>
							<div class="col-sm-9">
								<input type="TEXT" name="ice_type" id="ice_type" value="${iceListVO.ice_type}" class="form-control"/>	
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
	</div>	
	
	<!-- magic btn -->
	<button id="ice0" class="btn btn-xs magic">�h�B</button>
	<button id="ice3" class="btn btn-xs magic">�L�B</button>
	<button id="ice5" class="btn btn-xs magic">�b�B</button>
	<button id="ice7" class="btn btn-xs magic">�֦B</button>
	<button id="ice10" class="btn btn-xs magic">���`�B</button>
	
</div><!-- <div class="col-xs-12 col-sm-5"> -->		
	
<div class="col-xs-12 col-sm-7">

	<table class="table productList">
		<tr>

			<th>�B���W��</th>
			<th>���A</th>
	
		</tr>
		
		<c:forEach var="iceVO" items="${list}">
			
			<tr ${(iceVO.ice_num==param.ice_num)?'bgcolor=#DCE6D2':''}>

				<td>${iceVO.ice_type}</td>
				<td>${iceVO.status}</td>

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
		$('#ice0').click(function(){
			$('input[name=ice_type]').val("�h�B");
		});
		$('#ice3').click(function(){
			$('input[name=ice_type]').val("�L�B");
		});
		$('#ice5').click(function(){
			$('input[name=ice_type]').val("�b�B");
		});
		$('#ice7').click(function(){
			$('input[name=ice_type]').val("�֦B");
		});
		$('#ice10').click(function(){
			$('input[name=ice_type]').val("���`�B");
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