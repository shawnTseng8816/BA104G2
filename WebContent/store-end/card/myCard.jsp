<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.card.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="caSvc" scope="page" class="com.card.model.CardService" />

<%	

		CardService cardSvc = new CardService();
		List list = new ArrayList();
		list = cardSvc.getCardsBySto_num("ST0000000001");
		request.setAttribute("cardVO", list);

%>

<!DOCTYPE html>
<html lang="">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<title>Title Page</title>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
		
<link rel="stylesheet" type="text/css" href="css/store_base.css">
<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
		<style type="text/css">
			.body{
				font-family:Microsoft JhengHei;
			}
			.caption {
				font-size: 36px;
				color:#67AB9F;
				font-weight:bold;
			}
			.input-group-btn>.btn:active, .input-group-btn>.btn:focus, .input-group-btn>.btn:hover{
				border-color: #67AB9F;
				background-color:#67AB9F; 
				color:#ffffff;
			}

			.btn-info{
				border-color: #67AB9F;
				background-color:#ffffff; 
				color:#67AB9F;
			}
			.input-group-btn>.btn{
				position:absolute;
			}
			.navbar-form{
				margin: 0;
			}
			.form-control{
				border-color: #67AB9F;
			}

			.input-group-btn:last-child>.btn, .input-group-btn:last-child>.btn-group{
				margin:0;
			}
			table{
				border:1px solid #67AB9F; 
				/*width:98%;*/
			}
			.table>tbody>tr>td, .table>tfoot>tr>td, .table>thead>tr>td{
				border:1px solid #67AB9F; 
				border-style:dashed;
				/*https://www.1keydata.com/css-tutorial/tw/border.php*/ 
				color:#555;
				vertical-align: middle;
			}
			.table>tbody>tr:hover{
				font-weight: bold;
				background-color:#f3f3f3;
			}
			.table>caption+thead>tr:first-child>td, .table>caption+thead>tr:first-child>th, .table>colgroup+thead>tr:first-child>td, .table>colgroup+thead>tr:first-child>th, .table>thead:first-child>tr:first-child>td, .table>thead:first-child>tr:first-child>th{
				border-bottom: 0;
			}
			.pagination li.active a {
				background-color:#67AB9F;
				border-color:#67AB9F;
			    color: #ffffff;
			}
			.pagination>li>a, .pagination>li>span{
				border-color:#67AB9F;
				color:#67AB9F;
			}
			.pagination>li>a:hover{
				border-color:#67AB9F;
				background-color:#67AB9F;
				color:#ffffff;
			}
			.headerrow{ 
				background-color:#67AB9F !important;
			}
			.headerrow th{
				border:1px solid #67AB9F; 
				color:#ffffff; 
				text-align:center;
			}
			tr:nth-child(even) {
				background-color:#cbd8d6;
			}
			tr:nth-child(odd) {
				background-color:#ffffff;
				font-color:#555555;
			}
			.btn-default{
				border-color:#67AB9F;
				color:#67AB9F;
				background-color:#ffffff; 
			}
			.btn-default:hover{
				border-color:#67AB9F;
				color:#ffffff;
				background-color:#67AB9F; 
			}
			.popover {
				max-width:none; /*宽度改为由用户自行定义*/
			}
			#list-popover{
				width: 300px;
			}
		</style>
	</head>

	<body>
	<div>
	
	</div>
	<div class="container-fluid">
				<div class="row">
					<div class="col-xs-12 col-sm-3 ">
						<!-- XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX -->
					</div>
					<div class="col-xs-12 col-sm-8">
				<!--========================== 功能放這邊 =================================================================================================-->
					<div class="block-center panelheight">
						
						



						<table class="table table-hover" id="pricetable">
						<caption>
							<div class="caption">店家集點卡設定</div>
							<pre><h5><div style="color:red" >目前上架的集點卡編號為 : <c:out value="${caSvc.getStoNowCrad('ST0000000001').card_kinds}" default=" 目前無上架集點卡  !"/></div></h5></pre>
								<div class="input-group">
							
									</div>
						</caption>
						<thead>
						
							<tr class="headerrow">
								<th>集點卡編號</th>
								<th>需集滿</th>
								<th>可折扣金額</th>
								<th>折價券敘述</th>
								<th>有效期限(月)</th>
								<th>狀態</th>
								<th>操作</th>
							</tr>
						</thead>
						
						
						<tbody>
								<c:forEach var="cardVO" items="${cardVO}">
		<tr align='center' valign='middle' ><!--將修改的那一筆加入對比色而已-->
			<td>${cardVO.card_kinds}</td>
			<td>${cardVO.points}</td>
			<td>${cardVO.points_cash}</td>
			<td>${cardVO.card_des}</td>
			<td>${cardVO.exp_date}</td>
			<td id="cardstatus">${cardVO.status}</td>	
			<td>
			<c:if test="${cardVO.status.equals('下架')}">
			
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/CardServlet" style="margin-bottom: 0px;">			
			     <input type="submit"  class="btn btn-info" value="上架"> 
			     <input type="hidden" name="card_kinds"      value="${cardVO.card_kinds}">
			      <input type="hidden" name="sto_num"      value="${cardVO.sto_num}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->            
			     <input type="hidden" name="action"	    value="upCard"></FORM>
			</td>
			</c:if>
			<c:if test="${cardVO.status.equals('上架')}">
			 <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/CardServlet" style="margin-bottom: 0px;">
			     <input type="submit" class="btn btn-info" value="下架"> 
			     <!--要回傳的數值-->
			     <input type="hidden" name="sto_num"     value="${cardVO.sto_num}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->		             
			     <input type="hidden" name="action"	    value="downCard"></FORM>
			</td>
			</c:if>
	
		</tr>
	</c:forEach>
						</tbody>
					</table>			
						<!--========================== 功能放這邊 =================================================================================================-->
				</div>
				
				
				</div>
			</div>

			</div>




		
		<script src="https://code.jquery.com/jquery.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<script >
			// 改 panel裡面 active 項目顏色
			$("[class~='active']").css("background-color","#3C9682");

			// 改 panel外面 hover 顏色
			$("[class~='panel-heading']").hover(function(){$(this).css("background-color","#DCE6D2")},function(){$(this).css("background-color"," #FFFFFF")});
			
			// 改 panel裡面 hover 顏色
			$(".list-group > [class|='list-group-item']").hover(function(){$(this).css({"background-color":"#DCE6D2","color":"#595942"})},function(){$(this).css({"background-color":"#FFFFFF","color":"#595942"})});
		
$('[res="popover"]').popover({
				container: 'body',
				html: true,
				content: function () {
					var clone = $($(this).data('popover-content')).clone(true).removeClass('hide');
					return clone;
				}
			}).click(function(e) {
					e.preventDefault();
			});
			//https://stackoverflow.com/questions/13202762/html-inside-twitter-bootstrap-popover
			//http://fenturechance7.pixnet.net/blog/post/49190535-jquery%E7%9A%84trigger%E6%98%AF%E5%B9%B9%E5%98%9B%E7%9A%84

			//取消按鈕
			$('#delrow').click(function(){
				$('#pricetable tr input[type="checkbox"]:checked').each(function(){
				    if (this.checked) {
				         $(this).closest('tr').remove();
				    }
				    return false;
				})
			});
			//送出按鈕
			$(".bottombtn1").click(function(){
				$('#pricetable tr input[type="checkbox"]:checked').each(function(){
					var $row = $(this).closest('tr');
					//used :not(:first-child) to skip first element (the checkbox td)
					$('td:not(:first-child)', $row).each(function(i){
					    var abc = $(this).text();
					    alert(abc);
					})
				});              
			});// 按鈕那邊很微妙XD




</script>
	</body>
</html>