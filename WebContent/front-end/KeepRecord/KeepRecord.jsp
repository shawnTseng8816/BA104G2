<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.keep_record.controller.*"%>
<%		
		
List<KeepRecordDetail> keepList = (List<KeepRecordDetail>)request.getAttribute("keepList");

%>


<!DOCTYPE html>
<html lang="">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
		<script src="<%=request.getContextPath()%>/js/dist/sweetalert2.all.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/dist/sweetalert2.min.js"></script>

		<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
		<style type="text/css">
        .keepImg{
			margin-top: -25%;/*圖片往上移動*/
			width: 85%;
        }
        .well{
            background: #fff;/*圖片框架的顏色*/
            box-shadow: 0 0 0 0;/*圖片升起速度*/
        }
        .pricing-table{
            transition:all 0.3s ease-out;/*圖片升起來的速度*/
        }
        .pricing-table:hover{
             box-shadow:0 70px 100px #444;/*升起圖片高度和陰影*/
             margin-top: -1.7%;
         }
        .pricing-feature-list{
            padding: 10%;/*欄位大小*/
            background: #444;/*欄位外框顏色*/
            color: #fff;/*欄位字體顏色*/
        }
       .pricing-feature-list .list-group-item {
			padding: 5px 0px;/*欄位框架大小*/
			background-color: #444;/*欄位內框顏色*/
			font-size: 18px;/*欄位字體大小*/
            border: 0px;
		}
        .pricing-table-holder{
            background: #ccc;/*產品名稱欄位的顏色*/
            padding: 10%;/*框架整體往下一10%*/
        }
        .btn-info{
            color: #000;/*領取寄杯字*/
			background-color: #fff;/*領取寄杯按紐*/
			border-color: #f00;/*領取寄杯的框框顏色*/
			padding: 4%;/*領取寄杯框大小*/
			transition: all 0.4s ease-in;/*領取寄杯按鈕顏色變換時間*/
        }
        .danger{
        	background-color: #f00;/*領取寄杯按紐*/
        	padding: 4%;/*領取寄杯框大小*/
        }
        .tall{
        	height:80px;
        	padding-top:21px;
        }
		</style>
	</head>
	<body>
	
