<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>
<%@ page import="com.merged_commodity.model.*"%>
<jsp:useBean id="pdcTSvc" scope="request" class="com.product_type.model.ProductTypeService" />
<jsp:useBean id="pdSvc" scope="request" class="com.product.model.ProductService" />
<jsp:useBean id="mcSvc" scope="request" class="com.merged_commodity.model.MergedCommodityService" />

<%
	String sto_num = (String) session.getAttribute("sto_num");
	ProductService pdcSvc = new ProductService();	
    List<ProductVO> list = pdcSvc.stoFindAllProduct(sto_num);
    pageContext.setAttribute("list",list);
%>

<html>
<head>
	
	<title>所有商品資料</title>	
	<style>
		.list>tbody>tr>td{
			vertical-align:middle;
		}
	</style>
</head>


<body>
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

	<table class="table list">
		<tr>
			<th>商品編號</th>		
			<th>商品名稱</th>
			<th>小杯價錢</th>
			<th>大杯價錢</th>
			<th>描述</th>
			<th>圖片</th>
			<th>商品類別</th>
			<th>狀態</th>
			<th>合併狀態</th>
			<th>修改</th>
			<th>刪除</th>
		</tr>
		

		<c:forEach var="PdcVO" items="${list}" >
		<tr ${(PdcVO.com_num==param.com_num)?'bgcolor=#DCE6D2':''}>
			<td class="aaa">${PdcVO.com_num}</td>	
			<td>${PdcVO.com_name}</td>
			<td>${PdcVO.m_price}</td>
			<td>${PdcVO.l_price}</td>
			<td>${PdcVO.discribe}</td>
			<td><img height=50 src="<%=request.getContextPath()%>/PdcGifReader?com_num=${PdcVO.com_num}"></td> 
			<td>${pdcTSvc.getOnePdcT(PdcVO.pt_num).pt_name}</td>
			<td>${PdcVO.status}</td>
			<td width=200>				
			<c:forEach var="mcVO" items="${mcSvc.getMerList(PdcVO.mercom_num)}" varStatus="p">
				<small>				
				${pdSvc.getOneProduct(mcVO.com_num).com_name} 
				小杯 ${pdSvc.getOneProduct(mcVO.com_num).m_price}
				大杯 ${pdSvc.getOneProduct(mcVO.com_num).l_price}
				</small><br>
			</c:forEach>
			</td>
			
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/pdc_mng/StoPdcMng.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改"  class="btn btn-green">
			     <input type="hidden" name="com_num" value="${PdcVO.com_num}">
			      <input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM id="form_delete" METHOD="post" ACTION="<%=request.getContextPath()%>/pdc_mng/StoPdcMng.do" style="margin-bottom: 0px;">
			     <input type="button" value="刪除" class="btn btn-org delete">
			     <input type="hidden" name="com_num"  value="${PdcVO.com_num}">
			     <input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
			     <input type="hidden" name="action" value="delete" ></FORM>
			</td>
		</tr>
		</c:forEach>		

	</table>


<script>
	//table hover color
	$('tr').hover(
		function(){
			$(this).css("background-color","#ffe4b3");
		},
		function(){
			$(this).css("background-color","#FFFFFF");
		}
	);
	
	//delete alert
	$('.delete').click(function(){
		swal({
			  title: '確定要刪除資料?',
			  text: "資料刪除後無法回復",
			  type: 'warning',
			  showCancelButton: true,
			  confirmButtonColor: '#3C9682',
			  cancelButtonColor: '#FA5532',
			  confirmButtonText: '刪除',
			  cancelButtonText: '取消'
			}).then((result) => {
			  if (result.value) {
				  swal({
					  position: 'center',
					  type: 'success',
					  title: '資料已刪除',
					  showConfirmButton: false,
					  timer: 1000
				 	}).then((result) => {					  
			 			  $("#form_delete").submit();					  
					})
			  }
			})
	});
	
// 	$('document').ready(function(){
		
// 		var queryStr='${param.com_num}';
// 		if(queryStr!='null'){
// 			swal({
// 				  position: 'center',
// 				  type: 'success',
// 				  title: '修改成功',
// 				  showConfirmButton: false,
// 				  timer: 1000
// 			 	})	
// 		}
// 	});

	
</script>
</body>
</html>