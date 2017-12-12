<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>
<jsp:useBean id="pdcTSvc" scope="request" class="com.product_type.model.ProductTypeService" />	
<jsp:useBean id="pdSvc" scope="request" class="com.product.model.ProductService" />
<jsp:useBean id="mcSvc" scope="request" class="com.merged_commodity.model.MergedCommodityService" />

<%
	String sto_num = (String) session.getAttribute("sto_num");
	ProductService pdcSvc = new ProductService();
    List<ProductVO> list = pdcSvc.stoFindAllProduct(sto_num);
    pageContext.setAttribute("list",list);
    ProductVO productVO = (ProductVO) request.getAttribute("productVO");  	
	session.setAttribute("addform","permit");	//�qadd�����ӱo���ӳq����

%>


<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<title>�X�ְӫ~��Ʒs�W - addProduct.jsp</title>
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
	.list-hover{
		border-width: 0px 5px;
		border-color: #3C9682;
		border-style: solid;
	}

	#tdstyle>tbody>tr>td{
			vertical-align:middle;
			background-color:#FFFFFF;
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

<div class="panel">

	<div class="panel-body panel-green">
	
		<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/pdc_mng/StoPdcMng.do"  enctype="multipart/form-data">

			<div id="pdclist" class="table-responsive">
				
				<h3 class="text-center">�ӫ~��ƦX��</h3>
				
				<table class="table" id=" tdstyle">
					<tr>
						<th><input type="checkbox" name="selectall"></th>
						<th>�ӫ~�s��</th>		
						<th>�ӫ~�W��</th>
						<th>�p�M����</th>
						<th>�j�M����</th>
						<th>�y�z</th>
						<th>�Ϥ�</th>
						<th>�ӫ~���O</th>
						<th>���A</th>
						<th>�X�֪��A</th>		
					</tr>		
					
				<c:forEach var="PdcVO" items="${list}" >
				
					<tr ${(PdcVO.com_num==param.com_num)?'bgcolor=#DCE6D2':''}>					
						<td>
									
								<input type="checkbox" name="checkbox" value="${PdcVO.com_num}" 
									<c:forEach var="ckList" items="${ckList}">
										${( ckList==PdcVO.com_num ) ? 'checked':'' } 
									</c:forEach>
										${( PdcVO.mercom_num==null) ? '':'disabled'}
								>
								
						</td>
						<td>${PdcVO.com_num}</td>	
						<td class="com_name">${PdcVO.com_name}</td>
						<td class="m_price">${PdcVO.m_price}</td>
						<td class="l_price">${PdcVO.l_price}</td>
						<td>${PdcVO.discribe}</td>
						<td><img height=50 src="<%=request.getContextPath()%>/PdcGifReader?com_num=${PdcVO.com_num}"></td> 
						<td>${pdcTSvc.getOnePdcT(PdcVO.pt_num).pt_name}</td>
						<td>${PdcVO.status}</td>
						<td width=200>				
						<c:forEach var="mcVO" items="${mcSvc.getMerList(PdcVO.mercom_num)}" varStatus="p">
							
							<small>				
								${pdSvc.getOneProduct(mcVO.com_num).com_name} 
								${pdSvc.getOneProduct(mcVO.com_num).m_price} /
								${pdSvc.getOneProduct(mcVO.com_num).l_price}
							</small><br>
							
						</c:forEach>
						</td>						
					</tr>
				</c:forEach>	
				</table>
			</div>	<!--id=pdclist-->
		
			<div>
				<div class="col-xs-12 col-sm-7">
					<button type="button" id="getcheckbox" class="btn btn-green-rvs btn-block">��ܰӫ~</button>	
				</div>
				<div class="col-xs-12 col-sm-5">
					<button type="button" id="reset" class="btn btn-org btn-block ">�M��</button>	
				</div>
			</div>	
	
	
		
			<div id="insert">
				<div class="container-fluid ">
				
					<div class="col-xs-12 col-sm-6 area50">
						<div class="form-horizontal">
							<%-- ���~��C --%>
							<c:if test="${not empty errorMsgs}">
								<div id="err">
									<font style="color:red">�Эץ��H�U���~:</font>
									<ul>
										<c:forEach var="message" items="${errorMsgs}">
											<li style="color:red">${message.value}</li>
										</c:forEach>
									</ul>
								</div>	
							</c:if>
							
							<div class="panel-heading panel-green"><h3 class="text-center">�s�W�X�ְӫ~</h3></div>
								
								<div class="panel-body panel-green">	
								
									<div class="form-group panel-form">
										<label for="com_name" class="col-sm-3 control-label">�ӫ~�W��</label>
										<div class="col-sm-8">
											<input type="TEXT" name="com_name" id="com_name" value="${productVO.com_name}" class="form-control"/>	
										</div>
									</div>
					
									<div class="form-group panel-form">
										<label for="m_price" class="col-sm-3 control-label">�p�M����</label>
										<div class="col-sm-8">
											<input type="TEXT" name="m_price" id="m_price" value="${productVO.m_price}" class="form-control"/>	
										</div>
									</div>			
								
									<div class="form-group panel-form">
										<label for="l_price" class="col-sm-3 control-label">�j�M����</label>
										<div class="col-sm-8">
											<input type="TEXT" name="l_price" id="l_price" value="${productVO.l_price}" class="form-control"/>	
										</div>
									</div>				
								
									<div class="form-group panel-form">
										<label for="discribe" class="col-sm-3 control-label">�ӫ~�y�z</label>
										<div class="col-sm-8">
											<input type="TEXT" name="discribe" id="discribe" value="${productVO.discribe}" class="form-control"/>	
										</div>
									</div>	
									
									<div class="form-group panel-form">
										<label for="img" class="col-sm-3 control-label">�ӫ~�Ϥ�</label>
										<div class="col-sm-8">
											<input type="File" name="img" id="img" class="form-control"/>	
										</div>
									</div>			
					
									<div class="form-group panel-form">
										<label for="pt_num" class="col-sm-3 control-label">�ӫ~���O</label>
										<div class="col-sm-8">
											<select size="1" name="pt_num" class="form-control">
									         <c:forEach var="pdcTVO" items="${pdcTSvc.all}" > 
									         	<option value="${pdcTVO.pt_num}" ${(productVO.pt_num==pdcTVO.pt_num)? 'selected':'' } >${pdcTVO.pt_name}
									         </c:forEach>   
								       		</select>	
										</div>
									</div>		 
					
									<div class="form-group panel-form">
										<label for="status" class="col-sm-3 control-label">�ӫ~���A</label>
										<div class="col-sm-8">
											<select size="1" name="status" class="form-control">
												<option value="���W�[" ${(productVO.status=='���W�[')? 'selected':'' } >���W�[
												<option value="�w�W�[" ${(productVO.status=='�w�W�[')? 'selected':'' } >�w�W�[
											</select>	
										</div>
									</div>			
								
									<input type="hidden" name="sto_num" value="${sto_num}">
									<input type="hidden" name="action" value="insert_merge">
								
								</div><!-- panel body -->							
							</div><!-- form-horizontal-->
					</div><!--col-xs-12 col-sm-5 -->		
							
					
					<div class="col-xs-12 col-sm-6 area50">
						<div class="panel-heading panel-green"><h3 class="text-center">��ܪ��X�ְӫ~</h3></div>
						<div class="panel-body panel-green">	
							<table id="mergelist" class="table">
								<thead>
								<tr><th>�X�ְӫ~</th><th>�p�M����</th><th>�j�M����</th></tr>
								</thead>
							</table>	
						</div>			
					</div>
				
				</div><!-- class="container-fluid" -->
				
					<input type="submit" value="�e�X�X�ְӫ~" class="btn btn-green btn-block btn-lg panel-form ">
				
			</div><!--id=insert-->				
		
		</FORM>
	</div><!-- panel body -->
</div><!-- panel -->	
	
	
<!--========================== �\���o�� =============================================-->			


				</div><!-- class="block-center panelheight" -->			
			</div><!-- class="col-xs-12 col-sm-8 col-sm-offset-3" -->
		</div><!-- div class="row" -->
	</div><!-- div class="container-fluid" -->
	
<jsp:include page="/store-end/store_foot.jsp" />
	
	
<script src="https://code.jquery.com/jquery.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script>

	var add="";
	var mprice,lprice;

	$(document).ready(function () {
		$('#insert').hide();
		//�w�O�X�ְӫ~�S��checkbox		
		$("input[type=checkbox]:disabled").remove();		

		//���~�B�z���������FORM���
		if(location.pathname=="/BA104G2/pdc_mng/StoPdcMng.do"){
			$("#pdclist").hide("slow");
			$('#insert').show("slow");
			$("#mercom").text("${productVO.com_name}");
		}
	});	
		
		//����+������
		$("input[name=selectall]").change('click',function(){
			var checkboxes = $('input[name="checkbox"]:enabled');
			$(this).is(':checked') ? checkboxes.prop('checked', 'checked') : checkboxes.removeAttr('checked');			
		});
		
		//�X�֫��s
		$('#getcheckbox').on('click',function(){
			var pname="";
			var m_price=0;
			var l_price=0;
			$("input[name=selectall]").removeAttr('checked');
			$("input[type=checkbox]:checked").each(function(i){				
				if($(this).is(':enabled')){	//�D�X�ְӫ~�~�|�p��
					 pname = pname+$(this).parent().siblings("td.com_name").text()+"_";
					 m_price = m_price+parseInt($(this).parent().siblings("td.m_price").text());
					 l_price = l_price+parseInt($(this).parent().siblings("td.l_price").text());
				}
				//���mergelist
				$('#mergelist').append(
						'<tr><td>'+$(this).parent().siblings("td.com_name").text()+'</td>'+
							'<td>'+$(this).parent().siblings("td.m_price").text()+'</td>'+
							'<td>'+$(this).parent().siblings("td.l_price").text()+'</td></tr>'
				);
			});
		
			var pname2 = pname.substring(0, pname.length-1);
			
			
			
			//��ȶi�h�U����FORM
			$("#mercom").text(pname2);
			$("#insert").find("input[name=com_name]").val(pname2.trim());
			$("input[name=m_price]").val(m_price);
			$("input[name=l_price]").val(l_price);
			$("input[name=discribe]").val(pname2.trim());
			
			//�ӫ~�M������
			$("#pdclist").toggle("slow");
			$('#insert').toggle("slow");
			
			//���b����̤W��
			$('body').scrollTop(0);			

			return false;
		});		
		
		
		//�M�����s
		$('#reset').on('click',function(){	
			$("input[type=checkbox]").each(function(){
				$(this).prop("checked",false);
			});
			var all_Inputs = $("input[type=text]");
			all_Inputs.val("");
			$("#pdclist").show("fast");
			$('#insert').hide("fast");
			
			$('#err').remove();
			$('#mergelist').find('tbody').remove();
			return false;		
		});
		
	
	
		$('#pdclist').find('tr').hover(
			function(){
				$(this).css("background-color","#ffe4b3");
			},
			function(){
				$(this).css("background-color","#FFFFFF");
			}
		);
	
		$('#pdclist tr').click(function(event){
					
			if(event.target.type !== 'checkbox'){
				$(':checkbox',this).trigger('click');
			}			
		});

</script>
</body>
</html>