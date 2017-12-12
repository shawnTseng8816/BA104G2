<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.product.model.*"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="pdcTSvc" scope="request" class="com.product_type.model.ProductTypeService" />	
<jsp:useBean id="pdSvc" scope="request" class="com.product.model.ProductService" />
<jsp:useBean id="mcSvc" scope="request" class="com.merged_commodity.model.MergedCommodityService" />
<%
	ProductVO productVO = (ProductVO) request.getAttribute("productVO");
	String str = request.getQueryString();
	String sto_num = (String) session.getAttribute("sto_num");
	ProductService pdcSvc = new ProductService();	
    List<ProductVO> list = pdcSvc.stoFindAllProduct(sto_num);
    pageContext.setAttribute("list",list);
%>

<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<title>新增商品 - addProduct.jsp</title>
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
		<div class="panel">
        	<div class="panel-heading panel-green"><h3 class="text-center">新增商品</h3></div>
	
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
					<FORM id="insert" METHOD="post" ACTION="<%= request.getContextPath() %>/pdc_mng/StoPdcMng.do" enctype="multipart/form-data">
					
						<div class="form-group panel-form">
							<label for="sto_num" class="col-sm-3 control-label">店家編號</label>
							<div class="col-sm-8">
								<input type="TEXT" name="sto_num" id="sto_num" value="${sto_num}" disabled class="form-control"/>	
							</div>
						</div>
					
						<div class="form-group panel-form">
							<label for="com_name" class="col-sm-3 control-label">商品名稱</label>
							<div class="col-sm-8">
								<input type="TEXT" name="com_name" id="com_name" value="${productVO.com_name}" class="form-control"/>	
							</div>
						</div>
						
						<div class="form-group panel-form">
							<label for="m_price" class="col-sm-3 control-label">小杯商品價位</label>
							<div class="col-sm-8">
								<input type="TEXT" name="m_price" id="m_price" value="${productVO.m_price}" class="form-control"/>	
							</div>
						</div>						
						
						<div class="form-group panel-form">
							<label for="l_price" class="col-sm-3 control-label">大杯商品價位</label>
							<div class="col-sm-8">
								<input type="TEXT" name="l_price" id="l_price" value="${productVO.l_price}" class="form-control"/>	
							</div>
						</div>	

						<div class="form-group panel-form">
							<label for="discribe" class="col-sm-3 control-label">商品敘述</label>
							<div class="col-sm-8">
								<input type="TEXT" name="discribe" id="discribe" value="${productVO.discribe}" class="form-control"/>	
							</div>
						</div>	

						<div class="form-group panel-form">
							<label for="img" class="col-sm-3 control-label">商品圖片</label>
							<div class="col-sm-8">
								<input type="File" name="img" id="img" class="form-control"/>	
							</div>
						</div>	

						<div class="form-group panel-form">
							<label for="pt_num" class="col-sm-3 control-label">商品類別編號</label>
							<div class="col-sm-8">
								<select size="1" name="pt_num" class="form-control">
						         <c:forEach var="pdcTVO" items="${pdcTSvc.all}" > 
						         	<option value="${pdcTVO.pt_num}" ${(productVO.pt_num==pdcTVO.pt_num)? 'selected':'' } >${pdcTVO.pt_name}
						         </c:forEach>   
					       		</select>	
							</div>
						</div>		 
	 
						<div class="form-group panel-form">
							<label for="status" class="col-sm-3 control-label">商品狀態</label>
							<div class="col-sm-8">
								<select size="1" name="status" class="form-control">
									<option value="未上架" ${(productVO.status=='未上架')? 'selected':'' } >未上架
									<option value="已上架" ${(productVO.status=='已上架')? 'selected':'' } >已上架
								</select>	
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
	<button id="pdc1" class="btn btn-xs magic">金桔粒脆纖果</button>
	<button id="pdc2" class="btn btn-xs magic">橘子紅茶</button>
	<button id="pdc3" class="btn btn-xs magic">鮮奶綠</button>
	<button id="pdc4" class="btn btn-xs magic">鮮奶阿華田</button>
	<button id="pdc5" class="btn btn-xs magic">番茄梅</button>	
	
	
</div><!-- <div class="col-xs-12 col-sm-6"> -->		
	
<div class="col-xs-12 col-sm-7">
	

	<table class="table productList">
			<tr>
				<th>商品名稱</th>
				<th>小杯</th>
				<th>大杯</th>				
				<th>商品類別</th>
				<th>狀態</th>
				<th>合併狀態</th>
			</tr>
			
		
			<c:forEach var="PdcVO" items="${list}" >
			<tr ${(PdcVO.com_num==param.com_num)?'bgcolor=#DCE6D2':''}>
				<td>${PdcVO.com_name}</td>
				<td>${PdcVO.m_price}</td>
				<td>${PdcVO.l_price}</td>				
				<td>${pdcTSvc.getOnePdcT(PdcVO.pt_num).pt_name}</td>
				<td>${PdcVO.status}</td>
				<td>				
				<c:forEach var="mcVO" items="${mcSvc.getMerList(PdcVO.mercom_num)}" varStatus="p">
					<small>				
					${pdSvc.getOneProduct(mcVO.com_num).com_name} 
					${pdSvc.getOneProduct(mcVO.com_num).m_price}/
					${pdSvc.getOneProduct(mcVO.com_num).l_price}
					</small><br>
				</c:forEach>
				</td>
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
	<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.0.5/sweetalert2.min.js"></script>
	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script>
		

	
		$('#pdc1').click(function(){
			$('input[id=com_name]').val("金桔粒脆纖果");
			$('input[name=m_price]').val("20");
			$('input[name=l_price]').val("25");
			$('input[name=discribe]').val("金桔粒脆纖果");
			$('option[value=PT007]').attr('selected',true);
			$('option[value=已上架]').attr('selected',true);
		});
		$('#pdc2').click(function(){
			$('input[id=com_name]').val("橘子紅茶");
			$('input[name=m_price]').val("25");
			$('input[name=l_price]').val("30");
			$('input[name=discribe]').val("橘子紅茶");
			$('option[value=PT001]').attr('selected',true);
			$('option[value=已上架]').attr('selected',true);
		});
		$('#pdc3').click(function(){
			$('input[id=com_name]').val("鮮奶綠");
			$('input[name=m_price]').val("35");
			$('input[name=l_price]').val("50");
			$('input[name=discribe]').val("鮮奶綠");
			$('option[value=PT003]').attr('selected',true);
			$('option[value=已上架]').attr('selected',true);
		});
		$('#pdc4').click(function(){
			$('input[id=com_name]').val("鮮奶阿華田");
			$('input[name=m_price]').val("40");
			$('input[name=l_price]').val("50");
			$('input[name=discribe]').val("鮮奶阿華田");
			$('option[value=PT008]').attr('selected',true);
			$('option[value=已上架]').attr('selected',true);
		});
		$('#pdc5').click(function(){
			$('input[id=com_name]').val("番茄梅");
			$('input[name=m_price]').val("30");
			$('input[name=l_price]').val("45");
			$('input[name=discribe]').val("番茄梅");
			$('option[value=PT007]').attr('selected',true);
			$('option[value=未上架]').attr('selected',true);
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
		$(document).keydown(function( e ) {			
		    if( e.keyCode == '116' ){
		    	window.location.replace("<%= request.getContextPath() %>/store-end/pdc_mng/addProduct.jsp");
		    }
		});
		
		


	</script>
</body>
</html>