<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.backstage_management.model.*"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="flSvc" scope="request" class="com.func_list.model.FuncListService" />

<%
	
	BackstageManagementVO bmVO = (BackstageManagementVO) request.getAttribute("bmVO");
	BackstageManagementService bmSvc = new BackstageManagementService();	
	List<BackstageManagementVO> list = bmSvc.getAll();
    pageContext.setAttribute("list",list);
    String str = request.getQueryString();
%>

<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<title>�s�W��O�H�� - addProduct.jsp</title>
	<style>
	.magic{
		background-color:#FFFFFF;
		color:#ffd280;
	}
	.panel-green{
		border:1px solid #595942;
		color:#595942;	
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
	<jsp:include page="/back-end/back_top.jsp" /> <!-- navbar -->
	<!-- 1�h�j�خ� -->
	<div class="container-fluid">
		<div class="row">
	<!-- 2�h�إ� -->	
			<jsp:include page="/back-end/back_left.jsp" /> <!-- leftSidebar -->
	<!-- 2�h�إk -->		
			<div class="col-xs-12 col-sm-8 col-sm-offset-3" >				
				<div class="block-center panelheight">
	<!--========================== �\���o�� =============================================-->

<%-- �d��+ListAll���s --%>
<jsp:include page="/back-end/bks_mng/btn_select.jsp" />

<div class="col-xs-12 col-sm-5">
	<div class="row">	
		<div class="panel ">
        	<div class="panel-heading panel-green"><h3 class="text-center">�s�W��O�H��</h3></div>
	
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
				<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/bks_mng/BksMng.do" enctype="multipart/form-data">
					
					<div class="form-group panel-form">
						<label for="bm_name" class="col-sm-3 control-label">���u�W��</label>
						<div class="col-sm-9">
							<input type="TEXT" name="bm_name" id="bm_name" value="${bmVO.bm_name}" class="form-control"/>	
						</div>
					</div>
					
					<div class="form-group panel-form">
						<label for="bm_num" class="col-sm-3 control-label">���u�b��</label>
						<div class="col-sm-9">
							<input type="TEXT" name="bm_num" id="bm_num" value="${bmVO.bm_num}" class="form-control"/>	
							<span id = "checkicon"></span>
						</div>
					</div>					

					<div class="form-group panel-form">
						<label for="bm_number" class="col-sm-3 control-label">���u���</label>
						<div class="col-sm-9">
							<input type="TEXT" name="bm_number" id="bm_number" value="${bmVO.bm_number}" class="form-control"/>	
						</div>
					</div>					
					
					<div class="form-group panel-form">
						<label for="bm_mail" class="col-sm-3 control-label">���u�H�c</label>
						<div class="col-sm-9">
							<input type="TEXT" name="bm_mail" id="bm_mail" value="${bmVO.bm_mail}" class="form-control"/>	
						</div>
					</div>						
					
					<div class="form-group panel-form">
						<label for="bm_banknum" class="col-sm-3 control-label">���u�Ȧ�b��</label>
						<div class="col-sm-9">
							<input type="TEXT" name="bm_banknum" id="bm_banknum" value="${bmVO.bm_banknum}" class="form-control"/>	
						</div>
					</div>
					
					<div class="form-group panel-form">
						<label for="bm_img" class="col-sm-3 control-label">���u�Ӥ�</label>
						<div class="col-sm-9">
							<input type="File" name="bm_img" class="form-control"/>	
						</div>
					</div>					
					
					<div class="form-group panel-form">
						<label for="" class="col-sm-3 control-label">���u�v��</label>
						<div class="col-sm-9">
							<c:forEach var="funcVO" items="${flSvc.all}">
								<label class="h4 checkbox-inline">
									<input type="checkbox" name=func value="${funcVO.func_no}"
									
										<c:forEach var="funcList" items="${funcList}">
											${(funcList==funcVO.func_no)? 'checked':'' }
										</c:forEach>					
									
									> ${funcVO.func_name} 
								</label><br>
							</c:forEach>
						</div>
					</div>						
										

					
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
	<button id="bkm1" class="btn btn-xs magic">m</button>
	<button id="bkm2" class="btn btn-xs magic">��</button>
	<button id="bkm3" class="btn btn-xs magic">��</button>
	<button id="bkm4" class="btn btn-xs magic">��</button>		
	
</div><!-- <div class="col-xs-12 col-sm-5"> -->		
	
<div class="col-xs-12 col-sm-7">
		<table class="table list">
		<tr>
	
			<th>�W��</th>
			<th>���</th>
			<th>�H�c</th>
			<th>�H���b��</th>
			<th>���A</th>
		</tr>
		
	
		<c:forEach var="bmVO" items="${list}" >
		<tr ${(bmVO.bm_no==param.bm_no)?'bgcolor=#DCE6D2':''}>

			<td>${bmVO.bm_name}</td>
			<td>${bmVO.bm_number}</td>
			<td>${bmVO.bm_mail}</td>
			<td>${bmVO.bm_num}</td>
			<td>${bmVO.bm_jstatus}</td>
		</tr>
		</c:forEach>		
		
	</table>
</div>


		
<!--========================== �\���o�� =============================================-->			
				</div><!-- class="block-center panelheight" -->			
			</div><!-- class="col-xs-12 col-sm-8 col-sm-offset-3" -->
		</div><!-- div class="row" -->
	</div><!-- div class="container-fluid" -->
	
	<jsp:include page="/back-end/back_foot.jsp" />
	<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.0.5/sweetalert2.min.js"></script>	
	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script>
	$('input[type=submit]').on('click',function(){
		$('form').submit();
		swal({
			  position: 'center',
			  type: '',
			  title: '���b�x�s...',
			  allowOutsideClick: false,
			  showConfirmButton: false,
			  timer: 5000
			})
		$(this).prop('disabled',true)
		
	});
	
	
	//�ˬd�b���O�_����
	$('#bm_num').blur(checkBm_num);
	
	function checkBm_num(){
		$('#checkicon').empty();
		var a = $('#bm_num').val();
		console.log(a);
		console.log($('#bm_num').val().length);
		if($('#bm_num').val().length<2){
			$('#checkicon').removeClass("glyphicon glyphicon-remove-sign");
			$('#checkicon').removeClass("glyphicon glyphicon-ok-sign");
			$('#checkicon').addClass("glyphicon glyphicon-question-sign");
			$('#checkicon').css("color","orange");
			$('#checkicon').append('�п�J2-10�r�b��');
		}else{
			 $.ajax({
				    url: '/BA104G2/bks_mng/BksMng.do',
				    type: 'GET',
				    data: {
				    	action: 'check',
				    	bm_num: $('#bm_num').val()
				    },
				    error: function(xhr) {
				      alert('Ajax request �o�Ϳ��~');
				    },
				    success: function(response) {
				    	console.log(response);
				    	if(response==0){
				    		$('#checkicon').removeClass("glyphicon glyphicon-remove-sign");
				    		$('#checkicon').addClass("glyphicon glyphicon-ok-sign");
				    		$('#checkicon').css("color","#3C9682");
				    		$('#checkicon').text("�b���i�H�ϥ�");
				    	}else{
				    		
				    		$('#checkicon').removeClass("glyphicon glyphicon-ok-sign");
				    		$('#checkicon').addClass("glyphicon glyphicon-remove-sign");
				    		$('#checkicon').css("color","#FA5532");
				    		$('#checkicon').text("�b���w�Q�ϥ�");
				    	} 
				    }
			 });
		}
	}
	
	//magic btn
	
		$('#bkm1').click(function(){
			$('input[name=bm_name]').val("Max");
			$('input[name=bm_num]').val("Max");
			$('input[name=bm_number]').val("0910-012012");
			$('input[name=bm_mail]').val("maxchensw428@gmail.com");
			$('input[name=bm_banknum]').val("01200012121");			
		});
	
		$('#bkm2').click(function(){
			$('input[name=bm_name]').val("shawn");
			$('input[name=bm_num]').val("shawn");
			$('input[name=bm_number]').val("0911-018018");
			$('input[name=bm_mail]').val("tyhshawn@gmail.com");
			$('input[name=bm_banknum]').val("01800018181");	
		});
		
		$('#bkm3').click(function(){
			$('input[name=bm_name]').val("Peiiun");
			$('input[name=bm_num]').val("Peiiun");
			$('input[name=bm_number]').val("0952-008008");
			$('input[name=bm_mail]').val("peiiun7887@gmail.com");
			$('input[name=bm_banknum]').val("00800008080");	
		});
		
		$('#bkm4').click(function(){
			$('input[name=bm_name]').val("yachi");
			$('input[name=bm_num]').val("yachi");
			$('input[name=bm_number]').val("0972-030030");
			$('input[name=bm_mail]').val("yachi37@gmail.comm");
			$('input[name=bm_banknum]').val("0300030030");	
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
		    	window.location.replace("<%= request.getContextPath() %>/back-end/bks_mng/addStaff.jsp");
		    }
		});
	

	
	</script>
</body>
</html>