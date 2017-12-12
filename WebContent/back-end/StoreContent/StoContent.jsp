<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.report_member.model.*"%>
<%
	List<ReportMemberVO> listReportMember = (List<ReportMemberVO>)request.getAttribute("listReportMember");
	String stoContentStyleChinese = (String)request.getAttribute("stoContentStyleChinese");
%>

<!DOCTYPE html>
<html lang="">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<title>店家被檢舉</title>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
		<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
		<style type="text/css">
			thead th {
			    font-size: 20px;
			}
			tbody td, thead th {
			    width: 20%;
			}
			h1 {
				font-weight: bold;
				color: red;
			}
			table{  
				width:100px;  
				table-layout:fixed;
			}  
			
			</style>
		
		<jsp:include page="/back-end/back_top.jsp" /> <!-- navbar -->
		
		<style>	
		
			td{  
				width:100%;  
				word-break:keep-all;  
				white-space:nowrap;  
				overflow:hidden;
				text-overflow:ellipsis; 
			} 
			.block{
				height:450px;
			}
			p ,span {

				color:#000000;
			}
			
			.glyphicon {
				color:#DCE6D2;
			}
			
			.glyphicon-pencil{
				color:#000000;
			}
			.tall{
	        	height:40px;
	        }
		</style>
	</head>
	<body>
	
	
		
	<!-- 1層大框框 -->
	<div class="container-fluid">
		<div class="row">
	<!-- 2層框左 -->	
			<jsp:include page="/back-end/back_left.jsp" /> <!-- leftSidebar -->
	<!-- 2層框右 -->		
			<div class="col-xs-12 col-sm-8 col-sm-offset-3" >				
				<div class="block-center panelheight">
				
	<!--========================== 功能放這邊 ===↓↓↓↓↓↓↓↓↓↓==========================================-->
	
	
	
		<div class="container">
			<div class="row">
		<!-- sto_content========================================================================= -->
				<div class="col-xs-12 col-sm-12">
					<div class="sto_content">
						<div style="background-color:#595942;">
							<h1 class="text-center tall" style="color:#ffffff;">被檢舉店家處理</h1>
						</div>
							<form method="post" action="/BA104G2/contentStore/contentStore.do" id="formSto">
								<div class="text-right">
									<select name="stoContentStyle" id="stoContentStyle">
										<c:if test='${stoContentStyleChinese.equals("待處理")}'>
											<option value="待處理" selected="">待處理</option>
							         		<option value="已完成">已完成</option>
							         		<option value="全部">全部</option>
							         	</c:if>
							         	<c:if test='${stoContentStyleChinese.equals("已完成")}'>
											<option value="待處理">待處理</option>
							         		<option value="已完成" selected="">已完成</option>
							         		<option value="全部">全部</option>
							         	</c:if>
							         	<c:if test='${stoContentStyleChinese.equals("全部") || stoContentStyleChinese==null}'>
											<option value="待處理">待處理</option>
							         		<option value="已完成">已完成</option>
							         		<option value="全部" selected="">全部</option>
							         	</c:if>
							         </select>
								</div>
							</form>
						<%@ include file="StoContentPage1.file" %>
						<div class="block">
							<table class="table table-fixed">
								<thead>	
									<tr>
										<th>時間</th>
										<th>被檢舉店家</th>
										<th>檢舉會員</th>
										<th>檢舉原因</th>
										<th>處理方式</th>
										<th></th>
									</tr>
								</thead>
								<tbody>
								
									<c:forEach var="sto_content" items="${listReportMember}" varStatus="s" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
										<form method="get" action="<%=request.getContextPath()%>/contentStore/contentStore.do">
										<tr>
											<td>${sto_content[0].getRpt_time()}</td>
											<td>
												<a href="/BA104G2/store_detail/store_detail.do?sto_num=${sto_content[0].getSto_num()}" target="_blank">
													${sto_content[1].getSto_name()}
												</a>
											</td>
											<td><a href="">${sto_content[2].getMem_name()}</a></td>
											<td><a href="#content${s.index}" data-toggle="modal">${sto_content[0].getContent()}</a></td>
											
											<td>
											<c:if test='${sto_content[0].getStatus().equals("已完成")}' var="end">
												<p style="color:red;">${sto_content[0].getWay()}</p>
											</c:if>
											<c:if test='${!end}'>
												<select name="way">
													<option value="沒事">---沒事--</option>
													<option value="後端下架">後端下架</option>
													<option value="停權">---停權--</option>
												</select>
											</c:if>
											</td>
											<td>
												<input type="hidden" name="action" value="Sto_change">
												<input type="hidden" name="sto_num" value="${sto_content[0].getSto_num()}">
												<input type="hidden" name="rpt_mnum" value="${sto_content[0].getRpt_mnum()}">
		 										<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"> 
		 										<input type="hidden" name="stoContentStyleAgain" value="${stoContentStyleChinese}">
												<input type="hidden" name="whichPage"	value="<%=whichPage%>"> 
												<c:if test='${sto_content[0].getStatus().equals("已完成")}' var="finish">
													<button type="submit" class="btn  btn-green" disabled="false">確定</button>
												</c:if>
												<c:if test="${!finish}">
													<button type="submit" class="btn  btn-green">確定</button>
												</c:if>
											</td>
										</tr>
											<div class="modal fade" id="content${s.index}">
												<div class="modal-dialog">
													<div class="modal-content">
														<div class="modal-header">
															<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
															<h4 class="modal-title">檢舉原因</h4>
														</div>
														<div class="modal-body">
															<p>${sto_content[0].getContent()}</p>
														</div>
														<div class="modal-footer">
															<button type="button" class="btn  btn-green" data-dismiss="modal">是</button>
															<button type="button" class="btn btn-default" data-dismiss="modal">否</button>
														</div>
													</div>
												</div>
											</div>
										</form>
									</c:forEach>
								</tbody>
							</table>
							<c:if test="${listReportMember.size()==0}">
								<div style="color:red; font-size:100px;">暫無資料!!</div>
							</c:if>
						</div>
					<%@ include file="StoContentPage2.file" %>
					</div>
				</div>
			</div>
		</div>
		
		
		
	<!--========================== 功能放這邊 ====↑↑↑↑↑↑↑↑↑=====================================-->			
				</div><!-- class="block-center panelheight" -->			
			</div><!-- class="col-xs-12 col-sm-8 col-sm-offset-3" -->
		</div><!-- div class="row" -->
	</div><!-- div class="container-fluid" -->
	
	<jsp:include page="/back-end/back_foot.jsp" />

		
		
		
		<script src="https://code.jquery.com/jquery.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				$('#stoContentStyle').change(function(){
					 $('#formSto').submit();
				})
			});	
			
		</script>
	</body>
</html>