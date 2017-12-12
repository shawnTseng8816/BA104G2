<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.report.controller.*"%>
<%
	List<ReportMemberDetail> listReportMemberDetail = (List<ReportMemberDetail>)request.getAttribute("listReportMemberDetail");
	String reportMemberStyleChinese = (String)request.getAttribute("reportMemberStyleChinese");
%>		
	<!DOCTYPE html>
<html lang="">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
		
	</head>
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
		.block{
			height:380px;
		}
		.tall{
        	height:40px;
        }
        

	</style>
	<body>
		
		<jsp:include page="/front-end/member_top.jsp" flush="true" />
		<div class="container">
			<div class="row text-center" style="background-color:#3C9682;">
				<h1 class="tall" style="color:#ffffff;">會員檢舉專區</h1>
			</div>
			<form method="get" action="/BA104G2/reportMember/reportMember.do" id="formReMem">
				<div class="text-right">
					<select name="reportMemberStyle" id="reportMemberStyle">

						<c:if test='${reportMemberStyleChinese.equals("全部") || reportMemberStyleChinese==null}'>
							<option value="全部" selected="">全部</option>
							<option value="店家">檢舉店家</option>
							<option value="會員">檢舉會員</option>
							<option value="評論">檢舉評論</option>
						</c:if>
						<c:if test='${reportMemberStyleChinese.equals("店家")}'>
							<option value="全部">全部</option>
							<option value="店家" selected="">檢舉店家</option>
							<option value="會員">檢舉會員</option>
							<option value="評論">檢舉評論</option>
						</c:if>
						<c:if test='${reportMemberStyleChinese.equals("會員")}'>
							<option value="全部">全部</option>
							<option value="店家">檢舉店家</option>
							<option value="會員" selected="">檢舉會員</option>
							<option value="評論">檢舉評論</option>
						</c:if>
						<c:if test='${reportMemberStyleChinese.equals("評論")}'>
							<option value="全部">全部</option>
							<option value="店家">檢舉店家</option>
							<option value="會員">檢舉會員</option>
							<option value="評論" selected="">檢舉評論</option>
						</c:if>
						
					</select>
				</div>
			</form>
			<div class="block">
				<table class="table table-fixed table-striped">
				    <thead>
				        <tr>
				            <th>檢舉時間</th>
				            <c:if test='${reportMemberStyleChinese.equals("全部") || reportMemberStyleChinese==null}'>
				            	<th>被檢舉店家/會員/評論</th>
				            </c:if>
				            <c:if test='${reportMemberStyleChinese.equals("店家")}'>
				            	<th>被檢舉店家</th>
				            </c:if>
				            <c:if test='${reportMemberStyleChinese.equals("會員")}'>
				            	<th>被檢舉會員</th>
				            </c:if>
				            <c:if test='${reportMemberStyleChinese.equals("評論")}'>
				            	<th>被檢舉評論</th>
				            </c:if>
				            <th>檢舉原因</th>
				            <th>目前狀態</th>
				            <th>處理狀態</th>
				           
			            </tr>
			        </thead>
			        <tbody>
			        <%@ include file="ReportMemberPage1.file" %>
			        <c:forEach var="listReportMemberDetail" items="${listReportMemberDetail}" varStatus = "s" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
			            <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"> 
			        	<input type="hidden" name="whichPage"	value="<%=whichPage%>">
			            <tr>
			                <td>${listReportMemberDetail.getRpt_time()}</td>
			                <c:if test='${listReportMemberDetail.getSto_name()!=null}'>
				            	<td>${listReportMemberDetail.getSto_name()}</td>
				            </c:if>
				            <c:if test='${listReportMemberDetail.getMem_name()!=null}'>
				            	<td>${listReportMemberDetail.getMem_name()}</td>
				            </c:if>
				            <c:if test='${listReportMemberDetail.getCom_title()!=null}'>
				            	<td><a href="#reportCom${s.index}" data-toggle="modal">${listReportMemberDetail.getCom_title()}</a></td>
				            </c:if>
			                <td><a href="#reportMem${s.index}" data-toggle="modal">${listReportMemberDetail.getContent()}</a></td>
			                <td>${listReportMemberDetail.getStatus()}</td>
			                <td>${listReportMemberDetail.getWay()}</td>
			            </tr>
			           
			            <div class="modal fade" id="reportCom${s.index}" >
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										<h2 class="modal-title">標題:</h2>
										<h3 class="modal-title">${listReportMemberDetail.getCom_title()}</h3>
									</div>
									<div class="modal-body">
										<h4 class="modal-title">內容:</h4>
										<p>${listReportMemberDetail.getCom_comment()}</p>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-info" data-dismiss="modal">關閉</button>
									</div>
								</div>
							</div>
						</div>
			            <div class="modal fade" id="reportMem${s.index}">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										<h2>檢舉原因</h2>
									</div>
									<div class="modal-body">
										<h4 class="modal-title">內容:</h4>
										<p>${listReportMemberDetail.getContent()}</p>
										</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-info" data-dismiss="modal">確定</button>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
			        </tbody>
			    </table>
			   	<c:if test="${listReportMemberDetail.size()==0}">
					<div style="color:red; font-size:100px;">暫無資料!!</div>
				</c:if>
		    </div>
		   <%@ include file="ReportMemberPage2.file" %>
		</div>
		<script src="https://code.jquery.com/jquery.js"></script>

		<script type="text/javascript">
			$(document).ready(function(){
				$('#reportMemberStyle').change(function(){
					 $('#formReMem').submit();
				})
			});	
			
		</script>
		
		<jsp:include page="/front-end/member_foot.jsp" flush="true" />
	</body>
</html>