<jsp:include page="/front-end/member_top.jsp" />			
	    <div class="container text-center">
	    	<div class="page-header">
	    		<div style="background-color:#3C9682;">
	    			<h1 class="tall" style="color:#ffffff;">寄杯記錄</h1>
	    		</div>
	    		<form method="post" action="/BA104G2/keepRecordSearch/keepRecordSearch.do" id="formShawn">
	    		<input type="hidden" name="action" value="KeepRecord">
		        <div class="text-right">
		    	 	<select name="KeepRecordStyle" id="KeepRecordStyle">
		    	 	
		    	 	<c:if test='${statusString.equals("未領取")}'>
		         		<option value="未領取" selected="">未領取</option>
		         		<option value="申請中">申請中</option>
		         		<option value="審核通過">審核通過</option>
		         		<option value="已領取">已領取</option>
		         		<option value="全部">全部</option>
		    	 	</c:if>
		    	 	<c:if test='${statusString.equals("申請中")}'>
		         		<option value="未領取">未領取</option>
		         		<option value="申請中" selected="">申請中</option>
		         		<option value="審核通過">審核通過</option>
		         		<option value="已領取">已領取</option>
		         		<option value="全部">全部</option>
		    	 	</c:if>
		    	 	<c:if test='${statusString.equals("審核通過")}'>
		         		<option value="未領取">未領取</option>
		         		<option value="申請中">申請中</option>
		         		<option value="審核通過" selected="">審核通過</option>
		         		<option value="已領取">已領取</option>
		         		<option value="全部">全部</option>
		    	 	</c:if>
		    	 	<c:if test='${statusString.equals("已領取")}'>
		         		<option value="未領取">未領取</option>
		         		<option value="申請中">申請中</option>
		         		<option value="審核通過">審核通過</option>
		         		<option value="已領取" selected="">已領取</option>
		         		<option value="全部">全部</option>
		    	 	</c:if>
		    	 	<c:if test='${statusString.equals("全部") || statusString==null}'>
		         		<option value="未領取">未領取</option>
		         		<option value="申請中">申請中</option>
		         		<option value="審核通過">審核通過</option>
		         		<option value="已領取">已領取</option>
		         		<option value="全部" selected="">全部</option>
		    	 	</c:if>
		    	 		
		         	</select>
	    		</div>
	    		</form>
	    	</div>
	    </div>
	    
	    <c:forEach var="keepList" items="${keepList}" varStatus = "s">
	    	<c:if test = "${s.index%3==0 }">
				<div class="container aa">
		    	<div class="row">
		  	</c:if>
		        <div class="col-md-4 well pricing-table">
		            <div class="pricing-table-holder">
		                <center>
		                    <img class="keepImg" src="/BA104G2/GetPicShawn?sto_num=${keepList.getSto_num()}&com_num=${keepList.getCom_num()}&getType=keepRecord">
		                    <h3>${keepList.getCom_name()}</h3>
		                </center>
		            </div>
		            <div class="custom-button-group">
	                    <div class="col-md-12 col-sm-12" style="padding:0;">
	                        <p class="btn btn-primary  btn-block">${keepList.getKeep_status()}</p>
	                    </div>
		            </div>
		            <div class="pricing-feature-list">
		                <ul class="list-group">
		               	    <li class="list-group-item">訂單編號: ${keepList.getKeep_num()}</li>
	                        <li class="list-group-item">店家名稱: ${keepList.getSto_name()}</li>
	                        <li class="list-group-item">冰塊: ${keepList.getIce_type()}</li>
	                        <li class="list-group-item">甜度: ${keepList.getSweet_type()}</li>
	                        <li class="list-group-item">SIZE: ${keepList.getCom_size()}</li>
	                        <c:if test = "${keepList.getRec_time()==null}" var="norectime">
	                      	    <li class="list-group-item">寄杯時間: ${keepList.getKeep_time()}</li>
	                        </c:if>
	                        <c:if test = "${!norectime}">
	                      	    <li class="list-group-item">領取時間: ${keepList.getRec_time()}</li>
	                        </c:if>
	                        <c:if test='${keepList.getExt_name()==""}' var="noadd">
	                        	<li class="list-group-item">加料: 無</li>
	                        </c:if>
	                        <c:if test="${!noadd}">
	                        	<li class="list-group-item">加料: ${keepList.getExt_name()}</li>
	                      	</c:if>
	                    </ul>
		            </div>
		            <c:if test='${keepList.getKeep_status().equals("已領取")}' var="getKeep">
			            <div>
			            	<button class="btn danger btn-danger btn-block" disabled="false">已領取寄杯</button>
			            </div>
		            </c:if>
		            <form method="post" action="/BA104G2/keepRecordSearch/keepRecordSearch.do" id="sub">
			            <c:if test='${keepList.getKeep_status().equals("未領取")}'>
				            <div>
				            	<input type="hidden" name="action" value="keep_record_change">
								<input type="hidden" name="keep_num" value="${keepList.getKeep_num()}">
								<input type="hidden" name="statusStringAgain" value="${statusString}">
				            	<button type="button" class="btn btn-info btn-block record">領取寄杯</button>
				            </div>
			            </c:if>
			        </form>
		            <c:if test='${keepList.getKeep_status().equals("申請中")}'>
			            <div>
			            	<button class="btn danger btn-danger btn-block" disabled="false">送出申請</button>
			            </div>
		            </c:if>
		            <c:if test='${keepList.getKeep_status().equals("審核成功")}'>
			            <div>
			            	<button class="btn danger btn-danger btn-block" disabled="false">可領取寄杯囉</button>
			            </div>
		            </c:if>
		        </div>
			<c:if test = "${s.index%3==2 || s.last}">
				</div>
				</div>
			</c:if>
		
		</c:forEach>
		<c:if test="${keepList.size()==0}">
			<div class="text-center" style="color:red; font-size:100px;">暫無資料!!</div>
		</c:if>


		<script type="text/javascript">
			$(document).ready(function(){
				$(".record").click(function(){
					swal({
						  position: 'center',
						  type: 'success',
						  title: '已送出申請',
						  showConfirmButton: true,
						  timer: 2000
						})
					setTimeout("test1()",2000);
					
				})
				$('#KeepRecordStyle').change(function(){
					 $('#formShawn').submit();
				})
			});	
			function test1(){
				$('#sub').submit();
			}
			
		</script>
	</body>
	
	
		<jsp:include page="/front-end/member_foot.jsp" />	
</html>