<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.comment.controller.*"%>
<%
	List<CommentDetail> listCommentDetailMem = (List<CommentDetail>)request.getAttribute("listCommentDetailMem");
	List<CommentDetail> listCommentDetailSto = (List<CommentDetail>)request.getAttribute("listCommentDetailSto");
	String memCommentStyleChinese = (String)request.getAttribute("memCommentStyleChinese");
	String stoCommentStyleChinese = (String)request.getAttribute("stoCommentStyleChinese");
%> 


<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<title>會員被檢舉</title>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
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
			td{  
				width:100%;  
				word-break:keep-all;  
				white-space:nowrap;  
				overflow:hidden;
				text-overflow:ellipsis; 
			}
			
		</style>
		
		<jsp:include page="/back-end/back_top.jsp" /> <!-- navbar -->
		
		<style>		
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
				<div class="col-xs-12 col-sm-12">
					<div class="mem_content">
						<div style="background-color:#595942;">
							<h1 class="text-center tall" style="color:#ffffff;">被會員檢舉評論處理</h1>
						</div>
						<%@ include file="MemCommentPage1.file" %>
						<%@ include file="StoCommentPage1.file" %>
						<form method="post" action="/BA104G2/comment/comment.do" id="formMemCom">
							<div class="text-right">
							<input type="hidden" name="stoCommentStyleChinese" value="${stoCommentStyleChinese}">
							<input type="hidden" name="whichPage"	value="<%=whichPage%>">
									<select name="memCommentStyle" id="memCommentStyle">
										<c:if test='${memCommentStyleChinese.equals("待處理")}'>
											<option value="待處理" selected="">待處理</option>
							         		<option value="已完成">已完成</option>
							         		<option value="全部">全部</option>
							         	</c:if>
							         	<c:if test='${memCommentStyleChinese.equals("已完成")}'>
											<option value="待處理">待處理</option>
							         		<option value="已完成" selected="">已完成</option>
							         		<option value="全部">全部</option>
							         	</c:if>
							         	<c:if test='${memCommentStyleChinese.equals("全部") || memCommentStyleChinese==null}'>
											<option value="待處理">待處理</option>
							         		<option value="已完成">已完成</option>
							         		<option value="全部" selected="">全部</option>
							         	</c:if>
							        </select>
							</div>
						</form>
						<div class="block">
							<table class="table table-fixed">
								<thead>	
									<tr>
										<th>時間</th>
										<th>被檢舉評論</th>
										<th>檢舉會員</th>
										<th>檢舉原因</th>
										<th>處理方式</th>
										<th></th>
									</tr>
								</thead>
								<tbody>
								
									<c:forEach var="listCommentDetailMem" items="${listCommentDetailMem}" varStatus="s" begin="<%=pageIndex1%>" end="<%=pageIndex1+rowsPerPage1-1%>">
									<form method="get" action="/BA104G2/comment/comment.do">
										<tr>
											<td>${listCommentDetailMem.getRpt_time()}</td>
											<td><a href="#memComment${s.index}" data-toggle="modal">${listCommentDetailMem.getCom_title()}</a></td>
											<td><a href="">${listCommentDetailMem.getMem_name()}</a></td>
											<td><a href="#memComment1${s.index}" data-toggle="modal">${listCommentDetailMem.getContent()}</a></td>
										
											<td>
												<c:if test='${listCommentDetailMem.getStatus().equals("已完成")}' var="end">
													<p style="color:red;">${listCommentDetailMem.getWay()}</p>
												</c:if>
												<c:if test='${!end}'>
													<select name="way">
														<option value="沒事">---沒事--</option>
														<option value="刪除">---刪除--</option>
													</select>
												</c:if>
											</td>
											<td>
												
													<input type="hidden" name="action" value="Mem_change_comment">
													<input type="hidden" name="stoCommentStyleChinese" value="${stoCommentStyleChinese}">
													<input type="hidden" name="memCommentStyleChinese" value="${memCommentStyleChinese}">
													<input type="hidden" name="rpt_mnum" value="${listCommentDetailMem.getRpt_mnum()}">
													<input type="hidden" name="com_num" value="${listCommentDetailMem.getCom_num()}">
													<input type="hidden" name="whichPage1"	value="<%=whichPage1%>">
													<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
												<input type="hidden" name="whichPage"	value="<%=whichPage%>">
													<c:if test='${listCommentDetailMem.getStatus().equals("已完成")}' var="finish">
													<button type="submit" class="btn  btn-green" disabled="false">確定</button>
													</c:if>
													<c:if test="${!finish}">
														<button type="submit" class="btn  btn-green">確定</button>
													</c:if>
												
											</td>
										</tr>
		<!-- modal==================================================================================== -->
										<div class="modal fade" id="memComment1${s.index}">
											<div class="modal-dialog">
												<div class="modal-content">
													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
														<h3 style="color:red;">檢舉原因:</h3>
													</div>
													<div class="modal-body">
														<p>${listCommentDetailMem.getContent()}</p>
														
													</div>
													<div class="modal-footer">
														<button type="button" class="btn  btn-green" data-dismiss="modal">是</button>
														<button type="button" class="btn btn-default" data-dismiss="modal">否</button>
														
													</div>
												</div>
											</div>
										</div>
										<div class="modal fade" id="memComment${s.index}">
											<div class="modal-dialog">
												<div class="modal-content">
													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
														<h3 style="color:red;">評論標題:</h3>
														<p>${listCommentDetailMem.getCom_title()}</p>
													</div>
													<div class="modal-body">
														<h4 style="color:red;">評論內容:</h4>
														<p>${listCommentDetailMem.getComment()}</p>
														
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
							<c:if test="${listCommentDetailMem.size()==0}">
								<div style="color:red; font-size:100px;">暫無資料!!</div>
							</c:if>
						</div>
						<%@ include file="MemCommentPage2.file" %>
					</div>
					<div style="background-color:#595942;">
						<h1 class="text-center tall" style="color:#ffffff;">被店家檢舉評論處理</h1>
					</div>
					<%if (pageNumber>0){%>
					  <b><font color=red>第<%=whichPage%>/<%=pageNumber%>頁</font></b>
					<%}%>
					
					<b> ● 符 合 查 詢 條 件 如 下 所 示: 共<font color=red><%=rowNumber%></font>筆</b>
					
					<form method="post" action="/BA104G2/comment/comment.do" id="formStoCom">
						<div class="text-right">
						<input type="hidden" name="memCommentStyleChinese" value="${memCommentStyleChinese}">
						<input type="hidden" name="whichPage"	value="<%=whichPage1%>">
							<select name="stoCommentStyle" id="stoCommentStyle">
								<c:if test='${stoCommentStyleChinese.equals("待處理")}'>
									<option value="待處理" selected="">待處理</option>
							     	<option value="已完成">已完成</option>
							     	<option value="全部">全部</option>
								</c:if>
							    <c:if test='${stoCommentStyleChinese.equals("已完成")}'>
									<option value="待處理">待處理</option>
							     	<option value="已完成" selected="">已完成</option>
							     	<option value="全部">全部</option>
								</c:if>
							    <c:if test='${stoCommentStyleChinese.equals("全部") || stoCommentStyleChinese==null}'>
									<option value="待處理">待處理</option>
							    	<option value="已完成">已完成</option>
							   		<option value="全部" selected="">全部</option>
							    </c:if>
							</select>
						</div>
					</form>
					
					
					<div class="mem_commend">
						<div class="block">
							<table class="table table-fixed">
								<thead>
									<tr>
										<th>時間</th>
										<th>被檢舉評論</th>
										<th>檢舉店家</th>
										<th>檢舉原因</th>
										<th>處理方式</th>
										<th></th>
									</tr>
								</thead>
								<tbody>
								
									
									<c:forEach var="listCommentDetailSto" items="${listCommentDetailSto}" varStatus="s" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
										<form method="get" action="/BA104G2/comment/comment.do">
										<tr>
											<td>${listCommentDetailSto.getRpt_time()}</td>
											<td><a href="#commentSto${s.index}" data-toggle="modal">${listCommentDetailSto.getCom_title()}</a></td>
											<td><a href="/BA104G2/store_detail/store_detail.do?sto_num=${listCommentDetailSto.getSto_num()}" target="_blank">${listCommentDetailSto.getSto_name()}</a></td>
											<td><a href="#commentSto1${s.index}" data-toggle="modal">${listCommentDetailSto.getContent()}</a></td>
											<td>
												<c:if test='${listCommentDetailSto.getStatus().equals("已完成")}' var="end">
													<p style="color:red;">${listCommentDetailSto.getWay()}</p>
												</c:if>
												<c:if test='${!end}'>
													<select name="way">
														<option value="沒事">---沒事--</option>
														<option value="刪除">---刪除--</option>
													</select>
												</c:if>
											</td>
											<td>
													<input type="hidden" name="action" value="sto_change_comment">
													<input type="hidden" name="whichPage1"	value="<%=whichPage1%>">
													<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
													<input type="hidden" name="whichPage"	value="<%=whichPage%>">
													<input type="hidden" name="stoCommentStyleChinese" value="${stoCommentStyleChinese}">
													<input type="hidden" name="memCommentStyleChinese" value="${memCommentStyleChinese}">
													<input type="hidden" name="com_num" value="${listCommentDetailSto.getCom_num()}">
													<input type="hidden" name="rpt_snum" value="${listCommentDetailSto.getRpt_snum()}">
													<c:if test='${listCommentDetailSto.getStatus().equals("已完成")}' var="finish">
														<button type="submit" class="btn  btn-green" disabled="false">確定</button>
													</c:if>
													<c:if test="${!finish}">
														<button type="submit" class="btn  btn-green">確定</button>
													</c:if>
											</td>
										</tr>
	<!-- modal==================================================================================== -->
									<div class="modal fade" id="commentSto1${s.index}">
										<div class="modal-dialog">
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
													<h3 style="color:red;">檢舉原因:</h3>
												</div>
												<div class="modal-body">
													<p>${listCommentDetailSto.getContent()}</p>
													
												</div>
												<div class="modal-footer">
													<button type="button" class="btn  btn-green" data-dismiss="modal">是</button>
													<button type="button" class="btn btn-default" data-dismiss="modal">否</button>
													
												</div>
											</div>
										</div>
									</div>
									<div class="modal fade" id="commentSto${s.index}">
										<div class="modal-dialog">
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
													<h3 style="color:red;">評論標題:</h3>
													<p>${listCommentDetailSto.getCom_title()}</p>
												</div>
												<div class="modal-body">
													<h4 style="color:red;">評論內容:</h4>
													<p>${listCommentDetailSto.getComment()}</p>
													
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
						<c:if test="${listCommentDetailSto.size()==0}">
							<div style="color:red; font-size:100px;">暫無資料!!</div>
						</c:if>
					</div>
				<%@ include file="StoCommentPage2.file" %>
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
				$('#memCommentStyle').change(function(){
					 $('#formMemCom').submit();
				})
				$('#stoCommentStyle').change(function(){
					 $('#formStoCom').submit();
				})
			});	
			
		</script>
	</body>
</html>